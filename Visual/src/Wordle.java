import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Wordle extends JPanel{

	private final int height; // height of the picture
	private final int width; // width of the picture
	private BufferedImage img;
	
	
	public Wordle(int width, int height)
	{
		this.width = width;
		this.height = height;
		setSize(width, height);
		setPreferredSize(getSize());
		setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		g.drawImage(img, 0, 0, width, height, this);
	}
	
	public void setImg(BufferedImage img)
	{
		this.img = img;
	}
		
	public boolean imageUpdate(Image img, int flags, int x, int y, int w, int h) 
	{
		if ((flags & ALLBITS) != 0)
		{
			Graphics g = this.getGraphics();
			g.drawImage(img, x, y, x + w, y + h, x, y, x + w, y + h, this);
		}
		return (flags & ALLBITS) == 0;
	}

	public int saveImage(String file) throws IOException 
	{

		File outputfile = new File(file);  
		if (img == null) return 1;
		ImageIO.write(img, "gif", outputfile);
		return 0;
	}

}
