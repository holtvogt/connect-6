package edu.kit.informatik.exceptions;

/**
 * This exception is to be thrown when the user enters invalid input to the
 * command line.
 */
public class InvalidInputException extends Exception {

    private static final long serialVersionUID = 8514003408497704829L;

    /**
     * Creates a new Exception which is thrown by invalid inputs.
     *
     * @param errorMessage Matching message to an invalid input.
     */
    public InvalidInputException(final String errorMessage) {
        super(errorMessage);
    }
}
