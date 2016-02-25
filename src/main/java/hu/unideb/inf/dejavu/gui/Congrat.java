package hu.unideb.inf.dejavu.gui;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Congrat extends DVMenu {

	public Congrat() {
		super();

		DVText grat = new DVText("Ön nyert!", Font.font("Verdana",
				FontWeight.BOLD, 30));
		
		setHgap(300);
		setVgap(250);
		add(grat,1,1);
	}
}
