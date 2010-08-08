package de.thomasvoecking.screenruler.ui;

import org.junit.Test;

import junit.framework.Assert;


/**
 * Testcases for {@link Ruler}.
 * 
 * @author thomas
 */
public class RulerTest 
{

	/**
	 * Testcases for {@link Ruler#guessRulerBaseValue()}.
	 */
	@Test
	public void testGuessRulerBaseValue()
	{
		final Ruler ruler = new Ruler("cm", 0.1, 0.7);
		Assert.assertEquals(0.1, ruler.guessRulerBaseValue(), 0.000001);
		
		ruler.setRulerStartValue(0.55);
		Assert.assertEquals(0.1, ruler.guessRulerBaseValue(), 0.000001);
		
		ruler.setRulerStartValue(0.6);
		Assert.assertEquals(0.01, ruler.guessRulerBaseValue(), 0.000001);

		ruler.setRulerEndValue(100);
		Assert.assertEquals(10.0, ruler.guessRulerBaseValue(), 0.000001);

		ruler.setRulerStartValue(90);
		Assert.assertEquals(1.0, ruler.guessRulerBaseValue(), 0.000001);

		ruler.setRulerStartValue(91);
		Assert.assertEquals(1.0, ruler.guessRulerBaseValue(), 0.000001);

		ruler.setRulerStartValue(99);
		Assert.assertEquals(0.1, ruler.guessRulerBaseValue(), 0.000001);

	}
	
}
