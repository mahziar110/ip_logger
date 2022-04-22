package me.mahziar.srv_website.client_ip_submit;

import me.mahziar.srv_website.Configs;

import java.sql.*;
import java.util.Map;

public class Model {
    public static Boolean SubmitIpInDB(Map<String, String> ipInfo) {
        String url = "jdbc:postgresql://" + Configs.dbHost + ":" + Configs.dbPort + "/" + Configs.dbName;
        try (Connection connection = DriverManager.getConnection(url, Configs.dbUsername, Configs.dbPassword)) {
            Statement statement = connection.createStatement();
            String query =
                    String.format(
                            "INSERT INTO clients (ip, country, city, location, organization, date, time)\n" +
                            "VALUES ('%s', '%s', '%s', '%s', '%s', CURRENT_DATE, CURRENT_TIME)\n",
                            ipInfo.get("ip"),
                            ipInfo.get("country"),
                            ipInfo.get("city"),
                            ipInfo.get("location"),
                            ipInfo.get("org")
                    );
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return false;
        }
    }
}
