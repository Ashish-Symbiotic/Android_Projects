package com.example.naveen.assatemanagement.databaseConnection;

public class LoginDataTempStorage {

    private static String username="";
    private static String password="";
    private static String type="";

    public LoginDataTempStorage(){}

    public LoginDataTempStorage(String username,String password,String type)
    {
        this.username=username;
        this.password=password;
        this.type=type;
    }

    public LoginDataTempStorage(String username,String password)
    {
        this.username=username;
        this.password=password;
    }

    public static String getUsername() {
        return username;
    }

    public static String getType() {
        return type;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        LoginDataTempStorage.password = password;
    }

    public static void setUsername(String username) {
        LoginDataTempStorage.username = username;
    }

    public static void setType(String type) {
        LoginDataTempStorage.type = type;
    }
}
