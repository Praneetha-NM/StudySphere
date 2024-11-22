/**import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AssignmentPage extends Application {
    private VBox assignmentList;
    private List<Assignment> assignments = new ArrayList<>();
    private VBox inputPanel;
    private TextField topicInput;
    private TextArea descriptionInput;
    private DatePicker deadlineInputDate;
    private TextField deadlineInputTime;
    private int groupId;

    public AssignmentPage(int groupId) {
        this.groupId = groupId;
    }
    public AssignmentPage() {
    }

    public void start(Stage stage) {
        stage.setTitle("Assignments - StudySphere");
        VBox layout = new VBox(20.0);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-padding: 30;");
        Label title = new Label("Assignments Scheduled");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 24.0));
        title.setTextFill(Color.BLACK);

        this.assignmentList = new VBox(10.0);
        this.assignmentList.setAlignment(Pos.CENTER_LEFT);

        this.inputPanel = new VBox(10.0);
        this.inputPanel.setAlignment(Pos.CENTER);
        this.inputPanel.setStyle("-fx-padding: 20; -fx-border-color: #00cc99; -fx-border-width: 2; -fx-background-color: #f9f9f9;");
        this.inputPanel.setVisible(false);

        this.topicInput = new TextField();
        this.topicInput.setPromptText("Assignment Topic");
        this.descriptionInput = new TextArea();
        this.descriptionInput.setPromptText("Assignment Description");
        this.deadlineInputDate = new DatePicker();
        this.deadlineInputDate.setPromptText("Deadline Date");
        this.deadlineInputTime = new TextField();
        this.deadlineInputTime.setPromptText("Deadline Time (HH:mm)");

        Button submitButton = new Button("Submit Assignment");
        submitButton.setOnAction((event) -> {
            this.handleAssignmentSubmission();
        });

        this.inputPanel.getChildren().addAll(new Label("Assignment Topic:"), this.topicInput, new Label("Description:"), this.descriptionInput, new Label("Deadline Date:"), this.deadlineInputDate, new Label("Deadline Time:"), this.deadlineInputTime, submitButton);

        Button scheduleButton = new Button("Schedule an Assignment");
        scheduleButton.setStyle("-fx-background-color: #00cc99; -fx-text-fill: white;");
        scheduleButton.setOnAction((event) -> {
            this.inputPanel.setVisible(true);
        });

        HBox bottomBox = new HBox(scheduleButton);
        bottomBox.setAlignment(Pos.BOTTOM_CENTER);
        bottomBox.setStyle("-fx-padding: 20;");

        layout.getChildren().addAll(title, this.assignmentList, bottomBox, this.inputPanel);

        Scene scene = new Scene(layout, 800.0, 600.0);
        stage.setScene(scene);
        stage.show();
    }

    private void handleAssignmentSubmission() {
        String topic = this.topicInput.getText();
        String description = this.descriptionInput.getText();
        LocalDateTime deadline;

        if (this.deadlineInputDate.getValue() == null || this.deadlineInputTime.getText().isEmpty()) {
            this.showError("Please enter both date and time.");
            return;
        }

        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalDate deadlineDate = this.deadlineInputDate.getValue();
            LocalTime deadlineTime = LocalTime.parse(this.deadlineInputTime.getText().trim(), timeFormatter);

            // Combine date and time
            deadline = LocalDateTime.of(deadlineDate, deadlineTime);

            if (deadline.isBefore(LocalDateTime.now())) {
                this.showError("Please enter a deadline in the future.");
                return;
            }
        } catch (Exception e) {
            this.showError("Invalid date/time format. Time should be in HH:mm format.");
            return;
        }

        Assignment newAssignment = new Assignment(topic, description, deadline);
        this.assignments.add(newAssignment);
        this.sortAssignmentsByDeadline();
        this.displayAssignments();
        this.clearInputFields();
        this.inputPanel.setVisible(false);
    }

    private void sortAssignmentsByDeadline() {
        Collections.sort(assignments, Comparator.comparing(Assignment::getDeadline));
    }

    private void displayAssignments() {
        this.assignmentList.getChildren().clear();

        for (Assignment assignment : assignments) {
            Label assignmentLabel = new Label(assignment.toString());
            assignmentLabel.setMinWidth(1100.0);
            assignmentLabel.setStyle("-fx-padding: 150; -fx-border-width: 1; -fx-border-color: black;");

            if (assignment.getDeadline().isBefore(LocalDateTime.now())) {
                assignmentLabel.setStyle("-fx-background-color: #ff570a; -fx-text-fill: white;");
            } else {
                assignmentLabel.setStyle("-fx-background-color: #00cc99; -fx-text-fill: black;");
            }
            this.assignmentList.getChildren().add(assignmentLabel);
        }
    }

    private void clearInputFields() {
        this.topicInput.clear();
        this.descriptionInput.clear();
        this.deadlineInputDate.setValue(null);
        this.deadlineInputTime.clear();
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class Assignment {
        private String topic;
        private String description;
        private LocalDateTime deadline;

        public Assignment(String topic, String description, LocalDateTime deadline) {
            this.topic = topic;
            this.description = description;
            this.deadline = deadline;
        }

        public LocalDateTime getDeadline() {
            return deadline;
        }

        @Override
        public String toString() {
            return "Topic: " + topic + "\nDescription: " + description + "\nDeadline: " + deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }
    }
}**/
/**import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

public class AssignmentPage extends Application {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/studysphere"; // Change to your database name
    private static final String USER = "myuser"; // Change to your username
    private static final String PASSWORD = "12345";
    private VBox assignmentList;
    private List<Assignment> assignments = new ArrayList<>();
    private VBox inputPanel;
    private TextField topicInput;
    private TextArea descriptionInput;
    private DatePicker deadlineInputDate;
    private TextField deadlineInputTime;
    private int groupId;

    public AssignmentPage(int groupId) {
        this.groupId = groupId;
    }
    public AssignmentPage() {
    }

    public void start(Stage stage) {
        stage.setTitle("Assignments - StudySphere");
        VBox layout = new VBox(20.0);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-padding: 30;");
        Label title = new Label("Assignments Scheduled");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 24.0));
        title.setTextFill(Color.BLACK);

        this.assignmentList = new VBox(10.0);
        this.assignmentList.setAlignment(Pos.CENTER_LEFT);

        this.inputPanel = new VBox(10.0);
        this.inputPanel.setAlignment(Pos.CENTER);
        this.inputPanel.setStyle("-fx-padding: 20; -fx-border-color: #00cc99; -fx-border-width: 2; -fx-background-color: #f9f9f9;");
        this.inputPanel.setVisible(false);
        int currentGroupId = groupId; // Replace this with the actual group ID you're fetching
        fetchAssignmentsFromDB(currentGroupId);
        this.topicInput = new TextField();
        this.topicInput.setPromptText("Assignment Topic");
        this.descriptionInput = new TextArea();
        this.descriptionInput.setPromptText("Assignment Description");
        this.deadlineInputDate = new DatePicker();
        this.deadlineInputDate.setPromptText("Deadline Date");
        this.deadlineInputTime = new TextField();
        this.deadlineInputTime.setPromptText("Deadline Time (HH:mm)");

        Button submitButton = new Button("Submit Assignment");
        submitButton.setOnAction((event) -> {
            this.handleAssignmentSubmission();
            int groupId=currentGroupId;
            String topic = topicInput.getText();
            String description = descriptionInput.getText();
            LocalDate deadlineDate = deadlineInputDate.getValue();
            String deadlinedate = deadlineDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Custom format

            //System.out.println("Formatted date: " + formattedDate);
            //String deadlinedate = deadlineInputDate.getValue().toString();
            String deadlinetime = deadlineInputTime.getText();
            // Validate input fields
            if (topic.isEmpty() || description.isEmpty() || deadlinedate.isEmpty() || deadlinetime.isEmpty()) {
                System.out.println("All fields must be filled.");
                return;
            }
            if (insertUserDetails(topic,description,deadlinedate,deadlinetime,groupId)) {
                System.out.println("Assignment Scheduled Successfully!");
            } else {
                System.out.println("Assignment not Scheduled");
            }
        });

        this.inputPanel.getChildren().addAll(new Label("Assignment Topic:"), this.topicInput, new Label("Description:"), this.descriptionInput, new Label("Deadline Date:"), this.deadlineInputDate, new Label("Deadline Time:"), this.deadlineInputTime, submitButton);

        Button scheduleButton = new Button("Schedule an Assignment");
        scheduleButton.setStyle("-fx-background-color: #00cc99; -fx-text-fill: white;");
        scheduleButton.setOnAction((event) -> {
            this.inputPanel.setVisible(true);
        });

        HBox bottomBox = new HBox(scheduleButton);
        bottomBox.setAlignment(Pos.BOTTOM_CENTER);
        bottomBox.setStyle("-fx-padding: 20;");

        layout.getChildren().addAll(title, this.assignmentList, bottomBox, this.inputPanel);

        Scene scene = new Scene(layout, 800.0, 600.0);
        stage.setScene(scene);
        stage.show();
    }

    private void handleAssignmentSubmission() {
        String topic = this.topicInput.getText();
        String description = this.descriptionInput.getText();
        LocalDateTime deadline;

        if (this.deadlineInputDate.getValue() == null || this.deadlineInputTime.getText().isEmpty()) {
            this.showError("Please enter both date and time.");
            return;
        }

        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalDate deadlineDate = this.deadlineInputDate.getValue();
            LocalTime deadlineTime = LocalTime.parse(this.deadlineInputTime.getText().trim(), timeFormatter);

            // Combine date and time
            deadline = LocalDateTime.of(deadlineDate, deadlineTime);

            if (deadline.isBefore(LocalDateTime.now())) {
                this.showError("Please enter a deadline in the future.");
                return;
            }
        } catch (Exception e) {
            this.showError("Invalid date/time format. Time should be in HH:mm format.");
            return;
        }

        Assignment newAssignment = new Assignment(topic, description, deadline);
        this.assignments.add(newAssignment);
        this.sortAssignmentsByDeadline();
        this.displayAssignments();
        this.clearInputFields();
        this.inputPanel.setVisible(false);
    }

    private void sortAssignmentsByDeadline() {
        Collections.sort(assignments, Comparator.comparing(Assignment::getDeadline));
    }

    private void displayAssignments() {
        this.assignmentList.getChildren().clear();

        for (Assignment assignment : assignments) {
            Label assignmentLabel = new Label(assignment.toString());
            assignmentLabel.setMinWidth(1100.0);
            assignmentLabel.setStyle("-fx-padding: 150; -fx-border-width: 1; -fx-border-color: black;");

            if (assignment.getDeadline().isBefore(LocalDateTime.now())) {
                assignmentLabel.setStyle("-fx-background-color: #ff570a; -fx-text-fill: white;");
            } else {
                assignmentLabel.setStyle("-fx-background-color: #00cc99; -fx-text-fill: black;");
            }
            this.assignmentList.getChildren().add(assignmentLabel);
        }
    }

    private void clearInputFields() {
        this.topicInput.clear();
        this.descriptionInput.clear();
        this.deadlineInputDate.setValue(null);
        this.deadlineInputTime.clear();
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    /**private boolean insertUserDetails(String topic, String description, String deadlinedate, String deadlinetime,int groupId) {
        LocalDate deadlineDate = LocalDate.parse(deadlineDateStr);
        Date sqlDeadlineDate = Date.valueOf(deadlineDate);

        // Parse the deadline time from the string input
        LocalTime deadlineTime = LocalTime.parse(deadlineTimeStr);
        Time sqlDeadlineTime = Time.valueOf(deadlineTime);

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "INSERT INTO assignments (topic, description, deadlinedate, deadlinetime,groupid) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, topic);
            pstmt.setString(2, description);
            pstmt.setString(3, deadlinedate);
            pstmt.setString(4, deadlinetime);
            pstmt.setInt(5,groupId);
            pstmt.executeUpdate(); // Execute the insert statement
            return true; // Insert successful
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Insert failed
    }
    private boolean insertUserDetails(String topic, String description, String deadlinedate, String deadlinetime, int groupId) {
        try {
            // Parse the deadline date from the string input
            LocalDate deadlineDate = LocalDate.parse(deadlinedate);  // Correct parameter name
            Date sqlDeadlineDate = Date.valueOf(deadlineDate);       // Convert LocalDate to sql.Date

            // Parse the deadline time from the string input
            LocalTime deadlineTime = LocalTime.parse(deadlinetime);  // Correct parameter name
            Time sqlDeadlineTime = Time.valueOf(deadlineTime);       // Convert LocalTime to sql.Time

            // Database connection and SQL query
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
                String sql = "INSERT INTO assignments (topic, description, deadlinedate, deadlinetime, groupid) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);

                // Set the parameters in the PreparedStatement
                pstmt.setString(1, topic);
                pstmt.setString(2, description);
                pstmt.setDate(3, sqlDeadlineDate);    // Set DATE value
                pstmt.setTime(4, sqlDeadlineTime);    // Set TIME value
                pstmt.setInt(5, groupId);             // Set groupId as an integer

                // Execute the insert statement
                pstmt.executeUpdate();
                return true;  // Insert successful
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;  // Insert failed
    }

    private void fetchAssignmentsFromDB(int groupId) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "SELECT topic, description, deadlinedate, deadlinetime FROM assignments WHERE groupid = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, groupId); // Set the group ID parameter

            ResultSet rs = pstmt.executeQuery();
            assignments.clear(); // Clear the current list of assignments

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            while (rs.next()) {
                String topic = rs.getString("topic");
                String description = rs.getString("description");
                LocalDate deadlineDate = rs.getDate("deadlinedate").toLocalDate();
                LocalTime deadlineTime = LocalTime.parse(rs.getString("deadlinetime"));
                LocalDateTime deadline = LocalDateTime.of(deadlineDate, deadlineTime);

                // Create a new assignment and add it to the list
                Assignment assignment = new Assignment(topic, description, deadline);
                assignments.add(assignment);
            }
            // Sort and display the assignments
            sortAssignmentsByDeadline();
            displayAssignments();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class Assignment {
        private String topic;
        private String description;
        private LocalDateTime deadline;

        public Assignment(String topic, String description, LocalDateTime deadline) {
            this.topic = topic;
            this.description = description;
            this.deadline = deadline;
        }

        public LocalDateTime getDeadline() {
            return deadline;
        }

        @Override
        public String toString() {
            return "Topic: " + topic + "\nDescription: " + description + "\nDeadline: " + deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }
    }
}**/
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Date;

