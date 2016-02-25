package hu.unideb.inf.dejavu.gui;

import hu.unideb.inf.dejavu.DejaVu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

			if (args.equals("Bejelentkezés")
					&& DejaVu.game.loadProfile(name.getText(), pass.getText())) {

				boolean load = false;
				if (DejaVu.game.isStatusExist()) {
					
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

			} else if (args.equals("Új profil")
					&& DejaVu.game.addProfile(name.getText(), pass.getText())) {

				DejaVu.setNewMenu(new MainMenu());
				WelcomeMenu.signIn.setDisable(false);
				WelcomeMenu.newProfile.setDisable(false);

				((Stage) signIn.getScene().getWindow()).close();

			} else {
				final Stage stage = new Stage();
				final int width = 250, height = 130;

				DVMenu error = new DVMenu();
				error.setHgap(5);
				error.setVgap(5);

				ExitToolbar exit = new ExitToolbar(stage);
				ExitToolbar.toolbarButtons.closeButton
						.setOnAction(new EventHandler<ActionEvent>() {

							public void handle(ActionEvent arg0) {
								WelcomeMenu.signIn.setDisable(false);
								WelcomeMenu.newProfile.setDisable(false);
								stage.close();

							}
						});

				final DVButton OK = new DVButton("OK", 1);

				OK.setOnAction(new EventHandler<ActionEvent>() {

					public void handle(ActionEvent arg0) {
						((Stage) OK.getScene().getWindow()).close();
					}
				});

				DVText message = new DVText(
						"Hibás felhasználónév vagy jelszó!", Font.font(
								"Verdana", 14));

				error.setTop(exit);

				error.add(message, 1, 1);
				error.add(OK, 1, 10);

				stage.initStyle(StageStyle.TRANSPARENT);
				Scene scene = new Scene(error, width, height);
				scene.setFill(Color.TRANSPARENT);

				stage.setMaxHeight(height);
				stage.setMinHeight(height);
				stage.setMaxWidth(width);
				stage.setMinWidth(width);
				stage.setAlwaysOnTop(true);
				stage.setScene(scene);
				stage.centerOnScreen();
				stage.show();

			}
		});

		add(signIn, 8, 6);

	}
}
