import java.util.Scanner;

public class StringCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите выражение:");
        String input = scanner.nextLine();

        try {
            String result = calculate(input);
            System.out.println(result);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }

    private static String calculate(String input) {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Ошибка");
        }

        String a = parts[0];
        String operation = parts[1];
        String b = parts[2];

        if (!isValidString(a)) {
            throw new IllegalArgumentException("Ошибка");
        }

        a = a.substring(1, a.length() - 1);

        int numberB = 0;
        if (isNumber(b)) {
            numberB = parseNumberFromString(b);
        } else if (!isValidString(b)) {
            throw new IllegalArgumentException("Ошибка");
        } else {
            b = b.substring(1, b.length() - 1);
        }

        switch (operation) {
            case "+":
                return truncate(a + (isNumber(b) ? "" : b));

            case "-":
                return truncate(subtractStrings(a, b));

            case "*":
                return truncate(multiplyStringByNumber(a, numberB));

            case "/":
                return truncate(divideStringByNumber(a, numberB));

            default:
                throw new IllegalArgumentException("Ошибка");
        }
    }

    private static boolean isValidString(String str) {
        return str.startsWith("\"") && str.endsWith("\"");
    }

    private static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int parseNumberFromString(String str) {
        int number = Integer.parseInt(str);
        if (number < 1 || number > 10) {
            throw new IllegalArgumentException("Ошибка");
        }
        return number;
    }

    private static String subtractStrings(String a, String b) {
        if (a.contains(b)) {
            return a.replaceFirst(b, "");
        } else {
            return a;
        }
    }

    private static String multiplyStringByNumber(String str, int number) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < number; i++) {
            result.append(str);
        }
        return result.toString();
    }

    private static String divideStringByNumber(String str, int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Ошибка");
        }
        int length = str.length() / number;
        return str.substring(0, length);
    }

    private static String truncate(String result) {
        if (result.length() > 40) {
            return result.substring(0, 40) + "...";
        }
        return result;
    }
}
