package it.polito.pd2.lab6;

import java.io.IOException;

/**
 * This exception is thrown when it is not possible to read a command
 * from the input console either because the input was malformed or for
 * some other reason
 *
 */
public class CommandReadException extends Exception {

	public CommandReadException(Exception e) {
		super(e);
	}

}
