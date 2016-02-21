package produit;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ProduitView extends Component{
	
	private Produit produit;
	private int x;
	private int y;
	private ImageIcon imageType;

	public ProduitView(Produit produit,int x,int y) {
		super();
		this.produit = produit;
		this.imageType=new ImageIcon("resources/"+produit.getType()+".png");
		this.x=x;
		this.y=y;
	}

	public ProduitView(){
		
	}
	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void paint(Graphics graphics){
		
		File file = new File("resources/"+this.produit.getType().toString()+".png");
		try {
			BufferedImage img = ImageIO.read(file);
			graphics.drawImage(img, this.x,
					this.y, null);
		
		}
		catch( IOException e) {
			e.printStackTrace();
		}
	}
	
}
