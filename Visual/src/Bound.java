import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Bound {
	public enum BoundType { RECT,CIRCLE,HEART,STAR,ARC };
	private BoundType type;
	private int width;
	private int height;
	private Shape shape;
	
	public Bound(int t, int w, int h)
	{
		width=w;
		height=h;
		if ( t==1 ) {
			type=BoundType.CIRCLE;
			create_circle();
		} else if ( t==2 ) {
			type=BoundType.HEART;
			create_heart();
		} else if ( t==3 ) {
			type=BoundType.STAR;
			create_star();
		} else if (t==4 ) {
			type=BoundType.ARC;
			create_arc();
		} else {
			type=BoundType.RECT;
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
		/*AffineTransform rat = new AffineTransform();
	      rat.setToTranslation(100, 0);
	      rat.rotate(Math.PI / 6);
	      g2.transform(rat);
		*/
	}
	
	public void create_star()
	{
		
	}
	
}
