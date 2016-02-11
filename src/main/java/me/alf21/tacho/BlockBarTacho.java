package me.alf21.tacho;

import me.alf21.textdraw.BoxHeight;
import me.alf21.vehiclesystem.Calculation;
import me.alf21.vehiclesystem.Tacho;
import me.alf21.vehiclesystem.VehicleData;
import me.alf21.vehiclesystem.VehicleSystem;
import net.gtaun.shoebill.constant.TextDrawAlign;
import net.gtaun.shoebill.constant.TextDrawFont;
import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.object.PlayerTextdraw;
import net.gtaun.shoebill.object.Timer;
import net.gtaun.shoebill.object.Vehicle;


public class BlockBarTacho extends Tacho {
	private static final Color 	COLOR_GREEN = new Color(0,150,0,255),
								COLOR_RED = new Color(150,0,0,255);
	
	private Player 			player;
	private PlayerTextdraw	box,
							vehicleName,
							spacer1,
							spacer2,
							spacer3,
							spacer4,
							healthBar,
							tankBar,
							healthText,
							tankText,
							speedValue,
							unitText,
							healthValue,
							tankValue;
	private boolean			created;
	private VehicleData 	vehicleData;
	private Timer 			timer;
	
	
	public BlockBarTacho(Player player) {
		super(player);
		this.player = player;
	}
	
	/*
	 * (non-Javadoc)
	 * @see me.alf21.vehiclesystem.Tacho#create()
	 */
	@Override
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
				
			box = PlayerTextdraw.create(player, 560, 337);
			box.setText("_");
			box.setAlignment(TextDrawAlign.CENTER);
			box.setBackgroundColor(Color.BLACK);
			box.setFont(TextDrawFont.get(1));
			box.setLetterSize(0.5f, 10.6f);
			box.setColor(Color.WHITE);
			box.setOutlineSize(0);
			box.setProportional(true);
			box.setShadowSize(1);
			box.setUseBox(true);
			box.setBoxColor(new Color(0,0,0,50));
			box.setTextSize(0, -140);
			box.setSelectable(false);
	
			vehicleName = PlayerTextdraw.create(player, 560, 370);
			vehicleName.setText(vehicleData.getName());
			vehicleName.setAlignment(TextDrawAlign.CENTER);
			vehicleName.setBackgroundColor(Color.BLACK);
			vehicleName.setFont(TextDrawFont.get(1));
			vehicleName.setLetterSize(0.3f, 1.2f);
			vehicleName.setColor(Color.WHITE);
			vehicleName.setOutlineSize(1);
			vehicleName.setProportional(true);
			vehicleName.setShadowSize(1);
			vehicleName.setUseBox(true);
			vehicleName.setBoxColor(new Color(0,0,0,50));
			vehicleName.setTextSize(0, -140);
			vehicleName.setSelectable(false);
	
			spacer1 = PlayerTextdraw.create(player, 560, 385);
			spacer1.setText("_");
			spacer1.setAlignment(TextDrawAlign.CENTER);
			spacer1.setBackgroundColor(Color.BLACK);
			spacer1.setFont(TextDrawFont.get(1));
			spacer1.setLetterSize(0.5f, -0.4f);
			spacer1.setColor(Color.WHITE);
			spacer1.setOutlineSize(0);
			spacer1.setProportional(true);
			spacer1.setShadowSize(1);
			spacer1.setUseBox(true);
			spacer1.setBoxColor(new Color(255,127,0,255));
			spacer1.setTextSize(0, -140);
			spacer1.setSelectable(false);
	
			spacer2 = PlayerTextdraw.create(player, 560, 436);
			spacer2.setText("_");
			spacer2.setAlignment(TextDrawAlign.CENTER);
			spacer2.setBackgroundColor(Color.BLACK);
			spacer2.setFont(TextDrawFont.get(1));
			spacer2.setLetterSize(0.5f, -0.4f);
			spacer2.setColor(Color.WHITE);
			spacer2.setOutlineSize(0);
			spacer2.setProportional(true);
			spacer2.setShadowSize(1);
			spacer2.setUseBox(true);
			spacer2.setBoxColor(new Color(255,127,0,255));
			spacer2.setTextSize(0, -140);
			spacer2.setSelectable(false);
	
