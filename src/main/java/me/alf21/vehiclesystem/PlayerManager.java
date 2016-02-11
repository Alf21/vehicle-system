/**
 * @author Alf21 on 18.12.2015 in project event-system.
 * Copyright (c) 2015 Alf21. All rights reserved.
 */

package me.alf21.vehiclesystem;

import java.io.IOException;

import me.alf21.vehiclesystem.Tacho;
import me.alf21.tacho.BlockBarTacho;
import me.alf21.vehiclesystem.VehicleSystem;
import net.gtaun.shoebill.common.command.CommandGroup;
import net.gtaun.shoebill.common.command.PlayerCommandManager;
import net.gtaun.shoebill.constant.PlayerState;
import net.gtaun.shoebill.event.player.PlayerStateChangeEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.HandlerPriority;

public class PlayerManager
{	
	public EventManager eventManager;
	public PlayerCommandManager commandManager;
	public PlayerData playerLifecycle;

	public PlayerManager() throws IOException
	{	
		eventManager = VehicleSystem.getInstance().getEventManagerInstance();
		
		commandManager = new PlayerCommandManager(eventManager);
		commandManager.registerCommands(new Commands());
        
		commandManager.installCommandHandler(HandlerPriority.NORMAL);
		
		CommandGroup playerCommands = new CommandGroup(); 
		playerCommands.registerCommands(new Commands()); 
		commandManager.registerChildGroup(playerCommands, "player");
		
		commandManager.setUsageMessageSupplier((player, command, prefix, params, help) -> { 
			String message;
            if(help == null)
            {
            	message = prefix + command;
	            for (String param : params) {
	                message += " [" + param + "]"; 
	            }
            }
            else {
            	message = help;
            }
            return message; 
		}); 
		
	//--

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
			if(player.getState() == PlayerState.DRIVER || player.getState() == PlayerState.PASSENGER) {
				playerLifecycle.setTacho((Tacho) new BlockBarTacho(player));
				playerLifecycle.getTacho().create();
				playerLifecycle.getTacho().show();
			}
		});
	}


	public void uninitialize() {
		
	}


	public void destroy() {
		
	}
}