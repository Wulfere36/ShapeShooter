package application;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Morris
 *
 */
public class Main extends Application {

	/* just setting up the Canvas and GraphicsContext variables */
	private static Canvas canvas;
	static GraphicsContext gc;
	
	/* initial size values for scene and canvas */
	final int sceneWidth = 1024;
	final int sceneHeight = 800;
	final int minSceneWidth = 400;
	final int minSceneHeight = 400;
	
	int canvasWidth = sceneWidth;
	int buttonsHeight = 45;
	int canvasHeight = sceneHeight-buttonsHeight;
	
	/* value holders for x and y when mouse event begins */
	int evStartX = 0;
	int evStartY = 0;
	int clickCount = 0; // don't really need this one because we won't be doing the click thing anymore
	
	
	/* default animation settings */
	int incX = 3;
	int incY = 3;
	boolean animationStarted = false; // gives an chance to stop the animation
	int AnimationSpeed = 50;
	Timeline timeline = new Timeline(new KeyFrame(Duration.millis(AnimationSpeed),ae->step()));
	
	
	/* default bullet settings */
	int bulletHeight = 20;
	int bulletWidth = 10;
	int bulletX = (int)((canvasWidth - bulletWidth)/2);
	int bulletY = canvasHeight - bulletHeight;
	int bulletSpeed = 20;
	int bulletInc = 20;
	boolean bulletStarted = false;
	Timeline bulletline = new Timeline(new KeyFrame(Duration.millis(bulletSpeed),ae->shotFired()));
	Sprite bullet;
	
	/* holds the type of shape to be drawn when shape button clicked */
	static public ShapeType shapeClick = ShapeType.CIRCLE;
	
	/* ArrayList of all shapes created on the canvas */
	static public ArrayList<Sprite> myShapes = new ArrayList<>();
	
