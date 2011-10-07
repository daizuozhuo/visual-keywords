import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Hashtable;


public class Catcher {
	private Hashtable<String, String> library;
	private Hashtable<String, Word> result;
	
	public Catcher()
	{
		library = new Hashtable<String, String>();
		result = new Hashtable<String, Word>();
	}
	
	public void load_library() throws IOException 
	{
		
		 File read = new File("res/library.txt"); 
		 BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(read), "UTF-8"));
		 String word =null; 
		 while ((word = br.readLine()) != null)
		 {
			 library.put(word, word);
		 }	
	}
	
	public Collection<Word> get_values()
	{
		return result.values();
	}
	
	public void analyse(String file) throws IOException
	{
		 File read = new File(file); 
		 BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(read), "UTF-8"));
		 String temp =null;
		 String str = "";
		 while ((temp = br.readLine()) != null)
		 {
			 str += temp;
		 }	
		int index = 0;
//		System.out.println("Start of analysis");
		while (index < str.length())
		{
//			System.out.println("Index = " + index);
			for (int i = 7; i > 1; i--)
			{
				if (index + i > str.length()) continue;
//				System.out.println("Index = " + index + "  i = " + i);
//				System.out.println(str.substring(index, index + i));
				if (library.containsKey(str.substring(index, index + i)))
				{
					if (result.containsKey(str.substring(index, index + i)))
					{
						result.get(str.substring(index, index + i)).hit();
					}
					else
					{
						result.put(str.substring(index, index + i), new Word(str.substring(index, index + i)));
					}
//					System.out.println("Hit : " + str.substring(index, index + i));
					index += i - 1;
					break;
				}
			}
			index++;
		}
	}
}
