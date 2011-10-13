import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Wordle extends JPanel{

	public final int height = 900; // height of the picture
	public final int width = 1600; // width of the picture
	private BufferedImage img;
	private BufferedImage fimg;
	
	
	public Wordle()
	{
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
		this.fimg = img;
		this.img = img;
	}
		
	public void set_background(BufferedImage bimg)
	{
		if (bimg != null)
		{
			for(int i = 0; i < width; i++) 
			{
				for(int j = 0; j < height; j++) 
				{
					if(fimg.getRGB(i,j) == Color.white.getRGB())
					{
						img.setRGB(i, j, bimg.getRGB(i * bimg.getWidth() / width, j * bimg.getHeight() / height));
					}
					else 
					{
						img.setRGB(i, j, fimg.getRGB(i, j));
					}
				}
			}
		}
		img = fimg;
	}

	public void saveImage() 
	{
		File outputfile = new File("res/output.gif");  
        try
        {
			ImageIO.write(img, "gif", outputfile);
			System.out.println("Save Successful!"); // Save the picture
		} 
        catch (IOException e) 
		{
			System.out.println("Save Error!");
			e.printStackTrace();
		}    	
	}

	public void update() 
	{
		Graphics g = this.getGraphics();
		g.drawImage(img, 0, 0, width, height, this);	
	}

}
