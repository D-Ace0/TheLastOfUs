package views;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import windowViews.*;

public class Main extends Application{

	public static Stage window;
	private Scene introWindow;
	@Override
	public void start(Stage primarystage) throws Exception {
		window = primarystage;
		window.setTitle("The Last Of Us");
		
		window.setScene(new windowViews.IntroWindow().getIntroWindow());
		
		window.setFullScreen(true);
		window.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
