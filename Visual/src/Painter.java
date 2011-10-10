import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;


public class Painter {

	private Word[] words; // keywords to paint
	private final static String fontfile = "res/font.ttf"; // Font
	private final static int height = 900; // height of the picture
	private final static int width = 1600; // width of the picture
	Window window; // monitor window
	private BufferedImage img = new BufferedImage(1600, 900, BufferedImage.TYPE_INT_ARGB);
	Graphics g = img.createGraphics();
	private final static FontRenderContext context = new FontRenderContext (null, false, false);
	private static final int max_num = 1500;
	private static final int font_min = 10;
	private static final int font_max = 255;
	private static Point p_cen=new Point(width/2,height/2);
	private static int min_size=0;
	
	public int max(int a,int b)
	{
		if(a>=b)return a;
		else return b;
	}
	public Painter(Word[] result) 
	{
		words = result;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = img.createGraphics();
		g.fillRect(0, 0, width, height); // Fill the picture with white
		window = new Window("Window");
		window.set_img(img);
	}

	public void paint() throws IOException
	{
		long startTime = new Date().getTime();
		if (words.length  == 0)
		{
			System.out.println("No Keywords Found!");
			return;
		}

		reset_count(); // Reset make is the size of the font
		
		for (int i = 0; i < max_num && i < words.length; i++)
		{
			paint_str(words[i]); // paint the keywords one by one
			System.out.println((i + 1 ) + " / " + words.length + " done.");
			window.update();
		}		
		set_bacground();
		window.update();
		// Save the picture
		File outputfile = new File("res/output.gif");  
        ImageIO.write(img, "gif", outputfile);	
        
        
		System.out.println("Paint Successful!");   
		long endTime = new Date().getTime();
		System.out.println("Time used: " + (endTime - startTime) / 1000 + " s" );
	}
	//according to the frequency of word determine the size of font.
	private void reset_count() 
	{
		int sum = 0; // The sum of all the keywords found
		for (int i = 0; i < words.length; i++) sum += words[i].get_count();
		for (int i = 0; i < words.length; i++) 
		{
			int temp = words[i].get_count() * 150 / sum + 180 - 5 * i; // Function to determine the font size
			if (temp < font_min) temp = font_min; // Minimum size 
			else if (temp > font_max) temp = font_max; // Maximum size
			words[i].set_count(temp);
		}
		
	}
	
	//set background image;
	private void set_bacground()
	{
		BufferedImage bimg = null;
		try {
			bimg = ImageIO.read(new File("res/background.jpg"));
		} catch (IOException e) {
			System.out.println("use white bacground");
			return;
		}
		for(int i=0;i<width;i++) {
			for(int j=0;j<height;j++) {
				if(img.getRGB(i,j)==Color.white.getRGB()) {
					img.setRGB(i, j, bimg.getRGB(i,j));
				}
			}
		}
	}
	
