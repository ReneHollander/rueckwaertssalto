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
     * @throws java.sql.SQLException If a connection error occurs
     */
    public JDBCConnectionParser(Connection connection) throws SQLException {
        this.connection = connection;
        this.dbmd = this.connection.getMetaData();
    }

    @Override
    public Database parse() throws Exception {
        Database database = new Database(connection.getCatalog());

        ResultSet tables = dbmd.getTables(connection.getCatalog(), connection.getSchema(), null, null);
        while (tables.next()) {
            Table table = database.addTable(tables.getString(TABLE_COLUMN_NAME));

            ResultSet columns = dbmd.getColumns(connection.getCatalog(), connection.getSchema(), table.getName(), null);
            while (columns.next()) {
                table.addAttribute(columns.getString(COLUMN_COLUMN_NAME));
            }
            columns.close();
        }
        tables.close();

        // Find primary keys
        for (Table table : database.getTables()) {
            ResultSet primaryKeyRs = dbmd.getPrimaryKeys(connection.getCatalog(), connection.getSchema(), table.getName());
            while (primaryKeyRs.next()) {
                String pkey = primaryKeyRs.getString("COLUMN_NAME");
                table.getAttribute(pkey).getProperties().put(Attribute.PROPERTY_PRIMARY_KEY, null);
            }
            primaryKeyRs.close();
        }

        // Find not null attributes
        for (Table table : database.getTables()) {
            ResultSet columns = dbmd.getColumns(connection.getCatalog(), connection.getSchema(), table.getName(), null);
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                boolean nullable = columns.getBoolean("NULLABLE");
                Attribute attribute = table.getAttribute(columnName);
                if (!nullable && !columns.getBoolean("NULLABLE") && !attribute.getProperties().containsKey(Attribute.PROPERTY_PRIMARY_KEY)) {
                    attribute.getProperties().put(Attribute.PROPERTY_NOT_NULL, null);
                }
            }
            columns.close();
        }

        // Find unique attributes
        for (Table table : database.getTables()) {
            ResultSet indexInfo = dbmd.getIndexInfo(connection.getCatalog(), connection.getSchema(), table.getName(), true, true);
            while (indexInfo.next()) {
                String columnName = indexInfo.getString("COLUMN_NAME");
                Attribute attribute = table.getAttribute(columnName);
                if (!attribute.getProperties().containsKey(Attribute.PROPERTY_PRIMARY_KEY)) {
                    attribute.getProperties().put(Attribute.PROPERTY_UNIQUE, null);
                }
            }
            indexInfo.close();
        }

        // Find foreign keys
        for (Table table : database.getTables()) {
            ResultSet foreignKeyRs = dbmd.getImportedKeys(connection.getCatalog(), null, table.getName());
            while (foreignKeyRs.next()) {
                String pktable = foreignKeyRs.getString("PKTABLE_NAME");
                String pkey = foreignKeyRs.getString("PKCOLUMN_NAME");
                String fktable = foreignKeyRs.getString("FKTABLE_NAME");
                String fkcolumn = foreignKeyRs.getString("FKCOLUMN_NAME");
                table.getAttribute(fkcolumn).getProperties().put(Attribute.PROPERTY_FOREIGN_KEY, new Foreign(database.getTable(pktable).getAttribute(pkey)));
            }
            foreignKeyRs.close();
        }

        return database;
    }

}
