package interfaceGraphique;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.TransferHandler;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import action.ActionAjouterProduit;
import action.ActionAjouterRecette;
import action.ActionCuisiner;
import action.ActionModifierRecette;
import action.ActionSupprimerPerimee;
import action.ActionSupprimerProduit;
import action.ActionSupprimerRecette;
import action.ActionVariation;
import produit.Produit;
import produit.ProduitEnum;
import produit.ProduitView;
import recette.Recette;
import tries.ComparatorDateAjout;
import tries.ComparatorDatePerime;
import tries.ComparatorQuantiteCroissant;
import tries.ComparatorQuantiteDecroissant;
import tries.ComparatorType;
import exception.ProduitNonTrouveException;
import frigo.Frigo;

public class InterfaceGraphique extends JFrame {
	private Frigo frigo;
	private ArrayList<JPanel> btnProduits;
	private ArrayList<JPanel> btnRecettes;
	private int idProdSel = -1;
	private JPanel btnProdSel = null;
	private int idRecSel = -1;
	private JPanel btnRecSel = null;
	private TransferHandler dragAndDrop;
	private int onglet = 0;
	private JPanel pContainer, rContainer;

	private ChangeListener tabbedPaneListener = new ChangeListener() {

		public void stateChanged(ChangeEvent arg0) {
			if (onglet == 0) {
				onglet = 1;
			} else {
				onglet = 0;
			}

		}

	};

	private class ProduitSelectionListener implements MouseListener {
		private int index;

		public ProduitSelectionListener(int n) {
			index = n;
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			setProduitSelection(index);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
	}

	private class RecetteSelectionListener implements MouseListener {
		private int index;

		public RecetteSelectionListener(int n) {
			index = n;
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			setRecetteSelection(index);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
	}

	private class IconeRecetteListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int x = arg0.getX();
			int y = arg0.getY();

			if (x >= 5 && x <= 40 && y >= 10 && y <= 45) {
				// aff recette realisable
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
						String info = "Les recettes realisables sont: \n\n";

						for (Recette recetteRea : recetteRealisable) {
							info = info + "-" + recetteRea.getNom() + "\n\n";

						}
						JOptionPane.showMessageDialog(InterfaceGraphique.this,
								info, "Recettes réalisables",
								JOptionPane.INFORMATION_MESSAGE, new ImageIcon(
										"resources/iconeRecette2.png"));
					} else {
						JOptionPane noRece = new JOptionPane();
						JOptionPane.showMessageDialog(noRece,
								"Aucune recette réalisable",
								"Recettes réalisables",
								JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane noRece = new JOptionPane();
					JOptionPane
							.showMessageDialog(noRece,
									"Aucune recette réalisable",
									"Recettes réalisables",
									JOptionPane.WARNING_MESSAGE);
				}

			}

