package me.alf21.tacho;

import java.util.ArrayList;

import net.gtaun.shoebill.constant.TextDrawAlign;
import net.gtaun.shoebill.constant.TextDrawFont;
import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.data.Vector2D;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.object.PlayerTextdraw;
import net.gtaun.shoebill.object.Timer;
import net.gtaun.shoebill.object.Vehicle;
import me.alf21.textdraw.BoxHeight;
import me.alf21.vehiclesystem.Calculation;
import me.alf21.vehiclesystem.Tacho;
import me.alf21.vehiclesystem.VehicleData;
import me.alf21.vehiclesystem.VehicleSystem;


public class RealTacho extends Tacho {
	private static final Color 	COLOR_GREEN = new Color(0,150,0,255),
								COLOR_RED = new Color(150,0,0,255);
	
	private Player 			player;
	private PlayerTextdraw	box,
							mainDot,
							designMainDot,
							vehicleName,
							healthBar,
							tankBar;
	private ArrayList<PlayerTextdraw> values, dots;
	private boolean			created;
	private VehicleData 	vehicleData;
	private Timer 			timer;
	
	
	public RealTacho(Player player) {
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
			
			box = PlayerTextdraw.create(player, 564, 355);
			box.setText("_");
			box.setAlignment(TextDrawAlign.CENTER);
			box.setBackgroundColor(Color.BLACK);
			box.setFont(TextDrawFont.get(1));
			box.setLetterSize(0.5f, 9.1f);
			box.setColor(Color.WHITE);
			box.setOutlineSize(0);
			box.setProportional(true);
			box.setShadowSize(1);
			box.setUseBox(true);
			box.setBoxColor(new Color(255,255,255,50));
			box.setTextSize(0, 142);
			box.setSelectable(false);
			
			vehicleName = PlayerTextdraw.create(player, 564, 340);
			vehicleName.setText(vehicle.getModelName());
			vehicleName.setAlignment(TextDrawAlign.CENTER);
			vehicleName.setBackgroundColor(Color.BLACK);
			vehicleName.setFont(TextDrawFont.get(1));
			vehicleName.setLetterSize(0.36f, 0.9f);
			vehicleName.setColor(Color.WHITE);
			vehicleName.setOutlineSize(1);
			vehicleName.setProportional(true);
			vehicleName.setUseBox(true);
			vehicleName.setBoxColor(new Color(255,255,255,50));
			vehicleName.setTextSize(0, -150);
			vehicleName.setSelectable(false);

			createHealthBar();

			createTankBar();
	
			mainDot = PlayerTextdraw.create(player, 546, 404);
			mainDot.setText("|");
			mainDot.setBackgroundColor(Color.BLACK);
			mainDot.setFont(TextDrawFont.get(1));
			mainDot.setLetterSize(1.67f, 6.6f);
			mainDot.setColor(Color.WHITE);
			mainDot.setOutlineSize(1);
			mainDot.setProportional(true);
			mainDot.setSelectable(false);
	
			designMainDot = PlayerTextdraw.create(player, 551.5f, 412.5f);
			designMainDot.setText("|");
			designMainDot.setBackgroundColor(Color.BLACK);
			designMainDot.setFont(TextDrawFont.get(1));
			designMainDot.setLetterSize(0.97f, 3.4f);
			designMainDot.setColor(new Color(255,255,255,100));
			designMainDot.setOutlineSize(1);
			designMainDot.setProportional(true);
			designMainDot.setSelectable(false);
			
			dots = new ArrayList<PlayerTextdraw>();
			
			float speed = Calculation.getSpeed(vehicleData);
			for(int i = 1; i <= 6; i++) {
				createDot(i, speed);
			}
			
			values = new ArrayList<PlayerTextdraw>();
			values.add(PlayerTextdraw.create(player, 507, 417, "0"));
			values.add(PlayerTextdraw.create(player, 504, 401, "20"));
			values.add(PlayerTextdraw.create(player, 511, 385, "40"));
			values.add(PlayerTextdraw.create(player, 521, 371, "60"));
			values.add(PlayerTextdraw.create(player, 537, 362, "80"));
			values.add(PlayerTextdraw.create(player, 559, 357, "100"));
			values.add(PlayerTextdraw.create(player, 584, 362, "120"));
			values.add(PlayerTextdraw.create(player, 601, 371, "140"));
			values.add(PlayerTextdraw.create(player, 612, 385, "160"));
			values.add(PlayerTextdraw.create(player, 617, 401, "180"));
			values.add(PlayerTextdraw.create(player, 620, 417, "200"));
			
			for(PlayerTextdraw value : values) {
				value.setAlignment(TextDrawAlign.CENTER);
				value.setBackgroundColor(Color.BLACK);
				value.setFont(TextDrawFont.get(2));
				value.setLetterSize(0.3f, 1);
				value.setColor(Color.WHITE);
				value.setOutlineSize(1);
				value.setProportional(true);
				value.setSelectable(false);
			}
			
			timer = Timer.create(100, (factualInterval) -> {
				update();
			});
			
			created = true;
		}
	}
	
	private void createHealthBar() {
		BoxHeight boxHeight = new BoxHeight(534, 385, 435, 0.5f, -0.5f, 5, vehicleData.getVehicle().getHealth(), 1000);
		
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
		healthBar.setTextSize(0, 11);
		healthBar.setSelectable(false);
	}

	private void createTankBar() {
		BoxHeight boxHeight = new BoxHeight(584, 385, 435, 0.5f, -0.5f, 5, vehicleData.getTank(), 100);
		
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
		tankBar.setTextSize(0, 11);
		tankBar.setSelectable(false);
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
		
		if(values != null && !values.isEmpty()) {
			for(PlayerTextdraw value : values) {
				value.destroy();
			}
			values.clear();
			values = null;
		}

		if(dots != null && !dots.isEmpty()) {
			for(PlayerTextdraw dot : dots) {
				dot.destroy();
			}
			dots.clear();
			dots = null;
		}
		
		designMainDot.destroy();
		mainDot.destroy();
		tankBar.destroy();
		healthBar.destroy();
		vehicleName.destroy();
		box.destroy();
		
		designMainDot = mainDot = tankBar = healthBar = vehicleName = box = null;
		
		created = false;
	}
	
	private void createDot(int value, float speed) {
		float r = 7;
		PlayerTextdraw dot = PlayerTextdraw.create(player, getDotVector2d(3+r*value, new Vector2D(551.5f, 412), speed));
		dot.setText("|");
		dot.setBackgroundColor(new Color(0,0,0,100));
		dot.setFont(TextDrawFont.get(1));
		dot.setLetterSize(0.97f, 3.4f);
		dot.setColor(Color.BLACK);
		dot.setOutlineSize(1);
		dot.setProportional(true);
		dot.setSelectable(false);
		dots.add(dot);
	}
	
	private Vector2D getDotVector2d(float radius, Vector2D mainVector2d, float speed) {
		double cycleSize = 2.0 * Math.PI * (double) radius * (180.0 / 360.0);
		float currentWidth = (speed / 200f) * (float) cycleSize;
		double angle = (Math.round(currentWidth * 180f / ((float) Math.PI * radius))*100)/100;
		
		double x = (radius * Math.cos(Math.toRadians(angle)));
		double y = (radius * Math.sin(Math.toRadians(angle)));
		
		return new Vector2D(mainVector2d.getX() - (float) x, mainVector2d.getY() - (float) y);
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
			else {
				vehicle.getState().setEngine(0);
				player.sendGameText(3000, 3, "Tank is empty!");
			}
			
			healthBar.hide();
			tankBar.hide();
			
			healthBar.destroy();
			createHealthBar();
			tankBar.destroy();
			createTankBar();
			
			healthBar.show();
			tankBar.show();
			
			for(PlayerTextdraw dot : dots) {
				dot.hide();
				dot.destroy();
			}
			
			float speed = Calculation.getSpeed(vehicleData);
			for(int i = 1; i <= 6; i++) {
				createDot(i, speed);
			}
			
			for(PlayerTextdraw dot : dots) {
				dot.show();
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see me.alf21.vehiclesystem.Tacho#isDestroyed()
	 */
	@Override
	public boolean isDestroyed() {
		if(box != null
		|| vehicleName != null
		|| healthBar != null
		|| tankBar != null
		|| mainDot != null
		|| designMainDot != null
		|| dots != null
		|| values != null
		|| timer != null)
			return false;
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see me.alf21.vehiclesystem.Tacho#hide()
	 */
	@Override
	public void hide() {
		timer.stop();
		
		for(PlayerTextdraw value : values) {
			value.hide();
		}
		for(PlayerTextdraw dot : dots) {
			dot.hide();
		}
		designMainDot.hide();
		mainDot.hide();
		tankBar.hide();
		healthBar.hide();
		vehicleName.hide();
		box.hide();
	}
	
	/*
	 * (non-Javadoc)
	 * @see me.alf21.vehiclesystem.Tacho#show()
	 */
	@Override
	public void show() {
		box.show();
		vehicleName.show();
		healthBar.show();
		tankBar.show();
		mainDot.show();
		designMainDot.show();
		for(PlayerTextdraw dot : dots) {
			dot.show();
		}
		for(PlayerTextdraw value : values) {
			value.show();
		}
		
		timer.start();
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
	 * @see me.alf21.vehiclesystem.Tacho#getVehicleData()
	 */
	@Override
	public VehicleData getVehicleData() {
		return vehicleData;
	}
}
