package at.hackenbergerhollander.rueckwaertssalto.cli;

import at.hackenbergerhollander.rueckwaertssalto.error.InvalidOptionException;
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

    @Option(name = "-h", usage = "the hostname of the database", required = true)
    private String hostname;

    @Option(name = "-u", usage = "the username to login the database", required = true)
    private String username;

    @Option(name = "-p", usage = "the password to login the database", required = true)
    private String password;

    @Option(name = "-d", usage = "the database name", required = true)
    private String dbname;

    @Option(name = "-t", usage = "the type of export (ER or RM)", required = true)
    private String exporttype;

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
            throw new InvalidOptionException(ex.getMessage() + '\n' +
                    usage + '\n' + out.toString());
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
}
