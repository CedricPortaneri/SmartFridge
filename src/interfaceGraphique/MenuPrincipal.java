package interfaceGraphique;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class MenuPrincipal extends Component 
{
	private BufferedImage img;
	
	

	public MenuPrincipal()
	{
		try 
		{
			File file = new File("resources/MenuPrincipale1.png");
			img = ImageIO.read(file);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			img = null;
		}
	}


	public void paint(Graphics graphics) 
	{
		if (img != null)
			graphics.drawImage(img,0,0, null);
	}
}
