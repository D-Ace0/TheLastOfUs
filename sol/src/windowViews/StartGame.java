package windowViews;

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
	
    static String mp3 = "src/assets/startgame.mp3";
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

	Button moveUp = new Button("Up");
	Button moveDown = new Button("Down");
	Button moveRight = new Button("Right");
	Button moveLeft = new Button("Left");
	Button useSpecial = new Button("Use special");
	TilePane forMove = new TilePane();
	
	

	
	public StartGame() {
		
	//	forActions.getChildren().addAll(attack, endTurn, cure);
		forActions.setAlignment(Pos.TOP_LEFT);
		
		
		forMove.getChildren().addAll(moveUp, moveDown, moveRight, moveLeft, attack, endTurn, cure);
		forMove.setAlignment(Pos.TOP_RIGHT);
		BorderPane root = new BorderPane();
		File f = new File("src/assets/startgame.gif");
		Image img = new Image(f.toURI().toString());
		BackgroundImage background = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
		root.setBackground(new Background(background));
		
		player.play();
		
		initCells();
	    
	    root.setCenter(bpane);
	    root.setRight(forActions);
	    root.setLeft(forMove);
	    VBox stats = showStats(PickedHero);
	    
	    root.setRight(stats);
	    root.getChildren().addAll(v);

		startGameWindow = new Scene(root,1366,768);
		
		
		
		attack.setOnMouseClicked(e -> {
			try {
				getPickedHero().attack();
			} catch (NotEnoughActionsException | InvalidTargetException e1) {
				AlertBox.display(e1.getMessage(),"Attack Exception");
			}
			initCells();
			showStats(PickedHero);
		});
		
		cure.setOnMouseClicked(e -> {
			try {
				getPickedHero().cure();
			} catch (NoAvailableResourcesException | InvalidTargetException | NotEnoughActionsException e1) {
				e1.printStackTrace();
				AlertBox.display(e1.getMessage(), "Cure Exception");
			}
			initCells();
			showStats(PickedHero);

		});
		
		endTurn.setOnMouseClicked(e -> {
			try {
				Game.endTurn();
			} catch (NotEnoughActionsException | InvalidTargetException e1) {
				e1.printStackTrace();
				AlertBox.display(e1.getMessage(), "endTurn Exception");
			}
			initCells();
			showStats(PickedHero);

		});
		
		moveUp.setOnMouseClicked(e -> {
			try {
				getPickedHero().move(Direction.UP);
			} catch (MovementException | NotEnoughActionsException e1) {
				AlertBox.display(e1.getMessage(), "Movement Exception");
				e1.printStackTrace();
			}
			initCells();
			showStats(PickedHero);

		});
		moveDown.setOnMouseClicked(e -> {
			try {
				getPickedHero().move(Direction.DOWN);
				} catch (MovementException | NotEnoughActionsException e1) {
				AlertBox.display(e1.getMessage(), "Movement Exception");
				e1.printStackTrace();
			}
			initCells();
			showStats(PickedHero);

		});
		moveRight.setOnMouseClicked(e -> {
			try {
				getPickedHero().move(Direction.RIGHT);
				} catch (MovementException | NotEnoughActionsException e1) {
				AlertBox.display(e1.getMessage(), "Movement Exception");
				e1.printStackTrace();
			}
			initCells();
			showStats(PickedHero);

		});
		moveLeft.setOnMouseClicked(e -> {
			try {
				getPickedHero().move(Direction.LEFT);
				} catch (MovementException | NotEnoughActionsException e1) {
				AlertBox.display(e1.getMessage(), "Movement Exception");
				e1.printStackTrace();
			}
			initCells();
			showStats(PickedHero);

		});
		useSpecial.setOnMouseClicked(e -> {
			try {
				getPickedHero().useSpecial();
			} catch (NoAvailableResourcesException | InvalidTargetException e1) {
				AlertBox.display(e1.getMessage(), "UseSpecial Exception");
			}
			initCells();
			showStats(PickedHero);

		});
	}


public static boolean isVisible(Cell c) {
	return c.isVisible();
}

