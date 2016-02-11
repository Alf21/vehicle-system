package me.alf21.vehiclesystem;

import net.gtaun.shoebill.object.Destroyable;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.object.Timer;
import net.gtaun.shoebill.object.Vehicle;


public class Tacho implements Destroyable {

	private Player 			player;
	private boolean			created;
	private VehicleData 	vehicleData;
	private Timer 			timer;
	
	public Tacho(Player player) {
		this.player = player;
		vehicleData = null;
		created = false;
	}
	
	/**
	 * create the tacho
	 */
	public void create() {
		Vehicle vehicle = player.getVehicle();
		if(vehicle != null) {
			vehicleData = VehicleSystem.getVehicleData(player);
			if(vehicleData == null) {
				vehicleData = new VehicleData(vehicle);
				vehicleData.setHealth(vehicle.getHealth());
				vehicleData.setName(vehicle.getModelName());
				vehicleData.setTank(100);
			}
			
			timer = Timer.create(100, (factualInterval) -> {
				update();
			});
			
			created = true;
		}
	}
	
	/**
	 * on tacho update
	 */
	public void update() {
		Vehicle vehicle = player.getVehicle();
		if(isCreated() && vehicle != null) {
			if(vehicleData.getTank() >= 1) {
				if(vehicle.getState().getEngine() == 0)
					vehicle.getState().setEngine(1);
				vehicleData.setTank(vehicleData.getTank()-0.1f);
			}
			else vehicle.getState().setEngine(0);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.gtaun.shoebill.object.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		if(timer != null) {
			if(timer.isRunning())
				timer.stop();
			timer.destroy();
			timer = null;
		}
		
		created = false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.gtaun.shoebill.object.Destroyable#isDestroyed()
	 */
	@Override
	public boolean isDestroyed() {
		if(timer != null)
			return false;
		return true;
	}

	/**
	 * show the tacho
	 */
	public void show() {
		timer.start();
	}
	
	/**
	 * hide the tacho
	 */
	public void hide() {
		timer.stop();
	}
	
	
	/**
	 * get the vehicleData of the tacho
	 * @return
	 */
	public VehicleData getVehicleData() {
		return vehicleData;
	}
	
	/**
	 * check whether tacho is created
	 * @return created the boolean
	 */
	public boolean isCreated() {
		return created;
	}
	
	/**
	 * get the player of tacho
	 * @return player the player of tacho
	 */
	public Player getPlayer() {
		return player;
	}
}
