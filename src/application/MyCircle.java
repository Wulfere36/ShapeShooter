/*
 * Circle object - called it MyCircle to differentiate it from the javafx shape Circle object
 */
package application;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MyCircle implements Sprite {
	
	/* need this to allow for saving this object to the binary file */
	private static final long serialVersionUID = 1L;
	
	/* the basic variables for a circle and the movement of this object */
	int cornerX;
	int cornerY;
	int radius;
	
	int directionX = 1;
	int directionY = 1;
	
	
	public MyCircle(int x, int y, int r) {
		cornerX = x;
		cornerY = y;
		radius = r;
	}
	
	/* draw this object on the canvas */
	public void drawOn(GraphicsContext gc) {
		gc.setStroke(Color.valueOf(circleStrokeColor));
		gc.strokeOval(cornerX, cornerY, radius, radius);
	}

	/* set the top left corner of this object, adding the increment x and y */
	/* incX and incY could be negative - that is calculated in main app */
	public void incrementCorner(int incX, int incY) {
		this.cornerX += incX;
		this.cornerY += incY;
	}
	
	/* get current direction of x coordinate */
	public int getDirectionX() { return this.directionX; }
	
	/* get current direction of y coordinate */
	public int getDirectionY() { return this.directionY; }

	/* change direction of X by multiplying by -1 - called from main app */
	public void changeDirectionX( ){ this.directionX *= -1; }
	
	/* change direction of Y by multiplying by -1 - called from main app */
	public void changeDirectionY( ){ this.directionY *= -1; }
	
	/* 
	 * get the top corner coordinates and width and height 
	 * since this is a circle width will return radius and height will return radius
	 */
	public int getCornerX() { return cornerX; }
	public int getCornerY() { return cornerY; }
	public void setCornerX(int cornerX) { this.cornerX = cornerX; }
	public void setCornerY(int cornerY) { this.cornerY = cornerY; }
	
	/* set the width and height of the object
	 * since this is a circle the height and width will do the same thing - just need the two methods
	 * here because its what is in the interface
	 */
	public int getWidth() { return radius; }
	public int getHeight() { return radius; }
	public void setWidth(int width) { this.radius = width*2; }
	public void setHeight(int height) { this.radius = height*2; }
	
	/* return the ShapeType of this object which is CIRCLE */
	public ShapeType getShape() { return ShapeType.CIRCLE; }
	
	/* don't really need this anymore - unless we need to print something out later */
	/* doesn't hurt to keep it here but we should remove it if we don't need it */
	public String print() {
		return "C: " + cornerX + " " + cornerY + " " + radius + " " + radius;
	}
	
	public void moveSprite(int incX, int incY, Canvas canvas, GraphicsContext gc) {
		/* get the original x and y coordinates and height and width */
		int oldX = getCornerX();
		int oldY = getCornerY();
		int r = radius;
		
		/* recalculate the bottom and right side coordinates */
		int bottomX = oldX + r;
		int bottomY = oldY + r;
		
		/* calulate the direction it needs to go in - method will set the values in the object itself */
		AnimateUtils.calcDirection(oldX, oldY, bottomX, bottomY, canvas, this);
		
		/* set the new top left corner */
		incrementCorner(incX*directionX , incY*directionY);
		
		/* draw the sprite on the canvas */
		drawOn(gc);
	}
}
