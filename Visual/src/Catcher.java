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
	
	public void load_library() 
	{
		library.put("你好" , "你好");
		library.put("测试" , "测试");
		
	}
	
	public Collection<Word> get_values()
	{
		return result.values();
	}
	
	public void analyse(String str)
	{
		int index = 0;
//		System.out.println("Start of analysis");
		while (index < str.length())
		{
//			System.out.println("Index = " + index);
			for (int i = 4; i > 1; i--)
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
