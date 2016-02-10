package me.alf21.textdraw;

import net.gtaun.shoebill.data.Vector2D;


public class BoxHeight {
	
	private Vector2D letterSize, position;
	
	public BoxHeight(float x, float minY, float maxY, float letterX, float letterMinY, float letterMaxY, float value, float maxValue) {
		position = new Vector2D(x, maxY - (maxY - minY) * (value / maxValue));
		letterSize = new Vector2D(letterX, letterMinY + (letterMaxY - letterMinY) * (value / maxValue));
	}
	
	
	public Vector2D getLetterSize() {
		return letterSize;
	}
	
	public Vector2D getPosition() {
		return position;
	}
}
