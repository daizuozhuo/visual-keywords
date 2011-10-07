import java.util.Collection;
import java.util.Iterator;


public class Main {
    public static void main(String[] args) {
        Catcher catcher = new Catcher();
        catcher.load_library();
        catcher.analyse("你好呀你好呀, 这是一个测试");
        
        Collection<Word> c = catcher.get_values();        
        //iterate through the collection
        Iterator<Word> itr = c.iterator();
        while(itr.hasNext())
        {
            itr.next().print();
        }
    }
}
