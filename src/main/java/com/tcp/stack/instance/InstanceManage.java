package com.tcp.stack.instance;

import java.util.List;

import com.tcp.stack.tool.Table;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.log4j.Logger;

import com.tcp.stack.tool.MenuUtil;
import com.tcp.stack.tool.WebElementWait;

public class InstanceManage {

	private static Logger logger = Logger.getLogger(InstanceManage.class);

	private WebDriver driver;
	
	public InstanceManage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void start() {
		MenuUtil.openMenu(this.driver, "主机", "云主机管理");
		try {
			this.createInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createInstance() throws Exception {
		WebDriverWait driverWait = new WebDriverWait(this.driver, 3);
		WebElementWait elementWait = new WebElementWait(10);
		WebElement createBtn = driverWait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//button[@ng-click='createInstance()']")));
		createBtn.click(); // 点击打开创建云主机按钮，打开窗口

		// 判断窗口是否准备完毕
		WebElement createModal = driverWait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("createInstanceModal")));
		String hostname = "test";
		createModal.findElement(By.name("hostName")).sendKeys(hostname);
		createModal.findElement(By.name("loginPassword")).sendKeys("test");

		// 切换至选择镜像tab
		createModal.findElement(By.xpath("//span[contains(text(), '选择镜像')]")).click();
		List<WebElement> imageLinks = createModal.findElements(By.cssSelector("a[ng-click='selectImage(image)'"));
		if(imageLinks!=null && imageLinks.size()>0) {
			// 选中第0个镜像
			Thread.sleep(1000);
			imageLinks.get(0).click();
		}else{
			logger.error("没有可用镜像，无法创建虚拟机");
		}

		// 切换至主机配置tab
		createModal.findElement(By.xpath("//span[contains(text(), '主机配置')]")).click();
		WebElement securityGroup = createModal.findElement(By.xpath("//div[@ng-model='selectedSecurityGroupId']"));
		Thread.sleep(1000);
		securityGroup.click();

		// 选择安全组
		WebElement securityGroupUl = elementWait.findElement(securityGroup, By.cssSelector("ul.select-items"));
		List<WebElement> securityGroupLis = elementWait.
				findElements(securityGroupUl, By.cssSelector("li[ng-click='select(val)'"));
		if(securityGroupLis!=null  && securityGroupLis.size()>0) {
			for(WebElement secGroup : securityGroupLis){
				if(secGroup.getText().equals("default")) {
					secGroup.click();
				}
			}
		}else {
			logger.error("不能创建云主机，无可用安全组");
		}

		Thread.sleep(1000);// 切换至网络设置tab
		createModal.findElement(By.xpath("//span[contains(text(), '网络设置')]")).click();
		List<WebElement> subnetDivs = elementWait.
				findElements(createModal, By.cssSelector("div[ng-click='addInterface(net, subnet)']"));
		if(subnetDivs != null && subnetDivs.size() > 0) {
			Thread.sleep(1000);
			subnetDivs.get(0).click();
		}else {
			logger.error("不能创建云主机，无可用子网");
		}
		WebElement doCreateBtn = elementWait.
				findElement(createModal, By.cssSelector("button[ng-click='doCreate()']"));
		Thread.sleep(1000);
		doCreateBtn.click();
		Thread.sleep(30000);

		driver.findElement(By.xpath("//button[@class='btn btn-refresh']"));
		Thread.sleep(2000);

		//判断虚拟机是否创建成功
		Table table = new Table(driver);
		By by = By.xpath("//*[@id='instance']/div[2]/div/div[2]/div/table/tbody");
		//获取虚拟机名称和状态
		String vmName = table.getCellText(by, "0.1");
		String vmStatus = table.getCellText(by,"0.2");

		if (vmName.equals(hostname)&&vmStatus.equals("运行中")){
			logger.debug("创建虚拟机成功！");

			//删除instance
			List<WebElement> servers = driver.findElements(By.tagName("label"));
			if (servers.size()>1){
				servers.get(1).click();
			}
			WebElement moreOpts = driver.
					findElement(By.xpath("//div[@class='btn btn-primary btn-md dropdown-toggle']"));
			moreOpts.click();
			WebElement delBtn = elementWait.
					findElement(moreOpts,By.xpath("//a[@ng-click='deleteAction()']"));
			delBtn.click();

			WebElement okBtn = driverWait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@ng-click='confirm()']")));
			Thread.sleep(3000);
			okBtn.click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//button[@class='btn btn-refresh']"));
			logger.debug("删除虚拟机成功！");

		}else {
			logger.error("创建虚拟机失败！");
		}


	}

}
