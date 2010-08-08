package de.thomasvoecking.screenruler;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import de.thomasvoecking.screenruler.ui.ScreenrulerFrame;

/**
 * Main entry point for this application
 *
 * @author thomas
 */
public class App 
{
	/**
	 * The logger
	 */
	private static final Log log = LogFactory.getLog(App.class);
	
	/**
	 * Starts the application
	 * 
	 * @param args application arguments
	 */
    public static void main(final String[] args)
    {
    	log.info("Starting screenruler");
    	
    	log.debug("Parsing command line arguments");
    	final CommandLine options = parseOptions(args);
    	
        final ScreenrulerFrame frame = new ScreenrulerFrame();
        if (options.hasOption("loglevel"))
        {
        	final Level level = Level.toLevel(options.getOptionValue("loglevel"));
        	log.info("Setting loglevel to " + level);
        	Logger.getRootLogger().setLevel(level);
        }
        
        frame.setVisible(true);
        
        
    }

    /**
     * Parses the command line options.
     * 
     * @param args The program arguments
     * @return The options
     */
	private static CommandLine parseOptions(final String[] args) 
	{
		// TODO finish this!
		
		final Options options = new Options();
    	
    	options.addOption("size", true, "The size of the panel at startup");
    	options.addOption("opacity", true, "The opacity of the panel at startup");
    	options.addOption("loglevel", true, "The log4j level to use.");
    	options.addOption("help", false, "Print this help and exit.");
    	
    	final HelpFormatter formatter = new HelpFormatter(); 
    	
    	try 
    	{
			final CommandLine commandLine = new PosixParser().parse(options, args);
			if (commandLine.hasOption("help"))
	    	{
	    		formatter.printHelp("screenruler", options);
	    		System.exit(0);
			}
			return commandLine;
				
		} 
    	catch (final ParseException e) 
    	{
    		formatter.printHelp("screenruler", options);
    		System.exit(0);
		}
    	return null;
	}
}
