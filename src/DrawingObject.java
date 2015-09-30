
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

/**
 * This abstract class defines the four functions that all of the DrawingObject
 * derived classes must have.
 *
 * The four abstract functions defined in this class must be implemented in the
 * classes that extend this one.
 */
public abstract class DrawingObject {

	/*****************************************************
	 *
	 * These are the four abstract functions that the subclasses that extend
	 * this class must implement
	 *
	 *****************************************************/

	/**
	 * This function changes the coordinates that define the location and size
	 * of the object.
	 * 
	 * @param x1
	 *            x point for initial coordinate
	 * @param y1
	 *            y point for initial coordinate
	 * @param x2
	 *            x point for final coordinate
	 * @param y2
	 *            y point for final coordinate
	 */

	public abstract void resizeObject(int x1, int y1, int x2, int y2);

	/**
	 * This function returns a boolean that indicates whether the provided
	 * coordinate (px,py) hits any part of the object.
	 * 
	 * @param px
	 *            x coordinate of provided point
	 * @param py
	 *            y coordinate of provided point
	 * @return true if provided coordinate hits any part of the object else
	 *         false
	 */

	public abstract boolean wasClicked(int px, int py);

	/**
	 * This function returns a Rectangle object that describes the 'bounding
	 * box'. The bounding box is the smallest rectange that contains the object.
	 * 
	 * @return a rectangle object as a bounding box
	 */
	public abstract Rectangle getBoundingBox();

	/**
	 * This function is called to request that the object draw itself using the
	 * provided Graphics2D context.
	 * 
	 * @param g2
	 *            A graphics2D object
	 */
	public abstract void drawObject(Graphics2D g2);

	/*****************************************************
	 *
	 * The following code is all about checking whether a point or line has been
	 * clicked
	 *
	 *****************************************************/

	/*
	 * How close (in pixels) does one have to click to a line or point in order
	 * to select it
	 */
	static private final int SELECTION_DISTANCE = 5;

	/**
	 * This utility function makes it easy to check whether the coordinates of
	 * the user's mouse click (px,py) are on (or very close to) the line between
	 * the two points, (x1,y1) and (x2,y2).
	 * 
	 * @param px
	 *            x coordinate of mouse click
	 * @param py
	 *            y coordinate of mouse click
	 * @param x1
	 *            x point of the first coordinate of object to check for mouse
	 *            click proximity
	 * @param y1
	 *            y point of the first coordinate of object to check for mouse
	 *            click proximity
	 * @param x2
	 *            x point of the second coordinate of object to check for mouse
	 *            click proximity
	 * @param y2
	 *            y point of the second coordinate of object to check for mouse
	 *            click proximity
	 * @return true if the point where the user clicked is on or close to the
	 *         line between the two points,(x1,y1) and (x2,y2)
	 */

	public static boolean clickHitLine(int px, int py, int x1, int y1, int x2,
			int y2) {
		return Line2D.ptSegDist(x1, y1, x2, y2, px, py) < SELECTION_DISTANCE;
	}

	/**
	 * This utility function makes it easy to check whether the coordinates of
	 * the user's mouse click (px,py) are on (or very close to) the ellipse
	 * defined by the bounding box with corners at (x1,y1) and (x2,y2).
	 * 
	 * @param px
	 *            x coordinate of mouse click
	 * @param py
	 *            y coordinate of mouse click
	 * @param x1
	 *            x point of the first coordinate of object to check for mouse
	 *            click proximity
	 * @param y1
	 *            y point of the first coordinate of object to check for mouse
	 *            click proximity
	 * @param x2
	 *            x point of the second coordinate of object to check for mouse
	 *            click proximity
	 * @param y2
	 *            y point of the second coordinate of object to check for mouse
	 *            click proximity
	 * @return true if the coordinates of the user's mouse click (px,py) are on
	 *         close to the ellipse defined by the bounding box with corners at
	 *         (x1,y1) and (x2,y2).
	 */

	public static boolean clickHitEllipse(int px, int py, int x1, int y1,
			int x2, int y2) {
		int minX = (x1 < x2 ? x1 : x2);
		int minY = (y1 < y2 ? y1 : y2);
		int maxX = (x1 > x2 ? x1 : x2);
		int maxY = (y1 > y2 ? y1 : y2);

		Ellipse2D.Float innerEllipse = new Ellipse2D.Float(minX
				+ SELECTION_DISTANCE, minY + SELECTION_DISTANCE, maxX - minX
				- 2 * SELECTION_DISTANCE, maxY - minY - 2 * SELECTION_DISTANCE);

		Ellipse2D.Float outerEllipse = new Ellipse2D.Float(minX
				- SELECTION_DISTANCE, minY - SELECTION_DISTANCE, maxX - minX
				+ 2 * SELECTION_DISTANCE, maxY - minY + 2 * SELECTION_DISTANCE);

		return outerEllipse.contains(px, py) && !innerEllipse.contains(px, py);

	}

}// end class

