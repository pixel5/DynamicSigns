package com.pixel5.dynamicsigns;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.bukkit.configuration.file.YamlConfiguration;

import com.pixel5.dynamicsigns.DynamicSigns;
import com.pixel5.dynamicsigns.Log;
import com.pixel5.dynamicsigns.DSObject;

public class Config {
	private DynamicSigns instance;
	private Log log;
	private ArrayList<DSObject> signList = new ArrayList<DSObject>();
	
	public String directory = "plugins" + File.separator + DynamicSigns.class.getSimpleName();
	File file = new File(directory + File.separator + "sign.list");

	public Config(DynamicSigns instance) {
		this.instance = instance;
		this.log = this.instance.log;
	}

	public Config(DSBlockListener dsBlockListener) {
		this.instance = instance;
	}

	public boolean signListNull(){
		boolean result;
		if (instance.getSignList().equals(null)){
			result = true;
		}
		else {
			result = false;
		}
		return result;
	}
	
	// Check to see if signs.txt exists. If not, create it.
	// If signs.txt exists, deserialize and initial signList as ArrayList<Sign> with all signs in signlist.txt
	// Untested
	@SuppressWarnings("unchecked")
	public ArrayList<DSObject> signFileCheck() {
		new File(directory).mkdir();
		if (!file.exists()) {
			try {
				file.createNewFile();
				addDefaults();
			} catch (Exception e) {
				System.out.println("Unable to create sign list file. " + e);
			}
		} else {
			try {  
				FileInputStream fis = new FileInputStream(file); 
				ObjectInputStream ois = new ObjectInputStream(fis); 
				signList = (ArrayList<DSObject>)ois.readObject(); 
				ois.close(); 
				System.out.println("signList: " + signList); 
				} 
				catch(Exception e) { 
				System.out.println("Exception during deserialization: " + 
				e); 
				System.exit(0); 
				} 
				} 
		// loadKeys();
		return signList;
	}
	
	public void saveSignList(ArrayList<DSObject> signList) {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(file));
		    out.writeObject(signList);
		    System.out.println("Wrote Sign List with \"" + signList + "\" to file");
		}
		catch (IOException e) {
		    System.err.println("Error writing object: " + e.getMessage());
		}
		finally {
			try {
				out.close();
		      }
		    catch (IOException e) {
		        System.err.println(e.getMessage());
		    }
		}
	}

	public YamlConfiguration load() {
		try {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			return config;
		} catch (Exception e) {
			log.println("Unable to load config file.");
		}
		return null;
	}

	private void addDefaults() {
		log.println("Generating Config file...");
		// Do I need anything here?
	}

	//private void loadKeys() {
	//	log.println("Loading Config File...");
	//}

	public void write(String root, Object x) {
		YamlConfiguration config = load();
		config.set(root, x);
		try {
			config.save(file);
		} catch (IOException e) {
			log.println("There was an error saving configuration to file " + file.getName());
		}
	}

	public Boolean readBoolean(String root) {
		YamlConfiguration config = load();
		return config.getBoolean(root, true);
	}

	public Double readDouble(String root) {
		YamlConfiguration config = load();
		return config.getDouble(root, 0);
	}

	public String readString(String root) {
		YamlConfiguration config = load();
		return config.getString(root);
	}
}