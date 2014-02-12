package com.anteastra.GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import com.anteastra.metaextractor.MetaDataHandler;

public class GUIFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7976509621709688058L;
	
	private JPanel panel = new JPanel();
	private JButton filePickButton = new JButton("Pick a JPEG File");
	private JLabel imgWidthLabel = new JLabel();
	private JLabel imgHeightLabel = new JLabel();
	private JLabel xResLabel = new JLabel();
	private JLabel yResLabel = new JLabel();
	private JLabel orientationLabel = new JLabel();
	private JLabel makeLabel = new JLabel();
	private JLabel modelLabel = new JLabel();
	private JLabel gpsTimeLabel = new JLabel();
	
	
	{
		this.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(Box.createVerticalStrut(5));
		panel.add(filePickButton);
		filePickButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		panel.setPreferredSize(new Dimension(500, 600));		
		panel.add(Box.createVerticalStrut(15));
		
		JPanel panelTemp = new JPanel();
		panelTemp.setLayout(new BoxLayout(panelTemp, BoxLayout.X_AXIS));
		panelTemp.add(new JLabel("ImageWidth : "));
		panelTemp.add(imgWidthLabel);
		panel.add(panelTemp);
		panel.add(Box.createVerticalStrut(5));
		
		panelTemp = new JPanel();
		panelTemp.setLayout(new BoxLayout(panelTemp, BoxLayout.X_AXIS));
		panelTemp.add(new JLabel("ImageHeight : "));
		panelTemp.add(imgHeightLabel);
		panel.add(panelTemp);
		panel.add(Box.createVerticalStrut(5));
		
		panelTemp = new JPanel();
		panelTemp.setLayout(new BoxLayout(panelTemp, BoxLayout.X_AXIS));
		panelTemp.add(new JLabel("Make : "));
		panelTemp.add(makeLabel);
		panel.add(panelTemp);
		panel.add(Box.createVerticalStrut(5));
		
		panelTemp = new JPanel();
		panelTemp.setLayout(new BoxLayout(panelTemp, BoxLayout.X_AXIS));
		panelTemp.add(new JLabel("Model : "));
		panelTemp.add(modelLabel);
		panel.add(panelTemp);
		panel.add(Box.createVerticalStrut(5));
		
		panelTemp = new JPanel();
		panelTemp.setLayout(new BoxLayout(panelTemp, BoxLayout.X_AXIS));
		panelTemp.add(new JLabel("X Resolution : "));
		panelTemp.add(xResLabel);
		panel.add(panelTemp);
		panel.add(Box.createVerticalStrut(5));

		panelTemp = new JPanel();
		panelTemp.setLayout(new BoxLayout(panelTemp, BoxLayout.X_AXIS));
		panelTemp.add(new JLabel("Y Resolution : "));
		panelTemp.add(yResLabel);
		panel.add(panelTemp);
		panel.add(Box.createVerticalStrut(5));
		
		panelTemp = new JPanel();
		panelTemp.setLayout(new BoxLayout(panelTemp, BoxLayout.X_AXIS));
		panelTemp.add(new JLabel("GPS time : "));
		panelTemp.add(gpsTimeLabel);
		panel.add(panelTemp);
		panel.add(Box.createVerticalStrut(5));
		
		panelTemp = new JPanel();
		panelTemp.setLayout(new BoxLayout(panelTemp, BoxLayout.X_AXIS));
		panelTemp.add(Box.createHorizontalStrut(15));
		JLabel tmpLabel = new JLabel("Orientation : ");
		tmpLabel.setAlignmentY(JComponent.TOP_ALIGNMENT);
		panelTemp.add(tmpLabel);
		panelTemp.add(orientationLabel);
		panelTemp.add(Box.createHorizontalStrut(15));
		orientationLabel.setAlignmentY(JComponent.TOP_ALIGNMENT);
		panel.add(panelTemp);
		panel.add(Box.createVerticalStrut(5));
		
		filePickButton.addActionListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new JPEGImageFileFilter());
        int res = fc.showOpenDialog(null);
        if (res == JFileChooser.APPROVE_OPTION) {
        	File file = fc.getSelectedFile();
        	MetaDataHandler mdh = new MetaDataHandler(file);
        		
        	if (mdh.isParsedOK()) {
        		imgWidthLabel.setText(mdh.getExifTagValue(MetaDataHandler.EXIF_TAG_WIDTH));
        		imgHeightLabel.setText(mdh.getExifTagValue(MetaDataHandler.EXIF_TAG_HEIGHT));
        		makeLabel.setText(mdh.getExifTagValue(MetaDataHandler.EXIF_TAG_MAKE));
        		xResLabel.setText(mdh.getExifTagValue(MetaDataHandler.EXIF_TAG_X_RESOLUTION));
        		yResLabel.setText(mdh.getExifTagValue(MetaDataHandler.EXIF_TAG_Y_RESOLUTION));
        		orientationLabel.setText("<html>"+(mdh.getExifTagValue(MetaDataHandler.EXIF_TAG_ORIENTATION)==null
        				?" ":mdh.getExifTagValue(MetaDataHandler.EXIF_TAG_ORIENTATION))
        						+"</html>");
        		modelLabel.setText(mdh.getExifTagValue(MetaDataHandler.EXIF_TAG_MODEL));
        		gpsTimeLabel.setText(mdh.getGPSTagValue(MetaDataHandler.GPS_TAG_GPSTimeStamp));
        	}else {
        		JOptionPane.showMessageDialog(null,
        				"There is error while reading metadata."+ mdh.getErrorMsg(), "Aborting...",
                        JOptionPane.WARNING_MESSAGE);        		
            }            
        }else {
            JOptionPane.showMessageDialog(null,
                    "You must select one image to read meta data.", "Aborting...",
                    JOptionPane.WARNING_MESSAGE);
        }
	}
	
	private class JPEGImageFileFilter extends FileFilter implements java.io.FileFilter {
		public boolean accept(File f) {
			
			if (f.getName().toLowerCase().endsWith(".jpeg")) return true;
			if (f.getName().toLowerCase().endsWith(".jpg")) return true;
			if(f.isDirectory())return true;
			return false;
		}
		
		public String getDescription() {
			return "JPEG files";
		}
	}
}