			createHealthBar();
	
			createTankBar();
	
			healthText = PlayerTextdraw.create(player, 526, 386);
			healthText.setText("Health");
			healthText.setAlignment(TextDrawAlign.CENTER);
			healthText.setBackgroundColor(Color.BLACK);
			healthText.setFont(TextDrawFont.get(1));
			healthText.setLetterSize(0.5f, 1);
			healthText.setColor(Color.WHITE);
			healthText.setOutlineSize(1);
			healthText.setProportional(true);
			healthText.setShadowSize(1);
			healthText.setUseBox(true);
			healthText.setBoxColor(new Color(0,0,0,100));
			healthText.setTextSize(0, -72);
			healthText.setSelectable(false);
	
			tankText = PlayerTextdraw.create(player, 594, 386);
			tankText.setText("Tank");
			tankText.setAlignment(TextDrawAlign.CENTER);
			tankText.setBackgroundColor(Color.BLACK);
			tankText.setFont(TextDrawFont.get(1));
			tankText.setLetterSize(0.5f, 1);
			tankText.setColor(Color.WHITE);
			tankText.setOutlineSize(1);
			tankText.setProportional(true);
			tankText.setShadowSize(1);
			tankText.setUseBox(true);
			tankText.setBoxColor(new Color(0,0,0,100));
			tankText.setTextSize(0, -72);
			tankText.setSelectable(false);
	
			speedValue = PlayerTextdraw.create(player, 579, 337);
			speedValue.setText(String.valueOf((int) Calculation.getSpeed(vehicleData)));
			speedValue.setAlignment(TextDrawAlign.RIGHT);
			speedValue.setBackgroundColor(new Color(150,0,0,255));
			speedValue.setFont(TextDrawFont.get(2));
			speedValue.setLetterSize(0.8f, 3);
			speedValue.setColor(Color.WHITE);
			speedValue.setOutlineSize(1);
			speedValue.setProportional(true);
			speedValue.setSelectable(false);
	
			spacer3 = PlayerTextdraw.create(player, 560, 337);
			spacer3.setText("_");
			spacer3.setAlignment(TextDrawAlign.CENTER);
			spacer3.setBackgroundColor(Color.BLACK);
			spacer3.setFont(TextDrawFont.get(1));
			spacer3.setLetterSize(0.5f, -0.4f);
			spacer3.setColor(Color.WHITE);
			spacer3.setOutlineSize(0);
			spacer3.setProportional(true);
			spacer3.setShadowSize(1);
			spacer3.setUseBox(true);
			spacer3.setBoxColor(new Color(255,127,0,255));
			spacer3.setTextSize(0, -140);
			spacer3.setSelectable(false);
	
			spacer4 = PlayerTextdraw.create(player, 560, 370);
			spacer4.setText("_");
			spacer4.setAlignment(TextDrawAlign.CENTER);
			spacer4.setBackgroundColor(Color.BLACK);
			spacer4.setFont(TextDrawFont.get(1));
			spacer4.setLetterSize(0.5f, -0.4f);
			spacer4.setColor(Color.WHITE);
			spacer4.setOutlineSize(0);
			spacer4.setProportional(true);
			spacer4.setShadowSize(1);
			spacer4.setUseBox(true);
			spacer4.setBoxColor(new Color(255,127,0,255));
			spacer4.setTextSize(0, -140);
			spacer4.setSelectable(false);
	
			unitText = PlayerTextdraw.create(player, 579, 351);
			unitText.setText("km/h");
			unitText.setBackgroundColor(new Color(150,0,0,255));
			unitText.setFont(TextDrawFont.get(1));
			unitText.setLetterSize(0.3f, 1);
			unitText.setColor(Color.WHITE);
			unitText.setOutlineSize(1);
			unitText.setProportional(true);
			unitText.setSelectable(false);
			
