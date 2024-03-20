package com.canpathways.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.canpathways.utils.ElementUtil;
import com.canpathways.utils.JavaScriptUtil;

public class DashboardPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	private JavaScriptUtil jsUtil;
	Actions action;

	// Locators
	By surveyQuesModalPopUp = By.xpath("//div[@class='mfp-content']/section[contains(@class,'modal-dialog')]");
	By modalPopUpClose = By.xpath("//button[@class='mfp-close']");
	By pageLoader = By.xpath("//div[@class='loading row']");
	By sideNav_jobPosting = By.xpath("//ul[@id='employerNavs']/li//ul/li/a[@title='Job postings']");
	By jobListLengthDropDown = By.xpath("//select[@name='joblist_length']");
	By matchListLengthDropDown = By.xpath("//select[@name='matchlistpanel_length']");
	By totalJobListRows = By.xpath("//table[@id='joblist']//tbody/tr");
	By matchListRows = By.xpath("//table[@id='matchlistpanel']//tbody/tr");
	By inviteModalPopUp = By.xpath("//section[@id='howtoapply']");
	By inviteToApplyBtn = By.xpath("//section[@id='howtoapply']//input[@value='Invite to apply']");

	public DashboardPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
		action = new Actions(driver);
		jsUtil = new JavaScriptUtil(driver);
	}

	public DashboardPage closeModalPopUpIfDisplayed() {
		eleUtil.waitForPageToLoad(10);
		List<WebElement> eleList = eleUtil.getElements(surveyQuesModalPopUp);
		if (eleList.size() > 0) {
			eleUtil.doClick(modalPopUpClose);
			action.pause(Duration.ofSeconds(2)).perform();
		}
		return this;
	}

	public DashboardPage clickOnSideNav(String menuItem) {
		By sideMenu = By.xpath("//ul[@id='employerNavs']/li//ul/li/a[@title='" + menuItem + "']");
		eleUtil.doClick(sideMenu);
		eleUtil.waitForElementToBeInvisible(pageLoader, 15);
		eleUtil.waitForTitleContains(menuItem, 15);
		return this;
	}

	public void changeTableLengthTo(String number) {
		Select select = new Select(eleUtil.getElement(jobListLengthDropDown));
		select.selectByValue(number);
		action.pause(Duration.ofSeconds(3)).perform();
	}

	public void changeMatchListLengthTo(String number) {
		jsUtil.scrollIntoView(eleUtil.getElement(matchListLengthDropDown));
		Select select = new Select(eleUtil.getElement(matchListLengthDropDown));
		select.selectByValue(number);
		action.pause(Duration.ofSeconds(3)).perform();
	}

	public void navigateBackToJobMatchForEmployersPage() {
		driver.navigate().back();
		eleUtil.waitForTitleContains("Job Match for Employers", 10);
		action.pause(Duration.ofSeconds(3)).perform();
		eleUtil.waitForElementVisible(matchListLengthDropDown, 10);
		jsUtil.scrollIntoView(eleUtil.getElement(matchListLengthDropDown));
	}

	public void sendInvite() {
		changeTableLengthTo("100");
		List<WebElement> rowElements = eleUtil.getElements(totalJobListRows);

		for (WebElement ele : rowElements) {
			String fileStatus = ele.findElement(By.xpath("td/span[contains(@class,'statusId')]")).getText();
			String hitCount = ele
					.findElement(By.xpath("td[contains(@class,'align-center')]/a[contains(@class,'num-matches')]/span"))
					.getText();

			if (fileStatus.equalsIgnoreCase("Advertised") && !hitCount.equalsIgnoreCase("0")) {
				ele.findElement(By.xpath("td[contains(@class,'align-center')]/a[@class='num-matches hit']/span"))
						.click();
				eleUtil.waitForTitleContains("Job Match for Employers", 10);
				action.pause(Duration.ofSeconds(3)).perform();
				eleUtil.waitForElementVisible(matchListLengthDropDown, 10);

				changeMatchListLengthTo("100");
				action.pause(Duration.ofSeconds(2)).perform();
				List<WebElement> matchElements = eleUtil.getElements(matchListRows);

				for (WebElement match : matchElements) {
					String invitedStatus = match
							.findElement(
									By.xpath("td[contains(@class,'invited-column')]//span[contains(@class,'wb-inv')]"))
							.getText();
					if (invitedStatus.equalsIgnoreCase("Not invited to apply")) {
						match.findElement(By.xpath(
								"td[contains(@class,'control sorting')]/a[contains(@title,'Show full profile')]"))
								.click();
						eleUtil.waitForElementVisible(inviteModalPopUp, 20);
						jsUtil.scrollIntoView(eleUtil.getElement(inviteToApplyBtn));
						eleUtil.doClick(inviteToApplyBtn);
						eleUtil.waitForElementToBeInvisible(inviteModalPopUp, 40);

					}
				}

			}

		}
	}

}
