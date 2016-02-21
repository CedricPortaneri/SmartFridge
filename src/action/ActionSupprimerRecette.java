package action;

import exception.RecetteNonTrouveException;
import frigo.Frigo;
import recette.Recette;

public class ActionSupprimerRecette extends Action {
	
	private Recette recetteASupprimer;

	public ActionSupprimerRecette(Frigo frigo,Recette recetteASupprimer) {
		super(frigo);
		this.recetteASupprimer = recetteASupprimer;
	}


	public void doAction() throws RecetteNonTrouveException {
		if (!(this.frigo.getListeRecette().remove(recetteASupprimer))){
			throw new RecetteNonTrouveException(recetteASupprimer);
		}
	}
	
}