package tries;

import java.util.Comparator;

import produit.Produit;

public class ComparatorDateAjout implements Comparator<Produit> {

	public int compare(Produit s1, Produit s2) {
		// tri desc
		if (s1.getDateAjout().before(s2.getDateAjout())) {
			return -1;
		} else if (s1.getDateAjout().after(s2.getDateAjout())) {
			return 1;
		} else {
			return 0;
		}
	}
}
