import java.awt.Toolkit;
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
import javax.swing.JOptionPane;


@SuppressWarnings("serial")
public class Window extends JFrame {

	private Word[] result;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem_start;
	private JMenuItem menuItem_back;
	private JMenuItem menuItem_save;
	private JMenuItem menuItem_exit;
	private Wordle wordle;
	
	public Window(String title, Word[] result)
	{
		super(title);
		setLocation(30,50);
		setVisible(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage("res/icon.jpg"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMenubar();
		wordle = new Wordle();
		setContentPane(wordle);
		setResizable(false);
		pack();
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
		
		menuItem_start = new JMenuItem("Start",KeyEvent.VK_S);
		menuItem_back = new JMenuItem("Set background image",KeyEvent.VK_B);
		menuItem_save = new JMenuItem("Save Image",KeyEvent.VK_I);
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
		
		menuItem_save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
            {
                wordle.saveImage();
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
		menu.add(menuItem_save);
		menu.add(menuItem_exit);
		
		this.setJMenuBar(menuBar);
	}
	
	public void start()
	{
       	Painter painter = new Painter(result, wordle);

   		JOptionPane.showMessageDialog(null, painter.paint(), "Message", 1);

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
	    	   //set_background(bimg);
	       } catch (IOException e)
	       {
				System.out.println("use white bacground");
	       }
	    }
	}

}