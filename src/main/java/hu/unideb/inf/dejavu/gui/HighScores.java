package hu.unideb.inf.dejavu.gui;

import hu.unideb.inf.dejavu.DejaVu;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

public class HighScores extends JPanel implements GameMenu {

	public HighScores() {
		JButton Back = new Button("Vissza", new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				DejaVu.ground.setMenu(back());
			}
		});
		TreeMap<String, String> highScoreMap = DejaVu.game
				.getHighScores();
		Object[][] value = new Object[highScoreMap.size()][3];

		int no = 0;
		for (Entry<String, String> entry : highScoreMap.entrySet()) {
			value[no][0] = no + 1;
			value[no][1] = entry.getValue();
			value[no][2] = entry.getKey();
			no++;
			if (no > 9)
				break;
		}

		String[] head = { "No", "Név", "Idő" };
		JTable highScores = new JTable(value, head);
		highScores.setAutoResizeMode(highScores.AUTO_RESIZE_ALL_COLUMNS);
		highScores.setRowHeight(40);
		highScores.setPreferredSize(new Dimension(400, 400));
		highScores.setFont(new Font("Courier New", Font.ITALIC, 20));

		GridBagConstraints gb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		gb.gridy = 0;
		gb.weightx = 2;
		add(highScores, gb);
		gb.gridy++;

		gb.anchor = GridBagConstraints.LAST_LINE_START;
		gb.insets = new Insets(20, 50, 0, 0);
		Back.setPreferredSize(new Dimension(150, 30));
		add(Back, gb);

	}

	public GameMenu back() {

		return new Menu();
	}

}
