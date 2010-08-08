package de.thomasvoecking.screenruler.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.awt.AWTUtilities;

/**
 * The main frame. All components are directly painted on this component.
 * Also controls all dragging events.
 * 
 * @author thomas
 */
public class ScreenrulerFrame extends JFrame implements MouseListener, MouseMotionListener
{

	/**
	 * SUID
	 */
	private static final long serialVersionUID = 2158183440540339826L;

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
	
	/**
	 * The logger
	 */
	private static final Log log = LogFactory.getLog(ScreenrulerFrame.class);

	/**
	 * The opacity of this window.
	 */
	private static final float opacity = 0.7f;
	
	/**
	 * The size of this frame at startup.
	 */
	private static final Dimension size = new Dimension(600, 60); 
	
	/**
	 * Width of the resize controls
	 */
	private static final int resizeControlWidth = 10;
	
	/**
	 * The overlay color
	 */
	private static final Color overlayColor = new Color(255, 245, 173);
	
	/**
	 * The left resize control
	 */
	private final ScreenrulerResizeControl leftResizeControl = ScreenrulerResizeControl.LEFT;
	
	/**
	 * The right resize control
	 */
	private final ScreenrulerResizeControl rightResizeControl = ScreenrulerResizeControl.RIGHT;
	
	/**
	 * The current {@link DraggingMode}
	 */
	private DraggingMode draggingMode = null;
	
	/**
	 * The current bottom right border of this component. Is set when dragging is started.
	 */
	private Point bottomRight = null;

	/**
	 * The current mouse position relative to the top left corner of the component. Is set when dragging is started.
	 */
	private Point componentRelativeMouseLocationFromLeft = null;
	
	/**
	 * The current mouse position relative to the bottom right corner of the component. Is set when dragging is started.
	 */
	private Point componentRelativeMouseLocationFromRight = null;
	
	/**
	 * Constructor
	 */
	public ScreenrulerFrame() 
	{
		log.debug("Initializing frame.");
		
		// Set window properties
		this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		log.debug("Setting size: " + size);
		this.setSize(size);

		log.debug("Setting opacity: " + opacity);
		AWTUtilities.setWindowOpacity(this, opacity);
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	/**
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(final Graphics g) 
	{
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		
		// When the ruler is currently in resizing state, draw no ruler and no resize controls.
		// Instead draw an overlay.
		final boolean paintOverlay = 
			this.draggingMode == DraggingMode.RESIZE_LEFT || this.draggingMode == DraggingMode.RESIZE_RIGHT; 
		
		if (paintOverlay)
		{
			this.paintOverlay(g);
		}
		else
		{
			this.paintArrows(g);
		}
	}
	
	/**
	 * Paints the overlay.
	 * 
	 * @param g The {@link Graphics} Object.
	 */
	private void paintOverlay(final Graphics g)
	{
		final Color color = g.getColor();
		g.setColor(overlayColor);
		g.fillRect(0, 0, this.getWidth() + 100, this.getHeight());
		g.setColor(color);
	}
	
	/**
	 * Paints the resize controls.
	 * 
	 * @param g The {@link Graphics} Object.
	 */
	private void paintArrows(final Graphics g)
	{
		this.leftResizeControl.paint(g, this.getLeftResizeControlBoundingBox());
		this.rightResizeControl.paint(g, this.getRightResizeControlBoundingBox());
	}
	
	
	/**
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(final MouseEvent e) 
	{
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			this.componentRelativeMouseLocationFromLeft = e.getPoint();
			this.componentRelativeMouseLocationFromRight = new Point(
					(int) (this.getWidth() - e.getPoint().getX()), 
					(int) (this.getHeight() - e.getPoint().getY()));
			this.bottomRight = new Point(
					(int) (this.getLocationOnScreen().getX() + this.getWidth()),
					(int) (this.getLocationOnScreen().getY() + this.getHeight()));
			
			if (this.getLeftResizeControlBoundingBox().contains(e.getPoint()))
				this.draggingMode = DraggingMode.RESIZE_LEFT;
			else if (this.getRightResizeControlBoundingBox().contains(e.getPoint()))
				this.draggingMode = DraggingMode.RESIZE_RIGHT;
			else
				this.draggingMode = DraggingMode.MOVE;
			
			log.debug("New dragging mode: " + this.draggingMode);
		}
	}

	/**
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(final MouseEvent e) 
	{
		if (this.draggingMode == DraggingMode.RESIZE_LEFT)
		{
			this.setBounds(
					(int) (e.getLocationOnScreen().getX() - this.componentRelativeMouseLocationFromLeft.getX()), 
					(int) (this.getLocationOnScreen().getY()), 
					(int) (this.bottomRight.getX() - e.getLocationOnScreen().getX() - this.componentRelativeMouseLocationFromLeft.getX()), 
					this.getHeight());
		}
		else if (this.draggingMode == DraggingMode.RESIZE_RIGHT)
		{
			this.setSize(
					(int) (e.getLocationOnScreen().getX() - this.getLocationOnScreen().getX() + this.componentRelativeMouseLocationFromRight.getX()), 
					this.getHeight());
		}
		else if (this.draggingMode == DraggingMode.MOVE)
		{
			this.setLocation(
					(int) (e.getLocationOnScreen().getX() - this.componentRelativeMouseLocationFromLeft.getX()),
					(int) (e.getLocationOnScreen().getY() - this.componentRelativeMouseLocationFromLeft.getY()));
		}
	}	

	/**
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(final MouseEvent e) 
	{
		log.debug("Releasing dragging mode: " + this.draggingMode);
		
		this.draggingMode = null;
		this.repaint();
	}

	/**
	 * @return the bounding box for the left resize control.
	 */
	private Rectangle getLeftResizeControlBoundingBox()
	{
		return new Rectangle(0, 0, resizeControlWidth, this.getHeight());
	}

	/**
	 * @return the bounding box for the right resize control.
	 */
	private Rectangle getRightResizeControlBoundingBox()
	{
		return new Rectangle(this.getWidth() - resizeControlWidth, 0, resizeControlWidth, this.getHeight());
	}



	/**
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(final MouseEvent e) { /* unused */ }

	/**
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(final MouseEvent e) { /* unused */ }

	/**
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(final MouseEvent e) { /* unused */ }

	/**
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(final MouseEvent e) { /* unused */ }

}
