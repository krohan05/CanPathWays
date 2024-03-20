package com.canpathways.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.canpathways.factory.DriverFactory;
import com.canpathways.pages.DashboardPage;
import com.canpathways.pages.LoginPage;
import com.canpathways.pages.LoginSecurityQuesPage;
import com.canpathways.utils.ExcelUtil;

public class BaseTest {

	WebDriver driver;
	protected Properties prop;
	protected LoginPage loginPg;
	protected LoginSecurityQuesPage loginSecQuesPg;
	protected DashboardPage dashboardPg;
	protected ExcelUtil excelUtil;
	
	DriverFactory df;
	
	protected SoftAssert softAssert;
	
	@BeforeTest
	public void setUp() {
		df = new DriverFactory();
		prop = df.initProp();
		driver = df.initDriver(prop);
		loginPg = new LoginPage(driver);
		softAssert =  new SoftAssert();
	}
	
	
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
