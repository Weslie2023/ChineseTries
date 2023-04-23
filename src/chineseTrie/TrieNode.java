package chineseTrie;

import java.util.*;


class TrieNode {
    private Map<Character, TrieNode> children;
    private Map<String, List<Character>> pinyinToCharacters;
    private boolean isWordEnd;

    public TrieNode() {
        children = new HashMap<>();
        pinyinToCharacters = new HashMap<>();
        isWordEnd = false;
    }

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    public Map<String, List<Character>> getPinyinToCharacters() {
        return pinyinToCharacters;
    }

    public boolean isWordEnd() {
        return isWordEnd;
    }

    public void setWordEnd(boolean isWordEnd) {
        this.isWordEnd = isWordEnd;
    }
}

