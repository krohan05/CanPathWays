package com.canpathways.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.canpathways.constants.AppConstants;
import com.canpathways.utils.ElementUtil;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	// Locators
	private By email = By.id("loginForm:input-email");
	private By password = By.id("loginForm:input-password");
	private By signInBtn = By.xpath("//button[@type='submit' and contains(@class,'btn-lg')]");
	private By forgotPwdLink = By.linkText("Forgot your password?");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public LoginSecurityQuesPage login(String emailId, String pwd) {
		eleUtil.waitForElementVisible(email, AppConstants.MEDIUM_TIME_OUT).sendKeys(emailId);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(signInBtn);
		eleUtil.waitForTitleContains(AppConstants.SEC_QUES_PAGE_TITLE, AppConstants.MEDIUM_TIME_OUT);
		return new LoginSecurityQuesPage(driver);
	}

}
