/**
 * @author Alf21 on 18.12.2015 in project event-system.
 * Copyright (c) 2015 Alf21. All rights reserved.
 * http://forum.sa-mp.de/index.php?page=VCard&userID=34293
 * 							or
 * search for Alf21 in http://sa-mp.de || Breadfish
 **/

package me.alf21.vehiclesystem;

import net.gtaun.shoebill.common.player.PlayerLifecycleHolder;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.resource.Plugin;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.EventManagerNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VehicleSystem extends Plugin {

	public static final Logger LOGGER = LoggerFactory.getLogger(VehicleSystem.class);
	private static VehicleSystem instance;
	private PlayerManager playerManager;
	private EventManager eventManager;
    private PlayerLifecycleHolder playerLifecycleHolder;
    private EventManagerNode eventManagerNode;
    
	public static VehicleSystem getInstance() {
		if (instance == null)
			instance = new VehicleSystem();
		return instance;
	}
	
	@Override
	protected void onDisable() throws Throwable {
		playerLifecycleHolder.destroy();
        eventManagerNode.destroy();
		playerManager.uninitialize();
		playerManager.destroy();
		playerManager = null;
		System.out.println("[VEHICLESYSTEM] uninitialized");
	}

	@Override
	protected void onEnable() throws Throwable {
		instance = this;
		eventManager = getEventManager();
        eventManagerNode = eventManager.createChildNode();
        playerLifecycleHolder = new PlayerLifecycleHolder(eventManager);
        playerLifecycleHolder.registerClass(PlayerData.class);
		playerManager = new PlayerManager();
		System.out.println("[VEHICLESYSTEM] initialized");
	}

    public Logger getLoggerInstance() {
        return LOGGER;
    }

    public EventManager getEventManagerInstance() {
        return eventManagerNode;
    }
    
    public PlayerLifecycleHolder getPlayerLifecycleHolder() {
        return playerLifecycleHolder;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

	public static VehicleData getVehicleData(Player player) {
		//initialize the vehicleDatas and match them
		return null;
	}
}
