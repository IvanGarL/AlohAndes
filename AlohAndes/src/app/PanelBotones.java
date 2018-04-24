package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;


public class PanelBotones extends JPanel implements ActionListener{
	

	
	public final static String INICIO = "inicio";

	public final static String RF1 = "RF1";
	
	public final static String RF2 = "RF2";
	
	public final static String RF3 = "RF3";

	public final static String RF4 = "RF4";
	
	public final static String RF5 = "RF5";
	
	public final static String RF6 = "RF6";
	
	public final static String RF7 = "RF7";
	
	public final static String RF8 = "RF8";
	
	public final static String RF9 = "RF9";
	
	public final static String RF10 = "RF10";
	
	public final static String RFC1 = "RFC1";
	
	public final static String RFC2 = "RFC2";
	
	public final static String RFC3 = "RFC3";
	
	public final static String RFC4 = "RFC4";
	
	public final static String RFC5 = "RFC5";
	
	public final static String RFC6 = "RFC6";
	
	public final static String RFC7 = "RFC7";
	
	public final static String RFC8 = "RFC8";
	
	public final static String RFC9 = "RFC9";
	
	//
	//--------------
	//
	private InterfazAlohandes principal;
	
	private JLabel texto;

	private JButton rf1;
	private JButton rf2;
	private JButton rf3;
	private JButton rf4;
	private JButton rf5;
	private JButton rf6;
	private JButton rf7;
	private JButton rf8;
	private JButton rf9;
	private JButton rf10;
	private JButton rfc1;
	private JButton rfc2;
	private JButton rfc3;
	private JButton rfc4;
	private JButton rfc5;
	private JButton rfc6;
	private JButton rfc7;
	private JButton rfc8;
	private JButton rfc9;



	public PanelBotones(InterfazAlohandes pPrincipal)
	{
		
		JPanel secundario = new JPanel();
		JPanel terciario = new JPanel();
		setLayout(new BorderLayout());
		principal = pPrincipal;
		secundario.setBorder (new TitledBorder("Funcionales"));
		terciario.setBorder (new TitledBorder("Consulta"));
		secundario.setLayout(new GridLayout(10, 1));
		terciario.setLayout(new GridLayout(10, 1));
		
		texto = new JLabel("oprima los botones para probar los requerimientos funcionales");
		texto.setBorder(new TitledBorder("Resultados"));
		texto.setPreferredSize(new Dimension(675, 500));
		texto.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		
		rf1 = new JButton("RF1");
		rf1.setActionCommand(RF1);
		rf1.addActionListener(this);
		rf1.setPreferredSize(new Dimension(100,0));
		rf1.setEnabled(true);
		
		rf2 = new JButton("RF2");
		rf2.setActionCommand(RF2);
		rf2.addActionListener(this);
		rf2.setEnabled(true);
		
		rf3 = new JButton("RF3");
		rf3.setActionCommand(RF3);
		rf3.addActionListener(this);
		rf3.setEnabled(true);
		
		rf4 = new JButton("RF4");
		rf4.setActionCommand(RF4);
		rf4.addActionListener(this);
		rf4.setEnabled(true);
		
		rf5 = new JButton("RF5");
		rf5.setActionCommand(RF5);
		rf5.addActionListener(this);
		rf5.setEnabled(true);
		
		rf6 = new JButton("RF6");
		rf6.setActionCommand(RF6);
		rf6.addActionListener(this);
		rf6.setEnabled(true);
		
		rf7 = new JButton("RF7");
		rf7.setActionCommand(RF7);
		rf7.addActionListener(this);
		rf7.setEnabled(true);
		
		rf8 = new JButton("RF8");
		rf8.setActionCommand(RF8);
		rf8.addActionListener(this);
		rf8.setEnabled(true);
		
		rf9 = new JButton("RF9");
		rf9.setActionCommand(RF9);
		rf9.addActionListener(this);
		rf9.setEnabled(true);
		
		rf10 = new JButton("RF10");
		rf10.setActionCommand(RF10);
		rf10.addActionListener(this);
		rf10.setEnabled(true);
		
		rfc1 = new JButton("RFC1");
		rfc1.setActionCommand(RFC1);
		rfc1.addActionListener(this);
		rfc1.setPreferredSize(new Dimension(100, 0));
		rfc1.setEnabled(true);
		
		rfc2 = new JButton("RFC2");
		rfc2.setActionCommand(RFC2);
		rfc2.addActionListener(this);
		rfc2.setEnabled(true);
		
		rfc3 = new JButton("RFC3");
		rfc3.setActionCommand(RFC3);
		rfc3.addActionListener(this);
		rfc3.setEnabled(true);
		
		rfc4 = new JButton("RFC4");
		rfc4.setActionCommand(RFC4);
		rfc4.addActionListener(this);
		rfc4.setEnabled(true);
		
		rfc5 = new JButton("RFC5");
		rfc5.setActionCommand(RFC5);
		rfc5.addActionListener(this);
		rfc5.setEnabled(true);
		
		rfc6 = new JButton("RFC6");
		rfc6.setActionCommand(RFC6);
		rfc6.addActionListener(this);
		rfc6.setEnabled(true);
		
		rfc7 = new JButton("RFC7");
		rfc7.setActionCommand(RFC7);
		rfc7.addActionListener(this);
		rfc7.setEnabled(true);
		
		rfc8 = new JButton("RFC8");
		rfc8.setActionCommand(RFC8);
		rfc8.addActionListener(this);
		rfc8.setEnabled(true);
		
		rfc9 = new JButton("RFC9");
		rfc9.setActionCommand(RFC9);
		rfc9.addActionListener(this);
		rfc9.setEnabled(true);

		
		
		secundario.add(rf1);
		secundario.add(rf2);
		secundario.add(rf3);
		secundario.add(rf4);
		secundario.add(rf5);
		secundario.add(rf6);
		secundario.add(rf7);
		secundario.add(rf8);
		secundario.add(rf9);
		secundario.add(rf10);
		terciario.add(rfc1);
		terciario.add(rfc2);
		terciario.add(rfc3);
		terciario.add(rfc4);
		terciario.add(rfc5);
		terciario.add(rfc6);
		terciario.add(rfc7);
		terciario.add(rfc8);
		terciario.add(rfc9);
		
		add(secundario, BorderLayout.WEST);
		add(terciario, BorderLayout.EAST);
		add(texto, BorderLayout.CENTER);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comand = e.getActionCommand();
		switch(comand){
		case RF1:
			break;
		case RF2:
			break;
		case RF3:
			break;
		case RF4:
			break;
		case RF5:
			break;
		case RF6:
			break;
		case RF7:
			break;
		case RF8:
			break;
		case RF9:
			break;
		case RF10:
			break;
		case RFC1:
			break;
		case RFC2:
			break;
		case RFC3:
			break;
		case RFC4:
			break;
		case RFC5:
			break;
		case RFC6:
			break;
		case RFC7:
			break;
		case RFC8:
			break;
		case RFC9:
			break;
		}
		
	}

}
