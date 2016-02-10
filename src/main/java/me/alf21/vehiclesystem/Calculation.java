package me.alf21.vehiclesystem;

import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.data.Velocity;
import net.gtaun.shoebill.object.Vehicle;

public class Calculation {
	
	public static float getSpeed(VehicleData vehicleData) {
		float speed = 0;
		Vehicle vehicle = vehicleData.getVehicle();
		if(vehicle != null) {
			Velocity velocity = vehicle.getVelocity(); 
		  // speed = (float) Math.sqrt(((velocity.getX()*velocity.getX())+(velocity.getY()*velocity.getY())+(velocity.getZ()*velocity.getZ())))*100;
		  // 179.28625 = factor
		  // calculate in km/h
		  // speed *= 1.609344f; //transform from mp/h to km/h
			speed = (float) Math.sqrt(((velocity.getX()*velocity.getX())+(velocity.getY()*velocity.getY())+(velocity.getZ()*velocity.getZ())))*136.666667f;
		}
		return speed;
	}
	
	public static Color getBoxColor(Color startColor, Color endColor, float value, float maxValue) {
		float factor = value / maxValue;
		int	r =	startColor.getR() + Math.round((endColor.getR()-startColor.getR()) * factor),
			g =	startColor.getG() + Math.round((endColor.getG()-startColor.getG()) * factor),
			b =	startColor.getB() + Math.round((endColor.getB()-startColor.getB()) * factor),
			a =	startColor.getA() + Math.round((endColor.getA()-startColor.getA()) * factor);
		if(r > 255)	r = 255;	if(r < 0) r = 0;
		if(g > 255)	g = 255;	if(g < 0) g = 0;
		if(b > 255)	b = 255;	if(b < 0) b = 0;
		if(a > 255)	a = 255;	if(a < 0) a = 0;
		return new Color(r,g,b,a);
	}
}
