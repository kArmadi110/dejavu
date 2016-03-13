package hu.unideb.inf.dejavu.gui;

import java.io.File;
import java.net.MalformedURLException;

import hu.unideb.inf.dejavu.objects.Card;
import javafx.scene.control.Button;

public class CardButton extends Button {

	File card;
	int size;

	boolean click = false;

	public CardButton(Card card, int dimension) {
		super();

		size = (int) ((1.0 / dimension) * 400);
		this.card = card.getValue();
		setPrefSize(size, size);

		setBack();
	}

	public void setBack() {
		setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #15640F, #000000, #15640F);");
	}

	public void setCard() throws MalformedURLException {
		setStyle("-fx-background-image: url('"
				+ card.toURI().toURL().toString() + "');"
				+ "-fx-background-size:" + size + " " + size + ";");

	}

}
