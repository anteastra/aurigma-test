package com.anteastra.GUI;

import javax.swing.JFrame;

public class Sample {
	public static void main(String ... arg) {
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
             public void run() {
            	 createAndShowGUI();
             }
        });		
	}

	protected static void createAndShowGUI() {
		
		GUIFrame frame = new GUIFrame();
		frame.setTitle("EXIF reader");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}
}
