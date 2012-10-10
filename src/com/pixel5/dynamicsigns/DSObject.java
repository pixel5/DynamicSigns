package com.pixel5.dynamicsigns;

import java.io.Serializable;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;

public class DSObject implements Serializable {
	private DynamicSigns plugin;
	
	//private Location signLocation;
	//private World signWorld;
	private Integer signX;
	private Integer signY;
	private Integer signZ;
	private Integer sign_id;
	
	public DSObject(Sign sign, Integer dsKey) {
		//signLocation = sign.getLocation();
		//signWorld = sign.getWorld();
		signX = sign.getX();
		signY = sign.getY();
		signZ = sign.getZ();
		sign_id = dsKey;
	}
	
	public Integer getSignX(DSObject listSign) {
		return listSign.signX;
	}
	
	public Integer getSignY(DSObject listSign) {
		return listSign.signY;
	}
	
	public Integer getSignZ(DSObject listSign) {
		return listSign.signZ;
	}



}
