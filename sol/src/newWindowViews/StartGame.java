package newWindowViews;

import java.awt.Point;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.characters.Character;
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

public class StartGame {
	
    static String mp3 = "assets/startgame.mp3";
    static Media m = new Media(Paths.get(mp3).toUri().toString());
    static MediaPlayer player = new MediaPlayer(m);
    static MediaView v = new MediaView(player);
	private Scene startGameWindow;
	private static Hero PickedHero;
	private static ImageView pickedView;
	Button attack = new Button("Attack");
	Button endTurn = new Button("EndTurn");
	Button cure = new Button("Cure");
	FlowPane forActions = new FlowPane();
    static GridPane bpane = new GridPane();

	Button moveUp = new Button("Down");
	Button moveDown = new Button("Up");
	Button moveRight = new Button("Right");
	Button moveLeft = new Button("Left");
	Button useSpecial = new Button("Use special");
	TilePane forMove = new TilePane();
	
	

	
public StartGame() {
    // Initialize UI components
    forActions.setAlignment(Pos.TOP_LEFT);
    forMove.getChildren().addAll(moveUp, moveDown, moveRight, moveLeft, attack, endTurn, cure);
    forMove.setAlignment(Pos.TOP_RIGHT);

    // Initialize root pane and background
    BorderPane root = new BorderPane();
    File f = new File("assets/StartGame.jpeg");
    Image img = new Image(f.toURI().toString());
    BackgroundImage background = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
    root.setBackground(new Background(background));

    // Play sound or animation (player)
    player.play();
    
    VBox stats = new VBox();

    root.setCenter(bpane);
    root.setRight(forActions);
    root.setLeft(forMove);
    root.setBottom(stats); // Set stats to bottom if it's supposed to be persistent
    
    // Initialize and set up initial cells and stats
    initCells(bpane);
    showStats(stats, PickedHero);

    // root.getChildren().addAll(v); // Not sure what v represents here

    // Create the scene
    startGameWindow = new Scene(root, 1366, 768);

    // Set event handlers for actions
    attack.setOnMouseClicked(e -> {
        try {
            getPickedHero().attack();
            initCells(bpane); // Refresh cells after attack
            showStats(stats, PickedHero);
        } catch (NotEnoughActionsException | InvalidTargetException e1) {
            AlertBox.display(e1.getMessage(),"Attack Exception");
        }
    });

    cure.setOnMouseClicked(e -> {
        try {
            getPickedHero().cure();
            initCells(bpane); // Refresh cells after curing
            showStats(stats, PickedHero);
        } catch (NoAvailableResourcesException | InvalidTargetException | NotEnoughActionsException e1) {
            e1.printStackTrace();
            AlertBox.display(e1.getMessage(), "Cure Exception");
        }
    });

    endTurn.setOnMouseClicked(e -> {
        try {
            Game.endTurn();
            initCells(bpane); // Refresh cells after ending turn
            showStats(stats, PickedHero);
        } catch (NotEnoughActionsException | InvalidTargetException e1) {
            e1.printStackTrace();
            AlertBox.display(e1.getMessage(), "endTurn Exception");
        }
    });

    moveUp.setOnMouseClicked(e -> {
        try {
            getPickedHero().move(Direction.UP);
            initCells(bpane); // Refresh cells after moving
            showStats(stats, PickedHero);
        } catch (MovementException | NotEnoughActionsException e1) {
            AlertBox.display(e1.getMessage(), "Movement Exception");
            e1.printStackTrace();
        }
    });
    moveDown.setOnMouseClicked(e -> {
    	try {
    		getPickedHero().move(Direction.DOWN);
    		initCells(bpane); // Refresh cells after moving
    	    showStats(stats, PickedHero);
    	} catch (MovementException | NotEnoughActionsException e1) {
    		AlertBox.display(e1.getMessage(), "Movement Exception");
    		e1.printStackTrace();
    	}
    });
    moveRight.setOnMouseClicked(e -> {
    	try {
    		getPickedHero().move(Direction.RIGHT);
    		initCells(bpane); // Refresh cells after moving
    	    showStats(stats, PickedHero);
    	} catch (MovementException | NotEnoughActionsException e1) {
    		AlertBox.display(e1.getMessage(), "Movement Exception");
    		e1.printStackTrace();
    	}
    });
    moveLeft.setOnMouseClicked(e -> {
    	try {
    		getPickedHero().move(Direction.LEFT);
    		initCells(bpane); // Refresh cells after moving
    	    showStats(stats, PickedHero);
    	} catch (MovementException | NotEnoughActionsException e1) {
    		AlertBox.display(e1.getMessage(), "Movement Exception");
    		e1.printStackTrace();
    	}
    });
    
	useSpecial.setOnMouseClicked(e -> {
		try {
			getPickedHero().useSpecial();
			initCells(bpane);
		    showStats(stats, PickedHero);
		} catch (NoAvailableResourcesException | InvalidTargetException e1) {
			AlertBox.display(e1.getMessage(), "UseSpecial Exception");
		}

	});
}


public static boolean isVisible(Cell c) {
	return c.isVisible();
}

public static void initCells(GridPane bpane) {
    bpane.getChildren().clear(); // Clear existing children to refresh the grid

    for (int i = 0; i < Game.map.length; i++) {
        for (int k = 0; k < Game.map[i].length; k++) {
            Button button = new Button();
            button.setPrefWidth(50);
            button.setPrefHeight(50);

            Cell c = Game.map[i][k];
            ImageView view = null;

            if (!isVisible(c)) {
                File f = new File("assets/x.jpg");
                Image img = new Image(f.toURI().toString());
                view = new ImageView(img);
            } else {
                if (c instanceof CharacterCell) {
                    Character me = ((CharacterCell) c).getCharacter();
                    if (me instanceof Zombie) {
                        Zombie z = (Zombie) me;
                        File f = new File("assets/zombie.gif");
                        Image img = new Image(f.toURI().toString());
                        view = new ImageView(img);
                        button.setOnAction(ee -> {
                            PickedHero.setTarget(z);
                            AlertBox.display("This Zombie has been set as a target for " + PickedHero.getName(), "Set Target");
                        });
                    } else if (me instanceof Hero) {
                        Hero h = (Hero) me;
                        File heroImage = null;

                        if (h instanceof Fighter) {
                            if ("Joel Miller".equals(h.getName())) {
                                heroImage = new File(getImagePathForHero(h));
                            } else {
                                heroImage = new File(getImagePathForHero(h));
                            }
                        } else if (h instanceof Explorer) {
                            if ("Tess".equals(h.getName())) {
                                heroImage = new File(getImagePathForHero(h));
                            } else if ("Tommy Miller".equals(h.getName())) {
                                heroImage = new File(getImagePathForHero(h));
                            } else {
                                heroImage = new File(getImagePathForHero(h));
                            }
                        } else if (h instanceof Medic) {
                            if ("Henry Burell".equals(h.getName())) {
                                heroImage = new File(getImagePathForHero(h));
                            } else if ("Bill".equals(h.getName())) {
                                heroImage = new File(getImagePathForHero(h));
                            } else {
                                heroImage = new File(getImagePathForHero(h));
                            }
                        }

                        if (heroImage != null) {
                            Image img = new Image(heroImage.toURI().toString());
                            view = new ImageView(img);
                        }
                    }
                } else if (c instanceof CollectibleCell) {
                    Collectible collectible = ((CollectibleCell) c).getCollectible();
                    if (collectible instanceof Vaccine) {
                        File f = new File("assets/vaccine.gif");
                        Image img = new Image(f.toURI().toString());
                        view = new ImageView(img);
                    } else if (collectible instanceof Supply) {
                        File f = new File("assets/supply.gif");
                        Image img = new Image(f.toURI().toString());
                        view = new ImageView(img);
                    }
                }
            }

            if (view != null) {
                view.setFitWidth(button.getPrefWidth() - 20);
                view.setFitHeight(button.getPrefHeight() - 20);
                button.setGraphic(view);
            }

            bpane.add(button, k, i); // Directly add button to GridPane without clearing
        }
    }
}


private static String getImagePathForHero(Hero hero) {
    // Placeholder method to get image path based on hero
    return "assets/"+hero.getName()+".gif"; // Replace with actual logic
}

public static void showStats(VBox stats, Character character) {
    stats.getChildren().clear(); // Clear previous stats

    // Add character name
    Label name = new Label(character.getName());
    name.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
    name.setTextFill(Color.WHITE);

    // Add character health
    Label health = new Label("Health: " + character.getCurrentHp() + " / " + character.getMaxHp());
    health.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
    health.setTextFill(Color.WHITE);

    // Display stats specific to heroes
    if (character instanceof Hero) {
        Hero hero = (Hero) character;

        // Add hero actions
        Label actions = new Label("Actions: " + hero.getActionsAvailable() + " / " + hero.getMaxActions());
        actions.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        actions.setTextFill(Color.WHITE);

        // Add hero special action status
        Label specialAction = new Label("Special Action: " + (hero.isSpecialAction() ? "Available" : "Not Available"));
        specialAction.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        specialAction.setTextFill(Color.WHITE);

        stats.getChildren().addAll(actions, specialAction);

        // Display vaccine inventory
        if (!hero.getVaccineInventory().isEmpty()) {
            Label vaccines = new Label("Vaccine Inventory:");
            vaccines.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
            vaccines.setTextFill(Color.WHITE);
            stats.getChildren().add(vaccines);
            
            for (Vaccine vaccine : hero.getVaccineInventory()) {
            	vaccines.setText("Vaccine Inventory: "+hero.getVaccineInventory().size());
            }
        }

        // Display supply inventory
        if (!hero.getSupplyInventory().isEmpty()) {
            Label supplies = new Label("Supply Inventory:");
            supplies.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
            supplies.setTextFill(Color.WHITE);
            stats.getChildren().add(supplies);

            for (Supply supply : hero.getSupplyInventory()) {
            	supplies.setText("Supply Inventory: "+hero.getSupplyInventory().size());

            }
        }
    }

    // Display current health and other basic stats
    stats.getChildren().addAll(name, health);
}

	
	public static void setPickedView(ImageView pickedView) {
		StartGame.pickedView = pickedView;
	}
	
	
	public static Hero getPickedHero() {
		return PickedHero;
	}
	
	
	public static void setPickedHero(Hero pickedHero) {
		PickedHero = pickedHero;
	}
	
	public Scene getStartGameWindow() {
		return startGameWindow;
	}

}
