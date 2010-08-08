package de.thomasvoecking.screenruler.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * The ruler.
 * 
 * @author thomas
 */
public class Ruler 
{

	/**
	 * The logger
	 */
	private static final Log log = LogFactory.getLog(Ruler.class);

	/**
	 * The length of the (main) stroke in px 
	 */
	private static final int strokeLength = 30;
	
	/**
	 * The minimum width of an inner field before a scale is drawn inside.
	 */
	private static int minimumFieldWidthToDrawInnerScale = 60;
	
	/**
	 * Start value of the ruler
	 */
	private double rulerStartValue;
	
	/**
	 * End value of the ruler
	 */
	private double rulerEndValue;
	
	/**
	 * The name of this unit
	 */
	private String unitName;
	
	
	public Ruler(final String unitName, final double rulerStartValue, final double rulerEndValue) 
	{
		this.rulerStartValue = rulerStartValue;
		this.rulerEndValue = rulerEndValue;
		this.unitName = unitName;
	}

	/**
	 * Paints this ruler.
	 * 
	 * @param graphics The graphics object.
	 * @param bounds The area where to paint this ruler to.
	 */
	public void paint(final Graphics graphics, final Rectangle bounds)
	{

		final int logPrependPadding = 21;
		log.debug("Painting ruler");
		log.debug(StringUtils.rightPad("rulerStartValue", logPrependPadding) + ": " + this.rulerStartValue);
		log.debug(StringUtils.rightPad("rulerEndValue", logPrependPadding) + ": " + this.rulerEndValue);
		log.debug(StringUtils.rightPad("bounds", logPrependPadding) + ": " + bounds);

		// The width of the ruler in the current unit
		final double rulerValueWidth = this.rulerEndValue - this.rulerStartValue;
		log.debug(StringUtils.rightPad("rulerValueWidth", logPrependPadding) + ": " + rulerValueWidth + " " + this.unitName);
		
		// The highest unit we can display
		final double rulerBaseValue = this.guessRulerBaseValue();
		log.debug(StringUtils.rightPad("rulerBaseValue", logPrependPadding) + ": " + rulerBaseValue + " " + this.unitName);		
		
		// The left offset for the highest unit
		final double rulerBaseValueOffset = (rulerBaseValue - this.rulerStartValue) % rulerBaseValue;
		log.debug(StringUtils.rightPad("rulerBaseValueOffset", logPrependPadding) + ": " + rulerBaseValueOffset + " " + this.unitName);
		
		// The number of pixels per 1.0 units
		final double resolution = bounds.getWidth() / rulerValueWidth; 
		log.debug(StringUtils.rightPad("resolution", logPrependPadding) + ": " + resolution + "px/" + this.unitName + " (" + (resolution * rulerBaseValue) + "/" + rulerBaseValue + "cm)");

		// The number of strokes to display.
		final int numberOfStrokes = (int) (rulerValueWidth / rulerBaseValue);
		log.debug(StringUtils.rightPad("numberOfStrokes", logPrependPadding) + ": " + numberOfStrokes);

		// Save the last position where text has been drawn to 
		int textUntil = 0;

		// Draw a stroke at the beginning of the ruler
		graphics.drawLine((int) (bounds.getX()), 0, (int) (bounds.getX()), strokeLength);
		textUntil = this.drawUnitString(graphics, this.rulerStartValue, (int) (bounds.getX()), textUntil);

		int lastX = 0;
		// Iterate over all major strokes, iterate over one extra step in both directions to ensure that 
		// he minor scale is drawn.
		for (int i = 0; i <= numberOfStrokes + 1; i++)
		{
			final double value = this.rulerStartValue + rulerBaseValueOffset + i * rulerBaseValue;
			final int x = (int) (bounds.getX() + ((rulerBaseValueOffset + i * rulerBaseValue) * resolution));			
			
			log.debug(StringUtils.rightPad("x (" + value + " " + this.unitName + ")", logPrependPadding) + ": " + x + " px" + (i > 0 ? ", Distance to last x : " + (x - lastX) + " px" : ""));
			
			// Draw this step only if it is inside the range of this ruler.
			if (value >= this.rulerStartValue && value <= this.rulerEndValue)
			{
				graphics.drawLine(x, 0, x, strokeLength);			
				textUntil = this.drawUnitString(graphics, value, x, textUntil);
			}

			lastX = x;
		}
		
		// Draw a stroke at the end of the ruler
		if (this.rulerEndValue % rulerBaseValue != 0)
		{
			final int x = (int) (bounds.getMaxX());
			graphics.drawLine(x, 0, x, strokeLength);
			textUntil = this.drawUnitString(graphics, this.rulerEndValue, x, textUntil);
		}
	}

	/**
	 * Draws a unit string ("13.0 cm")
	 *  
	 * @param graphics
	 * @param value The value to draw
	 * @param x The position where to draw
	 * @param currentTextUntil The current text position.
	 * 
	 * @return The new textUntil 
	 */
	private int drawUnitString(final Graphics graphics, final double value, 
			final int x, final int currentTextUntil) 
	{
		final String text = value + " " + this.unitName;
		final Font currentFont = graphics.getFont();
		final Font font = new Font(currentFont.getName(), Font.PLAIN, 8);
		graphics.setFont(font);
		final Rectangle2D textBounds = graphics.getFontMetrics().getStringBounds(text, graphics);
		
		if (currentTextUntil < x - textBounds.getCenterX())
		{
			graphics.drawString(text, (int) (x - textBounds.getCenterX()), (int) (strokeLength + 2 + textBounds.getHeight()));
			return (int) (x + textBounds.getCenterX());
		}
		
		return currentTextUntil;
	}
	
	
	/**
	 * Returns the ruler base value.
	 * 
	 * Examples:
	 * 
	 * If the ruler starts at 15 and ends at 100, the base unit is 10.
	 * If the ruler starts at 0.3 and ends at 0.7, the base unit is 0.1.
	 * If the ruler starts at 1 and ends at 2, the base unit is 1.
	 * 
	 * @return The ruler base value.
	 */
	double guessRulerBaseValue()
	{
		return Math.pow(10.0, Math.ceil(Math.log10(this.rulerEndValue - this.rulerStartValue)) - 1.0);
	}
	
	public void setRulerStartValue(double rulerStartValue) {
		this.rulerStartValue = rulerStartValue;
	}
	
	public void setRulerEndValue(double rulerEndValue) {
		this.rulerEndValue = rulerEndValue;
	}
	
}
