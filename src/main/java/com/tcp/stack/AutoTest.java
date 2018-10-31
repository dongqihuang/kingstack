package com.tcp.stack;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.tcp.stack.instance.InstanceManage;
import com.tcp.stack.user.Auth;

public class AutoTest {

	public static void main(String[] args) {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("disable-infobars");
		System.setProperty("webdriver.chrome.driver", "/Users/huangdongqi/Documents/stack/src/main/resources/driver/chromedriver");
		WebDriver driver = new ChromeDriver(chromeOptions);
		driver.get("http://10.160.61.40:8080/login");




		new Auth(driver).start();
		new InstanceManage(driver).start();

		try {
			Thread.sleep(3000);
			driver.quit();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
