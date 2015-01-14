package at.hackenbergerhollander.rueckwaertssalto.parser;

import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Attribute;
import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Database;
import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Table;
import at.hackenbergerhollander.rueckwaertssalto.dbstructure.attributeproperties.Primary;

import javax.xml.crypto.Data;
import java.sql.*;

/**
 * Parse the JDBC Connection Metadata into a Database Object
 *
 * @author Rene Hollander
 * @version 1.0
 */
public class JDBCConnectionParser implements Parser {

    private static final String TABLE_COLUMN_NAME = "TABLE_NAME";
    private static final String COLUMN_COLUMN_NAME = "COLUMN_NAME";

    private Connection connection;
    private DatabaseMetaData dbmd;

    /**
     * Create a new JDBC Connection Parser from the connection
     *
     * @param connection Database to parse from
     */
    public JDBCConnectionParser(Connection connection) throws SQLException {
        this.connection = connection;
        this.dbmd = this.connection.getMetaData();
    }

    @Override
    public Database parse() throws SQLException {
        Database database = new Database("not yet set");

        ResultSet tables = dbmd.getTables(null, null, null, null);
        while (tables.next()) {
            Table table = database.addTable(tables.getString(TABLE_COLUMN_NAME));

            ResultSet columns = dbmd.getColumns(null, null, table.getName(), null);
            while (columns.next()) {
                for (int i = 1; i <= columns.getMetaData().getColumnCount(); i++) {
                    //System.out.println(columns.getMetaData().getColumnName(i) + ": " + columns.getString(i));
                }
                Attribute attribute = table.addAttribute(columns.getString(COLUMN_COLUMN_NAME));
                if (isPrimary(table, attribute)) {
                    attribute.getProperties().add(new Primary());
                }
            }
        }

        return database;
    }

    private boolean isPrimary(Table table, Attribute attribute) throws SQLException {
        ResultSet rs = dbmd.getExportedKeys("", "", table.getName());
        while (rs.next()) {
            String pkey = rs.getString("PKCOLUMN_NAME");
            if (pkey.equals(attribute.getName())) {
                return true;
            }
        }
        return false;
    }
}
