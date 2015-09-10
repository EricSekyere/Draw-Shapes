

import java.awt.*;
import java.util.*;
import java.io.*;

import java.awt.font.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.image.renderable.*;
import java.text.*;


/**
 * This class is the driver (client class) for Assignment #5,
 * it tests the subclasses of DrawingObject...
 */
class a5
{
   static public void main(String[] args)
   {
      // For aquiring input
      Scanner in = new Scanner(System.in);

      while(true)
      {
         // Output the command prompt
         System.out.println("Type:");
         System.out.println("   'test1, test2 ... test8' to run the tests,");
         System.out.println("   'testall' runs all of the tests,");
         System.out.println("   'exit' to exit:");
         System.out.print("> ");

         String command = in.nextLine();
         System.out.println("");

         boolean testall = false;
         if(command.equalsIgnoreCase("testall"))
            testall = true;

         // Handle the 'test1' command
         // QUESTION: Can we create each object?
         if(command.equalsIgnoreCase("test1") || testall)
         {
            System.out.println("Test #1: Can we create each object?");

            LinkedList<DrawingObject> list = new LinkedList<DrawingObject>();

            list.add(new DrawingEllipse(10, 20, 30, 40));
            list.add(new DrawingLine(50, 60, 70, 80));
            list.add(new DrawingSquare(110, 120, 130, 140));
            list.add(new DrawingX(150, 160, 170, 180));

            String[] expected = {
               "DrawingEllipse",
               "DrawingLine",
               "DrawingSquare",
               "DrawingX"
            };

            int i = 0;
            boolean passed = true;
            for(DrawingObject o : list)
            {
               Rectangle bb = o.getBoundingBox();
               String output = o.getClass().getName();
               if(!output.equals(expected[i]))
               {
                  System.out.println("Error:");
                  System.out.println("Expected: " + expected[i]);
                  System.out.println("Found:    " + output);
                  passed = false;
               }
               i += 1;
            }

            if(passed)
               System.out.println("PASSED");
            else
               System.out.println("FAILED");
         }

         // Handle the 'test2' command
         // QUESTION: Test getBoundingBox()
         if(command.equalsIgnoreCase("test2") || testall)
         {
            System.out.println("Test #2: Test getBoundingBox()");

            LinkedList<DrawingObject> list = new LinkedList<DrawingObject>();

            list.add(new DrawingEllipse(10, 20, 30, 40));
            list.add(new DrawingLine(50, 60, 70, 80));
            list.add(new DrawingSquare(110, 120, 130, 140));
            list.add(new DrawingX(150, 160, 170, 180));

            String[] expected = {
               "DrawingEllipse(10,20)-(30,40)",
               "DrawingLine(50,60)-(70,80)",
               "DrawingSquare(110,120)-(130,140)",
               "DrawingX(150,160)-(170,180)"
            };

            int i = 0;
            boolean passed = true;
            for(DrawingObject o : list)
            {
               Rectangle bb = o.getBoundingBox();
               String output = o.getClass().getName()
                     + "(" + bb.x
                     + "," + bb.y
                     + ")-(" + (bb.x+bb.width)
                     + "," + (bb.y+bb.height)
                     + ")";
               if(!output.equals(expected[i]))
               {
                  System.out.println("Error:");
                  System.out.println("Expected: " + expected[i]);
                  System.out.println("Found:    " + output);
                  passed = false;
               }
               i += 1;
            }

            if(passed)
               System.out.println("PASSED");
            else
               System.out.println("FAILED");
         }

         // Handle the 'test3' command
         // QUESTION: Test resizeObject()
         if(command.equalsIgnoreCase("test3") || testall)
         {
            System.out.println("Test #3: Test resizeObject()");

            LinkedList<DrawingObject> list = new LinkedList<DrawingObject>();

            int i = 0;
            list.add(new DrawingEllipse(i++, i++, i++, i++));
            list.add(new DrawingLine(i++, i++, i++, i++));
            list.add(new DrawingSquare(i++, i++, i++, i++));
            list.add(new DrawingX(i++, i++, i++, i++));

            i = 100;
            for(DrawingObject o : list)
               o.resizeObject(i++,i++,i++,i++);

            String[] expected = {
               "DrawingEllipse(100,101)-(102,103)",
               "DrawingLine(104,105)-(106,107)",
               "DrawingSquare(108,109)-(110,111)",
               "DrawingX(112,113)-(114,115)"
            };

            boolean passed = true;
            i = 0;
            for(DrawingObject o : list)
            {
               Rectangle bb = o.getBoundingBox();
               String output = o.getClass().getName()
                     + "(" + bb.x
                     + "," + bb.y
                     + ")-(" + (bb.x+bb.width)
                     + "," + (bb.y+bb.height)
                     + ")";
               if(!output.equals(expected[i]))
               {
                  System.out.println("Error:");
                  System.out.println("Expected: " + expected[i]);
                  System.out.println("Found:    " + output);
                  passed = false;
               }
               i += 1;
            }

            if(passed)
               System.out.println("PASSED");
            else
               System.out.println("FAILED");
         }

         // Handle the 'test4' command
         // QUESTION: Test DrawingEllipse.wasClicked()
         if(command.equalsIgnoreCase("test4") || testall)
         {
            System.out.println("Test #4: Test DrawingEllipse.wasClicked()");

            LinkedList<DrawingObject> list = new LinkedList<DrawingObject>();

            DrawingObject o = new DrawingEllipse(100, 100, 200, 200);

            int[] expectedTrue = {
               100, 150,   // check some middle points
               200, 150,
               150, 100,
               150, 200,
               115, 115,   // rounded corners (the curvey part)
               115, 185,
               185, 115,
               185, 185,
            };
            int[] expectedFalse = {
               100, 100,   // check the corners
               100, 200,
               200, 100,
               200, 200,
                 0,   0,   // clearly outside
               150, 150,   // middle
               250, 250,   // also clearly outside
               105, 105,   // rounded corners (the curvey part)
               125, 125,
               105, 195,
               125, 175,
               195, 105,
               175, 115,
               195, 195,
               175, 175,
            };
/*
 * this code was just for testing...
 *
            {
               boolean expecting = false;
               for(int i = 0; i < 200; i++)
               {
                  if(expecting != o.wasClicked(i,i))
                  {
                     expecting = !expecting;
                     System.out.println("  " + i + " switched to " + expecting);
                  }
               }
            }
*/

            boolean passed = true;
            int i = 0;
            for(i = 0; i < expectedTrue.length; i += 2)
               if(!o.wasClicked(expectedTrue[i],expectedTrue[i+1]))
               {
                  System.out.println("Error:");
                  System.out.println("Expected: wasClicked(...) to be true");
                  System.out.println("Found:    " + o.getClass().getName() + ".wasClicked(" + expectedTrue[i] + "," + expectedTrue[i+1] + ") == false");
                  passed = false;
               }

            i = 0;
            for(i = 0; i < expectedFalse.length; i += 2)
               if(o.wasClicked(expectedFalse[i],expectedFalse[i+1]))
               {
                  System.out.println("Error:");
                  System.out.println("Expected: wasClicked(...) to be false");
                  System.out.println("Found:    " + o.getClass().getName() + ".wasClicked(" + expectedFalse[i] + "," + expectedFalse[i+1] + ") == true");
                  passed = false;
               }

            if(passed)
               System.out.println("PASSED");
            else
               System.out.println("FAILED");
         }

         // Handle the 'test5' command
         // QUESTION: Test DrawingLine.wasClicked()
         if(command.equalsIgnoreCase("test5") || testall)
         {
            System.out.println("Test #5: Test DrawingLine.wasClicked()");

            LinkedList<DrawingObject> list = new LinkedList<DrawingObject>();

            DrawingObject o = new DrawingLine(100, 100, 200, 200);

            int[] expectedTrue = {
               100, 100,   // check the corners
               200, 200,
               150, 150,   // middle
            };
            int[] expectedFalse = {
                 0,   0,   // clearly outside
               250, 250,   // also clearly outside
               100, 200,   // other corners
               200, 100,
               100, 150,   // check some middle points
               200, 150,
               150, 100,
               150, 200,
            };

            boolean passed = true;
            int i = 0;
            for(i = 0; i < expectedTrue.length; i += 2)
               if(!o.wasClicked(expectedTrue[i],expectedTrue[i+1]))
               {
                  System.out.println("Error:");
                  System.out.println("Expected: wasClicked(...) to be true");
                  System.out.println("Found:    " + o.getClass().getName() + ".wasClicked(" + expectedTrue[i] + "," + expectedTrue[i+1] + ") == false");
                  passed = false;
               }

            i = 0;
            for(i = 0; i < expectedFalse.length; i += 2)
               if(o.wasClicked(expectedFalse[i],expectedFalse[i+1]))
               {
                  System.out.println("Error:");
                  System.out.println("Expected: wasClicked(...) to be false");
                  System.out.println("Found:    " + o.getClass().getName() + ".wasClicked(" + expectedFalse[i] + "," + expectedFalse[i+1] + ") == true");
                  passed = false;
               }

            if(passed)
               System.out.println("PASSED");
            else
               System.out.println("FAILED");
         }

         // Handle the 'test6' command
         // QUESTION: Test DrawingSquare.wasClicked()
         if(command.equalsIgnoreCase("test6") || testall)
         {
            System.out.println("Test #6: Test DrawingSquare.wasClicked()");

            LinkedList<DrawingObject> list = new LinkedList<DrawingObject>();

            DrawingObject o = new DrawingSquare(100, 100, 200, 200);

            int[] expectedTrue = {
               100, 100,   // check the corners
               100, 200,
               200, 100,
               200, 200,
               100, 150,   // check some middle points
               200, 150,
               150, 100,
               150, 200,
            };
            int[] expectedFalse = {
                 0,   0,   // clearly outside
               150, 150,   // middle
               250, 250,   // also clearly outside
            };

            boolean passed = true;
            int i = 0;
            for(i = 0; i < expectedTrue.length; i += 2)
               if(!o.wasClicked(expectedTrue[i],expectedTrue[i+1]))
               {
                  System.out.println("Error:");
                  System.out.println("Expected: wasClicked(...) to be true");
                  System.out.println("Found:    " + o.getClass().getName() + ".wasClicked(" + expectedTrue[i] + "," + expectedTrue[i+1] + ") == false");
                  passed = false;
               }

            i = 0;
            for(i = 0; i < expectedFalse.length; i += 2)
               if(o.wasClicked(expectedFalse[i],expectedFalse[i+1]))
               {
                  System.out.println("Error:");
                  System.out.println("Expected: wasClicked(...) to be false");
                  System.out.println("Found:    " + o.getClass().getName() + ".wasClicked(" + expectedFalse[i] + "," + expectedFalse[i+1] + ") == true");
                  passed = false;
               }

            if(passed)
               System.out.println("PASSED");
            else
               System.out.println("FAILED");
         }

         // Handle the 'test7' command
         // QUESTION: Test DrawingX.wasClicked()
         if(command.equalsIgnoreCase("test7") || testall)
         {
            System.out.println("Test #7: Test DrawingX.wasClicked()");

            LinkedList<DrawingObject> list = new LinkedList<DrawingObject>();

            DrawingObject o = new DrawingX(100, 100, 200, 200);

            int[] expectedTrue = {
               100, 100,   // check the corners
               100, 200,
               200, 100,
               200, 200,
               150, 150,   // middle
            };
            int[] expectedFalse = {
                 0,   0,   // clearly outside
               250, 250,   // also clearly outside
               100, 150,   // check some middle points
               200, 150,
               150, 100,
               150, 200,
            };

            boolean passed = true;
            int i = 0;
            for(i = 0; i < expectedTrue.length; i += 2)
               if(!o.wasClicked(expectedTrue[i],expectedTrue[i+1]))
               {
                  System.out.println("Error:");
                  System.out.println("Expected: wasClicked(...) to be true");
                  System.out.println("Found:    " + o.getClass().getName() + ".wasClicked(" + expectedTrue[i] + "," + expectedTrue[i+1] + ") == false");
                  passed = false;
               }

            i = 0;
            for(i = 0; i < expectedFalse.length; i += 2)
               if(o.wasClicked(expectedFalse[i],expectedFalse[i+1]))
               {
                  System.out.println("Error:");
                  System.out.println("Expected: wasClicked(...) to be false");
                  System.out.println("Found:    " + o.getClass().getName() + ".wasClicked(" + expectedFalse[i] + "," + expectedFalse[i+1] + ") == true");
                  passed = false;
               }

            if(passed)
               System.out.println("PASSED");
            else
               System.out.println("FAILED");
         }

         // Handle the 'test8' command
         // QUESTION: Test drawObject()
         if(command.equalsIgnoreCase("test8") || testall)
         {
            System.out.println("Test #8: Test drawObject()");

            {
               DrawingEllipse ellipse = new DrawingEllipse(10, 20, 30, 40);
               System.out.println("Testing DrawingEllipse.drawObject()");
               debugGraphics2D g = new debugGraphics2D();
               ellipse.drawObject(g);

               if(g.ellipsePassed())
                  System.out.println("Ellipse: PASSED");
               else
                  System.out.println("Ellipse: FAILED");
            }


            {
               DrawingLine line = new DrawingLine(10, 20, 30, 40);
               System.out.println("Testing DrawingLine.drawObject()");
               debugGraphics2D g = new debugGraphics2D();
               line.drawObject(g);

               if(g.linePassed())
                  System.out.println("Line: PASSED");
               else
                  System.out.println("Line: FAILED");
            }

            {
               DrawingSquare square = new DrawingSquare(10, 20, 30, 40);
               System.out.println("Testing DrawingSquare.drawObject()");
               debugGraphics2D g = new debugGraphics2D();
               square.drawObject(g);

               if(g.squarePassed())
                  System.out.println("Square: PASSED");
               else
                  System.out.println("Square: FAILED");
            }

            {
               DrawingX ex = new DrawingX(10, 20, 30, 40);
               System.out.println("Testing DrawingX.drawObject()");
               debugGraphics2D g = new debugGraphics2D();
               ex.drawObject(g);

               if(g.xPassed())
                  System.out.println("X: PASSED");
               else
                  System.out.println("X: FAILED");
            }
         }

         // Handle the 'exit' command
         if(command.equalsIgnoreCase("exit"))
         {
            return;
         }

         System.out.println("");
      }
   }


