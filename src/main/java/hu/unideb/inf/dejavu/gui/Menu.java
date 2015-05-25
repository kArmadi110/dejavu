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

public class Menu extends JPanel implements GameMenu {
	Button Game;
	Button Settings;
	Button HighScores;
	Button Back;

	Menu() {
		Game = new Button("Játék", new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (DejaVu.ground.game.isSetDim() && DejaVu.ground.game.filesExist())
					DejaVu.ground.setMenu(new PlayGround());
				else 
					DejaVu.ground.setMenu(new Settings());
			}
		});
		Settings = new Button("Beállítások", new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				DejaVu.ground.setMenu(new Settings());
			}
		});
		HighScores = new Button("Ranglista", new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				DejaVu.ground.setMenu(new HighScores());
			}
		});
		Back = new Button("Vissza", new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				DejaVu.ground.game.setDim(0);
				DejaVu.ground.dimensionChoser.removeAllItems();
				DejaVu.ground.setMenu(back());
			}
		});

		Game.setPreferredSize(new Dimension(400, 50));
		Settings.setPreferredSize(new Dimension(400, 50));
		HighScores.setPreferredSize(new Dimension(400, 50));
		Back.setPreferredSize(new Dimension(150, 30));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints gb = new GridBagConstraints();
		gb.gridy = 0;
		gb.insets = new Insets(50, 0, 0, 0);

		buttonPanel.add(Game, gb);
		gb.gridy++;
		buttonPanel.add(Settings, gb);
		gb.gridy++;
		buttonPanel.add(HighScores, gb);
		setLayout(new GridBagLayout());
		GridBagConstraints gb2 = new GridBagConstraints();
		gb2.gridy = 0;
		gb2.anchor = GridBagConstraints.SOUTH;

		JLabel dejavu = new JLabel("DeJaVu");
		dejavu.setFont(new Font("Courier New", Font.ITALIC, 50));
		// gb2.gridheight=1;
		gb2.weightx = 0;
		add(dejavu, gb2);
		gb2.gridy++;
		gb2.gridx = 0;
		gb2.weightx = 2;
		add(buttonPanel, gb2);
		gb2.gridy++;
		gb2.gridx = 0;
		gb2.weightx = 2;
		gb2.anchor = GridBagConstraints.LINE_START;
		gb2.insets = new Insets(60, 50, 0, 0);

		add(Back, gb2);

	}

	public GameMenu back() {
		return new QuestionPanel();
	}

	public String getName() {
		return getClass().getName();
	}

}
