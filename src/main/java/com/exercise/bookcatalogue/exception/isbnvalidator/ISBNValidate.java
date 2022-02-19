package com.exercise.bookcatalogue.exception.isbnvalidator;

public class ISBNValidate {

    public static final int SHORT_ISBN = 10;
    public static final int LONG_ISBN = 13;

    public boolean checkIsbn(String isbn) {

        if (isbn.length() == SHORT_ISBN) {
            return isValid10DigitISBN(isbn);
        } else if (isbn.length() == LONG_ISBN) {
            return isValid13DigitISBN(isbn);
        } else {
            throw new NumberFormatException("ISBN needs to be either 10 or 13 digits long.");
        }
    }

    private boolean isValid10DigitISBN(String isbn) {
        int total = 0;
        for (int i = 0; i < isbn.length(); i++) {
            if (!Character.isDigit(isbn.charAt(i))) {
                final int lastCharacter = isbn.length() - 1;
                if (i == lastCharacter && isbn.charAt(lastCharacter) == 'X') {
                    total += 10;
                } else {
                    System.out.println("ISBN needs to be number.");
                }
            } else {
                total += Character.getNumericValue(isbn.charAt(i)) * (10 - i);
            }
        }
        return total % 11 == 0;
    }

    private boolean isValid13DigitISBN(String isbn) {
        int total = 0;
        for (int i = 0; i < isbn.length(); i++) {
            if (i % 2 == 1) {
                total += Character.getNumericValue(isbn.charAt(i)) * 3;
            } else {
                total += Character.getNumericValue(isbn.charAt(i));
            }
        }
        return total % 10 == 0;
    }
}
