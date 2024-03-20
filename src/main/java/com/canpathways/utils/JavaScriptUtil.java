package com.canpathways.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {

	private WebDriver driver;
	private JavascriptExecutor js;

	public JavaScriptUtil(WebDriver driver) {
		this.driver = driver;
		js = (JavascriptExecutor) this.driver;
	}

	public String getTitleByJS() {
		return js.executeScript("return document.title;").toString();
	}

	public void goBackWithJS() {
		js.executeScript("history.go(-1);");
	}

	public void goForwardWithJS() {
		js.executeScript("history.go(1);");
	}

	public void pageRefreshWithJS() {
		js.executeScript("history.go(0);");
	}

	public String getPageInnerText() {
		return js.executeScript("return document.documentElement.innerText;").toString();
	}

	public void scrollPageDown() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public void scrollPageUp() {
		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
	}

	public void scrollPageDown(String height) {
		js.executeScript("window.scrollTo(0, '" + height + "')");
	}

	public void scrollPageDownMiddlepage() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight/2)");
	}

	public void scrollIntoView(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
}
