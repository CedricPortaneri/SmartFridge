package interfaceGraphique;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import produit.Produit;
import recette.Recette;

public class EditIngredientDialog extends JDialog {
	private JTextField fieldNom;
	private JTextField fieldNb;
	private String nom;
	private int qte;
	
	
	public EditIngredientDialog(JFrame parent,int nb)
	{
		super(parent, "Ingrediant n°"+nb, true);
		
		setLocationRelativeTo(null);
		JPanel pnl0 = new JPanel();
		add(pnl0);
		pnl0.setLayout(new BoxLayout(pnl0, BoxLayout.Y_AXIS));
		JPanel pnl1 = new JPanel();
		JPanel pnl2 = new JPanel();
		
		pnl0.add(pnl1);
		pnl1.setLayout(new GridLayout(5, 2));
		pnl1.setBorder(new EmptyBorder(10, 10, 0, 10));
		
		pnl1.add(new JLabel("Nom :  "));
		pnl1.add(fieldNom = new JTextField());
		pnl1.add(new JLabel("Quantite:  "));
		pnl1.add(fieldNb = new JTextField());
		
		pnl0.add(pnl2);
		pnl2.setLayout(new BoxLayout(pnl2, BoxLayout.X_AXIS));
		pnl2.setBorder(new EmptyBorder(0, 10, 10, 10));
		
		JButton btnOk, btnCancel;
		pnl2.add(btnOk = new JButton("Ok"));
		pnl2.add(btnCancel = new JButton("Annuler"));
		
		btnOk.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				nom = fieldNom.getText();
				qte = Integer.parseInt(fieldNb.getText());
				
				dispose();
			}
		});
		btnCancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				nom = fieldNom.getText();
				qte = Integer.parseInt(fieldNb.getText());
				dispose();
			}
		});
		
		setResizable(false);
		pack();
	}


	public String getNom() {
		return nom;
	}


	public int getQte() {
		return qte;
	}
	
	
}
