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


public class IndividuelRealTacho extends Tacho {
	private static final Color 	COLOR_GREEN = new Color(0,150,0,255),
								COLOR_RED = new Color(150,0,0,255);
	private static double tachoAngle = 190;
	private static double maxSpeed = 240;
	private static final int speedSteps = 20;
	private static Color color = new Color(0,0,150,50);
	
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
	
	
	public IndividuelRealTacho(Player player) {
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
			maxSpeed = VehicleSystem.getHandling(vehicle.getModelName()).getTransmission().getMaxVelocity(); //
			while(maxSpeed % speedSteps != 0) {
				maxSpeed++;
			}
			
			vehicleData = VehicleSystem.getVehicleData(player);
			
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
			box.setBoxColor(color);
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
			vehicleName.setBoxColor(color);
			vehicleName.setTextSize(0, -150);
			vehicleName.setSelectable(false);

			createHealthBar();

			createTankBar();
	
			mainDot = PlayerTextdraw.create(player, maxSpeed>=100?549:551, 404);
			mainDot.setText("|");
			mainDot.setBackgroundColor(Color.BLACK);
			mainDot.setFont(TextDrawFont.get(1));
			mainDot.setLetterSize(1.67f, 6.6f);
			mainDot.setColor(Color.WHITE);
			mainDot.setOutlineSize(1);
			mainDot.setProportional(true);
			mainDot.setSelectable(false);
	
			designMainDot = PlayerTextdraw.create(player, maxSpeed>=100?554.5f:556.5f, 412.5f);
			designMainDot.setText("|");
			designMainDot.setBackgroundColor(Color.BLACK);
			designMainDot.setFont(TextDrawFont.get(1));
			designMainDot.setLetterSize(0.97f, 3.4f);
			designMainDot.setColor(new Color(255,255,255,100));
			designMainDot.setOutlineSize(1);
			designMainDot.setProportional(true);
			designMainDot.setSelectable(false);
			
			dots = new ArrayList<PlayerTextdraw>();
			
			double speed = Calculation.getSpeed(vehicleData);
			for(int i = 1; i <= 6; i++) {
				createDot(i, speed);
			}
			
			values = new ArrayList<PlayerTextdraw>();
			for(int i = 0; i <= (int) maxSpeed / speedSteps; i++) {
				createValue(i);
			}
			
			timer = Timer.create(100, (factualInterval) -> {
				update();
			});
			
			created = true;
		}
	}
	
	private void createHealthBar() {
		BoxHeight boxHeight = new BoxHeight(maxSpeed>=100?532:534, 385, 435, 0.5f, -0.5f, 5, vehicleData.getVehicle().getHealth(), 1000);
		
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
		BoxHeight boxHeight = new BoxHeight(maxSpeed>=100?592:594, 385, 435, 0.5f, -0.5f, 5, (float) vehicleData.getTank(), (float) vehicleData.getMaxTankSize());
		
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
		tankBar.setBoxColor(Calculation.getBoxColor(COLOR_RED, COLOR_GREEN, (float) vehicleData.getTank(), (float) vehicleData.getMaxTankSize()));
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
	
	private void createValue(int i) {
		float r = 56;
		Vector2D vector2d = new Vector2D(maxSpeed>=100?562:564, 415);
		double angle = getDotAngle(r, vector2d, i*speedSteps);
		
		TextDrawAlign align = TextDrawAlign.CENTER;
		if(angle <= 70.0) 
			align = TextDrawAlign.RIGHT;
		else if(angle >= 110.0)
			align = TextDrawAlign.LEFT;
		else {
			if(angle > 90)
				angle += 4;
			else if(angle < 90)
				angle -= 4;
		}
		
		PlayerTextdraw value = PlayerTextdraw.create(player, getDotVector2dFromAngle(r, vector2d, angle), String.valueOf(i*speedSteps));		
		value.setAlignment(align);
		value.setBackgroundColor(Color.BLACK);
		value.setFont(TextDrawFont.get(2));
		value.setLetterSize(0.2f, 0.8f);
		value.setColor(Color.WHITE);
		value.setOutlineSize(1);
		value.setProportional(true);
		value.setSelectable(false);
		values.add(value);
	}
	
	private void createDot(int value, double speed) {
		float r = 7;
		PlayerTextdraw dot = PlayerTextdraw.create(player, getDotVector2dFromSpeed(3+r*value, new Vector2D(maxSpeed>=100?554.5f:556.5f, 412), speed));
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
	
	private Vector2D getDotVector2dFromSpeed(float radius, Vector2D mainVector2d, double speed) {
		double angle = getDotAngle(radius, mainVector2d, speed);
		
		double x = (radius * Math.cos(Math.toRadians(angle)));
		double y = (radius * Math.sin(Math.toRadians(angle)));
		
		return new Vector2D(mainVector2d.getX() - (float) x, mainVector2d.getY() - (float) y);
	}
	
	private Vector2D getDotVector2dFromAngle(float radius, Vector2D mainVector2d, double angle) {
		double x = (radius * Math.cos(Math.toRadians(angle)));
		double y = (radius * Math.sin(Math.toRadians(angle)));
		
		return new Vector2D(mainVector2d.getX() - (float) x, mainVector2d.getY() - (float) y);
	}
	
	private double getDotAngle(float radius, Vector2D mainVector2d, double speed) {
		double cycleSize = 2.0 * Math.PI * (double) radius * (tachoAngle / 360.0);
		float currentWidth = ((float) speed / (float) maxSpeed) * (float) cycleSize;
		double angle = (Math.round(currentWidth * (float) tachoAngle / ((float) Math.PI * radius))*100)/100;
		angle -= tachoAngle - 180.0;
		return angle;
	}

	/*
	 * (non-Javadoc)
	 * @see me.alf21.vehiclesystem.Tacho#update()
	 */
	@Override
	public void update() {
		Vehicle vehicle = player.getVehicle();
		if(isCreated() && vehicle != null) {
			if(vehicleData.getTank() < 1) {
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
			
			double speed = Calculation.getSpeed(vehicleData);
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
	
	/**
	 * set the background color of the tacho
	 * @param color the background color of the tacho
	 */
	public static void setColor(Color color) {
		IndividuelRealTacho.color = color;
	}
	
	/**
	 * get the color of the tacho
	 * @return color the color of the tacho
	 */
	public static Color getColor() {
		return color;
	}
	
	/**
	 * set the angle of the tacho
	 * @param tachoAngle the angle of the tacho
	 */
	public static void setTachoAngle(double tachoAngle) {
		IndividuelRealTacho.tachoAngle = tachoAngle;
	}
	
	/**
	 * set the limit of the tacho
	 * @param maxSpeed the limit of the tacho
	 */
	public static void setMaxSpeed(int maxSpeed) {
		IndividuelRealTacho.maxSpeed = maxSpeed;
	}
}
