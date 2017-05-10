/*
 * the sprite interface so we can do some polymorphism
 * allows us to create an ArrayList of Sprites which contains all the different shapes
 * such as circles, squares, disc, bullet, etc. Any shape that requires motion implements this
 */
package application;

import java.io.Serializable;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public interface Sprite extends Serializable {
	
	/* set the default colours */
	String circleStrokeColor = "RED";
	String squareStrokeColor = "YELLOW";
	String discStrokeColor = "RED";
	String discFillColor = "LIGHTBLUE";
	String defaultFillColor = "BLACK";
	
	/* the method to draw on the canvas */
	public default void drawOn(GraphicsContext gc) {}
	
	/* the method to set the top left corner of the shape */
	public default void incrementCorner(int x, int y) {}

	/* return the top left corner coordinates and the width and height - circles will return the radius and radius */
	public default int getCornerX() { return 0; }
	public default int getCornerY() { return 0; }
	public default int getWidth() { return 0; }
	public default int getHeight() { return 0; }

	/* set the individual values  */
	public default void setCornerX(int x) {}
	public default void setCornerY(int y) {}
	public default void setWidth(int width) {}
	public default void setHeight(int height) {}
	
	/* get the current direction for x and y and change the direction */
	public default int getDirectionX() { return 0; }
	public default int getDirectionY() { return 0; }
	public default void changeDirectionX( ){}
	public default void changeDirectionY( ){}
	
	/* return the shape type */
	public default ShapeType getShape() { return null; }
	
	/* old method - probably can get rid of it from here and the other classes */
	public default String print() {
		return "";
	}
	public default void moveSprite(int incX, int incY, Canvas canvas, GraphicsContext gc) { }
	
}
