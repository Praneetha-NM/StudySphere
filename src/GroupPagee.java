import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;

import java.sql.*;

public class GroupPagee extends Application {

    private int groupId;
    private int userId;
    private VBox messageContainer;

    private String dbUrl = "jdbc:postgresql://localhost:5432/studysphere";
    private String dbUser = "myuser";
    private String dbPassword = "12345";

    public GroupPagee(int groupId, int userId) {
        this.groupId = groupId;
        this.userId = userId;
    }

    private VBox createSidePanel() {
        VBox sidePanel = new VBox(40); // Reduced spacing for a better layout
        sidePanel.setStyle("-fx-background-color: #00cc99; -fx-pref-width: 200px; -fx-padding: 15;");

        // Create buttons
        Button scheduleAssignmentsButton = createStyledButton("Schedule Assignments");
        scheduleAssignmentsButton.setOnAction(e -> {
            // Logic for scheduling assignments
            System.out.println("Schedule Assignments button clicked");
            try {
                // Pass groupId to AssignmentPage
                AssignmentPage assignmentPage = new AssignmentPage(groupId);
                Stage assignmentStage = new Stage();
                assignmentPage.start(assignmentStage); // Call start() method of AssignmentPage
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button viewCalendarButton = createStyledButton("View Calendar");
        viewCalendarButton.setOnAction(e -> {
            // Logic for viewing calendar
            System.out.println("View Calendar button clicked");
            // Open CalendarPage
            try {
                CalendarPage calendarPage = new CalendarPage();
                Stage calendarStage = new Stage();
                calendarPage.start(calendarStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Add buttons to the side panel
        sidePanel.getChildren().addAll(scheduleAssignmentsButton, viewCalendarButton);
        return sidePanel;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Group Messaging - Group " + groupId);

        BorderPane mainLayout = new BorderPane();
        VBox sidePanel = createSidePanel();
        VBox messagingArea = createMessagingArea();

        mainLayout.setLeft(sidePanel);
        mainLayout.setCenter(messagingArea);

        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        loadPreviousMessages();
    }

    private VBox createMessagingArea() {
        VBox messagingArea = new VBox(50);
        messagingArea.setStyle("-fx-background-color: #ECF0F1; -fx-pref-width: 600px; -fx-padding: 10;");

        messageContainer = new VBox(20);
        ScrollPane scrollPane = new ScrollPane(messageContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #BDC3C7; -fx-border-radius: 5;");

        VBox.setVgrow(scrollPane, javafx.scene.layout.Priority.ALWAYS);

        HBox inputArea = new HBox(10);
        TextField messageInput = new TextField();
        messageInput.setPromptText("Type your message...");
        messageInput.setStyle("-fx-background-color: #F7F9F9; -fx-border-color: #BDC3C7; -fx-border-radius: 5;-fx-min-width:900px;");
        messageInput.setFont(Font.font("Arial", 14));

        Button sendButton = createStyledButton("Send");
        sendButton.setOnAction(e -> {
            String message = messageInput.getText().trim();
            if (!message.isEmpty()) {
                displayMessage("You", message);
                saveMessageToDatabase(message);
                messageInput.clear();
            }
        });
        sendButton.setStyle("-fx-background-color: #00cc99; -fx-border-color: #BDC3C7; -fx-border-radius: 5;-fx-min-width:50px;");

        inputArea.getChildren().addAll(messageInput, sendButton);
        messagingArea.getChildren().addAll(createMessagesLabel(), scrollPane, inputArea);
        return messagingArea;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black; -fx-font-size: 14px; -fx-padding: 10;");
        button.setPrefWidth(180);
        return button;
    }

    private Label createMessagesLabel() {
        Label messagesLabel = new Label("Group Chat");
        messagesLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        messagesLabel.setTextFill(Color.BLACK);
        return messagesLabel;
    }

    private void displayMessage(String username, String message) {
        Label messageLabel = new Label(username + ": " + message);
        messageLabel.setStyle("-fx-padding: 5; -fx-background-color: #F0F3F4; -fx-border-color: #BDC3C7; -fx-border-radius: 5;");
        messageLabel.setWrapText(true);
        messageContainer.getChildren().add(messageLabel);
    }

    private void loadPreviousMessages() {
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String sql = "SELECT u.full_name, m.message FROM messages m JOIN users u ON m.user_id = u.id WHERE m.group_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, groupId);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String username = rs.getString("full_name");
                    String message = rs.getString("message");
                    displayMessage(username, message);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveMessageToDatabase(String message) {
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String sql = "INSERT INTO messages (user_id, group_id, message) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, userId);
                pstmt.setInt(2, groupId);
                pstmt.setString(3, message);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


