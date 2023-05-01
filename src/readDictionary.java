
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class readDictionary {
    private static HashMap<String, String> dictionary = new HashMap<String, String>();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        loadDictionary();
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("Time to load dictionary: " + time + " ms");

        String wordToSearch = "apple";
        if (dictionary.containsKey(wordToSearch)) {
            System.out.println(wordToSearch + " is in the dictionary!");
        } else {
            System.out.println(wordToSearch + " is not in the dictionary.");
        }
    }

    public static void loadDictionary() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("dictionary.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] words = line.split(":");
                dictionary.put(words[0], words[1]);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}







