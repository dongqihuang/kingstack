package com.tcp.stack.tool;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MenuUtil {

	public static void openMenu(WebDriver driver, String menu, String subMenu) {
		try {
			WebDriverWait driverWait = new WebDriverWait(driver, 10);
			WebElement menuDiv = driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mCSB_1_container")));
			if(menuDiv != null) {
				// 判断“主机”菜单是否是打开状态，如果否则打开或选中状态
				WebElement sidebarItem = menuDiv.findElement(By.linkText(menu)).findElement(By.xpath(".."));
				String sidebarItemClass = sidebarItem.getAttribute("class");
				if(!sidebarItemClass.contains("open") && !sidebarItemClass.contains("selected")) {
					sidebarItem.click();
				}
				if(StringUtils.isNotBlank(subMenu)) {
					driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(subMenu))).click();
				}
			}else{
				// 获取菜单列表失败
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
