package com.skillio.PIMMenuTests;

import static com.skillio.base.Keyword.clear;
import static com.skillio.base.Keyword.clickOn;
import static com.skillio.base.Keyword.enterText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.skillio.base.Keyword;
import com.skillio.base.Hooks;
import com.skillio.pages.HomePage;
import com.skillio.pages.LoginPage;
import com.skillio.utils.Locator;
import com.skillio.utils.WaitFor;

/**
 * This class contains test cases related to PIM menu of OrangeHRM application
 * The app url is
 * https://opensource-demo.orangehrmlive.com/web/index.php/auth/login
 */
public class PIMMenuTests  {

	//@Test
	public void verifyIfUserIsCreatedAndPresentInPIMMenu() throws InterruptedException {
		// Login
		Thread.sleep(5000);
		Keyword.getDriver().findElement(By.xpath("//input[@name=\"username\"]")).sendKeys("Admin");
		System.out.println("Entered username");
		Keyword.getDriver().findElement(By.xpath("//input[@name=\"password\"]")).sendKeys("admin123");
		Keyword.getDriver().findElement(By.xpath("//button[@type=\"submit\"]")).click();
		Thread.sleep(3000);
		Keyword.getDriver().findElement(By.xpath("//ul[@class=\"oxd-main-menu\"]/li[2]")).click();
		Thread.sleep(4000);
		Keyword.getDriver().findElement(By.xpath("//nav[@class=\"oxd-topbar-body-nav\"]/ul/li[3]")).click();
		Thread.sleep(3000);
		Random random = new Random();
		String firstname = 1000 + random.nextInt(9000) + "";
		String lastname = 1000 + random.nextInt(9000) + "";
		Keyword.getDriver().findElement(By.xpath("//input[@name=\"firstName\"]")).sendKeys("Test_" + firstname);
		Keyword.getDriver().findElement(By.xpath("//input[@name=\"lastName\"]")).sendKeys("User_" + lastname);

		List<WebElement> errorMsg = new ArrayList<WebElement>();
		String empId = 1000 + random.nextInt(9000) + "";
		Keyword.getDriver().findElement(By.xpath("//label[contains(text(),'Employee Id')]/parent::div/following-sibling::div/input"))
				.clear();

		Keyword.getDriver().findElement(By.xpath("//label[contains(text(),'Employee Id')]/parent::div/following-sibling::div/input"))
				.sendKeys(empId);
		errorMsg = Keyword.getDriver().findElements(By.xpath("//span[contains(@class,'error')]"));
		System.out.println("Generated emp id: " + empId);

		while (!errorMsg.isEmpty()) {
			empId = 1000 + random.nextInt(9000) + "";
			System.out.println("Generated emp id again: " + empId);
			Keyword.getDriver().findElement(
					By.xpath("//label[contains(text(),'Employee Id')]/parent::div/following-sibling::div/input"))
					.clear();
			Keyword.getDriver().findElement(
					By.xpath("//label[contains(text(),'Employee Id')]/parent::div/following-sibling::div/input"))
					.sendKeys(empId);
			Thread.sleep(2000);
			errorMsg.clear();
			errorMsg = Keyword.getDriver().findElements(By.xpath("//span[contains(@class,'error')]"));
		}

		Keyword.getDriver().findElement(By.xpath("//button[@type=\"submit\"]")).click();
		Thread.sleep(3000);
		Keyword.getDriver().findElement(By.xpath("//ul[@class=\"oxd-main-menu\"]/li[2]")).click();
		Thread.sleep(3000);
		Keyword.getDriver().findElement(By.xpath("//div[@class=\"oxd-form-row\"]/div/div[1]/descendant::input"))
				.sendKeys("Test_" + firstname);
		Keyword.getDriver().findElement(By.xpath("//button[@type=\"submit\"]")).click();
		Thread.sleep(5000);
		String firstName = Keyword.getDriver()
				.findElement(By.xpath("//div[@class=\"oxd-table-card\"]/descendant::div[@role='cell'][3]/div"))
				.getText();
		String lastName = Keyword.getDriver()
				.findElement(By.xpath("//div[@class=\"oxd-table-card\"]/descendant::div[@role='cell'][4]/div"))
				.getText();

		SoftAssert softly = new SoftAssert();
		softly.assertTrue(firstName.equalsIgnoreCase("Test_" + firstname));
		softly.assertTrue(lastName.equalsIgnoreCase("User_" + lastname));
		softly.assertAll();
	}

