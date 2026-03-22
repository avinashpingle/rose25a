package com.skillio.LoginTests;

import static com.skillio.base.Keyword.*;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.skillio.base.Keyword;
import com.skillio.utils.Locator;
import com.skillio.utils.WaitFor;

/**
 * Negative login test using existing keyword framework.
 */
public class InvalidLoginTest{

   // @Test
    public void invalidLoginDisplaysErrorMessage() {
        // 1. Browser is launched and URL is opened by TestBase.setUp
        // 2. Enter invalid username
        enterText("xpath", Locator.username, "invalid_user");
        // 3. Enter invalid password
        enterText("xpath", Locator.password, "bad_password");
        // 4. Click Sign In
        clickOn("xpath", Locator.signInBtn);
        // 5. Wait for and assert the error message shown on login screen
        // OrangeHRM typically displays an alert with text like "Invalid credentials".
        By loginError = By.xpath("//*[contains(text(),'Invalid') or contains(text(),'invalid') or contains(text(),'Credentials') or contains(text(),'credentials')]");
        WaitFor.elementToBeVisible(loginError);
        String msg = Keyword.getDriver().findElement(loginError).getText();
        // Assert the message contains the word Invalid or credentials
        Assert.assertTrue(msg.toLowerCase().contains("invalid") || msg.toLowerCase().contains("credential"), "Expected login error message but got: " + msg);
    }
}
