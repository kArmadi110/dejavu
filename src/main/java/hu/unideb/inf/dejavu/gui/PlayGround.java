package hu.unideb.inf.dejavu.gui;

import hu.unideb.inf.dejavu.DejaVu;

public class PlayGround extends DVMenu {

	PlayGround() {
		super();

		setVgap(10);
		setHgap(10);

		DVButton back = new DVButton("Vissza", 1);

		back.setOnAction((arg0) -> {
			DejaVuGUI.setNewMenu(new MainMenu());
		});

		Pack pack = new Pack();

		add(pack, 3, 2);
		add(back, 1, 3);
		// timer

	}
}
