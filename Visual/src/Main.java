import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;



public class Main {
    
	public static void main(String[] args) {
        Catcher catcher = new Catcher();
        
        // Load library
        try
        {
			catcher.load_library();
			System.out.println("Library Load Successful!"); 
		} 
        catch (IOException e) 
        {
    		System.out.println("Library Load Error!");
			e.printStackTrace();
        }
        
        // Analyse input
        try
        {
			catcher.analyse("res/input1.txt");
    		System.out.println("Analysis Successful!");
		} 
        catch (IOException e)
        {
    		System.out.println("Analysis Error!");
			e.printStackTrace();
		}
        
        // Sort the words found
        Collection<Word> c = catcher.get_values();        
        Word[] result =  (Word[]) c.toArray(new Word[c.size()]);
        Arrays.sort(result);
        
       	for (int i = 0 ; i < result.length; i++) result[i].print();
       	System.out.println("------------------ " + result.length + " keywords found ------------------");
        
       	// Paint the words
       	Painter painter = new Painter(result);
        try
        {
			painter.paint();
		} 
        catch (IOException e) 
        {
    		System.out.println("Paint Successful!");
			e.printStackTrace();
		}
        
    }
}
