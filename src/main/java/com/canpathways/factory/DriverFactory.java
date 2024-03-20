package com.canpathways.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;

/**
 * @author Rohan
 */
public class DriverFactory {
	WebDriver driver;
	Properties prop;
	BrowserOptionsManager optionsManager;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	public WebDriver initDriver(Properties prop) {
		String browserName = prop.getProperty("browser");
		optionsManager = new BrowserOptionsManager(prop);
		switch (browserName.toLowerCase()) {
		case "chrome":
//			driver = new ChromeDriver(optionsManger.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;
		case "firefox":
//			driver = new FirefoxDriver(optionsManger.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;
		case "edge":
//			driver = new EdgeDriver(optionsManger.getEdgeOptions());
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;

		default:
			System.out.println("Please pass the right browser" + browserName);
			;
		}

		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}

	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	public Properties initProp() {
		prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream("./src/test/resources/config/config.properties");
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	public static String getScreenshot(String methodName) {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + methodName + "_" + System.currentTimeMillis()
				+ ".png";
		System.out.println("user directory: " + System.getProperty("user.dir"));
		System.out.println("screenshot path: " + path);
		File destination = new File(path);
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
