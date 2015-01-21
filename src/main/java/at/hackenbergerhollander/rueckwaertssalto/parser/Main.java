package at.hackenbergerhollander.rueckwaertssalto.parser;

import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Database;
import at.hackenbergerhollander.rueckwaertssalto.exporter.RMExporter;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql://10.0.5.30/rueckwaertssalto", "root", "password");
        JDBCConnectionParser jdbcConnectionParser = new JDBCConnectionParser(connection);
        Database db = jdbcConnectionParser.parse();
        RMExporter exporter = new RMExporter(new File("out.rtf"));
        exporter.export(db);
        System.out.println(db);
    }
}
