package at.hackenbergerhollander.rueckwaertssalto.error;

/**
 * MySQLConnectionException
 *
 * @author Rene Hollander
 * @version 1.0
 */
public class MySQLConnectionException extends Exception {

    /**
     * Creates a new object of MySQLConnectionException
     *
     * @param message the message of the Exception
     */
    public MySQLConnectionException(String message) {
        super(message);
    }
}
