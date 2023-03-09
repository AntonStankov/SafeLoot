import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PasswordChecker {

    // This method checks if the password matches any password in the lists of passwords provided in the files.

    public static boolean checkPassword(String password) throws IOException {


        BufferedReader reader = new BufferedReader(new FileReader("password_guessser/rockyou1.txt"));
        BufferedReader reader2 = new BufferedReader(new FileReader("password_guessser/rockyou2.txt"));
        BufferedReader reader3 = new BufferedReader(new FileReader("password_guessser/common_passwords.txt"));
        BufferedReader reader4 = new BufferedReader(new FileReader("password_guessser/top_200_passwords.txt"));

        String line;

        //file 1
        while ((line = reader.readLine()) != null) {
            if (password.equals(line)) {
                reader.close();
                return false;
            }
        }
        //file 2
        while ((line = reader2.readLine()) != null) {
            if (password.equals(line)) {
                reader.close();
                return false;
            }
        }
        //file 3
        while ((line = reader3.readLine()) != null) {
            if (password.equals(line)) {
                reader.close();
                return false;
            }
        }
        //file 4
        while ((line = reader4.readLine()) != null) {
            if (password.equals(line)) {
                reader.close();
                return false;
            }
        }

        reader.close();
        return true;
    }

    // This method checks the strength of a password and returns a score from 0 to 5.
    public static int checkStrength(String password) {
        int score = 0;
        if (password.length() >= 8) {
            score++;
        }
        if (password.matches("(?=.*[a-z]).*")) {
            score++;
        }
        if (password.matches("(?=.*[A-Z]).*")) {
            score++;
        }
        if (password.matches("(?=.*[0-9]).*")) {
            score++;
        }
        if (password.matches("(?=.*[!@#$%^&*()_+\\-={};':\"\\\\|,.<>\\/?]).*")) {
            score++;
        }
        return score;

    }
}