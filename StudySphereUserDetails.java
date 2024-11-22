import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class StudySphereUserDetails extends Application {
    // Database connection constants
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/studysphere"; // Change to your database name
    private static final String USER = "myuser"; // Change to your username
    private static final String PASSWORD = "12345"; // Change to your password

    @Override
    public void start(Stage primaryStage) {
        // Create the root layout (HBox)
        HBox root = new HBox(250); // Horizontal layout with spacing
        root.setAlignment(Pos.CENTER); // Center align the content
        root.setPadding(new Insets(40, 20, 20, 20)); // Add padding to the layout
        root.setStyle("-fx-background-color: white;"); // Set background color to white

        // Create a VBox for the input components
        VBox inputComponents = new VBox(30);
        inputComponents.setAlignment(Pos.CENTER_LEFT); // Align components to the left within the VBox
        inputComponents.setPadding(new Insets(40));

        // Create a label for the title
        Label title = new Label("User Details");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 35));
        title.setTextFill(Color.BLACK);

        // Label and TextField for Full Name
        Label nameLabel = new Label("Enter Your Full Name");
        nameLabel.setFont(new Font("Arial Narrow", 16));
        nameLabel.setTextFill(Color.GREY); // Set label text color to light gray
        nameLabel.setAlignment(Pos.CENTER_LEFT);

        TextField nameField = new TextField();
        nameField.setPromptText("E.g. John Gilbert");
        nameField.setStyle("-fx-font-size: 16px; -fx-pref-width: 350px; -fx-pref-height: 50px; -fx-background-color: #ECF0F1; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        // Label and TextField for Institution
        Label institutionLabel = new Label("Enter Your Institution/University Name");
        institutionLabel.setFont(new Font("Arial Narrow", 16));
        institutionLabel.setTextFill(Color.GREY); // Set label text color to light gray
        institutionLabel.setAlignment(Pos.CENTER_LEFT);

        TextField institutionField = new TextField();
        institutionField.setPromptText("E.g. Harvard University");
        institutionField.setStyle("-fx-font-size: 16px; -fx-pref-width: 350px; -fx-pref-height: 50px; -fx-background-color: #ECF0F1; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        // Create a submit button
        Button submitButton = new Button("Submit");
        submitButton.setMinWidth(350);  // Set minimum width
        submitButton.setMinHeight(50);
        submitButton.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px; -fx-pref-width: 350px; -fx-border-radius: 5px; -fx-pref-height: 50px;");

        // Button hover effects
        submitButton.setOnMouseEntered(e -> submitButton.setStyle("-fx-background-color: #219653; -fx-font-size: 16px;-fx-text-fill: white;"));
        submitButton.setOnMouseExited(e -> submitButton.setStyle("-fx-background-color: #27AE60;-fx-font-size: 16px; -fx-text-fill: white;"));

        // Add action for the submit button
        submitButton.setOnAction(e -> {
            String name = nameField.getText();
            String institution = institutionField.getText();
            if (insertUserDetails(name, institution)) {
                System.out.println("User details submitted: Name: " + name + ", Institution: " + institution);
            } else {
                System.out.println("Failed to submit user details.");
            }
        });

        // Add title, labels, and text fields to the VBox
        inputComponents.getChildren().addAll(title, nameLabel, nameField, institutionLabel, institutionField, submitButton);

        // Create and add the image
        ImageView profileImage = new ImageView(new Image("resources/StudySphereUserDetails.png")); // Path to the image
        profileImage.setFitWidth(450); // Set the image width
        profileImage.setPreserveRatio(true);
        profileImage.setSmooth(true); // Enable smooth property for better quality

        // Add input components and image to the root layout
        root.getChildren().addAll(inputComponents, profileImage);

        // Create a scene and add it to the stage
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("StudySphere - User Details");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to insert user details into the database
    private boolean insertUserDetails(String name, String institution) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "INSERT INTO users (full_name, university_name) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, institution);
            pstmt.executeUpdate(); // Execute the insert statement
            return true; // Insert successful
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Insert failed
    }

    public static void main(String[] args) {
        launch(args);
    }
}