   /*************************************************
    *
    *  Inner Class for testing the Graphics drawing
    *
    *************************************************/

   private static class debugGraphics2D extends Graphics2D
   {
      // function call counters
      public int drawOvalCounter = 0;
      public int drawLineCounter = 0;
      public int drawRectCounter = 0;

      public boolean ellipsePassed() { return (drawOvalCounter == 1); }
      public boolean linePassed()    { return (drawLineCounter == 1); }
      public boolean squarePassed()  { return (drawLineCounter == 4) || (drawRectCounter == 1); }
      public boolean xPassed()       { return (drawLineCounter == 2); }

      public void	drawLine(int x1, int y1, int x2, int y2)
      {
         System.out.println("drawLine(" + x1 + "," + y1 + "," + x2 + "," + y2 + ")");
         drawLineCounter++;
      }
      public void	drawOval(int x, int y, int width, int height)
      {
         System.out.println("drawOval(" + x + "," + y + "," + width + "," + height + ")");
         drawOvalCounter++;
      }
      public void drawRect(int x, int y, int width, int height)
      {
         System.out.println("drawRect(" + x + "," + y + "," + width + "," + height + ")");
         drawRectCounter++;
      }

      public debugGraphics2D() {}

      public void addRenderingHints(Map<?,?> hints) {}
      public void	clearRect(int x, int y, int width, int height) {}
      public void clip(Shape s) {}
      public void clipRect(int x, int y, int width, int height) {}
      public void copyArea(int x, int y, int width, int height, int dx, int dy) {}
      public Graphics create() { return null; }
      public void dispose() {}
      public void draw(Shape s) {}
      public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {}
      public void drawGlyphVector(GlyphVector g, float x, float y) {}
      public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {}
      public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) { return false; }
      public boolean drawImage(Image img, int x, int y, ImageObserver observer) { return false; }
      public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) { return false; }
      public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) { return false; }
      public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) { return false; }
      public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) { return false; }
      public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) { return false; }
      public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {}
      public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {}
      public void	drawRenderableImage(RenderableImage img, AffineTransform xform) {}
      public void	drawRenderedImage(RenderedImage img, AffineTransform xform) {}
      public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {}
      public void	drawString(AttributedCharacterIterator iterator, float x, float y) {}
      public void	drawString(AttributedCharacterIterator iterator, int x, int y) {}
      public void	drawString(String str, float x, float y) {}
      public void	drawString(String str, int x, int y) {}
      public void	fill(Shape s) {}
      public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {}
      public void	fillOval(int x, int y, int width, int height) {}
      public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {}
      public void	fillRect(int x, int y, int width, int height) {}
      public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {}
      public Color             getBackground()     { return null; }
      public Shape             getClip()           { return null; }
      public Rectangle         getClipBounds()     { return null; }
      public Color             getColor()          { return null; }
      public Composite         getComposite()      { return null; }
      public GraphicsConfiguration getDeviceConfiguration() { return null; }
      public Font              getFont()           { return null; }
      public FontMetrics       getFontMetrics(Font f) { return null; }
      public FontRenderContext getFontRenderContext() { return null; }
      public Paint             getPaint()          { return null; }
      public Object            getRenderingHint(RenderingHints.Key hintKey) { return null; }
      public RenderingHints    getRenderingHints() { return null; }
      public Stroke            getStroke()         { return null; }
      public AffineTransform   getTransform()      { return null; }
      public boolean	hit(Rectangle rect, Shape s, boolean onStroke) { return false; }
      public void	rotate(double theta) {}
      public void	rotate(double theta, double x, double y) {}
      public void	scale(double sx, double sy) {}
      public void	setBackground(Color color) {}
      public void setClip(int x, int y, int width, int height) {}
      public void setClip(Shape clip) {}
      public void setColor(Color c) {}
      public void	setComposite(Composite comp) {}
      public void setFont(Font font) {}
      public void	setPaint(Paint paint) {}
      public void setPaintMode() {}
      public void	setRenderingHint(RenderingHints.Key hintKey, Object hintValue) {}
      public void	setRenderingHints(Map<?,?> hints) {}
      public void	setStroke(Stroke s) {}
      public void	setTransform(AffineTransform Tx) {}
      public void setXORMode(Color c1) {}
      public void	shear(double shx, double shy) {}
      public void	transform(AffineTransform Tx) {}
      public void	translate(double tx, double ty) {}
      public void	translate(int x, int y) {}
   }
}



