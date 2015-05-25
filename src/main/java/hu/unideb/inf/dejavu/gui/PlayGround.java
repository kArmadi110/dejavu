package hu.unideb.inf.dejavu.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import hu.unideb.inf.dejavu.DejaVu;

import javax.swing.JPanel;

public class PlayGround extends JPanel implements GameMenu {
	Button Back;
	JLabel timer;
	Timer t;
	Pack pakli;

	public PlayGround() {
		DejaVu.ground.game.setCards(Arrays.asList(DejaVu.ground.choser
				.getSelectedFiles()));

		init();

	}

	public PlayGround(boolean status) {

		DejaVu.ground.game.loadCards();
		init();

	}

	private void init() {

		DejaVu.ground.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		Back = new Button("Vissza", new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String[] options = { "Igen", "Nem" };
				int loadInfo = JOptionPane.showOptionDialog(PlayGround.this,
						"Mented a játékállást?", "Játékállás mentése.",
						JOptionPane.YES_NO_OPTION, JOptionPane.NO_OPTION, null,
						options, options[0]);
				if (loadInfo == 0) {
					t.stop();
					DejaVu.ground.game.saveCards();
				}
				DejaVu.ground.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				DejaVu.ground.setMenu(back());
			}
		});

		Back.setPreferredSize(new Dimension(150, 30));

		setLayout(new GridBagLayout());
		GridBagConstraints gb = new GridBagConstraints();

		gb.gridy = 0;
		gb.weightx = 2;
		gb.weighty = 0;
		gb.gridheight = 1;
		gb.gridwidth = 1;

		timer = new JLabel();
		timer.setFont(new Font("Courier New", Font.ITALIC, 20));
		add(timer, gb);
		gb.gridy++;

		pakli = new Pack();
		add(pakli, gb);

		gb.gridy++;
		gb.anchor = GridBagConstraints.LINE_START;
		gb.insets = new Insets(0, 50, 0, 0);
		add(Back, gb);

		// new Thread() {//indokolt?
		// public void run() {
		t = new Timer(100, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				//DejaVu.ground.game.timer.update();
				timer.setText(DejaVu.ground.game.timer.toString());
				if (DejaVu.ground.game.isEnd()) {
					DejaVu.ground
							.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					t.stop();
					DejaVu.ground.setMenu(new Win());
				}
			}
		});
		t.start();
		// }
		// }.start();
	}

	public GameMenu back() {
		return new Menu();
	}

	@Override
	public String getName() {
		return "PlayGround";
	}

}
