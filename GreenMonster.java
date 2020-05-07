package ProjectileKS;
import java.awt.Color;
import java.util.ArrayList;
import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.*;
import org.opensourcephysics.frames.*;

/**
 * This project models the 2-D motion of a baseball being hit in Fenway Park
 * I am trying to determine the minimum velocity a ball needs in order to get over the Green Monster (11 meters tall) when it is hit at a height of 1 meter
 * I also find the corresponding angle for the ball that clears the wall.
 * 
 * @author Kara Siegel
 *
 */
public class GreenMonster extends AbstractSimulation {
	ArrayList <Ball> balls = new ArrayList<Ball>(); //makes an arraylist of balls so that I can shoot more than one ball at once

	DisplayFrame frame = new DisplayFrame("X", "Y", "Green Monster"); //Declares the displayframe
	double time=0; //sets the original time to 0
	double g = (-9.81); //declares the acceleration due to gravity
	double B=(0.02); //declares the air resistance constant
	double timestep = (0.1); //sets how much time in between each calculation of acceleration, velocity, and position

	double angle=20; //sets the original angle to 20 degrees
	double angleRadians=0; //declares the variable angle radians (the angle in radian form)
	
	double accelerationY=g; //sets the original acceleration of y equal to gravity
	double accelerationX=0; //sets the original acceleration of x equal 

	double initialVelocity=0; //declares the initial velocity
	double velocity=initialVelocity; //declares the new velocity that will eventually change to the original velocity
	double velocityY=velocity*(Math.sin(angleRadians)); //declares the orignal Y velocity as the sin of the angle(in radians) times the velocity
	double velocityX=velocity*(Math.cos(angleRadians)); //declares the original X velocity as the cosine of the angle(in radians) times the velocity

	double positionY=1; //sets y-coordinate of the original position to 1 
	double positionX=0; //sets the x-coordinate of the angle position to 0 

	double velocityXNew=velocityX; //sets the new X velocity that changes to the original X velocity
	double velocityYNew=velocityY; //sets the new Y velocity that changes to the original Y velocity

	@Override
	/**
	 * The doStep method runs the simulation and it continues to repeat itself every 1 millisecond so that the simulation changes. In this do step the acceleration, velocity, and position of each ball is changing.
	 * It also check every time the do step runs if a ball has made it over a wall or if the balls have hit the ground. 
	 */
	protected void doStep() {		

		for (int i = 0; i < balls.size(); i++) { //for loop so that everything that happens for all the balls in the arraylist
			balls.get(i).setXY(balls.get(i).getPositionX(), balls.get(i).getPositionY()); //sets the original position of each ball to the origin

			//Y MOTION:
			if (balls.get(i).getVelocityY() > 0) { //if the Y velocity of the ball is greater than 0 we want the ball to go up
				balls.get(i).setAccelerationY((g-(B*(balls.get(i).getVelocityY()*balls.get(i).getVelocityY())))); //set the acceleration to gravity minus the air resistance constant times velocity squared
			}
			else if (balls.get(i).getVelocityY() <= 0) {//if the Y velocity of the ball is less than 0 then we want the ball to go down
				balls.get(i).setAccelerationY((g+(B*(balls.get(i).getVelocityY()*balls.get(i).getVelocityY())))); //now set the acceleration to gravity plus the air resistance constant times velocity squared
			}
			
			balls.get(i).setVelocityYNew((balls.get(i).getVelocityY()+(balls.get(i).getAccelerationY()*timestep))); //sets the new velocity of the ball to velocity + acceleration*timestep
			balls.get(i).setPositionY((((balls.get(i).getVelocityYNew())*timestep)+balls.get(i).getPositionY())); //sets the position of the ball using the right hand rule of Riemann sums
			//If I would have used trapezoid rule to determine the new position, I would have done this:
			//balls.get(i).setPositionY((((balls.get(i).getVelocityYNew()+balls.get(i).getVelocityY())/2.0)*timestep)+balls.get(i).getPositionY());
			
			//X MOTION:
			balls.get(i).setAccelerationX(-(B*(balls.get(i).getVelocityX()*balls.get(i).getVelocityX()))); //sets the x acceleration of the ball (I don't need to include the gravity constant because it is in the x direction)
			balls.get(i).setVelocityXNew((balls.get(i).getVelocityX()+(balls.get(i).getAccelerationX()*timestep))); //sets the new velocity of the ball
			balls.get(i).setPositionX(((((balls.get(i).getVelocityXNew())*timestep)+balls.get(i).getPositionX()))); //sets the new position of the ball using the right hand rule
			//If I would have used trapezoid rule to determine the new position, I would have done this:
			//balls.get(i).setPositionX(((((balls.get(i).getVelocityXNew()+balls.get(i).getVelocityX())/2.0)*timestep)+balls.get(i).getPositionX()));
			
			//X AND Y MOTION:
			balls.get(i).setVelocityX(balls.get(i).getVelocityXNew()); //sets the old x velocity to the new x velocity (only would be necessary if I was using the trapezoid rule)
			balls.get(i).setVelocityY(balls.get(i).getVelocityYNew()); //sets the old y velocity to the new y velocity (only would be necessary if I was using the trapezoid rule)

			if (balls.get(i).getPositionY()>=11 && balls.get(i).getPositionX()>=100) { //tells the computer what to do if a ball went over the wall
				this.stopSimulation(); //stops the simulation so that I can determine the minimum velocity that a ball needs
				System.out.println("Minimum Velocity: " + balls.get(i).getInitialVelocity() + "m/s"); //prints out the minimum velocity of the first ball that makes it over the wall
				System.out.println("Angle: " + balls.get(i).getAngle() + " degrees"); //prints out the angle of the first ball that made it over the wall
			}
			else if (balls.get(i).getPositionY()<0) { //tells the computer what to do if none of the balls make it over the wall
				balls.get(i).setInitialVelocity((balls.get(i).getInitialVelocity()+.1)); //increase the initial velocity of each ball by .1
				angleRadians=Math.toRadians(balls.get(i).getAngle()); //converts the angle of each ball into radians (do this so that I can later find the X and Y velocities of the ball)
				balls.get(i).setVelocityX((balls.get(i).getInitialVelocity()*Math.cos(angleRadians))); //finds the new X velocity of the ball based on the new initial velocity
				balls.get(i).setVelocityY((balls.get(i).getInitialVelocity()*Math.sin(angleRadians))); //finds the new Y velocity of the ball based on the new initial velocity
				balls.get(i).setPositionY(1); //sets the y position of the ball back to 0 (so the ball starts at the origin again)
				balls.get(i).setPositionX(0); //sets the x position of the ball back to 0 
			}
		}	
	}

