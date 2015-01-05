package at.hackenbergerhollander.rueckwaertssalto.exporter;

import at.hackenbergerhollander.rueckwaertssalto.dbstructure.Database;

import java.io.File;

/**
 * Exporter for an ER-Diagram
 *
 * @author Hackenberger Christoph
 * @version 1.0
 */
public class ERExporter implements Exporter {

    private File outputFile;

    /**
     * Creates a new ERExporter
     * @param outputFile the output file for the diagram
     */
    public ERExporter(File outputFile) {
        this.outputFile = outputFile;
    }

    /**
     * Exports the database structer as an ER-Diagram
     * @see at.hackenbergerhollander.rueckwaertssalto.exporter.Exporter#export(at.hackenbergerhollander.rueckwaertssalto.dbstructure.Database)
     */
    @Override
    public boolean export(Database db) {
        return false;
    }
}
