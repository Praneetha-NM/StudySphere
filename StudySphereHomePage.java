/**import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class StudySphereHomePage extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Main VBox layout
        VBox mainLayout = new VBox(30);  // Increased spacing between elements
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_CENTER);

        // Navbar with logo and buttons
        HBox navBar = new HBox(20);
        navBar.setPadding(new Insets(10));
        navBar.setAlignment(Pos.CENTER);
        navBar.setStyle("-fx-background-color: #ff570a;"); // Set the background color of the navbar

        // Logo
        Image logo = new Image(new FileInputStream("resources/StudySphereLogo.png"));
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);

        // Login and Sign Up buttons
        Button loginButton = new Button("Login");
        Button signUpButton = new Button("Sign Up");

        loginButton.setStyle(
                "-fx-background-color: #ffffff; -fx-text-fill: black; -fx-font-size: 16px;" +
                        "-fx-padding: 10 20 10 20; -fx-background-radius: 30;"
        );
        signUpButton.setStyle(
                "-fx-background-color: #00cc99; -fx-text-fill: white; -fx-font-size: 16px;" +
                        "-fx-padding: 10 20 10 20; -fx-background-radius: 30;"
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        navBar.getChildren().addAll(logoView, spacer, loginButton, signUpButton);

        // Center content VBox for title and description
        VBox centerContent = new VBox(30);
        centerContent.setAlignment(Pos.CENTER);
        centerContent.setPadding(new Insets(10, 20, 10, 20));
        centerContent.setMaxWidth(800);  // Center content width limit

        // Title
        Label titleLabel = new Label("StudySphere");
        titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 45));
        titleLabel.setTextFill(Color.BLACK);

        // Description using TextFlow for center justification
        TextFlow descriptionFlow = new TextFlow();
        descriptionFlow.setTextAlignment(TextAlignment.CENTER); // Center text alignment
        descriptionFlow.setMaxWidth(600);  // Optional: limit width for wrapping

        // Add text to the TextFlow
        Text descriptionText = new Text(
                "StudySphere is a group study platform where students collaborate, " +
                        "share resources, and join study groups. \n\n" +
                        "Create or join a study group today!"
        );
        descriptionText.setFont(new Font("Comic Sans MS", 18));
        descriptionText.setFill(Color.BLACK);

        descriptionFlow.getChildren().add(descriptionText);

        centerContent.getChildren().addAll(titleLabel, descriptionFlow);

        // Gradient overlay
        Stop[] stops = new Stop[] {
                new Stop(0, Color.web("#f7F1EC")),
                new Stop(1, Color.web("#f7F1EC44"))
        };
        LinearGradient gradientOverlay = new LinearGradient(
                0, 0, 0, 1, true, null, stops
        );

        // Background image
        Image backgroundImage = new Image(new FileInputStream("resources/Homebgimage.gif"));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(1000);
        backgroundImageView.setFitHeight(500);  // Reduced height further
        backgroundImageView.setPreserveRatio(false);

        // StackPane for background
        StackPane backgroundPane = new StackPane();
        backgroundPane.getChildren().addAll(backgroundImageView);
        backgroundPane.setBackground(new Background(new BackgroundFill(gradientOverlay, CornerRadii.EMPTY, Insets.EMPTY)));
        StackPane.setAlignment(backgroundImageView, Pos.BOTTOM_CENTER);

        // Add to main layout
        mainLayout.getChildren().addAll(navBar, centerContent, backgroundPane);

        // Button actions for navigation
        loginButton.setOnAction(e -> {
            StudySphereLogin loginPage = new StudySphereLogin();
            loginPage.start(new Stage());
        });

        signUpButton.setOnAction(e -> {
            StudySphereSignUp signUpPage = new StudySphereSignUp();
            signUpPage.start(new Stage());// Close the home window
        });

        // Create scene
        Scene scene = new Scene(mainLayout, 1000, 1000);  // Increased scene height
        primaryStage.setTitle("StudySphere - Home");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}**/
