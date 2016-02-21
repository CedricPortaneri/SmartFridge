package interfaceGraphique;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import tries.TriesEnum;
import frigo.Frigo;

public class TrieDialog extends JDialog {
	private Frigo frigo;
	private JRadioButton da = new JRadioButton("par date d'ajout");
	private JRadioButton dp = new JRadioButton("par date de peremption");
	private JRadioButton qc = new JRadioButton("par quantité croissante");
	private JRadioButton qd = new JRadioButton("par quantité décroissante");
	private JRadioButton t = new JRadioButton("par type");
	
	public TrieDialog(JFrame parent, Frigo frigoo)
	{
		super(parent, "Trie du Frigo", true);
		setLocationRelativeTo(null);
		this.frigo = frigoo;
		JPanel pnl0 = new JPanel();
		add(pnl0);
		pnl0.setLayout(new BoxLayout(pnl0, BoxLayout.Y_AXIS));
		JPanel pnl1 = new JPanel();
		JPanel pnl2 = new JPanel();
		
		pnl0.add(pnl1);
		pnl1.setLayout(new GridLayout(5, 2));
		pnl1.setBorder(new EmptyBorder(10, 10, 0, 10));
		ButtonGroup bg = new ButtonGroup();
		
		bg.add(da);
		da.setSelected(true);
		bg.add(dp);
		bg.add(qc);
		bg.add(qd);
		bg.add(t);
		
		pnl1.add(da);
		pnl1.add(dp);
		pnl1.add(qc);
		pnl1.add(qd);
		pnl1.add(t);
		
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
				if (da.isSelected()){
					frigo.setTypeTrie(TriesEnum.TRIE_DATE_AJOUT);
				}
				else if (dp.isSelected()){
					frigo.setTypeTrie(TriesEnum.TRIE_DATE_PEREMPTION);
				}
				else if (qc.isSelected()){
					frigo.setTypeTrie(TriesEnum.TRIE_QUANTITE_CROISSANT);
				}
				else if (qd.isSelected()){
					frigo.setTypeTrie(TriesEnum.TRIE_QUANTITE_DECROISSANT);
				}
				else if (t.isSelected()){
					frigo.setTypeTrie(TriesEnum.TRIE_TYPE);
				}
				
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

}
