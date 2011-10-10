import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class Window extends JFrame
{
	private Graphics g;
	private Image img;
	
	public Window(String title)
	{
		super(title);
		setSize(1200,700);
		setLocation(30,50);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		g = this.getGraphics();
	}
	
	public void set_img(Image img)
	{
		this.img = img;
	}
	
	public void update()
	{
		g.drawImage(img, 0, 25, 1200, 675, this);
		//update(g);
	}
}