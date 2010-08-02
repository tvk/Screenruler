package de.thomasvoecking.screenruler.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.awt.AWTUtilities;

import de.thomasvoecking.screenruler.ui.util.RelocateMouseDraggingAdapter;

/**
 * The main frame of this application
 * 
 * @author thomas
 */
public class ScreenrulerFrame extends JFrame  
{
	
	/**
	 * SUID
	 */
	private static final long serialVersionUID = 7488531750060359725L;

	/**
	 * The opacity of this window.
	 */
	private static final float opacity = 0.7f;
	
	/**
	 * The size of this frame at startup.
	 */
	private static final Dimension size = new Dimension(600, 60); 
	
	/**
	 * The width of the resize controls on each side.
	 */
	private static final int resizeControlWidth = 10;
	

	/**
	 * Constructor
	 */
	public ScreenrulerFrame() 
	{
		// Set window properties
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(size);
        AWTUtilities.setWindowOpacity(this, opacity);
        
        // Attach listeners
        @SuppressWarnings("unused")
		final RelocateMouseDraggingAdapter relocateDraggingHelper = 
        	new RelocateMouseDraggingAdapter(this);
        
        // Initialize components
        final JPanel mainPanel = new JPanel();

        final ResizeControl leftResizeControl = new ResizeControl(this, ResizeControl.Direction.LEFT);
        final ResizeControl rightResizeControl = new ResizeControl(this, ResizeControl.Direction.RIGHT);
        
        final JPanel ruler = new JPanel();
        ruler.setBackground(Color.green);
        
        // Initialize layout
        final GroupLayout leftResizeControlLayout = new GroupLayout(leftResizeControl);
        leftResizeControl.setLayout(leftResizeControlLayout);
        leftResizeControlLayout.setHorizontalGroup(
            leftResizeControlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, resizeControlWidth, Short.MAX_VALUE)
        );
        leftResizeControlLayout.setVerticalGroup(
            leftResizeControlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, (int) size.getHeight(), Short.MAX_VALUE)
        );

        final GroupLayout rulerLayout = new GroupLayout(ruler);
        ruler.setLayout(rulerLayout);
        rulerLayout.setHorizontalGroup(
            rulerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, (int) (size.getWidth() - 2 * resizeControlWidth), Short.MAX_VALUE)
        );
        rulerLayout.setVerticalGroup(
            rulerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, (int) size.getHeight(), Short.MAX_VALUE)
        );

        final GroupLayout rightResizeControlLayout = new GroupLayout(rightResizeControl);
        rightResizeControl.setLayout(rightResizeControlLayout);
        rightResizeControlLayout.setHorizontalGroup(
            rightResizeControlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, resizeControlWidth, Short.MAX_VALUE)
        );
        rightResizeControlLayout.setVerticalGroup(
            rightResizeControlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, (int) size.getHeight(), Short.MAX_VALUE)
        );
        
        final GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addComponent(leftResizeControl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(ruler, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rightResizeControl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(ruler, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(leftResizeControl, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rightResizeControl, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        
        this.add(mainPanel);

	}	
}

