import java.util.HashMap;
import java.util.Map;

public class ChineseTrie {
    class TrieNode{
        Map<Character,TrieNode> children;
        boolean isWord;

        public TrieNode() {
            children=new HashMap<>();
            isWord=false;
        }
    }
    private final TrieNode root;
    public ChineseTrie() {
        root = new TrieNode();
    }

    public void insert(String CNchar){
        TrieNode curNode=root;
        for(int i=0;i<CNchar.length();i++){
            char ch=CNchar.charAt(i);
            TrieNode node=curNode.children.get(ch);
            if(node==null){
                node=new TrieNode();
                curNode.children.put(ch,node);
            }
            curNode=node;
        }
        curNode.isWord=true;
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
        return curNode.isWord;
    }
}
