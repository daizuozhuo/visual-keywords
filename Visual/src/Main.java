import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;


public class Main {
    public static void main(String[] args) {
        Catcher catcher = new Catcher();
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
        try
        {
			catcher.analyse("res/input.txt");
    		System.out.println("Analysis Successful!");
		} 
        catch (IOException e)
        {
    		System.out.println("Analysis Error!");
			e.printStackTrace();
		}
        
        Collection<Word> c = catcher.get_values();        
        //iterate through the collection
        Word[] result =  (Word[]) c.toArray(new Word[c.size()]);
        Arrays.sort(result);
       	for (int i = result.length - 1; i > -1; i--) result[i].print();
        Painter painter = new Painter(result);
        try
        {
			painter.paint();
    		System.out.println("Paint Successful!");
		} 
        catch (IOException e) 
        {
    		System.out.println("Paint Successful!");
			e.printStackTrace();
		}
    }
}
