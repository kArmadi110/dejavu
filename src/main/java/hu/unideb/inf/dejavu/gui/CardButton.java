package hu.unideb.inf.dejavu.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;

import hu.unideb.inf.dejavu.objects.Card;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CardButton extends JButton {
	double size;
	File card;


	CardButton(Card card, int dimension) {
		size = (1.0 / dimension) * 400;
		this.card = card.getValue();
		setPreferredSize(new Dimension((int) size, (int) size));
		
		setBorder(BorderFactory.createLineBorder(Color.gray,2));
		
		setBack();
	}

	void setBack() {
		
		ClassLoader cl = this.getClass().getClassLoader();
		
		ImageIcon imageIcon = new ImageIcon(cl.getResource("pictures/cards1/back2.png"));
		Image imageTemp = imageIcon.getImage().getScaledInstance((int) size,
				(int) size, Image.SCALE_DEFAULT);
		imageIcon = new ImageIcon(imageTemp);
		setIcon(imageIcon);

	}

	void setCard() {
		ImageIcon imageIcon = new ImageIcon(card.getPath());
		Image imageTemp = imageIcon.getImage().getScaledInstance((int) size,
				(int) size, Image.SCALE_DEFAULT);
		imageIcon = new ImageIcon(imageTemp);
		setIcon(imageIcon);
	}

}
