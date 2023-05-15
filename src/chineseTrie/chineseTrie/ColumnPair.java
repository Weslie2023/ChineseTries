package chineseTrie.chineseTrie;

public class ColumnPair {
    private String col1;
    private String col2;

    public ColumnPair(String col1, String col2) {
        this.col1 = col1;
        this.col2 = col2;
    }

    public String getCol1() {
        return col1;
    }

    public String getCol2() {
        return col2;
    }

    @Override
    public String toString() {
        return "ColumnPair{" +
                "col1='" + col1 + '\'' +
                ", col2='" + col2 + '\'' +
                '}';
    }
}
