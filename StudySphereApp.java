/**import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import java.time.LocalDate;
import java.sql.*;

public class StudySphereApp extends Application {

    private BorderPane mainLayout;
    private VBox sidePane;
    private VBox taskDisplayBox; // To display tasks
    private DatePicker calendar; // Calendar DatePicker
    private String dbUrl = "jdbc:postgresql://localhost:5432/studysphere"; // Your DB details
    private String dbUser = "myuser";
    private String dbPassword = "12345";
    private int userId; // User ID to filter tasks

    // Constructor to accept user ID
    public StudySphereApp(int userId) {
        this.userId = userId; // Initialize user ID
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("StudySphere - Group Study Platform");

        mainLayout = new BorderPane();
        sidePane = createSidePane();
        mainLayout.setLeft(sidePane);
        mainLayout.setCenter(createMainContent());

        // Create Calendar for right pane
        calendar = new DatePicker();
        calendar.setShowWeekNumbers(true);
        calendar.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); // Disable past dates
                }
            }
        });
        calendar.setOnAction(e -> showTasksForSelectedDate());

        taskDisplayBox = new VBox();
        taskDisplayBox.setAlignment(Pos.TOP_CENTER);
        taskDisplayBox.setPadding(new Insets(20));
        taskDisplayBox.setSpacing(15);

        VBox calendarBox = new VBox(20, new Label("Task Calendar"), calendar, taskDisplayBox);
        calendarBox.setPadding(new Insets(20));
        calendarBox.setStyle("-fx-background-color: rgba(240, 248, 255, 0.9);");
        calendarBox.setPrefWidth(400);
        mainLayout.setRight(calendarBox);

        Scene scene = new Scene(mainLayout, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createSidePane() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(20);
        vbox.setStyle("-fx-background-color: rgba(0, 123, 255, 0.9);");

        Button scheduleButton = createStyledButton("Schedule Assignments");
        scheduleButton.setOnAction(e -> showScheduleDialog());

        vbox.getChildren().add(scheduleButton);
        return vbox;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", 16));
        button.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8); -fx-text-fill: #007BFF; -fx-padding: 10px; -fx-font-weight: bold;");
        button.setMinWidth(150);
        return button;
    }

    private VBox createMainContent() {
        VBox mainContent = new VBox();
        mainContent.setAlignment(Pos.CENTER);
        mainContent.setPadding(new Insets(20));
        mainContent.getChildren().add(new Label("Welcome to StudySphere! User ID: " + userId));
        return mainContent;
    }

    private void showScheduleDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Schedule Assignments");

        VBox dialogContent = new VBox(15);
        dialogContent.setPadding(new Insets(15));
        dialogContent.setStyle("-fx-background-color: #f0f8ff;");

        TextField taskNameField = new TextField();
        taskNameField.setPromptText("Enter task name");
        taskNameField.setStyle("-fx-background-color: #eef; -fx-padding: 10px; -fx-border-radius: 5px;");

        DatePicker taskDatePicker = new DatePicker();
        taskDatePicker.setPromptText("Select date");
        taskDatePicker.setStyle("-fx-padding: 10px;");

        taskDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });

        TextField taskTimeField = new TextField();
        taskTimeField.setPromptText("Enter time as an integer (HHMM)");
        taskTimeField.setStyle("-fx-background-color: #eef; -fx-padding: 10px; -fx-border-radius: 5px;");

        Button submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Arial", 14));
        submitButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-weight: bold;");

        submitButton.setOnAction(e -> {
            String taskName = taskNameField.getText();
            LocalDate selectedDate = taskDatePicker.getValue();
            String taskDate = selectedDate != null ? selectedDate.toString() : null;
            String taskTime = taskTimeField.getText();

            if (taskName.isEmpty() || taskDate == null || taskTime.isEmpty()) {
                showErrorDialog("All fields must be filled out.");
            } else {
                try {
                    Integer.parseInt(taskTime); // Ensure taskTime is an integer
                    insertTaskToDatabase(taskName, taskDate, taskTime);
                    dialog.close();
                } catch (NumberFormatException ex) {
                    showErrorDialog("Task time must be a valid integer (HHMM).");
                }
            }
        });

        dialogContent.getChildren().addAll(new Label("Task Name:"), taskNameField, new Label("Date:"), taskDatePicker, new Label("Time (HHMM):"), taskTimeField, submitButton);
        dialog.getDialogPane().setContent(dialogContent);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showTasksForSelectedDate() {
        taskDisplayBox.getChildren().clear(); // Clear the previous tasks
        LocalDate selectedDate = calendar.getValue();

        if (selectedDate != null) {
            String sql = "SELECT * FROM task_schedule WHERE task_date = ? AND user_id = ?"; // Include user_id in the query
            try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setDate(1, java.sql.Date.valueOf(selectedDate));
                pstmt.setInt(2, userId); // Set user ID in the query
                ResultSet rs = pstmt.executeQuery();

                boolean tasksExist = false; // To check if any task exists for the selected date

                // Loop through all tasks and display them
                while (rs.next()) {
                    tasksExist = true;

                    String taskName = rs.getString("task_name");
                    int taskTime = rs.getInt("task_time"); // Changed to integer

                    Label taskLabel = new Label("Task: " + taskName);
                    taskLabel.setFont(Font.font("Arial", 16));
                    taskLabel.setStyle("-fx-font-weight: bold;");

                    Label timeLabel = new Label("Time: " + taskTime);
                    timeLabel.setFont(Font.font("Arial", 14));

                    Button completedButton = new Button("Completed");
                    completedButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-padding: 8px 15px; -fx-font-size: 14px;");
                    completedButton.setOnAction(e -> {
                        deleteTaskFromDatabase(taskName, selectedDate.toString(), taskTime);
                        showTasksForSelectedDate(); // Refresh the task list
                    });

                    VBox taskBox = new VBox(10, taskLabel, timeLabel, completedButton);
                    taskBox.setPadding(new Insets(10));
                    taskBox.setStyle("-fx-background-color: #d3e3f7; -fx-padding: 15px; -fx-border-radius: 10px;");
                    taskDisplayBox.getChildren().add(taskBox); // Add task to the display box
                }

                if (!tasksExist) {
                    // If no tasks exist for the selected date, show a message
                    Label noTaskLabel = new Label("No tasks scheduled for this date.");
                    noTaskLabel.setFont(Font.font("Arial", 16));
                    noTaskLabel.setStyle("-fx-font-weight: bold;");
                    taskDisplayBox.getChildren().add(noTaskLabel);
                }

                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
                showErrorDialog("Database error occurred while fetching tasks.");
            }
        }
    }

    private void insertTaskToDatabase(String taskName, String taskDate, String taskTime) {
        String sql = "INSERT INTO task_schedule (task_name, task_date, task_time, user_id) VALUES (?, ?, ?, ?)"; // Include user_id
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, taskName);
            pstmt.setDate(2, java.sql.Date.valueOf(taskDate));

            // Convert taskTime from String to Integer
            pstmt.setInt(3, Integer.parseInt(taskTime)); // Set task_time as INTEGER
            pstmt.setInt(4, userId); // Set user ID in the query
            pstmt.executeUpdate();
            showTasksForSelectedDate(); // Refresh task display
        } catch (NumberFormatException ex) {
            showErrorDialog("Task time must be a valid integer.");
        } catch (SQLException e) {
            showErrorDialog("Database error: " + e.getMessage());
        }
    }

    private void deleteTaskFromDatabase(String taskName, String taskDate, String taskTime) {
        String sql = "DELETE FROM task_schedule WHERE task_name = ? AND task_date = ? AND task_time = ? AND user_id = ?"; // Include user_id
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, taskName);
            pstmt.setDate(2, java.sql.Date.valueOf(taskDate));

            // Convert taskTime from String to Integer
            pstmt.setInt(3, Integer.parseInt(taskTime)); // Set task_time as INTEGER
            pstmt.setInt(4, userId); // Set user ID in the query
            pstmt.executeUpdate();
        } catch (NumberFormatException ex) {
            showErrorDialog("Task time must be a valid integer.");
        } catch (SQLException e) {
            showErrorDialog("Database error: " + e.getMessage());
        }
    }
}**/
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class StudySphereApp extends Application {

    private BorderPane mainLayout;
    private VBox sidePane;
    private int userId;
    public StudySphereApp(int userId) { // Constructor to accept user ID
        this.userId = userId;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("StudySphere - Group Study Platform");

        mainLayout = new BorderPane();
        sidePane = createSidePane();

        mainLayout.setLeft(sidePane);
        mainLayout.setCenter(createMainContent());

        // Load background image
        Image backgroundImage = new Image("resources/bgimg.png"); // Update with the correct path
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(1200); // Set width to match scene width
        backgroundImageView.setFitHeight(850); // Set height to match scene height
        // Add ImageView to the StackPane
        StackPane background = new StackPane();
        background.getChildren().addAll(backgroundImageView, createMainContent());
        mainLayout.setCenter(background);

        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createSidePane() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(40));
        vbox.setSpacing(40);
        vbox.setStyle("-fx-background-color: #a19e9b;"); // Set the sidebar background color

        // Create buttons with styles
        Button createGroupButton = createStyledButton("Create Group");
        createGroupButton.setOnAction(e -> showCreateGroupWindow()); // Update here to show CreateGroup window
        Button viewGroupButton = createStyledButton("View Group");
        viewGroupButton.setOnAction(e -> showViewGroupDialog());

        vbox.getChildren().addAll(createGroupButton, viewGroupButton);

        for (Button button : new Button[]{createGroupButton, viewGroupButton}) {
            VBox.setVgrow(button, javafx.scene.layout.Priority.ALWAYS);
        }

        return vbox;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        button.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 16px;" +
                "-fx-padding: 10 20 10 20; -fx-background-radius: 30;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #ff570a; -fx-text-fill: white; " +
                "-fx-font-size: 16px; -fx-padding: 10 20 10 20; -fx-background-radius: 30;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: white; -fx-text-fill: black; " +
                "-fx-font-size: 16px; -fx-padding: 10 20 10 20; -fx-background-radius: 30;"));

        return button;
    }

    private StackPane createMainContent() {
        StackPane mainContent = new StackPane();
        mainContent.setAlignment(Pos.CENTER);
        mainContent.setPadding(new Insets(20));

        // Title
        Text welcomeText = new Text("WELCOME TO STUDYSPHERE!");
        welcomeText.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        welcomeText.setFill(Color.BLACK);

        mainContent.getChildren().add(welcomeText);

        return mainContent;
    }

    private void showCreateGroupWindow() {
        // Open the CreateGroup window
        CreateGroup createGroup = new CreateGroup();
        Stage createGroupStage = new Stage();
        createGroup.start(createGroupStage);
    }

    private void showViewGroupDialog() {
        // Open the ViewGroup window and pass the userId
        ViewGroup viewGroup = new ViewGroup(userId); // Pass userId here
        Stage viewGroupStage = new Stage();
        viewGroup.start(viewGroupStage);
    }
}
