package action;

import produit.Produit;
import frigo.Frigo;

public class ActionAjouterProduit extends Action {

	private Produit produit;
	
	public ActionAjouterProduit(Frigo frigo,Produit produit) {
		super(frigo);
		this.produit=produit;
	}
	
	public void doAction(){
		super.frigo.getContenu().add(this.produit);
	}

}
