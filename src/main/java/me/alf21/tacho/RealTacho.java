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
import me.alf21.vehiclesystem.Calculation;
import me.alf21.vehiclesystem.Tacho;
import me.alf21.vehiclesystem.VehicleData;
import me.alf21.vehiclesystem.VehicleSystem;


public class RealTacho extends Tacho {
	
	private Player 			player;
	private PlayerTextdraw	box,
							mainDot,
							designMainDot;
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
			createDot(1, 0);
			createDot(2, 0);
			createDot(3, 0);
			
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
		
		designMainDot = mainDot = box = null;
		
		created = false;
	}
	
	private void createDot(int value, float speed) {
		float r = 14;
		PlayerTextdraw dot = PlayerTextdraw.create(player, getDotLocation(r*value, new Vector2D(551.5f, 409), speed));
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
	
	private Vector2D getDotLocation(float radius, Vector2D mainVector2d, float speed) {
		double cycleSize = 2.0 * Math.PI * (double) radius * (180.0 / 360.0);
		float currentWidth = (speed / 200f) * (float) cycleSize;
		float angle = currentWidth * 180f / ((float) Math.PI * radius);
		
		float x = (radius * (float) Math.cos(Math.toRadians(angle)));
		float y = (radius * (float) Math.sin(Math.toRadians(angle)));
		
		return new Vector2D(mainVector2d.getX()-x, mainVector2d.getY()-y);
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
			
			for(PlayerTextdraw dot : dots) {
				dot.hide();
				dot.destroy();
			}
			
			float speed = Calculation.getSpeed(vehicleData);
			
			createDot(1, speed);
			createDot(2, speed);
			createDot(3, speed);
			
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
		box.hide();
	}
	
	/*
	 * (non-Javadoc)
	 * @see me.alf21.vehiclesystem.Tacho#show()
	 */
	@Override
	public void show() {
		box.show();
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
