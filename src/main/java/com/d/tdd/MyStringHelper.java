package com.d.tdd;

public class MyStringHelper {
    public String replaceAInFirst2Positions(String string) {
        if(string == null){
            return null;
        }

        if(string.length() < 2){
            return string.replaceAll("A", "");
        }

        String firstTwoChars = string.substring(0, 2);
        String restOfTheString = string.substring(2);

        return firstTwoChars.replaceAll("A", "") + restOfTheString;
    }

    public boolean areTwoFirstAndTwoLastCharsTheSame(String string) {

        int length = string.length();
        if (length < 2)
            return false;

        String first2Chars = string.substring(0, 2);
        String last2Chars = string.substring(length - 2);

        return first2Chars.equals(last2Chars);
    }
}
