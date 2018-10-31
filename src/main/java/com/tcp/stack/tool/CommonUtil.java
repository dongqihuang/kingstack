package com.tcp.stack.tool;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class CommonUtil {

	public static void changeTitle(WebDriver driver, String title) {
		((JavascriptExecutor) driver).executeScript("document.title = '" + title + "'");
	}
}
