import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Bound {
	private String type;
	private int width;
	private int height;
	private Shape shape;
	
	public Bound(String str, int w, int h)
	{
		width=w;
		height=h;
		type=str;
		if ( type == "circle" ) {
			create_circle();
		} else if ( type == "heart" ) {
			create_heart();
		} else if ( type == "star" ) {
			create_star();
		} else if (type == "arc" ) {
			create_arc();
		} else {
			create_rect();
		}
	}
	public Shape get_shape()
	{
		return shape;
	}
	public void create_rect()
	{
		shape = new Rectangle2D.Float(0, 0, width, height);
	}
	
	public void create_arc()
	{
		
	}
	public void create_circle()
	{
		shape = new Ellipse2D.Float(0, 0, width, height); 
	}
	
	public void create_heart()
	{
		
		double d=height/1.58;
		double r1=width/2;
		double r2=height/2;
		//Rectangle2D rectangle=new Rectangle().getBounds2D();
		Area area1 = new Area ( new Rectangle2D.Double(r1,r2,d,d) ); 
		Area area2 = new Area ( new Ellipse2D.Double(r1-d/2, r2, d, d)); 
		Area area3 = new Area ( new Ellipse2D.Double(r1, r2-d/2, d, d));
		area1.add(area2);
		area1.add(area3);
		
		AffineTransform tx = AffineTransform.getTranslateInstance(r1, r2);
		
		tx.setToTranslation(-(d*0.44),-(d*0.44) );
		
		shape= (Shape) tx.createTransformedShape(area1);
		AffineTransform ax = new AffineTransform();
		ax.rotate(Math.PI/4,r1,r2);
		
		shape=ax.createTransformedShape(shape);
	}
	
	public void create_star()
	{
		
	}
	
}
