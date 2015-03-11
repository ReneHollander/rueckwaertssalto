package at.hackenbergerhollander.rueckwaertssalto.parser;

import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Database;
import at.hackenbergerhollander.rueckwaertssalto.exporter.ERExporter;
import at.hackenbergerhollander.rueckwaertssalto.exporter.Exporter;
import at.hackenbergerhollander.rueckwaertssalto.exporter.RMExporter;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/testdb", "root", "1234");
        JDBCConnectionParser jdbcConnectionParser = new JDBCConnectionParser(connection);
        Database db = jdbcConnectionParser.parse();
        Exporter exporter = new ERExporter(new File("out.gv"));
        exporter.export(db);
        System.out.println(db);
    }
}
