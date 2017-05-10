package application;

import javafx.scene.canvas.GraphicsContext;

public class SpriteUtils {
	
	/* main controller for drawing shapes - decide which shape to draw then call that specific function */
	/* note: temp variable indicates this is a temporary shape and should not be stored in the ArrayList */
	/*       gets passed to specific function draw program                                               */
	public static void drawShape(int x1, int y1, int x2, int y2, ShapeType shapeType, boolean temp, GraphicsContext gc) {
		
		if (shapeType==ShapeType.CIRCLE) {
			drawCircle(x1,y1,x2,y2,temp,gc);
			
		} else if (shapeType==ShapeType.SQUARE) {
			drawSquare(x1,y1,x2,y2,temp,gc);
			
		} else if (shapeType==ShapeType.DISC) {
			drawDisc(x1,y1,x2,y2,temp,gc);
			
		} else {
			System.out.println("No valid shape selected");
		}
	}
	
	/* draw the circle - uses the radius calculations, etc */
	private static void drawCircle(int x1, int y1, int x2, int y2, boolean temp, GraphicsContext gc) {
		// calulate the radius using the 4 coordinates from the mouse events
		int r = calcRadius(x1, y1, x2, y2);
		
		/* instantiate sprite object and use the sprite's drawOn function to actually draw it on the canvas */
		Sprite circle = new MyCircle(x1-r, y1-r, r*2);
		circle.drawOn(gc);
		
		/* if this isn't a temporary shape then add it to the myShapes array */
		if (!temp)
			Main.myShapes.add(circle);
	}
	
	/* identical to drawCircle in calculations - may be able to combine the two to some extent */
	/* maybe make another method that just does the calculations? */
	private static void drawDisc(int x1, int y1, int x2, int y2, boolean temp, GraphicsContext gc) {
		int r = calcRadius(x1, y1, x2, y2);
		Sprite disc = new MyDisc(x1-r, y1-r, r*2);
		disc.drawOn(gc);
		if (!temp)
			Main.myShapes.add(disc);
	}

	/* draw the square - implements a 3-variable switch to swap values if mouse is going up or left */
	private static void drawSquare(int x1, int y1, int x2, int y2, boolean temp, GraphicsContext gc) {
		int tempHold = 0;
		if (x2 < x1) {
			tempHold = x1;
			x1 = x2;
			x2 = tempHold;
		}
		if (y2 < y1) {
			tempHold = y1;
			y1 = y2;
			y2 = tempHold;
		}
		
		Sprite square = new MySquare(x1,y1,x2-x1,y2-y1);
		square.drawOn(gc);
		if (!temp)
			Main.myShapes.add(square);
	}
	
	private static int calcRadius(int x1, int y1, int x2, int y2) {
		/* gets the squares first */
		int aSquare = (x2-x1) * (x2-x1);
		int bSquare = (y2-y1) * (y2-y1);
		
		/* add the squares and calculate square root returning result */
		return (int) Math.sqrt( aSquare + bSquare );
		
	}
}
