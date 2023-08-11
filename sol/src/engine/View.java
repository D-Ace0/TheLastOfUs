package engine;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.characters.Direction;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Collectible;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;
import javafx.scene.input.*;

import java.awt.Point;

import exceptions.MovementException;
import exceptions.NotEnoughActionsException;

public class View extends Application {
    public static Hero tmp;
    public Hero chosenHero;
    public static ImageView iconImageView = new ImageView();
    public Image chosenHeroImage;
    public GridPane layout2 = new GridPane();
    public BorderPane borderPane = new BorderPane();
    private static Image supplyImage;
    private static Image vaccineImage;
    private static Image trapImage;
    private static Image zombieImage;
    

    @Override
    public void start(Stage stage) throws Exception {
        Stage mainStage = new Stage();
        mainStage.setTitle("The Last of Us");

        Game.loadHeroes("D:\\sol\\sol\\Heroes.csv");
        stage.setTitle("Choose A Hero");
        HBox layout1 = new HBox();

        for (int i = 0; i < Game.availableHeroes.size(); i++) {
            Hero tmp = Game.availableHeroes.get(i);
            Label heroName = new Label("Hero Name: " + tmp.getName());
            Label heroDmg = new Label("Damage: " + tmp.getAttackDmg());
            Label heroActions = new Label("Available Actions: " + tmp.getActionsAvailable());
            VBox vbox = new VBox();
            layout1.getChildren().addAll(vbox);

            ImageView iconImageView = new ImageView();
            iconImageView.setFitWidth(80);
            iconImageView.setFitHeight(80);

            if (tmp instanceof Fighter) {
                Image fighterIcon = new Image("images.png");
                iconImageView.setImage(fighterIcon);
            } else if (tmp instanceof Explorer) {
                Image explorerIcon = new Image("Explorer.png");
                iconImageView.setImage(explorerIcon);
            } else if (tmp instanceof Medic) {
                Image medicIcon = new Image("Medic.jpg");
                iconImageView.setImage(medicIcon);
            }

            vbox.getChildren().addAll(heroName, heroDmg, heroActions, iconImageView);

            iconImageView.setOnMouseClicked(e -> {
                chosenHeroImage = iconImageView.getImage();
                chosenHero = createCopy(tmp);
                Game.startGame(chosenHero);
                stage.close();
                Screen screen = Screen.getPrimary();
                Rectangle2D bounds = screen.getVisualBounds();
                mainStage.setX(bounds.getMinX());
                mainStage.setY(bounds.getMinY());
                mainStage.setWidth(bounds.getWidth());
                mainStage.setHeight(bounds.getHeight());
                Scene mainScene = new Scene(layout2);
                mainStage.setScene(mainScene);
                initCells();
                mainStage.show();
            });
        }

        // Assets for the stage of choosing a hero
        layout1.setSpacing(10);
        layout1.setPadding(new Insets(10));
        Scene scene = new Scene(layout1);
        stage.setScene(scene);
        stage.show();
    }


    private Hero createCopy(Hero original) {
        Hero copy = null;
        try {
            Class<?> heroClass = original.getClass();
            copy = (Hero) heroClass.getDeclaredConstructor(String.class, int.class, int.class, int.class)
                    .newInstance(original.getName(), original.getMaxHp(), original.getAttackDmg(), original.getActionsAvailable());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return copy;
    }

public void initCells() {
    layout2.setAlignment(Pos.CENTER);

    for (int k = 0; k < 15; k++) {
        for (int j = 0; j < 15; j++) {
            Button button = new Button();
            layout2.getChildren().add(button);
            button.setPrefWidth(40);
            button.setPrefHeight(40);

            Cell cell = Game.map[k][j];

            if (cell instanceof CharacterCell) {
                CharacterCell characterCell = (CharacterCell) cell;
                model.characters.Character character = characterCell.getCharacter();
                if (character instanceof Hero) {
                    Hero hero = (Hero) character;
                    if (chosenHero == hero) {
                        ImageView view = new ImageView(chosenHeroImage);
                        view.setFitWidth(30);
                        view.setFitHeight(30);
                        button.setGraphic(view);
                    }
                }
            } 
            layout2.setConstraints(button, j, k);
        }
    }
}

    public static void main(String[] args) {
        launch(args);
    }
}