			healthValue = PlayerTextdraw.create(player, 526.5f, 423.5f);
			healthValue.setText(String.valueOf((int) vehicle.getHealth()));
			healthValue.setAlignment(TextDrawAlign.CENTER);
			healthValue.setBackgroundColor(Color.BLACK);
			healthValue.setFont(TextDrawFont.get(1));
			healthValue.setLetterSize(0.5f, 0.9f);
			healthValue.setColor(Color.WHITE);
			healthValue.setOutlineSize(0);
			healthValue.setProportional(true);
			healthValue.setShadowSize(0);
			healthValue.setUseBox(true);
			healthValue.setBoxColor(new Color(0,0,0,100));
			healthValue.setTextSize(0, 60);
			healthValue.setSelectable(false);

			tankValue = PlayerTextdraw.create(player, 593.5f, 423.5f);
			tankValue.setText(String.valueOf((int) vehicleData.getTank()));
			tankValue.setAlignment(TextDrawAlign.CENTER);
			tankValue.setBackgroundColor(Color.BLACK);
			tankValue.setFont(TextDrawFont.get(1));
			tankValue.setLetterSize(0.5f, 0.9f);
			tankValue.setColor(Color.WHITE);
			tankValue.setOutlineSize(0);
			tankValue.setProportional(true);
			tankValue.setShadowSize(0);
			tankValue.setUseBox(true);
			tankValue.setBoxColor(new Color(0,0,0,100));
			tankValue.setTextSize(0, 60);
			tankValue.setSelectable(false);
			
			timer = Timer.create(100, (factualInterval) -> {
				update();
			});
			
