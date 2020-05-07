package ProjectileKS;
import java.awt.Color;
import java.util.ArrayList;
import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.frames.DisplayFrame;

/**
 * Objective:
 	* Make a program that models how a pool ball moves 
 	* Show how the friction of the surface of a pool table affects the speed and direction the ball moves
 	* Show a collision between two balls
 		* How the movement of each ball changes when it hits another ball 
 	* Show how the balls bounce off the sides of the table when the ball hits them
 * Data:
 	* Size of pool table: 4.5*9 meters
 	* Diameter of pool ball: 2.25 inches = 0.05715m
 	* Mass of pool ball:
 		* Cue ball: 0.17 kg
 		* All other balls: 0.16 kg 
 	* Friction constant of ball on table: 0.2
 * 
 * @author Kara Siegel
 *
 */

public class PoolExtension extends AbstractSimulation{
	ArrayList <Ball> cueBalls = new ArrayList<Ball>(); //makes an arraylist of cue balls so that each cue ball has a slightly different angle
	ArrayList <Ball> balls2 = new ArrayList<Ball>(); //makes an arralyist of 8 balls so that each cue ball has its own separate 8 ball and each 8 ball will only react with one cue ball
	
	DisplayFrame frame = new DisplayFrame("X", "Y", "Pool Table"); //Declares the displayframe
	double friction=(-0.2); //friction constant of the ball on the cloth surface of the pool table
	double timestep=(0.00025); //timestep needs to be really small because otherwise the collisions will not happen at an accurate angle
	
	//DECLARING VARIABLES:
	//Declaring all variables for cue ball:
	double angleCue=0;
	double angleRadiansCue=Math.toRadians(angleCue);
	double initialVelocityCue=0;
	double vXCue=0;
	double vYCue=0;
	double pXCue=0;
	double pYCue=0;
	double aXCue=1;
	double aYCue=1;
	
	//Declaring all variables for the ball that is hit:
	double pXBall2=0;
	double pYBall2=0;
	double aXBall2=0;
	double aYBall2=0;
	double vXBall2=0;
	double vYBall2=0;
	double angleBall2=0;
	double angleRadiansBall2=Math.toRadians(angleBall2);
	
