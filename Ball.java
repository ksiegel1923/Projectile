package ProjectileKS;
import org.opensourcephysics.display.Circle;

public class Ball extends Circle{ //makes a new class ball so that there can be many different balls inside the arraylist balls (it needs to extend circle so that each new ball created shows up on the display frame as a circle)
	//Properties of Ball:
	//declare all the variables of each ball so that they will all have these properties
	double positionX=0; 
	double positionY=1;
	double accelerationX=0;
	double accelerationY=0;
	double initialVelocity=0;
	double velocityXNew=0;
	double velocityYNew=0;
	double velocityX=0;
	double velocityY=0;
	double angle=0;
	
	
	//All the get and set methods for each variable of the ball
	//The get method allows the computer to know the the value of that property and the set method allows you to change the value of the variable
	/**
	 * This method allows the computer to get the X position of the ball (and use that value)
	 * @return	returns the X position of the ball
	 */
	public double getPositionX() {
		return positionX; //returns the X position
	}
	
	/**
	 * This methods allows the computer to change the X position of the ball
	 * @param positionX		takes the new X position as a parameter and sets that to the variable positionX
	 */
	public void setPositionX(double positionX) {
		this.positionX = positionX; //sets the new X position
	}
	
	/**
	 * This method allows the computer to get the Y position of the ball (and use that value)
	 * @return	returns the Y position of the ball
	 */
	public double getPositionY() {
		return positionY;
	}
	
	/**
	 * This methods allows the computer to change the Y position of the ball
	 * @param positionY		takes the new Y position as a parameter and sets that to the variable positionY
	 */
	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}
	
	/**
	 * This method gets the acceleration (in the X direction) of the ball
	 * @return	returns the X acceleration
	 */
	public double getAccelerationX() {
		return accelerationX;
	}
	
	/**
	 * This methods changes the acceleration (in the X direction)
	 * @param accelerationX		takes in the new X acceleration and sets the variable accelerationX to that new value
	 */
	public void setAccelerationX(double accelerationX) {
		this.accelerationX = accelerationX;
	}
	
	/**
	 * This method gets the acceleration (in the Y direction) of the ball
	 * @return	returns the Y acceleration
	 */
	public double getAccelerationY() {
		return accelerationY;
	}
	
	/**
	 * This method changes the acceleration (in the Y direction)
	 * @param accelerationY	takes in the new Y acceleration and sets the variable accelerationY to that new value
	 */
	public void setAccelerationY(double accelerationY) {
		this.accelerationY = accelerationY;
	}
	
	/**
	 * This methods gets the initial velocity of the ball
	 * @return	returns the initial velocity of the ball
	 */
	public double getInitialVelocity() {
		return initialVelocity;
	}
	
	/**
	 * This method changes the initial velocity of the ball
	 * @param initialVelocity	takes in the value of initial velocity and sets the variable initialVelocity to this new value
	 */
	public void setInitialVelocity(double initialVelocity) {
		this.initialVelocity= initialVelocity;
	}
	
	/**
	 * This methods gets the new X velocity of the ball
	 * @return	returns the new X velocity of the ball
	 */
	public double getVelocityXNew() {
		return velocityXNew;
	}
	
	/**
	 * This methods changes the new X velocity of the ball
	 * @param velocityXNew	takes in the value of the new X velocity and sets the variable velocityXNew to this new value
	 */
	public void setVelocityXNew(double velocityXNew) {
		this.velocityXNew = velocityXNew;
	}
	
	/**
	 * This methods gets the new Y velocity of the ball
	 * @return	returns the new Y velocity of the ball
	 */
	public double getVelocityYNew() {
		return velocityYNew;
	}
	
	/**
	 * This methods changes the new Y velocity of the ball
	 * @param velocityYNew	takes in the value of the new Y velocity and sets the variable velocityYNew to this new value
	 */
	public void setVelocityYNew(double velocityYNew) {
		this.velocityYNew = velocityYNew;
	}
	
	/**
	 * This methods gets the X velocity of the ball
	 * @return	returns the X velocity of the ball
	 */
	public double getVelocityX() {
		return velocityX;
	}
	
	/**
	 * This methods changes the X velocity of the ball
	 * @param velocityX	takes in the value of the X velocity and sets the variable velocityX to this new value
	 */
	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}
	
	/**
	 * This methods gets the Y velocity of the ball
	 * @return	returns the Y velocity of the ball
	 */
	public double getVelocityY() {
		return velocityY;
	}
	
	/**
	 * This methods changes the Y velocity of the ball
	 * @param velocityY		takes in the value of the Y velocity and sets the variable velocityY to this new value
	 */
	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}
	
	/**
	 * This methods gets the angle of the ball
	 * @return 	returns the angle of the ball
	 */
	public double getAngle() {
		return angle;
	}
	
	/**
	 * This methods changes the angle of the ball (this is very helpful because each ball is going to have its own angle)
	 * @param angle 		takes in the new angle and sets the variable angle to this new calue
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	
	/**
	 * This method creates a new ball with the properties set to the values in the parameters
	 * @param positionX			The original X position of the ball
	 * @param positionY			The original Y position of the ball
	 * @param accelerationX		The original X acceleration of the ball
	 * @param accelerationY		The original Y acceleration of the ball
	 * @param initialVelocity	The initial velocity of the ball
	 * @param velocityX			The X velocity of the ball
	 * @param velocityY			The Y velocity of the ball
	 * @param velocityXNew		The new X velocity of the ball
	 * @param velocityYNew		The new Y velocity of the ball
	 * @param angle				The angle of the ball
	 */
	public Ball (double positionX, double positionY, double accelerationX, double accelerationY, double initialVelocity, double velocityX, double velocityY, double velocityXNew, double velocityYNew, double angle) {
		//Sets the properties of each ball based on the parameters of the new ball created
		this.positionX=positionX;
		this.positionY=positionY;
		this.accelerationX=accelerationX;
		this.accelerationY=accelerationY;
		this.initialVelocity=initialVelocity;
		this.velocityX=velocityX;
		this.velocityY=velocityY;
		this.velocityXNew=velocityXNew;
		this.velocityYNew=velocityYNew;
		this.angle=angle;
	}
}
