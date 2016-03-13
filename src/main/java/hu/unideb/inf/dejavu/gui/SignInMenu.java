package hu.unideb.inf.dejavu.gui;

import hu.unideb.inf.dejavu.DejaVu;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SignInMenu extends DVMenu {

	public SignInMenu(final String args) {
		super();

		setHgap(10);
		setVgap(10);

		add(new DVText(args, Font.font("Verdana", FontWeight.BOLD, 20)), 3, 2);

		add(new DVText("Név:", Font.font("Verdana", 10)), 2, 4);
		final TextField name = new TextField();
		name.setPromptText("Név");
		add(name, 3, 4);

		add(new DVText("Jelszó:", Font.font("Verdana", 10)), 2, 5);
		final PasswordField pass = new PasswordField();
		pass.setPromptText("Jelszó");
		add(pass, 3, 5);

		final DVButton signIn = new DVButton(args, 1);

		signIn.setOnAction((arg0) -> {

			if (args.equals("Bejelentkezés") && DejaVu.game.loadProfile(name.getText(), pass.getText())) {

				boolean load = false;
				if (DejaVu.game.isStatusExist()) {
					// TODO: állás betöltése
				}
				if (load && DejaVu.game.filesExist()) {
					DejaVu.setNewMenu(new MainMenu());

				} else {
					DejaVu.game.removeStatus();
					DejaVu.setNewMenu(new MainMenu());
				}

				WelcomeMenu.signIn.setDisable(false);
				WelcomeMenu.newProfile.setDisable(false);

				((Stage) signIn.getScene().getWindow()).close();

			} else if (args.equals("Új profil") && DejaVu.game.addProfile(name.getText(), pass.getText())) {

				DejaVu.setNewMenu(new MainMenu());
				WelcomeMenu.signIn.setDisable(false);
				WelcomeMenu.newProfile.setDisable(false);

				((Stage) signIn.getScene().getWindow()).close();

			} else {
				DVText message = new DVText("Hibás név vagy jelszó!", Font.font("Verdana", 14));
				message.setFill(Color.RED);
				add(message, 8, 5);
			}
		});

		add(signIn, 8, 6);

	}
}
