package hu.unideb.inf.dejavu.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class QuestionPanel extends JPanel implements GameMenu {
	ActionListener firstA = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			profile = new Profile(QuestionPanel.this, "Bejelentkezés", true);

		}
	};

	ActionListener secondA = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			profile = new Profile(QuestionPanel.this, "Új profil létrehozása.",
					false);
		}
	};

	Button firstB;
	Button secondB;
	JLabel dialog;
	Profile profile;
	boolean ready;

	public QuestionPanel() {

		firstB = new Button("Bejelentkezés", firstA);
		secondB = new Button("Új profil", secondA);
		dialog = new JLabel("Bejelentkezik, vagy új profilt készít?");
		firstB.setPreferredSize(new Dimension(200,
				firstB.getPreferredSize().height));
		secondB.setPreferredSize(new Dimension(200,
				secondB.getPreferredSize().height));

		dialog.setFont(new Font("Courier New", Font.ITALIC, 20));

		JPanel top = new JPanel();
		JPanel bottom = new JPanel();
		top.add(dialog);
		bottom.add(firstB);
		bottom.add(secondB);

		setLayout(new GridBagLayout());
		GridBagConstraints gl = new GridBagConstraints();

		gl.gridy = 0;
		add(top, gl);

		gl.gridy++;
		add(bottom, gl);

	}

	public GameMenu back() {
		return new QuestionPanel();
	}

	@Override
	public String getName() {
		return getClass().getName();
	}

}
