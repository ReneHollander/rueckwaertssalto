package at.hackenbergerhollander.rueckwaertssalto.parser;

import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Attribute;
import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Database;
import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Table;
import at.hackenbergerhollander.rueckwaertssalto.dbstructure.attributeproperties.Foreign;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public Database parse() throws Exception {
        Database database = new Database("not yet set");

        ResultSet tables = dbmd.getTables(null, null, null, null);
        while (tables.next()) {
            Table table = database.addTable(tables.getString(TABLE_COLUMN_NAME));

            ResultSet columns = dbmd.getColumns(null, null, table.getName(), null);
            while (columns.next()) {
                Attribute attribute = table.addAttribute(columns.getString(COLUMN_COLUMN_NAME));
            }
        }

        // Check primary key
        for (Table table : database.getTables()) {
            ResultSet primaryKeyRs = dbmd.getExportedKeys("", "", table.getName());
            while (primaryKeyRs.next()) {
                String pkey = primaryKeyRs.getString("PKCOLUMN_NAME");
                table.getAttribute(pkey).getProperties().put(Attribute.PROPERTY_PRIMARY_KEY, null);
            }
        }

        for (Table table : database.getTables()) {
            ResultSet foreignKeyRs = dbmd.getImportedKeys("", "", table.getName());
            while (foreignKeyRs.next()) {
                for (int i = 1; i <= foreignKeyRs.getMetaData().getColumnCount(); i++) {
                    System.out.println(foreignKeyRs.getMetaData().getColumnName(i) + ": " + foreignKeyRs.getString(i));
                }
                System.out.println();


                String pkey = foreignKeyRs.getString("PKCOLUMN_NAME");
                String fktable = foreignKeyRs.getString("FKTABLE_NAME");
                String fkcolumn = foreignKeyRs.getString("FKCOLUMN_NAME");
                System.out.println(pkey);
                System.out.println(fktable);
                System.out.println(fkcolumn);
                System.out.println(table.getAttribute(pkey));
                table.getAttribute(pkey).getProperties().put(Attribute.PROPERTY_FOREIGN_KEY, new Foreign(database.getTable(fktable).getAttribute(fkcolumn)));

            }
        }

        return database;
    }

}
