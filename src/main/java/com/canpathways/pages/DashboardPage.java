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
	By paginationNextBtn = By.xpath("//a[@class='paginate_button next']");

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

	public void navigateBackToJobMatchForEmployersPage(String url) {
		driver.get(url);
		eleUtil.waitForTitleContains("Job Match for Employers", 10);
		action.pause(Duration.ofSeconds(2)).perform();
		eleUtil.waitForElementVisible(matchListLengthDropDown, 10);
		jsUtil.scrollIntoView(eleUtil.getElement(matchListLengthDropDown));
	}

	public void sendInvite() {
		changeTableLengthTo("100");
		List<WebElement> rowElements = eleUtil.getElements(totalJobListRows);
		int totalRows = rowElements.size();

		for (int i = 0; i < totalRows; i++) {
			int index = i + 1;
			By dasboardRowLocator = By.xpath("(//table[@id='joblist']//tbody/tr)[" + index + "]");

			WebElement rowElement = eleUtil.getElement(dasboardRowLocator);

			String fileStatus = rowElement.findElement(By.xpath("td/span[contains(@class,'statusId')]")).getText();
			String hitCount = rowElement
					.findElement(By.xpath("td[contains(@class,'align-center')]/a[contains(@class,'num-matches')]/span"))
					.getText();

			if (fileStatus.equalsIgnoreCase("Advertised") && !hitCount.equalsIgnoreCase("0")) {
				rowElement.findElement(By.xpath("td[contains(@class,'align-center')]/a[@class='num-matches hit']/span"))
						.click();
				eleUtil.waitForTitleContains("Job Match for Employers", 10);
				action.pause(Duration.ofSeconds(3)).perform();
				eleUtil.waitForElementVisible(matchListLengthDropDown, 10);

				changeMatchListLengthTo("100");
				action.pause(Duration.ofSeconds(2)).perform();

				String jobMatchUrl = driver.getCurrentUrl();
				List<WebElement> matchElements = eleUtil.getElements(matchListRows);
				int totaljobMatchRows = matchElements.size();

				for (int j = 0; j < totaljobMatchRows; j++) {
					int matchIndex = j + 1;
					By jobMatchRowLocator = By.xpath("(//table[@id='matchlistpanel']//tbody/tr)[" + matchIndex + "]");
					WebElement jobMatchRowElement = eleUtil.getElement(jobMatchRowLocator);
					jsUtil.scrollIntoView(jobMatchRowElement);

					String invitedStatus = jobMatchRowElement
							.findElement(
									By.xpath("td[contains(@class,'invited-column')]//span[contains(@class,'wb-inv')]"))
							.getText();

					if (invitedStatus.equalsIgnoreCase("Not invited to apply")) {

						jobMatchRowElement.findElement(By.xpath(
								"td[contains(@class,'control sorting')]/a[contains(@title,'Show full profile')]"))
								.click();
						eleUtil.waitForElementVisible(inviteModalPopUp, 20);
						jsUtil.scrollIntoView(eleUtil.getElement(inviteToApplyBtn));
						eleUtil.doClick(inviteToApplyBtn);
						eleUtil.waitForElementToBeInvisible(inviteModalPopUp, 40);
						navigateBackToJobMatchForEmployersPage(jobMatchUrl);

						if (j == totaljobMatchRows - 1) {
							eleUtil.waitForElementVisible(paginationNextBtn, 5);
							if (eleUtil.getElementsCount(paginationNextBtn) > 0) {
								eleUtil.doClick(paginationNextBtn);
								action.pause(Duration.ofSeconds(2)).perform();
								j = -1;
								totaljobMatchRows = eleUtil.getElements(matchListRows).size();
							} else {
								clickOnSideNav("Job postings");
								break;
							}
						}
					}
				}
				if (i == totalRows - 1) {
					eleUtil.waitForElementVisible(paginationNextBtn, 5);
					if (eleUtil.getElementsCount(paginationNextBtn) > 0) {
						eleUtil.doClick(paginationNextBtn);
						action.pause(Duration.ofSeconds(2)).perform();
						i = -1;
						totalRows = eleUtil.getElements(totalJobListRows).size();
					} else {
						break;
					}
				}
			}
		}
	}
}
