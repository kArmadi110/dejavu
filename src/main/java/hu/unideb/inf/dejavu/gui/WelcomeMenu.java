package hu.unideb.inf.dejavu.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WelcomeMenu extends DVMenu {

	public static DVButton signIn = new DVButton("Bejelentkezés", 1);//ok így statikusan?
	public static DVButton newProfile = new DVButton("Új profil", 1);

	public WelcomeMenu() {
		super();
		final int height = 200, width = 500;
		setHgap(10);
		setVgap(10);

		signIn.setOnAction((arg0) -> {
			signIn.setDisable(true);
			newProfile.setDisable(true);

			final Stage stage = new Stage();

			SignInMenu signIn = new SignInMenu("Bejelentkezés");

			ExitToolbar exit = new ExitToolbar(stage);
			exit.toolbarButtons.closeButton
					.setOnAction(new EventHandler<ActionEvent>() {

						public void handle(ActionEvent arg0) {
							WelcomeMenu.signIn.setDisable(false);
							WelcomeMenu.newProfile.setDisable(false);
							stage.close();

						}
					});

			signIn.setTop(exit);

			stage.initStyle(StageStyle.TRANSPARENT);
			Scene scene = new Scene(signIn, width, height);
			scene.setFill(Color.TRANSPARENT);

			stage.setMaxHeight(height);
			stage.setMinHeight(height);
			stage.setMaxWidth(width);
			stage.setMinWidth(width);
			stage.setAlwaysOnTop(true);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		});

		newProfile.setOnAction((arg0) -> {
			signIn.setDisable(true);
			newProfile.setDisable(true);

			final Stage stage = new Stage();

			SignInMenu signIn = new SignInMenu("Új profil");

			ExitToolbar exit = new ExitToolbar(stage);
			exit.toolbarButtons.closeButton
					.setOnAction(new EventHandler<ActionEvent>() {

						public void handle(ActionEvent arg0) {
							WelcomeMenu.signIn.setDisable(false);
							WelcomeMenu.newProfile.setDisable(false);
							stage.close();

						}
					});

			signIn.setTop(exit);

			stage.initStyle(StageStyle.TRANSPARENT);
			Scene scene = new Scene(signIn, width, height);
			scene.setFill(Color.TRANSPARENT);

			stage.setMaxHeight(height);
			stage.setMinHeight(height);
			stage.setMaxWidth(width);
			stage.setMinWidth(width);
			stage.setAlwaysOnTop(true);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		});

		DVText welcome = new DVText("      Üdvözöljük", Font.font("Verdana",
				FontWeight.BOLD, 30));

		DVText description = new DVText(
				"Jelenktezzen be vagy hozzon létre új profilt.", Font.font(
						"Verdana", 15));

		add(welcome, 8, 25);
		add(description, 8, 27);
		add(signIn, 5, 30);
		add(newProfile, 12, 30);

	}
}
