package com.patwolohan.Fodla;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.patwolohan.Fodla.data.DataManagerSQLite;
import com.patwolohan.Fodla.menu.MenuBuilder;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

/*****************************************************************
 *
 * Date: 2018
 *
 * @author PW
 *
 *
 *         The purpose of this application is to provide an example for the
 *         following:
 *
 *         - Demonstrates the use of development tools : GIT, MAVEN, Eclipse -
 *         Demonstrates how to use Eclipse - Provides a refresher of OOP in Java
 *         - Provide an introduction to project file structure layout - MAVEN
 *         Archetype - Show how to setup Log4j2 - Provide examples of pattern
 *         use (not necessarily good use) - Singleton, Strategy, Factory, DAO -
 *         Provide an MVC example - Demonstrate the use of parameters on the
 *         command line, JOpt library - Demonstrate database access methods and
 *         patterns based on SQLite and java JDBC libraries - Demonstrate
 *         Reflection, Exceptions, Lists, Maps, File handling
 *
 *         Eclipse usage notes - if you see a message cannot resolve to type and
 *         it is one of your own classes after import --A: try Menu, Project >
 *         Clean see if that resolves it - if you have created folders or you
 *         expect to see log files in the log folder after running the code byt
 *         don't see --A: try Meny, File > Refresh to refresh the Eclipse
 *         package explorer tree
 *
 *****************************************************************/
public class App {
	public static void main(String[] args) {

		// To view the arguments being entered
		seeCommandlineInput(args);
		// To instantiate App class based in the parameters entered at the commandline
		actionCommandlineInput(args);
	}

	// DATA
	// ............................................................

	// define attributes
	private Scanner someInput;
	private Date today;

	// This is added to every class that needs to log with one change
	// The getLogger( ) part should contain the name of the class its in
	private static Logger LOG;
	private static String VERSION = "0.5";

	// The URL and name of the SQLite database
	// should be of the form jdbc:sqlite:database/fodla.db
	private String dbURL;

	// CONSTRUCTORS
	// ............................................................
	public App(String dbURL, Level logLevel) {

		// set the database file
		this.dbURL = dbURL;
		// associate logging with this class so know the messages that came from objects
		// of this class
		LOG = LogManager.getLogger(App.class);
		Configurator.setLevel(LOG.getName(), logLevel);

		// Check the log level requested
		LOG.info("Commandline requested log level:" + logLevel);
		LOG.info("Application started with log level debug:" + LOG.isDebugEnabled());

		// test the logging - uncomment if needed
		// testLogOutput();
		this.someInput = new Scanner(System.in);

		// set the database file to use
		DataManagerSQLite.getInstance().setDataFile(this.dbURL);
		MenuBuilder theMenu = new MenuBuilder();

		// theMenu.print();
		// LOG.debug(theMenu.display());
		theMenu.getMenu().display();

		// pause before exit (this is only useful if an error occurs)
		System.out.println(" \n Press enter to exit the program");
		this.someInput.nextLine();

		// close the program without error
		System.exit(0);
	}

	public App(String dbFile) {
		this(dbFile, Level.INFO);
	}

	// METHODS used by main() or debug methods - note they are static methods
	// ............................................................
	public String getDatabaseName() {
		return this.dbURL;
	}

	/**
	 * action the arguments presented at the command line instantiate the App class
	 * based on the arguments passed
	 *
	 * Note: you cannot use LOG.info etc. here as it does not get instantiated until
	 * App() is called if you try you will get a NullPointerExecption
	 */

