package ProjectileKS;
import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.*;
import org.opensourcephysics.frames.*;

public class ProjectilePractice extends AbstractSimulation {
//	int x; 
	/** This program is going to be used for simulations and animations
	 * 
	 * 
	 */
	
	Circle c = new Circle();
	DisplayFrame frame = new DisplayFrame("X", "Y", "Display Frame Test");
	
	@Override
	protected void doStep() {
//		control.println("X = " + x);
//		x++;
		c.setXY(c.getX() + 0.1, c.getY() + 0.1);
	}
	
	public void initialize() {
//		int z = control.getInt("x");
		c.setXY(control.getDouble("x"), control.getDouble("y"));
		frame.setVisible(true);
		frame.addDrawable(c);
	}
	
	public void reset() {
//		control.setValue("x", 50);
		control.setValue("x", 0);
		control.setValue("y", 0);
	}
	
	
	public static void main(String[] args) {
		SimulationControl.createApp(new ProjectilePractice());
	}
	
}