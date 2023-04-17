import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class isHan {
    /** 汉字最小unicode值 */
    public static final char HAN_MIN = '一';
    /** 汉字最大unicode值 */
    public static final char HAN_MAX = '龥';
    /** 汉字数据，从"一"到"龥" */
    public static final String[] HAN_DATA = new String[HAN_MAX - HAN_MIN + 1];
    /** 汉字数据文件 */
    private static final String HAN_DATA_FILE = "data.txt";
    /** 汉字数据文件编码 */
    private static final Charset FILE_CHARSET = Charset.forName("utf-8");

    /** 拼音数据下标 */
    private static final int INDEX_PY = 0;
    /** 部首数据下标 */
    private static final int INDEX_BS = 1;
    /** 笔画数据下标 */
    private static final int INDEX_BH = 2;
    /** 拼音数据（中文字母注音）下标 */
    private static final int INDEX_PY_HAN = 0;
    /** 拼音数据（英文字母注音）下标 */
    private static final int INDEX_PY_EN = 1;

    public static boolean isHan(char ch) {
        if (ch >= HAN_MIN && ch <= HAN_MAX) {
            return true;
        }
        return false;
    }

    public static void loadHanData() throws IOException {
        InputStream in = HanDict.class.getResourceAsStream(HAN_DATA_FILE);

        if (in == null) {
            throw new IOException(HAN_DATA_FILE + "data file does not exist！");
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, FILE_CHARSET));
            String line = null;
            int index = 0;
            while ((line = br.readLine()) != null) {
                HAN_DATA[index++] = line;
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

}
