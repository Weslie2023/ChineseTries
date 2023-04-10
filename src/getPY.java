public class getPY {
    public static String getPY(String str, boolean useHanFormat) {
        if (str == null) {
            return "";
        }
        boolean lastBlank = true;
        StringBuffer sb = new StringBuffer();
        for (char ch : str.toCharArray()) {
            if (isHan(ch)) {
                List<String> pyList = getPY(ch, useHanFormat);
                if (!pyList.isEmpty()) {
                    if (!lastBlank) {
                        sb.append(" ");
                    }
                    sb.append(pyList.get(0)).append(' ');
                    lastBlank = true;
                }
            } else {
                sb.append(ch);
                lastBlank = false;
            }
        }
        return sb.toString();
    }
}
