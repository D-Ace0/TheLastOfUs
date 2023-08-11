package windowViews;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import engine.Game;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import model.characters.Hero;
import views.Main;

public class ChampionSelect {
    static String mp3 = "src/assets/champselect.mp3";
    static Media m = new Media(Paths.get(mp3).toUri().toString());
    static MediaPlayer player = new MediaPlayer(m);
    static MediaView v = new MediaView(player);
    private Scene championSelectWindow;
    Button btn = new Button("Start Game");
    Button pick = new Button("Pick");

    ArrayList<String> heroNames = new ArrayList<>();
    StackPane root = new StackPane();
    VBox attr = new VBox();

    public ChampionSelect() throws IOException {
        File f = new File("src/assets/hhhh.jpg");
        Image img = new Image(f.toURI().toString());
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        BackgroundImage background = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(screenWidth, screenHeight, false, false, true, true));
        root.setBackground(new Background(background));
        player.play();

        Game.loadHeroes("D:\\sol\\sol\\Heroes.csv");
        VBox Heroes = new VBox();

        Button JoelMiller = createHeroButton("src/assets/male_fighter.gif", "Fighter",Game.availableHeroes.get(0));
        Button EllieWilliams = createHeroButton("src/assets/medic1.gif", "Medic",Game.availableHeroes.get(1));
        Button Tess = createHeroButton("src/assets/explorer1.gif", "Explorer", Game.availableHeroes.get(2));
        Button RileyAbel = createHeroButton("src/assets/female_exploer.gif", "Explorer",Game.availableHeroes.get(3));
        Button TommyMiller = createHeroButton("src/assets/explorer2.gif", "Explorer",Game.availableHeroes.get(4));
        Button Bill = createHeroButton("src/assets/medic2.gif", "Medic",Game.availableHeroes.get(5));
        Button David = createHeroButton("src/assets/fighter.gif", "Fighter",Game.availableHeroes.get(6));
        Button HenryBurell = createHeroButton("src/assets/medic3.gif", "Medic",Game.availableHeroes.get(7));

        Heroes.setPadding(new Insets(8));
        Heroes.getChildren().addAll(JoelMiller, EllieWilliams, Tess, RileyAbel, TommyMiller, Bill, David,
                HenryBurell);

        VBox butto = new VBox(btn, pick);
        butto.setAlignment(Pos.BOTTOM_CENTER);

        StackPane championSelectLayout = new StackPane();
        championSelectLayout.getChildren().addAll(Heroes, butto, attr);
        root.getChildren().add(championSelectLayout);

        btn.setOnAction(e ->{
        	player.stop();
        	Main.window.setScene(new StartGame().getStartGameWindow());
        	Main.window.setFullScreen(true);
        });

        championSelectWindow = new Scene(root, 1366,768);
    }

    public Button createHeroButton(String imagePath, String type, Hero h) {
    	Button b = new Button("Name: "+ h.getName()+ "\nCurrentHP: " + h.getCurrentHp() + "\nActions Available: "+ h.getActionsAvailable()
    	+ "\nAttackDamage: "+h.getAttackDmg()+ "\nType: " + type);
    	b.setMinSize(90, 90);
    	File img = new File(imagePath);
    	Image image = new Image(img.toURI().toString() , 100, 0.0, true, true);
    	ImageView imageView = new ImageView(image);
    	b.setGraphic(imageView);
    	b.setOnAction(e -> {
    		pick.fire();
          pick.setOnAction(ee -> {
        	  AlertBox.display("Hero: " + h.getName() +" has been selected!", "ChampSelected ");
        	  Game.startGame(h);
        	  StartGame.setPickedHero(h);
        	  StartGame.setPickedView(imageView);
          	});
    	});
    	return b;
    }
    
//    private Button createHeroButton(String imagePath, Hero hero) {
//        Button button = new Button();
//        button.setMinSize(180, 180);
//        File imageFile = new File(imagePath);
//        Image image = new Image(imageFile.toURI().toString(), 150, 0.0, true, true);
//        ImageView imageView = new ImageView(image);
//        button.setGraphic(imageView);
//        button.setBackground(null);
//
//        button.setOnAction(e -> {
//            attr.getChildren().clear(); // Clear existing labels
//            pick.fire();
//            pick.setOnAction(ee -> {
//                Game.startGame(hero);
//                StartGame.setPickedHero(hero);
//                StartGame.setPickedView(imageView);
//            });
//
//            Label nameLabel = new Label("Name: " + hero.getName() + "\n");
//            nameLabel.setFont(Font.loadFont("file:src/assets/Sopberry.otf", 24));
//            nameLabel.setTextFill(Color.GOLD);
//            nameLabel.setEffect(new DropShadow(5, Color.BLACK));
//
//            Label damageLabel = new Label("\nDamage: " + hero.getAttackDmg() + "\n");
//            damageLabel.setFont(Font.loadFont("file:src/assets/Sopberry.otf", 24));
//            damageLabel.setTextFill(Color.GOLD);
//            damageLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
//
//            Label actionsLabel = new Label("\nActions Available: " + hero.getActionsAvailable());
//            actionsLabel.setFont(Font.loadFont("file:src/assets/Sopberry.otf", 24));
//            actionsLabel.setTextFill(Color.GOLD);
//            actionsLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
//
//            Label healLabel = new Label("\nHealth: " + hero.getCurrentHp());
//            healLabel.setFont(Font.loadFont("file:src/assets/Sopberry.otf", 24));
//            healLabel.setTextFill(Color.GOLD);
//            healLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
//            
//            attr.getChildren().addAll(nameLabel, damageLabel, actionsLabel, healLabel);
//            
//            boolean typeLabelExists = attr.getChildren().stream()
//                    .anyMatch(child -> child instanceof Label && ((Label) child).getText().contains("Type:"));
//            Label typeLabel = new Label();
//            
//            if (!typeLabelExists) {
//            	typeLabel = new Label();
//                if (hero instanceof Fighter)
//                    typeLabel.setText("\nType: Fighter");
//                else if (hero instanceof Medic)
//                    typeLabel.setText("\nType: Medic");
//                else if (hero instanceof Explorer)
//                    typeLabel.setText("\nType: Explorer");
//
//                typeLabel.setFont(Font.loadFont("file:src/assets/Sopberry.otf", 24));
//                typeLabel.setTextFill(Color.GOLD);
//                typeLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
//
//                attr.getChildren().add(typeLabel);
//            }
//            button.setText("Name: "+nameLabel + "\nHealth: "+healLabel+ "\nAttackDamage: " + damageLabel + "\nType:" + typeLabel);
//            attr.setAlignment(Pos.CENTER);
//        });
//        
//        return button;
//    }

    public Scene getChampionSelectWindow() {
        return championSelectWindow;
    }
}
