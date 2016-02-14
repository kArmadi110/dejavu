package hu.unideb.inf.dejavu.gui;

import hu.unideb.inf.dejavu.DejaVu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SignInMenu extends DVMenu {

	public SignInMenu(String args) {
		super();

		setHgap(10);
		setVgap(10);

		add(new DVText(args, Font.font("Verdana", FontWeight.BOLD, 20)), 3, 2);

		add(new DVText("Név:", Font.font("Verdana", 10)), 2, 4);
		TextField name = new TextField();
		name.setPromptText("Név");
		add(name, 3, 4);

		add(new DVText("Jelszó:", Font.font("Verdana", 10)), 2, 5);
		TextField pass = new TextField();
		pass.setPromptText("Jelszó");
		add(pass, 3, 5);

		final DVButton signIn = new DVButton(args, 1);

		signIn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {

				/*
				 * TODO: if args==bejelentkezés akkor bejelentkezünk else hiba
				 * else if args== új profil új profilt hozunk létre, valami nem
				 * klappol hiba validáljuk, hogy megfelelő karaktereket lehessen
				 * csak megadni valami más módot keresünk
				 */

				DejaVu.setNewMenu(new MainMenu());
				WelcomeMenu.signIn.setDisable(false);
				WelcomeMenu.newProfile.setDisable(false);

				((Stage) signIn.getScene().getWindow()).close();

			}
		});

		add(signIn, 8, 6);

	}

}
