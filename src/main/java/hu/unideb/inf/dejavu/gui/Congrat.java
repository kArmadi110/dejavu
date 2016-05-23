package hu.unideb.inf.dejavu.gui;

import hu.unideb.inf.dejavu.controller.DejaVu;
import hu.unideb.inf.dejavu.objects.HighScoreRecord;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Congrat extends DVMenu {

	public Congrat(int clicks,int dimension) {
		super();

		DVText grat = new DVText("Ön nyert!", Font.font("Verdana", FontWeight.BOLD, 30));

		DVButton back = new DVButton("Főmenü", 0);

		back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				DejaVuGUI.setNewMenu(new MainMenu());
			}
		});

		setHgap(100);
		setVgap(250);
		add(grat, 1, 1);
		add(back, 0, 2);
		
		DejaVu.game.updateHighScores(clicks,dimension);

	}
}
