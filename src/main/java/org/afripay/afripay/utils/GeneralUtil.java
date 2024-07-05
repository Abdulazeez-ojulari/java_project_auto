package org.afripay.afripay.utils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class GeneralUtil {    
    
    public static String generateStringOTP() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }

    public static long generateOTP() {
        Random random = new Random();
        return random.nextLong(10000);
    }

    public static BigDecimal getBigDecimal(String value) {
        try {
            if (Objects.nonNull(value)) {
                return new BigDecimal(value);
            }
        } catch (Exception e) {
            log.info("Error parsing {} to BigDecimal", value);
        }

        return new BigDecimal("0.00");
    }

    public static boolean isAdmin(String username) {
        return username.startsWith(".") && username.endsWith(".");
    }

    public static String generateTransactionRef(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmssSSSSSSSS");

        String ref = UUID.randomUUID().toString().replaceAll("[^0-9]", "");
        ref = ref.substring(0, 5);

        return dateFormat.format(date) + ref;
    }

    public static boolean stringIsNullOrEmpty(String value) {
        return Objects.isNull(value) || value.isEmpty();
    }

    public static boolean longIsNullOrZero(Long value) {
        return Objects.isNull(value) || value.equals(0L);
    }

    public static boolean bigDecimalIsNull(BigDecimal value) {
        return Objects.isNull(value) || value.compareTo(BigDecimal.ZERO) == 0;
    }

    public static boolean listIsEmpty(List<String> value) {
        return value.isEmpty();
    }

    public static String[] split(String toSplit, String splitValue) {
        return toSplit.split(splitValue);
    }

    public static String sha512(String args) {

        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            //Add password bytes to digest
            md.update(args.getBytes(StandardCharsets.UTF_8));
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //These bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format

            return Base64.getEncoder().encodeToString(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] generateRandomWords(int numberOfWords) {
        String[] randomStrings = new String[numberOfWords];
        Random random = new Random();
        for (int i = 0; i < numberOfWords; i++) {
            char[] word = new char[random.nextInt(8) + 3]; // words of length 3 through 10. (1 and 2-letter words are boring.)
            for (int j = 0; j < word.length; j++) {
                word[j] = (char) ('a' + random.nextInt(26));
            }
            randomStrings[i] = new String(word);
        }
        return randomStrings;
    }

    public static String generateRandomWord(int wordLength) {
        Random r = new Random(); // Initialize a Random Number Generator with SysTime as the seed
        StringBuilder sb = new StringBuilder(wordLength);
        for (int i = 0; i < wordLength; i++) { // For each letter in the word
            char tmp = (char) ('a' + r.nextInt('z' - 'a')); // Generate a letter between a and z
            sb.append(tmp); // Add it to the String
        }
        return sb.toString();
    }

    /**
     * ^ represents starting character of the string.
     * (?=.*[0-9]) represents a digit must occur at least once.
     * (?=.*[a-z]) represents a lower case alphabet must occur at least once.
     * (?=.*[A-Z]) represents an upper case alphabet that must occur at least once.
     * (?=.*[@#$%^&-+=()] represents a special character that must occur at least once.
     * (?=\\S+$) white spaces don’t allowed in the entire string.
     * .{8, 20} represents at least 8 characters and at most 20 characters.
     * $ represents the end of the string.
     */
    public static boolean isValidPassword(String password) {

        // Regex to check valid password.
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the password is empty
        // return false
        if (password == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given password
        // and regular expression.
        Matcher m = p.matcher(password);

        // Return if the password
        // matched the ReGex
        return m.matches();
    }

    public static boolean isValidGACurrency(String currency) {
        return !stringIsNullOrEmpty(currency) && "NGN".equals(currency);
    }


//    public static String checkPasswordValidity(String password) {
//        int passwordLength = 8, upChars = 0, lowChars = 0;
//        int special = 0, digits = 0;
//        char ch;
//        StringBuilder errors = new StringBuilder();
//
//        int total = password.length();
//        if (total < passwordLength) {
//            errors.append("Minimum number of characters: 8").append("\n");
//        } else {
//            for (int i = 0; i < total; i++) {
//                ch = password.charAt(i);
//                if (Character.isUpperCase(ch))
//                    upChars = 1;
//                else if (Character.isLowerCase(ch))
//                    lowChars = 1;
//                else if (Character.isDigit(ch))
//                    digits = 1;
//                else
//                    special = 1;
//            }
//        }
//
//        if (upChars != 1) {
//            errors.append("Password must contain a capital letter").append("\n");
//        }
//
//        if (lowChars != 1) {
//            errors.append("Password must contain a lowercase letter").append("\n");
//        }
//
//        if (digits != 1) {
//            errors.append("Password must contain a number").append("\n");
//        }
//
//        if (upChars == 1 && lowChars == 1 && digits == 1 && special == 1)
//            System.out.println("\nThe Password is Strong.");
//        else
//            System.out.println("\nThe Password is Weak.");
//    }

    public static void main(String[] args) {
        String reference = "test_20191123132233";
        log.info(String.valueOf(LocalDateTime.parse("2022-12-13T00:00:00")));
    }

}