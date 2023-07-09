package ru.DreadBot.Store.Sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;



public class SqLiteConnection {
    private Connection connection;



    private final String dataBaseName = "TelegramDreadBot";

    public static final SqLiteConnection SQL_LITE_CONNECTION = new SqLiteConnection();

    public String getDataBaseName() {
        return dataBaseName;
    }

    private SqLiteConnection() {
        try {
            String databaseName = "telegramBot.db";
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
            initTable();
        } catch (SQLException e) {
            System.out.println("Connection to database has been broken");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    private void initTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS deals ("
                + " id         INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " date       Date,"
                + " description Text"
                + ")");
    }


}
