package exception;

import recette.Recette;

public class RecetteNonTrouveException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecetteNonTrouveException(Recette recette) {
		System.err.println("La recette " + recette.getNom() +" que vous recherchez n'existe pas");
	}
}
