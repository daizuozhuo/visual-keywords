import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Hashtable;


public class Catcher {
	private Hashtable<String, String> library; // Hashtable for words in the library
	private Hashtable<String, Word> result; // Hashtable for words found in the input file
	
	public Catcher()
	{
		library = new Hashtable<String, String>();
		result = new Hashtable<String, Word>();
	}
	
	//read file and pub words in the hastable
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
	
	// Return the collection of all the words found
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
		 while (index < str.length()) // read the whole input file
		 {
//			System.out.println("Index = " + index);
			 for (int i = 7; i > 1; i--) // search for known words, from the longest to the shortest
			 {
				 if (index + i > str.length()) continue;
//				System.out.println("Index = " + index + "  i = " + i);
//				System.out.println(str.substring(index, index + i));
				 if (library.containsKey(str.substring(index, index + i))) // if a keyword is found
				 {
					 if (result.containsKey(str.substring(index, index + i)))
					 {
						 result.get(str.substring(index, index + i)).hit(); 
						 // if the keyword has been found before
					 }
					 else
					 {
						 result.put(str.substring(index, index + i), new Word(str.substring(index, index + i), 1));
						 // if it has never been found
					 }
//					System.out.println("Hit : " + str.substring(index, index + i));
					 index += i - 1;
					 break;
				 }
			 }
			 index++;
		 }
	}

	public void analyse(String string, String string2) throws IOException
	{
		analyse(string); // analysis the first file first;
		 File read = new File(string2); 
		 BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(read), "UTF-8"));
		 String temp =null;
		 String str = "";
		 while ((temp = br.readLine()) != null)
		 {
			 str += temp;
		 }	
		 int index = 0;
//		System.out.println("Start of analysis");
		 while (index < str.length()) // read the whole input file
		 {
//			System.out.println("Index = " + index);
			 for (int i = 7; i > 1; i--) // search for known words, from the longest to the shortest
			 {
				 if (index + i > str.length()) continue;
//				System.out.println("Index = " + index + "  i = " + i);
//				System.out.println(str.substring(index, index + i));
				 if (library.containsKey(str.substring(index, index + i))) // if a keyword is found
				 {
					 if (result.containsKey(str.substring(index, index + i)))
					 {
						 result.get(str.substring(index, index + i)).hit2(); 
						 // if the keyword has been found before
					 }
					 else
					 {
						 result.put(str.substring(index, index + i), new Word(str.substring(index, index + i), 2));
						 // if it has never been found
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