	//distance between centers of the bals:
	double xDistance=0;
	double yDistance=0;
	//original forces:
	double force1=0; //original force of the cue ball
	double force1X=0; 
	double force1Y=0;
	double a=0; //angle 1
	double b=0; //angle 2
	double c=0; //angle 3
	double force2=0; //force of other ball when hit
	double force2X=0;
	double force2Y=0;
	double force3=0; //force of cue ball after it hits the other ball
	double force3X=0;
	double force3Y=0;
			
	
	/**
	 * The doStep repeats so that the simulation will continue changing
	 * This is where the code for the movement of each ball is
	 */
	@Override
	protected void doStep() {
		for (int i = 0; i < cueBalls.size(); i++) {
			cueBalls.get(i).setXY(cueBalls.get(i).getPositionX(), cueBalls.get(i).getPositionY()); //changes the position of the cue ball on the display frame
			balls2.get(i).setXY(balls2.get(i).getPositionX(), balls2.get(i).getPositionY());//changes the position of the other ball on the display frame
			
			xDistance = (balls2.get(i).getPositionX()-cueBalls.get(i).getPositionX()); //distance between the x-coordinates of the centers of the two balls
			yDistance = (balls2.get(i).getPositionY()-cueBalls.get(i).getPositionY()); //distance between the y-coordinates of the centers of the two balls
		//Check if ball hit the other ball: (if the distance between the balls equals the radii added together, then the balls hit)
			if (((xDistance*xDistance)+(yDistance*yDistance)) > ((.2+.2)*(.2+.2) - .03) && ((xDistance*xDistance)+(yDistance*yDistance)) < ((.2+.2)*(.2+.2) + .01)) {  
			//Calculating the force and angle that the first ball hits the second ball:
					force1X=(0.17*cueBalls.get(i).getAccelerationX()); //finds the x component of the original force of the cue ball by doing acceleration*mass
					force1Y=(0.17*cueBalls.get(i).getAccelerationY()); //finds the y component of the original force by doing acceleration*mass
					force1=Math.sqrt((force1X*force1X)+(force1Y*force1Y)); //finds the original force by doing the pythagorean theorem with the x and y components
					a = (Math.atan2(force1Y, force1X)); //(a is in radians) finds the angle of the force
					b = (Math.atan2(yDistance, xDistance)); // finds the angle of the center of the two balls (same as force of new ball)
					c = Math.abs((a-b)); // finds the angle between the original force and the force of the new ball
					force2 = (Math.cos(c)*force1); // finds the force of the second ball
					force2X = ((Math.cos(b)*force2)); // finds the x component of the force of the second ball
					force2Y = ((Math.sin(b)*force2)); //finds the y component of the force of the second ball
					force3X=((force1X-(force2X))); //finds the x component of the new force of the cue ball
					force3Y=Math.abs((force1Y+(force2Y))); //finds the y component of the new force of the cue ball
					force3=Math.sqrt((force3X*force3X)+(force3Y*force3Y)); // finds the new force of the cue ball
			//BALL 2 MOTION AFTER COLLISION:
			//X Motion:
				balls2.get(i).setAccelerationX(force2X/0.16); //finds the new x acceleration based on the force
				balls2.get(i).setVelocityX((balls2.get(i).getVelocityX()+(balls2.get(i).getAccelerationX()*timestep))); //changes x velocity based on new acceleration
				balls2.get(i).setPositionX((((balls2.get(i).getVelocityX())*timestep)+balls2.get(i).getPositionX())); //changes x position based on new velocity
			//Y Motion:
				balls2.get(i).setAccelerationY(force2Y/0.16); //changes y acceleration based on force calculated
				balls2.get(i).setVelocityY((balls2.get(i).getVelocityY()+(balls2.get(i).getAccelerationY()*timestep))); //changes y velocity based on new acceleration
				balls2.get(i).setPositionY((((balls2.get(i).getVelocityY())*timestep)+balls2.get(i).getPositionY())); //changes y position based on new velocity
				
			//CUE BALL AFTER COLLISION:
			//X Motion:
				cueBalls.get(i).setAccelerationX(force3X/0.17);
				cueBalls.get(i).setVelocityX((cueBalls.get(i).getVelocityX()+(cueBalls.get(i).getAccelerationX()*timestep))); 
				cueBalls.get(i).setPositionX((((cueBalls.get(i).getVelocityX())*timestep)+cueBalls.get(i).getPositionX()));
				
			//Y Motion:
				cueBalls.get(i).setAccelerationY(force3Y/0.17);
				cueBalls.get(i).setVelocityY((cueBalls.get(i).getVelocityY()+(cueBalls.get(i).getAccelerationY()*timestep))); 
				cueBalls.get(i).setPositionY((((cueBalls.get(i).getVelocityY())*timestep)+cueBalls.get(i).getPositionY()));
			}
			
		//if the ball is in the top right pocket:
			else if (balls2.get(i).getPositionX() > 1.85 && balls2.get(i).getPositionY() > 4.1 && balls2.get(i).getPositionY()<4.35 && balls2.get(i).getPositionX() < 2.15) {
				this.stopSimulation();
				System.out.println("CONGRATS YOU GOT A BALL IN THE TOP RIGHT POCKET!");
				System.out.println("Angle that cue ball was hit at: " + angleCue);
			}
		//if the ball is in the bottom right pocket:
			else if (balls2.get(i).getPositionX() > 1.85 && balls2.get(i).getPositionX()<2.15 && balls2.get(i).getPositionY()<-4.1 && balls2.get(i).getPositionY()>-4.4) {
				this.stopSimulation();
				System.out.println("CONGRATS YOU GOT A BALL IN THE BOTTOM RIGHT POCKET!");
				System.out.println("Angle that cue ball was hit at: " + angleCue);
			}
		//if the ball is in the bottom left pocket:
			else if (balls2.get(i).getPositionY()<-4.1 && balls2.get(i).getPositionY()>-4.4 && balls2.get(i).getPositionX()<-1.85 && balls2.get(i).getPositionX()>-2.15) {
				this.stopSimulation();
				System.out.println("CONGRATS YOU GOT A BALL IN THE BOTTOM LEFT POCKET!");
				System.out.println("Angle that cue ball was hit at: " + angleCue);
			}
		//if the ball is in the top left pocket:
			else if (balls2.get(i).getPositionX()<-1.85 && balls2.get(i).getPositionX()>-2.15 && balls2.get(i).getPositionY()>4.1 && balls2.get(i).getPositionY()<4.35) {
				this.stopSimulation();
				System.out.println("CONGRATS YOU GOT A BALL IN THE TOP LEFT POCKET!");
				System.out.println("Angle that cue ball was hit at: " + angleCue);
			}
		//if the ball touches either side wall, then multiply the X velocity by -1 (so the direction of the ball changes)
			else if (balls2.get(i).getPositionX()<=-2.1 || balls2.get(i).getPositionX()>=2.2) {
				balls2.get(i).setVelocityX(-1*balls2.get(i).getVelocityX()); //change X velocity so that it is going in the other direction
				balls2.get(i).setAccelerationX(friction*balls2.get(i).getVelocityX()*balls2.get(i).getVelocityX()); //find new acceleration based on new velocity
				balls2.get(i).setVelocityX((balls2.get(i).getVelocityX()+(balls2.get(i).getAccelerationX()*timestep))); //sets the new velocity based on the new acceleration
				balls2.get(i).setPositionX((((balls2.get(i).getVelocityX())*timestep)+balls2.get(i).getPositionX())); //change the position of the ball based on the new velocity
				
				//the Y motion code is the same as if the ball did not hit a wall (because only the X is affected when it hits a side wall)
				if (balls2.get(i).getVelocityY()>=0) { //if the Y velocity is greater than 0 then the acceleration should be negative because the friction on the surface slows the ball down 
					balls2.get(i).setAccelerationY(friction*balls2.get(i).getVelocityY()*balls2.get(i).getVelocityY());
				}
				else if (balls2.get(i).getVelocityY()<0) { //if the Y velocity is less than 0 then the acceleration should be positive
					balls2.get(i).setAccelerationY((-1)*friction*balls2.get(i).getVelocityY()*balls2.get(i).getVelocityY());
				}
				balls2.get(i).setVelocityY((balls2.get(i).getVelocityY()+(balls2.get(i).getAccelerationY()*timestep))); //sets velocity based on new acceleration
				balls2.get(i).setPositionY((((balls2.get(i).getVelocityY())*timestep)+balls2.get(i).getPositionY())); //sets the position using right hand rule of Riemann Sums
			}
		//if the cue ball touches either side wall
			else if (cueBalls.get(i).getPositionX()<=-2.1 || cueBalls.get(i).getPositionX()>=2.2) {
				cueBalls.get(i).setVelocityX(-1*cueBalls.get(i).getVelocityX()); //change X velocity so that it goes in the opposite direction
				cueBalls.get(i).setAccelerationX(friction*cueBalls.get(i).getVelocityX()*cueBalls.get(i).getVelocityX()); //find new acceleration based on new velocity
				cueBalls.get(i).setVelocityX((cueBalls.get(i).getVelocityX()+(cueBalls.get(i).getAccelerationX()*timestep))); //sets the new velocity of the ball based on new acceleration
				cueBalls.get(i).setPositionX((((cueBalls.get(i).getVelocityX())*timestep)+cueBalls.get(i).getPositionX())); //change the position of the ball based on new velocity
				
				//The Y motion code is the same as if the ball did not hit a wall
				if (cueBalls.get(i).getVelocityY()>=0) {
					cueBalls.get(i).setAccelerationY(friction*cueBalls.get(i).getVelocityY()*cueBalls.get(i).getVelocityY());
				}
				else if (cueBalls.get(i).getVelocityY()<0) {
					cueBalls.get(i).setAccelerationY((-1)*friction*cueBalls.get(i).getVelocityY()*cueBalls.get(i).getVelocityY());
				}
				cueBalls.get(i).setVelocityY((cueBalls.get(i).getVelocityY()+(cueBalls.get(i).getAccelerationY()*timestep))); //sets the new velocity of the ball to velocity + acceleration*timestep
				cueBalls.get(i).setPositionY((((cueBalls.get(i).getVelocityY())*timestep)+cueBalls.get(i).getPositionY()));
			}
		//if the cue ball touches the top or bottom walls	
			else if (cueBalls.get(i).getPositionY()<=-4.37 || cueBalls.get(i).getPositionY()>=4.37) {
				//Y Motion:
				cueBalls.get(i).setVelocityY(-1*cueBalls.get(i).getVelocityY()); //changes velocity so ball moves in opposite Y direction
				cueBalls.get(i).setAccelerationY(friction*cueBalls.get(i).getVelocityY()*cueBalls.get(i).getVelocityY());
				cueBalls.get(i).setVelocityY((cueBalls.get(i).getVelocityY()+(cueBalls.get(i).getAccelerationY()*timestep))); //sets the new velocity of the ball to velocity + acceleration*timestep
				cueBalls.get(i).setPositionY((((cueBalls.get(i).getVelocityY())*timestep)+cueBalls.get(i).getPositionY()));
				
				//X Motion (same as normal code)
				if (cueBalls.get(i).getVelocityX()>=0) { //if the X velocity is greater than or equal to 0 then the acceleration should be negative so it slows the ball down
					cueBalls.get(i).setAccelerationX(friction*cueBalls.get(i).getVelocityX()*cueBalls.get(i).getVelocityX());
				}
				else if (cueBalls.get(i).getVelocityX()<0) { //if the X velocity is less than 0 then the acceleration should be positive
					cueBalls.get(i).setAccelerationX((-1)*friction*cueBalls.get(i).getVelocityX()*cueBalls.get(i).getVelocityX());
				}
				cueBalls.get(i).setVelocityX((cueBalls.get(i).getVelocityX()+(cueBalls.get(i).getAccelerationX()*timestep))); //sets the new velocity of the ball based on the acceleration
				cueBalls.get(i).setPositionX((((cueBalls.get(i).getVelocityX())*timestep)+cueBalls.get(i).getPositionX())); //sets the position of the ball using the right hand rule of Riemann sums
			}
		//if the other ball touches either the top or bottom walls
			else if (balls2.get(i).getPositionY()<=-4.37 || balls2.get(i).getPositionY()>=4.37) {
				//Y Motion:
				balls2.get(i).setVelocityY(-1*balls2.get(i).getVelocityY());
				balls2.get(i).setAccelerationY(friction*balls2.get(i).getVelocityY()*balls2.get(i).getVelocityY());
				balls2.get(i).setVelocityY((balls2.get(i).getVelocityY()+(balls2.get(i).getAccelerationY()*timestep)));
				balls2.get(i).setPositionY((((balls2.get(i).getVelocityY())*timestep)+balls2.get(i).getPositionY()));
				
				//X Motion:
				if (balls2.get(i).getVelocityX()>=0) { //if the X velocity is greater than 0 then the acceleration should be negative
					balls2.get(i).setAccelerationX(friction*balls2.get(i).getVelocityX()*balls2.get(i).getVelocityX());
				}
				else if (balls2.get(i).getVelocityX()<0) { //if the X velocity is greater than 0 then the acceleration should be positive 
					balls2.get(i).setAccelerationX((-1)*friction*balls2.get(i).getVelocityX()*balls2.get(i).getVelocityX());
				}
				balls2.get(i).setVelocityX((balls2.get(i).getVelocityX()+(balls2.get(i).getAccelerationX()*timestep))); //sets the new velocity of the ball to velocity + acceleration*timestep
				balls2.get(i).setPositionX((((balls2.get(i).getVelocityX())*timestep)+balls2.get(i).getPositionX()));
			}
			else { //if cue ball doesn't hit the first ball
			//CUE BALL:
			//Y Motion:	
				if (cueBalls.get(i).getVelocityY()>=0) { //if the Y velocity is greater than 0 then the acceleration should be negative because the friction on the surface slows the ball down (the friction coefficient is defined as negative)
					cueBalls.get(i).setAccelerationY(friction*cueBalls.get(i).getVelocityY()*cueBalls.get(i).getVelocityY());
				}
				else if (cueBalls.get(i).getVelocityY()<0) { //if the Y velocity is less than 0 then the acceleration should be positive because the friction should slow the the ball down
					cueBalls.get(i).setAccelerationY((-1)*friction*cueBalls.get(i).getVelocityY()*cueBalls.get(i).getVelocityY()); //multiply by -1 so that acceleration is positive
				}
				cueBalls.get(i).setVelocityY((cueBalls.get(i).getVelocityY()+(cueBalls.get(i).getAccelerationY()*timestep))); //sets the new velocity of the ball to velocity based on the acceleration
				cueBalls.get(i).setPositionY((((cueBalls.get(i).getVelocityY())*timestep)+cueBalls.get(i).getPositionY())); //sets the position of the ball using the right hand rule of Riemann sums
			//X Motion:
				if (cueBalls.get(i).getVelocityX()>=0) { //if the X velocity is greater than or equal to 0 then the acceleration should be negative so it slows the ball down
					cueBalls.get(i).setAccelerationX(friction*cueBalls.get(i).getVelocityX()*cueBalls.get(i).getVelocityX());
				}
				else if (cueBalls.get(i).getVelocityX()<0) { //if the X velocity is less than 0 then the acceleration should be positive
					cueBalls.get(i).setAccelerationX((-1)*friction*cueBalls.get(i).getVelocityX()*cueBalls.get(i).getVelocityX());
				}
				cueBalls.get(i).setVelocityX((cueBalls.get(i).getVelocityX()+(cueBalls.get(i).getAccelerationX()*timestep))); //sets the new velocity of the ball based on the acceleration
				cueBalls.get(i).setPositionX((((cueBalls.get(i).getVelocityX())*timestep)+cueBalls.get(i).getPositionX())); //sets the position of the ball using the right hand rule of Riemann sums

			//BALL 2:
			//Y Motion:
				if (balls2.get(i).getVelocityY()>=0) { //if the Y velocity is greater than 0 then the acceleration should be negative because the friction on the surface slows the ball down 
					balls2.get(i).setAccelerationY(friction*balls2.get(i).getVelocityY()*balls2.get(i).getVelocityY());
				}
				else if (balls2.get(i).getVelocityY()<0) { //if the Y velocity is less than 0 then the acceleration should be positive
					balls2.get(i).setAccelerationY((-1)*friction*balls2.get(i).getVelocityY()*balls2.get(i).getVelocityY());
				}
				balls2.get(i).setVelocityY((balls2.get(i).getVelocityY()+(balls2.get(i).getAccelerationY()*timestep))); //sets velocity based on new acceleration
				balls2.get(i).setPositionY((((balls2.get(i).getVelocityY())*timestep)+balls2.get(i).getPositionY())); //sets the position using right hand rule of Riemann Sums
			//X Motion:
				if (balls2.get(i).getVelocityX()>=0) { //if the X velocity is greater than 0 then the acceleration should be negative
					balls2.get(i).setAccelerationX(friction*balls2.get(i).getVelocityX()*balls2.get(i).getVelocityX());
				}
				else if (balls2.get(i).getVelocityX()<0) { //if the X velocity is greater than 0 then the acceleration should be positive 
					balls2.get(i).setAccelerationX((-1)*friction*balls2.get(i).getVelocityX()*balls2.get(i).getVelocityX());
				}
				balls2.get(i).setVelocityX((balls2.get(i).getVelocityX()+(balls2.get(i).getAccelerationX()*timestep))); //sets the new velocity of the ball to velocity + acceleration*timestep
				balls2.get(i).setPositionX((((balls2.get(i).getVelocityX())*timestep)+balls2.get(i).getPositionX()));
			}
		}
	}
	
