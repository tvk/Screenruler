package de.thomasvoecking.screenruler.ui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import de.thomasvoecking.screenruler.ui.util.MouseDraggingAdapter;


/**
 * @author thomas
 *
 */
class ResizeControl extends JPanel 
{
	
	/**
	 * SUID
	 */
	private static final long serialVersionUID = 9084158060116756317L;	

	/**
	 * Enumeration that represents the directions of a resize control 
	 * 
	 * @author thomas
	 */
	enum Direction 
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
		 * @param arrowImagePath Path to ann arrow image for this direction
		 * inside the classpath.
		 */
		private Direction(final String arrowImagePath) 
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
	}
	
	/**
	 * The mouse adapter handles mouse events and the resizing and relocating of
	 * the parent component.
	 */
	@SuppressWarnings("unused")
	private final MouseDraggingAdapter mouseDraggingAdapter = new MouseDraggingAdapter(ResizeControl.this) 
	{
		@Override
		public void handleDrag(final int dx, final int dy) 
		{
			switch (ResizeControl.this.direction)
			{
				case LEFT :
					ResizeControl.this.parent.setLocation(
							ResizeControl.this.parent.getX() + dx, 
							ResizeControl.this.parent.getY());
					ResizeControl.this.parent.setSize(
							ResizeControl.this.parent.getWidth() - dx, 
							ResizeControl.this.parent.getHeight());
					break;
				case RIGHT :
					ResizeControl.this.parent.setSize(
							ResizeControl.this.parent.getWidth() + dx, 
							ResizeControl.this.parent.getHeight());
					break;
			}
		}
	};
	
	/**
	 * The direction of this resize button
	 */
	final Direction direction;
	
	/**
	 * The parent component to resize.
	 */
	final Component parent;
	
	/**
	 * Constructor
	 * 
	 * @param parent The parent comonent to resize. 
	 * @param direction The {@link Direction} of this component.
	 */
	ResizeControl(final Component parent, final Direction direction) 
	{
		this.direction = direction;
		this.parent = parent;
	}
	
	/**
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(final Graphics g) 
	{		
		g.drawImage(this.direction.arrowImage, 
				0, 0, this.getWidth(), this.getHeight(), 
				0, 0, this.direction.arrowImage.getWidth(), this.direction.arrowImage.getHeight(), 
				null);
	}	
}

