package com.skillio.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.skillio.base.Keyword;
import com.skillio.utils.WaitFor;

public class HomePage {

	@FindBy(xpath = "//span[text()=\"Dashboard\"]")
	WebElement dashboardMenu;
	@FindBy(xpath = "//span[text()=\"PIM\"]")
	WebElement pimMenu;
	@FindBy(xpath = "//span[text()=\"Admin\"]")
	WebElement adminMenu;
	
	By element = By.xpath("");
	
	public void clickOnElement() {
		Keyword.getDriver().findElement(element).click();
	}
	
	
	{
		PageFactory.initElements(Keyword.getDriver(), this);
	}
	
	public void waitForPIMMenuToBeVisible() {
		WaitFor.elementToBeVisible(By.xpath("//span[text()=\"PIM\"]"));
	}
	public void clickPIMMenu() {
		pimMenu.click();		
	}
}