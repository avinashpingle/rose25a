package com.skillio.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.skillio.base.Keyword;
import com.skillio.utils.WaitFor;

public class LoginPage {

	@FindBy(xpath = "//input[@name=\"username\"]")
	WebElement userNameTxtBx; // NullPointerException

	@FindBy(xpath = "//input[@name=\"password\"]")
	WebElement passwordTxtBx;

	@FindBy(xpath = "//button[@type=\"submit\"]")
	WebElement loginBtn;

	@FindBy(css = "p.oxd-alert-content-text")
	WebElement loginError;

	{
		PageFactory.initElements(Keyword.getDriver(), this);
	}

	public void enterUserName(String userName) {
		WaitFor.elementToBeClickable(By.xpath("//input[@name=\"username\"]"));
		userNameTxtBx.clear();
		if (userName != null)
			userNameTxtBx.sendKeys(userName);
	}

	public void clearUserName() {
		userNameTxtBx.clear();
	}

	public void enterPassword(String password) {
		passwordTxtBx.clear();
		if (password != null)
			passwordTxtBx.sendKeys(password);
	}

	public void clickLoginBtn() {
		loginBtn.click();
	}

	public String getErroMessage() {
		try {
			WaitFor.elementToBePresent(By.cssSelector("p.oxd-alert-content-text"));
			return loginError.getText();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Attempts to perform login using provided values. Clears fields first.
	 */
	public void loginWithCredentials(String username, String password) {
		enterUserName(username);
		enterPassword(password);
		clickLoginBtn();
	}

	/**
	 * Returns the dashboard header text (commonly an <h6> element on OrangeHRM). Returns empty string if not found.
	 */
	public String getDashboardHeaderText() {
		try {
			WaitFor.elementToBePresent(By.xpath("//h6"));
			WebElement el = Keyword.getDriver().findElement(By.xpath("//h6"));
			return el.getText();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Returns the validation message shown for username input (if any). Empty if none found.
	 */
	public String getUsernameFieldError() {
		try {
			By xpath = By.xpath("//input[@name='username']/ancestor::div[1]//span");
			WaitFor.elementToBePresent(xpath);
			return Keyword.getDriver().findElement(xpath).getText();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Returns the validation message shown for password input (if any). Empty if none found.
	 */
	public String getPasswordFieldError() {
		try {
			By xpath = By.xpath("//input[@name='password']/ancestor::div[1]//span");
			WaitFor.elementToBePresent(xpath);
			return Keyword.getDriver().findElement(xpath).getText();
		} catch (Exception e) {
			return "";
		}
	}

}