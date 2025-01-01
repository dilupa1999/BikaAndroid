package com.example.bika;

public class EmailHolder {
    private static String userEmail;

    private static String userMobile;

    public static String getuserMobile(){

        return userMobile;
    }

    public static void setuserMobile(String userMobile) {

        EmailHolder.userMobile = userMobile;
    }

    public static String getUserEmail() {

        return userEmail;
    }

    public static void setUserEmail(String userEmail) {

        EmailHolder.userEmail = userEmail;
    }
}
