import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


@SuppressWarnings("serial")
public class Window extends JFrame
{
	private Graphics g;
	private Image img;
	public BufferedImage bimg;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem_start;
	private JMenuItem menuItem_back;
	private JMenuItem menuItem_exit;
	private boolean is_start=true;
	
	public Window(String title)
	{
		super(title);
		setSize(1200,700);
		setLocation(30,50);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMenubar();
		g = this.getGraphics();
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
		
		menuItem_start = new JMenuItem("start",KeyEvent.VK_T);
		menuItem_back = new JMenuItem("set background image",KeyEvent.VK_T);
		menuItem_exit=new JMenuItem("exit",KeyEvent.VK_T);
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
		is_start=true;
		System.out.println("start");
	}
	
	public boolean is_start()
	{
		System.out.println("check");
		return is_start;
	}
	public void set_img(Image img)
	{
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
	       try {
				bimg = ImageIO.read(file);
			} catch (IOException e) {
				System.out.println("use white bacground");
			}
	    }
	}
	
	public void set_background()
	{
		if ( bimg != null ){
			BufferedImage fimg=(BufferedImage) img;
			for(int i=0;i<fimg.getWidth();i++) {
				for(int j=0;j<fimg.getHeight();j++) {
					if(fimg.getRGB(i,j)==Color.white.getRGB()) {
						fimg.setRGB(i, j, bimg.getRGB(i,j));
					}
				}
			}
		img=fimg;
		}
	}
	public void update()
	{
		g.drawImage(img, 0, 50, 1200, 675, this);
		//update(g);
	}
}