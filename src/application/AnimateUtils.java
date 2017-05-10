package application;

import javafx.scene.canvas.Canvas;

public class AnimateUtils {

	/* calculate the direction the sprite should be moving */
	public static void calcDirection(int topX, int topY, int bottomX, int bottomY, Canvas canvas, Sprite sprite) {
		if ( (bottomX >= canvas.getWidth() && sprite.getDirectionX()>0) || (topX <= 0 && sprite.getDirectionX()<0) )
			sprite.changeDirectionX();
		if ( (bottomY >= canvas.getHeight() && sprite.getDirectionY()>0) || (topY <= 0 && sprite.getDirectionY()<0) )
			sprite.changeDirectionY();
	}
	

}
