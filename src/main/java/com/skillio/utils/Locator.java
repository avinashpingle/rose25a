package com.skillio.utils;

/**
 * This interface contains all the locators of the application. We are using
 * interface because we want to achieve abstraction and we don't want to create
 * the object of this class. We are declaring all the locators as public static
 * final because we want to access them from anywhere in the code and we don't
 * want to change their values.
 * Write all the locators of the application in this interface and use them in the test
 * @author skillio
 */
public interface Locator {

	String username = "//input[@name=\"username\"]";
	String password = "//input[@name=\"password\"]";
	String signInBtn = "//button[@type=\"submit\"]";
	String pimMenu = "//ul[@class=\"oxd-main-menu\"]/li[2]";
	String addEmployeeBtn = "//nav[@class=\"oxd-topbar-body-nav\"]/ul/li[3]";
	String firstName = "//input[@name=\"firstName\"]";
	String lastName = "//input[@name=\"lastName\"]";
	String empId = "//label[contains(text(),'Employee Id')]/parent::div/following"
			+ "-sibling::div/input";
	String empIdErrorMsg = "//span[contains(@class,'error')]";
	String submitBtn = "//button[@type=\"submit\"]";
	String firstNameTxtBx = "//div[@class=\"oxd-form-row\"]/div/div[1]/descendant::input";
	
}
