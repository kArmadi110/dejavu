package hu.unideb.inf.dejavu.gui;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Button extends JButton {
	Button(String a, ActionListener al) {
		super(a);
		addActionListener(al);
		setFont(new Font("Courier New", Font.ITALIC, 12));
	}
}
