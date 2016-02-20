package hu.unideb.inf.dejavu.gui;

import hu.unideb.inf.dejavu.DejaVu;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SlidePane extends GridPane {
	private double size;

	public SlidePane(double size, Button settingButton, Button highScoreButton) {
		setHgap(10);
		setVgap(10);
		setPrefWidth(0);
		setMinWidth(0);

		setSize(size);
		setVisible(false);
		setId("slidePane");
		getStylesheets().add(
				DVMenu.class.getResource("SlidePane.css").toExternalForm());

		settingButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent actionEvent) {
				Animation closeAnim = new Transition() {
					{
						setCycleDuration(Duration.millis(500));
					}

					@Override
					protected void interpolate(double arg0) {
						setPrefWidth((getSize()) * (1 - arg0));
					}
				};

				closeAnim.onFinishedProperty().set(
						new EventHandler<ActionEvent>() {

							public void handle(ActionEvent arg0) {
								setVisible(false);
								getChildren().clear();
							}
						});

				Animation openAnim = new Transition() {
					{
						setCycleDuration(Duration.millis(500));
					}

					@Override
					protected void interpolate(double arg0) {
						setPrefWidth(arg0 * getSize());
					}
				};

				openAnim.onFinishedProperty().set(
						new EventHandler<ActionEvent>() {

							public void handle(ActionEvent arg0) {
								setVisible(true);
							}
						});

				if (openAnim.statusProperty().get() == Animation.Status.STOPPED
						&& closeAnim.statusProperty().get() == Animation.Status.STOPPED) {
					if (isVisible()) {
						closeAnim.play();

					} else {
						add(new DVText("Beállítások", Font.font("Verdana", 22)),
								4, 3);

						DVButton chooser = new DVButton("Tallózás", 1);
						chooser.setOnAction(new EventHandler<ActionEvent>() {

							public void handle(ActionEvent arg0) {
								DejaVu.fileChooser
										.showOpenMultipleDialog((Stage) getScene()
												.getWindow());
							}
						});

						//TODO: dimensionChoser
						add(chooser, 5, 4);

						setVisible(true);
						openAnim.play();
					}
				}

			}
		});

		highScoreButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent actionEvent) {
				Animation closeAnim = new Transition() {
					{
						setCycleDuration(Duration.millis(500));
					}

					@Override
					protected void interpolate(double arg0) {
						setPrefWidth(getSize() * (1 - arg0));
					}
				};

				closeAnim.onFinishedProperty().set(
						new EventHandler<ActionEvent>() {

							public void handle(ActionEvent arg0) {
								setVisible(false);
								getChildren().clear();
							}
						});

				Animation openAnim = new Transition() {
					{
						setCycleDuration(Duration.millis(500));
					}

					@Override
					protected void interpolate(double arg0) {
						setPrefWidth(arg0 * getSize());
					}
				};

				openAnim.onFinishedProperty().set(
						new EventHandler<ActionEvent>() {

							public void handle(ActionEvent arg0) {
								setVisible(true);
							}
						});

				if (openAnim.statusProperty().get() == Animation.Status.STOPPED
						&& closeAnim.statusProperty().get() == Animation.Status.STOPPED) {
					if (isVisible()) {
						closeAnim.play();
					} else {
						add(new DVText("Eredménytábla", Font
								.font("Verdana", 22)), 4, 3);
						// TODO: táblázatot hozzáadni
						setVisible(true);
						openAnim.play();
					}
				}

			}
		});

	}

	public double getSize() {
		return size;
	}

	public void setHighScore() {

	}

	public void setSize(double size) {
		this.size = size;
	}

}