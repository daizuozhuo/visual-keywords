import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Shape;
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
	Window window; // monitor window
	private BufferedImage img;
	Graphics g;
	private final static FontRenderContext context = new FontRenderContext (null, false, false);
	private static Point p_cen;private static final int max_num = 150;
	private static final int font_min = 45;
	private static final int font_max = 150;
	private static Point min_size=new Point(0,0);
	private Bound bound;
	Shape bound_shape;
	
	private int max(int a,int b)
	{
		if(a>=b)return a;
		else return b;
	}
	
	public Painter(Word[] result,Window w) 
	{
		window = w;
		img = new BufferedImage(window.width, window.height, BufferedImage.TYPE_INT_ARGB);
		p_cen=new Point(window.width/2,window.height/2);
		
		//set shape
		bound=new Bound(2,window.width,window.height);
		bound_shape=bound.get_shape();
		
		words = result;
		g = img.createGraphics();
		g.fillRect(0, 0, window.width, window.height); // Fill the picture with white
		window.set_img(img);
	}

	public String paint() throws IOException
	{
		long startTime = new Date().getTime();
		if (words.length == 0)
		{
			return "No Keywords Found!";
		}

		int total = 0;
		int drawn = 0;
		set_size(); // Reset make is the size of the font
		
		for (int i = 0; i < max_num && i < words.length; i++)
		{
			drawn += paint_str(words[i]); // paint the keywords one by one
			System.out.println((i + 1 ) + " / " + words.length + " done.");
			window.update();
			total++;
		}		
		// Save the picture
		File outputfile = new File("res/output.gif");  
        ImageIO.write(img, "gif", outputfile);	
        
        
		System.out.println("Paint Successful!");   
		long endTime = new Date().getTime();
		return drawn + " / " + total + "drawn. \nTime used: " + (endTime - startTime) / 1000 + "." + (endTime - startTime) % 1000 + " s.";
	}
	//according to the frequency of word determine the size of font.
	private void set_size() 
	{
		int sum = 0; // The sum of all the keywords found
		for (int i = 0; i < words.length; i++) sum += words[i].get_count();
		for (int i = 0; i < words.length; i++) 
		{
			int temp = words[i].get_count() * 130 / sum + 150 - 5 * i; // Function to determine the font size
			if (temp < font_min) temp = font_min; // Minimum size 
			else if (temp > font_max) temp = font_max; // Maximum size
			words[i].set_size(temp);
		}
		
	}
	

	private int paint_str(Word word)
	{
		//Graphics2D g2=(Graphics2D) g;
		//g2.fill(bound_shape);
		// Set the font
		Font font = new Font(fontfile, Font.BOLD, word.get_size());
		g.setFont(font);
		
		// Get the bounds of the string
		Rectangle2D  bounds = g.getFont().getStringBounds (word.get_str(), context);
		// Try to find an empty space of the string
		Point position = search_space(bounds);
		if(position.x > window.width) return 0;
		
		// Set the color of the string which is related to its position
		g.setColor(new Color(
				 max( position.x/5>220 ? 220 : position.x/5 , 100 ),
				 max( position.y/5>220 ? 220 : position.y/5 , 100 ),
				 max( (position.x+position.y)/10>220 ? 220 : (position.x+position.y)/10 , 100 )
				 ));
		
		// Draw the string
		g.drawString(word.get_str(), (int) (position.x + 5 - bounds.getMinX()), (int) (position.y + 5 - bounds.getMinY()));
		return 1;	
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
			
			if(min_size.x!=0){
				if(str_X>=min_size.x&&str_Y>=min_size.y)
				{
					break;
				}
				if(min_size.y==font_min)break;
			}
			if (is_empty(x-str_X/2, y-str_Y/2, str_X, str_Y)) return new Point(x-str_X/2, y-str_Y/2);
			left_bound=p_cen.x-loop;
			right_bound=p_cen.x+loop;
			low_bound=p_cen.y-loop;
			up_bound=p_cen.y+loop;
			if(low_bound<=str_Y/2)low_bound=step+str_Y/2;
			if(up_bound >= window.height - str_Y / 2) up_bound = window.height - str_Y / 2;
			if(x<=left_bound)
			{
				if(y>low_bound)y=y-step;
				else x=x+step;
			}
			else if(x>=right_bound)
			{ 
				if(y<up_bound)y=y+step;
				else x=x-step;
			}
			else
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
			
			if(x<=init_X&&y<=init_Y)
			{
				init_X=left_bound-step;
				init_Y=low_bound-step;
				if(init_Y<=str_Y/2)init_Y=step+str_Y/2;
				loop=loop+step;
//				System.out.println("loop "+loop);
			}
			
		}	while (x < window.width - str_X / 2);	
		
		System.out.println("Error! No space available!");
		
			if (min_size.x==0) min_size.x=str_X;
			if (min_size.x>str_X) min_size.x=str_X;
			if(min_size.y==0) min_size.y=str_Y;
			if(min_size.y>str_Y) min_size.y=str_Y;
		
		
		// Some where outside the picture
		return new Point(window.width + 100, window.height + 100);
	}	
	
	private boolean is_empty(int x, int y, int str_X, int str_Y)
	{
		if (x + str_X >= window.width || y + str_Y >= window.height) return false;
		
		int i = 0;
		int j = 0;
		
		for (j = 0; j < str_X; j += 1)
			if (!is_inshape(x + j, y + j * str_Y / str_X)) return false;	

		for (j = 0; j < str_X; j += 1)
			if (!is_inshape(x + str_X - j, y + j * str_Y / str_X)) return false;		
		
		i = str_Y / 2;
		for (j = 0; j < str_X; j += 1)
			if (!is_inshape(x + j, y + i)) return false;	
		
		j = str_X /2;
		for (i = 0; i < str_Y; i += 1)
			if (!is_inshape(x + j, y + i)) return false;		
		
		i = 0;
		for (j = 0; j < str_X; j += 1)
			if (!is_inshape(x + j, y + i)) return false;
		
		i = str_Y;
		for (j = 0; j < str_X; j += 1)
			if (!is_inshape(x + j, y + i)) return false;	
		
		j = 0;
		for (i = 0; i < str_Y; i += 1)
			if (!is_inshape(x + j, y + i)) return false;
		
		j = str_X;
		for (i = 0; i < str_Y; i += 1)
			if (!is_inshape(x + j, y + i)) return false;		

		return true;
	}

	private boolean is_inshape(int a, int b)
	{
		if (img.getRGB(a, b) == Color.white.getRGB() && bound_shape.contains(a, b)) 
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}
}