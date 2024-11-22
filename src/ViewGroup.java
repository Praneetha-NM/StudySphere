import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViewGroup extends Application {

    private String dbUrl = "jdbc:postgresql://localhost:5432/studysphere";
    private String dbUser = "myuser";
    private String dbPassword = "12345";
    private int currentUserId;
    public ViewGroup(int currentUserId) {
        this.currentUserId = currentUserId;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Your Groups");

        VBox layout = new VBox(10);
        ListView<String> groupListView = new ListView<>();
        List<Integer> groupIds = new ArrayList<>();

        // Fetch the groups the current user is part of
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            System.out.println("Connected to the database."); // Debugging line
            String sql = "SELECT g.group_id, g.group_name FROM groups g " +
                    "JOIN group_users gu ON g.group_id = gu.group_id WHERE gu.user_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, currentUserId);
                ResultSet rs = pstmt.executeQuery();

                // Print query execution success message
                System.out.println("Query executed");

                // Fetch results from the query
                while (rs.next()) {
                    String groupName = rs.getString("group_name");
                    System.out.println("Found group: " + groupName); // Debugging line
                    groupListView.getItems().add(groupName);
                    groupIds.add(rs.getInt("group_id"));
                }

                // If no groups were found, print a message for debugging
                if (groupIds.isEmpty()) {
                    System.out.println("No groups found for user with ID: " + currentUserId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // When a group is clicked, open the messaging page
        groupListView.setOnMouseClicked(event -> {
            int selectedIndex = groupListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                int selectedGroupId = groupIds.get(selectedIndex);
                System.out.println("Selected group ID: " + selectedGroupId); // Debugging line
                openMessagingPage(selectedGroupId, primaryStage);
            }
        });

        layout.getChildren().add(groupListView);
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Opens the messaging page for the selected group
    private void openMessagingPage(int groupId, Stage primaryStage) {
        GroupPagee groupPagee = new GroupPagee(groupId, currentUserId);
        try {
            Stage messagingStage = new Stage();
            groupPagee.start(messagingStage);
            primaryStage.close(); // Optionally close the current window
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
