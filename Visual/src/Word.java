import java.awt.Polygon;



public class Word implements Comparable<Word> {
    private int count; // Times this key words was found
    private String str; // The key word
    private int size; // Font size
    private int x; // x coordinate
    private int y; // y coordinate
    private Polygon bounds;
   
    public Word(String str)
    {
        this.count = 1;
        this.str = str;
        this.size = 15;
        this.x = -1;
        this.y = -1;
    }

    public String getStr() {return str;}
    public int getCount() {return count;}
    public int getSize() {return size;}
    public void hit() {count++;} // Found again
    public void setSize(int i) {size = i;}
    public void setPoint(int x, int y) {this.x = x;    this.y = y;}
    public void setBounds(Polygon bounds) {this.bounds = bounds;}
    public int X() {return x;}
    public int Y() {return y;}
    public Polygon getBounds() {return bounds;}
   
    public void print()
    {
        System.out.println(str + " : " + count + " times");
    }

    public int compareTo(Word w) // Override function for Comparable
    {
        return w.count - count;
    }
}