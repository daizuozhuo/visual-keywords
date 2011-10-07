import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Painter {

	private Word[] words;
	
	public Painter(Word[] result) 
	{
		words = result;
	}

	public void paint() throws IOException
	{
		BufferedImage img = new BufferedImage(960, 540, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.createGraphics();
		g.fillRect(0, 0, 960, 540);
		g.setColor(Color.blue);
		g.drawString(words[0].get_str(), 480, 540);
		File outputfile = new File("res/output.gif");  
        ImageIO.write(img, "gif", outputfile);		
	}

}
