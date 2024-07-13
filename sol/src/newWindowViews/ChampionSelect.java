package newWindowViews;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import engine.Game;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import model.characters.Hero;
import views.Main;

public class ChampionSelect {
    private Scene championSelectWindow;
    private Button btn = new Button("Start Game");
    private Button pick = new Button("Pick");

    public ChampionSelect() throws IOException {
        Scene scene = new Scene(new Group(), 1366, 768);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
        btn.getStyleClass().add("game-button");
        pick.getStyleClass().add("game-button");

        File f = new File("assets/ChampSelect.png");
        Image img = new Image(f.toURI().toString());
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        BackgroundImage background = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(screenWidth, screenHeight, false, false, true, true));
        BorderPane root = new BorderPane();
        root.setBackground(new Background(background));

        Game.loadHeroes("../Heroes.csv");
        VBox heroesBox = createHeroesBox();

        VBox buttonBox = new VBox(btn, pick);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        root.setCenter(heroesBox);
        root.setBottom(buttonBox);

        btn.setOnAction(e -> {
            Main.window.setScene(new StartGame().getStartGameWindow());
            Main.window.setFullScreen(true);
        });

        championSelectWindow = new Scene(root, 1366, 768);
    }

    private VBox createHeroesBox() {
        VBox heroesBox = new VBox();
        heroesBox.setPadding(new Insets(8));
        heroesBox.setSpacing(8);

        for (Hero hero : Game.availableHeroes) {
            Button heroButton = createHeroButton(hero);
            heroesBox.getChildren().add(heroButton);
        }

        return heroesBox;
    }

    private Button createHeroButton(Hero hero) {
        VBox characterInfo = new VBox();
        characterInfo.setSpacing(5);
        characterInfo.setAlignment(Pos.CENTER_LEFT);

        Button button = new Button("Name: " + hero.getName() + "\nCurrent HP: " + hero.getCurrentHp()
                + "\nActions Available: " + hero.getActionsAvailable() + "\nAttack Damage: " + hero.getAttackDmg());
        button.getStyleClass().add("game-button");

        String imagePath = getImagePathForHero(hero); // Replace with your logic
        Image image = new Image(new File(imagePath).toURI().toString(), 100, 0.0, true, true);
        ImageView imageView = new ImageView(image);
        button.setGraphic(imageView);

        button.setOnAction(e -> {
            Game.startGame(hero);
            StartGame.setPickedHero(hero);
            StartGame.setPickedView(imageView);
        });

        StackPane buttonPane = new StackPane();
        buttonPane.getChildren().addAll(button, characterInfo);
        StackPane.setAlignment(characterInfo, Pos.CENTER_LEFT);

        return button;
    }

    public Scene getChampionSelectWindow() {
        return championSelectWindow;
    }

    private String getImagePathForHero(Hero hero) {
        // Placeholder method to get image path based on hero
        return "assets/"+hero.getName()+".gif"; // Replace with actual logic
    }
}
