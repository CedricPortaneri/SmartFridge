package frigo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;
import java.util.Map.Entry;

import exception.ProduitNonTrouveException;
import exception.RecetteNonTrouveException;
import action.Action;
import produit.Produit;
import produit.ProduitEnum;
import recette.Recette;
import tries.ComparatorDatePerime;
import tries.ComparatorQuantiteCroissant;
import tries.ComparatorQuantiteDecroissant;
import tries.ComparatorType;
import tries.TriesEnum;
import tries.ComparatorDateAjout;

public class Frigo {

	private ArrayList<Produit> contenu;
	private ArrayList<Produit> contenuInitial;
	private ArrayList<Action> actions;
	private ArrayList<Recette> listeRecette;
	private ArrayList<Recette> listeRecetteInitial;
	private int positionAction;
	private TriesEnum typeTrie;

	public Frigo(ArrayList<Produit> contenuInitial,
			ArrayList<Recette> listeRecetteInitial) {
		super();
		this.contenuInitial = contenuInitial;
		this.listeRecetteInitial = listeRecetteInitial;
		this.contenu = new ArrayList<Produit>();
		for (Produit produits : this.contenuInitial){
			this.contenu.add(produits);
		}
		this.listeRecette = new ArrayList<Recette>();
		for (Recette recette : this.listeRecetteInitial){
			this.listeRecette.add(recette);
		}

		this.actions = new ArrayList<Action>();
		this.positionAction = 0;
		this.typeTrie = TriesEnum.TRIE_DATE_AJOUT;
	}

	public ArrayList<Produit> getContenu() {
		return contenu;
	}

	public void setContenu(ArrayList<Produit> contenu) {
		this.contenu = contenu;
	}

	public ArrayList<Action> getActions() {
		return actions;
	}

	public void setActions(ArrayList<Action> actions) {
		this.actions = actions;
	}

	public ArrayList<Produit> getContenuInitial() {
		return contenuInitial;
	}

	public void setContenuInitial(ArrayList<Produit> contenuInitial) {
		this.contenuInitial = contenuInitial;
	}

	public ArrayList<Recette> getListeRecette() {
		return listeRecette;
	}

	public void setListeRecette(ArrayList<Recette> listeRecette) {
		this.listeRecette = listeRecette;
	}

	public ArrayList<Recette> getListeRecetteInitial() {
		return listeRecetteInitial;
	}

	public void setListeRecetteInitial(ArrayList<Recette> listeRecetteInitial) {
		this.listeRecetteInitial = listeRecetteInitial;
	}

	public int getPositionAction() {
		return positionAction;
	}

	public void setPositionAction(int positionAction) {
		this.positionAction = positionAction;
	}

	public TriesEnum getTypeTrie() {
		return typeTrie;
	}

	public void setTypeTrie(TriesEnum typeTrie) {
		this.typeTrie = typeTrie;
	}

	public void doAction(Action action)
	{
		/*
		 * Quand on ajoute on cree une nouvelle liste qui se remplie jusqu'a
		 * l'action actuel
		 */
		ArrayList<Action> newActions = new ArrayList<Action>();
		if (!this.actions.isEmpty()) {
			for (int i = 0; i < this.positionAction; i++) {
				newActions.add(this.actions.get(i));
			}
		}
		/*
		 * Ajout de la nouvelle action et maj de la liste d'actions et de la
		 * position
		 */
		newActions.add(action);
		this.actions = newActions;
		this.positionAction++;

		/*
		 * Contenu reinitialise et on effectue toute les actions de la liste
		 * Action
		 */
		this.contenu = new ArrayList<Produit>();
		for (Produit produits : this.contenuInitial){
			this.contenu.add(produits);
		}
		this.listeRecette = new ArrayList<Recette>();
		for (Recette recette : this.listeRecetteInitial){
			this.listeRecette.add(recette);
		}
		try {
			for (Action actions : this.actions) {
				actions.doAction();
			}
			

		} catch (ProduitNonTrouveException e) {
			throw new RuntimeException("!PRODUIT");
		} catch (RecetteNonTrouveException e) {
			throw new RuntimeException("!RECETTE");
		}
		/*
		 * for(Action actions : this.actions){
		 * System.out.println(actions.getClass().getName()); }
		 */

	}

	public void undoAction() {

		/* On decremente le curseur sur la liste des Actions */
		if (this.positionAction > 0) {
			this.positionAction--;

			/*
			 * Contenu reinitialise et on effectue toute les actions de la liste
			 * Action
			 */
			this.contenu = new ArrayList<Produit>();
			for (Produit produits : this.contenuInitial){
				this.contenu.add(produits);
			}
			this.listeRecette = new ArrayList<Recette>();
			for (Recette recette : this.listeRecetteInitial){
				this.listeRecette.add(recette);
			}
			try {
				for (int i = 0; i < this.positionAction; i++) {
					this.actions.get(i).doAction();
				}

			} catch (ProduitNonTrouveException e) {
				e.printStackTrace();
			} catch (RecetteNonTrouveException e) {
				e.printStackTrace();
			}
		}

	}

