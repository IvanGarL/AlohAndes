package rest;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelBanner extends JPanel{
	
	private JLabel panelImagen;

	//Constructor
	/**
	 * Construye el panel de la imagen superior.
	 */
	public PanelBanner()
	{
		panelImagen = new JLabel (new ImageIcon("docs/banner.jpg"));
		setPreferredSize( new Dimension( 0, 275) );
		setBackground(Color.BLACK);
		add(panelImagen);
	}
}
