import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.JCheckBoxMenuItem;
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
	private JMenu menuFile;
	private JMenu menuPreference;
	private JMenuItem menuItem_start;
	private JMenuItem menuItem_back;
	private JMenuItem menuItem_save;
	private JMenuItem menuItem_exit;
	private JCheckBoxMenuItem menuItem_update;
	private Wordle wordle;
	
	public Window(String title)
	{
		super(title);
		setLocation(30,50);
		setVisible(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage("res/icon.jpg"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		wordle = new Wordle();
		setContentPane(wordle);
		setResizable(false);
	}
	
	public void run() 
	{

        Catcher catcher = new Catcher();
        // Load library
        try
        {
			catcher.load_library();
			System.out.println("Library Load Successful!"); 
		} 
        catch (IOException e) 
        {
    		System.out.println("Library Load Error!");
			e.printStackTrace();
        }
        
        // Analyse input
        try
        {
			catcher.analyse("res/input1.txt");
    		System.out.println("Analysis Successful!");
		} 
        catch (IOException e)
        {
    		System.out.println("Analysis Error!");
			e.printStackTrace();
		}
        
        // Sort the words found
        Collection<Word> c = catcher.get_values();        
        result =  (Word[]) c.toArray(new Word[c.size()]);
        Arrays.sort(result);
        
       	for (int i = 0 ; i < result.length; i++) result[i].print();
       	System.out.println("------------------ " + result.length + " keywords found ------------------");
		setMenubar();
		pack();
	}
	
	//set menu;
	private void setMenubar()
	{
		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menuFile = new JMenu("File");		
		menuPreference = new JMenu("Preference");
		menuBar.add(menuFile);
		menuBar.add(menuPreference);

		//a group of JMenuItems
		
		menuItem_start = new JMenuItem("Start",KeyEvent.VK_S);
		menuItem_back = new JMenuItem("Set background image",KeyEvent.VK_B);
		menuItem_save = new JMenuItem("Save Image",KeyEvent.VK_I);
		menuItem_exit =new JMenuItem("Exit",KeyEvent.VK_E);
		menuItem_update = new JCheckBoxMenuItem("Real Time Update", true);

		
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
		
		
		
		menuFile.add(menuItem_start);
		menuFile.add(menuItem_back);
		menuFile.add(menuItem_save);
		menuFile.add(menuItem_exit);
		menuPreference.add(menuItem_update);
		this.setJMenuBar(menuBar);
	}
	
	private void start()
	{
       	Painter painter = new Painter(result, wordle, menuItem_update.getState());

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
	    	   wordle.setBackground(bimg);
	       } 
	       catch (IOException e)
	       {
				System.out.println("use white bacground");
	       }
	    }
	}


}