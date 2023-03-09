import java.util.regex.Pattern;

public class InputValidator {

    public static boolean username_check(String input) {
        // List of special characters that can potentially break the database or backend code
        String[] specialCharacters = {"'", "\"", ";", "(", ")", "{", "}", "[", "]", ",", "=", ">", "<", "!", "-", "+", "*", "/", "%", "&", "|", "^", "~", "`", "@", "#", "$"};
        // List of keywords that can potentially break the database or backend code
        String[] keywords = {"SELECT", "INSERT", "UPDATE", "DELETE", "DROP", "CREATE", "ALTER", "TRUNCATE", "EXECUTE", "GRANT", "REVOKE"};

        // Check if the input is empty or contains any newlines
        if (input == null || input.trim().isEmpty() || input.contains("\n")) {
            return false;
        }

        // Check if the input contains any of the special characters or keywords
        for (String specialChar : specialCharacters) {
            if (input.contains(specialChar)) {
                return false;
            }
        }
        for (String keyword : keywords) {
            if (Pattern.compile("\\b" + keyword + "\\b", Pattern.CASE_INSENSITIVE).matcher(input).find()) {
                return false;
            }
        }
        return true;
    }

    public static boolean password_check(String input) {
        // List of special characters that can potentially break the database or backend code
        //String[] specialCharacters = {"'", "\"", ";", "(", ")", "{", "}", "[", "]", ",", "=", ">", "<", "!", "-", "+", "*", "/", "%", "&", "|", "^", "~", "`", "@", "#", "$"};
        // List of keywords that can potentially break the database or backend code
        String[] keywords = {"SELECT", "INSERT", "UPDATE", "DELETE", "DROP", "CREATE", "ALTER", "TRUNCATE", "EXECUTE", "GRANT", "REVOKE"};

        // Check if the input is empty or contains any newlines
        if (input == null || input.trim().isEmpty() || input.contains("\n")) {
            return false;
        }

//        // Check if the input contains any of the special characters or keywords
//        for (String specialChar : specialCharacters) {
//            if (input.contains(specialChar)) {
//                return false;
//            }
//        }
        for (String keyword : keywords) {
            if (Pattern.compile("\\b" + keyword + "\\b", Pattern.CASE_INSENSITIVE).matcher(input).find()) {
                return false;
            }
        }
        return true;
    }


}