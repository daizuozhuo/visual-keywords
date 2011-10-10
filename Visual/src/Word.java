
public class Word implements Comparable<Word> {
	private int count; // Times this key words was found
	private String str; // The key word
	
	public Word(String str)
	{
		this.count = 1;
		this.str  = str;
	}
	
	public Word(String str, int count)
	{
		this.count = count;
		this.str  = str;
	}
	
	public String get_str() {return str;}
	public int get_count() {return count;}
	public void hit() {count++;} // Found again
	public void set_count(int i) {count = i;}

	public void print() 
	{
		System.out.println(str + " : " + count + " times");		
	}
	
	public int compareTo(Word w) // Override function for Comparable
	{
		return w.count - count;
    }
}
