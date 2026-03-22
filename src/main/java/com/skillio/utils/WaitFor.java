package com.skillio.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.skillio.base.Keyword;

/**
 * This class contains all the wait related keywords. It will have all the
 * methods which will wait for the element to be visible, clickable, etc.
 * 
 * @author skillio
 */
public class WaitFor {

	private static WebDriverWait getWait() {
		if (Keyword.getDriver() == null) {
			throw new IllegalStateException("WebDriver is not initialized. Call openBrowser() before using waits.");
		}
		WebDriverWait wait = new WebDriverWait(Keyword.getDriver(), Duration.ofSeconds(60));
		wait.pollingEvery(Duration.ofMillis(500));
		wait.ignoring(NoSuchElementException.class);
		return wait;
	}

	private WaitFor() {
		// prevent instantiation
	}

	public static void elementToBeVisible(By element) {
		getWait().until(ExpectedConditions.visibilityOfElementLocated(element));
	}

	public static void elementToBeClickable(By element) {
		getWait().until(ExpectedConditions.elementToBeClickable(element));
	}

	public static void elementToBePresent(By element) {
		getWait().until(ExpectedConditions.presenceOfElementLocated(element));
	}
	
	public static void stalenessOfElement(By element) {
		getWait().until(ExpectedConditions.stalenessOf(Keyword.getDriver().findElement(element)));
	}
}