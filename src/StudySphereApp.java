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

