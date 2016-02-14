package hu.unideb.inf.dejavu.gui;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class DVText extends Text {
	public DVText(String args, Font font) {
		super(args);
		setId("welcome");
		setFont(font);
		setFill(Color.WHITE);

		DropShadow drop = new DropShadow();
		drop.setOffsetY(5.0f);
		drop.setColor(Color.rgb(21, 100, 15));

		setEffect(drop);

	}
}
