package hu.unideb.inf.dejavu.gui;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

class WindowPane extends GridPane{
	WindowPane() {

		setId("menu");

		getStylesheets().add(
				DVMenu.class.getResource("Menu.css").toExternalForm());

	}
	
}

public class DVMenu extends BorderPane {
	
	public WindowPane window= new WindowPane();

	public DVMenu() {
		super();

		setId("window");

		getStylesheets().add(
				WindowPane.class.getResource("Window.css").toExternalForm());

		setCenter(window);

	}

	
	void setHgap(int h){
		((WindowPane)getCenter()).setHgap(h);
	}
	
	void setVgap(int v){
		((WindowPane)getCenter()).setVgap(v);
	}
	
	void add(Node button, int x, int y){
		((WindowPane)getCenter()).add(button,x,y);
	}

}
