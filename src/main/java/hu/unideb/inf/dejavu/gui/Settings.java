package hu.unideb.inf.dejavu.gui;

import hu.unideb.inf.dejavu.Game;
import hu.unideb.inf.dejavu.DejaVu;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.JPanel;

public class Settings extends JPanel implements GameMenu {
	Button Back;
	Button choserButton;
	Button game;

	Settings() {
		Back = new Button("Vissza", new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				DejaVu.ground.setMenu(back());
			}
		});

		choserButton = new Button("Tallózás", new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				DejaVu.ground.dimensionChoser.removeAllItems();
				DejaVu.ground.choser.setMultiSelectionEnabled(true);
				DejaVu.ground.choser.showOpenDialog(Settings.this);

				List<Integer> dimensions = Game.matrixSize(DejaVu.ground.choser
						.getSelectedFiles().length);

				if (!dimensions.isEmpty()) {
					for (int i = 0; i < dimensions.size(); i++)
						DejaVu.ground.dimensionChoser.addItem(dimensions.get(i)
								+ "X" + dimensions.get(i));
					String result = (String) DejaVu.ground.dimensionChoser
							.getItemAt(0);
					DejaVu.ground.game.setDim(Integer.parseInt(result
							.substring(0, 1)));
				}
			}
		});
		game = new Button("Játék", new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (DejaVu.ground.game.isSetDim())
					DejaVu.ground.setMenu(new PlayGround());
			}
		});

		DejaVu.ground.dimensionChoser.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 2) {
					String result = (String) DejaVu.ground.dimensionChoser
							.getSelectedItem();
					if (result != null)
						DejaVu.ground.game.setDim(Integer.parseInt(result
								.substring(0, 1)));
				}
			}
		});

		Back.setPreferredSize(new Dimension(150, 30));
		choserButton.setPreferredSize(new Dimension(150, 30));
		DejaVu.ground.dimensionChoser.setPreferredSize(new Dimension(300, 30));
		game.setPreferredSize(new Dimension(150, 30));
		setLayout(new GridBagLayout());
		GridBagConstraints gb = new GridBagConstraints();

		gb.gridy = 0;
		gb.weightx = 2;
		gb.gridx = 0;
		add(choserButton, gb);
		gb.gridy++;
		gb.insets = new Insets(10, 0, 0, 0);
		add(DejaVu.ground.dimensionChoser, gb);

		gb.anchor = GridBagConstraints.LINE_START;
		gb.insets = new Insets(350, 50, 0, 0);
		gb.gridy++;
		JPanel backAndGamePanel = new JPanel();
		backAndGamePanel.setLayout(new GridBagLayout());
		backAndGamePanel.add(Back);
		backAndGamePanel.add(Box.createHorizontalStrut(5));
		backAndGamePanel.add(game);

		add(backAndGamePanel, gb);

	}

	public GameMenu back() {
		return new Menu();
	}

	public String getName() {
		return "Settings";
	}

}
