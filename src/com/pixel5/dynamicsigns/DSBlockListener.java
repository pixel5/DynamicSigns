package com.pixel5.dynamicsigns;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.metadata.FixedMetadataValue;

import com.pixel5.dynamicsigns.DynamicSigns;
import com.pixel5.dynamicsigns.DSObject;

public class DSBlockListener implements Listener {
	private DynamicSigns plugin;
	
	public DSBlockListener(DynamicSigns instance) {
		this.plugin = instance;
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		//Block block = event.getBlock();
		//if (block.getType() == Material.SIGN) {
			//Sign sign = (Sign)block;
			//synchronized (plugin.getSignList()) {
				//plugin.getSignList().add(sign);
				//System.out.println("Sign placed.");
			//}
		//}
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		String dsChecker = event.getLine(0);
		Integer dsKey = Integer.parseInt(event.getLine(1));
		Block block = event.getBlock();
		Sign sign = (Sign)block.getState();
		if (dsChecker.equals("[DynamicSigns]") && dsKey.equals(null)) {
			sign.setMetadata("dsKey", new FixedMetadataValue(plugin, dsKey));
			System.out.println("DynamicSign placed.");
			
			// Create new DSObject, custom sign object that is serializable
			// then add that object to the list of signs
			DSObject newSign = new DSObject(sign, dsKey);
			plugin.getSignList().add(newSign);
			plugin.initialWriteToSign(event, sign, dsKey);
		}
		else {
			event.setLine(0, "Sign Not Found");
			event.setLine(1, "Go to");
			event.setLine(2, "website to");
			event.setLine(3, "make one.");
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		if (block.getType() == Material.SIGN) {
			Sign sign = (Sign)block;
			synchronized (plugin.getSignList()) {
				plugin.getSignList().remove(sign);
				System.out.println("Sign removed.");
			}
		}
	}
}
