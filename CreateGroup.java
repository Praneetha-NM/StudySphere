import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CreateGroup extends Application {

    private String dbUrl = "jdbc:postgresql://localhost:5432/studysphere"; // Your DB details
    private String dbUser = "myuser";
    private String dbPassword = "12345";
    private TextField groupNameField;
    private TextArea groupDescriptionField;
    private List<TextField> usernameFields = new ArrayList<>(); // List of username input fields
    private final int MAX_USERS = 10; // Maximum number of users in a group

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Create a New Group");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        // Group Name
        Label groupNameLabel = new Label("Group Name:");
        groupNameField = new TextField();
        groupNameField.setPromptText("Enter group name");

        // Group Description
        Label groupDescriptionLabel = new Label("Group Description:");
        groupDescriptionField = new TextArea();
        groupDescriptionField.setPromptText("Enter group description");
        groupDescriptionField.setPrefRowCount(3);

        // Usernames Grid
        GridPane usernamesGrid = new GridPane();
        usernamesGrid.setHgap(10);
        usernamesGrid.setVgap(10);
        usernamesGrid.setPadding(new Insets(10));
        for (int i = 0; i < MAX_USERS; i++) {
            Label usernameLabel = new Label("Username " + (i + 1) + ":");
            TextField usernameField = new TextField();
            usernameField.setPromptText("Enter username");
            usernamesGrid.addRow(i, usernameLabel, usernameField);
            usernameFields.add(usernameField);
        }

        // Submit Button
        Button createButton = new Button("Create Group");
        createButton.setOnAction(e -> createGroup());

        // Adding elements to the layout
        layout.getChildren().addAll(groupNameLabel, groupNameField, groupDescriptionLabel, groupDescriptionField, usernamesGrid, createButton);

        Scene scene = new Scene(layout, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createGroup() {
        String groupName = groupNameField.getText();
        String groupDescription = groupDescriptionField.getText();

        if (groupName.isEmpty() || groupDescription.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Group Creation Error", "Group name and description cannot be empty.");
            return;
        }

        // Collect usernames
        List<String> usernames = new ArrayList<>();
        for (TextField usernameField : usernameFields) {
            String username = usernameField.getText();
            if (!username.isEmpty()) {
                usernames.add(username);
            }
        }

        if (usernames.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Group Creation Error", "At least one username must be provided.");
            return;
        }

        // Check that we have no more than 10 users
        if (usernames.size() > MAX_USERS) {
            showAlert(Alert.AlertType.ERROR, "Group Creation Error", "You can only add up to 10 users.");
            return;
        }

        // Fetch user IDs from usernames and create the group
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            // Fetch user IDs for the provided usernames
            List<Integer> userIds = new ArrayList<>();
            for (String username : usernames) {
                String sql = "SELECT id FROM users WHERE full_name = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, username);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        userIds.add(rs.getInt("id"));
                    } else {
                        showAlert(Alert.AlertType.ERROR, "User Error", "Username '" + username + "' not found.");
                        return;
                    }
                }
            }

            // Insert the group into the groups table
            String groupSql = "INSERT INTO groups (group_name, group_description) VALUES (?, ?) RETURNING group_id";
            int groupId;
            try (PreparedStatement groupStmt = conn.prepareStatement(groupSql)) {
                groupStmt.setString(1, groupName);
                groupStmt.setString(2, groupDescription);
                ResultSet groupRs = groupStmt.executeQuery();
                if (groupRs.next()) {
                    groupId = groupRs.getInt("group_id");
                } else {
                    throw new SQLException("Failed to create group.");
                }
            }

            // Insert user-group associations
            String groupUserSql = "INSERT INTO group_users (group_id, user_id) VALUES (?, ?)";
            try (PreparedStatement groupUserStmt = conn.prepareStatement(groupUserSql)) {
                for (int userId : userIds) {
                    groupUserStmt.setInt(1, groupId);
                    groupUserStmt.setInt(2, userId);
                    groupUserStmt.addBatch();
                }
                groupUserStmt.executeBatch();
            }

            showAlert(Alert.AlertType.INFORMATION, "Success", "Group created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error occurred while creating group: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

