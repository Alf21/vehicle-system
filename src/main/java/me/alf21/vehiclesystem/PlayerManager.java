/**
 * @author Alf21 on 18.12.2015 in project event-system.
 * Copyright (c) 2015 Alf21. All rights reserved.
 */

package me.alf21.vehiclesystem;

import java.io.IOException;



import me.alf21.vehiclesystem.VehicleSystem;
import net.gtaun.shoebill.constant.PlayerState;
import net.gtaun.shoebill.event.player.PlayerConnectEvent;
import net.gtaun.shoebill.event.player.PlayerDeathEvent;
import net.gtaun.shoebill.event.player.PlayerDisconnectEvent;
import net.gtaun.shoebill.event.player.PlayerKeyStateChangeEvent;
import net.gtaun.shoebill.event.player.PlayerSpawnEvent;
import net.gtaun.shoebill.event.player.PlayerStateChangeEvent;
import net.gtaun.shoebill.event.player.PlayerTakeDamageEvent;
import net.gtaun.shoebill.event.player.PlayerWeaponShotEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;

public class PlayerManager
{	
	public EventManager eventManager;
	public PlayerData playerLifecycle;

	public PlayerManager() throws IOException
	{	
		eventManager = VehicleSystem.getInstance().getEventManagerInstance();

/*		eventManager.registerHandler(PlayerUpdateEvent.class, (e) -> {
			Player player = e.getPlayer();
			playerLifecycle = VehicleSystem.getInstance().getPlayerLifecycleHolder().getObject(player, PlayerData.class);
			if(player.getVehicle() != null && playerLifecycle.getTacho() != null) {
				VehicleData vehicleData = playerLifecycle.getTacho().getVehicleData(); //TODO get from each veh single -> not from Tacho.class

				vehicleData.setTank(vehicleData.getTank()-0.1f);
			//	playerLifecycle.getVehicleData().setHealth(player.getVehicle().getHealth()); //TODO Protect
			}
		});*/

		
		eventManager.registerHandler(PlayerStateChangeEvent.class, (e) -> {
			Player player = e.getPlayer();
			playerLifecycle = VehicleSystem.getInstance().getPlayerLifecycleHolder().getObject(player, PlayerData.class);
			if(playerLifecycle.getTacho() != null) {
				playerLifecycle.getTacho().destroy();
				playerLifecycle.setTacho(null);
			}
			if(e.getPlayer().getState() == PlayerState.DRIVER || e.getPlayer().getState() == PlayerState.PASSENGER) {
				playerLifecycle.setTacho(new Tacho(player));
				playerLifecycle.getTacho().create();
				playerLifecycle.getTacho().show();
			}
		});
		


		eventManager.registerHandler(PlayerWeaponShotEvent.class, (e) -> {

		});

		eventManager.registerHandler(PlayerTakeDamageEvent.class, (e) -> {

		});

		eventManager.registerHandler(PlayerConnectEvent.class, (e) -> {

		});
		
		eventManager.registerHandler(PlayerDisconnectEvent.class, (e) -> {

		});

		eventManager.registerHandler(PlayerDeathEvent.class, (e) -> {

		});

		eventManager.registerHandler(PlayerKeyStateChangeEvent.class, (e) -> {

		});
		
		eventManager.registerHandler(PlayerSpawnEvent.class, (e) -> {
			
		});
	}


	public void uninitialize() {
		
	}


	public void destroy() {
		
	}
}