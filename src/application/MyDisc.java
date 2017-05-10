/*
 * Creates a square object
 * Refer to MyCircle and Sprite for comments since this is almost identical
 */
package application;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MyDisc implements Sprite {
	
	private static final long serialVersionUID = 1L;
	
	int cornerX;
	int cornerY;
	int radius;
	
	int directionX = 1;
	int directionY = 1;
	
	public MyDisc(int cornerX, int cornerY, int radius) {
		this.cornerX = cornerX;
		this.cornerY = cornerY;
		this.radius = radius;
	}
	
	public void drawOn(GraphicsContext gc) {
		gc.setFill(Color.valueOf(discFillColor));
		gc.setStroke(Color.valueOf(discStrokeColor));
		gc.fillOval(cornerX, cornerY, radius, radius);
		gc.strokeOval(cornerX, cornerY, radius, radius);
		
		/* need to set the fill colour back to the default because JavaFX will keep the last colour used */
		gc.setFill(Color.valueOf(defaultFillColor));
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
	
	public int getWidth() { return radius; }
	public int getHeight() { return radius; }
	public void setWidth(int width) { this.radius = width*2; }
	public void setHeight(int height) { this.radius = height*2; }
	
	public ShapeType getShape() { return ShapeType.DISC; }

	public String print() {
		return "D: " + cornerX + " " + cornerY + " " + radius + " " + radius;
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
