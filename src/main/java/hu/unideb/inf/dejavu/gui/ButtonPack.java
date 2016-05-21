package hu.unideb.inf.dejavu.gui;

import java.net.MalformedURLException;

import hu.unideb.inf.dejavu.DejaVu;
import hu.unideb.inf.dejavu.objects.Position;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;

public class ButtonPack extends GridPane {
	CardButton[][] cardButtons;
	static int clicks=0;
	ButtonPack() {
		setHgap(5);
		setVgap(5);

		cardButtons = new CardButton[DejaVu.game.getDim()][DejaVu.game.getDim()];

		for (int i = 0; i < DejaVu.game.getDim(); i++)
			for (int j = 0; j < DejaVu.game.getDim(); j++)
				cardButtons[i][j] = new CardButton(DejaVu.game.getCard(i, j), DejaVu.game.getDim());

		for (int i = 0; i < DejaVu.game.getDim(); i++) {
			for (int j = 0; j < DejaVu.game.getDim(); j++) {
				cardButtons[i][j].setOnAction(new cardEventHandler(i, j));

				add(cardButtons[i][j], i, j);

				if (!DejaVu.game.getCard(i, j).isClicked())
					try {
						cardButtons[i][j].setCard();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				else
					cardButtons[i][j].setBack();
			}
		}
	}

	class cardEventHandler implements EventHandler<ActionEvent> {
		int i, j;

		cardEventHandler(int i, int j) {
			this.i = i;
			this.j = j;
		}

		@Override
		public void handle(ActionEvent arg0) {
			DejaVu.game.updateCardStatus(i, j);
			clicks++;
			
			try {
				cardButtons[i][j].setCard();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			for (Position temp : DejaVu.game.getDownCard())
				cardButtons[temp.getFirst()][temp.getSecond()].setBack();

			if (DejaVu.game.isEnd()) {
				DejaVuGUI.setNewMenu(new Congrat(clicks,DejaVu.game.getDim()));
			}
		}

	}

}
