/*
 * Creates a square object
 * Refer to MyCircle and Sprite for comments since this is almost identical
 */
package application;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MySquare implements Sprite {
	
	private static final long serialVersionUID = 1L;
	
	int cornerX;
	int cornerY;
	int width;
	int height;
	
	int directionX = 1;
	int directionY = 1;
	
	public MySquare(int cornerX, int cornerY, int width, int height) {
		this.cornerX = cornerX;
		this.cornerY = cornerY;
		this.width = width;
		this.height = height;
	}

	public void drawOn(GraphicsContext gc) {
		gc.setStroke(Color.valueOf(squareStrokeColor));
		gc.strokeRect(cornerX, cornerY, width, height);
	}

	public void incrementCorner(int x, int y) {
		this.cornerX += x;
		this.cornerY += y;
	}

	public int getDirectionX() { return this.directionX; }
	public int getDirectionY() { return this.directionY; }
	public void changeDirectionX( ){ this.directionX *= -1; }
	public void changeDirectionY( ){ this.directionY *= -1; }

	public int getCornerX() { return cornerX; }
	public int getCornerY() { return cornerY; }
	public void setCornerX(int cornerX) { this.cornerX = cornerX; }
	public void setCornerY(int cornerY) { this.cornerY = cornerY; }
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public void setWidth(int width) { this.width = width; }
	public void setHeight(int height) { this.height = height; }

	public String print() {
		return "R: " + cornerX + " " + cornerY + " " + width + " " + height;
	}
	
	public ShapeType getShape() { return ShapeType.SQUARE; }
	
	public void moveSprite(int incX, int incY, Canvas canvas, GraphicsContext gc) {
		/* get the original x and y coordinates and height and width */
		int oldX = getCornerX();
		int oldY = getCornerY();
		int h = height;
		int w = width;
		
		/* recalculate the bottom and right side coordinates */
		int bottomX = oldX + w;
		int bottomY = oldY + h;
		
		/* calulate the direction it needs to go in - method will set the values in the object itself */
		AnimateUtils.calcDirection(oldX, oldY, bottomX, bottomY, canvas, this);
		
		/* set the new top left corner */
		incrementCorner(incX*directionX , incY*directionY);
		
		/* draw the sprite on the canvas */
		drawOn(gc);
	}
}
