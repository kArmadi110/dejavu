package hu.unideb.inf.dejavu.gui;

import hu.unideb.inf.dejavu.DejaVu;
import hu.unideb.inf.dejavu.objects.User;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SignInMenu extends DVMenu {

	final int height = 200, width = 500;

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

			if (args.equals("Bejelentkezés") && DejaVu.game.loadProfile(new User(name.getText(), pass.getText()))) {

				if (DejaVu.game.isStatusExist()) {

					final Stage stage = new Stage();

					QuestionMenu loadState = new QuestionMenu("Szeretné betölteni a játékmentést?", "Igen", "Nem",
							() -> {
								DejaVu.game.loadStatus();
								stage.close();
							}, () -> {
								DejaVu.game.removeStatus();
								stage.close();
							});

					ExitToolbar exit = new ExitToolbar(stage);
					exit.toolbarButtons.closeButton.setOnAction((arg1) -> {
						stage.close();
					});

					loadState.setTop(exit);

					stage.initStyle(StageStyle.TRANSPARENT);
					Scene scene = new Scene(loadState, width, height);
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
				DejaVuGUI.setNewMenu(new MainMenu());

				WelcomeMenu.signIn.setDisable(false);
				WelcomeMenu.newProfile.setDisable(false);

				((Stage) getScene().getWindow()).close();

			} else if (args.equals("Új profil") && DejaVu.game.addProfile(name.getText(), pass.getText())) {

				DejaVuGUI.setNewMenu(new MainMenu());
				WelcomeMenu.signIn.setDisable(false);
				WelcomeMenu.newProfile.setDisable(false);

				((Stage) getScene().getWindow()).close();

			} else {
				DVText message = new DVText("Hibás név vagy jelszó!", Font.font("Verdana", 14));
				message.setFill(Color.RED);
				add(message, 8, 5);
			}
		});

		add(signIn, 8, 6);

	}
}
