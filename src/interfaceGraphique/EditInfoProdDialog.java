package interfaceGraphique;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import frigo.Frigo;
import produit.Produit;

public class EditInfoProdDialog extends JDialog{
	private JTextField fieldNb;
	private int qte;
	private Frigo frigo;
	private int id;
	
	
	public EditInfoProdDialog(JFrame parent,int id,Frigo frigo)
	{
		super(parent, "Ingrediant n°"+id, true);
		this.frigo = frigo;
		this.id = id;
		String[] prodenum = { "Non référencé", "Produit laitier", "Boisson",
				"Viande", "Poisson", "Oeuf", "Légume", "Fruit" };
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Produit prod = frigo.getContenu().get(id);
		String info, type, info2="", info3,img;

		type = prod.getType().name();

		info = prod.getNom() + " (" + prod.getQuantite() + ")";
		
		info3= "Ajouté le "
				+ df.format(prod.getDateAjout());

		Date per = prod.getDateDePeremption();
		if (per != null) {
			if (per.before(new Date()))
				info2 = "Périmé";
			else
				info2 = "Expire le " + df.format(per);
		}
		JLabel image = new JLabel(new ImageIcon("resources/"+frigo.getContenu().get(id).getType().toString()+".png"));
		JLabel infoL = new JLabel(info);
		JLabel info2L = new JLabel(info2);
		JLabel info3L = new JLabel(info3);
		setLocationRelativeTo(null);
		JPanel pnl0 = new JPanel();
		add(pnl0);
		pnl0.setLayout(new BoxLayout(pnl0, BoxLayout.Y_AXIS));
		JPanel pnl1 = new JPanel();
		JPanel pnl2 = new JPanel();
		
		pnl0.add(pnl1);
		pnl1.setLayout(new GridLayout(5, 2));
		pnl1.setBorder(new EmptyBorder(10, 10, 0, 10));
		pnl1.setLayout(new BoxLayout(pnl1, BoxLayout.Y_AXIS));
		pnl1.add(image);
		pnl1.add(infoL);
		pnl1.add(info2L);
		pnl1.add(info3L);
		
		pnl1.add(new JLabel("Quantite:  "));
		
		fieldNb = new JTextField();
		fieldNb.setText(String.valueOf(frigo.getContenu().get(id).getQuantite()));
		pnl1.add(fieldNb);
		
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
				qte = Integer.parseInt(fieldNb.getText());
				dispose();
			}
		});
		btnCancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				dispose();
			}
		});
		
		setResizable(false);
		pack();
	}


	public Frigo getFrigo() {
		return frigo;
	}


	public int getQte() {
		return qte;
	}


	public int getId() {
		return id;
	}

	
	

}
