/**import javafx.application.Application;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudySphereSignUp extends Application {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/studysphere"; // Change to your database name
    private static final String USER = "myuser"; // Change to your username
    private static final String PASSWORD = "12345";

    @Override
    public void start(Stage primaryStage) {
        // Create the root layout (HBox)
        HBox root = new HBox(250); // Horizontal layout with spacing
        root.setAlignment(Pos.CENTER); // Center align the content
        root.setPadding(new Insets(40, 20, 20, 20)); // Add padding to the layout
        root.setStyle("-fx-background-color: white;"); // Set background color to white

        // Create a VBox for the signup components
        VBox signUpComponents = new VBox(30);
        signUpComponents.setAlignment(Pos.CENTER_LEFT); // Left align the components within the VBox
        signUpComponents.setPadding(new Insets(40));

        // Create a label for the Sign Up title
        Label title = new Label("Sign Up");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 35));
        title.setTextFill(Color.BLACK);

        // Label and TextField for Email
        Label emailLabel = new Label("Enter your Email Id");
        emailLabel.setFont(new Font("Arial Narrow", 16));
        emailLabel.setTextFill(Color.GREY); // Set label text color to light gray

        TextField emailField = new TextField();
        emailField.setPromptText("E.g. example@gmail.com");
        emailField.setStyle("-fx-font-size: 16px; -fx-pref-width: 350px; -fx-pref-height: 50px; -fx-background-color: #ECF0F1; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        // Label and PasswordField for Password
        Label passwordLabel = new Label("Enter the Password");
        passwordLabel.setFont(new Font("Arial Narrow", 16));
        passwordLabel.setTextFill(Color.GREY); // Set label text color to light gray

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("E.g. studysphere123");
        passwordField.setStyle("-fx-font-size: 16px; -fx-pref-width: 350px; -fx-pref-height: 50px; -fx-background-color: #ECF0F1; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        // Label and TextField for Full Name
        Label nameLabel = new Label("Enter Your Full Name");
        nameLabel.setFont(new Font("Arial Narrow", 16));
        nameLabel.setTextFill(Color.GREY);

        TextField nameField = new TextField();
        nameField.setPromptText("E.g. John Gilbert");
        nameField.setStyle("-fx-font-size: 16px; -fx-pref-width: 350px; -fx-pref-height: 50px; -fx-background-color: #ECF0F1; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        // Label and TextField for University Name
        Label universityLabel = new Label("Enter Your University Name");
        universityLabel.setFont(new Font("Arial Narrow", 16));
        universityLabel.setTextFill(Color.GREY);

        TextField universityField = new TextField();
        universityField.setPromptText("E.g. Harvard University");
        universityField.setStyle("-fx-font-size: 16px; -fx-pref-width: 350px; -fx-pref-height: 50px; -fx-background-color: #ECF0F1; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        // Create buttons for Sign Up and Back
        Button signUpButton = new Button("Sign Up");
        Button backButton = new Button("Already have an account?");

        signUpButton.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px; -fx-pref-width: 350px; -fx-pref-height: 50px;");
        backButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #3498DB; -fx-font-size: 16px;");

        // Button hover effects
        signUpButton.setOnMouseEntered(e -> signUpButton.setStyle("-fx-background-color: #219653; -fx-text-fill: white;"));
        signUpButton.setOnMouseExited(e -> signUpButton.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white;"));

        // Add action for sign-up button
        signUpButton.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();
            String fullName = nameField.getText();
            String university = universityField.getText();

            // Validate input fields
            if (email.isEmpty() || password.isEmpty() || fullName.isEmpty() || university.isEmpty()) {
                System.out.println("All fields must be filled.");
                return;
            }

            // Insert user details into the database
            if (insertUserDetails(email, password, fullName, university)) {
                System.out.println("Sign-up successful!");

                // Navigate to the StudySphereApp page
                StudySphereApp app = new StudySphereApp(); // Pass necessary parameters if needed
                try {
                    app.start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                primaryStage.close(); // Close the current Sign-up stage
            } else {
                System.out.println("Sign-up failed. Please try again.");
            }
        });

        // Add action for back button
        backButton.setOnAction(e -> {
            StudySphereLogin loginPage = new StudySphereLogin();
            loginPage.start(new Stage());
            primaryStage.close(); // Close the current Sign-up stage
        });

        // Add all sign-up components to the VBox
        signUpComponents.getChildren().addAll(title, emailLabel, emailField, passwordLabel, passwordField, nameLabel, nameField, universityLabel, universityField, signUpButton, backButton);

        // Create and add the signup image (optional)
        ImageView signUpImage = new ImageView(new Image("resources/Login.png")); // Path to the sign-up image
        signUpImage.setFitWidth(350); // Set the image width
        signUpImage.setPreserveRatio(true);
        signUpImage.setSmooth(true); // Enable smooth property for better quality

        // Add sign-up components and image to the root layout
        root.getChildren().addAll(signUpComponents, signUpImage);

        // Create a scene and add it to the stage
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("StudySphere - Sign Up");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean insertUserDetails(String email, String password, String fullName, String university) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "INSERT INTO users (email, password, full_name, university_name) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, fullName);
            pstmt.setString(4, university);
            pstmt.executeUpdate(); // Execute the insert statement
            return true; // Insert successful
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Insert failed
    }

    public static void main(String[] args) {
        launch(args);
    }
}**/
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudySphereSignUp extends Application {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/studysphere"; // Change to your database name
    private static final String USER = "myuser"; // Change to your username
    private static final String PASSWORD = "12345";

    @Override
    public void start(Stage primaryStage) {
        // Create the root layout (HBox)
        HBox root = new HBox(250); // Horizontal layout with spacing
        root.setAlignment(Pos.CENTER); // Center align the content
        root.setPadding(new Insets(40, 20, 20, 20)); // Add padding to the layout
        root.setStyle("-fx-background-color: white;"); // Set background color to white

        // Create a VBox for the signup components
        VBox signUpComponents = new VBox(30);
        signUpComponents.setAlignment(Pos.CENTER_LEFT); // Left align the components within the VBox
        signUpComponents.setPadding(new Insets(40));

        // Create a label for the Sign Up title
        Label title = new Label("Sign Up");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 35));
        title.setTextFill(Color.BLACK);

        // Label and TextField for Email
        Label emailLabel = new Label("Enter your Email Id");
        emailLabel.setFont(new Font("Arial Narrow", 16));
        emailLabel.setTextFill(Color.GREY); // Set label text color to light gray

        TextField emailField = new TextField();
        emailField.setPromptText("E.g. example@gmail.com");
        emailField.setStyle("-fx-font-size: 16px; -fx-pref-width: 350px; -fx-pref-height: 50px; -fx-background-color: #ECF0F1; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        // Label and PasswordField for Password
        Label passwordLabel = new Label("Enter the Password");
        passwordLabel.setFont(new Font("Arial Narrow", 16));
        passwordLabel.setTextFill(Color.GREY); // Set label text color to light gray

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("E.g. studysphere123");
        passwordField.setStyle("-fx-font-size: 16px; -fx-pref-width: 350px; -fx-pref-height: 50px; -fx-background-color: #ECF0F1; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        // Label and TextField for Full Name
        Label nameLabel = new Label("Enter Your Full Name");
        nameLabel.setFont(new Font("Arial Narrow", 16));
        nameLabel.setTextFill(Color.GREY);

        TextField nameField = new TextField();
        nameField.setPromptText("E.g. John Gilbert");
        nameField.setStyle("-fx-font-size: 16px; -fx-pref-width: 350px; -fx-pref-height: 50px; -fx-background-color: #ECF0F1; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        // Label and TextField for University Name
        Label universityLabel = new Label("Enter Your University Name");
        universityLabel.setFont(new Font("Arial Narrow", 16));
        universityLabel.setTextFill(Color.GREY);

        TextField universityField = new TextField();
        universityField.setPromptText("E.g. Harvard University");
        universityField.setStyle("-fx-font-size: 16px; -fx-pref-width: 350px; -fx-pref-height: 50px; -fx-background-color: #ECF0F1; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        // Create buttons for Sign Up and Back
        Button signUpButton = new Button("Sign Up");
        Button backButton = new Button("Already have an account?");

        signUpButton.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px; -fx-pref-width: 350px; -fx-pref-height: 50px;");
        backButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #3498DB; -fx-font-size: 16px;");

        // Button hover effects
        signUpButton.setOnMouseEntered(e -> signUpButton.setStyle("-fx-background-color: #219653; -fx-text-fill: white;"));
        signUpButton.setOnMouseExited(e -> signUpButton.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white;"));

        // Add action for sign-up button
        signUpButton.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();
            String fullName = nameField.getText();
            String university = universityField.getText();

            // Validate input fields
            if (email.isEmpty() || password.isEmpty() || fullName.isEmpty() || university.isEmpty()) {
                System.out.println("All fields must be filled.");
                return;
            }

            // Insert user details into the database and retrieve user ID
            int userId = insertUserDetails(email, password, fullName, university);
            if (userId != -1) {
                System.out.println("Sign-up successful! User ID: " + userId);

                // Navigate to the StudySphereApp page with the user ID
                StudySphereApp app = new StudySphereApp(userId); // Pass the user ID
                try {
                    app.start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                primaryStage.close(); // Close the current Sign-up stage
            } else {
                System.out.println("Sign-up failed. Please try again.");
            }
        });

        // Add action for back button
        backButton.setOnAction(e -> {
            StudySphereLogin loginPage = new StudySphereLogin();
            loginPage.start(new Stage());
            primaryStage.close(); // Close the current Sign-up stage
        });

        // Add all sign-up components to the VBox
        signUpComponents.getChildren().addAll(title, emailLabel, emailField, passwordLabel, passwordField, nameLabel, nameField, universityLabel, universityField, signUpButton, backButton);

        // Create and add the signup image (optional)
        ImageView signUpImage = new ImageView(new Image("resources/Login.png")); // Path to the sign-up image
        signUpImage.setFitWidth(350); // Set the image width
        signUpImage.setPreserveRatio(true);
        signUpImage.setSmooth(true); // Enable smooth property for better quality

        // Add sign-up components and image to the root layout
        root.getChildren().addAll(signUpComponents, signUpImage);

        // Create a scene and add it to the stage
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("StudySphere - Sign Up");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private int insertUserDetails(String email, String password, String fullName, String university) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "INSERT INTO users (email, password, full_name, university_name) VALUES (?, ?, ?, ?) RETURNING id"; // Assuming user_id is the primary key
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, fullName);
            pstmt.setString(4, university);
            ResultSet rs = pstmt.executeQuery(); // Execute the insert statement

            if (rs.next()) {
                return rs.getInt("id"); // Retrieve the generated user ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Insert failed or user ID could not be retrieved
    }

    public static void main(String[] args) {
        launch(args);
    }
}