	private boolean paint_str(Word word)
	{
		// Set the font
		Font font = new Font(fontfile, Font.BOLD, word.get_count());
		g.setFont(font);
		
		// Get the bounds of the string
		Rectangle2D  bounds = g.getFont().getStringBounds (word.get_str(), context);
		//g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// Try to find an empty space of the string
		Point position = search_space(bounds);
		if(position.x>width)return false;
		
		// Set the color of the string
		g.setColor(new Color(
				 max( position.x/5>220 ? 220 : position.x/5 , 100 ),
				 max( position.y/5>220 ? 220 : position.y/5 , 100 ),
				 max( (position.x+position.y)/10>220 ? 220 : (position.x+position.y)/10 , 100 )
				 ));
		
		// Draw the string
		g.drawString(word.get_str(), (int) (position.x + 5 - bounds.getMinX()), (int) (position.y + 5 - bounds.getMinY()));
		return true;	
	}
	
	
	private Point search_space(Rectangle2D bounds)
	{		
		// The bounds of the string
		int str_X = (int) (bounds.getMaxX() - bounds.getMinX()) + 10;
		int str_Y = (int) (bounds.getMaxY() - bounds.getMinY()) + 10;

		int loop=1;
		int step=(int)(0.1*str_Y);
		if(step<1)step=1;
		int y=p_cen.y-loop;	
		int x=p_cen.x-loop;
		// The starting position of x and y
		int init_Y = y;
		int init_X = x;
		int left_bound=0;
		int right_bound=0;
		int up_bound=0;
		int low_bound=0;
		
		do
		{	
			
			//System.out.println(str_X+" "+str_Y+" "+min_size+" "+step);
			
			if(min_size!=0)
			{
				if(str_X>=min_size&&str_Y>=min_size)
				{
					break;
				}
			}
			
			if (is_empty(x-str_X/2, y-str_Y/2, str_X, str_Y)) return new Point(x-str_X/2, y-str_Y/2);
			left_bound=p_cen.x-loop;
			right_bound=p_cen.x+loop;
			low_bound=p_cen.y-loop;
			up_bound=p_cen.y+loop;
			if(low_bound<=str_Y/2)low_bound=step+str_Y/2;
			if(up_bound>=height-str_Y/2)up_bound=height-str_Y/2;
			if(x<=left_bound)
			{
				if(y>low_bound)y=y-step;
				else x=x+step;
			}else if(x>=right_bound)
			{ 
				if(y<up_bound)y=y+step;
				else x=x-step;
			}else
			{
				if(y<=low_bound)
				{
					x=x+step;
				}else if(y>=up_bound)
				{
					x=x-step;
				}else{
					x=x+step;
				}
			}
//			System.out.println(x+" "+y+" "+init_X+" "+init_Y+" "+loop);
			//System.out.println(x+" "+y);
			//System.out.println();
			if(x<=init_X&&y<=init_Y)
			{
				init_X=left_bound-step;
				init_Y=low_bound-step;
				if(init_Y<=str_Y/2)init_Y=step+str_Y/2;
				loop=loop+step;
//				System.out.println("loop "+loop);
			}
			
		}	while (x<width-str_X/2);	
		
		System.out.println("Error! No space available!");
		if (str_X<str_Y) {
			if (min_size==0) min_size=str_X;
			if (min_size>str_X) min_size=str_X;
		} else {
			if(min_size==0) min_size=str_Y;
			if(min_size>str_Y) min_size=str_Y;
		}
		
		
		// Some where outside the picture
		return new Point(width + 100, height + 100);
	}	
	
	private boolean is_empty(int x, int y, int str_X, int str_Y)
	{
		if (x + str_X >= width || y + str_Y >= height) return false;
		
		int i = 0;
		for (int j = 0; j < str_X; j += 1)
		{
			if (img.getRGB(x + j, y + i) != Color.white.getRGB()) 
			{
				return false;
			}
		}
		
		i = str_Y;
		for (int j = 0; j < str_X; j += 1)
		{
			if (img.getRGB(x + j, y + i) != Color.white.getRGB()) 
			{
				return false;
			}
		}	
		
		int j = 0;
		for (i = 0; i < str_Y; i += 1)
		{
			if (img.getRGB(x + j, y + i) != Color.white.getRGB()) 
			{
				return false;
			}
		}
		
		j = str_X;
		for (i = 0; i < str_Y; i += 1)
		{
			if (img.getRGB(x + j, y + i) != Color.white.getRGB()) 
			{
				return false;
			}
		}	
		
		
		for (j = 0; j < str_X; j += 1)
		{
			if (img.getRGB(x + j, y + j * str_Y / str_X) != Color.white.getRGB()) 
			{
				return false;
			}
		}	

		for (j = 0; j < str_X; j += 1)
		{
			if (img.getRGB(x +str_X - j, y + j * str_Y / str_X) != Color.white.getRGB()) 
			{
				return false;
			}
		}
		
//		g.drawLine(x, y, x + str_X, y);
//		g.drawLine(x, y + str_Y, x + str_X, y + str_Y);
//		g.drawLine(x, y, x, y + str_Y);
//		g.drawLine(x + str_X, y, x + str_X, y + str_Y);
//		g.drawLine(x, y, x + str_X, y + str_Y);
//		g.drawLine(x + str_X, y, x, y + str_Y);
		
		return true;
	}
}
