package hu.unideb.inf.dejavu.gui;

import hu.unideb.inf.dejavu.Game;
import hu.unideb.inf.dejavu.objects.StopWatch;

import java.awt.CardLayout;
import java.awt.Container;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

public class Ground extends JFrame implements GameFrame {
	GameMenu menu;
	Game game;

	JFileChooser choser = new JFileChooser();
	JComboBox<String> dimensionChoser = new JComboBox<String>();
	StopWatch stopWatch;

	public Ground() {
		game = new Game();
		init();

		setChoser();

		setMenu(new QuestionPanel());
	}

	public GameMenu getMenu() {
		return menu;
	}

	public void setMenu(GameMenu Menu) {
		this.menu = Menu;
		Container container = getContentPane();
		container.removeAll();
		container.add((JPanel) menu);
		container.validate();
		container.repaint();
	}

	void setChoser() {
		choser.addChoosableFileFilter(new FileFilter() {

			String[] fileNames = new String[] { "jpg", "png", "jpeg" };

			@Override
			public String getDescription() {
				return "Képek";
			}

			@Override
			public boolean accept(File f) {
				for (String a : fileNames)
					if (f.getName().toLowerCase().endsWith(a)
							|| f.isDirectory())
						return true;
				return false;
			}
		});
	}

	void init() {
		setTitle("Deja Vu");
		setResizable(false);
		addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                game.exitGame();
            }
        });
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(800, 600);
		setLayout(new CardLayout());

		ClassLoader cl = this.getClass().getClassLoader();

		ImageIcon imageIcon = new ImageIcon(
				cl.getResource("pictures/cards1/icon.png"));

		setIconImage(imageIcon.getImage());
	}
}
