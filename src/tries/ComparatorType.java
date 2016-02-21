package tries;

import java.util.Comparator;

import produit.Produit;

public class ComparatorType implements Comparator<Produit> {

	public int compare(Produit s1, Produit s2) {
		int s1nbr, s2nbr;
		switch (s1.getType()) {
		case BOISSON:
			s1nbr = 2;
			break;
		case FRUIT:
			s1nbr = 7;
			break;
		case LEGUME:
			s1nbr = 6;
			break;
		case NON_REFERENCE:
			s1nbr = 0;
			break;
		case OEUF:
			s1nbr = 5;
			break;
		case POISSON:
			s1nbr = 4;
			break;
		case PRODUIT_LAITIER:
			s1nbr = 1;
			break;
		case VIANDE:
			s1nbr = 3;
			break;
		default:
			s1nbr = 0;
			break;

		}
		switch (s2.getType()) {
		case BOISSON:
			s2nbr = 2;
			break;
		case FRUIT:
			s2nbr = 7;
			break;
		case LEGUME:
			s2nbr = 6;
			break;
		case NON_REFERENCE:
			s2nbr = 0;
			break;
		case OEUF:
			s2nbr = 5;
			break;
		case POISSON:
			s2nbr = 4;
			break;
		case PRODUIT_LAITIER:
			s2nbr = 1;
			break;
		case VIANDE:
			s2nbr = 3;
			break;
		default:
			s2nbr = 0;
			break;

		}
		if (s1nbr < s2nbr) {
			return -1;
		} else if (s1nbr > s2nbr) {
			return 1;
		} else {
			return 0;
		}
	}
}
