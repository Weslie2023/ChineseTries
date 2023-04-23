package chineseTrie;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

class ChineseTrie {
	private TrieNode root;

	public ChineseTrie() {
		root = new TrieNode();
	}

	// 添加拼音和汉字对应关系的方法
	public void insert(String pinyin, Character character) {
		TrieNode node = root;
		for (char c : pinyin.toCharArray()) {
			node.getChildren().putIfAbsent(c, new TrieNode());
			node = node.getChildren().get(c);
		}
		node.setWordEnd(true);
		node.getPinyinToCharacters().putIfAbsent(pinyin, new ArrayList<>());
		node.getPinyinToCharacters().get(pinyin).add(character);
	}

	// 根据拼音查询汉字的方法
	public List<Character> search(String pinyin) {
		TrieNode node = root;
		for (char c : pinyin.toCharArray()) {
			node = node.getChildren().get(c);
			if (node == null) {
				return Collections.emptyList();
			}
		}
		return node.isWordEnd() ? node.getPinyinToCharacters().get(pinyin) : Collections.emptyList();
	}

	public static void main(String[] args) {
		ChineseTrie trie = new ChineseTrie();
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