public class AssignmentPage extends Application {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/studysphere"; // Change to your database name
    private static final String USER = "myuser"; // Change to your username
    private static final String PASSWORD = "12345";
    private VBox assignmentList;
    private List<Assignment> assignments = new ArrayList<>();
    private VBox inputPanel;
    private TextField topicInput;
    private TextArea descriptionInput;
    private DatePicker deadlineInputDate;
    private TextField deadlineInputTime;
    private int groupId;

    public AssignmentPage(int groupId) {
        this.groupId = groupId;
    }

    public AssignmentPage() {
    }

    public void start(Stage stage) {
        stage.setTitle("Assignments - StudySphere");
        VBox layout = new VBox(20.0);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-padding: 30;");

        Label title = new Label("Assignments Scheduled");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 24.0));
        title.setTextFill(Color.BLACK);

        this.assignmentList = new VBox(10.0);
        this.assignmentList.setAlignment(Pos.CENTER_LEFT);

        this.inputPanel = new VBox(10.0);
        this.inputPanel.setAlignment(Pos.CENTER);
        this.inputPanel.setStyle("-fx-padding: 20; -fx-border-color: #00cc99; -fx-border-width: 2; -fx-background-color: #f9f9f9;");
        this.inputPanel.setVisible(false);

        int currentGroupId = groupId;
        fetchAssignmentsFromDB(currentGroupId);

        this.topicInput = new TextField();
        this.topicInput.setPromptText("Assignment Topic");

        this.descriptionInput = new TextArea();
        this.descriptionInput.setPromptText("Assignment Description");

        this.deadlineInputDate = new DatePicker();
        this.deadlineInputDate.setPromptText("Deadline Date");

        this.deadlineInputTime = new TextField();
        this.deadlineInputTime.setPromptText("Deadline Time (HH:mm)");

        Button submitButton = new Button("Submit Assignment");
        submitButton.setOnAction((event) -> {
            handleAssignmentSubmission();
        });

        this.inputPanel.getChildren().addAll(new Label("Assignment Topic:"), this.topicInput, new Label("Description:"), this.descriptionInput, new Label("Deadline Date:"), this.deadlineInputDate, new Label("Deadline Time:"), this.deadlineInputTime, submitButton);

        Button scheduleButton = new Button("Schedule an Assignment");
        scheduleButton.setStyle("-fx-background-color: #00cc99; -fx-text-fill: white;");
        scheduleButton.setOnAction((event) -> {
            this.inputPanel.setVisible(true);
        });

        HBox bottomBox = new HBox(scheduleButton);
        bottomBox.setAlignment(Pos.BOTTOM_CENTER);
        bottomBox.setStyle("-fx-padding: 20;");

        layout.getChildren().addAll(title, this.assignmentList, bottomBox, this.inputPanel);

        Scene scene = new Scene(layout, 800.0, 600.0);
        stage.setScene(scene);
        stage.show();
    }

    private void handleAssignmentSubmission() {
        String topic = this.topicInput.getText();
        String description = this.descriptionInput.getText();

        if (this.deadlineInputDate.getValue() == null || this.deadlineInputTime.getText().isEmpty()) {
            showError("Please enter both date and time.");
            return;
        }

        LocalDate deadlineDate = this.deadlineInputDate.getValue();
        LocalTime deadlineTime;
        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            deadlineTime = LocalTime.parse(this.deadlineInputTime.getText().trim(), timeFormatter);

            // Combine date and time
            LocalDateTime deadline = LocalDateTime.of(deadlineDate, deadlineTime);

            if (deadline.isBefore(LocalDateTime.now())) {
                showError("Please enter a deadline in the future.");
                return;
            }

            Assignment newAssignment = new Assignment(topic, description, deadline);
            this.assignments.add(newAssignment);
            insertUserDetails(topic, description, deadlineDate.toString(), deadlineTime.toString(), groupId);
            sortAssignmentsByDeadline();
            displayAssignments();
            clearInputFields();
            this.inputPanel.setVisible(false);

        } catch (Exception e) {
            showError("Invalid date/time format. Time should be in HH:mm format.");
        }
    }

    private void sortAssignmentsByDeadline() {
        Collections.sort(assignments, Comparator.comparing(Assignment::getDeadline));
    }

    private void displayAssignments() {
        this.assignmentList.getChildren().clear();

        for (Assignment assignment : assignments) {
            Label assignmentLabel = new Label(assignment.toString());
            assignmentLabel.setMinWidth(1100.0);
            assignmentLabel.setStyle("-fx-padding: 150; -fx-border-width: 1; -fx-border-color: black;");

            if (assignment.getDeadline().isBefore(LocalDateTime.now())) {
                assignmentLabel.setStyle("-fx-background-color: #ff570a; -fx-text-fill: white;");
            } else {
                assignmentLabel.setStyle("-fx-background-color: #00cc99; -fx-text-fill: black;");
            }
            this.assignmentList.getChildren().add(assignmentLabel);
        }
    }

    private void clearInputFields() {
        this.topicInput.clear();
        this.descriptionInput.clear();
        this.deadlineInputDate.setValue(null);
        this.deadlineInputTime.clear();
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean insertUserDetails(String topic, String description, String deadlinedate, String deadlinetime, int groupId) {
        try {
            LocalDate deadlineDate = LocalDate.parse(deadlinedate);
            Date sqlDeadlineDate = Date.valueOf(deadlineDate);

            LocalTime deadlineTime = LocalTime.parse(deadlinetime);
            Time sqlDeadlineTime = Time.valueOf(deadlineTime);

            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
                String sql = "INSERT INTO assignments (topic, description, deadlinedate, deadlinetime, groupid) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, topic);
                pstmt.setString(2, description);
                pstmt.setDate(3, sqlDeadlineDate);
                pstmt.setTime(4, sqlDeadlineTime);
                pstmt.setInt(5, groupId);
                pstmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void fetchAssignmentsFromDB(int groupId) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "SELECT topic, description, deadlinedate, deadlinetime FROM assignments WHERE groupid = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, groupId);
            ResultSet rs = pstmt.executeQuery();
            assignments.clear();

            while (rs.next()) {
                String topic = rs.getString("topic");
                String description = rs.getString("description");
                LocalDate deadlineDate = rs.getDate("deadlinedate").toLocalDate();
                LocalTime deadlineTime = rs.getTime("deadlinetime").toLocalTime();
                LocalDateTime deadline = LocalDateTime.of(deadlineDate, deadlineTime);

                Assignment assignment = new Assignment(topic, description, deadline);
                assignments.add(assignment);
            }
            sortAssignmentsByDeadline();
            displayAssignments();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class Assignment {
        private String topic;
        private String description;
        private LocalDateTime deadline;

        public Assignment(String topic, String description, LocalDateTime deadline) {
            this.topic = topic;
            this.description = description;
            this.deadline = deadline;
        }

        public LocalDateTime getDeadline() {
            return deadline;
        }

        @Override
        public String toString() {
            return "Topic: " + topic + "\nDescription: " + description + "\nDeadline: " + deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }
    }
}
