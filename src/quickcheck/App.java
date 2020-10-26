package quickcheck;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

	 private final Scene scene = new Scene(new AnchorPane());
	 
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("gui/mainView.fxml"));
		 Parent root = loader.load();
	        this.scene.setRoot(root);
	        this.scene.getStylesheets().addAll(this.getClass().getResource("gui/ModernTheme.css").toExternalForm());
	        stage.setScene(scene);
	        stage.setTitle("Plan Auswerte Tool");
	        stage.setMaximized(true);
	        stage.show();
	}

}
