package de.thomasvoecking.screenruler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
    	log.debug("Starting screenruler");
        final ScreenrulerFrame frame = new ScreenrulerFrame();
        frame.setVisible(true);
    }
}
