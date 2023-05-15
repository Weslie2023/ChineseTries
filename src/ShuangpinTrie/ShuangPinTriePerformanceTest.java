package ShuangpinTrie;

import ShuangpinTrie.ColumnPair;
import ShuangpinTrie.ShuangPinTrie;

import java.util.*;

import static ShuangpinTrie.ShuangPinTrie.extractCol;

public class ShuangPinTriePerformanceTest {

    static HashSet<String> allPinyin= getAllPinyin();
    static HashSet<String> allChar= getAllChar();

    public static HashSet<String> getAllPinyin(){
        HashSet<String> allPinyin= new HashSet<>();
        for(Map.Entry<String, ColumnPair> entry:extractCol.entrySet()){
            String pinYin= entry.getValue().getCol1();
            allPinyin.add(pinYin);
        }
        return allPinyin;
    }

    public static HashSet<String> getAllChar(){
        HashSet<String> allChar=new HashSet<>();
        for(Map.Entry<String,ColumnPair> entry:extractCol.entrySet()){
            String character=entry.getValue().getCol2();
            allChar.add(character);
        }
        return allChar;
    }
    public static void main(String[] args) {
        ShuangPinTrie trie = new ShuangPinTrie();
//        ShuangPinTrieOrigin trie=new ShuangPinTrieOrigin();

        List<String> pinyinList = new ArrayList<>(allPinyin);
        List<String> characterList = new ArrayList<>(allChar);

//        List<String> pinyinList=generateTestData(10000);
//        List<String> characterList=generateTestData(1000);


        int count=0;
        ArrayList<Long> insertTimeList=new ArrayList<>();
        ArrayList<Long> searchTimeList=new ArrayList<>();
        ArrayList<Long> usedMemoryList=new ArrayList<>();
        while(count<2000 ) {
            long startTime = System.nanoTime();
            for (int i = 0; i < pinyinList.size() - 1; i++) {
                trie.insert(pinyinList.get(i), characterList.get(i));
            }
            long endTime = System.nanoTime();
            insertTimeList.add(endTime - startTime);
//            System.out.println("Time taken to insert 10000 entries: " + (endTime - startTime) + " ms");


            startTime = System.nanoTime();
            for (int i = 0; i < 410; i++) {
                trie.search(pinyinList.get(i));
            }
            endTime = System.nanoTime();
            searchTimeList.add(endTime - startTime);
//            System.out.println("Time taken to search for 1000 entries: " + (endTime - startTime) + " ms");

            Runtime runtime = Runtime.getRuntime();
            long totalMemory = runtime.totalMemory();
            long freeMemory = runtime.freeMemory();
            long usedMemory = totalMemory - freeMemory;
//            System.out.println("Free memory: " + freeMemory + " bytes");
            usedMemoryList.add(usedMemory/1024);
//            System.out.println("Used memory:" + usedMemory + " bytes");
            count++;
        }

        Double insertSum=0d;
        for(int i=0;i<insertTimeList.size();i++){
            insertSum+=insertTimeList.get(i);
        }
        System.out.println("the avg of insert time is "+insertSum/count+" ns");

        Double searchSum=0d;
        for(int i=0;i<searchTimeList.size();i++){
            searchSum+=searchTimeList.get(i);
        }
        System.out.println("the avg of search time is "+searchSum/count+" ns");

        Double memorySum=0d;
        for(int i=0;i<usedMemoryList.size();i++){
            memorySum+=usedMemoryList.get(i);
        }
        System.out.println("the avg memory is "+ memorySum/count+" KB");
        System.out.println("insert time: "+insertTimeList);
        System.out.println("search time: "+searchTimeList);
        System.out.println("memory: "+usedMemoryList);
    }

    private static List<String> generateTestData(int n) {
        List<String> testData = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 3; j++) {
                int index = random.nextInt(allPinyin.size());
                sb.append(allPinyin.toArray()[index]);
            }
            sb.append(random.nextInt(5) + 1);
            testData.add(sb.toString());
        }
        return testData;
    }
}
