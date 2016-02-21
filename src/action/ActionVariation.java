package action;

import produit.Produit;
import frigo.Frigo;


public class ActionVariation extends Action 
{
	private int indiceProduit;
	private int nouvelleQuantite;
	
	// produit: le produit visé par l'action.
	// difference: la différence sur la quantité. Par exemple, si c'est 2 on ajoute 2 exemplaires du produit.
	public ActionVariation(Frigo frigo, int indiceProduit, int nouvelleQuantite) 
	{
		super(frigo);
	
		this.indiceProduit = indiceProduit;
		this.nouvelleQuantite = nouvelleQuantite;
	}

	@Override
	public void doAction() 
	{
		if (nouvelleQuantite < 1)
		{
			frigo.getContenu().remove(indiceProduit);
		}
		else
		{
			frigo.getContenu().get(indiceProduit).setQuantite(nouvelleQuantite);
		}
	}

}
