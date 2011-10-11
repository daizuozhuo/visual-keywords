import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


@SuppressWarnings("serial")
public class Window extends JFrame implements ComponentListener
{
	private Graphics g;
	private BufferedImage img;
	private BufferedImage fimg;
	private Word[] result;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem_start;
	private JMenuItem menuItem_back;
	private JMenuItem menuItem_exit;
	public final int height = 900; // height of the picture
	public final int width = 1600; // width of the picture
	
	public Window(String title, Word[] result)
	{
		super(title);
		setLocation(30,50);
		setVisible(true);
		setSize(width, height + 50);
		setIconImage(Toolkit.getDefaultToolkit().getImage("res/icon.jpg"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMenubar();
		addComponentListener(this);
		g = this.getContentPane().getGraphics();
		this.getContentPane().setSize(width, height);
		this.result = result;
	}
	
	//set menu;
	private void setMenubar()
	{
		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("File");
		menuBar.add(menu);

		//a group of JMenuItems
		
		menuItem_start = new JMenuItem("Start",KeyEvent.VK_T);
		menuItem_back = new JMenuItem("Set background image",KeyEvent.VK_B);
		menuItem_exit=new JMenuItem("Exit",KeyEvent.VK_E);
		menuItem_start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
            {
                start();
            }
        });      
		
		menuItem_back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
            {
                choose_backimg();
            }
        });    
		
		menuItem_exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });    
		
		menu.add(menuItem_start);
		menu.add(menuItem_back);
		menu.add(menuItem_exit);
		
		this.setJMenuBar(menuBar);
	}
	
	public void start()
	{
       	Painter painter = new Painter(result, this);
       	try 
       	{
       		JOptionPane.showMessageDialog(null, painter.paint(), "Message", 1);
       		update();
		} catch (IOException e) 
		{
			System.out.println("Paint Error!");
			e.printStackTrace();
		}
	}
	
	public void set_img(BufferedImage img)
	{
		this.fimg = img;
		this.img = img;
	}
	
	public void choose_backimg()
	{
		JFileChooser chooser = new JFileChooser();
	    int returnVal = chooser.showOpenDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       System.out.println("You chose to open this file: " +
	            chooser.getSelectedFile().getName());
	       File file=chooser.getSelectedFile();
	       try 
	       {
	    	   BufferedImage bimg = ImageIO.read(file);
	    	   set_background(bimg);
	       } catch (IOException e)
	       {
				System.out.println("use white bacground");
	       }
	    }
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
	}
	
	public void update()
	{
		g.drawImage(img, 0, 0, width, height, this);
		//update(g);
	}

	@Override
	public void componentHidden(ComponentEvent e) 
	{
		this.validate();		
	}

	@Override
	public void componentMoved(ComponentEvent e) 
	{
		this.validate();
	}

	@Override
	public void componentResized(ComponentEvent e)
	{
		this.validate();
	}

	@Override
	public void componentShown(ComponentEvent e)
	{
		this.validate();
	}
}