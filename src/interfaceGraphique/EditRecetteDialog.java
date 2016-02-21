package interfaceGraphique;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import action.ActionAjouterRecette;
import frigo.Frigo;
import produit.Produit;
import produit.ProduitEnum;
import recette.Recette;


public class EditRecetteDialog extends JDialog 
{
	private Recette recette;
	private JTextField fieldNom;
	private JTextField fieldDef;
	private JTextField fieldNbIngre;
	private HashMap<Produit,Integer> ingredients;
	
	
	public EditRecetteDialog(final JFrame parent,Recette rece)
	{
		super(parent, "Paramètres de la Recette", true);
		setLocationRelativeTo(null);
		ingredients=new HashMap<Produit,Integer>();
		if (rece == null)
		{
			recette = new Recette();
		}
		else
			recette = rece;
		
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
		pnl1.add(new JLabel("Description (facultative) :  "));
		pnl1.add(fieldDef = new JTextField());
		pnl1.add(new JLabel("Nombre d'ingrédiant :  "));
		pnl1.add(fieldNbIngre = new JTextField());
		
		fieldNom.setText(recette.getNom());
		fieldDef.setText(recette.getDescription());
		
		pnl0.add(pnl2);
		pnl2.setLayout(new BoxLayout(pnl2, BoxLayout.X_AXIS));
		pnl2.setBorder(new EmptyBorder(0, 10, 10, 10));
		
		JButton btnOk, btnCancel;
		pnl2.add(btnOk = new JButton("Continuer"));
		pnl2.add(btnCancel = new JButton("Annuler"));
		
		btnOk.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				String nom = fieldNom.getText();
				String desc = fieldDef.getText();
				int qte = Integer.parseInt(fieldNbIngre.getText());
				
				if (nom.trim().isEmpty())
				{
					JOptionPane.showMessageDialog(EditRecetteDialog.this, "Vous devez entrer un nom de produit.", 
							"SmartFridge", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (qte < 1)
				{
					JOptionPane.showMessageDialog(EditRecetteDialog.this, "La quantité doit être au moins 1.", 
							"SmartFridge", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				
				recette.setNom(nom);
				recette.setDescription(desc);
				
				for(int i =0; i < qte;i++){
					EditIngredientDialog dialogIngrediant = new EditIngredientDialog(parent,i+1);
					dialogIngrediant.setVisible(true);

					
					if (dialogIngrediant.getQte() != 0) {
						ingredients.put(new Produit(dialogIngrediant.getNom()), dialogIngrediant.getQte());
					}
						
						
					
				}
				
				recette.setIngredients(ingredients);
				
				dispose();
			}
		});
		btnCancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				recette = null;
				dispose();
			}
		});
		
		setResizable(false);
		pack();
	}
	
	public Recette getRecette()
	{
		return recette;
	}
}