	private static void actionCommandlineInput(String args[]) {

		String filename = null;
		String dbType = null;
		String dbURL = null;

		try {
			final OptionParser optionParser = new OptionParser();

			// define the allowed arguments
			optionParser.acceptsAll(Arrays.asList("v", "verbose"),
					"Set logging level to DEBUG to see all levels of log messages").forHelp();

			optionParser.acceptsAll(Arrays.asList("h", "help"), "Display help/usage	information").forHelp();

			optionParser.acceptsAll(Arrays.asList("r", "version"), "Display	program	version	information").forHelp();

			optionParser.acceptsAll(Arrays.asList("d", "database"), "Path and name of database file.").withRequiredArg()
					.ofType(String.class).describedAs("SQlite	database");

			final OptionSet options = optionParser.parse(args);

			if (options.has("help")) {
				System.out.println("This program takes an SQL database with a User table as displays the users.");
				System.out.println("It is provided as an example for teaching Java programming.");
				printUsage(optionParser);
				System.exit(0);
			}
			if (options.has("version")) {
				System.out.println("Fodla version : " + VERSION);
				System.exit(0);
			}
			// Check that a database file has been provided as now it is required
			if (!options.has("database")) {
				System.out.println("Option	\"-d database\"	is required");
				System.out.println("expecting the filename to be specified as follows: jdbc:sqlite:filepath\filename");
				System.exit(0);
			} else {
				// if two : not there throws IndexOutOfBoundsException
				// to check if the file exists we have to remove jdbc:sqlite:
				// to check what type of database is to be used extract sqlite or mysql
				dbURL = (String) options.valueOf("database");
				// System.out.println("dbURL: " + dbURL);
				filename = dbURL.substring(dbURL.lastIndexOf(':') + 1);
				// System.out.println("filename: " + filename);
				dbType = dbURL.substring(dbURL.indexOf(':') + 1, dbURL.lastIndexOf(':'));
				// System.out.println("dbType: " + dbType);
			}
			if (dbType.equals("sqlite")) {
				if (!new File(filename).isFile()) {
					System.out.println("ERROR: Database file does not exist : " + (String) options.valueOf("database"));
					System.out.println(
							"If	the	file is	in	the	same directory as the JAR then the location would be: databaseFileName.Extension");
					System.out.println(
							"for windows the database file location would be: C://folder/folder/databaseFileName.Extension");
					System.out.println(
							"for MAC the database file location would be: /Volumes/VolumeName/folder/folder/databaseFileName.Extension");
					System.exit(0);
				}
			} else if (dbType.equals("mysql")) {
				// to support mysql we will need to add -u and -p parameters
				// to get the username and password
				System.out.println("Support	for	mySQL is coming	soon. Please use an	SQLite database");
				System.exit(0);
			} else {
				System.out.println("Unsupported database type requested " + dbType);
				System.exit(0);
			}
			// valid input so start the program with the name of the database to use
			if (options.has("database") && options.has("verbose")) {
				Level logLevel = Level.DEBUG;
				System.out.println("RUN	WITH:	Database: " + dbURL + "	logging level requested: " + logLevel);
				App anApp = new App(dbURL, logLevel);
			} else {
				System.out.println("RUN	WITH: Database:	" + dbURL + " logging as per main/resources/Log4J2.xml");
				App anApp = new App(dbURL);
			}
		} catch (OptionException argsEx) {
			System.out.println("ERROR: Arguments\\parameter is not valid. " + argsEx);
			System.exit(0);
		} catch (IndexOutOfBoundsException iobEx) {
			System.out.println("ERROR: invalid database name format provided " + iobEx);
			System.out.println("expecting the filename to be specified as follows > jdbc:sqlite:filepath\filename");
			System.exit(0);
		}
	}// EOM

	/**
	 * Write help message to standard output using the provided instance of
	 * {@code	OptionParser}.
	 */
	private static void printUsage(final OptionParser parser) {
		try {
			parser.printHelpOn(System.out);
		} catch (IOException ioEx) {
			System.out.println("ERROR: Unable to print usage - " + ioEx);
		}
	}// EOM

	/**
	 * View the arguments presented at the commandline This is for debug and demo
	 * purposes
	 */
	private static void seeCommandlineInput(String args[]) {
		if (args.length == 0) {
			System.out.println("There were no commandline arguments passed!");
		} else {
			// display the command line entered
			for (int i = 0; i < args.length; i++) {
				System.out.println(args[i]);
			}
		}
	}// EOM

	/**
	 * Test the Log4J2 logging
	 */
	private static void testLogOutput() {
		LOG.debug("Log test: Test printed on debug");
		LOG.info("Log test: Test printed on info");
		LOG.warn("Log test: Test printed on warn");
		LOG.error("Log test: Test printed on error");
		LOG.fatal("Log test: Test printed on fatal");
		LOG.info("Appending string: {}.", "Application log test message - Hi");
	}// EOM
}// EOC