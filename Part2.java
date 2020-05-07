package ProjectileKS;
import java.awt.Color;
import java.util.ArrayList;
import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.frames.DisplayFrame;

/**
 * This program determines the air resistance constant (B) when a projectile is launched out of a cannon
 * I used the data I collected in my experiment (for this run the ball landed 6.37 mm short of where I expected it to land so I am calculated how much air resistance was affecting it)
 * Data:
 	* 6.37 mm short of 2.532 ---> 2.526
 	* Initial Velocity: 4.8253 (two clicks)
 	* Angle: 60 degrees
 	* Height: 1.01 m
 * @author Kara Siegel
 */

public class Part2 extends AbstractSimulation {
	ArrayList <BallPart2> balls = new ArrayList<BallPart2>(); //makes an arraylist of balls so that I can shoot more than one ball at once

	DisplayFrame frame = new DisplayFrame("X", "Y", "2D Motion"); //Declares the displayframe
	double g = (-9.81); //declares the acceleration due to gravity
	double B=0; //declares the air resistance constant
	double timestep = (0.001); //sets how much time in between each calculation of acceleration, velocity, and position

	double angle=60; //sets the angle to 60 degrees because that is the angle I shot from
	double angleRadians=Math.toRadians(angle); //declares the variable angle radians (the angle in radian form)
	
	double accelerationY=g; //sets the original acceleration of y equal to gravity
	double accelerationX=0; //sets the original acceleration of x equal 

	double initialVelocity=4.8253; //declares the initial velocity
	double velocity=initialVelocity; //declares the new velocity that will eventually change to the original velocity
	double velocityY=velocity*(Math.sin(angleRadians)); //declares the orignal Y velocity as the sin of the angle(in radians) times the velocity
	double velocityX=velocity*(Math.cos(angleRadians)); //declares the original X velocity as the cosine of the angle(in radians) times the velocity

	double positionY=1.01; //sets y-coordinate of the original position to 1 .01 because that is the height of the table I shot from
	double positionX=0; //sets the x-coordinate of the angle position to 0 

	double velocityXNew=velocityX; //sets the new X velocity that changes to the original X velocity
	double velocityYNew=velocityY; //sets the new Y velocity that changes to the original Y velocity
	
	double landingSpot=2.526; //sets the target landing spot (with air resistance) to 2.526 because that was my result

