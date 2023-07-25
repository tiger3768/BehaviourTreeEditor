package com.bteditor;

/**
 * Represents an entry in the Behaviour Tree Editor.
 */
public class Entry {
	
	private String type;
	private String name;
	private String behaviour;
	private int numberOfChildren;
	

	/**
	 * Constructs an Entry object with the specified type, name, behaviour, and number of children.
	 *
	 * @param type            The type of the entry.
	 * @param name            The name of the entry.
	 * @param behaviour       The behavior of the entry.
	 * @param numberOfChildren The number of children of the entry.
	 */
	public Entry(String type, String name, String behaviour, int numberOfChildren) {
		super();
		this.type = type;
		this.name = name;
		this.behaviour = behaviour;
		this.numberOfChildren = numberOfChildren;
	}
	
	/**
	 * Constructs an Entry object with the specified type, name, and behaviour.
	 *
	 * @param type      The type of the entry.
	 * @param name      The name of the entry.
	 * @param behaviour The behavior of the entry.
	 */
	public Entry(String type, String name, String behaviour) {
		super();
		this.type = type;
		this.name = name;
		this.behaviour = behaviour;
	}

	/**
	 * Constructs an Entry object with the specified type and name.
	 *
	 * @param type The type of the entry.
	 * @param name The name of the entry.
	 */
	public Entry(String type, String name) {
		super();
		this.type = type;
		this.name = name;
	}
	
	/**
	 * Gets the number of children of the entry.
	 *
	 * @return The number of children.
	 */
	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	/**
	 * Sets the number of children of the entry.
	 *
	 * @param numberOfChildren The number of children to set.
	 */
	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}
	
	/**
	 * Gets the type of the entry.
	 *
	 * @return The type.
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Gets the name of the entry.
	 *
	 * @return The name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the behavior of the entry.
	 *
	 * @return The behavior.
	 */
	public String getBehaviour() {
		return behaviour;
	}

	@Override
	public String toString() {
		return type + " " + name;
	}
	
}
