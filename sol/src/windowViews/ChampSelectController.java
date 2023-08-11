//package windowViews;
//
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.control.Button;
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BackgroundFill;
//import javafx.scene.layout.CornerRadii;
//import javafx.scene.layout.HBox;
//import javafx.scene.paint.Color;
//
//public class ChampSelectController {
//
//	public static void BPressed(Button b, String name, String Damage, String Actions, HBox attr) {
//		b.setOnAction(e -> {
//			
//			ChampionSelect.NameRes.setText(name);
//			ChampionSelect.NameRes.setTextFill(Color.RED);
//			
//			
//			ChampionSelect.DamageRes.setText(Damage);
//			
//			ChampionSelect.ActionsAvailableRes.setText(Actions);
//			
//			attr.getChildren().addAll(ChampionSelect.NameRes,
//					ChampionSelect.ActionsAvailableRes, ChampionSelect.DamageRes);
//			
//			attr.setAlignment(Pos.CENTER);
//			
//			b.setBackground(new Background(new BackgroundFill(Color.CYAN,
//					new CornerRadii(50, 50, 50, 50, true) , Insets.EMPTY)));
//		});
//		
////		b.setOnMouseReleased(ee -> {
////			ChampionSelect.NameRes.setText("");
////			ChampionSelect.DamageRes.setText("");
////			ChampionSelect.ActionsAvailableRes.setText("");
////		});
//
//	}
//	
//}
