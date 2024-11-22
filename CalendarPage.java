import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.Node;

public class CalendarPage extends Application {
    private GridPane calendarGrid;
    private LocalDate selectedDate;
    private VBox inputBox; // VBox to hold the input box
    private Label selectedDateLabel; // Label to display selected date message

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Calendar - StudySphere");

        VBox mainLayout = new VBox();
        mainLayout.setAlignment(Pos.CENTER); // Center align the main layout
        Region spacer = new Region();
        spacer.setMaxHeight(40);
        Label titleLabel = new Label("Calendar View");
        titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 30)); // Set font size to 30
        titleLabel.setTextFill(Color.BLACK);

        calendarGrid = new GridPane();
        createCalendar(LocalDate.now());

        // Create the input box for task
        inputBox = new VBox(10);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setStyle("-fx-padding: 20; -fx-border-color: #00cc99; -fx-border-width: 2; -fx-background-color: #f9f9f9;");

        Label taskLabel = new Label("Enter the task:");
        TextField taskInput = new TextField();
        Button setTaskButton = new Button("Set Task");

        // Initialize the selected date label
        selectedDateLabel = new Label("Selected Date: None"); // Initial message
        selectedDateLabel.setTextFill(Color.DARKGRAY); // Set color for the label

        // Action for the set task button
        setTaskButton.setOnAction(e -> {
            // Show the input box when the button is clicked
            taskLabel.setVisible(true);
            taskInput.setVisible(true);
            taskInput.requestFocus(); // Focus on the text field
        });

        // Action for pressing Enter in the text field
        taskInput.setOnAction(e -> {
            if (selectedDate != null) {
                String task = taskInput.getText();
                int dayOfMonth = selectedDate.getDayOfMonth();

                // Calculate the row and column for the selected date
                LocalDate firstDayOfMonth = selectedDate.withDayOfMonth(1);
                int startDay = firstDayOfMonth.getDayOfWeek().getValue(); // Monday=1, Sunday=7
                startDay = (startDay == 7) ? 0 : startDay; // Adjust for Sunday (0 index)

                int row = (dayOfMonth + startDay - 1) / 7; // Calculate row index
                int col = (dayOfMonth + startDay - 1) % 7; // Calculate column index

                // Find the button corresponding to the selected date
                for (Node node : calendarGrid.getChildren()) {
                    if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                        ((Button) node).setText(((Button) node).getText() + "\n" + task);
                        ((Button) node).setStyle("-fx-background-color: #00cc99; -fx-text-fill: black;"); // Change color when task is set
                    }
                }
                taskInput.clear(); // Clear the input field after adding the task
                selectedDateLabel.setText("Selected Date: " + selectedDate.format(DateTimeFormatter.ISO_DATE));
                selectedDateLabel.setStyle("-fx-text-fill:black;");// Update selected date label
            } else {
                // Show an alert if no date is selected
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Date Selected");
                alert.setHeaderText(null);
                alert.setContentText("Please select a date first.");
                alert.showAndWait();
            }
            // Hide the input box after pressing Enter
            taskLabel.setVisible(false);
            taskInput.setVisible(false);
        });

        // Initially hide the task input elements
        taskLabel.setVisible(false);
        taskInput.setVisible(false);

        // Create a HBox for the button
        HBox buttonContainer = new HBox(setTaskButton);
        buttonContainer.setAlignment(Pos.CENTER); // Center the button

        // Wrap the calendarGrid in a VBox to allow setting height
        VBox calendarContainer = new VBox(calendarGrid);
        calendarContainer.setMinHeight(450); // Increase the height of the calendar
        calendarContainer.setAlignment(Pos.CENTER);
        calendarContainer.setStyle("-fx-padding: 100;"); // Center the calendar grid

        mainLayout.getChildren().addAll(spacer,titleLabel, calendarContainer, buttonContainer, inputBox, selectedDateLabel); // Add the selected date label
        inputBox.getChildren().addAll(taskLabel, taskInput);
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createCalendar(LocalDate date) {
        calendarGrid.getChildren().clear();
        LocalDate firstDayOfMonth = date.withDayOfMonth(1);
        int daysInMonth = date.lengthOfMonth();
        int startDay = firstDayOfMonth.getDayOfWeek().getValue(); // Monday=1, Sunday=7
        startDay = (startDay == 7) ? 0 : startDay; // Adjust for Sunday (0 index)

        // Fill the calendar with date buttons
        for (int i = 0; i < daysInMonth; i++) {
            LocalDate currentDay = firstDayOfMonth.plusDays(i);
            String formattedDate = currentDay.format(DateTimeFormatter.ofPattern("dd/MM/yy")); // Format date

            Button dayButton = new Button(formattedDate);
            dayButton.setMinSize(160, 100); // Set size for better visibility

            dayButton.setOnAction(e -> {
                selectedDate = currentDay;
                // Update the selected date label when a button is clicked
                selectedDateLabel.setText("Selected Date: " + selectedDate.format(DateTimeFormatter.ISO_DATE)); // Update label
            });

            calendarGrid.add(dayButton, (i + startDay) % 7, (i + startDay) / 7);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
