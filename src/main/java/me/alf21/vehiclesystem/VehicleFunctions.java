package me.alf21.vehiclesystem;

import net.gtaun.shoebill.object.Vehicle;


public class VehicleFunctions {
	
	/**
	 * every second
	 * @param vehicleData the vehicleData
	 */
	public static void onTimer(PlayerData playerData, VehicleData vehicleData) {
		if(vehicleData != null) {
			Vehicle vehicle = vehicleData.getVehicle();
			if(playerData.getCount() % (int) (1000/PlayerData.UPDATE_DELAY) == 0) {
				if(vehicleData.getTank() >= 1) {
					if(vehicle.getState().getEngine() == 0)
						vehicle.getState().setEngine(1);
					vehicleData.setTank(vehicleData.getTank()-Calculation.getTankConsumption(vehicleData));
				}
				else vehicle.getState().setEngine(0);
			}
		}
	}
}
