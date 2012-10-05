package com.pixel5.dynamicsigns;

import java.io.File;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Sign;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.pixel5.dynamicsigns.Config;

public class DynamicSigns extends JavaPlugin {
	
	private DSBlockListener blockListener;
	private ArrayList<Sign> signList;
	private Config config;
	
	// Public objects
	public Log log = null;
	
	@Override
	public void onEnable() {
		// Create plugin configuration directory if it doesn't exist
		String pluginPath = "plugins" + File.separator + "DynamicSigns" + 
				File.separator;
		File pluginDir = new File(pluginPath);
		if (!pluginDir.exists()) {
			pluginDir.mkdir();
		}
		
		PluginManager pm = getServer().getPluginManager();
		
		// Create sign list file if it doesn't exist and/or initialize signList
		signList = config.signFileCheck();
		blockListener = new DSBlockListener(this);
		pm.registerEvents(this.blockListener, this);
	}
	
	@Override
	public void onDisable() {
		config.saveSignList(signList);
		signList = null; // you'll want to replace this with a method that writes your list to disk eventually
	}
	
	public List<Sign> getSignList() {
		return signList;
	}

	public void initialWriteToSign(Sign sign) {

		try {
			String url = "pixel5.co";
			String user = "dsUser";
			String pass = "testpass";
			Connection sqlConnect = DriverManager.getConnection(url, user, pass);
			Statement select = sqlConnect.createStatement();
			ResultSet result = select.executeQuery("SELECT * FROM `signs` WHERE sign_name = '" + sign.getMetadata("dsKey").get(0).asString() + "'");
			
			if(result.next()) { 
				sign.setLine(0, result.getString("line1"));
				sign.setLine(1, result.getString("line2"));
				sign.setLine(2, result.getString("line3"));
				sign.setLine(3, result.getString("line4"));
			}
			else {
				sign.setLine(0, "Sign Not Found");
				sign.setLine(1, "Go to");
				sign.setLine(2, "website to");
				sign.setLine(3, "make one.");
			}
			
			result.close();
			select.close();
			sqlConnect.close();
		} 
		catch (SQLException sq) {
			sq.printStackTrace();
		}

	}

}
