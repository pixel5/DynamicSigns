package com.pixel5.dynamicsigns;

import java.io.Serializable;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;

public class DSObject implements Serializable {
	
	public DSObject(Sign sign, Integer dsKey) {
		Location signLocation = sign.getLocation();
		World signWorld = sign.getWorld();
		int signX = sign.getX();
		int signY = sign.getY();
		int signZ = sign.getZ();
		Integer sign_id = dsKey;
	}
	
}
