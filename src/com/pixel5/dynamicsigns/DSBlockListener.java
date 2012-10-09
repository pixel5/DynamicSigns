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
		String dsKeyChecker = event.getLine(1);
		Block block = event.getBlock();
		Sign sign = (Sign)block.getState();
		if (dsChecker.equals("[DynamicSigns]") && (!dsKeyChecker.equals(null) || !dsKeyChecker.equals(""))) {
			sign.setMetadata("dsKey", new FixedMetadataValue(plugin, dsKeyChecker));
			plugin.getSignList().add(sign);
			System.out.println("DynamicSign placed.");
			plugin.initialWriteToSign(event, sign);
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
