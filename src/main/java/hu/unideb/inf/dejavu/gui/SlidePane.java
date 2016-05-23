package hu.unideb.inf.dejavu.gui;

import java.util.List;

import hu.unideb.inf.dejavu.controller.DejaVu;
import hu.unideb.inf.dejavu.controller.Game;
import hu.unideb.inf.dejavu.objects.Achievement;
import hu.unideb.inf.dejavu.objects.HighScoreRecord;
import hu.unideb.inf.dejavu.objects.HighScoreTable;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SlidePane extends GridPane {
	private double size;

	List<Integer> dimensions = Game.matrixSize(DejaVuGUI.cardPathList.size());

	ObservableList<String> result = FXCollections.observableArrayList();

	Animation closeAnim, openAnim;

	public SlidePane(double size, Button settingButton, Button highScoreButton, Button achievements) {
		setHgap(10);
		setVgap(10);
		setPrefWidth(0);
		setMinWidth(0);

		setSize(size);
		setVisible(false);
		setId("slidePane");
		getStylesheets().add(getClass().getClassLoader().getResource("SlidePane.css").toExternalForm());

		closeAnim = new Transition() {
			{
				setCycleDuration(Duration.millis(500));
			}

			@Override
			protected void interpolate(double arg0) {
				setPrefWidth(getSize() * (1 - arg0));
			}
		};

		closeAnim.onFinishedProperty().set((arg1) -> {
			setVisible(false);
			getChildren().clear();
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

		settingButton.setOnAction((arg0) -> {
			if (openAnim.statusProperty().get() == Animation.Status.STOPPED
					&& closeAnim.statusProperty().get() == Animation.Status.STOPPED) {
				if (isVisible()) {
					closeAnim.play();

				} else {
					add(new DVText("Beállítások", Font.font("Verdana", 22)), 4, 3);

					DVButton chooser = new DVButton("Tallózás", 1);
					chooser.setOnAction((arg1) -> {
						ButtonPack.clicks = 0;
						DejaVuGUI.dimensionChoser.getItems().clear();
						dimensions.clear();
						
						DejaVuGUI.cardPathList = DejaVuGUI.fileChooser
								.showOpenMultipleDialog((Stage) getScene().getWindow());

						if (DejaVuGUI.cardPathList != null && !DejaVuGUI.cardPathList.isEmpty())
							dimensions = Game.matrixSize(DejaVuGUI.cardPathList.size());
						if (!dimensions.isEmpty()) {

							for (int i = dimensions.size() - 1; i >= 0; i--)
								result.add(dimensions.get(i) + "X" + dimensions.get(i));
						}

						DejaVuGUI.dimensionChoser.setItems(result);
					});

					add(chooser, 5, 4);
					add(DejaVuGUI.dimensionChoser, 5, 5);
					setVisible(true);
					openAnim.play();
				}
			}

		});

		highScoreButton.setOnAction((arg0) -> {
			if (openAnim.statusProperty().get() == Animation.Status.STOPPED
					&& closeAnim.statusProperty().get() == Animation.Status.STOPPED) {

				if (isVisible()) {
					closeAnim.play();
				} else {
					add(new DVText("Eredménytábla", Font.font("Verdana", 22)), 4, 3);

					TableView<Player> table = new TableView<Player>();
					table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

					table.getStylesheets().add(getClass().getClassLoader().getResource("Table.css").toExternalForm());

					ObservableList<Player> data = FXCollections.observableArrayList();

					ComboBox<String> timeOrClick = new ComboBox<String>();
					ObservableList<String> list = FXCollections.observableArrayList();
					list.addAll("Idő", "Kattintás");
					timeOrClick.setItems(list);
					timeOrClick.setId("dimensionChoser");
					timeOrClick.getStylesheets().add(
							WelcomeMenu.class.getClassLoader().getResource("DimensionChoser.css").toExternalForm());

					ComboBox<String> dimensionChoser = new ComboBox<String>();
					ObservableList<String> list2 = FXCollections.observableArrayList();
					list2.addAll("2x2", "4x4", "6x6");
					dimensionChoser.setItems(list2);
					dimensionChoser.setId("dimensionChoser");
					dimensionChoser.getStylesheets().add(
							WelcomeMenu.class.getClassLoader().getResource("DimensionChoser.css").toExternalForm());

					timeOrClick.setOnAction((arg1) -> {
						if (!dimensionChoser.getSelectionModel().isEmpty()) {// TODO:
																				// REFACTOR
							table.getItems().clear();
							if (timeOrClick.getValue().equals(timeOrClick.getItems().get(0))) {
								HighScoreTable hs = DejaVu.game.getHighScoresByTime(dimensionChoser.getValue());
								int i = 1;
								for (HighScoreRecord p : hs.getTable()) {
									data.add(new Player(i, p.getName(), p.getTime()));
									i++;
									if (i > 10)
										break;
								}
							} else if (timeOrClick.getValue().equals(timeOrClick.getItems().get(1))) {// second
								HighScoreTable hs = DejaVu.game.getHighScoresByClicks(dimensionChoser.getValue());
								int i = 1;
								for (HighScoreRecord p : hs.getTable()) {
									data.add(new Player(i, p.getName(), Integer.toString(p.getClicks())));
									i++;
									if (i > 10)
										break;
								}
							}

						}
					});

					dimensionChoser.setOnAction((arg1) -> {
						if (!timeOrClick.getSelectionModel().isEmpty()) {// TODO:
																			// REFACTOR
							table.getItems().clear();
							if (timeOrClick.getValue().equals(timeOrClick.getItems().get(0))) {
								HighScoreTable hs = DejaVu.game.getHighScoresByTime(dimensionChoser.getValue());
								int i = 1;
								for (HighScoreRecord p : hs.getTable()) {
									data.add(new Player(i, p.getName(), p.getTime()));
									i++;
									if (i > 10)
										break;
								}
							} else if (timeOrClick.getValue().equals(timeOrClick.getItems().get(1))) {// second
								HighScoreTable hs = DejaVu.game.getHighScoresByClicks(dimensionChoser.getValue());
								int i = 1;
								for (HighScoreRecord p : hs.getTable()) {
									data.add(new Player(i, p.getName(), Integer.toString(p.getClicks())));
									i++;
									if (i > 10)
										break;
								}
							}
						}
					});

					add(dimensionChoser, 4, 4);
					add(timeOrClick, 4, 5);

					table.setItems(data);

					TableColumn<Player, Integer> number = new TableColumn<Player, Integer>("Sorszám");
					TableColumn<Player, String> name = new TableColumn<Player, String>("Név");
					TableColumn<Player, String> time = new TableColumn<Player, String>("Pontszám");

					number.setCellValueFactory(new PropertyValueFactory<Player, Integer>("number"));
					name.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
					time.setCellValueFactory(new PropertyValueFactory<Player, String>("time"));

					table.getColumns().addAll(number, name, time);

					add(table, 5, 6);

					setVisible(true);
					openAnim.play();

				}
			}
		});

		achievements.setOnAction((arg0) -> {
			if (openAnim.statusProperty().get() == Animation.Status.STOPPED
					&& closeAnim.statusProperty().get() == Animation.Status.STOPPED) {
				if (isVisible()) {
					closeAnim.play();

				} else {
					add(new DVText("Díjak", Font.font("Verdana", 22)), 4, 2);
					List<Achievement> ac = DejaVu.game.getAchievements();
					int i = 3;
					for (Achievement a : ac) {
						add(new DVText(a.getDescription(), Font.font("Verdana", 18)), 4, i);
						
						ImageView img = new ImageView(new Image(
								getClass().getClassLoader().getResource("images/first.png").toExternalForm()));
						
						if (a.getPrize() == 2)
							img = new ImageView(new Image(
									getClass().getClassLoader().getResource("images/second.png").toExternalForm()));
						else if (a.getPrize() == 3)
							img = new ImageView(new Image(
									getClass().getClassLoader().getResource("images/third.png").toExternalForm()));
						
						img.setFitHeight(50);
						img.setFitWidth(50);
						add(img, 5, i);
						i++;
					}

					setVisible(true);
					openAnim.play();
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