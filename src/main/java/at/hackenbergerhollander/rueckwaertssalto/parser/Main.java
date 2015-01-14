package at.hackenbergerhollander.rueckwaertssalto.parser;

import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://10.0.5.248/premiere", "root", "password");
        JDBCConnectionParser jdbcConnectionParser = new JDBCConnectionParser(connection);
        Database db = jdbcConnectionParser.parse();
        System.out.println(db);
    }
}
