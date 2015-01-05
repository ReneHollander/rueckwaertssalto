package at.hackenbergerhollander.rueckwaertssalto.exporter;

import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Database;

import java.io.File;

/**
 * Exporter for a relational model
 *
 * @author Hackenberger Christoph
 * @version 1.0
 */
public class RMExporter implements Exporter {


    private File outputFile;

    /**
     * Creates a new RMExporter
     * @param outputFile the output file for the model
     */
    public RMExporter(File outputFile) {
        this.outputFile = outputFile;
    }

    /**
     * Exports the database structer as a relational model
     * @see at.hackenbergerhollander.rueckwaertssalto.exporter.Exporter#export(at.hackenbergerhollander.rueckwaertssalto.dbstructure.Database)
     */
    @Override
    public boolean export(Database db) {
        return false;
    }
}
