package de.thomasvoecking.screenruler.ui.util;

import java.awt.Component;

/**
 * The RelocateDraggingHelper relocates a component when it is dragged
 * 
 * @author thomas
 */
public class RelocateMouseDraggingAdapter extends MouseDraggingAdapter 
{

	/**
	 * Constructor
	 * 
	 * @param component The component to relocate.
	 */
	public RelocateMouseDraggingAdapter(final Component component) 
	{
		super(component);
	}
	
	/**
	 * @see de.thomasvoecking.screenruler.ui.util.MouseDraggingAdapter#handleDrag(int, int)
	 */
	@Override
	public void handleDrag(final int dx, final int dy) 
	{
		this.component.setLocation(
				(int) (this.component.getLocation().getX() + dx), 
				(int) (this.component.getLocation().getY() + dy));
	}

}
