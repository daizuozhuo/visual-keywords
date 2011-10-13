
public class Word implements Comparable<Word> {
	private int count; // Times this key words was found
	private String str; // The key word
	private int size; // Font size
	
	public Word(String str)
	{
		this.count = 1;
		this.str  = str;
		this.size = 15;
	}
	
	public Word(String str, int count)
	{
		this.count = count;
		this.str  = str;
		this.size = 15;
	}
	
	public String getStr() {return str;}
	public int getCount() {return count;}
	public int getSize() {return size;}
	public void hit() {count++;} // Found again
	public void setSize(int i) {size = i;}

	public void print() 
	{
		System.out.println(str + " : " + count + " times");		
	}
	
	public int compareTo(Word w) // Override function for Comparable
	{
		return w.count - count;
    }
}
