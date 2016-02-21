package interfaceGraphique;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JMenuBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import produit.Produit;
import produit.ProduitView;
import frigo.Frigo;

public class ContenueMenu extends Component {
	
	private ProduitView produitView;
	




	public ContenueMenu(ProduitView produitView) {
		super();
		this.produitView = produitView;
	}





	public void paint(Graphics gra){
		
	    
		Graphics2D g = (Graphics2D) gra;
		this.produitView.paint(g);
	}
}
