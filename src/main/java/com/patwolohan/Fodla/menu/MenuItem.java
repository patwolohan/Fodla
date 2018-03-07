package com.patwolohan.Fodla.menu;

/*****************************************************************
 *
 * Date: 2018
 * 
 * @author PW
 *
 *         Ref:https://github.com/bethrobson/Head-First-Design-Patterns/tree/master/src/headfirst/designpatterns/composite/menu
 * 
 *****************************************************************/

public class MenuItem extends MenuWidget {

	String name;
	String description;
	int itemID;
	boolean executable;
	String executor;

	// CONSTRUCTORS
	// ............................................................

	public MenuItem(int id, String name, String description) {
		this(id, name, description, "sayInvalidOption", false);
	}

	public MenuItem(int id, String name, String description, String className) {
		this(id, name, description, className, true);
	}

	// METHODS
	// ............................................................

	private MenuItem(int id, String name, String description, String className, boolean canExecute) {

		this.itemID = id;
		this.name = name;
		this.description = description;
		this.executable = canExecute;
		this.executor = className;

	}

	@Override
	public int getID() {
		return this.itemID;
	}

	@Override
	public String getDisplayName() {
		return this.name;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public boolean isExecutable() {
		return this.executable;
	}

	@Override
	public String getExecutor() {
		return this.executor;
	}

	@Override
	public void print() {
		System.out.print("IID:	" + getID());
		System.out.print(",	Display: " + getDisplayName());
		System.out.print(",	" + getDescription());
		System.out.print(",	" + isExecutable());
		System.out.println(", Class:" + getExecutor());

	}// EOM

	@Override
	public String toString() {

		return String.format("ID: %x Display: %s, %s, %b, Class: %s \n", getID(), getDisplayName(), getDescription(),
				isExecutable(), getExecutor());
	}
}