/**import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class StudySphereHomePage extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Main VBox layout
        VBox mainLayout = new VBox(30);  // Increased spacing between elements
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_CENTER);

        // Navbar with logo and buttons
        HBox navBar = new HBox(20);
        navBar.setPadding(new Insets(10));
        navBar.setAlignment(Pos.CENTER);
        navBar.setStyle("-fx-background-color: #ff570a;"); // Set the background color of the navbar

        // Logo
        Image logo = new Image(new FileInputStream("resources/StudySphereLogo.png"));
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);

        // Login and Sign Up buttons
        Button loginButton = new Button("Login");
        Button signUpButton = new Button("Sign Up");

        loginButton.setStyle(
                "-fx-background-color: #ffffff; -fx-text-fill: black; -fx-font-size: 16px;" +
                        "-fx-padding: 10 20 10 20; -fx-background-radius: 30;"
        );
        signUpButton.setStyle(
                "-fx-background-color: #00cc99; -fx-text-fill: white; -fx-font-size: 16px;" +
                        "-fx-padding: 10 20 10 20; -fx-background-radius: 30;"
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        navBar.getChildren().addAll(logoView, spacer, loginButton, signUpButton);

        // Center content VBox for title and description
        VBox centerContent = new VBox(30);
        centerContent.setAlignment(Pos.CENTER);
        centerContent.setPadding(new Insets(10, 20, 10, 20));
        centerContent.setMaxWidth(800);  // Center content width limit

        // Title
        Label titleLabel = new Label("StudySphere");
        titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 45));
        titleLabel.setTextFill(Color.BLACK);

        // Description using TextFlow for center justification
        TextFlow descriptionFlow = new TextFlow();
        descriptionFlow.setTextAlignment(TextAlignment.CENTER); // Center text alignment
        descriptionFlow.setMaxWidth(600);  // Optional: limit width for wrapping

        // Add text to the TextFlow
        Text descriptionText = new Text(
                "StudySphere is a group study platform where students collaborate, " +
                        "share resources, and join study groups. \n\n" +
                        "Create or join a study group today!"
        );
        descriptionText.setFont(new Font("Comic Sans MS", 18));
        descriptionText.setFill(Color.BLACK);

        descriptionFlow.getChildren().add(descriptionText);

        centerContent.getChildren().addAll(titleLabel, descriptionFlow);

        // Gradient overlay
        Stop[] stops = new Stop[] {
                new Stop(0, Color.web("#f7F1EC")),
                new Stop(1, Color.web("#f7F1EC44"))
        };
        LinearGradient gradientOverlay = new LinearGradient(
                0, 0, 0, 1, true, null, stops
        );

        // Background image
        Image backgroundImage = new Image(new FileInputStream("resources/Homebgimage.gif"));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(1000);
        backgroundImageView.setFitHeight(500);  // Reduced height further
        backgroundImageView.setPreserveRatio(false);

        // StackPane for background
        StackPane backgroundPane = new StackPane();
        backgroundPane.getChildren().addAll(backgroundImageView);
        backgroundPane.setBackground(new Background(new BackgroundFill(gradientOverlay, CornerRadii.EMPTY, Insets.EMPTY)));
        StackPane.setAlignment(backgroundImageView, Pos.BOTTOM_CENTER);

        // Add to main layout
        mainLayout.getChildren().addAll(navBar, centerContent, backgroundPane);

        // Button actions for navigation
        loginButton.setOnAction(e -> {
            StudySphereLogin loginPage = new StudySphereLogin();
            loginPage.start(new Stage());
        });

        signUpButton.setOnAction(e -> {
            StudySphereSignUp signUpPage = new StudySphereSignUp();
            signUpPage.start(new Stage());// Close the home window
        });

        // Create scene
        Scene scene = new Scene(mainLayout, 1000, 1000);  // Increased scene height
        primaryStage.setTitle("StudySphere - Home");
        primaryStage.setScene(scene);
        primaryStage.show();
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class StudySphereHomePage extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Main VBox layout
        VBox mainLayout = new VBox(30);  // Increased spacing between elements
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_CENTER);

        // Navbar with logo and buttons
        HBox navBar = new HBox(20);
        navBar.setPadding(new Insets(10));
        navBar.setAlignment(Pos.CENTER);
        navBar.setStyle("-fx-background-color: #ff570a;"); // Set the background color of the navbar

        // Logo
        Image logo = new Image(new FileInputStream("resources/StudySphereLogo.png"));
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);

        // Login and Sign Up buttons
        Button loginButton = new Button("Login");
        Button signUpButton = new Button("Sign Up");

        loginButton.setStyle(
                "-fx-background-color: #ffffff; -fx-text-fill: black; -fx-font-size: 16px;" +
                        "-fx-padding: 10 20 10 20; -fx-background-radius: 30;"
        );
        signUpButton.setStyle(
                "-fx-background-color: #00cc99; -fx-text-fill: white; -fx-font-size: 16px;" +
                        "-fx-padding: 10 20 10 20; -fx-background-radius: 30;"
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        navBar.getChildren().addAll(logoView, spacer, loginButton, signUpButton);

        // Center content VBox for title and description
        VBox centerContent = new VBox(30);
        centerContent.setAlignment(Pos.CENTER);
        centerContent.setPadding(new Insets(10, 20, 10, 20));
        centerContent.setMaxWidth(800);  // Center content width limit

        // Title
        Label titleLabel = new Label("StudySphere");
        titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 45));
        titleLabel.setTextFill(Color.BLACK);

        // Description using TextFlow for center justification
        TextFlow descriptionFlow = new TextFlow();
        descriptionFlow.setTextAlignment(TextAlignment.CENTER); // Center text alignment
        descriptionFlow.setMaxWidth(600);  // Optional: limit width for wrapping

        // Add text to the TextFlow
        Text descriptionText = new Text(
                "StudySphere is a group study platform where students collaborate, " +
                        "share resources, and join study groups. \n\n" +
                        "Create or join a study group today!"
        );
        descriptionText.setFont(new Font("Comic Sans MS", 18));
        descriptionText.setFill(Color.BLACK);

        descriptionFlow.getChildren().add(descriptionText);

        centerContent.getChildren().addAll(titleLabel, descriptionFlow);

        // Gradient overlay
        Stop[] stops = new Stop[]{
                new Stop(0, Color.web("#f7F1EC")),
                new Stop(1, Color.web("#f7F1EC44"))
        };
        LinearGradient gradientOverlay = new LinearGradient(
                0, 0, 0, 1, true, null, stops
        );

        // Background image
        Image backgroundImage = new Image(new FileInputStream("resources/Homebgimage.gif"));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(1000);
        backgroundImageView.setFitHeight(500);  // Reduced height further
        backgroundImageView.setPreserveRatio(false);

        // StackPane for background
        StackPane backgroundPane = new StackPane();
        backgroundPane.getChildren().addAll(backgroundImageView);
        backgroundPane.setBackground(new Background(new BackgroundFill(gradientOverlay, CornerRadii.EMPTY, Insets.EMPTY)));
        StackPane.setAlignment(backgroundImageView, Pos.BOTTOM_CENTER);

        // Add to main layout
        mainLayout.getChildren().addAll(navBar, centerContent, backgroundPane);

        // Button actions for navigation
        loginButton.setOnAction(e -> {
            StudySphereLogin loginPage = new StudySphereLogin();
            loginPage.start(new Stage());
            primaryStage.close(); // Close the home window
        });

        signUpButton.setOnAction(e -> {
            StudySphereSignUp signUpPage = new StudySphereSignUp();
            signUpPage.start(new Stage());
            primaryStage.close(); // Close the home window
        });

        // Create scene
        Scene scene = new Scene(mainLayout, 1000, 1000);  // Increased scene height
        primaryStage.setTitle("StudySphere - Home");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
