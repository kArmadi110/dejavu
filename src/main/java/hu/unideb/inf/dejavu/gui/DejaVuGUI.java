package hu.unideb.inf.dejavu.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;

public class DejaVuGUI extends Application {

	public static ComboBox<String> dimensionChoser = new ComboBox<String>();

	public static FileChooser fileChooser = new FileChooser();

	public static List<File> cardPathList = new ArrayList<File>();
	
	public static DVMenu menu = new DVMenu();

	public static Stage stage;

	@Override
	public void start(Stage arg) throws Exception {
//TODO: ELLENŐRIZNI MINDENT MEGFELELŐEN MŰKÖDIK E
		dimensionChoser.setId("dimensionChoser");
		dimensionChoser.getStylesheets()
				.add(WelcomeMenu.class.getClassLoader().getResource("DimensionChoser.css").toExternalForm());

		fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

		fileChooser.setTitle("Nyissa meg a kívánt képeket");

		stage = arg;
		stage.initStyle(StageStyle.TRANSPARENT);

		setNewMenu(new WelcomeMenu());
		stage.setTitle("dejaVu");

		stage.setMaxHeight(600);
		stage.setMinHeight(600);
		stage.setMaxWidth(800);
		stage.setMinWidth(800);
		
		
		//TODO: wtill not work...
		stage.getIcons()
				.add(new Image(WelcomeMenu.class.getClassLoader().getResource("images/icon.png").toExternalForm()));
		stage.show();

	}

	public static void setNewMenu(DVMenu newMenu) {
		menu = newMenu;

		Scene scene = new Scene(menu, 800, 600);
		scene.setFill(Color.TRANSPARENT);

		stage.setScene(scene);
	}
}