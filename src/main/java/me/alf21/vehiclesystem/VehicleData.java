package me.alf21.vehiclesystem;

import net.gtaun.shoebill.data.Velocity;
import net.gtaun.shoebill.object.Destroyable;
import net.gtaun.shoebill.object.Vehicle;


public class VehicleData implements Destroyable {
	
	private float health;
	private double tank;
	private double maxTankSize;
	private String name;
	private Vehicle vehicle;
	private Velocity oldVelocity;
	
	public VehicleData(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	public float getHealth() {
		return health;
	}
	public void setHealth(float health) {
		this.health = health;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public double getTank() {
		return tank;
	}
	public void setTank(double tank) {
		this.tank = tank;
	}
	
	
	public Vehicle getVehicle() {
		return vehicle;
	}
	
	
	public double getMaxTankSize() {
		return maxTankSize;
	}
	public void setMaxTankSize(double maxTankSize) {
		this.maxTankSize = maxTankSize;
	}
	
	
	public Velocity getOldVelocity() {
		return oldVelocity;
	}
	public void setOldVelocity(Velocity oldVelocity) {
		this.oldVelocity = oldVelocity;
	}

	/*
	 * (non-Javadoc)
	 * @see net.gtaun.shoebill.object.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		if(vehicle != null) {
			vehicle.destroy();
			vehicle = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.gtaun.shoebill.object.Destroyable#isDestroyed()
	 */
	@Override
	public boolean isDestroyed() {
		if(vehicle != null)
			return false;
		return true;
	}
}
