package me.mahziar.srv_website;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Configs {
    public static String dbHost;
    public static String dbUsername;
    public static String dbPassword;
    public static String dbName;
    public static String ipinfoToken;

    static {
        Properties prop = new Properties();
        String fileName = "app.config";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
        } catch (IOException ex) {
            System.out.println("error");
        }

        dbHost = prop.getProperty("SERVER_MANAGEMENT_HOST");
        dbUsername = prop.getProperty("SERVER_MANAGEMENT_USERNAME");
        dbPassword = prop.getProperty("SERVER_MANAGEMENT_PASSWORD");
        dbName = prop.getProperty("SERVER_MANAGEMENT_DB");
        ipinfoToken = prop.getProperty("IP_INFO_TOKEN");
    }

}
