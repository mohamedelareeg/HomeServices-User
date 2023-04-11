package com.rovaindu.homeservice.utils;

public class Utilites {
    public static int getNumberDigits(String inString){
        if (isEmpty(inString)) {
            return 0;
        }
        int numDigits= 0;
        int length= inString.length();
        for (int i = 0; i < length; i++) {
            if (Character.isDigit(inString.charAt(i))) {
                numDigits++;
            }
        }
        return numDigits;
    }

    public static boolean isEmpty(String inString) {
        return inString == null || inString.length() == 0;
    }
}
