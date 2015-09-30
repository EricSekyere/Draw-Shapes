
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * @author Eric Sekyere
 * A DrawingX is an extension of the DrawingObject abstract
 * class. It is used to store the details pertaining to an X-shaped
 * object for the drawing program.
 */

public class DrawingX extends DrawingObject {

	// -----------
	// attributes
	// -----------
	// height is the difference between x2 and x1
	// width is the difference between y2 and y1

	protected int x1, y1, width, height;

	/**
	 * This constructs a new DrawingX object. The X is defined by its two
	 * opposite corners, (x1, y1) and (x2, y2).
	 * 
	 * @param x1
	 *            x coordinate for the first corner
	 * @param y1
	 *            y coordinate for the first corner
	 * @param x2
	 *            x coordinate for the first corner
	 * @param y2
	 *            y coordinate for the first corner
	 */

	// ------------
	// constructor
	// ------------

	public DrawingX(int x1, int y1, int x2, int y2) {
		resizeObject(x1, y1, x2, y2);
	}

	/**
	 * This resizes a DrawingX object. The two corners, (x1, y1) and (x2, y2),
	 * that define the X are updated.
	 */
	// ---------------
	// methods
	// ---------------
	@Override
	public void resizeObject(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.width = x2 - x1;
		this.height = y2 - y1;
	}

	/**
	 * This function determines whether the given point (px, py), where the user
	 * has clicked, hits any part of this X.
	 * 
	 * @param px
	 *            the x point on one of the two lines
	 * @param py
	 *            the x point on one of the two lines
	 */

	// Handle each of the the two lines in the X object to enable drag

	@Override
	public boolean wasClicked(int px, int py) {
		return clickHitLine(px, py, x1, y1, x1 + width, y1 + height)
				|| clickHitLine(px, py, x1 + width, y1, x1, y1 + height);
	}

	/**
	 * This function returns a Bounding Box. A bounding box is the smallest
	 * rectangle that contains the X.
	 * 
	 * @return a rectangular bonding box
	 */

	// a box around the bounding the X object(perfect fit)

	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(x1, y1, width, height);
	}

	/**
	 * This function draws the X using the given Graphics2D context.
	 *
	 * @param g2
	 *            a Graphics2D object use to draw the X shape
	 */

	// draw Draw two lines in the shape of an X

	@Override
	public void drawObject(Graphics2D g2) {
		g2.drawLine(x1 + width, y1, x1, y1 + height);
		g2.drawLine(x1, y1, x1 + width, y1 + height);
	}

}
