package ProjectileKS;
import java.util.ArrayList;
import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.*;
import org.opensourcephysics.frames.*;

public class OneDMotion extends AbstractSimulation {
	ArrayList <Circle> balls = new ArrayList<Circle>(); //declares an arralist of circles that all the balls can be placed into
	Circle ball = new Circle();
	
	DisplayFrame frame = new DisplayFrame("X", "Y", "Free Fall(1-D Motion)"); //declares the displayframe that the simulation will occur in
	double time=0; //sets the original time to 0
	double g = (-9.81); //gravity = -9.81
	double acceleration=g; //original acceleration is gravity
	double B = (.02); //given value of B=(.02)
	double velocity=0; //declares the variable velocity
	double position = 0; //declares the variable position
	double timestep = (0.1); //sets how much time in between each calculation of acceleration, velocity, and position
	double velocityNew=velocity; //sets the new velocity to the original velocity
	
	//Declares plotframe for velocity vs. time graph
	PlotFrame v = new PlotFrame("t","v", "Velocity vs. Time Graph"); 
	
	//Declares plotframe for position vs. time graph
	PlotFrame p = new PlotFrame("x","t","Position vs. Time Graph"); 
	
	//Declares plotframe for acceleration vs. time graph
	PlotFrame a = new PlotFrame("a","t", "Acceleration vs. Time Graph"); 
	
	/**
	 * The do step method tells the simulation what to do. It repeats itself so that the simulation will change. 
	 * This do step calculates the new acceleration, velocity, and position so that the ball moves each time
	 */
	@Override
	protected void doStep() {		
		velocity=velocityNew; //sets the old velocity to the new velocity
		ball.setXY(ball.getX(), position); //keeps the x position the same (because this is only 1-D Motion), but changes the x position based on the acceleration and velocity
		v.append(1, time, velocity); //adds point to the velocity vs. time graph
		p.append(2, time, position); //adds point to the position vs. time graph
		a.append(3, time, acceleration); //adds point to the acceleration vs. time graph
		
		acceleration = (g+(B*(velocityNew*velocityNew))); //calculates the new acceleration
		velocityNew = (velocity+(acceleration*timestep)); //calculates the new velocity		
		position = ((((velocityNew+velocity)/2)*timestep)+position); //calculates the new position using the trapezoid rule
		
		time+=timestep; //increases the time by the timestep so that the next plot on the graphs will be at a new time
	}
	
	/**
	 * The intitalize method gets the simulation ready to be started. It sets the variables to their original values and shows the display frame that the simulation will appear on.
	 */
	public void initialize() {		
		balls.add(ball); //adds the ball to the arraylist balls
		
		velocity = control.getDouble("velocity"); //sets the velocity to the value inputed by the user in the control panel
		position = control.getDouble("position"); //sets the Y position to the value inputed by the user in the control panel
		
		ball.setXY(0,position); //makes the ball start with an x value of 0 and y value based on the position that the user inputed
		frame.setVisible(true); //makes the display frame appear
		frame.addDrawable(ball); //adds the ball to the display frame
		
		//Set velocity plotframe
		v.setAutoscaleX(true); //automatically sets the scale of the plotframe
		v.setAutoscaleY(true);
		v.setDefaultCloseOperation(3);
		v.setVisible(true); //makes plotframe visible
		
		//Set position plotframe
		p.setAutoscaleX(true); //automatically sets the scale of the plotframe
		p.setAutoscaleY(true);
		p.setDefaultCloseOperation(3);
		p.setVisible(true); //makes plotframe visible
		
		//Set acceleration plotframe
		a.setAutoscaleX(true); //automatically sets the scale of the plotframe
		a.setAutoscaleY(true);
		a.setDefaultCloseOperation(3);
		a.setVisible(true); //makes the plotframe visible
	}
	
	/**
	 * The reset method makes it so that every time the simulation runs a control panel appears so that the user can input some of the values. 
	 * The user can change the position and the original velocity
	 */
	public void reset(){
		control.setValue("position", 0); //sets the default position to 0
		control.setValue("velocity", 0); //sets the default velocity to 0
	}
	
	/**
	 * The main method tells the simulation to run
	 * @param args
	 */
	public static void main(String[] args) {
		SimulationControl.createApp(new OneDMotion()); //runs the simulation
	}
	
}