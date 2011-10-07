
public class Word implements Comparable<Word> {
	private int count;
	private String str;
	
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
	public void hit() {count++;}

	public void print() 
	{
		System.out.println(str + " : " + count + " times");		
	}
	
	public int compareTo(Word w)
	{
		return count - w.count;
    }
}
