
public class Word implements Comparable<Word> {
	private int count; // Times this key words was found
	private String str; // The key word
	private int size; // Font size
	private int x; // x coordinate 
	private int y; // y coordinate
	
	public Word(String str)
	{
		this.count = 1;
		this.str  = str;
		this.size = 15;
		this.x = 0;
		this.y = 0;
	}
	
	public String getStr() {return str;}
	public int getCount() {return count;}
	public int getSize() {return size;}
	public void hit() {count++;} // Found again
	public void setSize(int i) {size = i;}
	public void setPoint(int x, int y) {this.x = x; this.y = y;}
	public int x() {return x;}
	public int y() {return y;}
	
	public void print() 
	{
		System.out.println(str + " : " + count + " times");		
	}
	
	public int compareTo(Word w) // Override function for Comparable
	{
		return w.count - count;
    }
}
