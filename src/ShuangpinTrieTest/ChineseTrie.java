

import java.util.*;

public class ChineseTrie {


    class TrieNode {
        private Map<Character, TrieNode> children;
        private Map<String, List<String>> pinyinToCharacters;
        private boolean isWordEnd;

        public TrieNode() {
            children = new HashMap<>();
            pinyinToCharacters = new HashMap<>();
            isWordEnd = false;
        }

        public Map<Character, TrieNode> getChildren() {
            return children;
        }

        public Map<String, List<String>> getPinyinToCharacters() {
            return pinyinToCharacters;
        }

        public boolean isWordEnd() {
            return isWordEnd;
        }

        public void setWordEnd(boolean isWordEnd) {
            this.isWordEnd = isWordEnd;
        }
    }

    private final TrieNode root;
    static Map<String, ColumnPair> extractCol= extractColumns.getExtractCol();
    static HashMap<String,String> allShuangpin= ShuangPinTrie.getAllShuangpin();
    public ChineseTrie() {
        root = new TrieNode();
    }


    public void insert(String pinyin, String character) {
        TrieNode node = root;
        for (char c : pinyin.toCharArray()) {
            node.getChildren().putIfAbsent(c, new TrieNode());
            node = node.getChildren().get(c);
        }
        node.setWordEnd(true);
        node.getPinyinToCharacters().putIfAbsent(pinyin, new ArrayList<>());
        node.getPinyinToCharacters().get(pinyin).add(character);
    }

    public boolean search(String pinyin){
        TrieNode curNode=root;
        for(int i=0;i<pinyin.length();i++){
            char ch=pinyin.charAt(i);
            TrieNode node=curNode.children.get(ch);
            if(node==null){
                return false;
            }
            curNode=node;
        }
        return curNode.isWordEnd();
    }

    public void insertAll(){
        for(Map.Entry<String,ColumnPair> entry:extractCol.entrySet()){
            ColumnPair pair=entry.getValue();
            String py= pair.getCol1();
            String character=pair.getCol2();

            this.insert(py,character);
        }
    }

    public static void main(String[] args) {
        ChineseTrie trie = new ChineseTrie();

        List<String> pinyinList = new ArrayList<>(ShuangPinTriePerformanceTest.allPinyin);
        List<String> characterList = new ArrayList<>(ShuangPinTriePerformanceTest.allChar);

//        List<String> pinyinList=generateTestData(100000);
//        List<String> characterList=generateTestData(10000);

        int count=0;
        ArrayList<Long> insertTimeList=new ArrayList<>();
        ArrayList<Long> searchTimeList=new ArrayList<>();
        ArrayList<Long> usedMemoryList=new ArrayList<>();
        while(count<2000) {
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
                int index = random.nextInt(ShuangPinTriePerformanceTest.allPinyin.size());
                sb.append(ShuangPinTriePerformanceTest.allPinyin.toArray()[index]);
            }
            sb.append(random.nextInt(5) + 1);
            testData.add(sb.toString());
        }
        return testData;
    }
}
