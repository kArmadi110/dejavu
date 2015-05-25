package hu.unideb.inf.dejavu.gui;

import hu.unideb.inf.dejavu.DejaVu;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Win extends JPanel implements GameMenu {
	Button back;

	Win() {
		DejaVu.ground.game.updateHighScores();
		JLabel Win = new JLabel("Ön nyert!");
		Win.setFont(new Font("Courier New", Font.ITALIC, 50));

		GridBagConstraints gb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		gb.gridy = 0;
		gb.weightx = 2;
		add(Win, gb);
		gb.gridy++;
		back = new Button("Vissza", new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				DejaVu.ground.setMenu(back());
			}
		});
		gb.anchor = GridBagConstraints.LAST_LINE_START;
		gb.insets = new Insets(360, 50, 0, 0);
		back.setPreferredSize(new Dimension(150, 30));
		add(back, gb);
	}

	public GameMenu back() {
		return new Menu();
	}

}
