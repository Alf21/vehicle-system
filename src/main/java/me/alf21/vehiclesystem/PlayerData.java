/**
 * @author Alf21 on 18.12.2015 in project event-system.
 * Copyright (c) 2015 Alf21. All rights reserved.
 */

package me.alf21.vehiclesystem;

import net.gtaun.shoebill.common.player.PlayerLifecycleObject;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;

public class PlayerData extends PlayerLifecycleObject {
	private Player player;
	private Tacho tacho;
	
	public PlayerData(EventManager manager, Player p) { 
		super(manager, p);
        player = p;
	}
	
	public Tacho getTacho() {
		return tacho;
	}
	public void setTacho(Tacho tacho) {
		this.tacho = tacho;
	}
	
	/* (non-Javadoc)
	 * @see net.gtaun.shoebill.common.player.PlayerLifecycleObject#getPlayer()
	 */
	@Override
	public Player getPlayer() {
		return player;
	}

	@Override 
	protected void onInit() { 
		
	} 

	@Override 
	protected void onDestroy() { 
		if(tacho != null && !tacho.isDestroyed())
			tacho.destroy();
	}
}