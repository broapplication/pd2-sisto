package it.polito.pd2.lab6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputConsole {
	private boolean silent=false;
	private InputStreamReader reader = new InputStreamReader(System.in);	
	private BufferedReader in = null;
	
	public InputConsole() {
		in = new BufferedReader(reader);	
	}
	
	public InputConsole(FileReader freader) {
		in = new BufferedReader(freader);	
	}
	
	public int getNextCommand() throws CommandReadException {
		Integer command;
		
		try {
			if (!silent)
				System.out.println("> ");
			String line = in.readLine();
			command = Integer.parseInt(line);
			return command.intValue();
		} catch (IOException e) {
			throw new CommandReadException(e);
		}		
	}

	/**
	 * @return the silent
	 */
	public boolean isSilent() {
		return silent;
	}

	/**
	 * @param silent the silent to set
	 */
	public void setSilent(boolean silent) {
		this.silent = silent;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InputConsole ic = new InputConsole();
		ic.setSilent(true);
		int command;
		try {
			while ( (command = ic.getNextCommand())!=0) {
				System.out.println("command received: " + command);
			}
		} catch (CommandReadException e) {
			System.err.println("Unable to read command.");
			e.printStackTrace();
		}
		System.out.println("terminating");
	}
}
