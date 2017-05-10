package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet implements Sprite {
	
	private static final long serialVersionUID = 1L;
	
	int cornerX;
	int cornerY;
	int width;
	int height;
	int baseCornerY;
	
	int directionX = 1;
	int directionY = 1;
	
	String bulletStrokeColor = "WHITE";
	String bulletFillColor = "YELLOW";

	public Bullet(int cornerX, int cornerY, int width, int height) {
		this.cornerX = cornerX;
		this.cornerY = cornerY;
		this.width = width;
		this.height = height;
		this.baseCornerY = cornerY;
	}

	public void drawOn(GraphicsContext gc) {
		gc.setStroke(Color.valueOf(bulletStrokeColor));
		gc.setFill(Color.valueOf(bulletFillColor));
		gc.fillRect(cornerX, cornerY, width, height);
		gc.strokeRect(cornerX, cornerY, width, height);
		gc.setFill(Color.valueOf("BLACK"));
	}
	
	public void incrementCorner(int x, int y) {
		this.cornerX += x;
		this.cornerY += y;
	}

	public int getDirectionX() {
		return this.directionX;
	}
	public int getDirectionY() {
		return this.directionY;
	}

	public void changeDirectionX( ){
		this.directionX *= -1;
	}
	public void changeDirectionY( ){
		this.directionY *= -1;
	}
	
	public int getCornerX() { return cornerX; }
	public int getCornerY() { return cornerY; }
	public void setCornerX(int x) { this.cornerX = x; }
	public void setCornerY(int y) { this.cornerY = y; }
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public void setWidth(int width) { this.width = width; }
	public void setHeight(int height) { this.height = height; }

	public ShapeType getShape() {
		return ShapeType.BULLET;
	}
	
	public String print() {
		return "B: " + cornerX + " " + cornerY + " " + width + " " + height;
	}
	

}
