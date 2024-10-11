import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class StudySphereLogin extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the root layout (HBox)
        HBox root = new HBox(250); // Horizontal layout with spacing
        root.setAlignment(Pos.CENTER); // Center align the content
        root.setPadding(new Insets(40, 20, 20, 20)); // Add padding to the layout
        root.setStyle("-fx-background-color: white;"); // Set background color to white

        // Create a VBox for the login components
        VBox loginComponents = new VBox(30);
        loginComponents.setAlignment(Pos.CENTER_LEFT); // Left align the components within the VBox
        loginComponents.setPadding(new Insets(40));

        // Create a label for the Login title
        Label title = new Label("Login");
        title.setFont(Font.font("Verdana", FontWeight.BOLD,35));
        title.setTextFill(Color.BLACK);

        // Label and TextField for Email
        Label emailLabel = new Label("Enter your Email Id");
        emailLabel.setFont(new Font("Arial Narrow", 16));
        emailLabel.setTextFill(Color.GREY); // Set label text color to light gray
        
        TextField usernameField = new TextField();
        usernameField.setPromptText("E.g. example@gmail.com");
        usernameField.setStyle("-fx-font-size: 16px; -fx-pref-width: 350px; -fx-pref-height: 50px; -fx-background-color: #ECF0F1; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        // Label and PasswordField for Password
        Label passwordLabel = new Label("Enter the Password");
        passwordLabel.setFont(new Font("Arial Narrow", 16));
        passwordLabel.setTextFill(Color.GREY); // Set label text color to light gray
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("E.g. studysphere123");
        passwordField.setStyle("-fx-font-size: 16px; -fx-pref-width: 350px; -fx-pref-height: 50px; -fx-background-color: #ECF0F1; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        // Create buttons for Login and Back
        Button loginButton = new Button("Log In");
        Button backButton = new Button("Don't have an account?");
                
        loginButton.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px; -fx-pref-width: 350px; -fx-pref-height: 50px;");
        loginButton.setMinWidth(350);  // Set minimum width
        loginButton.setMinHeight(50);   // Set minimum height

        backButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #3498DB; -fx-font-size: 16px;");

        backButton.setMinWidth(350); // Optional: Set minimum width for back button if needed

        // Button hover effects
        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: #219653; -fx-font-size: 16px; -fx-text-fill: white; -fx-min-width: 350px; -fx-min-height: 50px;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color: #27AE60; -fx-font-size: 16px; -fx-text-fill: white; -fx-min-width: 350px; -fx-min-height: 50px;"));

        // Add action for login button
        loginButton.setOnAction(e -> {
            // Add login functionality here
            System.out.println("Login button clicked");
        });

        // Add action for back button (Navigate to Sign Up page)
        backButton.setOnAction(e -> {
            StudySphereSignUp signUpPage = new StudySphereSignUp();
            signUpPage.start(new Stage()); // Open the Sign Up page in a new stage
        });

        // Add all login components to the VBox
        loginComponents.getChildren().addAll(title, emailLabel, usernameField, passwordLabel, passwordField, loginButton, backButton);

        // Create and add the login image
        ImageView loginImage = new ImageView(new Image("resources/Login.png")); // Path to the login image
        loginImage.setFitWidth(350); // Set the image width
        loginImage.setPreserveRatio(true);
        loginImage.setSmooth(true); // Enable smooth property for better quality

        // Add login components and image to the root layout
        root.getChildren().addAll(loginComponents, loginImage);

        // Create a scene and add it to the stage
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("StudySphere - Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
