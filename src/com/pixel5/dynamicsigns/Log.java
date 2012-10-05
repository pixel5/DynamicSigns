package com.pixel5.dynamicsigns;

import com.pixel5.dynamicsigns.DynamicSigns;

public class Log {
	@SuppressWarnings("unused")
	private DynamicSigns plugin;

	public Log(DynamicSigns instance) {
		this.plugin = instance;
	}

	public void println(String str) {
		System.out.println(str);
	}
}