package action;

import java.util.ArrayList;
import java.util.Date;

import produit.Produit;
import frigo.Frigo;

public class ActionSupprimerPerimee extends Action 
{
	public ActionSupprimerPerimee(Frigo frigo) 
	{
		super(frigo);
	}

	@Override
	public void doAction() 
	{
		Date today = new Date();
		ArrayList<Produit> contenu = frigo.getContenu();
		
		for (int i = 0; i < contenu.size();)
		{
			Produit prod = contenu.get(i);
			
			if (prod.getDateDePeremption().before(today))
				contenu.remove(prod);
			else
				i++;
		}
	}

}
