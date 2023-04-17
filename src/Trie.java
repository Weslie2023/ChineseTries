package chineseTrie;
import java.util.HashMap;
import java.util.Map;

public class Trie {
	private TrieNode root;
	Map<Character, Integer> bihuaMap;
	
	private static class TrieNode {
        Map<Integer, TrieNode> children;
        boolean isEndOfWord;

        public TrieNode() {
            children = new HashMap<>();
            isEndOfWord = false;
        }
    }
	
    public Trie() {
        root = new TrieNode();
        bihuaMap = new HashMap<>();
        
		bihuaMap.put('横', 0);
		bihuaMap.put('竖', 1);
		bihuaMap.put('撇', 2);
		bihuaMap.put('捺', 3);
    }
    
    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
        	int code = bihuaMap.get(ch);
            node.children.putIfAbsent(code, new TrieNode());
            node = node.children.get(code);
        }
        node.isEndOfWord = true;
    }
    
    public boolean search(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
        	int code = bihuaMap.get(ch);
            if (!node.children.containsKey(code)) {
                return false;
            }
            node = node.children.get(code);
        }
        return node.isEndOfWord;
    }
    
    public static void main(String[] args) {
        Trie trie = new Trie();
        //先把汉字分成笔画
        Map<Character, String> chineseWordMap = new HashMap<>();
        chineseWordMap.put('大', "横撇捺");
        chineseWordMap.put('人', "撇捺");
        chineseWordMap.put('天', "横横撇捺");
        chineseWordMap.put('王', "横横竖横");
        //加入三个字
        trie.insert(chineseWordMap.get('大'));
        trie.insert(chineseWordMap.get('人'));
        trie.insert(chineseWordMap.get('天'));
        //搜索四个字，其中一个不在字典里
        System.out.println(trie.search(chineseWordMap.get('大'))); // 输出: true
        System.out.println(trie.search(chineseWordMap.get('人'))); // 输出: true
        System.out.println(trie.search(chineseWordMap.get('王'))); // 输出: false
        System.out.println(trie.search(chineseWordMap.get('天'))); // 输出: true
    }
    
}
