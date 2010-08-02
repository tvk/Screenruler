package de.thomasvoecking.screenruler;

import de.thomasvoecking.screenruler.ui.ScreenrulerFrame;

/**
 * Main entry point for this application
 *
 * @author thomas
 */
public class App 
{
	/**
	 * Starts the application
	 * 
	 * @param args application arguments
	 */
    public static void main(final String[] args)
    {
        final ScreenrulerFrame frame = new ScreenrulerFrame();
        frame.setVisible(true);
    }
}
