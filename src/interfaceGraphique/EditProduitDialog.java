package interfaceGraphique;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import frigo.Frigo;
import produit.Produit;
import produit.ProduitEnum;


public class EditProduitDialog extends JDialog 
{
	private Produit produit;
	private JComboBox fieldType;
	private JTextField fieldNom;
	private JTextField fieldQte;
	private JTextField fieldDate;
	
	
	public EditProduitDialog(JFrame parent, Produit prod)
	{
		super(parent, "Paramètres du produit", true);
		setLocationRelativeTo(null);
		String[] prodenum = {"Non référencé", "Produit laitier", "Boisson", "Viande", "Poisson", "Oeuf", "Légume", "Fruit"};
		
		if (prod == null)
		{
			produit = new Produit();
			produit.setDateAjout(new Date());
			produit.setType(ProduitEnum.NON_REFERENCE);
			produit.setQuantite(1);
		}
		else
			produit = prod;
		
		JPanel pnl0 = new JPanel();
		add(pnl0);
		pnl0.setLayout(new BoxLayout(pnl0, BoxLayout.Y_AXIS));
		JPanel pnl1 = new JPanel();
		JPanel pnl2 = new JPanel();
		
		pnl0.add(pnl1);
		pnl1.setLayout(new GridLayout(5, 2));
		pnl1.setBorder(new EmptyBorder(10, 10, 0, 10));
		
		pnl1.add(new JLabel("Type :  "));
		pnl1.add(fieldType = new JComboBox(prodenum));
		pnl1.add(new JLabel("Nom :  "));
		pnl1.add(fieldNom = new JTextField());
		pnl1.add(new JLabel("Quantité :  "));
		pnl1.add(fieldQte = new JTextField());
		pnl1.add(new JLabel("Date de péremption :  "));
		pnl1.add(fieldDate = new JTextField());
		
		fieldType.setSelectedIndex(produit.getType().ordinal());
		fieldNom.setText(produit.getNom());
		fieldQte.setText(new Integer(produit.getQuantite()).toString());
		
		Date per = produit.getDateDePeremption();
		if (per != null)
			fieldDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(per));
		
		pnl0.add(pnl2);
		pnl2.setLayout(new BoxLayout(pnl2, BoxLayout.X_AXIS));
		pnl2.setBorder(new EmptyBorder(0, 10, 10, 10));
		
		JButton btnOk, btnCancel;
		pnl2.add(btnOk = new JButton("OK"));
		pnl2.add(btnCancel = new JButton("Annuler"));
		
		btnOk.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				String nom = fieldNom.getText();
				int qte = Integer.parseInt(fieldQte.getText());
				
				if (nom.trim().isEmpty())
				{
					JOptionPane.showMessageDialog(EditProduitDialog.this, "Vous devez entrer un nom de produit.", 
							"SmartFridge", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (qte < 1)
				{
					JOptionPane.showMessageDialog(EditProduitDialog.this, "La quantité doit être au moins 1.", 
							"SmartFridge", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				produit.setType(ProduitEnum.values()[fieldType.getSelectedIndex()]);
				produit.setNom(nom);
				produit.setQuantite(qte);
				
				String date = fieldDate.getText();
				if (!date.isEmpty())
				{
					try
					{
						String[] partsdate = date.split("/");
						produit.setDateDePeremption(Frigo.createDate(
								Integer.parseInt(partsdate[2]),
								Integer.parseInt(partsdate[1]),
								Integer.parseInt(partsdate[0])));
					}
					catch (IndexOutOfBoundsException e)
					{
						JOptionPane.showMessageDialog(EditProduitDialog.this, "La date doit être entrée sous la forme jj/mm/aaaa.", 
								"SmartFridge", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				else
					produit.setDateDePeremption(null);
				
				dispose();
			}
		});
		btnCancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				produit = null;
				dispose();
			}
		});
		
		setResizable(false);
		pack();
	}
	
	public Produit getProduit()
	{
		return produit;
	}
}
