package newWindowViews;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import views.Main;

public class AlertBox {
	public static void display(String message, String title) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		
		Label msg = new Label(message);
		Button btn = new Button("Close Window");
		btn.setOnAction(e -> window.close());
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(msg,btn);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout, 400,400);
		window.setScene(scene);
		window.showAndWait();
	}
 /** 
  * code of this alert box in for javaFx class
  * 
  * 
  * public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("test");
		
		StackPane layout = new StackPane();
		Button btn = new Button("Click me to test Alert Box");
		layout.getChildren().add(btn);
		btn.setOnAction(e -> AlertBox.display("Warning", "Alert"));
		Scene scene = new Scene(layout, 800,700);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
  * 
  * 
  * **/
}
