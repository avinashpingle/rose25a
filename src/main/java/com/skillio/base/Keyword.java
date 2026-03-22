package com.skillio.base;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.skillio.exceptions.InvalidBrowserNameException;

/**
 * This class contains all the keywords related to selenium webdriver. Keywords
 * are nothing but the methods which will perform some action on the browser or
 * on the web element.
 * 
 * @author skillio
 */
public class Keyword {

	// Thread-local driver storage. Each thread will get its own RemoteWebDriver
	public static final ThreadLocal<RemoteWebDriver> threadLocal = new ThreadLocal<>();

	// DO NOT store a static driver reference here. It would capture null at class load time
	// and break multi-threaded runs. Use getDriver() to access the thread-specific driver.
	public static RemoteWebDriver getDriver() {
		return threadLocal.get();
	}

	public static void openBrowser(String browserName) {
		if (browserName.equalsIgnoreCase("Chrome")) {
			threadLocal.set(new ChromeDriver());
			System.out.println("Launched browser: " + browserName);
		} else if (browserName.equalsIgnoreCase("Firefox")) {
			threadLocal.set(new FirefoxDriver());
		} else if (browserName.equalsIgnoreCase("Safari")) {
			threadLocal.set(new SafariDriver());
		} else {
			throw new InvalidBrowserNameException(browserName);
		}
	}

	public static void launchUrl(String url) {
		RemoteWebDriver d = getDriver();
		if (d == null) {
			throw new IllegalStateException("Browser has not been initialized. Call openBrowser() before launchUrl().");
		}
		d.get(url);
		System.out.println("Launched url: " + url);
	}

	/**
	 * This method will enter the text in the text box based on the locator type
	 * and locator value passed by the user. Supported locator types are:
	 * <ul>
	 * <li>id</li>
	 * <li>name</li>
	 * <li>className</li>
	 * <li>tagName</li>
	 * <li>linkText</li>
	 * <li>partialLinkText</li>
	 * <li>cssSelector</li>
	 * <li>xpath</li>
	 * </ul>
	 */
	public static void enterText(String locatorType, String locatorValue, String textToEnter) {
		RemoteWebDriver d = getDriver();
		if (d == null) {
			throw new IllegalStateException("Browser is not initialized. Call openBrowser() before interacting with elements.");
		}
		if (locatorType.equalsIgnoreCase("id")) {
			d.findElement(By.id(locatorValue)).sendKeys(textToEnter);
		} else if (locatorType.equalsIgnoreCase("name")) {
			d.findElement(By.name(locatorValue)).sendKeys(textToEnter);
		} else if (locatorType.equalsIgnoreCase("className")) {
			d.findElement(By.className(locatorValue)).sendKeys(textToEnter);
		} else if (locatorType.equalsIgnoreCase("tagName")) {
			d.findElement(By.tagName(locatorValue)).sendKeys(textToEnter);
		} else if (locatorType.equalsIgnoreCase("linkText")) {
			d.findElement(By.linkText(locatorValue)).sendKeys(textToEnter);
		} else if (locatorType.equalsIgnoreCase("partialLinkText")) {
			d.findElement(By.partialLinkText(locatorValue)).sendKeys(textToEnter);
		} else if (locatorType.equalsIgnoreCase("cssSelector")) {
			d.findElement(By.cssSelector(locatorValue)).sendKeys(textToEnter);
		} else if (locatorType.equalsIgnoreCase("xpath")) {
			d.findElement(By.xpath(locatorValue)).sendKeys(textToEnter);
		} else {
			throw new InvalidSelectorException(locatorType);
		}
	}

	/**
	 * This method will click on the web element based on the locator type and locator
	 * @param locatorType
	 * @param locatorValue
	 */
	public static void clickOn(String locatorType, String locatorValue) {
		RemoteWebDriver d = getDriver();
		if (d == null) {
			throw new IllegalStateException("Browser is not initialized. Call openBrowser() before interacting with elements.");
		}
		if (locatorType.equalsIgnoreCase("id")) {
			d.findElement(By.id(locatorValue)).click();
		} else if (locatorType.equalsIgnoreCase("name")) {
			d.findElement(By.name(locatorValue)).click();
		} else if (locatorType.equalsIgnoreCase("className")) {
			d.findElement(By.className(locatorValue)).click();
		} else if (locatorType.equalsIgnoreCase("tagName")) {
			d.findElement(By.tagName(locatorValue)).click();
		} else if (locatorType.equalsIgnoreCase("linkText")) {
			d.findElement(By.linkText(locatorValue)).click();
		} else if (locatorType.equalsIgnoreCase("partialLinkText")) {
			d.findElement(By.partialLinkText(locatorValue)).click();
		} else if (locatorType.equalsIgnoreCase("cssSelector")) {
			d.findElement(By.cssSelector(locatorValue)).click();
		} else if (locatorType.equalsIgnoreCase("xpath")) {
			d.findElement(By.xpath(locatorValue)).click();
		} else {
			throw new InvalidSelectorException(locatorType);
		}
	}

	/**
	 * Clear the text of the element identified by the given locator type/value.
	 * Supported locator types: id, name, className, tagName, linkText,
	 * partialLinkText, cssSelector, xpath.
	 */
	public static void clear(String locatorType, String locatorValue) {
		RemoteWebDriver d = getDriver();
		if (d == null) {
			throw new IllegalStateException("Browser is not initialized. Call openBrowser() before interacting with elements.");
		}
		if (locatorType.equalsIgnoreCase("id")) {
			d.findElement(By.id(locatorValue)).clear();
		} else if (locatorType.equalsIgnoreCase("name")) {
			d.findElement(By.name(locatorValue)).clear();
		} else if (locatorType.equalsIgnoreCase("className")) {
			d.findElement(By.className(locatorValue)).clear();
		} else if (locatorType.equalsIgnoreCase("tagName")) {
			d.findElement(By.tagName(locatorValue)).clear();
		} else if (locatorType.equalsIgnoreCase("linkText")) {
			d.findElement(By.linkText(locatorValue)).clear();
		} else if (locatorType.equalsIgnoreCase("partialLinkText")) {
			d.findElement(By.partialLinkText(locatorValue)).clear();
		} else if (locatorType.equalsIgnoreCase("cssSelector")) {
			d.findElement(By.cssSelector(locatorValue)).clear();
		} else if (locatorType.equalsIgnoreCase("xpath")) {
			d.findElement(By.xpath(locatorValue)).clear();
		} else {
			throw new InvalidSelectorException(locatorType);
		}
	}
	
	public static void quitBrowser() {
		RemoteWebDriver d = getDriver();
		if (d != null) {
			d.quit();
			// remove reference for this thread to avoid reuse after quit
			threadLocal.remove();
			System.out.println("Driver quit successfully.");
		} else {
			System.out.println("No driver instance found for this thread to quit.");
		}
	} 

}