package hu.unideb.inf.dejavu.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;

class ToolbarButtons extends HBox {
	public Button closeButton = new Button("X");
	public Button downButton = new Button("_");

	ToolbarButtons(final Stage stage) {

		closeButton.setId("close");

		closeButton.getStylesheets().add(getClass().getClassLoader().getResource("TopMenu.css").toExternalForm());

		closeButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				Platform.exit();
			}
		});

		this.getChildren().add(closeButton);

		downButton.setId("minimize");

		downButton.getStylesheets().add(getClass().getClassLoader().getResource("TopMenu.css").toExternalForm());

		downButton.setOnAction((arg0) -> {
			stage.setIconified(true);
			// iconified bug
			// https://bugs.openjdk.java.net/browse/JDK-8095451
		});

		this.getChildren().add(downButton);

	}
}

public class ExitToolbar extends ToolBar {
	public static int height = 20;
	public static double Xc = 0, Yc = 0;
	public static ToolbarButtons toolbarButtons;

	public ExitToolbar(final Stage stage) {
		super();

		setPrefHeight(height);
		setMinHeight(height);
		setMaxHeight(height);
		toolbarButtons = new ToolbarButtons(stage);

		getItems().add(toolbarButtons);

		setOnMousePressed((event) -> {
			Xc = event.getSceneX();
			Yc = event.getSceneY();
		});

		setOnMouseDragged((event) -> {
			stage.setX(event.getScreenX() - Xc);
			stage.setY(event.getScreenY() - Yc);
		});

		setId("topBar");
		getStylesheets().add(getClass().getClassLoader().getResource("TopMenu.css").toExternalForm());

	}

}
