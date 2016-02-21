package action;

import produit.Produit;
import recette.Recette;
import frigo.Frigo;

public class ActionModifierRecette extends Action {
	
	private Recette recetteAModifier;
	private Recette nouvelleRecette;
	
	// produit: le produit vis� par l'action.
	// difference: la diff�rence sur la quantit�. Par exemple, si c'est 2 on ajoute 2 exemplaires du produit.
	public ActionModifierRecette(Frigo frigo, Recette recetteAModifier, Recette nouvelleRecette)
	{
		super(frigo);
		this.recetteAModifier=recetteAModifier;
		this.nouvelleRecette=nouvelleRecette;
		
	}

	@Override
	public void doAction() 
	{
		frigo.getListeRecette().set(frigo.getListeRecette().indexOf(recetteAModifier), nouvelleRecette);
		
	}
}