			created = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see me.alf21.vehiclesystem.Tacho#destroy()
	 */
	@Override
	public void destroy() {
		hide();
		
		if(timer != null) {
			if(timer.isRunning())
				timer.stop();
			timer.destroy();
			timer = null;
		}
		
		box.destroy();
		vehicleName.destroy();
		spacer1.destroy();
		spacer2.destroy();
		spacer3.destroy();
		spacer4.destroy();
		healthBar.destroy();
		tankBar.destroy();
		healthText.destroy();
		tankText.destroy();
		speedValue.destroy();
		unitText.destroy();
		healthValue.destroy();
		tankValue.destroy();
		
		box = vehicleName = spacer1 = spacer2 = spacer3 = spacer4 = healthBar = tankBar = 
		healthText = tankText = speedValue = unitText = healthValue = tankValue = null;
		
		created = false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see me.alf21.vehiclesystem.Tacho#show()
	 */
	@Override
	public void show() { //TODO sort
		box.show();
		vehicleName.show();
		spacer1.show();
		spacer2.show();
		spacer3.show();
		spacer4.show();
		healthBar.show();
		tankBar.show();
		healthText.show();
		tankText.show();
		speedValue.show();
		unitText.show();
		healthValue.show();
		tankValue.show();
		
		timer.start();
	}
	
	/*
	 * (non-Javadoc)
	 * @see me.alf21.vehiclesystem.Tacho#hide()
	 */
	@Override
	public void hide() {
		timer.stop();
		
		box.hide();
		vehicleName.hide();
		spacer1.hide();
		spacer2.hide();
		spacer3.hide();
		spacer4.hide();
		healthBar.hide();
		tankBar.hide();
		healthText.hide();
		tankText.hide();
		speedValue.hide();
		unitText.hide();
		healthValue.hide();
		tankValue.hide();
	}
	
	/*
	 * (non-Javadoc)
	 * @see me.alf21.vehiclesystem.Tacho#update()
	 */
	@Override
	public void update() {
		Vehicle vehicle = player.getVehicle();
		if(isCreated() && vehicle != null) {
			if(vehicleData.getTank() >= 1) {
				if(vehicle.getState().getEngine() == 0)
					vehicle.getState().setEngine(1);
				vehicleData.setTank(vehicleData.getTank()-0.1f);
			}
			else vehicle.getState().setEngine(0);
			
			healthBar.hide();
			tankBar.hide();
			speedValue.hide();
			
			healthBar.destroy();
			createHealthBar();
			
			tankBar.destroy();
			createTankBar();
			
			healthValue.setText((int) vehicle.getHealth()>=250?String.valueOf((int) vehicle.getHealth()):"FIRE"); //TODO when does it burn
			tankValue.setText((int) vehicleData.getTank()>=1?String.valueOf((int) vehicleData.getTank()):"empty");
			
			speedValue.setText(String.valueOf((int) Calculation.getSpeed(vehicleData)));
	
			healthBar.show();
			tankBar.show();
			speedValue.show();
		}
	}
	
	private void createHealthBar() {
		BoxHeight boxHeight = new BoxHeight(526.5f, 399.85f, 423.85f, 0.5f, 0.85f, 3.5f, vehicleData.getVehicle().getHealth(), 1000);
		
		healthBar = PlayerTextdraw.create(player, boxHeight.getPosition());
		healthBar.setText("_");
		healthBar.setAlignment(TextDrawAlign.CENTER);
		healthBar.setBackgroundColor(Color.BLACK);
		healthBar.setFont(TextDrawFont.get(1));
		healthBar.setLetterSize(boxHeight.getLetterSize());
		healthBar.setColor(Color.WHITE);
		healthBar.setOutlineSize(0);
		healthBar.setProportional(true);
		healthBar.setShadowSize(1);
		healthBar.setUseBox(true);
		healthBar.setBoxColor(Calculation.getBoxColor(COLOR_RED, COLOR_GREEN, vehicleData.getVehicle().getHealth(), 1000));
		healthBar.setTextSize(0, -68);
		healthBar.setSelectable(false);
	}
	

	private void createTankBar() {
		BoxHeight boxHeight = new BoxHeight(593.5f, 399.85f, 423.85f, 0.5f, 0.85f, 3.5f, vehicleData.getTank(), 100);
		
		tankBar = PlayerTextdraw.create(player, boxHeight.getPosition());
		tankBar.setText("_");
		tankBar.setAlignment(TextDrawAlign.CENTER);
		tankBar.setBackgroundColor(Color.BLACK);
		tankBar.setFont(TextDrawFont.get(1));
		tankBar.setLetterSize(boxHeight.getLetterSize());
		tankBar.setColor(Color.WHITE);
		tankBar.setOutlineSize(0);
		tankBar.setProportional(true);
		tankBar.setShadowSize(1);
		tankBar.setUseBox(true);
		tankBar.setBoxColor(Calculation.getBoxColor(COLOR_RED, COLOR_GREEN, vehicleData.getTank(), 100));
		tankBar.setTextSize(0, -68);
		tankBar.setSelectable(false);
	}
	
	/*
	 * (non-Javadoc)
	 * @see me.alf21.vehiclesystem.Tacho#getVehicleData()
	 */
	@Override
	public VehicleData getVehicleData() {
		return vehicleData;
	}
	
	/*
	 * (non-Javadoc)
	 * @see me.alf21.vehiclesystem.Tacho#isCreated()
	 */
	@Override
	public boolean isCreated() {
		return created;
	}

	/*
	 * (non-Javadoc)
	 * @see me.alf21.vehiclesystem.Tacho#isDestroyed()
	 */
	@Override
	public boolean isDestroyed() {
		if(box != null 
		|| vehicleName != null
		|| spacer1 != null
		|| spacer2 != null
		|| spacer3 != null
		|| spacer4 != null
		|| healthBar != null
		|| tankBar != null
		|| healthText != null
		|| tankText != null
		|| speedValue != null
		|| unitText != null
		|| healthValue != null
		|| tankValue != null
		|| timer != null)
			return false;
		return true;
	}
}
