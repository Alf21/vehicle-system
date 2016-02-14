package me.alf21.vehiclesystem;

import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.object.Vehicle;

public class Calculation {
	
	public static double getSpeed(VehicleData vehicleData) {
		double speed = 0;
		Vehicle vehicle = vehicleData.getVehicle();
		if(vehicle != null) {
			speed = vehicle.getVelocity().speed3d()*136.666667;
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
	/*
	public static float getTankVerbrauch() {
		//TODO increase when Engine is toggled, lights are on, Klimaanlage, Radio usw...
	}
	*/
}
