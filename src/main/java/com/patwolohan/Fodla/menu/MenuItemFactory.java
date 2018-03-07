package com.patwolohan.Fodla.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.patwolohan.Fodla.music.MusicViewMenuItem;
import com.patwolohan.Fodla.playlist.PlaylistViewMenuItem;
import com.patwolohan.Fodla.user.UserViewMenuItem;

/*
 *	This	class	could	be	used	instead	of	the	generic	Factory	class
 */

public class MenuItemFactory {
	private static Logger LOG;

	public MenuItemFactory() {
		LOG = LogManager.getLogger(MenuItemFactory.class);
	}

	public IExecutable getMenuItem(String className) {
		if ("com.patwolohan.Fodla.playlist.PlaylistViewMenuItem".equals(className)) {
			return new PlaylistViewMenuItem();
		}
		if ("com.patwolohan.Fodla.music.MusicViewMenuItem".equals(className)) {
			return new MusicViewMenuItem();
		}
		if ("com.patwolohan.Fodla.user.UserViewMenuItem".equals(className)) {
			return new UserViewMenuItem();
		}

		// for each menu item take a copy of an if line above change the class it is
		// looking for
		// and the class to use
		// this catches deliberately undefined menu items and errors

		if ("com.patwolohan.Fodla.playlist.UndefinedMenuItem".equals(className)) {
			return new UndefinedMenuItem();
		} else {
			LOG.error("Undefined	class	requested" + className);
			return new UndefinedMenuItem();
		}
	}
}
