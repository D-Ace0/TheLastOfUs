package newWindowViews;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import views.Main;

public class HomeWindow {
    static String mp3 = "assets/introaudio.mp3";
    static Media m = new Media(Paths.get(mp3).toUri().toString());
    static MediaPlayer player = new MediaPlayer(m);
    static MediaView v = new MediaView(player);

    private static Scene HomeWindow;

    public HomeWindow() {
        StackPane root = new StackPane();
        StackPane forButtons = new StackPane();
        
        forButtons.setAlignment(Pos.TOP_CENTER);
        
        File f = new File("assets/HomeWindow.jpeg");
        Image backgroundImage = new Image(f.toURI().toString());
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));

        root.setBackground(new Background(background));
        root.getChildren().addAll(v, forButtons);

        HomeWindow = new Scene(root, 1366,768);
        
        player.play();
        v.fitWidthProperty().bind(HomeWindow.widthProperty());
        v.fitHeightProperty().bind(HomeWindow.heightProperty());
        
        
        
        
        
        Button Play = new Button("Champion Select");
        Play.setOnMouseClicked(e -> {
        	try {
        		player.stop();
				Main.window.setScene(new ChampionSelect().getChampionSelectWindow());
				Main.window.setFullScreen(true);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        });
        forButtons.getChildren().add(Play);
    }

    public Scene getHomeWindow() {
        return HomeWindow;
    }
    
    
}
