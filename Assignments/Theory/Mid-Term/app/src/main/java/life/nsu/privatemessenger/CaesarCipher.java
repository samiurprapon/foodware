package life.nsu.privatemessenger;

//import android.util.Log;

public class CaesarCipher {

    public String encrypt(String text, int secret) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
//            if(i == 5) {
//                Log.e("CYPHER", "on 5 : "+Character.isUpperCase(text.charAt(i)));
//            }

            if (text.charAt(i) >= 'A' && text.charAt(i) <= 'Z') {
                char ch = (char)(((int)text.charAt(i) + secret - 65) % 26 + 65);
                result.append(ch);
            } else if(text.charAt(i) >= 'a' && text.charAt(i) <= 'z'){
                char ch = (char)(((int)text.charAt(i) +
                        secret - 97) % 26 + 97);
                result.append(ch);
            } else {
                result.append(text.charAt(i));
            }
        }

        return result.toString();
    }

    public String decrypt(String message) {
        String[] tokens = message.split(" ", 2);

//        Log.e("CYPHER", tokens[0]+"    "+tokens[1]);

        int secret = Integer.parseInt(tokens[0]);
        String cypher = tokens[1];

        return encrypt(cypher, 26-secret);
    }



}
