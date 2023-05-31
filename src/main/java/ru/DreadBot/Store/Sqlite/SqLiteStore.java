package ru.DreadBot.Store.Sqlite;

import ru.DreadBot.Store.BaseStore;
import ru.DreadBot.entity.Deal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SqLiteStore implements BaseStore {

    private Connection sqlLiteConnection = SqLiteConnection.SQL_LITE_CONNECTION.getConnection();

    @Override
    public void save(LocalDate date, String deal) {

        try {
            PreparedStatement insertStmt = sqlLiteConnection.prepareStatement(
                    "INSERT INTO deals(date, description) VALUES(?, ?)");
            insertStmt.setString(1, date.toString());
            insertStmt.setString(2, deal);
            insertStmt.executeUpdate();
            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                System.out.println("Row has been saved");
            }
        } catch (Exception e) {
            System.out.println(("can not save to DB"));
        }
    }

    @Override
    public List<String> selectAll(LocalDate date) {
        List<Deal> result = new ArrayList<>();
        try {
            Statement statement = sqlLiteConnection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT *  FROM deals ORDER BY deals.date DESC");
            while (rs.next()) {
                result.add(new Deal(rs.getInt("id"),
                        rs.getString("date"),
                        rs.getString("description")));
            }
        } catch (Exception e) {
            throw new RuntimeException("sql select error");
        }
        return result.stream().map(Deal::getDescription).collect(Collectors.toList());
    }
}
