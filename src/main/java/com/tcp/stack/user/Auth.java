package com.tcp.stack.user;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tcp.stack.tool.CommonUtil;

public class Auth {

	private WebDriver driver;
	private WebElement nameInput;
	private WebElement pwdInput;
	private WebElement loginBtn;

	public Auth(WebDriver driver) {
		this.driver = driver;
	}

	public void start() {
		try {
			this.nameInput = this.driver.findElement(By.className("do-input-name"));
			this.pwdInput = this.driver.findElement(By.className("do-input-pwd"));
			this.loginBtn = this.driver.findElement(By.id("doLogin"));
			CommonUtil.changeTitle(this.driver, "【该用户不存在。】测试");
			boolean loginResult = this.login("admin1", "openstack1", "该用户不存在。");
			CommonUtil.changeTitle(this.driver, "【该用户不存在。】测试" + (loginResult ? "通过" : "失败"));
			Thread.sleep(1000);
			CommonUtil.changeTitle(this.driver, "【密码错误。】测试");
			loginResult = this.login("admin", "openstack2", "密码错误。");
			CommonUtil.changeTitle(this.driver, "【密码错误。】测试" + (loginResult ? "通过" : "失败"));
			Thread.sleep(1000);
			CommonUtil.changeTitle(this.driver, "【登录成功】测试");
			loginResult = this.login("hdq1", "123", null);
			CommonUtil.changeTitle(this.driver, "【登录成功】测试" + (loginResult ? "通过" : "失败"));
			Thread.sleep(1000);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private boolean login(String username, String password, String expectResult) {

		try {
			this.nameInput.clear();
			this.pwdInput.clear();

			this.nameInput.sendKeys(username);
			this.pwdInput.sendKeys(password);
			loginBtn.click();

			if (StringUtils.isNotBlank(expectResult)) {
				WebDriverWait driverWait = new WebDriverWait(this.driver, 10);
				boolean loginResult = driverWait.until(
						ExpectedConditions.textToBePresentInElementLocated(By.className("login-msg"), expectResult));
				if (loginResult) {
					// System.out.println("测试通过");
				} else {
					System.err.println("测试失败");
				}
				return loginResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
