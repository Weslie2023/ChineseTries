
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

class ChineseTrie {
	private TrieNode root;

	public ChineseTrie() {
		root = new TrieNode();
	}

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
	
    public void loadPinyinDataset(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" : ");
                if (parts.length == 2) {
                    Character character = parts[0].charAt(0);
                    System.out.print(character);
                    String pinyin = parts[1];
                    insert(pinyin, character);
                }
            }
        }
    }

    public Set<String> getAllPinyin() {
        Set<String> pinyinSet = new HashSet<>();
        getAllPinyinHelper(root, new StringBuilder(), pinyinSet);
        return pinyinSet;
    }

    private void getAllPinyinHelper(TrieNode node, StringBuilder currentPinyin, Set<String> pinyinSet) {
        if (node.isWordEnd()) {
            pinyinSet.add(currentPinyin.toString());
        }

        for (char c : node.getChildren().keySet()) {
            currentPinyin.append(c);
            getAllPinyinHelper(node.getChildren().get(c), currentPinyin, pinyinSet);
            currentPinyin.deleteCharAt(currentPinyin.length() - 1);
        }
    }

	public static void main(String[] args) {
        ChineseTrie trie = new ChineseTrie();

        long startTime = System.currentTimeMillis();
        try {
            trie.loadPinyinDataset("pinyinDataset.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;

        try {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("output.txt"), StandardCharsets.UTF_8));
            out.println("The construction time of the trie： " + duration + " ns");


            Set<String> allPinyin = trie.getAllPinyin();
            int queryCount = 1000;
            long totalTime = 0;

            for (int i = 0; i < queryCount; i++) {

                int randomIndex = new Random().nextInt(allPinyin.size());
                String randomPinyin = allPinyin.toArray(new String[0])[randomIndex];

                long startTime2 = System.nanoTime();
                trie.search(randomPinyin);
                long endTime2 = System.nanoTime();

                totalTime += (endTime2 - startTime2);
            }

            double averageQueryTime = totalTime / (double) queryCount;


            out.println("avg searching time： " + averageQueryTime + " ns");
            out.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

	}

}
