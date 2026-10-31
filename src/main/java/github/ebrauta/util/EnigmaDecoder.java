package github.ebrauta.util;

public class EnigmaDecoder {
    private EnigmaDecoder() {}

    public static String decode(String cypheredText, int shift) {
        StringBuilder result = new StringBuilder();
        for (char c : cypheredText.toCharArray()) {
            if(c >= 'A' && c <= 'Z') {
                char newChar = (char) (c - shift);
                if (newChar < 'A') {
                    newChar += 26;
                }
                result.append(newChar);
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
