package recette;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import produit.Produit;

public class Recette {
	private String nom;
	private String description;
	private HashMap<Produit,Integer> ingredients;
	
	public Recette(String nom, String description,
			HashMap<Produit, Integer> ingredients) {
		super();
		this.nom = nom;
		this.description = description;
		this.ingredients = ingredients;
	}
	
	public Recette(){
		
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public HashMap<Produit, Integer> getIngredients() {
		return ingredients;
	}

	public void setIngredients(HashMap<Produit, Integer> ingredients) {
		this.ingredients = ingredients;
	}
	
	public String toString(){
		String result;
		if (this.description != null){
			result= "Nom :"+this.nom+", Description :"+this.description+",\n   Ingrediants : \n";
		}
		else {
			result ="Nom :"+this.nom+", \n   Ingrediants : \n";
		}
		for (Entry<Produit, Integer> entry : this.ingredients.entrySet()){
			result = result.concat("	- "+entry.getKey().getNom()+"  Quantite necessaire : "+entry.getValue()+"\n");
		}
		return result;
	}
	
	
}
