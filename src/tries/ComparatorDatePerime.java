package tries;

import java.util.Comparator;

import produit.Produit;

public class ComparatorDatePerime implements Comparator<Produit> {

	public int compare(Produit s1, Produit s2) {
		if(s1.getDateDePeremption()==null){
			return 1;
		}
		else if(s2.getDateDePeremption()==null){
			return -1;
		}
		else if (s1.getDateDePeremption().before(s2.getDateDePeremption())) {
			return -1;
		} else if (s1.getDateDePeremption().after(s2.getDateDePeremption())) {
			return 1;
		} else {
			return 0;
		}
	}
}
