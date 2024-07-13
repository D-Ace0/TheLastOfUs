package views;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import newWindowViews.*;

public class Main extends Application {

    public static Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("The Last Of Us");

        IntroWindow introWindow = new IntroWindow(window);
        Scene introScene = introWindow.getIntroScene(); 
        // Load CSS file
        
        Scene scene = new Scene(new Group(), 1366, 768);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
        
        window.setScene(introScene);
        window.setFullScreen(true);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
