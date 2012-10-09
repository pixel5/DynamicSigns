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
		System.out.println(dsChecker);
		String dsKeyChecker = event.getLine(1);
		System.out.println(dsKeyChecker);
		Block block = event.getBlock();
		Sign sign = (Sign)block.getState();
		if (dsChecker.equals("[DynamicSigns]") && (!dsKeyChecker.equals(null) || !dsKeyChecker.equals(""))) {
			System.out.println("Made it to the if statement!");
			sign.setMetadata("dsKey", new FixedMetadataValue(plugin, dsKeyChecker));
			System.out.println("made it to checkpoint 2");
			plugin.getSignList().add(sign);
			System.out.println("Sign placed.");
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
