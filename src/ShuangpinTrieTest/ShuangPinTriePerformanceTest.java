import java.util.*;

public class ShuangPinTriePerformanceTest {
    static HashSet<String> allPinyin= ShuangPinTrie.getAllPinyin();
    static HashSet<String> allChar= ShuangPinTrie.getAllChar();


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
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < pinyinList.size() - 1; i++) {
                trie.insert(pinyinList.get(i), characterList.get(i));
            }
            long endTime = System.currentTimeMillis();
            insertTimeList.add(endTime - startTime);
//            System.out.println("Time taken to insert 10000 entries: " + (endTime - startTime) + " ms");


            startTime = System.currentTimeMillis();
            for (int i = 0; i < 410; i++) {
                trie.search(pinyinList.get(i));
            }
            endTime = System.currentTimeMillis();
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
        System.out.println("the avg of insert time is "+insertSum/count+" ms");

        Double searchSum=0d;
        for(int i=0;i<searchTimeList.size();i++){
            insertSum+=searchTimeList.get(i);
        }
        System.out.println("the avg of search time is "+searchSum/count+" ms");

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
