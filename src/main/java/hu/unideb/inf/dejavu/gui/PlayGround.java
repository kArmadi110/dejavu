package hu.unideb.inf.dejavu.gui;

import java.util.Timer;
import java.util.TimerTask;

import hu.unideb.inf.dejavu.controller.DejaVu;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PlayGround extends DVMenu {

	static DVButton back = new DVButton("Vissza", 1);
	final int height = 200, width = 500;

	PlayGround() {
		super();
		
		ExitToolbar exitToolbar = new ExitToolbar(DejaVuGUI.stage);

		exitToolbar.toolbarButtons.closeButton.setOnAction((arg0) -> {

			back.setDisable(true);

			final Stage stage = new Stage();

			QuestionMenu loadState = new QuestionMenu("Szeretné menteni a játékállást?", "Igen", "Nem", () -> {
				DejaVu.game.saveStatus();
				Platform.exit();
			}, () -> {
				Platform.exit();
			});

			ExitToolbar exit = new ExitToolbar(stage);
			exit.toolbarButtons.closeButton.setOnAction((arg1) -> {
				PlayGround.back.setDisable(false);
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
		});

		setTop(exitToolbar);

		setVgap(10);
		setHgap(10);

		back.setOnAction((arg0) -> {
			back.setDisable(true);

			final Stage stage = new Stage();

			QuestionMenu state = new QuestionMenu("Szeretné menteni a játékállást?", "Igen", "Nem", () -> {
				DejaVu.game.saveStatus();
				back.setDisable(false);
				DejaVuGUI.setNewMenu(new MainMenu());
				stage.close();
			}, () -> {
				back.setDisable(false);
				DejaVuGUI.setNewMenu(new MainMenu());
				stage.close();
			});

			ExitToolbar exit = new ExitToolbar(stage);
			exit.toolbarButtons.closeButton.setOnAction((arg1) -> {
				PlayGround.back.setDisable(false);
				stage.close();
			});

			state.setTop(exit);

			stage.initStyle(StageStyle.TRANSPARENT);
			Scene scene = new Scene(state, width, height);
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

		ButtonPack pack = new ButtonPack();

		DVText time = new DVText(DejaVu.game.mainStatus.getTime().toString(),
				Font.font("Verdana", FontWeight.BOLD, 14));

		new Timer().scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				Platform.runLater(() -> {
					time.setText(DejaVu.game.mainStatus.getTime().toString());
				});
			}
		}, 1000, 1000);

		add(pack, 7, 4);
		add(back, 1, 6);
		add(time, 20, 2);

	}
}