			else if (x >= 5 && x <= 40 && y >= 55 && y <= 90) {
				// aff produit manquant
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
					if (!produitManquant.isEmpty()) {
						String info = "Les Produit manquants sont: \n\n";

						for (Entry<Produit, Integer> pro : produitManquant.entrySet()) {
							info = info + "-" + pro.getKey().getNom() + " Quantité :"+pro.getValue()+"\n\n";

						}
						JOptionPane.showMessageDialog(InterfaceGraphique.this,
								info, "Produit manquant",
								JOptionPane.INFORMATION_MESSAGE, new ImageIcon(
										"resources/iconeFrigo.png"));
					} else {
						JOptionPane noRece = new JOptionPane();
						JOptionPane.showMessageDialog(noRece,
								"Aucuneproduit n'est manquant",
								"Produit Manquant",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
			else if (x >= 5 && x <= 40 && y >= 100 && y <= 145) {
					int res = JOptionPane
							.showConfirmDialog(
									InterfaceGraphique.this,
									"Etes-vous sûr de vouloir supprimer tous les produits de la recette ?",
									"SmartFridge", JOptionPane.YES_NO_OPTION,
									JOptionPane.QUESTION_MESSAGE);
					if (res == 0) {
						try
						{
							frigo.doAction(new ActionCuisiner(frigo,frigo.getListeRecette().get(idRecSel)));
							fillProdList();
							setProduitSelection(-1);
						}
						catch (RuntimeException e)
						{
							JOptionPane
							.showMessageDialog(InterfaceGraphique.this,
									"Cette recette ne peut pas être cuisinée car il manque un ou plusieurs ingrédients.",
									"Erreur",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				
				}
					
				
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
	}

	private class MenuPrincipalListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			int x = arg0.getX();
			int y = arg0.getY();

			if (x >= 58 && x <= 162 && y >= 16 && y <= 110) {
				// suppr produit
				if (idProdSel != -1 && onglet == 0) {
					frigo.doAction(new ActionSupprimerProduit(frigo, frigo
							.getContenu().get(idProdSel)));
					fillProdList();
					setProduitSelection(-1);
				} else if (idRecSel != -1 && onglet == 1) {
					frigo.doAction(new ActionSupprimerRecette(frigo, frigo
							.getListeRecette().get(idRecSel)));
					fillRecList();
					setRecetteSelection(-1);
				}

			} else if (x >= 230 && x <= 336 && y >= 16 && y <= 110) {
				// ajout produit
				if (onglet == 0) {
					EditProduitDialog dlg = new EditProduitDialog(
							InterfaceGraphique.this, null);
					dlg.setVisible(true);
					Produit prod = dlg.getProduit();
					if (prod != null) {
						frigo.doAction(new ActionAjouterProduit(frigo, prod));
						fillProdList();
						setProduitSelection(idProdSel);
					}
				}
				// ajout recette
				else {
					EditRecetteDialog dlr = new EditRecetteDialog(
							InterfaceGraphique.this, null);
					dlr.setVisible(true);

					Recette recette = dlr.getRecette();
					if (recette != null) {
						frigo.doAction(new ActionAjouterRecette(frigo, recette));
						fillRecList();
						setProduitSelection(idRecSel);
					}
				}
			}

			else if (x >= 165 && x <= 232 && y >= 90 && y <= 150) {
				// Ouvre une nouvelle fenetre qui indique toutes les
				// informations sur le produit

				if (onglet == 0 && idProdSel != -1) {
					infoProduit(idProdSel);
				} else if (onglet == 1 && idRecSel != -1) {
					infoRecette(idRecSel);
				}
			} else if (x >= 5 && x <= 40 && y >= 15 && y <= 45) {
				frigo.undoAction();
				fillProdList();
				setProduitSelection(idProdSel);
			} else if (x >= 5 && x <= 40 && y >= 50 && y <= 82) {
				frigo.redoAction();
				fillProdList();
				setProduitSelection(idProdSel);
			} else if (x >= 5 && x <= 40 && y >= 89 && y <= 119) {

				JOptionPane confirm = new JOptionPane();
				String[] options = { "Oui", "Non" };
				int n = JOptionPane
						.showOptionDialog(
								confirm,
								"Etes vous sur de vouloir charger le Smart-Fridge a partir de votre sauvegarde?",
								"Chargement du Smart Fridge",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE, new ImageIcon(
										"resources/download_icon1.png"),
								options, options[1]);
				if (n == 0) {
					JOptionPane ok = new JOptionPane();
					JOptionPane.showMessageDialog(ok,
							"Votre frigo a bien été chargé",
							"Chargement du Smart Fridge",
							JOptionPane.INFORMATION_MESSAGE);
					frigo.load();
					fillProdList();
					setProduitSelection(idProdSel);
				}
			} else if (x >= 5 && x <= 40 && y >= 126 && y <= 156) {
				JOptionPane confirm = new JOptionPane();
				String[] options = { "Oui", "Non" };
				int n = JOptionPane
						.showOptionDialog(
								confirm,
								"Etes vous sur de vouloir sauvegarder vos données?",
								"Sauvegarde du Smart Fridge",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE, new ImageIcon(
										"resources/save_icon.gif"), options,
								options[1]);
				if (n == 0) {
					JOptionPane ok = new JOptionPane();
					JOptionPane.showMessageDialog(ok,
							"Votre frigo a bien été sauvgardé",
							"Sauvegarde du Smart Fridge",
							JOptionPane.INFORMATION_MESSAGE);
					frigo.save();
					fillProdList();
					setProduitSelection(idProdSel);
				}

			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
	}

	private void infoProduit(int id) {

		EditInfoProdDialog eipd = new EditInfoProdDialog(this, id, frigo);
		eipd.setVisible(true);
		if (frigo.getContenu().get(id).getQuantite() != eipd.getQte()) {
			frigo.doAction(new ActionVariation(frigo, id, eipd.getQte()));
			fillProdList();
			setProduitSelection(idProdSel);
		}

	}

	private void infoRecette(int id) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Recette rec = frigo.getListeRecette().get(id);
		String info;

		info = rec.getNom() + "\n\n" + rec.getDescription() + "\n\n"
				+ "Ingrédients nécessaires :\n";

		for (Entry<Produit, Integer> ingred : rec.getIngredients().entrySet()) {
			Produit prod = ingred.getKey();
			int qte = ingred.getValue();

			info += " - " + qte + "x " + prod.getNom() + "\n";
		}

		JOptionPane.showMessageDialog(InterfaceGraphique.this, info,
				"Informations sur la recette", JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon("resources/iconeRecette2.png"));
	}

	private void setProduitSelection(int n) {
		idRecSel = -1;
		btnRecSel = null;

		for (JPanel p : btnRecettes)
			p.setBackground(Color.white);

		if (n == -1) {
			idProdSel = -1;
			btnProdSel = null;
		} else {
			idProdSel = n;
			btnProdSel = btnProduits.get(n);
		}

		for (JPanel p : btnProduits)
			p.setBackground(Color.white);
		if (btnProdSel != null) {
			btnProdSel.setBackground(new Color(200, 230, 255));

		}
	}

	private void setRecetteSelection(int n) {
		idProdSel = -1;
		btnProdSel = null;

		for (JPanel p : btnProduits)
			p.setBackground(Color.white);

		if (n == -1) {
			idRecSel = -1;
			btnRecSel = null;
		} else {
			idRecSel = n;
			btnRecSel = btnRecettes.get(n);
		}

		for (JPanel p : btnRecettes)
			p.setBackground(Color.white);
		if (btnRecSel != null) {
			btnRecSel.setBackground(new Color(200, 230, 255));

		}
	}

	private void fillProdList() {
		pContainer.removeAll();
		btnProduits.clear();

		for (int i = 0; i < frigo.getContenu().size(); i++) {
			JPanel pContainerLigne = new JPanel();
			pContainer.add(Box.createRigidArea(new Dimension(0, 20)));
			BoxLayout gridLayoutLigne = new BoxLayout(pContainerLigne,
					BoxLayout.X_AXIS);
			pContainerLigne.setLayout(gridLayoutLigne);
			pContainerLigne.setBackground(Color.white);
			for (int j = i; j < i + 4 && j < frigo.getContenu().size(); j++) {
				JPanel pContainerBox = new JPanel();
				JLabel label = new JLabel(
						frigo.getContenu().get(j).getNom(),
						new ImageIcon("resources/"
								+ frigo.getContenu().get(j).getType() + ".png"),
						JLabel.CENTER);
				label.setVerticalTextPosition(JLabel.BOTTOM);
				label.setHorizontalTextPosition(JLabel.CENTER);
				JLabel label2 = new JLabel(" ", JLabel.CENTER);
				JLabel label3 = new JLabel(" ", JLabel.CENTER);
				label2.setText("  Quantité: "
						+ frigo.getContenu().get(j).getQuantite());
				label2.setFont(new Font("Calibri", Font.ITALIC, 10));

				Date dateper = frigo.getContenu().get(j).getDateDePeremption();
				if (dateper != null && dateper.before(new Date())) {
					label3.setText("    Perimée!");
					label3.setFont(new Font("Calibri", Font.BOLD, 10));
					label3.setForeground(Color.red);
					label3.setVerticalTextPosition(JLabel.BOTTOM);
					label3.setHorizontalTextPosition(JLabel.CENTER);
				}

				label2.setVerticalTextPosition(JLabel.BOTTOM);
				label2.setHorizontalTextPosition(JLabel.CENTER);

				pContainerBox.add(label);
				pContainerBox.add(label2);
				pContainerBox.add(label3);
				BoxLayout gridLayoutBox = new BoxLayout(pContainerBox,
						BoxLayout.Y_AXIS);
				pContainerBox.setLayout(gridLayoutBox);
				pContainerBox.setBackground(Color.white);

				pContainerLigne.add(Box.createRigidArea(new Dimension(16, 0)));
				pContainerLigne.add(pContainerBox);

				pContainerBox.addMouseListener(new ProduitSelectionListener(j));
				btnProduits.add(pContainerBox);

			}
			pContainerLigne.add(Box.createHorizontalGlue());

			pContainer.add(pContainerLigne);

			pContainer.add(Box.createRigidArea(new Dimension(0, 20)));
			i = i + 3;

		}

		repaint();
	}

	private void fillRecList() {
		rContainer.removeAll();
		btnRecettes.clear();

		for (int i = 0; i < frigo.getListeRecette().size(); i++) {
			rContainer.add(Box.createRigidArea(new Dimension(0, 15)));

			JPanel rContainerLigne = new JPanel();
			BoxLayout gridLayoutLigneR = new BoxLayout(rContainerLigne,
					BoxLayout.X_AXIS);
			rContainerLigne.setLayout(gridLayoutLigneR);
			rContainerLigne.setBackground(Color.white);
			JLabel label = new JLabel(new ImageIcon(
					"resources/iconeRecette2.png"), JLabel.CENTER);

			JPanel rContainerBox = new JPanel();
			BoxLayout gridLayoutBoxR = new BoxLayout(rContainerBox,
					BoxLayout.Y_AXIS);
			rContainerBox.setLayout(gridLayoutBoxR);
			rContainerBox.setBackground(Color.white);

			JLabel label3 = new JLabel(frigo.getListeRecette().get(i).getNom(),
					JLabel.CENTER);
			JLabel label2 = new JLabel(" ", JLabel.CENTER);
			label2.setText(frigo.getListeRecette().get(i).getDescription());
			label2.setFont(new Font("Calibri", Font.ITALIC, 10));
			label2.setHorizontalTextPosition(JLabel.CENTER);

			rContainerBox.setBackground(null);

			rContainerBox.add(label3);
			rContainerBox.add(label2);
			rContainerLigne.add(Box.createRigidArea(new Dimension(15, 0)));
			rContainerLigne.add(label);
			rContainerLigne.add(Box.createRigidArea(new Dimension(5, 0)));
			rContainerLigne.add(rContainerBox);
			rContainerLigne.add(Box.createHorizontalGlue());

			rContainer.add(rContainerLigne);

			rContainerLigne.addMouseListener(new RecetteSelectionListener(i));
			btnRecettes.add(rContainerLigne);

			rContainer.add(Box.createRigidArea(new Dimension(0, 15)));

		}

		repaint();
	}

	public InterfaceGraphique() {

		ArrayList<Produit> contenu = new ArrayList<Produit>();
		btnProduits = new ArrayList<JPanel>();
		btnRecettes = new ArrayList<JPanel>();
		dragAndDrop = new TransferHandler("drag&drop");

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
		Produit glacon = new Produit("PatePizza", ProduitEnum.NON_REFERENCE,
				4, Frigo.createDate(2014, 6, 29), Frigo.createDate(2014, 6, 20));
		Produit tomate = new Produit("Tomate", ProduitEnum.FRUIT, 3,
				Frigo.createDate(2014, 6, 22), Frigo.createDate(2014, 6, 20));
		contenu.add(tomate);
		contenu.add(glacon);
		contenu.add(viande);
		contenu.add(viande);
		contenu.add(viande);
		contenu.add(viande);
		contenu.add(viande);
		HashMap<Produit, Integer> ingred = new HashMap<Produit, Integer>();
		ingred.put(tomate, 2);
		ingred.put(glacon, 1);
		ArrayList<Recette> recettes = new ArrayList<Recette>();
		recettes.add(new Recette("Pizza", "Une pizza à l'italienne", ingred));

		frigo = new Frigo(contenu, recettes);

		setSize(365, 620);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		setResizable(false);
		Container pane = getContentPane();
		pane.setBackground(Color.white);
		pane.setLayout(new FlowLayout(0, 0, 0));

		JPanel titre = new JPanel();
		titre.setPreferredSize(new Dimension(350, 50));
		titre.add(new JLabel(new ImageIcon("resources/logo.jpg")));
		titre.setBackground(Color.white);

		JPanel menuOnglet1 = new JPanel();
		menuOnglet1.setPreferredSize(new Dimension(350, 340));
		menuOnglet1.setBackground(Color.white);
		BoxLayout gridLayoutMenuOnglet1 = new BoxLayout(menuOnglet1,
				BoxLayout.X_AXIS);
		menuOnglet1.setLayout(gridLayoutMenuOnglet1);

		JPanel menuContextuel1 = new JPanel();
		menuContextuel1.setPreferredSize(new Dimension(40, 340));
		BoxLayout gridLayoutMenuContextuel = new BoxLayout(menuContextuel1,
				BoxLayout.Y_AXIS);
		menuContextuel1.setLayout(gridLayoutMenuContextuel);
		menuContextuel1.setBackground(Color.white);

		menuContextuel1.add(Box.createRigidArea(new Dimension(0, 6)));
		JLabel trieProduitIcone = new JLabel(" ", new ImageIcon(
				"resources/iconeListe.png"), JLabel.CENTER);
		trieProduitIcone.setFont(new Font("Calibri", Font.BOLD, 1));
		trieProduitIcone.setHorizontalTextPosition(JLabel.LEFT);
		trieProduitIcone.setVerticalTextPosition(JLabel.BOTTOM);
		menuContextuel1.add(trieProduitIcone);
		menuContextuel1.add(Box.createRigidArea(new Dimension(0, 6)));

		JLabel affPerimeIcone = new JLabel(" ", new ImageIcon(
				"resources/iconeCheck.png"), JLabel.CENTER);
		affPerimeIcone.setFont(new Font("Calibri", Font.BOLD, 1));
		affPerimeIcone.setHorizontalTextPosition(JLabel.LEFT);
		affPerimeIcone.setVerticalTextPosition(JLabel.BOTTOM);
		menuContextuel1.add(affPerimeIcone);
		menuContextuel1.add(Box.createRigidArea(new Dimension(0, 6)));

		JLabel suppPerimeIcone = new JLabel(" ", new ImageIcon(
				"resources/iconeSupPerime.png"), JLabel.CENTER);
		suppPerimeIcone.setFont(new Font("Calibri", Font.BOLD, 1));
		suppPerimeIcone.setHorizontalTextPosition(JLabel.LEFT);
		suppPerimeIcone.setVerticalTextPosition(JLabel.BOTTOM);
		menuContextuel1.add(suppPerimeIcone);
		menuContextuel1.add(Box.createVerticalGlue());

		trieProduitIcone.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				TrieDialog trie = new TrieDialog(InterfaceGraphique.this, frigo);
				trie.setVisible(true);
				frigo.setTypeTrie(trie.getFrigo().getTypeTrie());
				switch (frigo.getTypeTrie()) {
				case TRIE_DATE_AJOUT:
					Collections.sort(frigo.getContenu(),
							new ComparatorDateAjout());
					break;
				case TRIE_DATE_PEREMPTION:
					Collections.sort(frigo.getContenu(),
							new ComparatorDatePerime());
					break;
				case TRIE_QUANTITE_CROISSANT:
					Collections.sort(frigo.getContenu(),
							new ComparatorQuantiteCroissant());
					break;
				case TRIE_QUANTITE_DECROISSANT:
					Collections.sort(frigo.getContenu(),
							new ComparatorQuantiteDecroissant());
					break;
				case TRIE_TYPE:
					Collections.sort(frigo.getContenu(), new ComparatorType());
					break;
				default:
					break;

				}

				fillProdList();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		affPerimeIcone.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String ret = JOptionPane.showInputDialog(
						InterfaceGraphique.this, "Nombre de jours :",
						"Vérification des produits bientôt périmés",
						JOptionPane.QUESTION_MESSAGE);
				if (ret != null) {
					try {
						int numjours = Integer.parseInt(ret);
						Calendar cal = Calendar.getInstance();
						cal.setTime(new Date());
						cal.add(Calendar.DATE, numjours*12);
						Date dateper = cal.getTime();

						for (int i = 0; i < btnProduits.size(); i++) {
							if (frigo.getContenu().get(i).getDateDePeremption()
									.before(dateper))
								btnProduits.get(i).setBackground(
										new Color(255, 128, 128));
						}

						repaint();
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(InterfaceGraphique.this,
								"Mauvais nombre entré", "Erreur",
								JOptionPane.ERROR_MESSAGE);
					}
				}

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		suppPerimeIcone.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int res = JOptionPane
						.showConfirmDialog(
								InterfaceGraphique.this,
								"Etes-vous sûr de vouloir supprimer tous les produits périmés ?",
								"SmartFridge", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (res == 0) {
					frigo.doAction(new ActionSupprimerPerimee(frigo));
					fillProdList();
					setProduitSelection(-1);
				}

				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		MenuPrincipal menu = new MenuPrincipal();
		menu.setPreferredSize(new Dimension(350, 200));
		menu.addMouseListener(new MenuPrincipalListener());
		pContainer = new JPanel();
		BoxLayout gridLayout = new BoxLayout(pContainer, BoxLayout.Y_AXIS);
		pContainer.setLayout(gridLayout);

		fillProdList();
		pContainer.setBackground(Color.white);
		pContainer.addMouseListener(new ProduitSelectionListener(-1));
		JScrollPane scroll = new JScrollPane(pContainer);

		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setViewportBorder(new LineBorder(Color.GRAY));
		scroll.getViewport().setBackground(Color.white);
		scroll.setPreferredSize(new Dimension(310, 340));

		JTabbedPane tabbedPane = new JTabbedPane();
		ImageIcon iconeFrigo = new ImageIcon("resources/iconeFrigo.png");
		ImageIcon iconeRecette = new ImageIcon("resources/iconeRecette.png");

		menuOnglet1.add(menuContextuel1);
		menuOnglet1.add(scroll);

		JPanel menuOnglet2 = new JPanel();
		menuOnglet2.setPreferredSize(new Dimension(350, 340));
		menuOnglet2.setBackground(Color.white);
		BoxLayout gridLayoutMenuOnglet2 = new BoxLayout(menuOnglet2,
				BoxLayout.X_AXIS);
		menuOnglet2.setLayout(gridLayoutMenuOnglet2);

		JPanel menuContextuel2 = new JPanel();
		menuContextuel2.setPreferredSize(new Dimension(40, 340));
		BoxLayout gridLayoutMenuContextuel2 = new BoxLayout(menuContextuel2,
				BoxLayout.Y_AXIS);
		menuContextuel2.setLayout(gridLayoutMenuContextuel2);
		menuContextuel2.setBackground(Color.white);

		menuContextuel2.add(Box.createRigidArea(new Dimension(0, 6)));
		JLabel affRecetteReaIcone = new JLabel(" ", new ImageIcon(
				"resources/iconeRecettePossible.png"), JLabel.CENTER);
		affRecetteReaIcone.setFont(new Font("Calibri", Font.BOLD, 1));
		affRecetteReaIcone.setHorizontalTextPosition(JLabel.LEFT);
		affRecetteReaIcone.setVerticalTextPosition(JLabel.BOTTOM);
		menuContextuel2.add(affRecetteReaIcone);
		menuContextuel2.add(Box.createRigidArea(new Dimension(0, 6)));

		JLabel affManquantIcone = new JLabel(" ", new ImageIcon(
				"resources/iconeProduitManquant.png"), JLabel.CENTER);
		affManquantIcone.setFont(new Font("Calibri", Font.BOLD, 1));
		affManquantIcone.setHorizontalTextPosition(JLabel.LEFT);
		affManquantIcone.setVerticalTextPosition(JLabel.BOTTOM);
		menuContextuel2.add(affManquantIcone);
		menuContextuel2.add(Box.createRigidArea(new Dimension(0, 6)));

		JLabel cuisinerIcone = new JLabel(" ", new ImageIcon(
				"resources/iconeCuisiner.png"), JLabel.CENTER);
		cuisinerIcone.setFont(new Font("Calibri", Font.BOLD, 1));
		cuisinerIcone.setHorizontalTextPosition(JLabel.LEFT);
		cuisinerIcone.setVerticalTextPosition(JLabel.BOTTOM);
		menuContextuel2.add(cuisinerIcone);
		menuContextuel2.add(Box.createVerticalGlue());
		menuContextuel2.addMouseListener(new IconeRecetteListener());

		rContainer = new JPanel();
		BoxLayout gridLayoutR = new BoxLayout(rContainer, BoxLayout.Y_AXIS);
		rContainer.setLayout(gridLayoutR);
		fillRecList();
		rContainer.setBackground(Color.white);
		rContainer.addMouseListener(new RecetteSelectionListener(-1));

		JScrollPane scroll2 = new JScrollPane(rContainer);

		scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll2.setViewportBorder(new LineBorder(Color.GRAY));
		scroll2.getViewport().setBackground(Color.white);
		scroll2.setPreferredSize(new Dimension(310, 340));

		menuOnglet2.add(menuContextuel2);
		menuOnglet2.add(scroll2);

		tabbedPane.addTab("Vos Produits", iconeFrigo, menuOnglet1,
				"Le contenu de votre Smart Fridge");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		tabbedPane.setBackground(Color.white);
		tabbedPane.addTab("Vos Recettes", iconeRecette, menuOnglet2,
				"Toutes vos Recettes");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		tabbedPane.setBackground(Color.white);
		tabbedPane.addChangeListener(tabbedPaneListener);
		// tabbedPane.addTab("Vos Recettes", iconeRecette, menuOnglet1,
		// "Toutes vos recettes");
		// // tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		pane.add(titre);
		pane.add(tabbedPane);
		pane.add(menu);

		this.setTransferHandler(dragAndDrop);
		setVisible(true);
	}

	public static void main(String[] args) {

		InterfaceGraphique smartFridge = new InterfaceGraphique();

	}
}