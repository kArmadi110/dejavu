package hu.unideb.inf.dejavu.gui;

import hu.unideb.inf.dejavu.DejaVu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainMenu extends DVMenu {
	DVButton game = new DVButton("Játék", 0);
	DVButton settings = new DVButton("Beállítások", 0);
	DVButton highScore = new DVButton("Eredménytábla", 0);
	DVButton back = new DVButton("Vissza", 1);

	MainMenu() {
		super();

		SlidePane rightPane = new SlidePane(500, settings,highScore);
		setRight(rightPane);
		
		back.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				DejaVu.setNewMenu(new WelcomeMenu());

			}
		});

		setHgap(10);
		setVgap(10);
		add(new DVText("DejaVu", Font.font("Verdana", FontWeight.BOLD, 30)), 1,
				2);

		add(game, 1, 17);
		add(settings, 1, 19);
		add(highScore, 1, 21);
		add(back, 1, 42);

	}
}
