package com.patwolohan.Fodla.music;

import java.util.Scanner;

import com.patwolohan.Fodla.menu.ConsoleControls;
import com.patwolohan.Fodla.menu.IExecutable;

public class MusicViewMenuItem implements IExecutable {

	Scanner input = new Scanner(System.in);

	public MusicViewMenuItem() {

	}

	// @Override
	public void execute() {

		System.out.println(ConsoleControls.ANSI_YELLOW_BRIGHT + "... MUSIC VIEWER ..." + ConsoleControls.ANSI_RESET);
		System.out.println("\n" + MusicViewMenuItem.class.getName() + " has no MVC implementation.");
		System.out.println(" \n Press enter to return to the main menu");
		this.input.nextLine();
	}

}
