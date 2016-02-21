package action;

import java.util.Date;
import java.util.Map.Entry;

import produit.Produit;

import recette.Recette;
import exception.ProduitNonTrouveException;
import exception.RecetteNonTrouveException;
import frigo.Frigo;

public class ActionCuisiner extends Action{

	private Recette recette;
	public ActionCuisiner(Frigo frigo,Recette recette) {
		super(frigo);
		this.recette=recette;
	}

	@Override
	public void doAction() throws ProduitNonTrouveException,
			RecetteNonTrouveException 
	{
		for (Entry<Produit, Integer> ingred : recette.getIngredients().entrySet())
		{
			Produit prod = ingred.getKey();
			int qte = ingred.getValue();
			Date today = new Date();
			int qterest = qte;
			
			for (int i = 0; i < frigo.getContenu().size(); )
			{
				Produit unprod = frigo.getContenu().get(i);
				
				if (unprod.getType() != prod.getType()) {
					i++;
					continue;
				}
				if (unprod.getNom() != prod.getNom()){
					i++;
					continue;
				}
				/* Ne compte pas les perimés*/
				if (unprod.getDateDePeremption().before(today)) {
					i++;
					continue;
				}
				
				unprod.setQuantite(unprod.getQuantite() - qte);
				if (unprod.getQuantite() < 0)
				{
					
					qterest -= unprod.getQuantite();
					frigo.getContenu().remove(i);
				}
				else
				{
					qterest -= qte;
					i++;
				}
				
				break;
			}
			
			if (qterest < 0)
				qterest=0;
		}
	}

}
