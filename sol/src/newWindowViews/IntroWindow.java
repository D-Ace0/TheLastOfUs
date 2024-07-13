package newWindowViews;

import java.io.File;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class IntroWindow {
    private final Scene introScene; // Declare introScene as final

    public IntroWindow(Stage primaryStage) {
        File f = new File("assets/thelastofus.mp4");

        Media media = new Media(f.toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        MediaView mediaView = new MediaView(player);

        // Get the primary screen and set the MediaView dimensions to match the screen size
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        mediaView.setFitWidth(screenBounds.getWidth());
        mediaView.setFitHeight(screenBounds.getHeight());
        mediaView.setPreserveRatio(false); 

        StackPane root = new StackPane();
        root.getChildren().add(mediaView);

        player.setAutoPlay(true);

        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
        introScene = scene; 

        primaryStage.setScene(scene);

        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(javafx.scene.input.KeyCode.SPACE)) {
                player.stop();
                primaryStage.setScene(new HomeWindow().getHomeWindow());
                primaryStage.setFullScreen(true);
            }
        });

        primaryStage.show();

        // Check if the video has ended naturally
        player.setOnEndOfMedia(() -> {
            primaryStage.setScene(new HomeWindow().getHomeWindow());
            primaryStage.setFullScreen(true);
        });

        player.play();
    }

    public Scene getIntroScene() {
        return introScene;
    }
}
