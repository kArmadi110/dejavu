package hu.unideb.inf.dejavu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import hu.unideb.inf.dejavu.gui.DVMenu;
import hu.unideb.inf.dejavu.gui.ExitToolbar;
import hu.unideb.inf.dejavu.gui.SlidePane;
import hu.unideb.inf.dejavu.gui.WelcomeMenu;

//TODO: a logika megkapa, hogy hányszor hanyas, és ott vágjuk szét
//TODO: playground
//TODO: NyereményPane
//TODO: játékmentés betöltése új ablak
//TODO: refactor: átnézni, getterek, ahol a static tagra hivatkoztam, dejavu osztályból kigányolni ami nem oda való

/**
 * A main metódust tartalmazó osztály.
 * 
 * A {@code DejaVu} osztály tartalmazza a játékban lévő objektumokat.
 * 
 * @author iam346
 *
 */
public class DejaVu extends Application {

	public static ComboBox<String> dimensionChoser = new ComboBox<String>();

	public static FileChooser fileChooser = new FileChooser();
	/**
	 * Az aktuális képernyő közepe.
	 */
	public static DVMenu menu = new DVMenu();
	/**
	 * Az aktuális ablak.
	 */
	public static Stage stage;

	/**
	 * A logika
	 */
	public static Game game;

	/**
	 * A program main metódusa.
	 * 
	 * @param args
	 *            Parancssori argumentumok.
	 */
	public static void main(String[] args) {
		DataB.connect();
		dimensionChoser.setId("dimensionChoser");
		dimensionChoser.getStylesheets().add(
				SlidePane.class.getResource("DimensionChoser.css")
						.toExternalForm());
		game = new Game();

		launch(args);
	}

	@Override
	public void start(Stage arg) throws Exception {

		fileChooser.setTitle("Nyissa meg a kívánt képeket");

		stage = arg;
		stage.initStyle(StageStyle.TRANSPARENT);

		setNewMenu(new WelcomeMenu());

		stage.setTitle("dejaVu");

		stage.setMaxHeight(600);// nem túl szép
		stage.setMinHeight(600);
		stage.setMaxWidth(800);
		stage.setMinWidth(800);

		stage.show();

	}

	public static void setNewMenu(DVMenu newMenu) {
		menu = newMenu;
		menu.setTop(new ExitToolbar(stage));

		Scene scene = new Scene(menu, 800, 600);
		scene.setFill(Color.TRANSPARENT);

		stage.setScene(scene);
	}

}
