package hu.unideb.inf.dejavu.gui;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class QuestionMenu extends DVMenu {

	QuestionMenu(String q, String answ1, String answ2, FuncIQuestion Answ1Func, FuncIQuestion Answ2Func) {

		setHgap(10);
		setVgap(10);

		DVButton yes = new DVButton(answ1, 1);

		yes.setOnAction((arg0) -> {
			Answ1Func.call();
		});

		DVButton no = new DVButton(answ2, 1);

		no.setOnAction((arg0) -> {
			Answ2Func.call();
		});

		add(new DVText(q, Font.font("Verdana", FontWeight.BOLD, 14)), 2, 2);

		add(yes, 2, 10);
		add(no, 3, 10);
	}

}
