import java.awt.Graphics;
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

	public void update(int x, int y, int str_X, int str_Y) 
	{
		Graphics g = this.getGraphics();
		g.drawImage(img, x, y, x + str_X, y + str_Y, x, y, x + str_X, y + str_Y, this);	
//		Graphics2D g2d = (Graphics2D)g;		
//		AffineTransform affineTransform = new AffineTransform();
//		//set the translation to the mid of the component
//		affineTransform.rotate(Math.toRadians(45), width/2, height/2);
//		//draw the image using the AffineTransform
//		g.drawImage(img, affineTransform, this); 
//		g.drawImage(img, affineTransform, this); 
	}

}
