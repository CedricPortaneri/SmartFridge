package action;

import exception.ProduitNonTrouveException;
import exception.RecetteNonTrouveException;
import frigo.Frigo;

public abstract class Action {
	

	protected Frigo frigo;
	
	public Action(Frigo frigo) {
		this.frigo = frigo;
	}
	
	public Frigo getFrigo() {
		return frigo;
	}

	public abstract void doAction() throws ProduitNonTrouveException, RecetteNonTrouveException;
}
