package com.patwolohan.Fodla.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*****************************************************************
 *
 * Date: 2018
 * 
 * @author PW
 *
 *         Ref:
 *         https://github.com/bethrobson/Head-First-Design-Patterns/tree/master/src/headfirst/designpatterns/composite/menu
 *         Ref: https://dzone.com/articles/java-string-format-examples Ref:
 *         http://www.novixys.com/blog/java-string-format-examples/
 * 
 *****************************************************************/

public class MenuHeading extends MenuWidget {

	ArrayList<MenuWidget> menu = new ArrayList<MenuWidget>();
	String name;
	String description;
	int headingID;

	// CONSTRUCTORS
	// ............................................................
	public MenuHeading(int id, String name, String description) {
		this.headingID = id;
		this.name = name;
		this.description = description;
	}

	// METHODS
	// ............................................................

	@Override
	public void add(MenuWidget menuWidget) {
		this.menu.add(menuWidget);
	}

	@Override
	public void remove(MenuWidget menuWidget) {
		this.menu.remove(menuWidget);
	}

	@Override
	public MenuWidget getChild(int i) {
		return this.menu.get(i);
	}

	@Override
	public int getID() {
		return this.headingID;
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
	public Map<Integer, String> getExecutorList() {

		Map<Integer, String> menuOfExecutors = new HashMap<Integer, String>();

		Iterator<MenuWidget> iterator = this.menu.iterator();

		int headerNumber = this.headingID * 10;

		while (iterator.hasNext()) {

			MenuWidget menuWidget = iterator.next();
			menuOfExecutors.put((headerNumber + menuWidget.getID()), menuWidget.getExecutor());
		} // EOW

		return menuOfExecutors;
	}

	@Override
	public void display() {

		Iterator<MenuWidget> iterator = this.menu.iterator();
		int headerNumber = this.headingID * 10;
		while (iterator.hasNext()) {

			MenuWidget menuWidget = iterator.next();
			System.out.println((headerNumber + menuWidget.getID()) + ".				" + menuWidget.getDisplayName());
		} // EOW
	}

	@Override
	public void print() {

		System.out.println("\n----------------------");
		System.out.print("HID:	" + getID());
		System.out.print(",	Display:	" + getDisplayName());
		System.out.println(",	" + getDescription());
		System.out.println("----------------------");
		Iterator<MenuWidget> iterator = this.menu.iterator();
		while (iterator.hasNext()) {
			MenuWidget menuWidget = iterator.next();
			menuWidget.print();
		}

	}// EOM

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(
				String.format("HEADING:	%x	%s,	%s	\n", getID(), getDisplayName(), getDescription()));
		Iterator<MenuWidget> iterator = this.menu.iterator();
		while (iterator.hasNext()) {
			MenuWidget menuWidget = iterator.next();
			sb.append(menuWidget.toString());
		}
		return sb.toString();

	}// EOM
}// EOC
