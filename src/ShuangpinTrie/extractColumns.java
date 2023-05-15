package ShuangpinTrie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class extractColumns {
    public static void main(String[] args) {
        String inputFileName = "frequency&pinyin.txt";
        Map<String, ColumnPair> extractedColumns = extractColumn(inputFileName);
        printExtractedColumns(extractedColumns);
        System.out.println(extractedColumns.size());
    }

    private static Map<String, ColumnPair> extractColumn(String inputFileName) {
        Map<String, ColumnPair> resultMap = new HashMap<>(10000, 0.75f);
        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split("\t");

                if (columns.length < 5) {
                    //System.err.println("Skipping line due to insufficient columns: " + line);
                    continue;
                }

                String col1 = columns[0];
                String col2 = columns[1];
                String col5 = columns[4];

                // Remove '/' and everything after it in column 5
                int slashIndex = col5.indexOf('/');
                if (slashIndex != -1) {
                    col5 = col5.substring(0, slashIndex);
                }
                col5=removeNumbers(col5);
//                boolean containsDigit = col5.matches(".*\\d.*");
//                if(!containsDigit){
//                    col5=col5+"0";
//                }

                resultMap.put(col1, new ColumnPair(col5, col2));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }


    private static void printExtractedColumns(Map<String, ColumnPair> extractedColumns) {
        for (Map.Entry<String, ColumnPair> entry : extractedColumns.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }

    public static String removeNumbers(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!Character.isDigit(c)) {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static Map<String, ColumnPair> getExtractCol(){
        String inputFileName = "frequency&pinyin.txt";
        return extractColumn(inputFileName);
    }
}


