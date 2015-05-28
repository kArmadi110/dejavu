package hu.unideb.inf.dejavu.gui;

import hu.unideb.inf.dejavu.DejaVu;
import hu.unideb.inf.dejavu.objects.Position;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class Pack extends JPanel {
	CardButton[][] cardButtons;

	Pack() {
		setLayout(new GridBagLayout());
		GridBagConstraints gb = new GridBagConstraints();
		gb.gridy = 0;

		cardButtons = new CardButton[DejaVu.ground.game.getDim()][DejaVu.ground.game
				.getDim()];

		for (int i = 0; i < DejaVu.ground.game.getDim(); i++) {
			for (int j = 0; j < DejaVu.ground.game.getDim(); j++)
				cardButtons[i][j] = new CardButton(DejaVu.ground.game.getCard(
						i, j), DejaVu.ground.game.getDim());
		}

		for (int i = 0; i < DejaVu.ground.game.getDim(); i++) {
			JPanel panelTemp = new JPanel();
			for (int j = 0; j < DejaVu.ground.game.getDim(); j++) {
				cardButtons[i][j]
						.addActionListener(new cardActionListener(i, j));

				panelTemp.add(cardButtons[i][j]);
				if(!DejaVu.ground.game.getCard(i, j).isClicked())
					cardButtons[i][j].setCard();
				else
					cardButtons[i][j].setBack();
			}
			add(panelTemp, gb);
			gb.gridy++;
		}
	}

	class cardActionListener implements ActionListener {
		int i, j;

		cardActionListener(int i, int j) {
			this.i = i;
			this.j = j;
		}

		public void actionPerformed(ActionEvent e) {
			DejaVu.ground.game.updateCardStatus(i, j);
			
			cardButtons[i][j].setCard();
			
			for (Position temp : DejaVu.ground.game.getDownCard())
				cardButtons[temp.getFirst()][temp.getSecond()].setBack();
		}
	}
}