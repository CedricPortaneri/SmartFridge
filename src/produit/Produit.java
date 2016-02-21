package produit;

import java.util.Date;

public class Produit {
	
	private String nom;
	private ProduitEnum type;
	private int quantite;
	private Date dateDePeremption;
	private Date dateAjout;

	public Produit(String nom, ProduitEnum type, int quantite,
			Date dateDePeremption, Date dateAjout) {
		super();
		this.nom = nom;
		this.type = type;
		this.quantite = quantite;
		this.dateDePeremption = dateDePeremption;
		this.dateAjout = dateAjout;
	}

	public Produit () {
		
	}
	
	public Produit (String nom) {
		this.nom = nom;
	}
	
	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public ProduitEnum getType() {
		return type;
	}

	public void setType(ProduitEnum type) {
		this.type = type;
	}

	public Date getDateDePeremption() {
		return dateDePeremption;
	}

	public void setDateDePeremption(Date dateDePeremption) {
		this.dateDePeremption = dateDePeremption;
	}

	public Date getDateAjout() {
		return dateAjout;
	}

	public void setDateAjout(Date dateAjout) {
		this.dateAjout = dateAjout;
	}
	
	@Override
	public String toString() {
		if (dateDePeremption == null){
			return "Nom :" + nom + ", Type: " + type + ", Quantite: "
					+ quantite + ", Ajoute le : " + dateAjout + ", pas de date d'expiration\n";
		}
		return "Nom :" + nom + ", Type: " + type + ", Quantite: "
				+ quantite + ", Ajoute le : " + dateAjout + ", espire le : " + dateDePeremption+"\n";
	}
	
	/*Renvoie une copie d'un produit*/
	public static Produit copyOf(Produit p){
		Produit result = new Produit();
		result.setDateAjout(p.getDateAjout());
		result.setDateDePeremption(p.getDateDePeremption());
		result.setNom(p.getNom());
		result.setQuantite(p.getQuantite());
		result.setType(p.getType());
		return result;
	}
	
}
