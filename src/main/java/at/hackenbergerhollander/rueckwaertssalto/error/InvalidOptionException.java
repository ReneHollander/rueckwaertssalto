package at.hackenbergerhollander.rueckwaertssalto.error;

/**
 * InvalidOptionException
 * @author Hackenberger Christoph
 * @version 1.0
 */
public class InvalidOptionException extends Exception {

    /**
     * Creates a new object of InvalidOptionException
     * @param message the message of the Exception
     */
    public InvalidOptionException(String message) {
        super(message);
    }
}
