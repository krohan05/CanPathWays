package com.canpathways.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.canpathways.base.BaseTest;
import com.canpathways.constants.AppConstants;
import com.canpathways.utils.ExcelUtil;

public class SendInviteTest extends BaseTest {
	
	@BeforeClass
	public void dataSetup() {
		String testDataPath = "./src/test/resources/testdata/CanPathwayTestData.xlsx";
		excelUtil = new ExcelUtil(testDataPath);
	}

	@Test
	public void loginTest() throws InterruptedException {
		loginSecQuesPg = loginPg.login(prop.getProperty("username"), prop.getProperty("password"));
		String secQues = loginSecQuesPg.getSecurityQuestion().trim();
		String secAnswer = excelUtil.getDataForKnownValue("securityQuestions", "Answer", secQues);

		dashboardPg = loginSecQuesPg.answerSecurityQuestion(secAnswer);
 		dashboardPg.closeModalPopUpIfDisplayed().clickOnSideNav("Job postings").sendInvite();

	}

}
