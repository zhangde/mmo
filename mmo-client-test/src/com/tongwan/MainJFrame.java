package com.tongwan;

import javax.swing.JFrame;

public class MainJFrame extends JFrame{
	private static MainJPanel panel; 
	public MainJFrame(){
		super("test");
		setSize(300, 300);
		panel =new MainJPanel();
		add(panel);
	}
	/**
	 * @return the panel
	 */
	public static MainJPanel getPanel() {
		return panel;
	}
	
}