	@Override
	/**
	 * The doStep method runs the simulation and it continues to repeat itself every 1 millisecond so that the simulation changes. In this do step the acceleration, velocity, and position of each ball is changing.
	 * It also check every time the do step runs if a ball has landed in the correct spot, and if it has, I print out the air resistance that that ball has
	 */
	protected void doStep() {		

		for (int i = 0; i < balls.size(); i++) { //for loop so that everything that happens for all the balls in the arraylist
			balls.get(i).setXY(balls.get(i).getPositionX(), balls.get(i).getPositionY()); //sets the original position of each ball to the origin

			//Y MOTION:
			if (balls.get(i).getVelocityY() > 0) { //if the Y velocity of the ball is greater than 0 we want the ball to go up
				balls.get(i).setAccelerationY((g-(balls.get(i).getB()*(balls.get(i).getVelocityY()*balls.get(i).getVelocityY())))); //set the acceleration to gravity minus the air resistance constant times velocity squared
			}
			else if (balls.get(i).getVelocityY() <= 0) {//if the Y velocity of the ball is less than 0 then we want the ball to go down
				balls.get(i).setAccelerationY((g+(balls.get(i).getB()*(balls.get(i).getVelocityY()*balls.get(i).getVelocityY())))); //now set the acceleration to gravity plus the air resistance constant times velocity squared
			}
			
			balls.get(i).setVelocityYNew((balls.get(i).getVelocityY()+(balls.get(i).getAccelerationY()*timestep))); //sets the new velocity of the ball to velocity + acceleration*timestep
			balls.get(i).setPositionY((((balls.get(i).getVelocityYNew())*timestep)+balls.get(i).getPositionY())); //sets the position of the ball using the right hand rule of Riemann sums
			//If I would have used trapezoid rule to determine the new position, I would have done this:
			//balls.get(i).setPositionY((((balls.get(i).getVelocityYNew()+balls.get(i).getVelocityY())/2.0)*timestep)+balls.get(i).getPositionY());
			
			//X MOTION:
			balls.get(i).setAccelerationX(-(balls.get(i).getB()*(balls.get(i).getVelocityX()*balls.get(i).getVelocityX()))); //sets the x acceleration of the ball (I don't need to include the gravity constant because it is in the x direction)
			balls.get(i).setVelocityXNew((balls.get(i).getVelocityX()+(balls.get(i).getAccelerationX()*timestep))); //sets the new velocity of the ball
			balls.get(i).setPositionX(((((balls.get(i).getVelocityXNew())*timestep)+balls.get(i).getPositionX()))); //sets the new position of the ball using the right hand rule
			//If I would have used trapezoid rule to determine the new position, I would have done this:
			//balls.get(i).setPositionX(((((balls.get(i).getVelocityXNew()+balls.get(i).getVelocityX())/2.0)*timestep)+balls.get(i).getPositionX()));
			
			//X AND Y MOTION:
			balls.get(i).setVelocityX(balls.get(i).getVelocityXNew()); //sets the old x velocity to the new x velocity (only would be necessary if I was using the trapezoid rule)
			balls.get(i).setVelocityY(balls.get(i).getVelocityYNew()); //sets the old y velocity to the new y velocity (only would be necessary if I was using the trapezoid rule)

			if (balls.get(i).getPositionY()<=0 && balls.get(i).getPositionX()>(landingSpot-0.001) && balls.get(i).getPositionX()<(landingSpot+0.001)) { //checks if the ball lands in the correct spot
				this.stopSimulation(); //stops the simulation if it lands in the correct spot
				System.out.println("B: " + balls.get(i).getB()); //prints out the air resistance constant of the ball(s) that landed in the correct spot
			}
			else if (balls.get(i).getPositionY()<0) { //if none of the balls land in the correct spot then start over
				balls.get(i).setVelocityX(velocityX); //sets the X velocity back to the original X velocity
				balls.get(i).setVelocityY(velocityY); //sets the Y velocity back to the original Y velocity
				balls.get(i).setPositionX(0); //sets the X position back to 0 (back to the start)
				balls.get(i).setPositionY(1.01); //sets the Y position back to 1.01 because it starts at the top of the table
				balls.get(i).setB(balls.get(449).getB()+(.0001*i)); //increases the air resistance constant for each ball
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

		B = control.getDouble("Air Resistance"); //sets the initial air resistance to what the user inputed in the control panel
		angle = control.getDouble("Angle"); //sets the angle to the angle the user inputed in the control panel
		angleRadians=Math.toRadians(angle);  //converts the angle into radians

		initialVelocity = control.getDouble("Velocity"); //sets the initial velocity to the value that the user inputed in the control panel
		velocity=control.getDouble("Velocity"); //sets the velocity(that is going to change) to the value that the user inputed in the control panel
		velocityY=velocity*(Math.sin(angleRadians)); //declares the orignal Y velocity as the sin of the angle(in radians) times the velocity
		velocityX=velocity*(Math.cos(angleRadians)); //declares the original X velocity as the cosine of the angle(in radians) times the velocity

		positionX = control.getDouble("X"); //sets the original X position to what the user inputed in the control panel
		positionY=control.getDouble("Y"); //sets the original Y position to what the user inputed in the control panel 

		landingSpot = control.getDouble("Landing Spot (round to nearest thousandth)"); //sets the spot where the ball is supposed to land based on user input
		
		DrawableShape table = DrawableShape.createRectangle(-0.5, (positionY/2), 1, positionY); //draws the table that the cannon shoots the ball off of (x-coordinate, y-coordinate, width, height)
		table.color=Color.blue; //sets the color of the table 
		table.edgeColor=Color.black; //sets the edge color of the table
		
		for (int i = 0; i < 450; i++) { //for loop that makes a lot of new balls with different values of air resistance
			//declaring a new ball (has properties: X position, Y position, X acceleration, Y acceleration, X velocity, Y velocity, new X velocity, new X velocity, air resistance constant) 
			BallPart2 b = new BallPart2(positionX, positionY, accelerationX, accelerationY,velocityX, velocityY, velocityXNew, velocityYNew, B);
			
			b.color=Color.yellow; //changes color of ball
			b.setXY(positionX, positionY);
			balls.add(b); //adds the new ball to the arraylist balls
			frame.addDrawable(b); //adds the ball to the display frame
			B=(B+.0001); //changes the air resistance constant by 0.001 for each ball
		}
		
		frame.addDrawable(table); //adds the table to the display frame
		frame.setVisible(true); //makes the display frame visible to the user
		frame.setPreferredMinMaxY(0, 10); //sets the height of the display frame 
		frame.setPreferredMinMaxX(-2,8); //sets the width of the display frame
		
		System.out.println("Air resistance values:");
	}
	/**
	 * The reset method makes it so every time the user runs the program a control panel pops up allowing them to change some of the initial values 
	 * These variables are also set to their default values so the user does not need to change anything
	 */
	public void reset(){ //
		//Allows the user to change all the values so could find the air resistance with other data
		control.setValue("X", 0); //allows user to change the x-coordinate of the original position, sets default to 0
		control.setValue("Y", 1.01); //allows user to change the y-coordinate of the original position in the control panel, sets default to 1.01
		control.setValue("Velocity", 4.8253); //allows user to change the initial velocity that all the balls start with in the control panel, sets default to 4.8253 meters per second
		control.setValue("Angle", 60.0); //allows user to change the smallest angle that a ball can have, sets default to 60 degrees
		control.setValue("Air Resistance", 0.0); //allows user to change the air resistance constant, sets default to 0.
		control.setValue("Landing Spot (round to nearest thousandth)", 2.526); //allows the user to change where the ball is supposed to land
	}

	/**
	 * This method tells the simulation to run
	 * @param args
	 */
	public static void main(String[] args) {
		SimulationControl.createApp(new Part2()); //tells the simulation to run
	}

}
