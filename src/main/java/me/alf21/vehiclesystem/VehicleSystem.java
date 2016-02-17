/**
 * @author Alf21 on 18.12.2015 in project event-system.
 * Copyright (c) 2015 Alf21. All rights reserved.
 * http://forum.sa-mp.de/index.php?page=VCard&userID=34293
 * 							or
 * search for Alf21 in http://sa-mp.de || Breadfish
 **/

package me.alf21.vehiclesystem;

import java.util.ArrayList;

import me.alf21.handling.DriveType;
import me.alf21.handling.EngineType;
import me.alf21.handling.Handling;
import me.alf21.handling.VehicleType;
import me.alf21.handling.bikeData.BikeData;
import me.alf21.handling.boatData.BoatData;
import me.alf21.handling.planeData.PlaneData;
import net.gtaun.shoebill.common.player.PlayerLifecycleHolder;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.object.Vehicle;
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
    private static ArrayList<VehicleData> vehicleDatas;
    private static ArrayList<Handling> handlings;
    
	public static VehicleSystem getInstance() {
		if (instance == null)
			instance = new VehicleSystem();
		return instance;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.gtaun.shoebill.resource.Resource#onDisable()
	 */
	@Override
	protected void onDisable() throws Throwable {
		playerLifecycleHolder.destroy();
        eventManagerNode.destroy();
		playerManager.uninitialize();
		playerManager.destroy();
		playerManager = null;
		handlings = null;
		System.out.println("[VEHICLESYSTEM] uninitialized");
	}

	/*
	 * (non-Javadoc)
	 * @see net.gtaun.shoebill.resource.Resource#onEnable()
	 */
	@Override
	protected void onEnable() throws Throwable {
		instance = this;
		eventManager = getEventManager();
        eventManagerNode = eventManager.createChildNode();
        playerLifecycleHolder = new PlayerLifecycleHolder(eventManager);
        playerLifecycleHolder.registerClass(PlayerData.class);
		playerManager = new PlayerManager();
		vehicleDatas = new ArrayList<VehicleData>();
		initializeVehicleBasicDatas();
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
		Vehicle vehicle = player.getVehicle();
		if(vehicle != null) {
			for(VehicleData vehicleData : vehicleDatas) {
				if(vehicleData.getVehicle() == vehicle)
					return vehicleData;
			}
			VehicleData vehicleData = createVehicleData(player);
			vehicleDatas.add(vehicleData);
			return vehicleData;
		}
		return null;
	}

	public static VehicleData createVehicleData(Player player) {
		Vehicle vehicle = player.getVehicle();
		if(vehicle != null) {
			vehicle.getState().set(1, 0, 0, 0, 0, 0, 0);
			VehicleData vehicleData = new VehicleData(vehicle);
			vehicleData.setHealth(vehicle.getHealth());
			vehicleData.setName(vehicle.getModelName());
			vehicleData.setMaxTankSize(getHandling(vehicle.getModelName()).getTankSize());
			vehicleData.setTank(getHandling(vehicle.getModelName()).getTankSize());
			return vehicleData;
		}
		return null;
	}

	/**
	 * Handling information:<br>
	 * <br>
	 * vehicle identifier 14 characters max<br>
	 * dimensions in metres<br>
	 * mass in Kg<br>
	 * velocity in Km/h<br>
	 * acceleration/deceleration in ms-2<br>
	 * multipliers x1.0 is default<br>
	 * angles in degrees<br>
	 * Levels :- (L)ow, (M)edium, (H)igh<br>
	 * buoyancy = percent submerged (> 100% vehicle sinks)<br>
	 * engine type :- (P)etrol, (D)iesel, (E)lectric<br>
	 * engine drive :- (F)ront, (R)ear, (4)-wheel drive<br>
	 * <br>
	 * it depends on the default handling.cfg file<br>
	 * <br>
	 * @param modelName name of vehicle
	 * @return Handling
	 */
	public static Handling getHandling(String modelName) {
		for(Handling handling : handlings) {
			if(handling.getName().equals(modelName.toUpperCase())) {
				return handling;
			}
		}
		//TODO create new Handling with default settings
		return null;
	}
	
    private void initializeVehicleBasicDatas() {
    	handlings = new ArrayList<Handling>();
    	handlings.add(new Handling("LANDSTAL", VehicleType.CAR, 1700.0, 5008.3, 2.5, 0.0, 0.0, -0.3, 85, 0.75, 0.85, 0.5, 5, 160.0, 25.0, 20.0, DriveType.FOUR, EngineType.D, 6.2, 0.60, 0, 35.0, 2.4, 0.08, 0.0, 0.28, -0.14, 0.5, 0.25, 0.27, 0.23, 25000, "20", "500002", 0, 1, 0));
    	handlings.add(new Handling("BRAVURA", VehicleType.CAR, 1300.0, 2200.0, 1.7, 0.0, 0.3, 0.0, 70, 0.65, 0.80, 0.52, 5, 160.0, 15.0, 10.0, DriveType.F, EngineType.P, 08.0, 0.80, 0, 30.0, 1.3, 0.08, 0.0, 0.31, -0.15, 0.57, 0.0, 0.26, 0.50, 9000, "1", "1", 0, 0, 0));
    	handlings.add(new Handling("BUFFALO", VehicleType.CAR, 1500.0, 4000.0, 2.0, 0.0, 0.0, -0.1, 85, 0.7, 0.9, 0.5, 5, 200.0, 28.0, 5.0, DriveType.R, EngineType.P, 11.0, 0.45, 0, 30.0, 1.2, 0.12, 0.0, 0.28, -0.24, 0.5, 0.4, 0.25, 0.50, 35000, "2800", "10200000", 1, 1, 0));
    	handlings.add(new Handling("LINERUN", VehicleType.CAR, 3800.0, 19953.2, 5.0, 0.0, 0.0, -0.2, 90, 0.95, 0.65, 0.4, 5, 120.0, 25.0, 30.0, DriveType.R, EngineType.D, 8.0, 0.30, 0, 25.0, 1.6, 0.06, 0.0, 0.40, -0.20, 0.5, 0.0, 0.65, 0.25, 35000, "6000", "200", 0, 1, 2));
    	handlings.add(new Handling("PEREN", VehicleType.CAR, 1200.0, 3000.0, 2.5, 0.0, 0.1, 0.0, 70, 0.70, 0.90, 0.48, 5, 150.0, 18.0, 20.0, DriveType.F, EngineType.P, 4.0, 0.80, 0, 30.0, 1.4, 0.1, 0.0, 0.37, -0.17, 0.5, 0.0, 0.2, 0.60, 10000, "20", "0", 1, 1, 0));
    	handlings.add(new Handling("SENTINEL", VehicleType.CAR, 1600.0, 4000.0, 2.2, 0.0, 0.0, -0.2, 75, 0.65, 0.75, 0.5, 5, 165.0, 24.0, 10.0, DriveType.R, EngineType.P, 10.0, 0.5, 0, 27.0, 1.0, 0.08, 0.0, 0.30, -0.20, 0.5, 0.3, 0.2, 0.56, 35000, "0", "400000", 0, 1, 0));
    	handlings.add(new Handling("DUMPER", VehicleType.CAR, 20000.0, 200000.0, 4.0, 0.0, 0.5, -0.4, 90, 0.78, 0.8, 0.55, 4, 110.0, 25.0, 30.0, DriveType.R, EngineType.D, 03.17, 0.40, 0, 30.0, 0.8, 0.06, 0.0, 0.20, -0.30, 0.55, 0.0, 0.45, 0.20, 5000, "c008", "1340201", 0, 1, 20));
    	handlings.add(new Handling("FIRETRUK", VehicleType.CAR, 6500.0, 36670.8, 3.0, 0.0, 0.0, 0.0, 90, 0.55, 0.8, 0.5, 5, 170.0, 27.0, 10.0, DriveType.R, EngineType.D, 10.00, 0.45, 0, 27.0, 1.2, 0.08, 0.0, 0.47, -0.17, 0.5, 0.0, 0.2, 0.26, 15000, "4098", "0", 0, 1, 2));
    	handlings.add(new Handling("TRASH", VehicleType.CAR, 5500.0, 33187.9, 5.0, 0.0, 0.0, -0.2, 90, 0.60, 0.9, 0.5, 4, 110.0, 20.0, 30.0, DriveType.R, EngineType.D, 3.5, 0.40, 0, 30.0, 1.0, 0.06, 0.0, 0.45, -0.25, 0.55, 0.3, 0.45, 0.20, 5000, "4008", "200", 0, 1, 0));
    	handlings.add(new Handling("STRETCH", VehicleType.CAR, 2200.0, 10000.0, 1.8, 0.0, 0.0, 0.0, 75, 0.60, 0.8, 0.5, 5, 180.0, 18.0, 25.0, DriveType.R, EngineType.P, 10.0, 0.40, 0, 30.0, 1.1, 0.07, 0.0, 0.35, -0.20, 0.5, 0.0, 0.2, 0.72, 40000, "282000", "10400001", 1, 1, 0));
    	handlings.add(new Handling("MANANA", VehicleType.CAR, 1000.0, 1400.0, 2.8, 0.0, 0.2, 0.0, 70, 0.80, 0.8, 0.5, 3, 160.0, 19.0, 15.0, DriveType.F, EngineType.P, 8.0, 0.80, 0, 30.0, 1.2, 0.10, 5.0, 0.31, -0.15, 0.5, 0.2, 0.26, 0.50, 9000, "0", "0", 0, 0, 0));
    	handlings.add(new Handling("INFERNUS", VehicleType.CAR, 1400.0, 2725.3, 1.5, 0.0, 0.0, -0.25, 70, 0.70, 0.8, 0.50, 5, 240.0, 30.0, 10.0, DriveType.FOUR, EngineType.P, 11.0, 0.51, 0, 30.0, 1.2, 0.19, 0.0, 0.25, -0.10, 0.5, 0.4, 0.37, 0.72, 95000, "40002004", "C04000", 1, 1, 1));
    	handlings.add(new Handling("VOODOO", VehicleType.CAR, 1800.0, 4411.5, 2.0, 0.0, -0.1, -0.2, 70, 0.95, 0.80, 0.45, 5, 160.0, 23.0, 5.0, DriveType.R, EngineType.P, 6.50, 0.50, 0, 30.0, 1.0, 0.08, 0.0, 0.20, -0.25, 0.5, 0.6, 0.26, 0.41, 30000, "0", "2410008", 1, 1, 0));
    	handlings.add(new Handling("PONY", VehicleType.CAR, 2600.0, 8666.7, 3.0, 0.0, 0.0, -0.25, 80, 0.55, 0.90, 0.50, 5, 160.0, 15.0, 25.0, DriveType.R, EngineType.D, 6.0, 0.80, 0, 30.0, 2.6, 0.07, 0.0, 0.35, -0.15, 0.25, 0.0, 0.2, 0.50, 20000, "4001", "1", 0, 3, 13));
    	handlings.add(new Handling("MULE", VehicleType.CAR, 3500.0, 14000.0, 4.0, 0.0, 0.0, 0.1, 80, 0.55, 0.85, 0.46, 5, 140.0, 18.0, 20.0, DriveType.R, EngineType.D, 4.5, 0.60, 0, 30.0, 2.0, 0.07, 5.0, 0.30, -0.15, 0.5, 0.0, 0.46, 0.53, 22000, "4088", "0", 0, 3, 0));
    	handlings.add(new Handling("CHEETAH", VehicleType.CAR, 1200.0, 3000.0, 2.0, 0.0, -0.2, -0.2, 70, 0.80, 0.9, 0.50, 5, 230.0, 30.0, 10.0, DriveType.R, EngineType.P, 11.1, 0.48, 0, 35.0, 0.8, 0.20, 0.0, 0.10, -0.15, 0.5, 0.6, 0.40, 0.54, 105000, "c0002004", "208000", 0, 0, 1));
    	handlings.add(new Handling("AMBULAN", VehicleType.CAR, 2600.0, 10202.8, 2.5, 0.0, 0.0, -0.1, 90, 0.75, 0.80, 0.47, 5, 155.0, 24.0, 10.0, DriveType.FOUR, EngineType.D, 7.0, 0.55, 0, 35.0, 1.0, 0.07, 0.0, 0.40, -0.20, 0.5, 0.0, 0.58, 0.33, 10000, "4001", "4", 0, 1, 13));
    	handlings.add(new Handling("MOONBEAM", VehicleType.CAR, 2000.0, 5848.3, 2.8, 0.0, 0.2, -0.1, 85, 0.60, 0.80, 0.50, 5, 150.0, 15.0, 15.0, DriveType.R, EngineType.D, 5.5, 0.6, 0, 30.0, 1.4, 0.1, 0.0, 0.35, -0.15, 0.55, 0.0, 0.2, 0.75, 16000, "20", "0", 1, 3, 0));
    	handlings.add(new Handling("ESPERANT", VehicleType.CAR, 1800.0, 4350.0, 2.0, 0.0, 0.0, 0.0, 70, 0.55, 0.88, 0.52, 5, 160.0, 18.0, 5.0, DriveType.R, EngineType.P, 4.0, 0.60, 0, 28.0, 1.0, 0.05, 1.0, 0.35, -0.18, 0.5, 0.0, 0.36, 0.54, 19000, "40000000", "10000000", 0, 3, 0));
    	handlings.add(new Handling("TAXI", VehicleType.CAR, 1450.0, 4056.4, 2.2, 0.0, 0.3, -0.25, 75, 0.80, 0.75, 0.45, 5, 180.0, 19.0, 10.0, DriveType.F, EngineType.P, 9.1, 0.60, 0, 35.0, 1.4, 0.1, 0.0, 0.25, -0.15, 0.54, 0.0, 0.2, 0.51, 20000, "0", "200000", 0, 1, 0));
    	handlings.add(new Handling("WASHING", VehicleType.CAR, 1850.0, 5000.0, 2.2, 0.0, 0.0, -0.1, 75, 0.75, 0.65, 0.52, 5, 180.0, 21.0, 10.0, DriveType.R, EngineType.P, 7.5, 0.65, 0, 30.0, 1.0, 0.20, 0.0, 0.27, -0.20, 0.5, 0.35, 0.24, 0.60, 18000, "0", "10400000", 1, 1, 0));
    	handlings.add(new Handling("BOBCAT", VehicleType.CAR, 1700.0, 4000.0, 2.5, 0.0, 0.05, -0.2, 75, 0.65, 0.85, 0.57, 5, 165.0, 20.0, 15.0, DriveType.FOUR, EngineType.D, 8.5, 0.5, 0, 35.0, 1.5, 0.10, 5.0, 0.35, -0.18, 0.4, 0.0, 0.26, 0.20, 26000, "40", "104004", 0, 1, 0));
    	handlings.add(new Handling("MRWHOOP", VehicleType.CAR, 1700.0, 4108.3, 3.5, 0.0, 0.0, 0.0, 85, 0.75, 0.75, 0.5, 5, 145.0, 14.0, 50.0, DriveType.R, EngineType.D, 4.17, 0.80, 0, 35.0, 1.2, 0.1, 0.0, 0.35, -0.15, 0.5, 0.0, 0.24, 0.77, 29000, "88", "2", 0, 1, 0));
    	handlings.add(new Handling("BFINJECT", VehicleType.CAR, 1200.0, 2000.0, 4.0, 0.0, -0.1, -0.1, 80, 0.75, 0.85, 0.5, 4, 170.0, 30.0, 10.0, DriveType.R, EngineType.P, 6.0, 0.50, 0, 35.0, 1.0, 0.07, 5.0, 0.20, -0.15, 0.45, 0.0, 0.38, 0.40, 15000, "201904", "308200", 1, 2, 25));
    	handlings.add(new Handling("PREMIER", VehicleType.CAR, 1600.0, 3921.3, 1.8, 0.0, -0.4, 0.0, 75, 0.75, 0.85, 0.52, 5, 200.0, 22.0, 10.0, DriveType.R, EngineType.P, 10.0, 0.53, 0, 35.0, 1.3, 0.12, 0.0, 0.28, -0.12, 0.38, 0.0, 0.2, 0.24, 25000, "40000000", "10200008", 0, 1, 0));
    	handlings.add(new Handling("ENFORCER", VehicleType.CAR, 4000.0, 17333.3, 1.8, 0.0, 0.1, 0.0, 85, 0.55, 0.8, 0.48, 5, 170.0, 20.0, 20.0, DriveType.R, EngineType.D, 5.4, 0.45, 0, 27.0, 1.4, 0.1, 0.0, 0.40, -0.25, 0.5, 0.0, 0.32, 0.16, 40000, "4011", "0", 0, 1, 13));
    	handlings.add(new Handling("SECURICA", VehicleType.CAR, 7000.0, 30916.7, 1.5, 0.0, 0.0, 0.0, 90, 0.50, 0.7, 0.46, 5, 170.0, 15.0, 30.0, DriveType.R, EngineType.D, 8.4, 0.45, 0, 27.0, 1.0, 0.06, 0.0, 0.35, -0.15, 0.5, 0.0, 0.27, 0.35, 40000, "4001", "4", 1, 1, 13));
    	handlings.add(new Handling("BANSHEE", VehicleType.CAR, 1400.0, 3000.0, 2.0, 0.0, 0.0, -0.20, 70, 0.75, 0.89, 0.50, 5, 200.0, 33.0, 10.0, DriveType.R, EngineType.P, 8.0, 0.52, 0, 34.0, 1.6, 0.10, 5.0, 0.30, -0.15, 0.5, 0.3, 0.15, 0.49, 45000, "2004", "200000", 1, 1, 1));
    	handlings.add(new Handling("BUS", VehicleType.CAR, 5500.0, 33187.9, 2.0, 0.0, 0.5, 0.0, 90, 0.75, 0.85, 0.40, 4, 130.0, 14.0, 50.0, DriveType.R, EngineType.D, 4.17, 0.40, 0, 30.0, 1.2, 0.07, 0.0, 0.45, -0.25, 0.45, 0.0, 0.2, 0.75, 15000, "4002", "0", 1, 1, 16));
    	handlings.add(new Handling("RHINO", VehicleType.CAR, 25000.0, 250000.0, 5.0, 0.0, 0.0, 0.0, 90, 2.50, 0.8, 0.5, 4, 80.0, 40.0, 150.0, DriveType.FOUR, EngineType.D, 5.0, 0.50, 0, 35.0, 0.4, 0.02, 0.0, 0.35, -0.10, 0.5, 0.0, 0.22, 0.09, 110000, "40008008", "308840", 0, 1, 24));
    	handlings.add(new Handling("BARRACKS", VehicleType.CAR, 10500.0, 61407.5, 4.0, 0.0, 0.0, 0.0, 90, 0.65, 0.7, 0.47, 5, 180.0, 20.0, 25.0, DriveType.FOUR, EngineType.D, 4.00, 0.40, 0, 27.0, 1.2, 0.05, 0.0, 0.47, -0.17, 0.5, 0.0, 0.62, 0.26, 10000, "200809", "100000", 0, 1, 2));
    	handlings.add(new Handling("HOTKNIFE", VehicleType.CAR, 1400.0, 3400.0, 2.5, 0.0, 0.3, -0.3, 75, 0.75, 0.8, 0.5, 5, 200.0, 28.0, 5.0, DriveType.R, EngineType.P, 11.0, 0.45, 0, 30.0, 0.8, 0.08, 0.0, 0.28, -0.20, 0.4, 0.3, 0.20, 0.60, 35000, "40006800", "8000", 1, 1, 0));
    	handlings.add(new Handling("ARTICT1", VehicleType.CAR, 3800.0, 30000.0, 2.0, 0.0, 0.0, -0.5, 90, 0.45, 0.75, 0.5, 5, 120.0, 18.0, 5.0, DriveType.R, EngineType.D, 8.0, 0.30, 0, 25.0, 1.5, 0.05, 0.0, 0.30, -0.15, 0.5, 0.0, 0.65, 0.25, 35000, "20002000", "0000", 0, 1, 0));
    	handlings.add(new Handling("PREVION", VehicleType.CAR, 1400.0, 3000.0, 2.0, 0.0, 0.3, -0.1, 70, 0.70, 0.80, 0.45, 4, 160.0, 18.0, 7.0, DriveType.F, EngineType.P, 8.0, 0.65, 0, 35.0, 1.1, 0.08, 2.0, 0.31, -0.18, 0.55, 0.3, 0.21, 0.50, 9000, "0", "0", 0, 0, 0));
    	handlings.add(new Handling("COACH", VehicleType.CAR, 9500.0, 57324.6, 1.8, 0.0, 0.0, 0.0, 90, 0.65, 0.85, 0.35, 5, 160.0, 18.0, 10.0, DriveType.R, EngineType.D, 5.7, 0.35, 0, 30.0, 1.5, 0.04, 0.0, 0.45, -0.25, 0.5, 0.0, 0.45, 0.48, 20000, "2", "200000", 0, 1, 15));
    	handlings.add(new Handling("CABBIE", VehicleType.CAR, 1750.0, 4351.7, 2.9, 0.0, 0.1, -0.15, 75, 0.75, 0.85, 0.51, 4, 160.0, 24.0, 6.0, DriveType.R, EngineType.P, 7.0, 0.44, 0, 40.0, 0.7, 0.06, 2.0, 0.25, -0.30, 0.5, 0.5, 0.2, 0.40, 10000, "0", "0", 1, 1, 0));
    	handlings.add(new Handling("STALLION", VehicleType.CAR, 1600.0, 3921.3, 2.0, 0.0, 0.0, -0.15, 70, 0.80, 0.75, 0.55, 4, 160.0, 23.0, 5.0, DriveType.R, EngineType.P, 8.17, 0.52, 0, 35.0, 1.2, 0.1, 0.0, 0.30, -0.2, 0.5, 0.0, 0.3, 0.64, 19000, "2800", "4", 1, 1, 0));
    	handlings.add(new Handling("RUMPO", VehicleType.CAR, 2000.0, 4901.7, 2.4, 0.0, 0.4, -0.1, 85, 0.6, 0.75, 0.52, 5, 160.0, 18.0, 15.0, DriveType.F, EngineType.P, 5.5, 0.45, 0, 30.0, 1.4, 0.05, 0.0, 0.43, -0.11, 0.5, 0.0, 0.2, 0.60, 26000, "1", "0", 0, 1, 13));
    	handlings.add(new Handling("RCBANDIT", VehicleType.CAR, 100.0, 24.1, 6.0, 0.0, 0.05, -0.1, 70, 0.80, 0.90, 0.49, 1, 75.0, 35.0, 5.0, DriveType.FOUR, EngineType.E, 5.5, 0.50, 0, 25.0, 1.6, 0.1, 0.0, 0.28, -0.08, 0.5, 0.0, 0.2, 0.05, 500, "440000", "0", 0, 1, 0));
    	handlings.add(new Handling("ROMERO", VehicleType.CAR, 2500.0, 5960.4, 2.0, 0.0, -0.8, 0.2, 70, 0.75, 0.80, 0.50, 5, 150.0, 16.0, 15.0, DriveType.R, EngineType.P, 4.0, 0.80, 0, 30.0, 1.0, 0.1, 0.0, 0.35, -0.15, 0.4, 0.0, 0.2, 1.25, 10000, "40000020", "0", 0, 1, 0));
    	handlings.add(new Handling("PACKER", VehicleType.CAR, 8000.0, 48273.3, 2.0, 0.0, 0.0, 0.0, 90, 0.65, 0.85, 0.35, 5, 150.0, 13.0, 5.0, DriveType.R, EngineType.D, 5.7, 0.35, 0, 30.0, 1.5, 0.04, 0.0, 0.45, -0.25, 0.5, 0.0, 0.56, 0.40, 20000, "4000", "440000", 0, 1, 2));
    	handlings.add(new Handling("MONSTER", VehicleType.CAR, 5000.0, 20000.0, 3.0, 0.0, 0.0, -0.35, 80, 0.65, 0.85, 0.55, 5, 110.0, 45.0, 25.0, DriveType.FOUR, EngineType.P, 7.0, 0.45, 0, 35.0, 1.5, 0.07, 0.0, 0.45, -0.30, 0.5, 0.3, 0.44, 0.35, 40000, "28", "1300045", 0, 1, 20));
    	handlings.add(new Handling("ADMIRAL", VehicleType.CAR, 1650.0, 3851.4, 2.0, 0.0, 0.0, -0.05, 75, 0.65, 0.90, 0.51, 5, 165.0, 22.0, 8.0, DriveType.F, EngineType.P, 8.5, 0.52, 0, 30.0, 1.0, 0.15, 0.0, 0.27, -0.19, 0.5, 0.55, 0.2, 0.56, 35000, "0", "400000", 0, 1, 0));
    	handlings.add(new Handling("TRAM", VehicleType.CAR, 1900.0, 4795.9, 1.0, 0.0, -0.3, 0.0, 85, 0.97, 0.77, 0.51, 5, 150.0, 25.0, 5.0, DriveType.R, EngineType.P, 8.5, 0.45, 0, 30.0, 1.3, 0.08, 0.0, 0.0, -1.00, 0.4, 0.5, 0.2, 0.50, 26000, "1", "400000", 0, 1, 13));
    	handlings.add(new Handling("AIRTRAIN", VehicleType.CAR, 25500.0, 139272.5, 1.0, 0.0, 0.0, 0.0, 85, 0.58, 0.7, 0.46, 5, 140.0, 24.0, 5.0, DriveType.FOUR, EngineType.D, 10.00, 0.45, 0, 27.0, 1.2, 0.05, 0.0, 0.47, -0.17, 0.5, 0.0, 0.2, 1.00, 10000, "0", "0", 0, 1, 0));
    	handlings.add(new Handling("ARTICT2", VehicleType.CAR, 3800.0, 30000.0, 2.0, 0.0, 0.0, -0.5, 90, 0.45, 0.75, 0.5, 5, 120.0, 18.0, 5.0, DriveType.R, EngineType.D, 8.0, 0.30, 0, 25.0, 1.5, 0.05, 0.0, 0.30, -0.15, 0.5, 0.0, 0.65, 0.25, 35000, "20002000", "0000", 0, 1, 0));
    	handlings.add(new Handling("TURISMO", VehicleType.CAR, 1400.0, 3000.0, 2.0, 0.0, -0.3, -0.2, 70, 0.75, 0.85, 0.45, 5, 240.0, 30.0, 10.0, DriveType.FOUR, EngineType.P, 11.0, 0.51, 0, 30.0, 1.2, 0.13, 0.0, 0.15, -0.20, 0.5, 0.4, 0.17, 0.72, 95000, "40002004", "C08401", 1, 1, 1));
    	handlings.add(new Handling("FLATBED", VehicleType.CAR, 8500.0, 48804.2, 2.5, 0.0, 0.0, 0.3, 90, 0.70, 0.7, 0.46, 5, 140.0, 25.0, 80.0, DriveType.R, EngineType.D, 10.00, 0.45, 0, 27.0, 1.2, 0.05, 0.0, 0.47, -0.17, 0.5, 0.0, 0.62, 0.43, 10000, "801", "0", 0, 1, 2));
    	handlings.add(new Handling("YANKEE", VehicleType.CAR, 4500.0, 18003.7, 3.0, 0.0, 0.0, 0.0, 80, 0.55, 0.70, 0.48, 5, 160.0, 14.0, 40.0, DriveType.R, EngineType.D, 4.5, 0.80, 0, 30.0, 1.8, 0.12, 0.0, 0.30, -0.25, 0.5, 0.0, 0.35, 0.45, 22000, "4088", "1", 0, 1, 0));
    	handlings.add(new Handling("GOLFCART", VehicleType.CAR, 1000.0, 1354.2, 4.0, 0.0, 0.0, -0.1, 70, 0.55, 0.85, 0.5, 3, 160.0, 15.0, 30.0, DriveType.FOUR, EngineType.E, 13.0, 0.50, 0, 30.0, 1.0, 0.09, 0.0, 0.28, -0.13, 0.5, 0.0, 0.26, 0.50, 9000, "1100", "8804", 1, 1, 0));
    	handlings.add(new Handling("SOLAIR", VehicleType.CAR, 2000.0, 5500.0, 2.0, 0.0, 0.0, 0.0, 75, 0.75, 0.80, 0.52, 4, 165.0, 20.0, 10.0, DriveType.R, EngineType.P, 5.0, 0.60, 0, 30.0, 1.2, 0.1, 0.0, 0.27, -0.17, 0.5, 0.2, 0.24, 0.48, 18000, "20", "0", 1, 1, 0));
    	handlings.add(new Handling("TOPFUN", VehicleType.CAR, 1900.0, 6333.3, 2.0, 0.0, 0.0, -0.2, 80, 0.85, 0.70, 0.46, 5, 160.0, 15.0, 25.0, DriveType.R, EngineType.D, 6.0, 0.80, 0, 30.0, 1.5, 0.07, 2.0, 0.35, -0.15, 0.4, 0.0, 0.2, 0.43, 20000, "1", "1", 0, 3, 13));
    	handlings.add(new Handling("GLENDALE", VehicleType.CAR, 1600.0, 4000.0, 2.5, 0.0, 0.0, 0.05, 75, 0.60, 0.84, 0.52, 5, 160.0, 22.0, 15.0, DriveType.R, EngineType.P, 6.2, 0.55, 0, 30.0, 0.8, 0.07, 0.0, 0.35, -0.22, 0.5, 0.5, 0.23, 0.40, 20000, "0", "10800002", 1, 1, 0));
    	handlings.add(new Handling("OCEANIC", VehicleType.CAR, 1900.0, 4529.9, 2.0, 0.0, 0.0, 0.0, 75, 0.67, 0.75, 0.52, 5, 160.0, 16.0, 5.0, DriveType.R, EngineType.P, 5.0, 0.55, 0, 30.0, 1.0, 0.1, 0.0, 0.35, -0.17, 0.5, 0.5, 0.23, 0.45, 20000, "0", "10800000", 2, 1, 0));
    	handlings.add(new Handling("PATRIOT", VehicleType.CAR, 2500.0, 7968.7, 2.5, 0.0, 0.0, 0.0, 80, 0.70, 0.85, 0.5, 5, 170.0, 25.0, 20.0, DriveType.FOUR, EngineType.P, 8.0, 0.50, 0, 30.0, 1.5, 0.08, 4.0, 0.35, -0.35, 0.5, 0.0, 0.28, 0.25, 40000, "8", "300000", 0, 1, 0));
    	handlings.add(new Handling("HERMES", VehicleType.CAR, 1950.0, 4712.5, 2.0, 0.0, 0.3, 0.0, 70, 0.70, 0.75, 0.51, 5, 160.0, 18.0, 15.0, DriveType.F, EngineType.P, 3.5, 0.60, 0, 28.0, 1.0, 0.05, 0.0, 0.35, -0.20, 0.58, 0.0, 0.25, 0.42, 19000, "40002000", "1", 1, 3, 0));
    	handlings.add(new Handling("SABRE", VehicleType.CAR, 1700.0, 4000.0, 2.0, 0.0, 0.1, 0.0, 70, 0.70, 0.80, 0.53, 4, 160.0, 24.0, 10.0, DriveType.R, EngineType.P, 8.0, 0.52, 0, 35.0, 1.3, 0.08, 5.0, 0.30, -0.20, 0.5, 0.25, 0.25, 0.52, 19000, "0", "10000006", 1, 1, 0));
    	handlings.add(new Handling("ZR350", VehicleType.CAR, 1400.0, 2979.7, 2.0, 0.0, 0.2, -0.1, 70, 0.80, 0.80, 0.51, 5, 200.0, 28.0, 10.0, DriveType.R, EngineType.P, 11.1, 0.52, 0, 30.0, 1.2, 0.10, 0.0, 0.31, -0.15, 0.5, 0.3, 0.24, 0.60, 45000, "0", "C00000", 1, 1, 0));
    	handlings.add(new Handling("WALTON", VehicleType.CAR, 1850.0, 3534.0, 2.5, 0.0, 0.0, 0.0, 75, 0.70, 0.70, 0.5, 4, 150.0, 14.0, 25.0, DriveType.FOUR, EngineType.D, 6.5, 0.5, 0, 35.0, 1.6, 0.12, 0.0, 0.35, -0.18, 0.4, 0.0, 0.26, 0.19, 26000, "40", "10000006", 1, 1, 0));
    	handlings.add(new Handling("REGINA", VehicleType.CAR, 1500.0, 3800.0, 2.0, 0.0, 0.2, 0.0, 75, 0.65, 0.85, 0.52, 4, 165.0, 16.0, 25.0, DriveType.F, EngineType.P, 5.0, 0.60, 0, 30.0, 1.0, 0.1, 0.0, 0.27, -0.17, 0.5, 0.2, 0.24, 0.48, 18000, "20", "1", 1, 1, 0));
    	handlings.add(new Handling("COMET", VehicleType.CAR, 1400.0, 2200.0, 2.2, 0.0, 0.1, -0.2, 75, 0.70, 0.9, 0.5, 5, 200.0, 30.0, 10.0, DriveType.FOUR, EngineType.P, 11.0, 0.45, 0, 30.0, 1.4, 0.14, 3.0, 0.28, -0.15, 0.5, 0.3, 0.25, 0.60, 35000, "40000800", "0", 1, 1, 19));
    	handlings.add(new Handling("BURRITO", VehicleType.CAR, 1900.0, 5000.0, 2.5, 0.0, 0.0, -0.2, 85, 0.60, 0.87, 0.51, 5, 150.0, 25.0, 20.0, DriveType.R, EngineType.P, 8.5, 0.45, 0, 30.0, 1.3, 0.07, 2.0, 0.40, -0.25, 0.4, 0.5, 0.2, 0.50, 26000, "1", "400000", 0, 1, 13));
    	handlings.add(new Handling("CAMPER", VehicleType.CAR, 1900.0, 4000.0, 2.6, 0.0, -0.5, -0.4, 85, 0.60, 0.80, 0.46, 5, 120.0, 16.0, 20.0, DriveType.R, EngineType.P, 8.5, 0.45, 0, 30.0, 1.1, 0.08, 0.0, 0.35, -0.10, 0.4, 0.5, 0.2, 0.50, 26000, "4000c000", "400000", 0, 1, 0));
    	handlings.add(new Handling("BAGGAGE", VehicleType.CAR, 1000.0, 1354.2, 5.0, 0.0, 0.4, -0.2, 70, 1.00, 0.85, 0.5, 3, 160.0, 20.0, 30.0, DriveType.R, EngineType.E, 5.0, 0.50, 0, 30.0, 2.0, 0.09, 0.0, 0.25, -0.10, 0.5, 0.0, 0.26, 0.50, 9000, "3300", "4", 1, 1, 0));
    	handlings.add(new Handling("DOZER", VehicleType.CAR, 10000.0, 35000.0, 20.0, 0.0, -0.5, -0.5, 90, 0.85, 0.8, 0.60, 5, 100.0, 35.0, 150.0, DriveType.FOUR, EngineType.D, 5.0, 0.40, 0, 45.0, 1.4, 0.15, 0.0, 0.25, -0.20, 0.35, 0.0, 0.45, 0.20, 5000, "308", "340220", 0, 1, 17));
    	handlings.add(new Handling("RANCHER", VehicleType.CAR, 2500.0, 7604.2, 2.5, 0.0, 0.0, -0.35, 80, 0.70, 0.85, 0.54, 5, 170.0, 20.0, 5.0, DriveType.FOUR, EngineType.P, 7.0, 0.45, 0, 35.0, 0.8, 0.08, 0.0, 0.45, -0.25, 0.45, 0.3, 0.44, 0.35, 40000, "4020", "100004", 0, 1, 0));
    	handlings.add(new Handling("FBIRANCH", VehicleType.CAR, 3500.0, 11156.2, 2.2, 0.0, 0.0, -0.2, 80, 0.80, 0.80, 0.52, 5, 170.0, 22.0, 5.0, DriveType.FOUR, EngineType.P, 8.5, 0.50, 0, 30.0, 0.7, 0.15, 0.0, 0.34, -0.20, 0.5, 0.5, 0.44, 0.30, 40000, "4020", "500000", 0, 1, 0));
    	handlings.add(new Handling("VIRGO", VehicleType.CAR, 1700.0, 3435.4, 2.0, 0.0, 0.0, -0.1, 70, 0.70, 0.86, 0.5, 4, 160.0, 18.0, 15.0, DriveType.R, EngineType.P, 7.0, 0.50, 0, 32.0, 0.8, 0.10, 0.0, 0.31, -0.15, 0.5, 0.5, 0.26, 0.85, 9000, "40000000", "10000000", 0, 0, 0));
    	handlings.add(new Handling("GREENWOO", VehicleType.CAR, 1600.0, 4000.0, 2.5, 0.0, 0.0, 0.0, 70, 0.70, 0.8, 0.52, 4, 160.0, 20.0, 20.0, DriveType.R, EngineType.P, 5.4, 0.60, 0, 30.0, 1.1, 0.12, 5.0, 0.32, -0.20, 0.5, 0.0, 0.22, 0.54, 19000, "0", "10000001", 0, 3, 0));
    	handlings.add(new Handling("HOTRING", VehicleType.CAR, 1600.0, 4500.0, 1.4, 0.0, 0.2, -0.4, 70, 0.85, 0.80, 0.48, 5, 220.0, 26.0, 5.0, DriveType.R, EngineType.P, 10.0, 0.52, 0, 30.0, 1.5, 0.10, 10.0, 0.29, -0.16, 0.6, 0.4, 0.20, 0.56, 45000, "40002004", "C00000", 1, 1, 0));
    	handlings.add(new Handling("SANDKING", VehicleType.CAR, 2000.0, 4000.0, 2.2, 0.0, 0.0, -0.6, 80, 0.75, 0.85, 0.5, 5, 170.0, 28.0, 10.0, DriveType.FOUR, EngineType.P, 8.0, 0.50, 0, 30.0, 0.8, 0.08, 0.0, 0.35, -0.31, 0.5, 0.0, 0.38, 0.35, 40000, "0", "318800", 0, 1, 22));
    	handlings.add(new Handling("BLISTAC", VehicleType.CAR, 1000.0, 2141.7, 2.4, 0.0, 0.0, -0.10, 50, 0.85, 0.85, 0.5, 5, 200.0, 26.0, 5.0, DriveType.F, EngineType.P, 11.0, 0.45, 0, 30.0, 1.4, 0.1, 0.0, 0.28, -0.12, 0.5, 0.0, 0.25, 0.50, 35000, "c0000000", "C00000", 1, 1, 0));
    	handlings.add(new Handling("BOXVILLE", VehicleType.CAR, 5500.0, 23489.6, 3.0, 0.0, 0.0, 0.0, 80, 0.82, 0.70, 0.46, 5, 140.0, 14.0, 25.0, DriveType.R, EngineType.D, 4.5, 0.60, 0, 30.0, 0.9, 0.08, 0.0, 0.25, -0.25, 0.35, 0.6, 0.26, 0.40, 22000, "4009", "201", 0, 3, 13));
    	handlings.add(new Handling("BENSON", VehicleType.CAR, 3500.0, 13865.8, 2.3, 0.0, 0.0, -0.2, 80, 0.75, 0.70, 0.46, 5, 140.0, 14.0, 20.0, DriveType.R, EngineType.D, 4.5, 0.60, 0, 30.0, 1.2, 0.20, 0.0, 0.35, -0.15, 0.45, 0.0, 0.46, 0.53, 22000, "4088", "1", 1, 3, 0));
    	handlings.add(new Handling("MESA", VehicleType.CAR, 1300.0, 1900.0, 3.0, 0.0, 0.2, -0.3, 85, 0.70, 0.80, 0.50, 5, 160.0, 24.0, 15.0, DriveType.FOUR, EngineType.D, 8.0, 0.50, 0, 35.0, 1.2, 0.08, 0.0, 0.32, -0.20, 0.35, 0.4, 0.18, 0.30, 25000, "200840", "0", 0, 1, 0));
    	handlings.add(new Handling("BLOODRA", VehicleType.CAR, 2100.0, 5146.7, 2.0, 0.0, 0.0, 0.0, 75, 0.75, 0.70, 0.52, 5, 160.0, 24.0, 5.0, DriveType.R, EngineType.P, 6.2, 0.55, 0, 35.0, 1.0, 0.06, 3.0, 0.35, -0.24, 0.5, 0.0, 0.28, 0.30, 500, "100", "10800200", 1, 1, 25));
    	handlings.add(new Handling("BLOODRB", VehicleType.CAR, 1600.0, 3866.7, 2.0, 0.0, 0.0, 0.0, 75, 0.80, 0.70, 0.52, 5, 160.0, 26.0, 5.0, DriveType.R, EngineType.P, 6.2, 0.55, 0, 35.0, 1.0, 0.06, 0.0, 0.35, -0.17, 0.5, 0.0, 0.28, 0.30, 500, "0", "800200", 1, 1, 0));
    	handlings.add(new Handling("SUPERGT", VehicleType.CAR, 1400.0, 2800.0, 2.0, 0.0, -0.2, -0.24, 70, 0.75, 0.86, 0.48, 5, 230.0, 26.0, 5.0, DriveType.R, EngineType.P, 8.0, 0.52, 0, 30.0, 1.0, 0.20, 0.0, 0.25, -0.10, 0.5, 0.3, 0.40, 0.54, 105000, "40002004", "208000", 0, 0, 1));
    	handlings.add(new Handling("ELEGANT", VehicleType.CAR, 2200.0, 5000.0, 1.8, 0.0, 0.1, -0.1, 75, 0.70, 0.80, 0.46, 5, 165.0, 20.0, 10.0, DriveType.R, EngineType.P, 6.0, 0.55, 0, 30.0, 1.0, 0.10, 0.0, 0.35, -0.15, 0.5, 0.3, 0.2, 0.30, 35000, "40000000", "10400000", 0, 1, 0));
    	handlings.add(new Handling("JOURNEY", VehicleType.CAR, 3500.0, 13865.8, 3.0, 0.0, 0.0, 0.0, 80, 0.62, 0.70, 0.46, 5, 140.0, 14.0, 25.0, DriveType.R, EngineType.D, 4.5, 0.60, 0, 30.0, 1.5, 0.11, 0.0, 0.30, -0.15, 0.5, 0.0, 0.46, 0.53, 22000, "88", "1", 0, 3, 0));
    	handlings.add(new Handling("PETROL", VehicleType.CAR, 3800.0, 20000.0, 2.0, 0.0, 0.0, -0.2, 90, 0.85, 0.75, 0.4, 5, 120.0, 25.0, 20.0, DriveType.R, EngineType.D, 8.0, 0.30, 0, 35.0, 1.0, 0.10, 0.0, 0.25, -0.20, 0.5, 0.0, 0.35, 0.25, 35000, "20206008", "1000001", 0, 1, 2));
    	handlings.add(new Handling("RDTRAIN", VehicleType.CAR, 5000.0, 28000.0, 2.0, 0.0, 0.5, -0.4, 90, 0.95, 0.65, 0.4, 5, 120.0, 25.0, 20.0, DriveType.R, EngineType.D, 8.0, 0.30, 0, 25.0, 0.7, 0.10, 0.0, 0.20, -0.17, 0.5, 0.0, 0.65, 0.25, 35000, "20200008", "200", 0, 1, 2));
    	handlings.add(new Handling("NEBULA", VehicleType.CAR, 1400.0, 4000.0, 2.0, 0.0, 0.3, -0.1, 75, 0.65, 0.80, 0.50, 5, 165.0, 20.0, 10.0, DriveType.F, EngineType.P, 8.0, 0.55, 0, 30.0, 1.4, 0.10, 0.0, 0.27, -0.10, 0.58, 0.3, 0.2, 0.56, 35000, "0", "400000", 0, 1, 0));
    	handlings.add(new Handling("MAJESTIC", VehicleType.CAR, 1400.0, 3267.8, 2.2, 0.0, 0.1, -0.1, 75, 0.75, 0.80, 0.52, 5, 165.0, 22.0, 10.0, DriveType.R, EngineType.P, 7.0, 0.55, 0, 30.0, 1.3, 0.13, 0.0, 0.27, -0.15, 0.5, 0.3, 0.2, 0.56, 35000, "400000", "10400000", 0, 1, 0));
    	handlings.add(new Handling("BUCCANEE", VehicleType.CAR, 1700.0, 4500.0, 2.2, 0.0, 0.3, 0.0, 70, 0.60, 0.86, 0.54, 4, 160.0, 24.0, 15.0, DriveType.R, EngineType.P, 5.0, 0.52, 0, 35.0, 0.8, 0.08, 0.0, 0.20, -0.20, 0.54, 0.4, 0.3, 0.52, 19000, "40400004", "4", 1, 1, 1));
    	handlings.add(new Handling("CEMENT", VehicleType.CAR, 5500.0, 33187.9, 2.0, 0.0, 0.0, 0.0, 90, 0.58, 0.8, 0.5, 4, 110.0, 20.0, 20.0, DriveType.R, EngineType.D, 03.17, 0.40, 0, 30.0, 1.4, 0.06, 0.0, 0.45, -0.25, 0.55, 0.0, 0.45, 0.20, 5000, "404008", "40200", 0, 1, 0));
    	handlings.add(new Handling("TOWTRUCK", VehicleType.CAR, 3500.0, 12000.0, 2.5, 0.0, 0.3, -0.25, 80, 0.85, 0.70, 0.46, 5, 160.0, 25.0, 30.0, DriveType.R, EngineType.D, 6.0, 0.80, 0, 45.0, 1.6, 0.07, 0.0, 0.35, -0.15, 0.25, 0.0, 0.2, 0.50, 20000, "240001", "1148200", 0, 3, 13));
    	handlings.add(new Handling("FORTUNE", VehicleType.CAR, 1700.0, 4166.4, 2.0, 0.0, 0.0, -0.2, 70, 0.70, 0.84, 0.53, 4, 160.0, 20.0, 10.0, DriveType.R, EngineType.P, 8.17, 0.52, 0, 35.0, 1.2, 0.15, 0.0, 0.30, -0.10, 0.5, 0.25, 0.3, 0.52, 19000, "40000000", "4", 1, 1, 0));
    	handlings.add(new Handling("CADRONA", VehicleType.CAR, 1200.0, 2000.0, 2.2, 0.0, 0.15, -0.1, 70, 0.70, 0.86, 0.5, 4, 160.0, 20.0, 5.0, DriveType.R, EngineType.P, 8.0, 0.60, 0, 30.0, 1.4, 0.12, 0.0, 0.30, -0.08, 0.5, 0.0, 0.26, 0.50, 9000, "40000000", "2", 0, 0, 0));
    	handlings.add(new Handling("FBITRUCK", VehicleType.CAR, 4000.0, 10000.0, 2.0, 0.0, 0.0, -0.2, 85, 0.65, 0.85, 0.54, 5, 170.0, 25.0, 25.0, DriveType.FOUR, EngineType.D, 6.0, 0.40, 0, 30.0, 0.8, 0.1, 0.0, 0.30, -0.15, 0.5, 0.0, 0.32, 0.16, 40000, "4001", "0", 0, 1, 13));
    	handlings.add(new Handling("WILLARD", VehicleType.CAR, 1800.0, 4350.0, 2.0, 0.0, 0.0, 0.0, 70, 0.70, 0.8, 0.52, 4, 160.0, 18.0, 15.0, DriveType.R, EngineType.P, 5.4, 0.60, 0, 30.0, 1.1, 0.15, 0.0, 0.32, -0.14, 0.5, 0.0, 0.26, 0.54, 19000, "40000000", "0", 0, 3, 0));
    	handlings.add(new Handling("FORKLIFT", VehicleType.CAR, 1000.0, 1354.2, 2.0, 0.0, -0.2, -0.35, 70, 0.80, 0.85, 0.5, 3, 60.0, 20.0, 15.0, DriveType.F, EngineType.E, 6.0, 0.50, 0, 30.0, 2.0, 0.14, 0.0, 0.25, -0.20, 0.5, 0.0, 0.26, 0.50, 9000, "1300", "1040024", 1, 1, 0));
    	handlings.add(new Handling("TRACTOR", VehicleType.CAR, 2000.0, 5000.0, 3.0, 0.0, 0.0, -0.2, 70, 0.90, 0.85, 0.5, 4, 70.0, 20.0, 90.0, DriveType.R, EngineType.D, 15.0, 0.20, 0, 50.0, 2.0, 0.12, 0.0, 0.25, -0.05, 0.5, 0.0, 0.26, 0.50, 9000, "201310", "1340005", 1, 1, 28));
    	handlings.add(new Handling("COMBINE", VehicleType.CAR, 8500.0, 48804.2, 5.0, 0.0, 0.3, -0.2, 90, 0.88, 0.7, 0.46, 5, 140.0, 25.0, 80.0, DriveType.FOUR, EngineType.D, 10.00, 0.45, 0, 27.0, 1.2, 0.10, 0.0, 0.47, -0.11, 0.5, 0.0, 1.20, 0.43, 10000, "12c008", "20", 0, 1, 20));
    	handlings.add(new Handling("FELTZER", VehicleType.CAR, 1600.0, 4500.0, 2.5, 0.0, 0.0, -0.15, 75, 0.65, 0.9, 0.5, 5, 200.0, 28.0, 25.0, DriveType.R, EngineType.P, 7.0, 0.52, 0, 30.0, 1.1, 0.09, 0.0, 0.30, -0.10, 0.5, 0.3, 0.25, 0.60, 35000, "40002800", "0", 1, 1, 19));
    	handlings.add(new Handling("REMINGTN", VehicleType.CAR, 1800.0, 4000.0, 2.0, 0.0, -0.4, -0.2, 70, 0.75, 0.80, 0.56, 5, 160.0, 23.0, 5.0, DriveType.R, EngineType.P, 6.50, 0.50, 0, 30.0, 0.5, 0.10, 0.0, 0.00, -0.20, 0.4, 0.6, 0.21, 0.41, 30000, "40002004", "2410000", 1, 1, 1));
    	handlings.add(new Handling("SLAMVAN", VehicleType.CAR, 1950.0, 4712.5, 4.0, 0.0, 0.1, 0.0, 70, 0.65, 0.90, 0.50, 5, 160.0, 40.0, 10.0, DriveType.R, EngineType.P, 10.0, 0.50, 0, 28.0, 1.6, 0.12, 0.0, 0.35, -0.14, 0.5, 0.3, 0.36, 0.42, 19000, "40002000", "2010000", 1, 3, 0));
    	handlings.add(new Handling("BLADE", VehicleType.CAR, 1500.0, 2500.0, 2.0, 0.0, -0.2, 0.1, 70, 0.75, 0.84, 0.53, 4, 160.0, 24.0, 5.0, DriveType.R, EngineType.P, 8.17, 0.52, 0, 35.0, 1.0, 0.10, 0.0, 0.30, -0.15, 0.44, 0.25, 0.3, 0.52, 19000, "40202000", "12010000", 1, 1, 0));
    	handlings.add(new Handling("FREIGHT", VehicleType.CAR, 5500.0, 65000.0, 3.0, 0.0, 0.0, 0.0, 90, 0.58, 0.8, 0.5, 4, 110.0, 20.0, 20.0, DriveType.R, EngineType.D, 03.17, 0.40, 0, 30.0, 1.4, 0.06, 0.0, 0.45, 0.0, 0.55, 0.0, 0.45, 0.20, 5000, "8", "200", 0, 1, 2));
    	handlings.add(new Handling("STREAK", VehicleType.CAR, 5500.0, 65000.0, 3.0, 0.0, 0.0, 0.0, 90, 0.58, 0.8, 0.5, 4, 110.0, 20.0, 20.0, DriveType.R, EngineType.D, 03.17, 0.40, 0, 30.0, 1.4, 0.06, 0.0, 0.45, -0.10, 0.55, 0.0, 0.65, 0.20, 5000, "8", "200", 0, 1, 2));
    	handlings.add(new Handling("VINCENT", VehicleType.CAR, 1800.0, 3000.0, 2.0, 0.0, 0.3, 0.0, 70, 0.70, 0.8, 0.5, 4, 160.0, 18.0, 20.0, DriveType.F, EngineType.P, 5.4, 0.60, 0, 30.0, 1.0, 0.09, 0.0, 0.32, -0.16, 0.56, 0.0, 0.26, 0.54, 19000, "0", "2", 0, 3, 0));
    	handlings.add(new Handling("BULLET", VehicleType.CAR, 1200.0, 2500.0, 1.8, 0.0, -0.15, -0.2, 70, 0.75, 0.90, 0.48, 5, 230.0, 30.0, 10.0, DriveType.R, EngineType.P, 8.0, 0.58, 0, 30.0, 1.0, 0.13, 5.0, 0.25, -0.10, 0.45, 0.3, 0.15, 0.54, 105000, "c0002004", "204000", 0, 0, 1));
    	handlings.add(new Handling("CLOVER", VehicleType.CAR, 1600.0, 3000.0, 2.2, 0.0, 0.0, 0.0, 70, 0.65, 0.80, 0.52, 4, 160.0, 24.0, 10.0, DriveType.R, EngineType.P, 8.0, 0.5, 0, 35.0, 1.0, 0.10, 0.0, 0.30, -0.10, 0.5, 0.25, 0.25, 0.52, 19000, "40280000", "10008004", 1, 1, 0));
    	handlings.add(new Handling("SADLER", VehicleType.CAR, 1700.0, 4500.0, 2.7, 0.0, 0.0, -0.05, 75, 0.65, 0.70, 0.5, 5, 165.0, 25.0, 20.0, DriveType.FOUR, EngineType.D, 8.5, 0.5, 0, 35.0, 0.8, 0.08, 3.0, 0.25, -0.15, 0.4, 0.4, 0.26, 0.20, 26000, "200040", "104004", 0, 1, 0));
    	handlings.add(new Handling("RANGER", VehicleType.CAR, 1700.0, 4108.3, 2.5, 0.0, 0.0, 0.0, 85, 0.85, 0.85, 0.51, 5, 160.0, 20.0, 5.0, DriveType.FOUR, EngineType.D, 6.2, 0.60, 0, 35.0, 1.7, 0.08, 0.0, 0.25, -0.15, 0.5, 0.25, 0.27, 0.23, 25000, "20", "700000", 0, 1, 0));
    	handlings.add(new Handling("HUSTLER", VehicleType.CAR, 1700.0, 4000.0, 2.5, 0.0, 0.0, -0.05, 75, 0.75, 0.75, 0.52, 5, 160.0, 22.0, 10.0, DriveType.R, EngineType.P, 8.0, 0.50, 0, 30.0, 0.45, 0.1, 0.0, 0.10, -0.15, 0.5, 0.5, 0.18, 0.45, 20000, "0", "800000", 2, 1, 0));
    	handlings.add(new Handling("INTRUDER", VehicleType.CAR, 1800.0, 4350.0, 2.0, 0.0, 0.0, 0.0, 70, 0.70, 0.8, 0.49, 5, 160.0, 18.0, 25.0, DriveType.R, EngineType.P, 5.4, 0.60, 0, 30.0, 1.0, 0.09, 0.0, 0.32, -0.15, 0.54, 0.0, 0.26, 0.54, 19000, "0", "2", 0, 3, 0));
    	handlings.add(new Handling("PRIMO", VehicleType.CAR, 1600.0, 3300.0, 2.2, 0.0, 0.0, 0.0, 70, 0.70, 0.8, 0.54, 4, 160.0, 18.0, 7.0, DriveType.R, EngineType.P, 5.4, 0.60, 0, 30.0, 1.1, 0.14, 0.0, 0.32, -0.14, 0.5, 0.0, 0.26, 0.54, 19000, "0", "0", 0, 3, 0));
    	handlings.add(new Handling("TAMPA", VehicleType.CAR, 1700.0, 4166.4, 2.5, 0.0, 0.15, 0.0, 70, 0.60, 0.85, 0.52, 4, 160.0, 24.0, 10.0, DriveType.R, EngineType.P, 8.17, 0.52, 0, 35.0, 0.7, 0.08, 3.0, 0.30, -0.16, 0.5, 0.50, 0.3, 0.52, 19000, "40000004", "4", 1, 1, 1));
    	handlings.add(new Handling("SUNRISE", VehicleType.CAR, 1600.0, 3550.0, 2.0, 0.0, 0.3, 0.0, 70, 0.70, 0.8, 0.52, 5, 160.0, 17.0, 5.0, DriveType.F, EngineType.P, 5.4, 0.60, 0, 30.0, 1.0, 0.09, 0.0, 0.30, -0.12, 0.55, 0.0, 0.26, 0.54, 19000, "40000000", "1", 0, 3, 0));
    	handlings.add(new Handling("MERIT", VehicleType.CAR, 1800.0, 4500.0, 2.2, 0.0, 0.2, -0.10, 75, 0.65, 0.8, 0.49, 5, 165.0, 22.0, 10.0, DriveType.R, EngineType.P, 9.0, 0.55, 0, 30.0, 1.1, 0.15, 0.0, 0.27, -0.08, 0.54, 0.3, 0.2, 0.56, 35000, "40000000", "400001", 0, 1, 0));
    	handlings.add(new Handling("UTILITY", VehicleType.CAR, 2600.0, 8666.7, 3.0, 0.0, 0.0, 0.0, 80, 0.85, 0.70, 0.46, 5, 160.0, 18.0, 10.0, DriveType.R, EngineType.D, 6.0, 0.80, 0, 30.0, 1.8, 0.07, 0.0, 0.35, -0.18, 0.25, 0.0, 0.2, 0.50, 20000, "1", "0", 0, 3, 13));
    	handlings.add(new Handling("YOSEMITE", VehicleType.CAR, 3000.0, 6000.0, 3.0, 0.0, 0.35, 0.0, 80, 0.60, 0.80, 0.4, 5, 170.0, 25.0, 15.0, DriveType.R, EngineType.P, 8.5, 0.30, 0, 30.0, 1.0, 0.12, 0.0, 0.24, -0.20, 0.5, 0.5, 0.44, 0.30, 40000, "20200020", "504400", 0, 1, 0));
    	handlings.add(new Handling("WINDSOR", VehicleType.CAR, 1500.0, 3500.0, 3.0, 0.0, 0.05, -0.2, 75, 0.55, 0.85, 0.5, 5, 180.0, 30.0, 10.0, DriveType.R, EngineType.P, 8.0, 0.45, 0, 30.0, 0.65, 0.07, 0.0, 0.15, -0.10, 0.5, 0.3, 0.25, 0.60, 35000, "40282804", "0", 1, 1, 1));
    	handlings.add(new Handling("MTRUCK_A", VehicleType.CAR, 5000.0, 20000.0, 3.0, 0.0, 0.0, -0.35, 80, 0.65, 0.85, 0.55, 5, 110.0, 45.0, 25.0, DriveType.FOUR, EngineType.P, 7.0, 0.45, 0, 35.0, 1.5, 0.07, 0.0, 0.45, -0.30, 0.5, 0.3, 0.44, 0.35, 40000, "28", "1300045", 0, 1, 20));
    	handlings.add(new Handling("MTRUCK_B", VehicleType.CAR, 5000.0, 20000.0, 3.0, 0.0, 0.0, -0.35, 80, 0.65, 0.85, 0.55, 5, 110.0, 45.0, 25.0, DriveType.FOUR, EngineType.P, 7.0, 0.45, 0, 35.0, 1.5, 0.07, 0.0, 0.45, -0.30, 0.5, 0.3, 0.44, 0.35, 40000, "28", "1300045", 0, 1, 20));
    	handlings.add(new Handling("URANUS", VehicleType.CAR, 1400.0, 2998.3, 2.0, 0.0, 0.1, -0.3, 75, 0.80, 0.85, 0.47, 5, 200.0, 20.0, 5.0, DriveType.R, EngineType.P, 8.0, 0.45, 0, 30.0, 1.3, 0.15, 0.0, 0.28, -0.10, 0.5, 0.3, 0.25, 0.60, 35000, "c0002800", "4000001", 1, 1, 0));
    	handlings.add(new Handling("JESTER", VehicleType.CAR, 1500.0, 3600.0, 2.2, 0.0, 0.0, -0.05, 75, 0.85, 0.8, 0.5, 5, 200.0, 28.0, 10.0, DriveType.F, EngineType.P, 10.0, 0.45, 0, 30.0, 1.1, 0.10, 0.0, 0.28, -0.15, 0.5, 0.3, 0.25, 0.60, 35000, "c0002804", "4000000", 1, 1, 1));
    	handlings.add(new Handling("SULTAN", VehicleType.CAR, 1400.0, 3400.0, 2.4, 0.0, 0.1, -0.1, 75, 0.80, 0.8, 0.5, 5, 200.0, 28.0, 5.0, DriveType.FOUR, EngineType.P, 10.0, 0.5, 0, 30.0, 1.2, 0.15, 0.0, 0.28, -0.20, 0.5, 0.3, 0.25, 0.60, 35000, "2800", "4000002", 1, 1, 0));
    	handlings.add(new Handling("STRATUM", VehicleType.CAR, 1800.0, 4500.0, 2.1, 0.0, 0.1, -0.1, 75, 0.60, 0.85, 0.5, 5, 200.0, 20.0, 10.0, DriveType.R, EngineType.P, 7.0, 0.5, 0, 30.0, 1.0, 0.15, 0.0, 0.28, -0.16, 0.5, 0.3, 0.25, 0.60, 35000, "2800", "4000000", 1, 1, 0));
    	handlings.add(new Handling("ELEGY", VehicleType.CAR, 1500.0, 3500.0, 2.2, 0.0, 0.3, -0.15, 75, 0.65, 0.9, 0.5, 5, 200.0, 28.0, 5.0, DriveType.R, EngineType.P, 8.0, 0.5, 0, 35.0, 1.0, 0.20, 0.0, 0.28, -0.10, 0.5, 0.3, 0.25, 0.60, 35000, "40002804", "4000001", 1, 1, 1));
    	handlings.add(new Handling("RCTIGER", VehicleType.CAR, 100.0, 24.1, 5.0, 0.0, 0.0, -0.1, 70, 0.70, 0.9, 0.49, 1, 75.0, 35.0, 15.0, DriveType.FOUR, EngineType.E, 5.0, 0.50, 0, 45.0, 1.6, 0.1, 0.0, 0.28, -0.14, 0.5, 0.0, 0.2, 0.05, 500, "0", "40", 0, 1, 0));
    	handlings.add(new Handling("FLASH", VehicleType.CAR, 1400.0, 2998.3, 2.2, 0.0, 0.2, -0.1, 75, 0.75, 0.9, 0.5, 5, 200.0, 24.0, 10.0, DriveType.F, EngineType.P, 8.0, 0.55, 0, 30.0, 1.4, 0.15, 0.0, 0.28, -0.10, 0.5, 0.3, 0.25, 0.60, 35000, "2804", "4000001", 1, 1, 1));
    	handlings.add(new Handling("TAHOMA", VehicleType.CAR, 1800.0, 4000.0, 2.3, 0.0, -0.3, 0.0, 75, 0.75, 0.85, 0.52, 5, 160.0, 24.0, 10.0, DriveType.R, EngineType.P, 7.0, 0.50, 0, 35.0, 1.0, 0.08, 0.0, 0.28, -0.20, 0.45, 0.3, 0.25, 0.60, 35000, "0", "12010000", 1, 1, 0));
    	handlings.add(new Handling("SAVANNA", VehicleType.CAR, 1500.0, 2500.0, 2.0, 0.0, -0.6, 0.1, 70, 0.70, 0.84, 0.55, 4, 160.0, 24.0, 5.0, DriveType.R, EngineType.P, 8.17, 0.52, 0, 35.0, 1.0, 0.10, 0.0, 0.30, -0.15, 0.3, 0.25, 0.3, 0.52, 19000, "200000", "2010000", 1, 1, 0));
    	handlings.add(new Handling("BANDITO", VehicleType.CAR, 1000.0, 2500.3, 4.0, 0.0, 0.0, -0.3, 80, 0.70, 0.88, 0.55, 4, 170.0, 35.0, 5.0, DriveType.R, EngineType.P, 6.1, 0.55, 0, 35.0, 1.0, 0.10, 5.0, 0.25, -0.20, 0.35, 0.0, 0.60, 0.40, 15000, "40001b04", "308400", 1, 2, 1));
    	handlings.add(new Handling("FREIFLAT", VehicleType.CAR, 5500.0, 33187.9, 1.0, 0.0, 0.0, 0.0, 90, 0.58, 0.8, 0.5, 4, 110.0, 20.0, 20.0, DriveType.R, EngineType.D, 03.17, 0.40, 0, 30.0, 1.4, 0.06, 0.0, 0.45, 0.0, 0.55, 0.0, 0.45, 0.20, 5000, "8", "200", 0, 1, 0));
    	handlings.add(new Handling("CSTREAK", VehicleType.CAR, 5500.0, 33187.9, 1.0, 0.0, 0.0, 0.0, 90, 0.58, 0.8, 0.5, 4, 110.0, 20.0, 20.0, DriveType.R, EngineType.D, 03.17, 0.40, 0, 30.0, 1.4, 0.06, 0.0, 0.45, -0.10, 0.55, 0.0, 0.65, 0.20, 5000, "8", "200", 0, 1, 21));
    	handlings.add(new Handling("KART", VehicleType.CAR, 300.0, 150.0, 5.0, 0.0, 0.0, -0.15, 110, 0.90, 0.85, 0.48, 4, 90.0, 18.0, 5.0, DriveType.R, EngineType.P, 15.0, 0.20, 0, 35.0, 1.5, 0.20, 0.0, 0.25, -0.04, 0.5, 0.0, 0.38, 0.40, 15000, "40001b00", "308201", 1, 2, 18));
    	handlings.add(new Handling("MOWER", VehicleType.CAR, 800.0, 500.0, 5.0, 0.0, 0.0, -0.3, 80, 0.70, 0.80, 0.48, 3, 60.0, 12.0, 30.0, DriveType.R, EngineType.P, 6.1, 0.55, 0, 35.0, 1.0, 0.15, 0.0, 0.15, -0.05, 0.5, 0.0, 0.38, 0.40, 15000, "40001b00", "1308001", 1, 2, 28));
    	handlings.add(new Handling("DUNE", VehicleType.CAR, 10000.0, 50000.0, 2.0, 0.0, 0.0, -0.6, 80, 0.65, 0.85, 0.5, 5, 110.0, 35.0, 25.0, DriveType.FOUR, EngineType.P, 7.0, 0.45, 0, 35.0, 0.8, 0.10, 0.0, 0.40, -0.40, 0.5, 0.3, 0.29, 0.35, 40000, "6028", "1300005", 0, 1, 2));
    	handlings.add(new Handling("SWEEPER", VehicleType.CAR, 800.0, 632.7, 5.0, 0.0, 0.0, -0.3, 80, 0.70, 0.80, 0.46, 3, 60.0, 12.0, 30.0, DriveType.R, EngineType.P, 6.1, 0.55, 0, 35.0, 1.6, 0.15, 0.0, 0.34, -0.10, 0.5, 0.0, 0.30, 0.40, 15000, "40005a00", "1308201", 1, 2, 0));
    	handlings.add(new Handling("BROADWAY", VehicleType.CAR, 1700.0, 4166.4, 2.0, 0.0, 0.1, 0.1, 70, 0.65, 0.75, 0.46, 4, 160.0, 20.0, 10.0, DriveType.R, EngineType.P, 6.0, 0.55, 0, 25.0, 0.8, 0.07, 0.0, 0.30, -0.14, 0.5, 0.25, 0.3, 0.52, 19000, "220000", "2012100", 1, 1, 0));
    	handlings.add(new Handling("TORNADO", VehicleType.CAR, 1700.0, 4166.4, 2.0, 0.0, -0.1, 0.1, 70, 0.75, 0.75, 0.52, 4, 160.0, 20.0, 10.0, DriveType.R, EngineType.P, 6.0, 0.55, 0, 35.0, 0.8, 0.08, 0.0, 0.30, -0.15, 0.5, 0.25, 0.3, 0.52, 19000, "400220000", "2010000", 1, 1, 0));
    	handlings.add(new Handling("DFT30", VehicleType.CAR, 5500.0, 33187.9, 2.0, 0.0, 0.0, -0.2, 90, 0.65, 0.8, 0.40, 5, 110.0, 20.0, 20.0, DriveType.R, EngineType.D, 3.5, 0.40, 0, 30.0, 1.4, 0.06, 0.0, 0.45, -0.25, 0.55, 0.0, 0.45, 0.20, 5000, "4008", "200", 0, 1, 2));
    	handlings.add(new Handling("HUNTLEY", VehicleType.CAR, 2500.0, 6000.0, 2.5, 0.0, 0.0, -0.20, 80, 0.62, 0.89, 0.5, 5, 160.0, 25.0, 25.0, DriveType.FOUR, EngineType.P, 7.0, 0.45, 0, 35.0, 1.0, 0.05, 0.0, 0.45, -0.21, 0.45, 0.3, 0.44, 0.35, 40000, "20", "4404", 0, 1, 0));
    	handlings.add(new Handling("STAFFORD", VehicleType.CAR, 2200.0, 6000.0, 2.5, 0.0, 0.0, 0.0, 75, 0.65, 0.92, 0.5, 5, 165.0, 24.0, 15.0, DriveType.R, EngineType.P, 5.0, 0.55, 0, 30.0, 1.1, 0.1, 0.0, 0.27, -0.22, 0.5, 0.3, 0.2, 0.56, 35000, "0", "400000", 0, 1, 0));
    	handlings.add(new Handling("NEWSVAN", VehicleType.CAR, 1900.0, 6333.3, 2.0, 0.0, 0.0, -0.15, 80, 0.85, 0.70, 0.46, 5, 160.0, 15.0, 15.0, DriveType.R, EngineType.D, 6.0, 0.80, 0, 30.0, 1.3, 0.07, 0.0, 0.35, -0.15, 0.45, 0.0, 0.2, 0.43, 20000, "4001", "2", 0, 3, 13));
    	handlings.add(new Handling("TUG", VehicleType.CAR, 800.0, 632.7, 5.0, 0.0, 0.0, -0.1, 80, 0.85, 0.80, 0.46, 4, 170.0, 15.0, 30.0, DriveType.R, EngineType.P, 6.1, 0.55, 0, 35.0, 1.2, 0.15, 0.0, 0.34, -0.10, 0.5, 0.0, 0.38, 0.40, 15000, "1a00", "308200", 1, 2, 0));
    	handlings.add(new Handling("PETROTR", VehicleType.CAR, 3800.0, 30000.0, 2.0, 0.0, 0.0, -0.5, 90, 0.45, 0.75, 0.5, 5, 120.0, 18.0, 5.0, DriveType.R, EngineType.D, 8.0, 0.30, 0, 25.0, 1.5, 0.05, 0.0, 0.30, -0.15, 0.5, 0.0, 0.65, 0.25, 35000, "20002000", "0000", 0, 1, 0));
    	handlings.add(new Handling("EMPEROR", VehicleType.CAR, 1800.0, 4000.0, 2.2, 0.0, 0.2, 0.15, 75, 0.65, 0.80, 0.52, 5, 165.0, 21.0, 20.0, DriveType.R, EngineType.P, 8.0, 0.45, 0, 30.0, 0.9, 0.13, 3.0, 0.30, -0.10, 0.5, 0.3, 0.2, 0.56, 35000, "40000000", "400000", 0, 1, 0));
    	handlings.add(new Handling("FLOAT", VehicleType.CAR, 5500.0, 33187.9, 2.0, 0.0, 0.0, 0.0, 90, 0.58, 0.8, 0.5, 4, 110.0, 20.0, 20.0, DriveType.R, EngineType.D, 03.17, 0.40, 0, 30.0, 1.4, 0.06, 0.0, 0.45, -0.25, 0.55, 0.0, 0.45, 0.20, 5000, "8", "200", 0, 1, 0));
    	handlings.add(new Handling("EUROS", VehicleType.CAR, 1400.0, 2998.3, 2.2, 0.0, 0.1, -0.1, 75, 0.70, 0.8, 0.5, 5, 200.0, 24.0, 5.0, DriveType.FOUR, EngineType.P, 8.0, 0.55, 0, 30.0, 1.2, 0.15, 0.0, 0.30, -0.10, 0.5, 0.3, 0.25, 0.60, 35000, "40002804", "0", 1, 1, 0));
    	handlings.add(new Handling("HOTDOG", VehicleType.CAR, 5500.0, 23489.6, 3.0, 0.0, 0.0, 0.3, 80, 0.72, 0.70, 0.46, 5, 140.0, 14.0, 25.0, DriveType.R, EngineType.D, 4.5, 0.60, 0, 30.0, 0.6, 0.08, 0.0, 0.30, -0.24, 0.4, 0.6, 0.36, 0.40, 22000, "40000009", "201", 0, 3, 13));
    	handlings.add(new Handling("CLUB", VehicleType.CAR, 1400.0, 3000.0, 2.8, 0.0, 0.0, 0.0, 80, 0.75, 0.90, 0.49, 5, 200.0, 30.0, 10.0, DriveType.F, EngineType.P, 11.0, 0.45, 0, 30.0, 1.7, 0.1, 0.0, 0.28, -0.12, 0.5, 0.0, 0.25, 0.50, 35000, "2000", "C00000", 1, 1, 0));
    	handlings.add(new Handling("ARTICT3", VehicleType.CAR, 3800.0, 30000.0, 2.0, 0.0, 0.0, -0.5, 90, 0.45, 0.75, 0.5, 5, 120.0, 18.0, 5.0, DriveType.R, EngineType.D, 8.0, 0.30, 0, 25.0, 1.5, 0.05, 0.0, 0.30, -0.15, 0.5, 0.0, 0.65, 0.25, 35000, "20002000", "0000", 0, 1, 0));
    	handlings.add(new Handling("RCCAM", VehicleType.CAR, 100.0, 50.0, 20.0, 0.0, 0.05, -0.2, 70, 0.60, 0.90, 0.49, 1, 60.0, 50.0, 10.0, DriveType.FOUR, EngineType.E, 5.5, 0.50, 0, 25.0, 3.0, 0.3, 0.0, 0.15, -0.15, 0.5, 0.0, 0.2, 0.05, 500, "0", "1000000", 0, 1, 0));
    	handlings.add(new Handling("POLICE_LA", VehicleType.CAR, 1600.0, 4500.0, 2.0, 0.0, 0.3, -0.1, 75, 0.75, 0.85, 0.50, 5, 200.0, 25.0, 10.0, DriveType.R, EngineType.P, 10.0, 0.53, 0, 35.0, 1.0, 0.12, 0.0, 0.28, -0.12, 0.55, 0.0, 0.2, 0.24, 25000, "40000000", "10200008", 0, 1, 0));
    	handlings.add(new Handling("POLICE_SF", VehicleType.CAR, 1600.0, 4500.0, 2.0, 0.0, 0.3, -0.15, 75, 0.75, 0.85, 0.52, 5, 200.0, 25.0, 10.0, DriveType.R, EngineType.P, 10.0, 0.53, 0, 35.0, 1.1, 0.12, 0.0, 0.28, -0.17, 0.55, 0.0, 0.2, 0.24, 25000, "40000000", "10200008", 0, 1, 0));
    	handlings.add(new Handling("POLICE_VG", VehicleType.CAR, 1600.0, 4500.0, 2.0, 0.0, 0.3, -0.1, 75, 0.75, 0.85, 0.52, 5, 200.0, 25.0, 10.0, DriveType.R, EngineType.P, 10.0, 0.53, 0, 35.0, 0.9, 0.08, 0.0, 0.28, -0.17, 0.55, 0.0, 0.2, 0.24, 25000, "40000000", "10200008", 0, 1, 0));
    	handlings.add(new Handling("POLRANGER", VehicleType.CAR, 2500.0, 5500.0, 3.0, 0.0, 0.0, -0.2, 85, 0.65, 0.85, 0.55, 5, 160.0, 30.0, 15.0, DriveType.FOUR, EngineType.D, 6.2, 0.60, 0, 35.0, 0.7, 0.06, 1.0, 0.30, -0.25, 0.5, 0.25, 0.27, 0.23, 25000, "284020", "308800", 0, 1, 0));
    	handlings.add(new Handling("PICADOR", VehicleType.CAR, 1600.0, 3800.0, 2.7, 0.0, 0.2, 0.0, 75, 0.65, 0.70, 0.52, 5, 165.0, 25.0, 20.0, DriveType.R, EngineType.D, 8.5, 0.5, 0, 35.0, 0.8, 0.08, 2.0, 0.25, -0.15, 0.4, 0.4, 0.26, 0.20, 26000, "40200040", "104004", 0, 1, 0));
    	handlings.add(new Handling("SWATVAN", VehicleType.CAR, 5000.0, 10000.0, 2.5, 0.0, 0.0, -0.1, 85, 0.65, 0.7, 0.46, 5, 110.0, 24.0, 25.0, DriveType.FOUR, EngineType.D, 6.4, 0.45, 0, 27.0, 0.7, 0.08, 1.0, 0.30, -0.18, 0.5, 0.0, 0.32, 0.06, 40000, "880010", "1000000", 0, 1, 13));
    	handlings.add(new Handling("ALPHA", VehicleType.CAR, 1500.0, 3400.0, 2.0, 0.0, 0.1, -0.2, 85, 0.7, 0.8, 0.5, 5, 200.0, 23.0, 5.0, DriveType.R, EngineType.P, 7.0, 0.55, 0, 30.0, 1.2, 0.12, 0.0, 0.30, -0.15, 0.5, 0.4, 0.25, 0.50, 35000, "40002800", "200000", 1, 1, 0));
    	handlings.add(new Handling("PHOENIX", VehicleType.CAR, 1500.0, 4000.0, 2.2, 0.0, 0.3, -0.15, 85, 0.7, 0.9, 0.52, 5, 200.0, 26.0, 5.0, DriveType.R, EngineType.P, 6.0, 0.55, 0, 30.0, 0.8, 0.08, 0.0, 0.28, -0.24, 0.59, 0.4, 0.25, 0.50, 35000, "2800", "200000", 1, 1, 0));
    	handlings.add(new Handling("BAGBOXA", VehicleType.CAR, 1000.0, 1354.2, 5.0, 0.0, 0.4, -0.2, 70, 1.00, 0.85, 0.5, 3, 160.0, 20.0, 30.0, DriveType.R, EngineType.E, 5.0, 0.50, 0, 30.0, 2.0, 0.09, 0.0, 0.25, -0.10, 0.5, 0.0, 0.26, 0.50, 9000, "3100", "4", 1, 1, 0));
    	handlings.add(new Handling("BAGBOXB", VehicleType.CAR, 1000.0, 1354.2, 5.0, 0.0, 0.4, -0.2, 70, 1.00, 0.85, 0.5, 3, 160.0, 20.0, 30.0, DriveType.R, EngineType.E, 5.0, 0.50, 0, 30.0, 2.0, 0.09, 0.0, 0.25, -0.10, 0.5, 0.0, 0.26, 0.50, 9000, "3100", "4", 1, 1, 0));
    	handlings.add(new Handling("STAIRS", VehicleType.CAR, 1000.0, 2500.0, 5.0, 0.0, 0.4, -0.2, 70, 1.00, 0.85, 0.5, 3, 160.0, 20.0, 30.0, DriveType.R, EngineType.E, 5.0, 0.50, 0, 30.0, 2.0, 0.09, 0.0, 0.25, -0.10, 0.5, 0.0, 0.26, 0.50, 9000, "3100", "4", 1, 1, 0));
    	handlings.add(new Handling("BOXBURG", VehicleType.CAR, 5500.0, 23489.6, 3.0, 0.0, 0.0, 0.0, 80, 0.82, 0.70, 0.46, 5, 140.0, 14.0, 25.0, DriveType.R, EngineType.D, 4.5, 0.60, 0, 30.0, 0.9, 0.08, 0.0, 0.25, -0.25, 0.35, 0.6, 0.36, 0.40, 22000, "4009", "201", 0, 3, 13));
    	handlings.add(new Handling("FARM_TR1", VehicleType.CAR, 400.0, 400.0, 5.0, 0.0, -0.4, 0.0, 70, 0.60, 0.85, 0.5, 3, 160.0, 20.0, 30.0, DriveType.R, EngineType.E, 5.0, 0.50, 0, 30.0, 1.0, 0.10, 0.0, 0.25, -0.10, 0.5, 0.0, 0.26, 0.50, 9000, "3100", "4", 1, 1, 0));
    	handlings.add(new Handling("UTIL_TR1", VehicleType.CAR, 1000.0, 1354.2, 5.0, 0.0, 0.0, 0.0, 70, 1.00, 0.85, 0.5, 3, 160.0, 20.0, 30.0, DriveType.R, EngineType.E, 5.0, 0.50, 0, 30.0, 2.0, 0.09, 0.0, 0.25, -0.10, 0.5, 0.0, 0.26, 0.50, 9000, "3100", "4", 1, 1, 0));
    	//handlings.add(new Handling("ROLLER", VehicleType.CAR, 1000.0, 1354.2, 4.0, 0.0, 0.0, -0.1, 70, 0.55, 0.85, 0.5, 3, 160.0, 15.0, 30.0, DriveType.FOUR, EngineType.E, 13.0, 0.50, 0, 30.0, 1.0, 0.09, 0.0, 0.28, -0.13, 0.5, 0.0, 0.26, 0.50, 9000, "1100", "8804", 1, 1, 0)); //TODO car?
    	handlings.add(new Handling("FAGGIO", VehicleType.BIKE, 1000.0, 1354.2, 4.0, 0.0, 0.0, -0.1, 70, 0.55, 0.85, 0.5, 3, 160.0, 15.0, 30.0, DriveType.FOUR, EngineType.E, 13.0, 0.50, 0, 30.0, 1.0, 0.09, 0.0, 0.28, -0.13, 0.5, 0.0, 0.26, 0.50, 9000, "1100", "8804", 1, 1, 0)); //TODO car?
    	handlings.add(new Handling("BIKE", VehicleType.BIKE, 500.0, 161.7, 4.0, 0.0, 0.05, -0.09, 103, 1.6, 0.9, 0.48, 5, 190.0, 50.0, 5.0, DriveType.R, EngineType.P, 15.0, 0.50, 0, 35.0, 0.85, 0.15, 0.0, 0.15, -0.16, 0.5, 0.0, 0.0, 0.15, 10000, "1002000", "0", 1, 1, 4));
    	handlings.add(new Handling("MOPED", VehicleType.BIKE, 350.0, 119.6, 5.0, 0.0, 0.05, -0.1, 103, 1.8, 0.9, 0.48, 3, 190.0, 30.0, 5.0, DriveType.R, EngineType.P, 14.0, 0.50, 0, 35.0, 1.0, 0.15, 0.0, 0.12, -0.17, 0.5, 0.0, 0.0, 0.11, 10000, "1000000", "0", 1, 1, 5));
    	handlings.add(new Handling("DIRTBIKE", VehicleType.BIKE, 500.0, 195.0, 5.0, 0.0, 0.05, -0.09, 103, 1.6, 0.9, 0.48, 5, 190.0, 50.0, 5.0, DriveType.R, EngineType.P, 14.0, 0.50, 0, 35.0, 0.85, 0.15, 0.0, 0.15, -0.16, 0.5, 0.0, 0.0, 0.15, 10000, "1000000", "0", 1, 1, 7));
    	handlings.add(new Handling("FCR900", VehicleType.BIKE, 500.0, 200.0, 4.0, 0.0, 0.05, -0.09, 103, 1.5, 0.9, 0.48, 5, 190.0, 50.0, 5.0, DriveType.R, EngineType.P, 15.0, 0.50, 0, 35.0, 0.85, 0.15, 0.0, 0.15, -0.16, 0.5, 0.0, 0.0, 0.11, 10000, "1000000", "0", 1, 1, 4));
    	handlings.add(new Handling("NRG500", VehicleType.BIKE, 400.0, 200.0, 4.0, 0.0, 0.08, -0.09, 103, 1.8, 0.9, 0.48, 5, 190.0, 60.0, 5.0, DriveType.R, EngineType.P, 15.0, 0.50, 0, 35.0, 0.85, 0.15, 0.0, 0.15, -0.16, 0.5, 0.0, 0.0, 0.15, 10000, "1002000", "2", 1, 1, 4));
    	handlings.add(new Handling("HPV1000", VehicleType.BIKE, 500.0, 240.0, 4.5, 0.0, 0.05, -0.09, 103, 1.5, 0.9, 0.46, 5, 190.0, 50.0, 5.0, DriveType.R, EngineType.P, 15.0, 0.50, 0, 35.0, 0.85, 0.15, 0.0, 0.15, -0.16, 0.5, 0.0, 0.0, 0.15, 10000, "1002000", "0", 1, 1, 4));
    	handlings.add(new Handling("BF400", VehicleType.BIKE, 500.0, 200.0, 4.5, 0.0, 0.05, -0.09, 103, 1.4, 0.9, 0.48, 5, 190.0, 50.0, 5.0, DriveType.R, EngineType.P, 15.0, 0.50, 0, 35.0, 0.85, 0.15, 0.0, 0.15, -0.20, 0.5, 0.0, 0.0, 0.15, 10000, "1000000", "0", 1, 1, 4));
    	handlings.add(new Handling("WAYFARER", VehicleType.BIKE, 800.0, 600.0, 4.0, 0.0, 0.1, 0.0, 103, 1.4, 0.85, 0.48, 4, 190.0, 40.0, 5.0, DriveType.R, EngineType.P, 10.0, 0.55, 0, 35.0, 0.65, 0.20, 0.0, 0.09, -0.11, 0.55, 0.0, 0.0, 0.24, 10000, "41002000", "0", 1, 1, 8));
    	handlings.add(new Handling("QUADBIKE", VehicleType.BIKE, 400.0, 300.0, 5.0, 0.0, 0.05, -0.20, 70, 0.7, 0.9, 0.49, 4, 160.0, 25.0, 5.0, DriveType.FOUR, EngineType.P, 8.0, 0.50, 0, 35.0, 0.8, 0.10, 0.0, 0.15, -0.15, 0.5, 0.0, 0.2, 0.50, 9000, "281300", "205", 1, 1, 12));
    	handlings.add(new Handling("BMX", VehicleType.BIKE, 100.0, 39.0, 7.0, 0.0, 0.05, -0.09, 103, 1.6, 0.9, 0.48, 5, 120.0, 18.0, 5.0, DriveType.R, EngineType.P, 19.0, 0.50, 0, 35.0, 0.80, 0.15, 0.0, 0.20, -0.10, 0.5, 0.0, 0.0, 0.15, 10000, "41000000", "0", 1, 1, 9));
    	handlings.add(new Handling("CHOPPERB", VehicleType.BIKE, 100.0, 39.0, 6.0, 0.0, 0.05, -0.09, 103, 1.6, 0.9, 0.48, 5, 120.0, 18.0, 5.0, DriveType.R, EngineType.P, 19.0, 0.50, 0, 35.0, 0.85, 0.15, 0.0, 0.20, -0.10, 0.5, 0.0, 0.0, 0.15, 10000, "41000000", "0", 1, 1, 11));
    	handlings.add(new Handling("MTB", VehicleType.BIKE, 100.0, 60.0, 5.0, 0.0, 0.05, -0.09, 103, 1.6, 0.9, 0.48, 4, 140.0, 25.0, 15.0, DriveType.R, EngineType.P, 19.0, 0.50, 0, 35.0, 0.85, 0.15, 0.0, 0.20, -0.10, 0.5, 0.0, 0.0, 0.15, 10000, "41000000", "2", 1, 1, 10));
    	handlings.add(new Handling("FREEWAY", VehicleType.BIKE, 800.0, 403.3, 4.0, 0.0, 0.1, 0.0, 103, 1.2, 0.82, 0.51, 4, 190.0, 40.0, 5.0, DriveType.R, EngineType.P, 10.0, 0.55, 0, 35.0, 0.65, 0.20, 0.0, 0.09, -0.11, 0.55, 0.0, 0.0, 0.24, 10000, "1002000", "0", 1, 1, 6));
    	handlings.add(new Handling("PREDATOR", VehicleType.BOAT, 2200.0, 29333.3, 1.0, 0.0, 0.0, 0.0, 14, 2.30, 15.0, 0.58, 5, 190.0, 1.7, 5.0, DriveType.R, EngineType.P, 0.05, 0.01, 0, 24.0, 1.0, 3.0, 0.0, 0.10, 0.1, 0.0, 0.0, 0.2, 0.33, 40000, "8000000", "0", 0, 1, 0));
    	handlings.add(new Handling("SPEEDER", VehicleType.BOAT, 2200.0, 20210.7, 1.0, 0.0, 0.0, 0.0, 22, 2.5, 15.0, 0.65, 5, 190.0, 2.5, 5.0, DriveType.R, EngineType.P, 0.04, 0.01, 0, 20.0, 1.3, 3.0, 0.0, 0.10, 0.5, 2.0, 0.0, 0.7, 0.39, 30000, "8000400", "0", 0, 1, 0));
    	handlings.add(new Handling("REEFER", VehicleType.BOAT, 5000.0, 25520.8, 1.0, 0.0, 0.0, 0.0, 15, -1.50, 15.0, 0.45, 5, 190.0, 0.7, 5.0, DriveType.R, EngineType.P, 0.02, 0.02, 0, 25.0, 1.0, 3.0, 0.0, 0.10, 0.1, 0.0, 0.0, 0.2, 0.38, 25000, "8000000", "0", 0, 1, 0));
    	handlings.add(new Handling("RIO", VehicleType.BOAT, 3000.0, 17312.5, 1.0, 0.0, 0.0, 0.0, 15, -4.00, 25.0, 0.50, 5, 190.0, 0.5, 5.0, DriveType.R, EngineType.P, 0.02, 0.00, 0, 20.0, 1.0, 3.0, 0.0, 15.00, 0.1, 0.0, 0.0, 0.2, 0.23, 70000, "8000000", "0", 0, 1, 0));
    	handlings.add(new Handling("SQUALO", VehicleType.BOAT, 2200.0, 29333.3, 1.0, 0.0, 0.0, 0.0, 42, 3.00, 15.0, 0.65, 5, 190.0, 3.0, 5.0, DriveType.R, EngineType.P, 0.02, 0.00, 0, 24.0, 0.45, 5.0, 0.0, 0.10, 0.05, 0.0, 0.0, 0.2, 0.33, 60000, "8000400", "0", 0, 1, 0));
    	handlings.add(new Handling("TROPIC", VehicleType.BOAT, 2200.0, 29333.3, 1.0, 0.0, 0.0, 0.0, 10, 2.20, 12.0, 0.45, 5, 190.0, 1.4, 5.0, DriveType.R, EngineType.P, 0.05, 0.01, 0, 24.0, 1.8, 3.0, 0.0, 0.10, 0.1, 0.0, 0.0, 0.2, 0.33, 73000, "8000400", "0", 0, 1, 0));
    	handlings.add(new Handling("COASTGRD", VehicleType.BOAT, 1200.0, 6525.0, 1.0, 0.0, -0.3, 0.0, 14, 2.00, 4.2, 0.70, 5, 190.0, 1.6, 5.0, DriveType.R, EngineType.P, 0.05, 0.01, 0, 24.0, 1.0, 3.0, 0.0, 3.20, 0.1, 2.5, 0.0, 0.2, 0.18, 10000, "8000000", "0", 0, 1, 0));
    	handlings.add(new Handling("DINGHY", VehicleType.BOAT, 800.0, 1483.3, 1.0, 0.0, 0.0, 0.0, 16, 3.50, 3.5, 1.00, 5, 190.0, 1.2, 5.0, DriveType.R, EngineType.P, 0.07, 0.01, 0, 30.0, 1.0, 4.5, 0.0, 3.50, 0.1, 0.7, 0.0, 0.2, 0.12, 5000, "8000000", "0", 0, 1, 0));
    	handlings.add(new Handling("MARQUIS", VehicleType.BOAT, 5000.0, 155520.8, 1.0, 0.0, 0.0, 0.0, 10, -3.50, 25.0, 0.40, 5, 190.0, 0.5, 5.0, DriveType.R, EngineType.P, 0.04, 0.03, 0, 38.0, 1.0, 3.0, 0.0, 0.10, 0.0, 1.0, 0.0, 0.2, 0.38, 99000, "8000000", "0", 0, 1, 0));
    	handlings.add(new Handling("CUPBOAT", VehicleType.BOAT, 3000.0, 40000.0, 1.0, 0.0, 0.0, 0.0, 35, 2.00, 15.0, 0.50, 5, 190.0, 3.0, 5.0, DriveType.R, EngineType.P, 0.02, 0.00, 0, 24.0, 0.75, 4.0, 0.0, 0.10, 0.3, 1.5, 0.0, 0.2, 0.45, 48000, "8000400", "0", 0, 1, 0));
    	handlings.add(new Handling("LAUNCH", VehicleType.BOAT, 2200.0, 20210.7, 1.0, 0.0, -1.0, 0.0, 22, 1.5, 15.0, 0.65, 5, 190.0, 1.5, 5.0, DriveType.R, EngineType.P, 0.03, 0.01, 0, 24.0, 1.0, 3.0, 0.0, 0.10, 0.5, 2.0, 0.0, 0.7, 0.39, 30000, "8000400", "0", 0, 1, 0));
    	handlings.add(new Handling("SEAPLANE", VehicleType.PLANE, 5000.0, 27083.3, 12.0, 0.0, 0.0, 0.0, 9, 0.83, 45.0, 0.5, 1, 200.0, 1.7, 5.0, DriveType.FOUR, EngineType.P, 0.01, 0.05, 0, 24.0, 1.5, 0.75, 0.0, 0.10, 0.0, 2.0, 0.0, 1.0, 0.05, 10000, "4000400", "0", 0, 1, 0));
    	handlings.add(new Handling("VORTEX", VehicleType.PLANE, 1900.0, 4795.9, 20.0, 0.0, 0.0, 0.2, 85, 0.05, 1.0, 0.5, 5, 150.0, 2.0, 5.0, DriveType.R, EngineType.P, 1.0, 0.50, 0, 30.0, 0.5, 0.05, 0.0, 0.33, -0.25, 0.5, 0.02, 0.65, 0.50, 26000, "301", "400040", 0, 1, 23));
    	handlings.add(new Handling("RUSTLER", VehicleType.PLANE, 5000.0, 27083.3, 10.0, 0.0, 0.0, 0.0, 75, 0.65, 0.9, 0.5, 1, 200.0, 16.0, 5.0, DriveType.FOUR, EngineType.P, 1.5, 0.45, 0, 45.0, 2.0, 0.15, 0.0, 0.50, -0.20, 0.5, 0.0, 0.30, 0.75, 45000, "4008108", "400020", 0, 1, 14));
    	handlings.add(new Handling("BEAGLE", VehicleType.PLANE, 10000.0, 80000.0, 14.0, 0.0, 0.0, 0.0, 75, 0.65, 0.9, 0.5, 1, 200.0, 16.0, 5.0, DriveType.FOUR, EngineType.P, 1.5, 0.45, 0, 45.0, 2.0, 0.15, 0.0, 1.00, -0.10, 0.35, 0.0, 0.30, 0.75, 45000, "24000008", "400000", 0, 1, 13));
    	handlings.add(new Handling("CROPDUST", VehicleType.PLANE, 5000.0, 27083.3, 15.0, 0.0, 0.0, 0.0, 75, 0.65, 0.9, 0.5, 1, 200.0, 16.0, 5.0, DriveType.FOUR, EngineType.P, 1.5, 0.45, 0, 45.0, 2.0, 0.15, 0.0, 0.55, -0.05, 0.5, 0.0, 0.30, 0.75, 45000, "4008000", "400020", 0, 1, 14));
    	handlings.add(new Handling("STUNT", VehicleType.PLANE, 5000.0, 20000.0, 14.0, 0.0, 0.0, 0.0, 75, 0.65, 0.9, 0.5, 1, 200.0, 16.0, 5.0, DriveType.FOUR, EngineType.P, 1.5, 0.45, 0, 45.0, 2.0, 0.15, 0.0, 0.50, -0.10, 0.9, 0.0, 0.30, 0.75, 45000, "4008100", "400020", 0, 1, 14));
    	handlings.add(new Handling("SHAMAL", VehicleType.PLANE, 15000.0, 81250.0, 8.0, 0.0, 0.0, 0.0, 75, 0.55, 0.8, 0.7, 1, 200.0, 16.0, 5.0, DriveType.FOUR, EngineType.P, 1.5, 0.15, 0, 45.0, 4.0, 0.15, 0.0, 1.00, -0.00, 0.3, 0.0, 0.30, 0.75, 45000, "400c108", "400000", 0, 1, 26));
    	handlings.add(new Handling("HYDRA", VehicleType.PLANE, 9000.0, 48750.0, 20.0, 0.0, 0.0, 0.0, 75, 0.65, 0.9, 0.5, 1, 200.0, 16.0, 5.0, DriveType.FOUR, EngineType.P, 1.5, 0.45, 0, 45.0, 1.0, 0.15, 0.0, 0.50, -0.20, 0.8, 0.0, 0.60, 0.75, 45000, "4008100", "400000", 0, 1, 27));
    	handlings.add(new Handling("NEVADA", VehicleType.PLANE, 25000.0, 438750.0, 10.0, 0.0, 0.0, 0.0, 75, 0.65, 0.9, 0.5, 1, 200.0, 16.0, 5.0, DriveType.FOUR, EngineType.P, 1.0, 0.45, 0, 45.0, 1.0, 0.10, 0.0, 0.40, -0.30, 0.5, 0.0, 0.30, 0.75, 45000, "400c108", "400020", 0, 1, 29));
    	handlings.add(new Handling("AT400", VehicleType.PLANE, 60000.0, 9000000.0, 4.0, 0.0, 0.0, 0.0, 75, 1.5, 0.9, 0.85, 1, 200.0, 16.0, 5.0, DriveType.FOUR, EngineType.P, 1.0, 0.45, 0, 45.0, 1.5, 0.15, 0.0, 0.50, -0.20, 0.3, 0.0, 0.70, 0.75, 45000, "2400c008", "400000", 0, 1, 15));
    	handlings.add(new Handling("ANDROM", VehicleType.PLANE, 40000.0, 3000000.0, 4.0, 0.0, 0.0, 0.0, 75, 0.65, 0.9, 0.5, 1, 200.0, 16.0, 5.0, DriveType.FOUR, EngineType.P, 1.0, 0.45, 0, 45.0, 2.0, 0.15, 0.0, 0.50, -0.20, 0.5, 0.0, 0.30, 0.75, 45000, "24114108", "440000", 0, 1, 0));
    	handlings.add(new Handling("DODO", VehicleType.PLANE, 5000.0, 27083.3, 12.0, 0.0, 0.3, 0.0, 75, 0.65, 0.9, 0.5, 1, 200.0, 16.0, 5.0, DriveType.FOUR, EngineType.P, 1.5, 0.45, 0, 45.0, 1.5, 0.15, 0.0, 0.50, -0.05, 0.2, 0.0, 0.50, 0.75, 45000, "4000000", "400000", 0, 1, 13));
    	handlings.add(new Handling("SPARROW", VehicleType.PLANE, 2500.0, 6041.7, 0.2, 0.0, 0.0, -0.1, 75, 0.65, 0.9, 0.5, 1, 200.0, 16.0, 5.0, DriveType.FOUR, EngineType.P, 5.0, 0.45, 0, 30.0, 2.0, 0.1, 0.0, 0.50, -0.20, 0.5, 0.0, 0.30, 0.50, 25000, "2004000", "400000", 0, 1, 0));
    	handlings.add(new Handling("SEASPAR", VehicleType.PLANE, 3000.0, 7250.0, 0.1, 0.0, 0.0, -0.1, 5, 0.65, 0.9, 0.5, 1, 200.0, 16.0, 5.0, DriveType.FOUR, EngineType.P, 5.0, 0.45, 0, 30.0, 2.0, 0.1, 0.0, 0.50, -0.20, 0.5, 0.0, 0.30, 0.60, 28000, "2004000", "400000", 0, 1, 0));
    	handlings.add(new Handling("MAVERICK", VehicleType.PLANE, 5000.0, 29270.8, 0.2, 0.0, 0.0, -0.1, 75, 0.65, 0.9, 0.5, 1, 200.0, 16.0, 5.0, DriveType.FOUR, EngineType.P, 5.0, 0.45, 0, 30.0, 2.0, 0.15, 0.0, 0.50, -0.20, 0.5, 0.0, 0.30, 0.75, 45000, "2004000", "400000", 0, 1, 0));
    	handlings.add(new Handling("COASTMAV", VehicleType.PLANE, 3500.0, 8458.3, 0.2, 0.0, 0.0, -0.1, 75, 0.65, 0.9, 0.5, 1, 200.0, 16.0, 5.0, DriveType.FOUR, EngineType.P, 5.0, 0.45, 0, 30.0, 2.0, 0.1, 0.0, 0.50, -0.20, 0.5, 0.0, 0.30, 0.65, 50000, "2004000", "400000", 0, 1, 0));
    	handlings.add(new Handling("POLMAV", VehicleType.PLANE, 4500.0, 26343.7, 0.2, 0.0, 0.0, -0.1, 75, 0.65, 0.9, 0.5, 1, 200.0, 16.0, 5.0, DriveType.FOUR, EngineType.P, 5.0, 0.45, 0, 30.0, 2.0, 0.1, 0.0, 0.50, -0.20, 0.5, 0.0, 0.30, 0.65, 52000, "2004000", "400000", 0, 1, 0));
    	handlings.add(new Handling("HUNTER", VehicleType.PLANE, 10000.0, 150000.0, 0.2, 0.0, 0.0, 0.0, 75, 0.65, 0.9, 0.5, 1, 200.0, 16.0, 5.0, DriveType.FOUR, EngineType.P, 5.0, 0.45, 0, 30.0, 1.0, 0.05, 0.0, 0.20, -0.15, 0.85, 0.0, 0.40, 0.50, 99000, "200c000", "400000", 0, 1, 14));
    	handlings.add(new Handling("LEVIATHN", VehicleType.PLANE, 15000.0, 200000.0, 0.1, 0.0, 0.0, 0.0, 30, 0.65, 0.9, 0.5, 1, 200.0, 16.0, 5.0, DriveType.FOUR, EngineType.P, 5.0, 0.45, 0, 30.0, 1.0, 0.05, 0.0, 0.50, -0.20, 0.9, 0.0, 0.30, 0.50, 25000, "2004000", "400000", 0, 1, 0));
    	handlings.add(new Handling("CARGOBOB", VehicleType.PLANE, 20000.0, 48333.3, 0.2, 0.0, 0.0, -0.1, 75, 0.65, 0.9, 0.5, 1, 200.0, 16.0, 5.0, DriveType.FOUR, EngineType.P, 5.0, 0.45, 0, 30.0, 0.6, 0.05, 0.0, 0.50, -0.10, 0.3, 0.0, 0.30, 0.50, 25000, "22004000", "400000", 0, 1, 0));
    	handlings.add(new Handling("RAINDANC", VehicleType.PLANE, 10000.0, 96666.7, 0.05, 0.0, 0.0, -1.0, 75, 0.65, 0.9, 0.5, 1, 200.0, 16.0, 5.0, DriveType.FOUR, EngineType.P, 5.0, 0.45, 0, 30.0, 1.5, 0.1, 0.0, 0.20, -0.15, 0.5, 0.0, 0.70, 0.50, 99000, "2004000", "400000", 0, 1, 0));
    	handlings.add(new Handling("RCBARON", VehicleType.PLANE, 100.0, 50.0, 120.0, 0.0, 0.0, 0.0, 99, 0.20, 0.9, 0.5, 1, 75.0, 1.0, 5.0, DriveType.F, EngineType.P, 0.5, 0.50, 0, 45.0, 0.6, 0.1, 0.0, 0.25, -0.00, 0.8, 0.0, 0.2, 0.08, 300, "4000000", "20", 0, 1, 0));
    	handlings.add(new Handling("RCGOBLIN", VehicleType.PLANE, 100.0, 24.1, 0.2, 0.0, 0.0, -0.1, 70, 1.10, 0.75, 0.50, 1, 75.0, 35.0, 5.0, DriveType.FOUR, EngineType.P, 5.5, 0.50, 0, 25.0, 1.6, 0.1, 0.0, 0.28, -0.08, 0.5, 0.0, 0.2, 0.05, 800, "2000000", "0", 0, 1, 0));
    	handlings.add(new Handling("RCRAIDER", VehicleType.PLANE, 100.0, 24.1, 0.2, 0.0, 0.0, -0.1, 70, 1.10, 0.75, 0.50, 1, 75.0, 35.0, 5.0, DriveType.FOUR, EngineType.P, 5.5, 0.50, 0, 25.0, 1.6, 0.1, 0.0, 0.28, -0.08, 0.5, 0.0, 0.2, 0.10, 500, "2000000", "0", 0, 1, 0));
    
		getHandling("PREDATOR").setBoatData(new BoatData(0.79, 0.5, 0.6, 7.0, 0.60, -1.9, 4.0, 0.8, 0.998, 0.998, 0.85, 0.98, 0.97, 4.0));
		getHandling("SPEEDER").setBoatData(new BoatData(0.65, 0.5, 0.5, 8.0, 0.70, -0.5, 3.0, 0.7, 0.998, 0.999, 0.85, 0.98, 0.96, 4.0));
		getHandling("REEFER").setBoatData(new BoatData(0.35, 0.6, 0.0, 3.0, 0.20, -0.9, 3.5, 0.8, 0.998, 0.995, 0.85, 0.98, 0.96, 4.0));
		getHandling("RIO").setBoatData(new BoatData(0.75, 0.7, 0.0, 3.0, 0.20, 0.0, 10.0, 0.7, 0.998, 0.999, 0.85, 0.96, 0.96, 4.0));
		getHandling("SQUALO").setBoatData(new BoatData(0.50, 1.1, 0.2, 9.0, 0.80, 1.2, 4.0, 0.90, 0.998, 0.999, 0.89, 0.975, 0.97, 4.0));
		getHandling("TROPIC").setBoatData(new BoatData(0.35, 0.6, 0.5, 4.0, 0.35, -1.0, 6.0, 0.7, 0.998, 0.995, 0.78, 0.985, 0.96, 5.0));
		getHandling("COASTGRD").setBoatData(new BoatData(0.50, 0.65, 0.5, 8.0, 0.60, -0.3, 3.0, 0.7, 0.996, 0.998, 0.85, 0.96, 0.96, 3.0));
		getHandling("DINGHY").setBoatData(new BoatData(0.70, 0.6, 0.4, 5.0, 0.30, -0.8, 3.0, 0.94, 0.993, 0.997, 0.85, 0.94, 0.94, 3.0));
		getHandling("MARQUIS").setBoatData(new BoatData(0.95, 0.7, 0.0, 1.5, 0.10, 0.0, 3.5, 0.8, 0.998, 0.970, 0.84, 0.99, 0.94, 5.0));
		getHandling("CUPBOAT").setBoatData(new BoatData(0.50, 0.6, 0.9, 9.0, 0.80, -1.0, 4.0, 0.92, 0.998, 0.998, 0.87, 0.96, 0.97, 4.0));
		getHandling("LAUNCH").setBoatData(new BoatData(0.50, 0.9, 0.5, 6.0, 0.50, -0.5, 3.0, 0.7, 0.998, 0.999, 0.85, 0.98, 0.96, 4.0));
		getHandling("SEAPLANE").setBoatData(new BoatData(0.50, 1.2, 1.2, 50.0, 0.85, -0.4, 20.0, 0.93, 0.998, 0.999, 0.95, 0.96, 0.96, 4.5));
		
		getHandling("BIKE").setBikeData(new BikeData(0.35, 0.15, 0.34, 0.10, 45.0, 38.0, 0.93, 0.70, 0.5, 0.1, 35.0, -40.0, -0.009, 0.7, 0.6));
		getHandling("MOPED").setBikeData(new BikeData(0.35, 0.25, 0.25, 0.04, 35.0, 25.0, 0.90, 0.75, 0.8, 0.1, 35.0, -45.0, -0.010, 0.7, 0.5));
		getHandling("DIRTBIKE").setBikeData(new BikeData(0.40, 0.40, 0.30, 0.08, 48.0, 43.0, 0.92, 0.60, 1.0, 0.1, 45.0, -35.0, -0.010, 0.5, 0.5));
		getHandling("FCR900").setBikeData(new BikeData(0.33, 0.15, 0.28, 0.15, 45.0, 38.0, 0.93, 0.70, 0.5, 0.1, 35.0, -40.0, -0.009, 0.7, 0.6));
		getHandling("NRG500").setBikeData(new BikeData(0.25, 0.10, 0.30, 0.10, 55.0, 38.0, 0.95, 0.60, 0.5, 0.1, 30.0, -35.0, -0.012, 0.7, 0.6));
		getHandling("HPV1000").setBikeData(new BikeData(0.25, 0.08, 0.25, 0.08, 40.0, 30.0, 0.93, 0.65, 0.5, 0.1, 35.0, -40.0, -0.009, 0.5, 0.5));
		getHandling("BF400").setBikeData(new BikeData(0.35, 0.10, 0.35, 0.10, 40.0, 30.0, 0.92, 0.65, 0.6, 0.1, 40.0, -40.0, -0.013, 0.6, 0.5));
		getHandling("WAYFARER").setBikeData(new BikeData(0.10, 0.05, 0.20, 0.05, 30.0, 25.0, 0.945, 1.20, 0.7, 0.1, 33.0, -55.0, -0.006, 0.4, 0.3));
		getHandling("QUADBIKE").setBikeData(new BikeData(0.14, 0.04, 0.14, 0.08, 48.0, 43.0, 0.88, 0.60, 1.0, 0.1, 45.0, -35.0, -0.007, 1.0, 0.5));
		getHandling("BMX").setBikeData(new BikeData(0.35, 0.50, 0.30, 0.09, 48.0, 43.0, 0.92, 0.60, 1.0, 0.1, 45.0, -35.0, -0.007, 1.0, 0.5));
		getHandling("CHOPPERB").setBikeData(new BikeData(0.35, 0.50, 0.30, 0.09, 40.0, 30.0, 0.92, 0.60, 1.0, 0.1, 45.0, -35.0, -0.007, 0.6, 0.5));
		getHandling("MTB").setBikeData(new BikeData(0.35, 0.30, 0.25, 0.08, 48.0, 43.0, 0.92, 0.60, 1.0, 0.1, 35.0, -35.0, -0.015, 0.6, 0.5));
		getHandling("FREEWAY").setBikeData(new BikeData(0.10, 0.05, 0.30, 0.062, 30.0, 25.0, 0.945, 1.20, 0.7, 0.1, 33.0, -55.0, -0.006, 0.5, 0.3));
		
		getHandling("SEAPLANE").setPlaneData(new PlaneData(0.5, 0.40, -0.00006, 0.002, 0.10, 0.002, -0.002, 0.0002, 0.0020, 0.020, 0.15, 1.0, 1.0, 0.2, 1.0, 0.998, 0.998, 0.995, 20.0, 50.0, 20.0));
		getHandling("VORTEX").setPlaneData(new PlaneData(0.4, 0.30, -0.0025, 0.02, 0.10, 0.001, -0.002, 0.0003, 0.0020, 0.000, 0.10, 1.0, 1.0, 0.2, 0.995, 1.000, 1.000, 0.998, 0.0, 0.0, 10.0));
		getHandling("RUSTLER").setPlaneData(new PlaneData(0.5, 0.30, -0.00010, 0.004, 0.10, 0.002, -0.002, 0.0002, 0.0020, 0.008, 0.10, 0.2, 1.2, 0.2, 1.0, 0.998, 0.998, 0.990, 10.0, 20.0, 0.0));
		getHandling("BEAGLE").setPlaneData(new PlaneData(0.65, 0.10, -0.00010, 0.002, 0.10, 0.002, -0.002, 0.0002, 0.0020, 0.018, 0.15, 1.0, 1.0, 0.2, 1.0, 0.998, 0.998, 0.990, 20.0, 50.0, 20.0));
		getHandling("CROPDUST").setPlaneData(new PlaneData(0.6, 0.10, -0.00015, 0.002, 0.10, 0.002, -0.002, 0.0003, 0.0020, 0.030, 0.20, 1.0, 1.0, 0.2, 1.0, 0.9985, 0.997, 0.995, 20.0, 30.0, 20.0));
		getHandling("STUNT").setPlaneData(new PlaneData(0.8, 0.30, -0.00020, 0.002, 0.010, 0.004, -0.001, 0.0005, 0.0015, 0.014, 0.14, 1.0, 1.0, 0.2, 1.0, 0.997, 0.997, 0.997, 10.0, 5.0, 5.0));
		getHandling("SHAMAL").setPlaneData(new PlaneData(0.5, 1.00, -0.00003, 0.001, 0.10, 0.002, -0.002, 0.00006, 0.0015, 0.006, 0.10, 0.3, 1.7, 0.2, 1.0, 0.998, 0.996, 0.995, 20.0, 50.0, 20.0));
		getHandling("HYDRA").setPlaneData(new PlaneData(1.1, 1.00, -0.00010, 0.004, 0.10, 0.002, -0.002, 0.0001, 0.0020, 0.006, 0.10, 0.1, 1.3, 0.2, 1.0, 0.998, 0.997, 0.995, 10.0, 10.0, 40.0));
		getHandling("NEVADA").setPlaneData(new PlaneData(0.4, 0.30, -0.00001, 0.005, 0.10, 0.001, -0.003, 0.00003, 0.0030, 0.012, 0.20, 0.3, 1.3, 0.1, 1.0, 0.998, 0.997, 0.995, 20.0, 100.0, 20.0));
		getHandling("AT400").setPlaneData(new PlaneData(0.35, 1.00, -0.000005, 0.0002, 0.10, 0.0003, -0.0001, 0.00001, 0.00005, 0.008, 0.10, 0.5, 1.5, 0.1, 1.0, 0.998, 0.997, 0.995, 20.0, 100.0, 20.0));
		getHandling("ANDROM").setPlaneData(new PlaneData(0.35, 1.00, -0.000005, 0.0002, 0.10, 0.0003, -0.0001, 0.00001, 0.00005, 0.008, 0.10, 0.5, 1.5, 0.1, 1.0, 0.998, 0.997, 0.995, 20.0, 100.0, 20.0));
		getHandling("DODO").setPlaneData(new PlaneData(0.5, 0.40, -0.00015, 0.002, 0.10, 0.003, -0.002, 0.0003, 0.0020, 0.020, 0.15, 1.0, 1.0, 0.2, 1.0, 0.998, 0.998, 0.995, 20.0, 50.0, 20.0));
		getHandling("SPARROW").setPlaneData(new PlaneData(0.40, 0.95, -0.001, 0.01, 0.05, 0.0020, 2.0, 0.0020, 2.0, 0.7, 0.004, 0.2, 1.0, 0.2, 0.998, 0.980, 0.980, 0.990, 1.0, 1.0, 10.0));
		getHandling("SEASPAR").setPlaneData(new PlaneData(0.50, 0.95, -0.001, 0.01, 0.05, 0.0020, 2.0, 0.0020, 2.0, 0.7, 0.003, 0.2, 1.0, 0.2, 0.998, 0.980, 0.980, 0.990, 1.0, 1.0, 10.0));
		getHandling("MAVERICK").setPlaneData(new PlaneData(0.60, 0.85, -0.0005, 0.005, 0.03, 0.0010, 1.5, 0.0010, 1.5, 0.6, 0.002, 0.2, 1.0, 0.2, 0.9985, 0.992, 0.992, 0.996, 0.5, 0.5, 10.0));
		getHandling("COASTMAV").setPlaneData(new PlaneData(0.60, 0.95, -0.0005, 0.005, 0.03, 0.0012, 1.0, 0.0015, 1.0, 0.6, 0.002, 0.2, 1.0, 0.2, 0.9985, 0.992, 0.992, 0.996, 1.5, 1.5, 10.0));
		getHandling("POLMAV").setPlaneData(new PlaneData(0.60, 0.75, -0.0005, 0.005, 0.03, 0.0010, 1.5, 0.0010, 1.5, 0.6, 0.002, 0.2, 1.0, 0.2, 0.9985, 0.992, 0.992, 0.996, 0.5, 0.5, 10.0));
		getHandling("HUNTER").setPlaneData(new PlaneData(0.55, 0.50, -0.0005, 0.005, 0.01, 0.001, 1.0, 0.001, 1.0, 0.5, 0.001, 0.2, 1.0, 0.2, 0.9985, 0.992, 0.992, 0.992, 2.0, 2.0, 7.0));
		getHandling("LEVIATHN").setPlaneData(new PlaneData(0.3, 0.85, -0.0003, 0.005, 0.02, 0.0007, 1.0, 0.0007, 1.0, 0.5, 0.001, 0.2, 1.0, 0.1, 0.9985, 0.992, 0.992, 0.995, 2.0, 2.0, 10.0));
		getHandling("CARGOBOB").setPlaneData(new PlaneData(0.4, 0.85, -0.0003, 0.005, 0.02, 0.0007, 1.0, 0.0007, 1.0, 0.5, 0.001, 0.2, 1.0, 0.1, 0.9985, 0.992, 0.992, 0.995, 2.0, 2.0, 10.0));
		getHandling("RAINDANC").setPlaneData(new PlaneData(0.4, 0.85, -0.0003, 0.005, 0.02, 0.0007, 1.0, 0.0007, 1.0, 0.5, 0.001, 0.2, 1.0, 0.1, 0.9985, 0.992, 0.992, 0.995, 2.0, 2.0, 10.0));
		getHandling("RCBARON").setPlaneData(new PlaneData(0.5, -0.05, -0.006, 0.6, 0.30, 0.015, -0.005, 0.005, 0.2, 0.10, 0.30, 0.2, 1.0, 0.1, 1.0, 0.998, 0.996, 0.990, 10.0, 40.0, 10.0));
		getHandling("RCGOBLIN").setPlaneData(new PlaneData(0.20, 0.75, -0.001, 0.05, 0.10, 0.006, 6.0, 0.006, 6.0, 0.7, 0.015, 0.2, 1.0, 0.1, 0.989, 0.850, 0.860, 0.992, 0.0, 0.0, 7.0));
		getHandling("RCRAIDER").setPlaneData(new PlaneData(0.25, 0.6, -0.002, 0.05, 0.10, 0.009, 5.0, 0.009, 5.0, 0.4, 0.008, 0.2, 1.0, 0.1, 0.989, 0.880, 0.880, 0.998, 0.0, 0.0, 5.0));
    
		handlings.forEach((handling) -> {
			handling.setTankSize(Calculation.getTankSize(handling));
		});
    }
}