	/**
	 * The initialize method makes it so that when the user presses initialize on the control panel everything gets set up
	 * This is where the the base values for all the variables are set, where I declare the new balls that go into the arraylist, and where I tell the display frame to appear
	 * This is the step before starting the simulation
	 */
	public void initialize() {		
		this.setDelayTime(1); //sets how often the doStep repeats (every 1 millisecond)

		B = control.getDouble("Air Resistance"); //sets the air resistance constant to what the user inputed in the control panel
		angle = control.getDouble("Angle"); //sets the initial angle to the angle the user inputed in the control panel
		angleRadians=Math.toRadians(angle);  //converts the angle into radianss

		initialVelocity = control.getDouble("Velocity"); //sets the initial velocity to the value that the user inputed in the control panel
		velocity=control.getDouble("Velocity"); //sets the velocity(that is going to change) to the value that the user inputed in the control panel

		positionX = control.getDouble("X"); //sets the original X position to what the user inputed in the control panel
		positionY=control.getDouble("Y"); //sets the original Y position to what the user inputed in the control panel 

		for (int i = 0; i < 650; i++) { //for loop that makes 650 new balls each with a different angle (angle goes from 20 to 85 increasing by 0.1)
			angleRadians=Math.toRadians(angle); //converts the angle to radians
			velocityX =(initialVelocity*Math.cos(angleRadians)); //finds the new x velocity based on the angle(in radians)
			velocityY=(initialVelocity*Math.sin(angleRadians)); //finds the new y velocity based on the angle (in radians)
			velocityXNew=velocityX; //sets the new X velocity to the old X velocity
			velocityYNew=velocityY; //sets the new Y velocity to the old Y velocity

			//declaring a new ball (has properties: X position, Y position, X acceleration, Y acceleration, initial velocity, X velocity, Y velocity, new X velocity, new X velocity, angle) 
			Ball b = new Ball(positionX, positionY, accelerationX, accelerationY,initialVelocity, velocityX, velocityY, velocityXNew, velocityYNew, angle);
			
			b.color=Color.blue; //changes color of ball
			b.setXY(positionX, positionY);
			balls.add(b); //adds the new ball to the arraylist balls
			frame.addDrawable(b); //adds the ball to the display frame

			angle+=.1; //changes the angle by .1 so the next ball created will have a new angle
		}
		frame.setVisible(true); //makes the display frame visible to the user

		//the height of the wall needs to be 10 meters because the reference frame places the batter at 0 so the wall is 10 meters higher than the batter
		DrawableShape wall = DrawableShape.createRectangle(102, 5.5, 4, 11); //makes the wall (x-coordinate, y-coordinate, width, height) 
		wall.color=Color.green; //changes the color of the wall
		wall.edgeColor=Color.green; //changes the color of the outline of the wall
		frame.addDrawable(wall); //adds the green monster wall to the display frame
		frame.setPreferredMinMax(0, 110, 0, 12); //sets the size of the display frame (makes x go from 0 to 110 and y go from 0 to 100)
	}
	
	/**
	 * The reset method makes it so every time the user runs the program a control panel pops up allowing them to change some of the initial values 
	 * These variables are also set to their default values so the user does not need to change anything
	 */
	public void reset(){ //
		control.setValue("X", 0); //allows user to change the x-coordinate of the original position, sets default to 0
		control.setValue("Y", 1); //allows user to change the y-coordinate of the original position in the control panel, sets default to 1
		control.setValue("Velocity", 80); //allows user to change the initial velocity that all the balls start with in the control panel, sets default to 80 meters per second
		control.setValue("Angle", 20); //allows user to change the smallest angle that a ball can have, sets default to 20 degrees
		control.setValue("Air Resistance", 0.02); //allows user to change the air resistance constant, sets default to 0.02
	}


	/**
	 * This is the method that runs the simulation
	 * @param args
	 */
	public static void main(String[] args) {
		SimulationControl.createApp(new GreenMonster()); //tells the simulation to run
	}

}
