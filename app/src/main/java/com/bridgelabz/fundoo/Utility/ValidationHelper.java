package com.bridgelabz.fundoo.Utility;

import java.util.regex.Pattern;

public class ValidationHelper
{
    private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" + "(?=.*[a-zA-Z])" + //any letter
            "(?=.*[@#$%^&+=])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces
            ".{6,}" +               //at least 4 characters
            "$");


    public static boolean validateEmail(String email) {
        if (email.isEmpty()) {
            return false;
        } else if (!EMAIL_PATTERN.matcher(email).matches()) {
            return false;
        } else {
            return true;
        }
    }
    public static boolean validatePassword(String password)
    {
        if(password.isEmpty())
        {
            return false;
        }
        else if(!PASSWORD_PATTERN.matcher(password).matches())
        {
            return false;
        }
        else
        {
            return true;
        }

    }
    public static boolean validateDescription(String description)
    {
        if(description.isEmpty()){
            return false;
        }
        else
        {
            return true;
        }
    }

    public static boolean validateTitle(String title)
    {
        if(title.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
}
