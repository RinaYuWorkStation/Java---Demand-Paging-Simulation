
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class randomNumberGenerator {
    private static ArrayList<Integer> random = new ArrayList<Integer>();
    private static int count = 0;
    static{
        File file = new File("random-numbers.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while((line = reader.readLine())!=null){
                random.add(Integer.parseInt(line.trim()));
            }
            reader.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    
    public static int getRandomNumber(Process p){
        int num = random.get(count++);
        return num;
    }
}
