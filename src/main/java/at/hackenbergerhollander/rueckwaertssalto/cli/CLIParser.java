package at.hackenbergerhollander.rueckwaertssalto.cli;

import at.hackenbergerhollander.rueckwaertssalto.error.InvalidOptionException;
import java.io.File;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.ByteArrayOutputStream;

/**
 * A Parser to parse options and arguments from the CLI
 *
 * @author Hackenberger Christoph
 * @version 1.0
 */
public class CLIParser {

    @Option(name = "-h", usage = "hostname of the dbms (Only MySQL supported)", required = true)
    private String hostname;

    @Option(name = "-u", usage = "username for the dbms", required = true)
    private String username;

    @Option(name = "-p", usage = "password for the dbms", required = true)
    private String password;

    @Option(name = "-d", usage = "database name", required = true)
    private String dbname;

    @Option(name = "-t", usage = "type of export file\n\t-ER: Chen ER diagramm in form of a dot file\n\t-RM: Relationemodell in form of a RTF File", required = true)
    private String exporttype;

    @Option(name = "-o", usage = "filename of the file to write the output to", required = true)
    private File outputfile;


    /**
     * This method starts the parsing of the options and arguments
     *
     * @param args  the arguments from the main method
     * @param usage a usage String of how to start the program
     * @throws InvalidOptionException if any thing goes wrong while parsing (ex: a required option is missing)
     */
    public void parse(String[] args, String usage) throws InvalidOptionException {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException ex) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            parser.printUsage(out);
            throw new InvalidOptionException(ex.getMessage() + '\n' + usage + '\n' + out.toString());
        }
    }

    public String getHostname() {
        return hostname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDBname() {
        return dbname;
    }

    public String getExportType() {
        return exporttype;
    }

    public File getOutputFile() {
        return outputfile;
    }
}
