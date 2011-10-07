import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Painter {

	private Word[] words;
	private final static String fontfile = "res/font.ttf";
	private final static int height = 900;
	private final static int width = 1600;
	private BufferedImage img = new BufferedImage(1600, 900, BufferedImage.TYPE_INT_ARGB);
	Graphics g = img.createGraphics();
	private final static FontRenderContext context = new FontRenderContext (null, false, false);
	
	public Painter(Word[] result) 
	{
		words = result;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = img.createGraphics();
		g.fillRect(0, 0, 1600, 900);
	}

	public void paint() throws IOException
	{
		if (words.length  == 0)
		{
			System.out.println("No Keywords Found!");
			return;
		}
		
		reset_count();
		for (int i = 0; i < 150; i++)
		{
			paint_str(words[i]);
			System.out.println(i + "/" + words.length + " done.");
		}
		File outputfile = new File("res/output.gif");  
        ImageIO.write(img, "gif", outputfile);	
        
        
		System.out.println("Paint Successful!");           
	}
	
	private void reset_count() 
	{
		int sum = 0;
		for (int i = 0; i < words.length; i++) sum += words[i].get_count();
		for (int i = 0; i < words.length; i++) 
		{
			int temp = words[i].get_count() * 150 / sum + 200 - 5 * i;
			if (temp < 30) temp = 30;
			else if (temp > 255) temp = 255;
			words[i].set_count(temp);
		}
		
	}

	private void paint_str(Word word)
	{
		Font font = new Font(fontfile, Font.BOLD, word.get_count());
		g.setFont(font);
		g.setColor(new Color(word.get_count() / 4 ,255 - word.get_count(),255 - word.get_count()));
		Rectangle2D  bounds = g.getFont().getStringBounds (words[0].get_str(), context);
		Point position = search_space(bounds);
		g.drawString(word.get_str(), (int) (position.x - bounds.getMinX()), (int) (position.y - bounds.getMinY()));
//		g.drawRect(position.x, position.y, 
//				(int) (bounds.getMaxX() - bounds.getMinX()),
//				(int) (bounds.getMaxY() - bounds.getMinY()));		
	}
	
	private Point search_space(Rectangle2D bounds)
	{
		int x = (int) (Math.random() * width);
		int y = (int) (Math.random() * height);
		int str_X = (int) (bounds.getMaxX() - bounds.getMinX());
		int str_Y = (int) (bounds.getMaxY() - bounds.getMinY());
		x %= (width - str_X);
		y %= (height - str_Y);
		int init_Y = y;
		int init_X = x;
		do
		{
			boolean found = true;
			for (int i = 0; i < str_Y; i++)
			{
				for (int j = 0; j < str_X; j++)
				{
					if (img.getRGB(x + j, y + i) != Color.white.getRGB()) 
					{
						found = false;
						i = str_Y;
						break;
					}
				}
			}
			if (found) return new Point(x, y);
			x++;
			if (x >= width - str_X)
			{
				x = 0;
				y++;
				if (y >= height - str_Y) y = 0;
			}
		}	while (y != init_Y || x != init_X);	
		return new Point(width + 100, height + 100);
	}	

}
