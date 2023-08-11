package windowViews;

import java.awt.Color;
import java.io.File;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import views.Main;

public class IntroWindow {
    private final Scene introWindow;
    private Scene homeWindow;

    public Scene getIntroWindow() {
        return introWindow;
    }

    public IntroWindow() {
        File f = new File("src/assets/thelastofus.mp4");
        Media media = new Media(f.toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.setAutoPlay(true);

        MediaView V = new MediaView(player);
        V.setPreserveRatio(true);

        VBox root = new VBox();
        root.getChildren().add(V);
        root.setAlignment(Pos.CENTER);

        player.setOnPlaying(() -> {
            Main.window.getScene().setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.SPACE) {
                    player.stop();
                    Main.window.setScene(new HomeWindow().getHomeWindow());
                    Main.window.setFullScreen(true);
                }
            });
        });

        root.setStyle("-fx-background-color: black"); // Set the background color to black using CSS
        introWindow = new Scene(root, 1024, 1024);
    }
}
