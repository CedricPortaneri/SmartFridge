package tries;

import java.util.Comparator;

import produit.Produit;

public class ComparatorQuantiteCroissant implements Comparator<Produit> {

	public int compare(Produit s1, Produit s2) {
		// tri desc
		if (s1.getQuantite() < s2.getQuantite()) {
			return -1;
		} else if (s1.getQuantite() > s2.getQuantite()) {
			return 1;
		} else {
			return 0;
		}
	}
}
