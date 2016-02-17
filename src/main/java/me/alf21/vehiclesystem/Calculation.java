package me.alf21.vehiclesystem;

import me.alf21.handling.EngineType;
import me.alf21.handling.Handling;
import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.data.Velocity;
import net.gtaun.shoebill.object.Vehicle;
import net.gtaun.shoebill.object.VehicleParam;

public class Calculation {
	
	public static final double MAGIC_NUMBER = 179.28625;
	
	public static double getSpeed(VehicleData vehicleData) {
		double speed = 0;
		Vehicle vehicle = vehicleData.getVehicle();
		if(vehicle != null) {
			//speed = vehicle.getVelocity().speed3d()*136.666667;
			//speed = vehicle.getVelocity().speed3d()*223.636; //(for Taxi)
			speed = vehicle.getVelocity().speed3d()*MAGIC_NUMBER;
		}
		return speed;
	}
	
	public static Color getBoxColor(Color maxColor, Color minColor, float value, float maxValue) {
		float factor = value / maxValue;
		int	r =	maxColor.getR() + Math.round((minColor.getR()-maxColor.getR()) * factor),
			g =	maxColor.getG() + Math.round((minColor.getG()-maxColor.getG()) * factor),
			b =	maxColor.getB() + Math.round((minColor.getB()-maxColor.getB()) * factor),
			a =	maxColor.getA() + Math.round((minColor.getA()-maxColor.getA()) * factor);
		if(r > 255)	r = 255;	if(r < 0) r = 0;
		if(g > 255)	g = 255;	if(g < 0) g = 0;
		if(b > 255)	b = 255;	if(b < 0) b = 0;
		if(a > 255)	a = 255;	if(a < 0) a = 0;
		return new Color(r,g,b,a);
	}
	
	/**
	 * @param vehicleData the vehicleData
	 * @return tank consumption per seconds
	 */
	public static double getTankConsumption(VehicleData vehicleData) {
		double multiplier = 0.0;
		Vehicle vehicle = vehicleData.getVehicle();
		VehicleParam vehicleParam = vehicle.getState();
		if(vehicleParam.getLights() == 1)
			multiplier += 0.25;
		if(vehicleParam.getEngine() == 1) {
			Handling handling = VehicleSystem.getHandling(vehicle.getModelName());
			multiplier += 1.0;
			if(handling.getTransmission().getEngineType() == EngineType.P)
				multiplier += 1.0;
			else if(handling.getTransmission().getEngineType() == EngineType.D)
				multiplier += 0.5;
			multiplier *= handling.getMass() / 1000.0;
			//TODO increase when Engine is toggled, lights are on, Klimaanlage, Radio usw...
			multiplier += multiplier * getSpeed(vehicleData)/1000.0;
		}
		multiplier /= 10.0;
		return multiplier;
	}
	
	/**
	 * @param handling the vehicle handling
	 * @return default size of tank
	 */
	public static double getTankSize(Handling handling) {
		double tankSize;
		if(handling.getTransmission().getEngineType() == EngineType.P)
			tankSize = handling.getMass() / 5.0;
		else if(handling.getTransmission().getEngineType() == EngineType.D)
			tankSize = handling.getMass() / 10.0;
		else tankSize = handling.getMass() / 20.0;
		return tankSize;
	}
	
	/*
	public static void boostVehicle(VehicleData vehicleData, double maxSpeed) {
		if(vehicleData.getOldVelocity() != null) {
			Vehicle vehicle = vehicleData.getVehicle();
			Handling handling = VehicleSystem.getHandling(vehicle.getModelName());
			Velocity velocity = vehicle.getVelocity();
			double acceleration = handling.getTransmission().getEngineAcceleration(); //Beschleunigung
			float x = velocity.getX(), y = velocity.getY(), z = velocity.getZ();
			double newSpeed = getSpeed(vehicleData);
			if(newSpeed < maxSpeed) {
				newSpeed = maxSpeed - newSpeed;
				newSpeed = Math.sqrt(newSpeed) * ((float) acceleration/10000.0f);
				x += x * (float) newSpeed;
				y += y * (float) newSpeed; //TODO INERTIA
			//	z += z * (float) newSpeed * ((float) acceleration/10000.0f);
				System.out.println(velocity);
				velocity.set(x, y, z);
				System.out.println(velocity);
				vehicle.setVelocity(velocity);
			}
		}
	}
	*/
	

	public static void boostVehicle(VehicleData vehicleData, double maxSpeed) {
		if(vehicleData.getOldVelocity() != null) {
			Vehicle vehicle = vehicleData.getVehicle();
			Handling handling = VehicleSystem.getHandling(vehicle.getModelName());
			Velocity 	velocity = vehicle.getVelocity(),
						oldVelocity = vehicleData.getOldVelocity();
			double acceleration = handling.getTransmission().getEngineAcceleration(); //Beschleunigung
			float 	x = velocity.getX(), 
					y = velocity.getY(), 
					z = velocity.getZ();
			float 	oldX = oldVelocity.getX(),
					oldY = oldVelocity.getY();
			//		oldZ = oldVelocity.getZ();
			double newSpeed = getSpeed(vehicleData);
			if(newSpeed < maxSpeed) {
				newSpeed = maxSpeed - newSpeed;
				newSpeed /= acceleration;
				x += (x - oldX) * (float) newSpeed;
				y += (y - oldY) * (float) newSpeed; //TODO INERTIA
			//	z += z * (float) newSpeed * ((float) acceleration/10000.0f);
				System.out.println(velocity);
				velocity.set(x, y, z);
				System.out.println(velocity);
				vehicle.setVelocity(velocity);
			}
		}
	}
}
