package de.thomasvoecking.screenruler.ui;

import java.awt.Point;

/**
 * Contains stateful data that is necessary for the dragging behaviour.
 * 
 * @author thomas
 *
 */
class ScreenrulerDraggingData {

	/**
	 * The current {@link DraggingMode}
	 */
	DraggingMode draggingMode;
	
	/**
	 * The current bottom right border of this component. Is set when dragging is started.
	 */
	Point bottomRight;
	
	/**
	 * The current mouse position relative to the top left corner of the component. Is set when dragging is started.
	 */
	Point componentRelativeMouseLocationFromLeft;
	
	/**
	 * The current mouse position relative to the bottom right corner of the component. Is set when dragging is started.
	 */
	Point componentRelativeMouseLocationFromRight;

}

/**
 * Possible dragging modes.
 * 
 * @author thomas
 */
enum DraggingMode
{
	/** Resize mode to the left side. */
	RESIZE_LEFT,
	
	/** Resize mode to the right side. */
	RESIZE_RIGHT,
	
	/** Move mode */
	MOVE
}