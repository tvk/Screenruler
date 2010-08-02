/**
 * 
 */
package de.thomasvoecking.screenruler.ui.util;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The abstract DraggingHelper can be attached to any component 
 * and manages the dragging capabilities. 
 * 
 * Overriding classes have to override the {@link MouseDraggingAdapter#handleDrag(int, int)}-Method
 * and implement the functionality that should happen when the given component is dragged.
 * 
 * @author thomas
 *
 */
public abstract class MouseDraggingAdapter extends MouseAdapter 
{

	/**
	 * Saves the last dragging position
	 */
	private Point lastLocationOnScreen = null;
	
	/**
	 * The component to control
	 */
	protected final Component component;
	
	/**
	 * Constructor
	 * 
	 * @param component The dragging helper will be attached to this component. 
	 */
	protected MouseDraggingAdapter(final Component component) 
	{
		this.component = component;
		this.component.addMouseListener(this);
		this.component.addMouseMotionListener(this);
	}
	
	/**
	 * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public final void mouseDragged(final MouseEvent e) 
	{
		if (this.lastLocationOnScreen != null)
		{
			this.handleDrag(
					(int) (e.getLocationOnScreen().getX() - this.lastLocationOnScreen.getX()), 
					(int) (e.getLocationOnScreen().getY() - this.lastLocationOnScreen.getY()));
		}
		
		this.lastLocationOnScreen = e.getLocationOnScreen();
	}
	
	/**
	 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public final void mousePressed(final MouseEvent e) 
	{
		this.lastLocationOnScreen = e.getLocationOnScreen();
	}
	
	/**
	 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public final void mouseReleased(final MouseEvent e) 
	{
		this.lastLocationOnScreen = null;
	}
	
	/**
	 * Handles the drag event.
	 * 
	 * @param dx The number of pixels the mouse has been dragged to the left side.
	 * @param dy The number of pixels the mouse has been dragged to the bottom side.
	 */
	public abstract void handleDrag(int dx, int dy);
}
