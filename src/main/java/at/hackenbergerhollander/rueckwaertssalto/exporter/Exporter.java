package at.hackenbergerhollander.rueckwaertssalto.exporter;

import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Database;

/**
 * Exporter to export a database structure in any modeling language
 *
 * @author Hackenberger Christoph
 * @version 1.0
 */
public interface Exporter {

    /**
     * Exports the database structure
     * @param db Database to export
     * @return true when export finishes successful, false when not
     */
    public boolean export(Database db) throws Exception;
}
