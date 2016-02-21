package interfaceConsole;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.swing.TransferHandler;

import action.Action;
import action.ActionAjouterProduit;
import action.ActionAjouterRecette;
import action.ActionModifierRecette;
import action.ActionSupprimerPerimee;
import action.ActionSupprimerProduit;
import action.ActionSupprimerRecette;
import action.ActionVariation;
import produit.Produit;
import produit.ProduitEnum;
import recette.Recette;
import tries.TriesEnum;
import frigo.Frigo;

public class InterfaceConsole {

	public static void main(String[] args) {
		ArrayList<Produit> contenu = new ArrayList<Produit>();

		Produit lait = new Produit("Lait", ProduitEnum.PRODUIT_LAITIER, 1,
				Frigo.createDate(2014, 6, 29), Frigo.createDate(2014, 6, 20));
		contenu.add(lait);
		Produit pomme = new Produit("Pomme", ProduitEnum.FRUIT, 3,
				Frigo.createDate(2014, 6, 29), Frigo.createDate(2014, 6, 20));
		contenu.add(pomme);
		Produit viande = new Produit("Viande", ProduitEnum.VIANDE, 2,
				Frigo.createDate(2014, 6, 29), Frigo.createDate(2014, 6, 20));
		contenu.add(viande);
		Produit carotte = new Produit("Carotte", ProduitEnum.LEGUME, 4,
				Frigo.createDate(2014, 6, 29), Frigo.createDate(2014, 6, 20));
		contenu.add(carotte);
		Produit poisson = new Produit("Poisson", ProduitEnum.POISSON, 2,
				Frigo.createDate(2014, 6, 29), Frigo.createDate(2014, 6, 20));
		contenu.add(poisson);
		Produit eau = new Produit("Eau", ProduitEnum.BOISSON, 4,
				Frigo.createDate(2014, 6, 29), Frigo.createDate(2014, 6, 20));
		contenu.add(eau);
		Produit oeufs = new Produit("Oeufs", ProduitEnum.OEUF, 6,
				Frigo.createDate(2014, 6, 22), Frigo.createDate(2014, 6, 20));
		contenu.add(oeufs);
		Produit glaçon = new Produit("PatePizza", ProduitEnum.NON_REFERENCE,
				4, Frigo.createDate(2014, 6, 29), Frigo.createDate(2014, 6, 20));
		Produit tomate = new Produit("Tomate", ProduitEnum.FRUIT, 3,
				Frigo.createDate(2014, 6, 22), Frigo.createDate(2014, 6, 20));
		contenu.add(tomate);
		contenu.add(glaçon);
		HashMap<Produit, Integer> ingred = new HashMap<Produit, Integer>();
		ingred.put(tomate, 2);
		ingred.put(glaçon, 1);
		ArrayList<Recette> recettes = new ArrayList<Recette>();
		recettes.add(new Recette("Pizza", "Une pizza à l'italienne", ingred));

		Frigo frigo = new Frigo(contenu, recettes);
		Scanner scan1 = new Scanner(System.in);
		Scanner scan2 = new Scanner(System.in);
		Scanner scan3 = new Scanner(System.in);
		boolean quit = false;
		boolean mauvaisFormat = false;
		while (!quit) {
			System.out.println(frigo + "\n\n");
			System.out
					.println("Actions valides :             PRODUITS                  |                  TRIES                 |              RECETTES\n\n");
			System.out
					.println("                   'ap'  - ajouter produit                 'aff' - affichage du contenu              'ar'   - ajouter recette\n");
			System.out
					.println("                   'sp'  - supprimer produit               'tda' - trie par date d'ajout             'sr'   - supprimer recette\n");
			System.out
					.println("                   'spp' - enleve les produits perimes     'tde' - trie par date d'expiration        'mr'   - modifier recette\n");
			System.out
					.println("                   'mp'  - modifie la quantite produit     'tqc' - trie par quantite croissante      'afrr' - affiche recette realisable\n");
			System.out
					.println("                   'vp'  - verifie les produits            'tqd' - trie par quantite decroissante    'afpm' - afficher produits manquants\n");
			System.out
					.println("                                                           'tt'  - trie par type                     \n\n");

			System.out
					.println("----------------------------------------------------------------------------------------------------------------------------------------\n");
			System.out
					.println("                                 'rar' retour arriere - 'rav' retour avant - 'sa' sauvegarder - 'ch' charger - 'so' sortir\n");
			System.out.print("Veuillez choisir une action dans cette liste : ");

			String commande = scan1.next();

			switch (commande) {

			case "ap":
				System.out.println("AJOUT DE PRODUIT --- Type de produit");
				System.out.println("   0 - NON_REFERENCE");
				System.out.println("   1 - PRODUIT_LAITIER");
				System.out.println("   2 - BOISSON");
				System.out.println("   3 - VIANDE");
				System.out.println("   4 - POISSON");
				System.out.println("   5 - OEUF");
				System.out.println("   6 - LEGUME");
				System.out.println("   7 - FRUIT\n");
				System.out
						.println("Pour ajouter un nouveau produit, veuillez suivre ce format et appuyer sur entrer:");
				System.out
						.println("Type#;Nom;Date d'expiration (dd/MM/yyyy) (optionnel);quantite (optionnal) : ");

				String newProduitStr = scan2.nextLine();
				String[] newProduitTab = newProduitStr.split(";");
				Produit newProduit = new Produit();
				newProduit.setDateAjout(new Date());
				mauvaisFormat = false;
				switch (newProduitTab[0]) {
				case "0":
					newProduit.setType(ProduitEnum.NON_REFERENCE);
					break;
				case "1":
					newProduit.setType(ProduitEnum.PRODUIT_LAITIER);
					break;
				case "2":
					newProduit.setType(ProduitEnum.BOISSON);
					break;
				case "3":
					newProduit.setType(ProduitEnum.VIANDE);
					break;
				case "4":
					newProduit.setType(ProduitEnum.POISSON);
					break;
				case "5":
					newProduit.setType(ProduitEnum.OEUF);
					break;
				case "6":
					newProduit.setType(ProduitEnum.LEGUME);
					break;
				case "7":
					newProduit.setType(ProduitEnum.FRUIT);
					break;
				default:
					System.out.println("Mauvais type");
					mauvaisFormat = true;
					break;
				}
				if (!mauvaisFormat) {
					if (newProduitTab.length <= 1) {
						System.out.println("Veuillez saisir un nom");
						mauvaisFormat = true;
					} else {
						if (newProduitTab[1].equals("")) {
							System.out.println("Veuillez saisir un nom");
							mauvaisFormat = true;
						} else {
							newProduit.setNom(newProduitTab[1]);
							if (newProduitTab.length < 3) {
								if (!mauvaisFormat) {
									newProduit.setQuantite(1);
									ActionAjouterProduit actionAjout = new ActionAjouterProduit(
											frigo, newProduit);
									frigo.doAction(actionAjout);
								}
							} else {

								try {
									String[] dateStr = newProduitTab[2]
											.split("/");
									if (dateStr[0].equals("")) {

									} else if (dateStr.length != 3) {
										System.out.println("Mauvaise Date");
										mauvaisFormat = true;
									} else {
										Date date = Frigo
												.createDate(
														Integer.parseInt(dateStr[2]),
														Integer.parseInt(dateStr[1]) - 1,
														Integer.parseInt(dateStr[0]));

										newProduit.setDateDePeremption(date);
									}
								} catch (NumberFormatException e) {
									System.out.println("Mauvaise Date");
									mauvaisFormat = true;
								}
								if (!mauvaisFormat) {
									if (newProduitTab.length < 4) {
										newProduit.setQuantite(1);
									} else {
										try {
											if (Integer
													.parseInt(newProduitTab[3]) >= 1) {
												newProduit
														.setQuantite(Integer
																.parseInt(newProduitTab[3]));
											} else {
												System.out
														.println("Mauvaise Quantite");
												mauvaisFormat = true;
											}
										} catch (NumberFormatException e) {
											System.out
													.println("Mauvaise Quantite");
											mauvaisFormat = true;
										}
									}
								}
								if (!mauvaisFormat) {
									ActionAjouterProduit actionAjout = new ActionAjouterProduit(
											frigo, newProduit);
									frigo.doAction(actionAjout);
								}
							}
						}

					}

				}

				break;

			case "mp": {
				System.out.print("Entrez le numéro du produit : ");
				int numprod = Integer.parseInt(scan1.next()) - 1;
				ArrayList<Produit> listeprod = frigo.getContenu();

				if (numprod < 0 || numprod >= listeprod.size()) {
					System.out.println("Numéro de produit incorrect");
					break;
				}

				System.out.print("Entrez la nouvelle quantité du produit : ");
				int qte = Integer.parseInt(scan1.next());

				if (qte < 0) {
					System.out
							.println("Quantité incorrecte. A moins que vous désiriez un trou noir dans votre frigo ?");
					break;
				} else if (qte > 100) {
					System.out
							.println("Ah bah nan, y en a beaucoup trop là! Ca rentrera jamais dans ton frigo tout ça");
					break;
				}

				frigo.doAction(new ActionVariation(frigo, numprod, qte));
			
				break;
			}
			case "sp": {
				System.out.print("Entrez le numéro du produit : ");
				int numprod = Integer.parseInt(scan1.next()) - 1;
				ArrayList<Produit> listeprod = frigo.getContenu();

				if (numprod < 0 || numprod >= listeprod.size()) {
					System.out.println("Numéro de produit incorrect");
					break;
				}

				Produit prod = listeprod.get(numprod);
				System.out.print("Voulez-vous vraiment supprimer le produit "
						+ numprod + " (" + prod.getNom() + ") ? (O/N) ");
				String reponse = scan1.next();
				if (!reponse.equals("O") && !reponse.equals("o"))
					break;

				frigo.doAction(new ActionSupprimerProduit(frigo, prod));
			
				break;
			}
			case "spp": {
				System.out
						.print("Voulez-vous vraiment supprimer les produits périmés ? (O/N) ");
				String reponse = scan1.next();
				if (!reponse.equals("O") && !reponse.equals("o"))
					break;

				frigo.doAction(new ActionSupprimerPerimee(frigo));
			
				break;
			}
			case "vp": {
				Date today = new Date();
				System.out
						.print("Veuillez rentrer un nombre de jours qui affichera,entre aujourdhui et ce nombre, les futures produits perimés : ");

				try {
					int numdate = Integer.parseInt(scan1.next()) - 1;
					boolean pasDePerime = true;
					for (int i = 0; i < frigo.getContenu().size(); i++) {
						Produit prod = frigo.getContenu().get(i);
						long tempsNbrJour = 1000 * 60 * 60 * 24 * numdate; // conversion
																			// en
																			// milliseconde
						Date dateMoinsNbrJour = new Date(prod
								.getDateDePeremption().getTime() - tempsNbrJour);
						if (dateMoinsNbrJour.before(today)) {
							long temps = prod.getDateDePeremption().getTime()
									- today.getTime();
							if (temps < 0) {
								System.out.println("Le produit "
										+ prod.getNom() + " est déjà perimé");
							} else {
								System.out.println("Le produit "
										+ prod.getNom() + " va perimer dans "
										+ (int) (temps / (1000 * 60 * 60))
										+ " heures");
							}
							pasDePerime = false;
						}

					}
					if (pasDePerime)
						System.out
								.println("Il n'y a pas de produit perimee ou qui vont perimer dans "
										+ numdate + " jours");
					System.out.println("\n");
				} catch (NumberFormatException e) {
					System.out.println("Vous n'avez pas rentré un nombre");
				}

				break;
			}

			case "tda":
				frigo.setTypeTrie(TriesEnum.TRIE_DATE_AJOUT);
				break;

			case "tde":
				frigo.setTypeTrie(TriesEnum.TRIE_DATE_PEREMPTION);
				break;

			case "tqc":
				frigo.setTypeTrie(TriesEnum.TRIE_QUANTITE_CROISSANT);
				break;

			case "tqd":
				frigo.setTypeTrie(TriesEnum.TRIE_QUANTITE_DECROISSANT);
				break;

			case "tt":
				frigo.setTypeTrie(TriesEnum.TRIE_TYPE);
				break;

			case "aff":
				break;

			case "ch":
				frigo.load();
				System.out.println("Votre frigo a bien été chargé!");
				break;

			case "sa":
				frigo.save();
				System.out.println("Votre frigo a bien été sauvegardé!");
				break;

			case "rar":
				frigo.undoAction();
				break;

			case "rav":
				frigo.redoAction();
				break;

			case "so":
				return;

			case "ar":
				System.out
						.println("Pour ajouter une nouvelle recette, veuillez suivre ce format et appuyer sur entrer:");
				System.out
						.println("Nom;Ingredient1,Quantite/Ingredient2,Quantite...;Description(optionnel) ");

				String newRecetteStr = scan3.nextLine();
				String[] newRecetteTab = newRecetteStr.split(";");
				Recette newRecette = new Recette();
				newRecette.setNom(newRecetteTab[0]);
				boolean mauvaiseSyntaxe = false;
				HashMap<Produit, Integer> listIngredients = new HashMap<Produit, Integer>();
				try {
					String[] IngredientTab = newRecetteTab[1].split("/");
					for (String ingredients : IngredientTab) {
						try {
							String[] sep = ingredients.split(",");
							listIngredients.put(new Produit(sep[0]),
									Integer.parseInt(sep[1]));
						} catch (IndexOutOfBoundsException e) {
							System.out
									.println("Mauvaise saisie des ingredients");
							mauvaiseSyntaxe = true;
						} catch (NumberFormatException e) {
							System.out
									.println("Mauvaise saisie des ingredients");
							mauvaiseSyntaxe = true;
						}
					}
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Mauvaise saisie des ingredients");
					mauvaiseSyntaxe = true;
				}
				if (!listIngredients.isEmpty()) {
					newRecette.setIngredients(listIngredients);
				}
				if (newRecetteTab.length > 2) {
					newRecette.setDescription(newRecetteTab[2]);
				}
				if (!mauvaiseSyntaxe) {
					ActionAjouterRecette ajoutRecette = new ActionAjouterRecette(
							frigo, newRecette);
					frigo.doAction(ajoutRecette);
				}
				break;

			case "sr":
				System.out.print("Entrez le numéro de la recette : ");
				int numprod = Integer.parseInt(scan1.next()) - 1;
				ArrayList<Recette> listeRecette = frigo.getListeRecette();

				if (numprod < 0 || numprod >= listeRecette.size()) {
					System.out.println("Numéro de recette incorrect");
					break;
				}
				Recette rece = listeRecette.get(numprod);
				System.out.print("Voulez-vous vraiment supprimer la recette "
						+ (numprod + 1) + " (" + rece.getNom() + ") ? (O/N) ");
				String reponse = scan1.next();
				if (!reponse.equals("O") && !reponse.equals("o"))
					break;

				frigo.doAction(new ActionSupprimerRecette(frigo, rece));
				break;

			case "mr":
				System.out
						.print("Entrez le numéro de la recette a modifier : ");
				int numprod2 = Integer.parseInt(scan1.next()) - 1;

				if (numprod2 < 0 || numprod2 >= frigo.getListeRecette().size()) {
					System.out.println("Numéro de produit incorrect");
					break;
				}
				System.out
						.println("Pour modifier la recette, veuillez suivre ce format et appuyer sur entrer:");
				System.out
						.println("Nom;Ingredient1,Quantite/Ingredient2,Quantite...;Description(optionnel) ");

				String modRecetteStr = scan3.nextLine();
				boolean mauvaiseSyntaxe2 = false;
				Recette modRecette = new Recette();
				try {
					String[] modRecetteTab = modRecetteStr.split(";");
					
					modRecette.setNom(modRecetteTab[0]);
					
					HashMap<Produit, Integer> listIngredients2 = new HashMap<Produit, Integer>();
					try {

						String[] IngredientTab = modRecetteTab[1].split("/");
						for (String ingredients : IngredientTab) {
							try {
								String[] sep = ingredients.split(",");
								listIngredients2.put(new Produit(sep[0]),
										Integer.parseInt(sep[1]));
							} catch (IndexOutOfBoundsException e) {
								System.out
										.println("Mauvaise saisie des ingredients");
								mauvaiseSyntaxe2 = true;
							} catch (NumberFormatException e) {
								System.out
										.println("Mauvaise saisie des ingredients");
								mauvaiseSyntaxe2 = true;
							}
						}
						if (!listIngredients2.isEmpty()) {
							modRecette.setIngredients(listIngredients2);
						}
						if (modRecetteTab.length > 2) {
							modRecette.setDescription(modRecetteTab[2]);
						}
					} catch (IndexOutOfBoundsException e) {
						System.out.println("Mauvaise saisie des ingredients");
						mauvaiseSyntaxe2 = true;
					}
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Mauvaise saisie des ingredients");
					mauvaiseSyntaxe2 = true;
				}

				if (!mauvaiseSyntaxe2) {
					
					ActionModifierRecette modifRecette = new ActionModifierRecette(
							frigo, frigo.getListeRecette().get(numprod2),
							modRecette);
					frigo.doAction(modifRecette);
				}
				break;

			case "afrr":

				ArrayList<Recette> recetteRealisable = new ArrayList<Recette>();
				if (!frigo.getContenu().isEmpty()) {
					for (Recette recettesContenu : frigo.getListeRecette()) {
						boolean isRealisable = true;
						for (Entry<Produit, Integer> p : recettesContenu
								.getIngredients().entrySet()) {
							boolean isIngrediant = false;
							for (int j = 0; j < frigo.getContenu().size(); j++) {

								if ((p.getKey().getNom().equals(frigo
										.getContenu().get(j).getNom()))
										&& p.getValue() <= frigo.getContenu()
												.get(j).getQuantite()) {
									isIngrediant = true;
									break;

								}
							}
							if (!isIngrediant) {
								isRealisable = false;
								break;
							}
						}

						if (isRealisable) {
							recetteRealisable.add(recettesContenu);
						}
					}
					if (!recetteRealisable.isEmpty()) {
						System.out
								.println("Les Recettes réalisables avec le contenu de votre frigo sont :");
						for (Recette recettesRealisable : recetteRealisable) {
							System.out.println(recettesRealisable + "\n");
						}
					} else {
						System.out.println("Aucune Recette n'est réalisable.");
					}
				} else {
					System.out.println("Aucune Recette n'est réalisable.");
				}

				break;

			case "afpm":
				
				HashMap<Produit, Integer> produitManquant = new HashMap<Produit, Integer>();

				for (Recette recettesContenu : frigo.getListeRecette()) {
					boolean isRealisable = true;
					for (Entry<Produit, Integer> p : recettesContenu
							.getIngredients().entrySet()) {
						boolean isIngrediant = false;
						boolean isQuantity = false;
						for (int j = 0; j < frigo.getContenu().size(); j++) {

							if (p.getKey().getNom()
									.equals(frigo.getContenu().get(j).getNom())) {
								if (p.getValue() <= frigo.getContenu().get(j)
										.getQuantite()) {
									isIngrediant = true;
									break;

								} else {
									produitManquant.put(p.getKey(),
											p.getValue()
													- frigo.getContenu().get(j)
															.getQuantite());
									isQuantity = true;
								}
							}
						}
						if (!isIngrediant && !isQuantity) {

							produitManquant.put(p.getKey(), p.getValue());
							break;
						}
					}

				}
				if (!produitManquant.isEmpty()) {
					System.out
							.println("Les Produit manquants pour réaliser vos Recettes sont :");
					for (Entry<Produit, Integer> p : produitManquant.entrySet()) {
						System.out.println("  - " + p.getValue()
								+ " quantité de " + p.getKey().getNom() + "\n");
					}
				} else {
					System.out
							.println("Vous pouvez réaliser toute vos recettes!");
				}

				break;
			default:
				System.out.println("Commande inconnue");
				break;

			}
			commande = "";
		}
		scan1.close();
		scan2.close();

	}
}