public static GridPane initCells() {
	

    for (int i = 0; i < 15; i++) {
        for (int k = 0; k < 15; k++) {
            ImageView view = null;
            Button button = new Button();
            button.setPrefWidth(50);
            button.setPrefHeight(50);
            Cell c = Game.map[i][k];
            if(!isVisible(c)) {
                File f = new File("src/assets/x.jpg");
                Image img = new Image(f.toURI().toString());
                view = new ImageView(img);
                view.setFitWidth(button.getPrefWidth() - 20);
                view.setFitHeight(button.getPrefHeight() - 20);
                button.setGraphic(view);
            }else {
                if (c instanceof CharacterCell) {
                    Character me = ((CharacterCell) c).getCharacter();
                    if (me instanceof Zombie) {
                    	Zombie z = (Zombie) ((CharacterCell) c).getCharacter();
                        File f = new File("src/assets/zombie.gif");
                        Image img = new Image(f.toURI().toString());
                        view = new ImageView(img);
                        button.setOnAction(ee -> {
                        	PickedHero.setTarget(z);
                        	AlertBox.display("This Zombie has been set as a target for " + PickedHero.getName(), "Set Target");
                        });
                    } else if (me instanceof Hero) {
                        Hero h = (Hero) me;

                        if (h instanceof Fighter) {
                        	File f = new File("src/assets/fighter.gif");
                        	if(h.getName().equals("Joel Miller")) {
                        		f = new File("src/assets/male_fighter.gif");
                        	}
                            Image img = new Image(f.toURI().toString());
                            view = new ImageView(img);
                            view.setFitWidth(button.getPrefWidth() - 20);
                            view.setFitHeight(button.getPrefHeight() - 20);
                        } else if (h instanceof Explorer) {
                            File f = new File("src/assets/female_exploer.gif");
                            if(h.getName().equals("Tess")) {
                        		f = new File("src/assets/explorer1.gif");
                        	}
                            if(h.getName().equals("Tommy Miller")) {
                            	f = new File("src/assets/explorer2.gif");
                            }
                            Image img = new Image(f.toURI().toString());
                            view = new ImageView(img);
                            view.setFitWidth(button.getPrefWidth() - 20);
                            view.setFitHeight(button.getPrefHeight() - 20);
                        } else if (h instanceof Medic) {
                            File f = new File("src/assets/medic1.gif");
                            if(h.getName().equals("Henry Burell")) {
                            	f = new File("src/assets/medic3.gif");
                            }
                            if(h.getName().equals("Bill")) {
                            	f = new File("src/assets/medic2.gif");
                            }
                            Image img = new Image(f.toURI().toString());
                            view = new ImageView(img);
                            view.setFitWidth(button.getPrefWidth() - 20);
                            view.setFitHeight(button.getPrefHeight() - 20);	
                        }
                    }

                    if (view != null) {
                        view.setFitWidth(button.getPrefWidth() - 20);
                        view.setFitHeight(button.getPrefHeight() - 20);
                        button.setGraphic(view);
                    }
                } else if (c instanceof CollectibleCell) {
                    view = null;
                    if (((CollectibleCell) c).getCollectible() instanceof Vaccine) {
                        File f = new File("src/assets/vaccine.gif");
                        Image img = new Image(f.toURI().toString());
                        view = new ImageView(img);
                    } else if (((CollectibleCell) c).getCollectible() instanceof Supply) {
                        File f = new File("src/assets/supply.gif");
                        Image img = new Image(f.toURI().toString());
                        view = new ImageView(img);
                    }

                    if (view != null) {
                        view.setFitWidth(button.getPrefWidth() - 20);
                        view.setFitHeight(button.getPrefHeight() - 20);
                        button.setGraphic(view);
                    }
                }
            }
            bpane.getChildren().add(button);
            bpane.setAlignment(Pos.CENTER);
            GridPane.setConstraints(button, k, Game.map.length - 1 - i);
        }
    }
    return bpane;
}

	public static VBox showStats(Hero h) {
		VBox stats = new VBox();
		Label Heal = new Label("HP: " + h.getCurrentHp());
		Label Damage = new Label("Damage: " + h.getAttackDmg());
		Label Vaccines = new Label("Vaccines: " + h.getVaccineInventory().size());
		Label Supplies = new Label("Supplies: " + h.getSupplyInventory().size());
		stats.getChildren().addAll(Heal,Damage,Vaccines,Supplies);
		Heal.setFont(Font.loadFont("file:src/assets/Sopberry.otf", 24));
      	Heal.setTextFill(Color.GOLD);
      	Heal.setFont(Font.font("Arial", FontWeight.BOLD, 18));
      	
      	Damage.setFont(Font.loadFont("file:src/assets/Sopberry.otf", 24));
      	Damage.setTextFill(Color.GOLD);
      	Damage.setFont(Font.font("Arial", FontWeight.BOLD, 18));
      	
      	Vaccines.setFont(Font.loadFont("file:src/assets/Sopberry.otf", 24));
      	Vaccines.setTextFill(Color.GOLD);
      	Vaccines.setFont(Font.font("Arial", FontWeight.BOLD, 18));

      	Supplies.setFont(Font.loadFont("file:src/assets/Sopberry.otf", 24));
      	Supplies.setTextFill(Color.GOLD);
      	Supplies.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		return stats;
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
