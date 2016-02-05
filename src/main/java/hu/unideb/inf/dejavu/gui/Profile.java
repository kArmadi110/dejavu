package hu.unideb.inf.dejavu.gui;

import hu.unideb.inf.dejavu.DejaVu;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Profile extends JDialog {
	JLabel dialog;
	JTextField userName;
	JPasswordField password;

	Profile(final QuestionPanel parent, String text, final boolean login) {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setSize(new Dimension(400, 130));
		setPreferredSize(new Dimension(400, 130));
		setResizable(false);
		parent.firstB.setEnabled(false);
		parent.secondB.setEnabled(false);
		parent.setEnabled(false);
		if (login)
			setTitle("Bejelentkezés");

		else
			setTitle("Új profil");
		JPanel panel = new JPanel(new GridBagLayout());
		setLayout(new GridBagLayout());

		dialog = new JLabel(text);

		GridBagConstraints gb1 = new GridBagConstraints(), gb2 = new GridBagConstraints();
		gb1.gridy = 0;
		gb2.gridy = 0;

		add(dialog, gb1);
		gb1.gridy++;

		panel.add(new JLabel("Felhasználónév:"), gb2);
		userName = new JTextField(15);
		panel.add(userName, gb2);

		gb2.gridy++;
		panel.add(new JLabel("Jelszó:"), gb2);
		password = new JPasswordField(15);
		panel.add(password, gb2);
		add(panel, gb1);
		gb1.gridy++;

		Button ok = new Button("OK", new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (login
						&& DejaVu.game.loadProfile(userName.getText(),
								new String(password.getPassword()))) {
					if (DejaVu.game.isStatusExist()) {
						String[] options = { "Igen", "Nem" };
						int loadInfo = JOptionPane.showOptionDialog(
								Profile.this,
								"Betöltöd a mentett játékállást?",
								"Mentés betöltése", JOptionPane.YES_NO_OPTION,
								JOptionPane.NO_OPTION, null, options,
								options[0]);
						if (loadInfo == 0 && DejaVu.game.filesExist()) {
							parent.firstB.setEnabled(true);
							parent.secondB.setEnabled(true);
							DejaVu.ground.setMenu(new PlayGround(true));
							dispose();
						} else {
							parent.firstB.setEnabled(true);
							parent.secondB.setEnabled(true);
							DejaVu.game.removeStatus();
							DejaVu.ground.setMenu(new Menu());
							dispose();
						}
					} else {
						parent.firstB.setEnabled(true);
						parent.secondB.setEnabled(true);
						DejaVu.ground.setMenu(new Menu());
						dispose();
					}

				} else if (!login
						&& DejaVu.game.addProfile(userName.getText(),
								new String(password.getPassword()))) {

					parent.firstB.setEnabled(true);
					parent.secondB.setEnabled(true);
					DejaVu.ground.setMenu(new Menu());
					dispose();
				} else {
					JOptionPane.showMessageDialog(Profile.this,
							"Nem megfelelő név vagy jelszó.", "Hiba",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});

		Button cancel = new Button("Cancel", new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				parent.ready = true;
				parent.firstB.setEnabled(true);
				parent.secondB.setEnabled(true);
				dispose();
			}
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(ok);
		buttonPanel.add(cancel);
		add(buttonPanel, gb1);

		setLocationRelativeTo(parent);
		setAlwaysOnTop(true);
		setVisible(true);

	}

}
