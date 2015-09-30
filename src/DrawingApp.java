
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/*****************************************************
 *
 *@author Eric Sekyere
 * This program provides the View and Controller portions of the Drawing Object
 * program.
 *
 *****************************************************/

class DrawingApp extends JFrame implements ActionListener, MouseInputListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		// use look and feel for my system (Win32)
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		/*
		 * this is the standard GUI progrm startup code, we instantiate and
		 * configure a JFrame-based object
		 */

		DrawingApp frame = new DrawingApp();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Drawing Object");
		frame.pack();
		frame.setVisible(true);
	}

	/*
	 * GUI components we need to define
	 */

	private ViewPanel vp;
	private JRadioButton line;
	private JRadioButton square;
	private JRadioButton ex;
	private JRadioButton ellipse;
	private JButton clear;
	private JButton delete;
	private JButton exit;

	/*
	 * How close (in pixels) does one have to click to a line or point in order
	 * to select it
	 */
	static private final int SELECTION_DISTANCE = 5;

	/*
	 * the constuctor
	 */

	public DrawingApp() {
		/*
		 * construct and configure components
		 */

		vp = new ViewPanel();
		vp.setBorder(BorderFactory.createLineBorder(Color.gray));

		line = new JRadioButton("Line");
		square = new JRadioButton("Square");
		ex = new JRadioButton("X");
		ellipse = new JRadioButton("Ellipse");

		ButtonGroup bg = new ButtonGroup();
		bg.add(square);
		bg.add(line);
		bg.add(ex);
		bg.add(ellipse);

		line.setSelected(true);

		clear = new JButton("Clear");
		delete = new JButton("Delete");
		exit = new JButton("Exit");

		exit.setMaximumSize(clear.getPreferredSize());

		/*
		 * add listeners
		 */

		vp.addMouseListener(this);
		vp.addMouseMotionListener(this);
		clear.addActionListener(this);
		delete.addActionListener(this);
		exit.addActionListener(this);

		/*
		 * arrange components
		 */

		JPanel leftPanel = new JPanel();

		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.add(new JLabel(
				"Click and drag to create new drawing object..."));
		leftPanel.add(vp);

		JPanel rightPanel = new JPanel();

		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(line);
		rightPanel.add(square);

		rightPanel.add(ex);
		rightPanel.add(ellipse);

		rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		rightPanel.add(clear);

		rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		rightPanel.add(delete);

		rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		rightPanel.add(exit);

		rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		leftPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		rightPanel.setAlignmentY(Component.TOP_ALIGNMENT);

		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));

		p.add(leftPanel);
		p.add(rightPanel);

		p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		/*
		 * once we've placed our GUI components in a JPanel, we make that panel
		 * the 'content pane' of the JFrame
		 */

		setContentPane(p);
	}

	/************************************
	 * implement ActionListener method
	 *
	 * this handles clicks from the two buttons - clear and exit
	 ************************************/

	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();

		if (source == clear)
			vp.clear();

		else if (source == delete)
			vp.deleteSelectedObject();

		else if (source == exit)
			System.exit(0);
	}

	/***************************************
	 * implement (5) MouseListener methods
	 ***************************************/

	// we're not using these four functions, but we must
	// provide implementations for them to satisfy the
	// MouseInputListener interface
	public void mouseEntered(MouseEvent me) {
	}

	public void mouseMoved(MouseEvent me) {
	}

	public void mouseClicked(MouseEvent me) {
	}

	public void mouseExited(MouseEvent me) {
	}

	// these hold the coordinates of the place where the mouse
	// button is first pressed, during a mouse action
	int start_of_drag_x;
	int start_of_drag_y;

	// these hold the displacement (measured in pixels) from the
	// place there the mouse pointer grabs the selected object,
	// and the upper left corner of the object
	int offset_x;
	int offset_y;

	// this flag indicates that the current mouse gesture is a
	// click (i.e., the mouse button is pressed and released
	// without much intervening mouse movement)
	//
	// if this flag is false, then the current mouse gesture is
	// a drag (i.e., the mouse button is being moved while the
	// mouse is being moved)
	boolean isClick;

	// this flag indicates that the current mouse gesture is
	// causing the currently selected object to tbe resized
	// (because the mouse has grabbed the resize handle in the
	// lower right corner)
	//
	// if this flag is false, then the cirrent mouse gesture
	// is causing the currently selected object to be moved
	// (because the mouse grabbed somewhere else on the object)
	boolean isResize;

	/*
	 * This event listener is called when the user presses the mouse button.
	 */
	public void mousePressed(MouseEvent me) {
		// let's remember where the user first clicked
		start_of_drag_x = me.getX();
		start_of_drag_y = me.getY();

		// for now, we assume that it's going to be a 'click'
		isClick = true;

		// also, we assume that we're going to resize the object
		isResize = true;

		// here we determine whether the object that the user has clicked
		// on was already selected
		DrawingObject previouslySelected = vp.getSelectedObject();

		vp.processSelectionClick(start_of_drag_x, start_of_drag_y);

		DrawingObject currentlySelected = vp.getSelectedObject();

		// if the object is being clicked for the first time, we
		// only make that object selected
		// but if this is the second click on the same object, then
		// we're going to allow the user to move or resize the object
		if (previouslySelected != null
				&& previouslySelected == currentlySelected) {
			// if we're here, we're moving or resizing
			// so let's figure-out which
			Rectangle bb = currentlySelected.getBoundingBox();
			isResize = ((bb.x + bb.width - SELECTION_DISTANCE < start_of_drag_x)
					&& (start_of_drag_x < bb.x + bb.width + SELECTION_DISTANCE)
					&& (bb.y + bb.height - SELECTION_DISTANCE < start_of_drag_y) && (start_of_drag_y < bb.y
					+ bb.height + SELECTION_DISTANCE));
			if (isResize) {
				// if we're resizing, then we remember the location
				// of the upper left corner of the object
				start_of_drag_x = bb.x;
				start_of_drag_y = bb.y;
			} else {
				// if we're moving the object, then we need to know
				// the offset (distance) from the upper left corner
				// of the object to the place where the user has
				// grabbed the object
				offset_x = bb.x - start_of_drag_x;
				offset_y = bb.y - start_of_drag_y;
			}
		}
	}

	/*
	 * This event listener is called when the user drags the mouse button (i.e.,
	 * when the mouse is moved while the button is being pressed).
	 */
	public void mouseDragged(MouseEvent me) {
		int x = me.getX();
		int y = me.getY();

		// we define a 'click' as a button press where the user
		// doesn't drag the mouse. but to be more user friendly,
		// we'll ignore a (very) small amount of mouse movement
		if (isClick) {
			// determine whether this is a 'click' or a 'drag'
			int abs_delta_x = start_of_drag_x - x;
			abs_delta_x = (abs_delta_x > 0 ? abs_delta_x : -abs_delta_x);

			int abs_delta_y = start_of_drag_y - y;
			abs_delta_y = (abs_delta_y > 0 ? abs_delta_y : -abs_delta_y);

			if (abs_delta_x > 5 || abs_delta_y > 5)
				isClick = false;
		}

		// if we're dragging, then we have to adjust the selected object
		if (!isClick) {
			// if there is no object selected, then this means that the
			// user is trying to create a new object (they've clicked and
			// dragged on an empty bit of the panel
			// so we create a new object...
			if (vp.getSelectedObject() == null) {
				DrawingObject newObject = null;

				if (line.isSelected())
					newObject = new DrawingLine(start_of_drag_x,
							start_of_drag_y, x, y);

				else if (square.isSelected())
					newObject = new DrawingSquare(start_of_drag_x,
							start_of_drag_y, x, y);

				else if (ex.isSelected())
					newObject = new DrawingX(start_of_drag_x, start_of_drag_y,
							x, y);

				else if (ellipse.isSelected())
					newObject = new DrawingEllipse(start_of_drag_x,
							start_of_drag_y, x, y);

				vp.addEntity(newObject);
			} else {
				// if an object is selected, then we need to either resize it
				// or move it, depending upon the isResize flag
				if (isResize)
					vp.updateSelection(start_of_drag_x, start_of_drag_y, x, y);
				else {
					Rectangle bb = vp.getSelectedObject().getBoundingBox();
					vp.updateSelection(x + offset_x, y + offset_y, x + bb.width
							+ offset_x, y + bb.height + offset_y);
				}
			}
		}
	}

	/*
	 * This event listener is called when the user releases the mouse button.
	 */
	public void mouseReleased(MouseEvent me) {
		int x = me.getX();
		int y = me.getY();

		// if this is a 'click' then we want to select the
		// object that was clicked on
		if (isClick)
			vp.processSelectionClick(x, y);

		// otherwise this is a 'drag', meaning we want to adjust
		// the position or size of the currently selected object
		// the isResize boolean tells us which it is.
		else {
			if (isResize)
				// change the size of the current object
				vp.updateSelection(start_of_drag_x, start_of_drag_y, x, y);

			else {
				// change the position of the current object
				Rectangle bb = vp.getSelectedObject().getBoundingBox();
				vp.updateSelection(x + offset_x, y + offset_y, x + bb.width
						+ offset_x, y + bb.height + offset_y);
			}
		}

		isClick = false;
	}

	/***************************************
	 *
	 * This inner class contains both the Model and the Viewer
	 *
	 ***************************************/

	class ViewPanel extends JPanel {
		/**
	 * 
	 */
		private static final long serialVersionUID = 1L;

		ViewPanel() {
			super(null); // call the superclass's constructor

			setPreferredSize(new Dimension(350, 300));
		}

		/***************************************
		 * This is the Model (the data used to draw the panel)
		 ***************************************/

		// this holds the collection of DrawingObjects
		LinkedList<DrawingObject> objects = new LinkedList<DrawingObject>();

		// this identifies which of the objects is the 'selected' one
		DrawingObject selectedObject = null;

		/*
		 * Add a widget to our collection of DrawingObjects
		 */
		public void addEntity(DrawingObject o) {
			objects.add(o);
			selectedObject = o;
			repaint();
		}

		/*
		 * This function is called when the user clicks the mouse in the drawing
		 * window.
		 * 
		 * Its purpose is to determine which object the user has clicked on.
		 * This is accomplished by asking each object whether they've been hit
		 * (the wasClicked() function).
		 * 
		 * This function also returns a boolean which indicates whether the
		 * click was in the resize corner (the lower left corner)
		 */
		public void processSelectionClick(int x, int y) {
			selectedObject = null;
			boolean isResize = false;

			for (DrawingObject o : objects) {
				Rectangle bb = o.getBoundingBox();
				isResize = ((bb.x + bb.width - SELECTION_DISTANCE < x)
						&& (x < bb.x + bb.width + SELECTION_DISTANCE)
						&& (bb.y + bb.height - SELECTION_DISTANCE < y) && (y < bb.y
						+ bb.height + SELECTION_DISTANCE));

				if (o.wasClicked(x, y) || isResize)
					selectedObject = o;
			}

			repaint();
		}

		/*
		 * Add a widget to our collection of DrawingObjects
		 */

		public void updateSelection(int x1, int y1, int x2, int y2) {
			// in order for most of the drawing functions in the
			// Graphics2D object to work properly, we need to
			// make sure that x1 < x2, and y1 < y2....
			int minX = (x1 < x2 ? x1 : x2);
			int minY = (y1 < y2 ? y1 : y2);
			int maxX = (x1 > x2 ? x1 : x2);
			int maxY = (y1 > y2 ? y1 : y2);

			// ... the exception is for a line, wherein the
			// pairing of (x1,y1) and (x2,y2) must be preserved
			if (selectedObject instanceof DrawingLine) {
				minX = x1;
				minY = y1;
				maxX = x2;
				maxY = y2;
			}

			selectedObject.resizeObject(minX, minY, maxX, maxY);
			repaint();
		}

		/*
		 * This function returns a reference to the object that the user has
		 * selected, or null if nothing is selected.
		 */
		public DrawingObject getSelectedObject() {
			return selectedObject;
		}

		/*
		 * This function unselects the object that the user had previously
		 * selected.
		 */
		public void unselectSelectedObject() {
			selectedObject = null;
			repaint();
		}

		/*
		 * Clear the screen (by emptying out collection of DrawingObjects)
		 */
		public void deleteSelectedObject() {
			objects.remove(selectedObject);
			selectedObject = null;
			repaint();
		}

		/*
		 * Clear the screen (by emptying out collection of DrawingObjects)
		 */
		public void clear() {
			objects.clear();
			repaint();
		}

		/*************************************
		 * This is the View (the code that draws the panel contents, based upon
		 * the model)
		 *************************************/

		public void paintComponent(Graphics g) {
			// the super clears the background
			super.paintComponent(g);

			// cast to the better Graphics object
			Graphics2D g2 = (Graphics2D) g;

			// we're going to draw in black
			g2.setColor(Color.BLACK);

			// thicker line
			g2.setStroke(new BasicStroke(2));

			// loop through all of the objects
			for (DrawingObject o : objects) {
				// but, if this object is the currently selected object,
				// then we draw it in with a blue bounding box and resize
				// handle
				if (o == selectedObject) {
					// first, we draw the object itself
					o.drawObject(g2);

					g2.setColor(Color.BLUE);
					Rectangle bb = o.getBoundingBox();

					// next, the resize handle
					g2.fillRect(bb.x + bb.width - SELECTION_DISTANCE, bb.y
							+ bb.height - SELECTION_DISTANCE,
							2 * SELECTION_DISTANCE, 2 * SELECTION_DISTANCE);
					if (bb.width < 0) {
						bb.x = bb.x + bb.width;
						bb.width = -bb.width;
					}
					if (bb.height < 0) {
						bb.y = bb.y + bb.height;
						bb.height = -bb.height;
					}

					// and lastly, the blue bounding box
					g2.drawRect(bb.x, bb.y, bb.width, bb.height);
					g2.setColor(Color.BLACK);
				}

				else
					o.drawObject(g2);
			}
		}
	}
}
