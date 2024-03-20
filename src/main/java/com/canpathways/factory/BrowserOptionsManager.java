package com.canpathways.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserOptionsManager {

	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions eo;
	
	private Properties prop;

	
	public BrowserOptionsManager(Properties prop) {
		this.prop = prop;
	}
	
	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();	
		if(prop.getProperty("headless").equalsIgnoreCase("true")) {
			co.addArguments("--headless=new");
		}
		return co;
	}
	
	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();	
		if(prop.getProperty("headless").equalsIgnoreCase("true")) {
			fo.addArguments("--headless");
		}
		return fo;
	}
	
	public EdgeOptions getEdgeOptions() {
		eo = new EdgeOptions();	
		if(prop.getProperty("headless").equalsIgnoreCase("true")) {
			eo.addArguments("--headless");
		}
		return eo;
	}
}
