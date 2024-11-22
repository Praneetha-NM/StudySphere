import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;

public class StudySphereInviteMembersDynamic extends Application {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/studysphere";
    private static final String USER = "myuser";
    private static final String PASSWORD = "12345";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Invite Members - StudySphere");

        // Create a VBox layout for the form
        VBox formLayout = new VBox(20);
        formLayout.setPadding(new Insets(40));
        formLayout.setAlignment(Pos.CENTER_LEFT); // Align left to match other pages

        // Create a label for the page title
        Label titleLabel = new Label("Invite Members to Group");
        titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        titleLabel.setTextFill(Color.BLACK);

        // Create a button to add new email fields
        Button addButton = new Button("+");
        addButton.setStyle("-fx-background-color: #00cc99; -fx-text-fill: white; -fx-font-size: 14px; " +
                "-fx-pref-width: 30px; -fx-pref-height: 30px; -fx-border-radius: 15px; -fx-background-radius: 15px;"); // Round cancel button
        addButton.setFont(Font.font(24));
        // Create an HBox to hold the title and the add button
        HBox titleBox = new HBox(10);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        titleBox.getChildren().addAll(titleLabel, addButton);

        // Create a VBox to hold all email fields
        VBox emailFieldsContainer = new VBox(10); // Spacing between email fields
        emailFieldsContainer.setAlignment(Pos.CENTER_LEFT);

        // Create the first email field and add it to the container
        HBox firstEmailBox = createEmailField(emailFieldsContainer, false);
        emailFieldsContainer.getChildren().add(firstEmailBox);

        // Create the "Invite" button
        Button inviteButton = new Button("Invite Members");
        inviteButton.setStyle("-fx-background-color: #00cc99; -fx-text-fill: white; -fx-font-size: 16px; " +
                "-fx-padding: 10px; -fx-pref-width: 350px; -fx-pref-height: 50px;");
        inviteButton.setMinWidth(450);  // Set minimum width
        inviteButton.setMinHeight(50);   // Set minimum height

        // Button hover effects
        inviteButton.setOnMouseEntered(e -> inviteButton.setStyle("-fx-background-color: #219653; -fx-font-size: 16px; -fx-text-fill: white; -fx-min-width: 450px; -fx-min-height: 50px;"));
        inviteButton.setOnMouseExited(e -> inviteButton.setStyle("-fx-background-color: #00cc99; -fx-font-size: 16px; -fx-text-fill: white; -fx-min-width: 450px; -fx-min-height: 50px;"));

        // Add functionality to the "Invite" button
        inviteButton.setOnAction(e -> {
            List<String> emails = new ArrayList<>();
            boolean hasEmptyField = false;

            for (Node emailBox : emailFieldsContainer.getChildren()) {
                TextField field = (TextField) ((HBox) emailBox).getChildren().get(0); // Get the TextField
                String email = field.getText().trim();
                if (email.isEmpty()) {
                    hasEmptyField = true;
                    break;
                } else {
                    emails.add(email);  // Collect all non-empty emails
                }
            }

            if (hasEmptyField) {
                // Show an alert if any email field is empty
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Input Error");
                alert.setHeaderText("Missing Email");
                alert.setContentText("Please enter an email address in all fields.");
                alert.showAndWait();
            } else {
                // Check each email in the database
                for (String email : emails) {
                    if (checkIfUserExists(email)) {
                        // Show success message if user exists
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Invitation Sent");
                        successAlert.setHeaderText("Success!");
                        successAlert.setContentText("The user with email '" + email + "' has been invited.");
                        successAlert.showAndWait();
                    } else {
                        // Show error message if user does not exist
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("User Not Found");
                        errorAlert.setHeaderText("Error!");
                        errorAlert.setContentText("No user found with the email '" + email + "'.");
                        errorAlert.showAndWait();
                    }
                }
            }
        });

        // Add action to the "Add" button
        addButton.setOnAction(e -> {
            if (emailFieldsContainer.getChildren().size() < 10) { // Check if less than 10 fields exist
                HBox newEmailBox = createEmailField(emailFieldsContainer, true);
                emailFieldsContainer.getChildren().add(newEmailBox); // Add to the VBox
            } else {
                addButton.setDisable(true); // Disable the button when the limit is reached
            }
        });

        // Add the title box, email fields container, and the invite button to the form layout
        formLayout.getChildren().addAll(titleBox, emailFieldsContainer, inviteButton);

        // Create an image view for the image to be placed beside the form
        ImageView inviteImage = new ImageView(new Image("resources/InviteMembers.png")); // Path to the image
        inviteImage.setFitWidth(350); // Set the image width
        inviteImage.setPreserveRatio(true);
        inviteImage.setSmooth(true); // Enable smooth property for better quality

        // Create an HBox to hold both the form and the image side by side
        HBox rootLayout = new HBox(100); // Set spacing between form and image
        rootLayout.setAlignment(Pos.CENTER); // Center align everything
        rootLayout.setPadding(new Insets(40, 20, 20, 20)); // Add padding around the layout

        // Set background color to white (#FFFFFF)
        rootLayout.setStyle("-fx-background-color: #FFFFFF;");

        // Add the form and the image to the HBox
        rootLayout.getChildren().addAll(formLayout, inviteImage);

        // Create the scene and show the stage
        Scene scene = new Scene(rootLayout, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to check if the user exists in the database
    private boolean checkIfUserExists(String email) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returns true if a record is found
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Return false if any error occurs or user does not exist
    }

    // Method to create a new email input field dynamically
    private HBox createEmailField(VBox emailFieldsContainer, boolean hasCancelButton) {
        TextField emailField = new TextField();
        emailField.setPromptText("E.g. example@gmail.com");
        emailField.setStyle("-fx-font-size: 16px; -fx-pref-width: 450px; -fx-pref-height: 50px; " +
                "-fx-background-color: #ECF0F1; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        HBox emailBox = new HBox(10);  // Create a box to hold the text field and buttons
        emailBox.setAlignment(Pos.CENTER_LEFT);
        emailBox.getChildren().add(emailField); // Add email field to the HBox

        // Add a cancel button if it is not the first field
        if (hasCancelButton) {
            Button cancelButton = new Button("X");
            cancelButton.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white; -fx-font-size: 14px; " +
                    "-fx-pref-width: 30px; -fx-pref-height: 30px; -fx-border-radius: 15px; -fx-background-radius: 15px;"); // Round cancel button

            // Action for the cancel button to remove the email field
            cancelButton.setOnAction(e -> emailFieldsContainer.getChildren().remove(emailBox));

            emailBox.getChildren().add(cancelButton); // Add cancel button to the HBox
        }

        return emailBox; // Return the HBox containing the email field and optional cancel button
    }

    public static void main(String[] args) {
        launch(args);
    }
}
