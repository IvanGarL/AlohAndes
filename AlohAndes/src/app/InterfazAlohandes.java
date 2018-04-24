package app;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import tm.AlohAndesTransactionManager;

public class InterfazAlohandes extends JFrame{
	
	AlohAndesTransactionManager controller;
	
	PanelBanner panelBanner;
	
	PanelBotones panelBotones;
	
	public InterfazAlohandes(){
		
		controller = new AlohAndesTransactionManager("/WebContent/WEB-INF/ConnectionData");
		
		setTitle("Alohandes");
		setSize(900, 700);
		setResizable(false);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		panelBanner = new PanelBanner();
		
		panelBotones= new PanelBotones(this);
		add(panelBanner, BorderLayout.NORTH);
		add(panelBotones, BorderLayout.WEST);
	}
	
	
	public static void main(String[] args)
	{
		try {
			InterfazAlohandes app = new InterfazAlohandes();
			app.setVisible(true);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}
