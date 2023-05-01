import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

public class ShuangPinTrie {
    private class TrieNode{
        private Map<Character, TrieNode> children;
        private Map<String, List<Character>> pinyinToCharacters;
        boolean isWord;

        public TrieNode(){
            children = new HashMap<>();
            pinyinToCharacters=new HashMap<>();
            isWord = false;
        }

        public Map<Character, TrieNode> getChildren(){
            return children;
        }

        public Map<String, List<Character>> getPinyinToCharacters(){
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
    private static HashSet<String> vowelSet = new HashSet<>(Arrays.asList("q","w","e","r","t","y","o","p","a","s","d","f","g","h","j","k","l","z","x","c","b","n","m","sh","ch","zh"));

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

    public ShuangPinTrie(){
        root = new TrieNode();
    }

    public static String getShuangpin(String pinYin) {
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

    public void insert(String pinyin, Character character) {
        String shuangPin=getShuangpin(pinyin);
        TrieNode node = root;
        for (char c : shuangPin.toCharArray()) {
            node.getChildren().putIfAbsent(c, new TrieNode());
            node = node.getChildren().get(c);
        }
        node.setWordEnd(true);
        node.getPinyinToCharacters().putIfAbsent(shuangPin, new ArrayList<>());
        node.getPinyinToCharacters().get(shuangPin).add(character);
    }

    public List<Character> search(String pinyin) {
        String shuangPin=getShuangpin(pinyin);
        TrieNode node = root;
        for (char c : shuangPin.toCharArray()) {
            node = node.getChildren().get(c);
            if (node == null) {
                return Collections.emptyList();
            }
        }
        return node.isWord() ? node.getPinyinToCharacters().get(shuangPin) : Collections.emptyList();
    }

    public static void main(String[] args) {
        String pinYin="en";
        System.out.println(getShuangpin(pinYin));
        System.out.println(consonantMap);
        String pinYin2="eng";
        System.out.println(getShuangpin(pinYin2));
        String pinYin3="a";
        System.out.println(getShuangpin(pinYin3));
        String pinYin4="meng";
        System.out.println(getShuangpin(pinYin4));

        ShuangPinTrie trie = new ShuangPinTrie();
        trie.insert("zhong", '中');
        trie.insert("zhong", '忠');
        trie.insert("zhong", '种');
        trie.insert("shen", '深');
        trie.insert("shen", '神');
        trie.insert("shen", '申');
        trie.insert("xiao", '小');
        trie.insert("xiao", '笑');
        trie.insert("xiao", '晓');
        trie.insert("da", '大');
        trie.insert("da", '达');
        trie.insert("da", '打');

        List<Character> zhong = trie.search("zhong");
        List<Character> guo = trie.search("guo");
        List<Character> xiao = trie.search("xiao");
        List<Character> da = trie.search("da");

        try {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("output.txt"), "UTF-8"));
            out.println("zhong: " + zhong);
            out.println("guo: " + guo);
            out.println("xiao: " + xiao);
            out.println("da: " + da);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
