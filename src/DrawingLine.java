import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * 
 * @author cse13105
 *A DrawingLine is an extension of the DrawingObject abstract class. 
 *It is used to store the details pertaining to a line for the drawing program.
 */

public class DrawingLine extends DrawingObject {
	
	//----------
	//attributes
	//----------
	//height is the diffrence between x2 and x1
	//width is the diffrence between y2 and y1
	
	private int x1, y1, width, height;
	
	/**
	 * This constructs a new DrawingLine object. 
	 * The line is defined by its two endpoints, (x1, y1) and (x2, y2).
	 */
	
	//-----------
	//constructor
	//-----------
	public DrawingLine(int x1, int y1, int x2, int y2) {
		resizeObject(x1, y1, x2, y2);
	}
	
	/**
	 * This resizes a DrawingLine object. 
	 * The two endpoints, (x1, y1) and (x2, y2), that define the line are updated.
	 */
	
	//---------
	//methods
	//---------
	@Override
	public void resizeObject(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.width = x2 - x1;
		this.height = y2 - y1;
	}
	
	/**
	 * This function determines whether the given point (px, py),
	 * where the user has clicked, hits this line.
	 */
	
	// handle  the line object to enable drag
	
	@Override
	public boolean wasClicked(int px, int py) {
		return clickHitLine(px, py, x1, y1, x1 + width, y1 + height);
				
	}
	
	/**
	 * This function returns a Bounding Box. 
	 * A bounding box is the smallest rectangle that contains the line.
	 */
	
	// a box around the line(a perfect fit)
	
	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(x1, y1, width, height);
	}
	
	/**
	 * This function draws the line using the given Graphics2D context.
	 */
	
	@Override
	public void drawObject(Graphics2D g2) {
		g2.drawLine(x1, y1, x1 + width, y1 + height);
	}

}