	@Override
	public void start(Stage primaryStage) {

		try {
			
			primaryStage.setTitle("Shape Shooter");
			
			/* user can't make it smaller then this */
			primaryStage.setMinHeight(minSceneHeight+buttonsHeight);
			primaryStage.setMinWidth(minSceneWidth);
			
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, sceneWidth, sceneHeight);
			
			root.setStyle("-fx-background-color: black");
			primaryStage.setScene(scene);
			
			scene.getStylesheets().add("application/application.css");

			/* instantiate the buttons we'll need */
			Button btnCircle = new Button("Circle");
			Button btnSquare = new Button("Square");
			Button btnDisc = new Button("Disc");
			Button btnMove = new Button("Start Animation");
			Button btnSave = new Button("Save");
			Button btnRestore = new Button("Restore");
			Button btnClear = new Button("Clear");
			Button btnFaster = new Button("Speed +");
			Button btnSlower = new Button("Speed -");
			Button btnShoot = new Button("-- FIRE --");
			
			/* set up the styles for the two types of buttons */
			btnShoot.getStyleClass().remove("button");
			btnShoot.getStyleClass().add("fire-button");
			
			canvas = new Canvas(canvasWidth, canvasHeight);
			root.setCenter(canvas);

			/* instantiate a FlowPane and populate it with the buttons */
			FlowPane buttonsPanel = new FlowPane();
			buttonsPanel.setPadding(new Insets(5, 0, 5, 0));
			buttonsPanel.setAlignment(Pos.CENTER);
			buttonsPanel.setHgap(5);
			buttonsPanel.setStyle("-fx-background-color: #8fbc8f");
			buttonsPanel.getChildren().add(btnCircle);
			buttonsPanel.getChildren().add(btnSquare);
			buttonsPanel.getChildren().add(btnDisc);
			buttonsPanel.getChildren().add(btnShoot);
			buttonsPanel.getChildren().add(btnSave);
			buttonsPanel.getChildren().add(btnRestore);
			buttonsPanel.getChildren().add(btnClear);
			buttonsPanel.getChildren().add(btnMove);
			buttonsPanel.getChildren().add(btnSlower);
			buttonsPanel.getChildren().add(btnFaster);
			buttonsPanel.setPrefHeight(buttonsHeight);

			root.setBottom(buttonsPanel);
			
			gc = canvas.getGraphicsContext2D();
			
			/* set up a new bullet. separate from the other shapes so does not get added to ArrayList<myShapes> */
			bullet = new Bullet(bulletX, bulletY, bulletWidth, bulletHeight);
			bullet.drawOn(gc);
			
			
			/* set the animation speed faster or slower */
			btnSlower.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					if (timeline.getRate()>=.5) {
						timeline.setRate(timeline.getRate()-.5);
					}
					System.out.println(timeline.getRate());
				}
			});
			btnFaster.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					timeline.setRate(timeline.getRate()+.5);
					System.out.println(timeline.getRate());
				}
			});
			
			/* three buttons to select type of shape to draw when user drags with the mouse */
			/* will probably change these to ToggleButtons */
			btnCircle.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					shapeClick = ShapeType.CIRCLE;
				}
			});
			btnSquare.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					shapeClick = ShapeType.SQUARE;
				}
			});
			btnDisc.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					shapeClick = ShapeType.DISC;
				}
			});
			
			/* Rest of the buttons */
			/* Starts the animation - also changes prompt and function to stop animation once its started */
			btnMove.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					// Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50),ae->step()));
					timeline.setCycleCount(Animation.INDEFINITE);
					if (!animationStarted) {
						timeline.play();
						animationStarted = true;
						btnMove.setText("Stop Animation");
					} else {
						timeline.stop();
						animationStarted = false;
						btnMove.setText("Start Animation");
					}					
				}
			});
			
			/* Fires the bullet - starts the animation for the bullet */
			btnShoot.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// if animation has not yet started then start it and changed the boolean and text of button 
					bulletline.setCycleCount(Animation.INDEFINITE);
					if (!bulletStarted) {
						bulletline.play();
						bulletStarted = true;
					}
				}
			});

			/* save the ArrayList myShapes to a binary file */
			btnSave.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					FileUtils.save("myShapes.bin", myShapes);
				}
			});
			
			/* stores the binary file contents into the array and repaints the canvas */
			btnRestore.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					myShapes = FileUtils.read("myShapes.bin");
					paintCanvas();
				}
			});

			/* removes all elements in the ArrayList myShapes and clears the canvas */
			btnClear.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					myShapes.removeAll(myShapes);
					gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); 
				}
			});
			
			/* event driven methods */
			/* executes as soon as user presses down on the left mouse button in the canvas area */
			canvas.setOnMousePressed(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent ev) {
					/* store the mouse coordinates X and Y into the evStartX and Y variables */
					evStartX = (int)ev.getX();
					evStartY = (int)ev.getY();
					
					/* draw the shape on the screen with a radius of 1 - gives us the center point for a circle */
					/* pass true for last parameter to indicate it is temporary */
					SpriteUtils.drawShape(evStartX, evStartY, evStartX+1, evStartY+1, shapeClick, true, gc);
				}
				
			});
			/* executes every time the user drags the mouse while holding down the left mouse button */
			canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent ev) {
					/* need current mouse x and y coordinates */
					int mouseX = (int)ev.getX();
					int mouseY = (int)ev.getY();
					
					/* paint the canvas - clear canvas and draw all shapes in the ArrayList myShapes */
					paintCanvas();
					
					/* draw the center point */
					SpriteUtils.drawShape(evStartX, evStartY, evStartX+1, evStartY+1, shapeClick, true, gc);
					
					/* draw the new shape with the new size - pass true for last parameter to indicate it is temporary */
					SpriteUtils.drawShape(evStartX, evStartY, mouseX, mouseY, shapeClick, true, gc);
				}
				
			});
			/* executes when the user lets go of the left mouse button */
			canvas.setOnMouseReleased(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent ev) {
					/* need current mouse coordinates */
					int mouseX = (int)ev.getX();
					int mouseY = (int)ev.getY();
					
					/* paint the canvas - clear canvas and draw all shapes in the ArrayList myShapes */
					paintCanvas();
					
					/* draw the final new shape - pass false for last parameter to indicate it is permanent */
					SpriteUtils.drawShape(evStartX, evStartY, mouseX, mouseY, shapeClick, false, gc);
				}
				
			});
			
			
			/* setup some events for when the scene width and height properties are changed - user resizes the window */
			scene.widthProperty().addListener(new ChangeListener<Number>() {
			    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
		    		canvas.setWidth(newSceneWidth.doubleValue());
		    		canvasWidth = (int) canvas.getWidth();
			    	bulletX = (int)((canvasWidth - bulletWidth)/2);
			    	bullet.setCornerX(bulletX);
			        paintCanvas();
			    }
			});
			scene.heightProperty().addListener(new ChangeListener<Number>() {
			    @Override 
			    public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
		    		canvas.setHeight(newSceneHeight.doubleValue()-buttonsHeight);
			        canvasHeight = (int) canvas.getHeight();
			    	bulletY = canvasHeight - bulletHeight;
			    	bullet.setCornerY(bulletY);
			        paintCanvas();
			    }
			});
			

			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	/* animation of shapes in ArrayList myShapes */
	public void step() {
		/* clear canvas */
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		/* loop through every shape in ArrayList myShapes */
		for (Sprite sprite : myShapes) {
			sprite.moveSprite(incX, incY, canvas, gc);
		}
		bullet.drawOn(gc);
	}

	/* animation of bullet once the fire button has been clicked on */
	public void shotFired() {
		int oldX = bullet.getCornerX();
		int oldY = bullet.getCornerY();
		int h = bullet.getHeight();
		int w = bullet.getWidth();
		
		/* erase the old bullet */
		gc.clearRect(oldX, oldY, w+1, h+1);
		
		/* recalculate the top and left side coordinates */
		oldY -= bulletInc;
		
		/* if bullet has passed the top then reset it to the bottom */
		if (oldY+h<0) {
			resetBullet(oldX, oldY, w+1, h+1);
		} else {
			/* set the new top left corner */
			bullet.setCornerY(oldY);
			Sprite deadSprite = checkCollision(bullet);
			if (deadSprite!=null)
				myShapes.remove(deadSprite);
			paintCanvas();
		}

	}
	
    /* reset the bullet - stops animation, resets coordinates */
	private void resetBullet(int x, int y, int w, int h) {
		gc.clearRect(x, y, w, h);
		bullet.setCornerY(bulletY);
		bulletStarted = false;
		bulletline.stop();
		bullet.drawOn(gc);
    }
    
    /* the main collision detection method */
	private Sprite checkCollision(Sprite bullet) {
    	/* get top coordinates of bullet */
    	int bullX = bullet.getCornerX();
    	int bullY = bullet.getCornerY();
    	int bullW = bullet.getWidth();
    	int bullH = bullet.getHeight();
    	int bullR = (int)bullW/2;
    	
    	/* loop through myShapes and check if coordinates of bullet are inside */
    	for (Sprite sprite : myShapes) {
			int shapeX = sprite.getCornerX();
			int shapeY = sprite.getCornerY();
			int shapeW = sprite.getWidth();
			int shapeH = sprite.getHeight();
			int shapeR = (int)shapeW/2;
    			
			/* do the square check first */
			if ( bullX < shapeX+shapeW+1 && bullX+bullW+3 > shapeX && bullY < shapeY+shapeH+3 ) {
				
	    		/* square sides of bullet and shape are touching - see if its a circle then do a second check */
				if ( sprite.getShape()==ShapeType.CIRCLE || sprite.getShape()==ShapeType.DISC ) {
					
	    			// centre point of bullet
	    			int bullCenterX = bullX + bullR;
	    			int bullCenterY = bullY + bullR;
	    			
	    			// centre of circle
	    			int shapeCenterX = shapeX + shapeR;
	    			int shapeCenterY = shapeY + shapeR;
	    			
	    			/* if the length of the radius between two center points is less then the radius of two objects combined */
	    			int distance = (int) Math.sqrt( ((bullCenterX - shapeCenterX) * (bullCenterX - shapeCenterX)) + ((bullCenterY - shapeCenterY) * (bullCenterY - shapeCenterY)) );
	    			if (distance < bullR + shapeR + 3) {
						resetBullet(bullX, bullY, bullW, bullH);
						System.out.println("Collision!!!");
						return(sprite);
	    			}
	    			
	    		/* just a square so go ahead and do the collision */
	    		} else {
	    			resetBullet(bullX, bullY, bullW, bullH);
	    			System.out.println("Collision!!!");
	    			return(sprite);
	    		}
			}
		}
		return null;
    }
	
	/* loops through all the shapes in myShapes ArrayList and draws the on the canvas */
	public void paintCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for (Sprite sprite : myShapes) {
			sprite.drawOn(gc);
		}
		bullet.drawOn(gc);
		
	}

}
