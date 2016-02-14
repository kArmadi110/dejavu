package hu.unideb.inf.dejavu.gui;

import javafx.scene.control.Button;

public class DVButton extends Button {

	DVButton(String text, int horizOrVertc) {
		super();

		setText(text);

		if (horizOrVertc == 0)// 0 fuggoleges, else vizszintes
			getStylesheets().add(
					DVButton.class.getResource("Button.css").toExternalForm());
		else
			getStylesheets().add(
					DVButton.class.getResource("ButtonTop.css")
							.toExternalForm());
	}

}
