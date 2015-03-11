package at.hackenbergerhollander.rueckwaertssalto.cli;

import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Database;
import at.hackenbergerhollander.rueckwaertssalto.error.GeneralException;
import at.hackenbergerhollander.rueckwaertssalto.error.InvalidOptionException;
import at.hackenbergerhollander.rueckwaertssalto.error.MySQLConnectionException;
import at.hackenbergerhollander.rueckwaertssalto.exporter.ERExporter;
import at.hackenbergerhollander.rueckwaertssalto.exporter.Exporter;
import at.hackenbergerhollander.rueckwaertssalto.exporter.RMExporter;
import at.hackenbergerhollander.rueckwaertssalto.parser.JDBCConnectionParser;
import at.hackenbergerhollander.rueckwaertssalto.parser.Parser;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            CLIParser cliParser = new CLIParser();
            cliParser.parse(args, "java -jar Rueckwaertssalto.jar [options]");

            Connection connection = null;
            Parser parser = null;
            Exporter exporter = null;
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + cliParser.getHostname() + "/" + cliParser.getDBname(), cliParser.getUsername(), cliParser.getPassword());
            } catch (SQLException e) {
                throw new MySQLConnectionException(e.getMessage());
            }
            try {
                parser = new JDBCConnectionParser(connection);
            } catch (SQLException e) {
                throw new GeneralException("An error occured initializing database parser: " + e.getMessage());
            }
            switch (cliParser.getExportType().toLowerCase()) {
                case "rm":
                    exporter = new RMExporter(cliParser.getOutputFile());
                    break;
                case "er":
                    exporter = new ERExporter(cliParser.getOutputFile());
                    break;
                default:
                    throw new InvalidOptionException("The Export Type Option has to be either rm (Relationenmodell) or er (Dot File Chen ER)");
            }
            Database db = null;
            try {
                db = parser.parse();
            } catch (Exception e) {
                throw new GeneralException("An error occured parsing jdbc connection: " + e.getMessage());
            }

            try {
                exporter.export(db);
            } catch (Exception e) {
                throw new GeneralException("An error occured exporting parsed database to file: " + e.getMessage());
            }
        } catch (InvalidOptionException | GeneralException ex) {
            System.err.println(ex.getMessage());
        } catch (MySQLConnectionException mce) {
            System.err.println("An error occured while connecting to the dbms: " + mce.getMessage());
        }
    }

}