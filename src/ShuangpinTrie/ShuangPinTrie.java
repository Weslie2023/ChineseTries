package ShuangpinTrie;

import org.w3c.dom.ls.LSOutput;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShuangPinTrie {
    class TrieNode{
        private Map<Character, TrieNode> children;
        private Map<String, List<String>> pinyinToCharacters;
        boolean isWord;

        public TrieNode(){
            children = new HashMap<>();
            pinyinToCharacters=new HashMap<>();
            isWord = false;
        }

        public Map<Character, TrieNode> getChildren(){
            return children;
        }

        public Map<String, List<String>> getPinyinToCharacters(){
            return pinyinToCharacters;
        }

        public boolean isWord(){
            return  isWord;
        }

        public void setWordEnd(boolean isWord){
            this.isWord=isWord;
        }
    }

    private TrieNode root;
    public static Map<String, ColumnPair> extractCol= extractColumns.getExtractCol();


    Map<String,List<String>> cache=new HashMap<>();
    static HashSet<String> vowelSet = new HashSet<>(Arrays.asList("q","w","e","r","t","y","o","p","a","s","d","f","g","h","j","k","l","z","x","c","b","n","m","sh","ch","zh"));
    private static HashMap<String, String> consonantMap= new HashMap<>();
    static {
        consonantMap.put("sh","u");
        consonantMap.put("ch","i");
        consonantMap.put("zh","v");
        consonantMap.put("iu","q");
        consonantMap.put("ei","w");
        consonantMap.put("uan","r");
        consonantMap.put("ue","t");
        consonantMap.put("ve","t");
        consonantMap.put("un","y");
        consonantMap.put("u","u");
        consonantMap.put("i","i");
        consonantMap.put("uo","o");
        consonantMap.put("o","o");
        consonantMap.put("ie","p");
        consonantMap.put("a","a");
        consonantMap.put("e","e");
        consonantMap.put("ong","s");
        consonantMap.put("iong","s");
        consonantMap.put("ai","d");
        consonantMap.put("en","f");
        consonantMap.put("eng","g");
        consonantMap.put("ang","h");
        consonantMap.put("an","j");
        consonantMap.put("uai","k");
        consonantMap.put("ing","k");
        consonantMap.put("iang","l");
        consonantMap.put("uang","l");
        consonantMap.put("ou","z");
        consonantMap.put("ua","x");
        consonantMap.put("ia","x");
        consonantMap.put("ao","c");
        consonantMap.put("ui","v");
        consonantMap.put("v","v");
        consonantMap.put("in","b");
        consonantMap.put("iao","n");
        consonantMap.put("ian","m");
    }
    static HashMap<String,String> allShuangpin=getAllShuangpin();

    public ShuangPinTrie(){
        root = new TrieNode();
    }

    public static String getShuangpin(String pinYin) {
//        pinYin=pinYin.substring(0,pinYin.length()-1);
        String shuangPin = "";
        String vowel;
        String consonant;


        if (pinYin.length() == 1) {
            shuangPin = pinYin + pinYin;
        }

        if (pinYin.length() == 2) {
            if(consonantMap.containsKey(pinYin)){
                return pinYin;
            }
            vowel = pinYin.substring(0, 1);
            consonant = pinYin.substring(1);
            shuangPin = vowel + consonantMap.get(consonant);
        }

        if (pinYin.length() >= 3) {
            if(consonantMap.containsKey(pinYin)){
                return pinYin.charAt(0) + consonantMap.get(pinYin);
            }
            if (vowelSet.contains(pinYin.substring(0, 2))) {
                vowel = pinYin.substring(0, 2);
                consonant = pinYin.substring(2);

                shuangPin = consonantMap.get(vowel) + consonantMap.get(consonant);
            } else if (vowelSet.contains(pinYin.substring(0, 1))) {
                vowel = pinYin.substring(0, 1);
                consonant = pinYin.substring(1);

                shuangPin = vowel + consonantMap.get(consonant);
            }
        }
        return shuangPin;
    }

    public void insert(String pinyin, String character) {
//        String tone=pinyin.substring(pinyin.length()-1);
//        String shuangPin=allShuangpin.get(pinyin)+tone;
        String shuangPin=allShuangpin.get(pinyin);
        TrieNode node = root;
        for (char c : shuangPin.toCharArray()) {
            node.getChildren().putIfAbsent(c, new TrieNode());
            node = node.getChildren().get(c);
        }
        node.setWordEnd(true);
        node.getPinyinToCharacters().putIfAbsent(shuangPin, new ArrayList<>());
        node.getPinyinToCharacters().get(shuangPin).add(character);
    }

    public List<String> search(String pinyin) {
        if (cache.containsKey(pinyin)) {
            return cache.get(pinyin);
        }
//
//        String tone=pinyin.substring(pinyin.length()-1);
//        String shuangPin=allShuangpin.get(pinyin)+tone;
        String shuangPin=allShuangpin.get(pinyin);
        TrieNode node = root;
        for (char c : shuangPin.toCharArray()) {
            node = node.getChildren().get(c);
            if (node == null) {
                return Collections.emptyList();
            }
        }
        List<String> result = node.isWord() ? node.getPinyinToCharacters().get(shuangPin) : Collections.emptyList();
        cache.put(pinyin, result);
        return result;
    }

    public void insertAll(){
        for(Map.Entry<String,ColumnPair> entry:extractCol.entrySet()){
            ColumnPair pair=entry.getValue();
            String pinYin= pair.getCol1();
            String character=pair.getCol2();

            this.insert(pinYin,character);
        }
    }

    public static HashMap<String, String> getAllShuangpin(){
        HashMap<String,String> allShuangpin=new HashMap<>();
        for(Map.Entry<String,ColumnPair> entry:extractCol.entrySet()){
            String pinYin= entry.getValue().getCol1();
            allShuangpin.put(pinYin,getShuangpin(pinYin));
        }
        return allShuangpin;
    }
    public static HashSet<String> getAllPinyin(){
        HashSet<String> allPinyin= new HashSet<>();
        for(Map.Entry<String,ColumnPair> entry:extractCol.entrySet()){
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

//    public static HashSet<String> getAllShuangpin(){
//        HashSet<String> allShuangpin=new HashSet<>();
//        Iterator<String> iterator=getAllPinyin().iterator();
//
//        while(iterator.hasNext()){
//            String element=iterator.next();
//            allShuangpin.add(getShuangpin(element));
//        }
//
//        return allShuangpin;
//    }

    public static void main(String[] args) {
    ShuangPinTrie test=new ShuangPinTrie();
    test.insertAll();
    Scanner scanner=new Scanner(System.in);
        System.out.println("Please enter pinyin");
        String pinyin=scanner.nextLine();
        System.out.println("the result is "+test.search(pinyin));
        scanner.close();

//    System.out.println(test.search("miao"));
//        System.out.println(getAllShuangpin().size());
    }



}
