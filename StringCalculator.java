import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();

        try {
            String result = calculate(input);
            System.out.println(result);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка " + e.getMessage());
        }

        scanner.close();
    }

    private static String calculate(String input) {
        Pattern pattern = Pattern.compile("\"([^\"]{1,10})\"\\s*(\\+|-|\\*|/)\\s*(\"[^\"]{1,10}\"|\\d{1,2})");
        Matcher matcher = pattern.matcher(input);

        if (!matcher.find()) {
            throw new IllegalArgumentException("1");
        }

        String a = matcher.group(1);
        String operation = matcher.group(2);
        String b = matcher.group(3);


        int numberB = 0;
        if (isNumber(b)) {
            numberB = parseNumberFromString(b);
        } else if (!isValidString(b)) {
            throw new IllegalArgumentException("2");
        } else {
            b = b.substring(1, b.length() - 1);
        }

        switch (operation) {
            case "+":
                return truncate(a + b);

            case "-":
                return truncate(subtractStrings(a, b));

            case "*":
                return truncate(multiplyStringByNumber(a, numberB));

            case "/":
                return truncate(divideStringByNumber(a, numberB));

            default:
                throw new IllegalArgumentException("3" + operation);
        }
    }

    private static boolean isValidString(String str) {
        return str.startsWith("\"") && str.endsWith("\"") && str.length() <= 12; // 12 включает кавычки
    }

    private static boolean isNumber(String str) {
        try {
            int number = Integer.parseInt(str.trim());
            return number >= 1 && number <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int parseNumberFromString(String str) {
        int number = Integer.parseInt(str.trim());
        if (number < 1 || number > 10) {
            throw new IllegalArgumentException("4");
        }
        return number;
    }

    private static String subtractStrings(String a, String b) {
        if (a.contains(b)) {
            return a.replaceFirst(Pattern.quote(b), "");
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
            throw new IllegalArgumentException("5");
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