	/**
	 * This test case is same as above but we are using keywords to perform the actions on the web elements
	 * @throws IOException 
	 */
//	@Test
	public void verifyIfUserIsCreatedAndPresentInPIMMenuUsiungKeywords() throws IOException {
		By username = By.xpath(Locator.username);
		WaitFor.elementToBeVisible(username);
		enterText("xpath", Locator.username, "Admin");
		enterText("xpath", Locator.password, "admin123");
		clickOn("xpath",Locator.signInBtn);
		WaitFor.elementToBeVisible(By.xpath(Locator.pimMenu));
		clickOn("xpath",Locator.pimMenu);
		WaitFor.elementToBeVisible(By.xpath(Locator.addEmployeeBtn));
		clickOn("xpath",Locator.addEmployeeBtn);
		Random random = new Random();
		String firstname = 1000 + random.nextInt(9000) + "";
		String lastname = 1000 + random.nextInt(9000) + "";
		enterText("xpath", Locator.firstName, "Test_"+firstname);
		enterText("xpath", Locator.lastName, "User_"+lastname);
		 List<WebElement> errorMsg = new ArrayList<WebElement>();
		 String empId = 1000 + random.nextInt(9000) + "";
		 //Convert below line to use keyword instead of driver.findElement, change Keyword class to accomodate this change
		 clear("xpath", Locator.empId);
		 enterText("xpath", Locator.empId, empId);
		 errorMsg = Keyword.getDriver().findElements(By.xpath(Locator.empIdErrorMsg));
		 System.out.println("Generated emp id: "+empId);
		 while (!errorMsg.isEmpty()) {
			 empId = 1000 + random.nextInt(9000) + "";
			 System.out.println("Generated emp id again: "+empId);
			 clear("xpath", Locator.empId);
			 enterText("xpath", Locator.empId, empId);
			 WaitFor.elementToBeVisible(By.xpath(Locator.empIdErrorMsg));
			 errorMsg.clear();
			 errorMsg = Keyword.getDriver().findElements(By.xpath(Locator.empIdErrorMsg));
		 	
		 }
		 clickOn("xpath",Locator.submitBtn);
		 WaitFor.elementToBeVisible(By.xpath(Locator.pimMenu));
		 clickOn("xpath",Locator.pimMenu);
		 WaitFor.elementToBeVisible(By.xpath(Locator.firstNameTxtBx));
		 enterText("xpath", Locator.firstNameTxtBx, "Test_"+firstname);
		 clickOn("xpath",Locator.submitBtn);
		 WaitFor.elementToBeVisible(By.xpath("//div[@class=\"oxd-table-card\""
		 			+ "]/descendant::div[@role='cell'][3]/div"));
		 String firstName = Keyword.getDriver()
		 		.findElement(By.xpath("//div[@class=\"oxd-table-card\"]/descendant"
		 			+ "div[@role='cell'][3]/div"))
		 		.getText();
		 String lastName = Keyword.getDriver().findElement(By.xpath("//div[@class=\"oxd-tablecard\"]/descendant::div[@role='cell'][4]/div"))
		 		.getText();
		 SoftAssert softly = new SoftAssert();
		 softly.assertTrue(firstName.equalsIgnoreCase("Test_"+firstname));
		 softly.assertTrue(lastName.equalsIgnoreCase("User_"+lastname));
		 softly.assertAll();
	 	 
	}
	
	//@Test
	public void verifyIfUserIsCreatedAndPresentInPIMMenuUsiungPOM() {
		By username = By.xpath(Locator.username);
		WaitFor.elementToBeVisible(username);
		LoginPage loginPage= new LoginPage();
		loginPage.enterUserName("Admin");
		loginPage.enterPassword("admin123");
		loginPage.clickLoginBtn();
		HomePage homePage = new HomePage();
		homePage.waitForPIMMenuToBeVisible();
		homePage.clickPIMMenu();
	}
}
