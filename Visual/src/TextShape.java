import java.awt.Font;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import javax.swing.JPanel;

public class TextShape extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Shape s;

    public TextShape(Font font,String str) {
        Font f = font;
        GlyphVector v = f.createGlyphVector(getFontMetrics(f).getFontRenderContext(), str);
        s = v.getOutline();
        
    }
    
    public Shape getShape() {
    	return s;
    }
};
