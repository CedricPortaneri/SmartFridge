package action;

import exception.ProduitNonTrouveException;
import frigo.Frigo;
import produit.Produit;

public class ActionSupprimerProduit extends Action {
	
	private Produit produitASupprimer;

	public ActionSupprimerProduit(Frigo frigo,Produit produitASupprimer) {
		super(frigo);
		this.produitASupprimer = produitASupprimer;
		// TODO Auto-generated constructor stub
	}


	public void doAction() throws ProduitNonTrouveException {
		if (!(this.frigo.getContenu().remove(produitASupprimer))){
			throw new ProduitNonTrouveException(produitASupprimer);
		}
	}
	
}