	public void redoAction() {

		/* On incremente le curseur sur la liste des Actions */
		if (this.positionAction < this.actions.size()) {
			this.positionAction++;
			this.contenu = new ArrayList<Produit>();
			for (Produit produits : this.contenuInitial){
				this.contenu.add(produits);
			}
			this.listeRecette = new ArrayList<Recette>();
			for (Recette recette : this.listeRecetteInitial){
				this.listeRecette.add(recette);
			}
			try {
				for (int i = 0; i < this.positionAction; i++) {
					this.actions.get(i).doAction();
				}

			} catch (ProduitNonTrouveException e) {
				e.printStackTrace();
			} catch (RecetteNonTrouveException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void load() {
		DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",
				Locale.US);
		df.setLenient(true);

		contenu.clear();
		listeRecette.clear();
		actions.clear();

		try {
			Scanner sc = new Scanner(new File("frigo.txt"));

			int numprod = Integer.parseInt(sc.nextLine());
			for (int i = 0; i < numprod; i++) {
				String nom = sc.nextLine();
				String type = sc.nextLine();
				int qte = Integer.parseInt(sc.nextLine());
				String dateAjout = sc.nextLine();
				String datePer = sc.nextLine();

				contenu.add(new Produit(nom, ProduitEnum.valueOf(type), qte, df
						.parse(datePer), df.parse(dateAjout)));
				// TODO Probleme ne peut charger produit qui n'ont pas de date de peremption
			}

			int numrec = Integer.parseInt(sc.nextLine());
			for (int i = 0; i < numrec; i++) {
				String nom = sc.nextLine();
				String desc = sc.nextLine().replace((char) 0x01, '\n');

				int numingred = Integer.parseInt(sc.nextLine());
				HashMap<Produit, Integer> ingred = new HashMap<Produit, Integer>(
						numingred);
				for (int j = 0; j < numingred; j++) {
					String inom = sc.nextLine();
					String itype = sc.nextLine();
					int iqte = Integer.parseInt(sc.nextLine());
					String idateAjout = sc.nextLine();
					String idatePer = sc.nextLine();
					String idQtNec = sc.nextLine();

					ingred.put(new Produit(inom, ProduitEnum.valueOf(itype),
							iqte, df.parse(idatePer), df.parse(idateAjout)),
							Integer.parseInt(idQtNec));
				}

				listeRecette.add(new Recette(nom, desc, ingred));
			}

			sc.close();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("parti en couille à " + e.getErrorOffset());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.contenuInitial = (ArrayList<Produit>) contenu.clone();
		this.listeRecetteInitial = (ArrayList<Recette>) listeRecette.clone();
		this.positionAction = contenuInitial.size()-1;
	}

	public void save() {
		try {
			PrintWriter pw = new PrintWriter(new File("frigo.txt"));

			pw.write(contenu.size() + "\n");
			for (Produit prod : contenu) {
				pw.write(prod.getNom() + "\n" + prod.getType() + "\n"
						+ prod.getQuantite() + "\n" + prod.getDateAjout()
						+ "\n" + prod.getDateDePeremption() + "\n");
			}

			pw.write(listeRecette.size() + "\n");
			for (Recette rec : listeRecette) {
				pw.write(rec.getNom() + "\n"
						+ rec.getDescription().replace('\n', (char) 0x01)
						+ "\n"); // remplace les sauts de ligne par un caractère
									// qu'on peut pas taper, comme ça ça fout
									// pas la merde

				HashMap<Produit, Integer> ingred = rec.getIngredients();
				pw.write(ingred.size() + "\n");
				for (Entry<Produit, Integer> entry : ingred.entrySet()) {
					pw.write(entry.getKey().getNom() + "\n"
							+ entry.getKey().getType() + "\n"
							+ entry.getKey().getQuantite() + "\n"
							+ entry.getKey().getDateAjout() + "\n"
							+ entry.getKey().getDateDePeremption() + "\n"
							+ entry.getValue() + "\n");
				}
			}

			// TODO: sauvegarder les actions? non on s'en fout

			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String toString() {
		String resultat="----------------------------------------------------------\n";
		if (this.contenu != null && !(this.contenu.isEmpty())) {
			switch (this.typeTrie) {
			case TRIE_DATE_AJOUT:
				Collections.sort(this.getContenu(), new ComparatorDateAjout());
				break;
			case TRIE_DATE_PEREMPTION:
				Collections.sort(this.getContenu(), new ComparatorDatePerime());
				break;
			case TRIE_QUANTITE_CROISSANT:
				Collections.sort(this.getContenu(),
						new ComparatorQuantiteCroissant());
				break;
			case TRIE_QUANTITE_DECROISSANT:
				Collections.sort(this.getContenu(),
						new ComparatorQuantiteDecroissant());
				break;
			case TRIE_TYPE:
				Collections.sort(this.getContenu(), new ComparatorType());
				break;
			default:
				break;

			}
			int i = 1;
			resultat = resultat.concat("Le Frigo contient :\n\n");

			for (Produit produit : this.contenu) {
				resultat = resultat.concat("#" + i + " " + produit);
				i++;
			}

		} else {
			resultat = resultat.concat("Votre Frigo est vide!");
		}
		if (this.listeRecette != null && !(this.listeRecette.isEmpty())) {
			int i = 1;
			resultat = resultat.concat("\n\nLes Recettes disponible sont:\n\n");
			for (Recette recette : this.listeRecette) {
				resultat = resultat.concat("#" + i + " " + recette);
				i++;
			}
			
		}
		else {
			resultat = resultat.concat("\nVous n'avez pas de Recettes!");
		}
		return resultat;
	}
/*
	// méthode de test.
	public static void main(String[] args) {
		System.out.println("TEST UNITAIRE -- ENREGISTREMENT ET LECTURE");

		ArrayList<Produit> contenu = new ArrayList<Produit>();
		ArrayList<Action> actions = new ArrayList<Action>();
		ArrayList<Recette> recettes = new ArrayList<Recette>();

		contenu.add(new Produit("Poulet", ProduitEnum.VIANDE, 3, new Date(),
				createDate(1992, 3, 8)));
		contenu.add(new Produit("Rôti de boeuf", ProduitEnum.VIANDE, 387,
				createDate(2014, 6, 12), createDate(2014, 5, 31)));
		contenu.add(new Produit("Lait de poulpe", ProduitEnum.PRODUIT_LAITIER,
				2, createDate(2014, 6, 12), createDate(2014, 5, 31)));
		contenu.add(new Produit("RATP", ProduitEnum.FRUIT, 1, createDate(2014,
				6, 6), createDate(2013, 3, 4)));
		contenu.add(new Produit("Porc gallo-romain", ProduitEnum.VIANDE, 1,
				createDate(-287, 8, 12), createDate(-287, 7, 19)));
		contenu.add(new Produit("Mac", ProduitEnum.FRUIT, 3, createDate(2012,
				9, 27), createDate(2007, 12, 4)));

		HashMap<Produit, Integer> ingred = new HashMap<Produit, Integer>();
		ingred.put(new Produit("Pomme", ProduitEnum.FRUIT, 2, new Date(),
				new Date()), 2);
		ingred.put(new Produit("Oeuf", ProduitEnum.OEUF, 4, new Date(),
				new Date()), 4);
		recettes.add(new Recette(
				"Tarte aux pommes",
				"1. Faire de la pâte\n2. Couper les pommes\n3. Faire cuire tout ce bordel 3 heures\n4. Démouler avant que ça prenne feu",
				ingred));

		ingred = new HashMap<Produit, Integer>();
		ingred.put(new Produit("iPhone", ProduitEnum.FRUIT, 1, new Date(),
				new Date()), 1);
		ingred.put(new Produit("Oeuf de poule malgache", ProduitEnum.OEUF, 4,
				new Date(), new Date()), 1);
		ingred.put(new Produit("Chocolat", ProduitEnum.PRODUIT_LAITIER, 1,
				new Date(), new Date()), 1);
		ingred.put(new Produit("Araignée", ProduitEnum.VIANDE, 18, new Date(),
				new Date()), 1);
		recettes.add(new Recette(
				"Schploutz de Madagascar",
				"1. Faire de la pâte\n2. Incorporer le piment rouge\n3. Broyer des araignées\n4. Couler le chocolat fondu\n5. Ajouter un iPhone\n6. Faire cuire 17 heures",
				ingred));

		Frigo machin = new Frigo(contenu, recettes);
		machin.save();
		System.out.println("sauvegardé -- lecture");

		// à la sortie ça doit donner le même frigo
		// sinon c'est que y a un bug
		machin.load();
		System.out.println("re-sauvegarde!");
		machin.save();
		System.out.println("fini");
	}*/

	public static Date createDate(int y, int m, int d) {
		Calendar gishan = new GregorianCalendar();
		gishan.set(y, m, d);
		return gishan.getTime();
	}

}
