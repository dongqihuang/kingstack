package com.tcp.stack.tool;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

public class WebElementWait {

	private int timeout = 10 * 1000; // default 10 seconds

	/**
	 * 
	 * @param timeout
	 *            unit: seconds
	 */
	public WebElementWait(int timeout) {
		this.timeout = timeout * 1000;
	}

	public WebElementWait() {
	}

	public WebElement findElement(WebElement currentElement, By by) {
		WebElement resultElement = null;
		try {
			long startTime = System.currentTimeMillis();
			while (true) {
				if (System.currentTimeMillis() - startTime > this.timeout) {
					throw new TimeoutException("fail: find element by ["+by+"]");
				}
				resultElement = currentElement.findElement(by);
				if (resultElement != null) {
					return resultElement;
				}
				Thread.sleep(100);
				continue;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return resultElement;
	}
	
	public List<WebElement> findElements(WebElement currentElement, By by) {
		List<WebElement> resultElements = null;
		try {
			long startTime = System.currentTimeMillis();
			while (true) {
				if (System.currentTimeMillis() - startTime > this.timeout) {
					throw new TimeoutException("fail: find elements by ["+by+"]");
				}
				resultElements = currentElement.findElements(by);
				if (resultElements != null && !resultElements.isEmpty()) {
					return resultElements;
				}
				Thread.sleep(100);
				continue;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return resultElements;
	}
}
