/**
 * @author Alf21 on 18.12.2015 in project event-system.
 * Copyright (c) 2015 Alf21. All rights reserved.
 */

package me.alf21.vehiclesystem;

import me.alf21.vehiclesystem.Tacho;
import net.gtaun.shoebill.common.player.PlayerLifecycleObject;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.object.Timer;
import net.gtaun.util.event.EventManager;

public class PlayerData extends PlayerLifecycleObject {
	private Player player;
	private Tacho tacho;
	private Timer timer;
	
	public static final int UPDATE_DELAY = 200;
	private Long count;
	
	public PlayerData(EventManager manager, Player p) { 
		super(manager, p);
        player = p;
        count = 0L;
        timer = Timer.create(UPDATE_DELAY, (factualInterval) -> {
        	VehicleFunctions.onTimer(this, VehicleSystem.getVehicleData(p));
        	count++;
        });
	}
	
	public Tacho getTacho() {
		return tacho;
	}
	public void setTacho(Tacho tacho) {
		this.tacho = tacho;
	}
	
	public Timer getTimer() {
		return timer;
	}
	
	public Long getCount() {
		return count;
	}
	
	/* (non-Javadoc)
	 * @see net.gtaun.shoebill.common.player.PlayerLifecycleObject#getPlayer()
	 */
	@Override
	public Player getPlayer() {
		return player;
	}

	/*
	 * (non-Javadoc)
	 * @see net.gtaun.shoebill.common.AbstractShoebillContext#onInit()
	 */
	@Override 
	protected void onInit() { 
		
	} 

	/*
	 * (non-Javadoc)
	 * @see net.gtaun.shoebill.common.AbstractShoebillContext#onDestroy()
	 */
	@Override 
	protected void onDestroy() { 
		if(tacho != null && !tacho.isDestroyed()) {
			tacho.destroy();
			tacho = null;
		}
		if(timer != null) {
			if(timer.isRunning())
				timer.stop();
			timer.destroy();
			timer = null;
		}
	}
}