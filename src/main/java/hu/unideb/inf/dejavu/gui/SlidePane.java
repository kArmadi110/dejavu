package hu.unideb.inf.dejavu.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

import hu.unideb.inf.dejavu.DejaVu;
import hu.unideb.inf.dejavu.Game;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SlidePane extends GridPane {
	private double size;

	List<File> cardPathList = new ArrayList<File>();

	List<Integer> dimensions = Game.matrixSize(cardPathList.size());

	ObservableList<String> result = FXCollections.observableArrayList();
	Animation closeAnim, openAnim;

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
				closeAnim = new Transition() {
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

				openAnim = new Transition() {
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
								cardPathList = DejaVu.fileChooser
										.showOpenMultipleDialog((Stage) getScene()
												.getWindow());

								dimensions = Game.matrixSize(cardPathList
										.size());

								if (!dimensions.isEmpty()) {
									for (int i = dimensions.size() - 1; i >= 0; i--)
										result.add(dimensions.get(i) + "X"
												+ dimensions.get(i));
								}

								DejaVu.dimensionChoser.setItems(result);

							}
						});

						add(chooser, 5, 4);
						add(DejaVu.dimensionChoser, 5, 5);
						setVisible(true);
						openAnim.play();
					}
				}

			}
		});

		highScoreButton.setOnAction(new EventHandler<ActionEvent>() {
			@SuppressWarnings("unchecked")
			public void handle(ActionEvent actionEvent) {
				closeAnim = new Transition() {
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

				openAnim = new Transition() {
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

						TableView<Player> table = new TableView<Player>();
						table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

						table.getStylesheets().add(
								SlidePane.class.getResource("Table.css")
										.toExternalForm());

						ObservableList<Player> data = FXCollections
								.observableArrayList();

						TreeMap<String, String> highScoreMap = DejaVu.game
								.getHighScores();

						int i = 1;
						for (Entry<String, String> entry : highScoreMap
								.entrySet()) {
							data.add(new Player(i, entry.getKey(), entry
									.getValue()));
							i++;
							if (i > 10)
								break;
						}

						table.setItems(data);

						TableColumn<Player, Integer> number = new TableColumn<Player, Integer>(
								"Sorszám");
						TableColumn<Player, String> name = new TableColumn<Player, String>(
								"Név");
						TableColumn<Player, String> time = new TableColumn<Player, String>(
								"Idő");

						number.setCellValueFactory(new PropertyValueFactory<Player, Integer>(
								"number"));
						name.setCellValueFactory(new PropertyValueFactory<Player, String>(
								"name"));
						time.setCellValueFactory(new PropertyValueFactory<Player, String>(
								"time"));

						table.getColumns().addAll(number, name, time);

						add(table, 5, 4);

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

	public void setSize(double size) {
		this.size = size;
	}

}