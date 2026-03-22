package com.skillio.stepdefinitions;

import static com.skillio.base.Keyword.launchUrl;
import static com.skillio.base.Keyword.openBrowser;

import org.testng.Assert;

import com.skillio.pages.LoginPage;
import com.skillio.utils.App;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

	@Given("The browser is launched, and the URL is opened")
	public void openBrowserAndLaunchUrl() {
		openBrowser(App.getBrowserName());
		launchUrl(App.getAppUrl("qa"));
	}
	
	@When("user enters invalid credentials")
	public void enterInvalidCredentials() {
		LoginPage login = new LoginPage();
		login.enterUserName("Admn");
		login.enterPassword("111");
		login.clickLoginBtn();
	}
	
	@Then("check if the error message appears")
	public void verifyLoginErrorMsg() {
		LoginPage login = new LoginPage();
		String expMsg="Invalid credentials";
		String actualErroMsg = login.getErroMessage();
		Assert.assertEquals(actualErroMsg, expMsg,"Error message is invalid: "+actualErroMsg);
	}

	@When("user enters username {string} and password {string} and clicks login")
	public void user_enters_username_and_password_and_clicks_login(String username, String password) {
		LoginPage login = new LoginPage();
		login.loginWithCredentials(username, password);
	}

	@Then("the user should be redirected to the dashboard and see the text {string}")
	public void the_user_should_be_redirected_to_the_dashboard_and_see_the_text(String expectedText) {
		LoginPage login = new LoginPage();
		String header = login.getDashboardHeaderText();
		Assert.assertTrue(header != null && header.contains(expectedText), "Expected dashboard text not found. Actual: " + header);
	}

	@Then("the user should see {string}")
	public void the_user_should_see(String expectedOutcome) {
		LoginPage login = new LoginPage();
		// Normalize expectedOutcome and verify appropriate page state or messages
		if ("Dashboard".equalsIgnoreCase(expectedOutcome)) {
			String header = login.getDashboardHeaderText();
			Assert.assertTrue(header != null && header.contains("Dashboard"), "Dashboard not visible. Actual header: " + header);
			return;
		}
		if ("Invalid credentials".equalsIgnoreCase(expectedOutcome)) {
			String msg = login.getErroMessage();
			Assert.assertEquals(msg, "Invalid credentials", "Invalid credentials message mismatch: " + msg);
			return;
		}
		if ("Username cannot be empty".equalsIgnoreCase(expectedOutcome)) {
			String userFieldMsg = login.getUsernameFieldError();
			Assert.assertTrue(userFieldMsg != null && !userFieldMsg.isEmpty(), "Expected username field validation message but found none.");
			return;
		}
		if ("Password cannot be empty".equalsIgnoreCase(expectedOutcome)) {
			String pwdFieldMsg = login.getPasswordFieldError();
			Assert.assertTrue(pwdFieldMsg != null && !pwdFieldMsg.isEmpty(), "Expected password field validation message but found none.");
			return;
		}
		// Fallback: check if the expectedOutcome appears anywhere on the page
		String pageText = "";
		try {
			pageText = com.skillio.base.Keyword.getDriver().getPageSource();
		} catch (Exception e) {
			// ignore
		}
		Assert.assertTrue(pageText.contains(expectedOutcome), "Expected outcome text not found on page: " + expectedOutcome);
	}

}