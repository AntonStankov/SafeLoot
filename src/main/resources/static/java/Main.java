public class Main {
    public static void main(String[] args) {
        String input = "This is a valid input.";
        boolean isValid = InputValidator.password_check(input);
        System.out.println(input + " is valid: " + isValid);

        input = "SELECT * FROM users";
        isValid = InputValidator.password_check(input);
        System.out.println(input + " is valid for pass: " + isValid);
        isValid = InputValidator.username_check(input);
        System.out.println(input + " is valid for user: " + isValid);

        input = "1van4ov";
        isValid = InputValidator.username_check(input);
        System.out.println(input + " is valid: " + isValid);


        input = PasswordGenerator.generatePassword(12);
        isValid = InputValidator.password_check(input);
        System.out.println(input + " Generated pass is valid: " + isValid); // true
    }
}
