/**
 * 
 */
package it.polito.pd2.WF;

/**
 * This immutable class represents information about an actor who can take in charge workflow action executions.
 * <br>
 * It includes the name and the role of the actors. These are both alphabetic strings.
 *
 */
public class Actor {
	/**
	 * The name of the actor (an alphabetic string)
	 */
	private String name;
	
	/**
	 * The role of the actor
	 */
	private String role;

	/**
	 * Builds an actor object with the given name and role
	 * @param name the actor name
	 * @param role the actor role
	 */
	public Actor(String name, String role) {
		this.name = name;
		this.role = role;
	}

	/**
	 * Gets the name of the actor
	 * @return the name of the actor (an alphabetic string)
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the role of the actor
	 * @return the role of the actor (an alphabetic string)
	 */
	public String getRole() {
		return role;
	}

}
