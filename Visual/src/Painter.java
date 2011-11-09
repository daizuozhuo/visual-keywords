import java.awt.image.BufferedImage;


public interface Painter {
	

	public BufferedImage getImg();
	public void setBackground(BufferedImage bimg);
	public void setColorStyle(String str);
	public Object paint();

}
