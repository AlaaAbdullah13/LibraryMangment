package app.util;

public class Validator {

    public static boolean isValidName(String name) {
        return name != null && name.trim().matches("[a-zA-Z ]{3,}");
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public static boolean isValidText(String text) {
        return text != null && text.trim().matches("[A-Za-z0-9 ,.?!'-]{2,}");
    }

    public static boolean isPositiveNumber(String input) {
        return input != null && input.matches("\\d+") && Integer.parseInt(input) > 0;
    }

    public static boolean isValidId(int id) {
        return id > 0;
    }
}