	/**
	 * The initialize method makes it so that when the user presses initialize on the control panel everything gets set up
	 * This is where the the base values for all the variables are set, where I declare the new balls that go into the arraylist, and where I tell the display frame to appear
	 * This is the step before starting the simulation
	 */
	public void initialize() {
		this.setDelayTime(1); //sets how often the doStep runs
		angleCue = control.getDouble("Angle Cue Ball"); //sets the initial angle of the cue ball to the angle the user inputed in the control panel
		initialVelocityCue = control.getDouble("Initial Velocity Cue Ball"); //sets the initial velocity of the cue ball to the value the user inputed
		pXCue = control.getDouble("X Cue Ball"); //sets the X position of the cue ball based on user input
		pYCue = control.getDouble("Y Cue Ball"); //sets the Y position of the cue ball based on user input
		pXBall2 = control.getDouble("X Ball 2"); //sets the X position of the second ball based on user input
		pYBall2 = control.getDouble("Y Ball 2"); //sets the Y position of the second ball based on user input
		DrawableShape table = DrawableShape.createRectangle(0, 0, 4.5, 9.0); //makes the table (x-coordinate, y-coordinate, width, height) 
		table.color=Color.green; //makes the table green (like the surface of a pool table :) )
		table.edgeColor=Color.black; //makes the edges of the table black
		//declaring pockets
		DrawableShape pocket1 = DrawableShape.createRectangle(2, 4.25, .5, .5); //draws the top right pocket
		DrawableShape pocket2 = DrawableShape.createRectangle(-2, 4.25, .5, .5); //draws the top left pocket
		DrawableShape pocket3 = DrawableShape.createRectangle(-2, -4.25, .5, .5); //draws the bottom left pocket
		DrawableShape pocket4 = DrawableShape.createRectangle(2, -4.25, .5, .5); //draws the bottom right pocket
		//setting color of pockets
		pocket1.color=Color.gray;  
		pocket2.color=Color.gray;
		pocket3.color=Color.gray;
		pocket4.color=Color.gray;
		//adds table and pockets to the display frame so that user can see them
		frame.addDrawable(table);
		frame.addDrawable(pocket1);
		frame.addDrawable(pocket2); 
		frame.addDrawable(pocket3);
		frame.addDrawable(pocket4);
		frame.setPreferredMinMax(-5, 5, -6, 6); //sets the preferred min and max of the plot frame (xmin, xmax, ymin, ymax)
		frame.setVisible(true); //shows the plotframe
		
		//adding cue balls to the arraylist of cue balls:
			for (int i = 0; i < 50; i++) {
				angleRadiansCue=Math.toRadians(angleCue); //converts the angle to radians
				vXCue = (initialVelocityCue*Math.cos(angleRadiansCue)); //finds the new x velocity based on the angle(in radians)
				vYCue = (initialVelocityCue*Math.sin(angleRadiansCue)); //finds the new y velocity based on the angle (in radians)
				
				Ball b = new Ball(pXCue, pYCue, aXCue, aYCue, initialVelocityCue, vXCue, vYCue, vXCue, vYCue, angleCue); //declares the ball
				b.color=Color.white; //sets the ball color to white
				b.pixRadius=6; //defines the radius of the ball
				b.setXY(pXCue, pYCue); //sets the x and y coordinates of the ball
				cueBalls.add(b); //adds the new ball to the arraylist balls
				frame.addDrawable(b); //adds the ball to the display frame
				angleCue+=.3; //changes the angle so that each cue ball shot will have a different angle
			}
		//adding the other balls to the arraylist of balls (balls2):
			for (int i = 0; i < 50; i++) {
				Ball b2 = new Ball(pXBall2, pYBall2, aXBall2, aYBall2, 0, vXBall2, vYBall2, vXBall2, vYBall2, angleBall2); //declares the ball
				b2.color=Color.black; //sets the ball color to black 
				b2.pixRadius=6; //defines the radius of the ball
				b2.setXY(pXBall2, pYBall2); //sets the x and y coordinates of the ball
				balls2.add(b2); //adds the ball to the arraylist of balls
				frame.addDrawable(b2); //adds the ball to the display frame
			}
	}
	
	/**
	 * The reset method makes it so every time the user runs the program a control panel pops up allowing them to change some of the initial values 
	 * These variables are also set to their default values so the user does not need to change anything
	 */
	public void reset() {
		control.setValue("X Cue Ball", 0); //allows user to change the x-coordinate of the original position of the cue ball, sets default to 0
		control.setValue("Y Cue Ball", -3); //allows user to change the y-coordinate of the original position of the cue ball in the control panel, sets default to -3
		control.setValue("Initial Velocity Cue Ball", 20); //allows user to change the initial velocity that all the balls start with, sets default to 20 meters per second
		control.setValue("Angle Cue Ball", 87); //allows user to change the smallest angle that a ball can have, sets default to 88 degrees
		control.setValue("X Ball 2", .5); //allows the user to change the original x position of the second ball, sets default to 0.5
		control.setValue("Y Ball 2", 2); //allows the user to change the original y position of the second ball, sets default to 2
	}
	
	/**
	 * This method tells the simulation to run
	 * @param args
	 */
	public static void main(String[] args) {
		SimulationControl.createApp(new PoolExtension()); //tells the simulation to run
	}
}
