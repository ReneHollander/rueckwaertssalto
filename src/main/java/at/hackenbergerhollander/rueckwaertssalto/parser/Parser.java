package at.hackenbergerhollander.rueckwaertssalto.parser;

import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Database;

import java.sql.SQLException;

/**
 * Describes a parser that parses a structure defining a database into a Database Object
 *
 * @author Rene Hollander
 * @version 1.0.0
 */
public interface Parser {
    /**
     * Parse a data structure defining the database into a Database Object
     *
     * @return Database Object from the defining structure
     */
    public Database parse() throws Exception;
}
