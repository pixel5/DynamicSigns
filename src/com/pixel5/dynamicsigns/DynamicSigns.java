package com.pixel5.dynamicsigns;

import java.io.File;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.bukkit.block.Sign;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.pixel5.dynamicsigns.Config;

public class DynamicSigns extends JavaPlugin {
	
	private DSBlockListener blockListener;
	private Config config = new Config(this);
	private List<Sign> signList;
	
	// Public objects
	public Log log = null;
	
	@Override
	public void onEnable() {
		signList = new ArrayList<Sign>();
		// Create plugin configuration directory if it doesn't exist
		String pluginPath = "plugins" + File.separator + "DynamicSigns" + 
				File.separator;
		File pluginDir = new File(pluginPath);
		if (!pluginDir.exists()) {
			pluginDir.mkdir();
		}
		
		PluginManager pm = getServer().getPluginManager();
		
		// Create sign list file if it doesn't exist and/or initialize signList
		//signList = config.signFileCheck();
		blockListener = new DSBlockListener(this);
		pm.registerEvents(this.blockListener, this);
	}
	
	@Override
	public void onDisable() {
		signList = null;
		//config.saveSignList(signList);
		//signList = null; // you'll want to replace this with a method that writes your list to disk eventually
	}
	
	public List<Sign> getSignList() {
		return signList;
	}

	public void initialWriteToSign(SignChangeEvent event, Sign sign) {

		try {
			String url = "jdbc:mysql://localhost/dsigns";
			String user = "root";
			String pass = "Sideline1!";
			Connection sqlConnect = DriverManager.getConnection(url, user, pass);
			Statement select = sqlConnect.createStatement();
			ResultSet result = select.executeQuery("SELECT * FROM `signs` WHERE sign_name = '" + sign.getMetadata("dsKey").get(0).asString() + "'");
			
			if(result.next()) { 
				event.setLine(0, result.getString("line1"));
				event.setLine(1, result.getString("line2"));
				event.setLine(2, result.getString("line3"));
				event.setLine(3, result.getString("line4"));
			}
			else {
				event.setLine(0, "Sign Not Found");
				event.setLine(1, "Go to");
				event.setLine(2, "website to");
				event.setLine(3, "make one.");
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
