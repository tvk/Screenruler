package de.thomasvoecking.screenruler.ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * ResizeControl enum.
 * 
 * @author thomas
 */
public enum ScreenrulerResizeControl 
{
	/** Pointing to the left side */
	LEFT ("arrow-left.png"),
	
	/** Pointing to the right side */
	RIGHT ("arrow-right.png");
	
	/**
	 * An arrow image for this direction. 
	 */
	final BufferedImage arrowImage;	

	/**
	 * Constructor
	 * 
	 * @param arrowImagePath The path to the image that represents this control. 
	 */
	private ScreenrulerResizeControl(final String arrowImagePath) 
	{
		try 
		{
			this.arrowImage = ImageIO.read(ClassLoader.getSystemResource(arrowImagePath));
		} 
		catch (final IOException e) 
		{
			throw new RuntimeException(
					"Resource \"" + arrowImagePath + "\" could not be read.", e);
		}
	}
	
	/**
	 * Paints this control.
	 * 
	 * @param graphics The graphics object.
	 * @param bounds The area where to paint this control to.
	 */
	public void paint(final Graphics graphics, final Rectangle bounds)
	{
		graphics.drawImage(this.arrowImage,  
				(int) bounds.getX(), (int) bounds.getY(), 
				(int) (bounds.getX() + bounds.getWidth()), 
				(int) (bounds.getY() + bounds.getHeight()), 
				0, 0, this.arrowImage.getWidth(null), this.arrowImage.getHeight(null), null);
	}
	
}
