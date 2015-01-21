package at.hackenbergerhollander.rueckwaertssalto.parser;

import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql://10.0.5.30/rueckwaertssalto", "root", "password");
        JDBCConnectionParser jdbcConnectionParser = new JDBCConnectionParser(connection);
        Database db = jdbcConnectionParser.parse();
        System.out.println(db);
    }
}
