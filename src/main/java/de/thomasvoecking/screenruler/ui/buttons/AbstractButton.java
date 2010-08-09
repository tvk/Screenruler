package de.thomasvoecking.screenruler.ui.buttons;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Abstract button implementation
 * 
 * @author thomas
 *
 */
public abstract class AbstractButton 
{

	/**
	 * The image that represents this button
	 */
	private final BufferedImage image;	
	
	/**
	 * The image that represents this button in mouseover state
	 */
	private final BufferedImage imageMouseover;	
	
	/**
	 * Indicates that this button is in mouseover state
	 */
	private boolean mouseOver = false;
	
	/**
	 * Constructor
	 * 
	 * @param imagePath Path to the icon that represents this image.
	 * @param imagePathMouseover Path to the icon that represents this image in mouseover state.
	 */
	public AbstractButton(final String imagePath, final String imagePathMouseover) 
	{
		this.image = this.loadImage(imagePath);
		this.imageMouseover = this.loadImage(imagePathMouseover);
	}
	
	/**
	 * Loads an image
	 * 
	 * @param path Path to the image
	 * @return The image
	 */
	private BufferedImage loadImage(final String path)
	{
		try 
		{
			return ImageIO.read(ClassLoader.getSystemResource(path));
		} 
		catch (final IOException e) 
		{
			throw new RuntimeException(
					"Resource \"" + path + "\" could not be read.", e);
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
		graphics.drawImage(this.mouseOver ? this.imageMouseover : this.image,  
				(int) bounds.getX(), (int) bounds.getY(), 
				(int) (bounds.getX() + bounds.getWidth()), 
				(int) (bounds.getY() + bounds.getHeight()), 
				0, 0, this.image.getWidth(null), this.image.getHeight(null), null);
	}
	
	/**
	 * Set's a new mouseover state
	 * 
	 * @param mouseOver The new mouseover state
	 * @return true if the value has changed, false otherwise.
	 */
	public boolean setMouseOver(final boolean mouseOver)
	{
		final boolean stateChanged = this.mouseOver != mouseOver;
		this.mouseOver = mouseOver;
		return stateChanged;
	}
	
}
