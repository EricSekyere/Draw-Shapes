
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * 
 * @author Eric Sekyere 
 * A DrawingEllipse is an extension of the DrawingObject
 * abstract class. It is used to store the details pertaining to an
 * ellipse for the drawing program.
 */

public class DrawingEllipse extends DrawingObject {

	// ---------------
	// attributes
	// ---------------
	// height is the diffrence between x2 and x1
	// width is the diffrence between y2 and y1
	private int x1, y1, width, height;

	/**
	 * This constructs a new DrawingEllipse object. The ellipse is defined by
	 * its bounding box coordinates, (x1, y1) and (x2, y2).
	 * 
	 * @param x1
	 *            x point for the first coordinate of the bounding box
	 * @param y1
	 *            y point for the first coordinate of the bounding box
	 * @param x2
	 *            x point for the second coordinate of the bounding box
	 * @param y2
	 *            y point for the second coordinate of the bounding box
	 */

	// ----------------
	// constructor
	// ----------------

	public DrawingEllipse(int x1, int y1, int x2, int y2) {
		resizeObject(x1, y1, x2, y2);
	}

	/**
	 * This resizes a DrawingEllipse object. The two bounding box coordinates,
	 * (x1, y1) and (x2, y2), that define the ellipse are updated.
	 */
	// --------------
	// methods
	// --------------

	@Override
	public void resizeObject(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.width = x2 - x1;
		this.height = y2 - y1;
	}

	/**
	 * This function determines whether the given point (px, py), where the user
	 * has clicked, hits the edge of this ellipse.
	 */

	// handle the ellipse to enable click and drag

	@Override
	public boolean wasClicked(int px, int py) {
		return clickHitEllipse(px, py, x1, y1, x1 + width, y1 + height);
	}

	/**
	 * This function returns a Bounding Box. A bounding box is the smallest
	 * rectangle that contains the ellipse.
	 */

	// a box around the ellipse object(a perfect fit)

	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(x1, y1, width, height);
	}

	/**
	 * This function draws the ellipse using the given Graphics2D context.
	 */

	@Override
	public void drawObject(Graphics2D g2) {
		g2.drawOval(x1, y1, width, height);
	}
}
