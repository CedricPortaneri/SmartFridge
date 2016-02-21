package exception;

import produit.Produit;

public class ProduitNonTrouveException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProduitNonTrouveException(Produit produit) {
		System.err.println("Le produit " + produit.getNom() +" que vous recherchez n'existe pas");
	}
}
