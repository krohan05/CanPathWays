package com.canpathways.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.canpathways.constants.AppConstants;
import com.canpathways.utils.ElementUtil;

public class LoginSecurityQuesPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	// Locators
	private By secQuestion = By.xpath("//span[@class='field-name']");
	private By secAnswerTextBox = By.id("securityForm:input-security-answer");
	private By continueBtn = By.id("continueButton");
	private By getDifferentQues = By.xpath("//button[@type='submit' and contains(@class,'btn-default cancel')]");
	private By displayAnswerChkbox = By.xpath("//input[@type='checkbox' and contains(@id,'challenge-answer')]");

	public LoginSecurityQuesPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getSecurityQuestion() {
		eleUtil.waitForPageToLoad(15);
		return eleUtil.waitForElementVisible(secQuestion, AppConstants.MEDIUM_TIME_OUT).getText().trim();
	}

	public DashboardPage answerSecurityQuestion(String answer) {
		eleUtil.waitForElementVisible(secAnswerTextBox, AppConstants.MEDIUM_TIME_OUT).sendKeys(answer);
		eleUtil.doClick(continueBtn);
		eleUtil.waitForTitleContains("Employer Dashboard", AppConstants.LONG_TIME_OUT);
		return new DashboardPage(driver);
	}

}
