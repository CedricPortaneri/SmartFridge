package action;

import recette.Recette;
import frigo.Frigo;

public class ActionAjouterRecette extends Action {

	private Recette recette;
	
	public ActionAjouterRecette(Frigo frigo,Recette recette) {
		super(frigo);
		this.recette=recette;
	}
	
	public void doAction(){
		super.frigo.getListeRecette().add(this.recette);
	}

}
