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
	private static Point p_cen=new Point(width/2,height/2);
	private static int min_size=0;
	
	public Painter(Word[] result) 
	{
		words = result;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = img.createGraphics();
		g.fillRect(0, 0, 1600, 900); // Fill the picture with white
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
		
		for (int i = 0; i < 150 && i < words.length; i++)
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
			if (temp < 30) temp = 30; // Minimum size 
			else if (temp > 255) temp = 255; // Maximum size
			words[i].set_count(temp);
		}
		
	}
	
	//set bacground image;
	private void set_bacground(){
		BufferedImage bimg = null;
		try {
			bimg = ImageIO.read(new File("res/background.jpg"));
		} catch (IOException e) {
			System.out.println("use white bacground");
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
		
		// Set the color of the string
		g.setColor(new Color(word.get_count() / 4 ,255 - word.get_count(),255 - word.get_count()));
		
		// Get the bounds of the string
		Rectangle2D  bounds = g.getFont().getStringBounds (word.get_str(), context);
		
		// Try to find an empty space of the string
		Point position = search_space(bounds);
		if(position.x>width)return false;
		// Draw the string
		g.drawString(word.get_str(), (int) (position.x + 5 - bounds.getMinX()), (int) (position.y + 5 - bounds.getMinY()));
		return true;
//		// The bounds of the string
//		g.drawRect(position.x, position.y, 
//				(int) (10 + bounds.getMaxX() - bounds.getMinX()),
//				(int) (10 + bounds.getMaxY() - bounds.getMinY()));		
	}
	
	private Point search_space(Rectangle2D bounds)
	{		
		// The bounds of the string
		int str_X = (int) (bounds.getMaxX() - bounds.getMinX()) + 10;
		int str_Y = (int) (bounds.getMaxY() - bounds.getMinY()) + 10;

		int loop=str_Y;
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
			boolean found = true;
			System.out.println(str_X+" "+str_Y+" "+min_size+" "+step);
			
			if(min_size!=0){
				if(str_X>=min_size&&str_Y>=min_size)
				{
					found=false;
					break;
				}
			}
			for (int i = 0; i < str_Y; i++)
			{
				for (int j = 0; j < str_X; j++)
				{
					if (x + j >= img.getWidth()) {
						found = false;
						break;
					}
					if (y + i >= img.getHeight()) {
						found = false;
						break;
					}
					if (img.getRGB(x + j, y + i) != Color.white.getRGB()) 
					{
						found = false;
						i = str_Y;
						break;
					}
				}
			}
			if (found) return new Point(x, y);
			left_bound=p_cen.x-loop;
			right_bound=p_cen.x+loop;
			low_bound=p_cen.y-loop;
			up_bound=p_cen.y+loop;
			if(low_bound<=1)low_bound=step;
			if(up_bound>=height-str_Y)up_bound=height-str_Y;
			//System.out.println("x "+x+" y "+y+" l "+left_bound+" r "+right_bound+" l "+low_bound+" u "+up_bound);
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
			System.out.println(x+" "+y+" "+init_X+" "+init_Y+" "+loop);
			//System.out.println(x+" "+y);
			//System.out.println();
			if(x<=init_X&&y<=init_Y)
			{
				init_X=left_bound-step;
				init_Y=low_bound-step;
				if(init_Y<=1)init_Y=step;
				loop=loop+step;
				System.out.println("loop "+loop);
			}
			
			if(x>width-str_X)break;
		}	while (loop<500);	
		
		System.out.println("Error! No space available!");
		if(str_X<str_Y){
			if(min_size==0)min_size=str_X;
			if(min_size>str_X)min_size=str_X;
		}else{
			if(min_size==0)min_size=str_Y;
			if(min_size>str_Y)min_size=str_Y;
		}
		
		
		// Some where outside the picture
		return new Point(width + 100, height + 100);
	}	

}
