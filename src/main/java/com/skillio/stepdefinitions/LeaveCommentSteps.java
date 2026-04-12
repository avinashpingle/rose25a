package com.skillio.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
//static import Assert from testng;
import static org.testng.Assert.*;

public class LeaveCommentSteps {

	WebDriver driver;
	WebDriverWait wait;

	// ─── Locators ────────────────────────────────────────────────────────────────

	// Login Page
	private final By USERNAME_INPUT = By.cssSelector("input[name='username']");
	private final By PASSWORD_INPUT = By.cssSelector("input[name='password']");
	private final By LOGIN_BUTTON = By.cssSelector("button[type='submit']");

	// Dashboard
	private final By DASHBOARD_HEADER = By.cssSelector(".oxd-topbar-header-title span");

	// Sidebar Navigation
	private final By LEAVE_MENU = By.xpath("//span[text()='Leave']");

	// Leave List Page
	private final By SEARCH_BUTTON = By.cssSelector("button.oxd-button--secondary");
	private final By RECORD_COUNT_LABEL = By.xpath("//*[contains(text(),'Record Found')]");

	// Leave List Table
	private final By THREE_DOTS_BUTTON = By.cssSelector(".oxd-table-cell-actions button");
	private final By ADD_COMMENT_OPTION = By.cssSelector(".oxd-table-dropdown-item:first-child");

	// Add Comment Dialog
	private final By DIALOG_TITLE = By.cssSelector(".oxd-dialog-container-default .oxd-text--h6");
	private final By COMMENT_TEXTAREA = By.cssSelector("textarea");
	private final By SAVE_BUTTON = By.cssSelector(".oxd-button--secondary.orangehrm-left-space");

	// Toast / Success Message
	private final By SUCCESS_TOAST = By.cssSelector(".oxd-toast--success .oxd-toast-content-text");

	// Comments Column in table (8th cell, index 7)
	private final By COMMENTS_CELL = By.xpath("(//div[contains(@class,'oxd-table-body')]"
			+ "//div[contains(@class,'oxd-table-row')]" + "//div[contains(@class,'oxd-table-cell')])[8]");

	// ─── Hooks ───────────────────────────────────────────────────────────────────

	@Before
	public void setUp() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		// options.addArguments("--headless"); // uncomment for headless execution
		driver = new ChromeDriver(options);
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}

	@After
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	// ─── Step Definitions ────────────────────────────────────────────────────────

	@Given("I open the Chrome browser")
	public void iOpenTheChromeBrowser() {
		// ChromeDriver is initialised in @Before hook
	}

	@Given("I navigate to the OrangeHRM demo site {string}")
	public void iNavigateToTheOrangeHRMDemoSite(String url) {
		driver.get(url);
		wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_INPUT));
	}

	@Given("I login with username {string} and password {string}")
	public void iLoginWithUsernameAndPassword(String username, String password) {
		driver.findElement(USERNAME_INPUT).sendKeys(username);
		driver.findElement(PASSWORD_INPUT).sendKeys(password);
		driver.findElement(LOGIN_BUTTON).click();
	}

	@And("I should be on the Dashboard page")
	public void iShouldBeOnTheDashboardPage() {
		wait.until(ExpectedConditions.urlContains("dashboard"));
		WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(DASHBOARD_HEADER));
		assertTrue(header.getText().contains("Dashboard"), "Should land on Dashboard");
	}

	@When("I click on {string} menu from the sidebar")
	public void iClickOnMenuFromTheSidebar(String menuName) {
		By menuLocator = By.xpath("//span[text()='" + menuName + "']");
		WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(menuLocator));
		menu.click();
	}

	@And("I click on the {string} button to load leave records")
	public void iClickOnTheButtonToLoadLeaveRecords(String buttonText) {
		// Reset any existing status filter, then search
		WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(SEARCH_BUTTON));
		searchBtn.click();
	}

	@Then("I should see {string} in the leave list")
	public void iShouldSeeInTheLeaveList(String recordText) {
		WebElement recordLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(RECORD_COUNT_LABEL));
		assertTrue(recordLabel.getText().contains(recordText.replace("(1) ", "")),
				"Expected record count label to contain: " + recordText);
	}

	@When("I click on the three dots action menu of the first record")
	public void iClickOnTheThreeDotsActionMenuOfTheFirstRecord() {
		WebElement threeDotsBtn = wait.until(ExpectedConditions.elementToBeClickable(THREE_DOTS_BUTTON));
		threeDotsBtn.click();
	}

	@And("I click on {string} from the dropdown")
	public void iClickOnFromTheDropdown(String option) {
		// Wait for dropdown to appear
		wait.until(ExpectedConditions.visibilityOfElementLocated(ADD_COMMENT_OPTION));

		// Use JS click to avoid interception issues
		WebElement addCommentOption = driver.findElement(ADD_COMMENT_OPTION);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", addCommentOption);
	}

	@Then("the {string} dialog should be displayed")
	public void theDialogShouldBeDisplayed(String dialogTitle) {
		WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(DIALOG_TITLE));
		assertEquals("Dialog title should match", dialogTitle, title.getText().trim());
	}

	@When("I enter the comment {string} in the text area")
	public void iEnterTheCommentInTheTextArea(String comment) {
		WebElement textarea = wait.until(ExpectedConditions.visibilityOfElementLocated(COMMENT_TEXTAREA));
		textarea.clear();
		textarea.sendKeys(comment);
	}

	@And("I click the {string} button")
	public void iClickTheButton(String buttonText) {
		// Use JS click to avoid dialog overlay interception
		WebElement saveBtn = wait.until(ExpectedConditions.presenceOfElementLocated(SAVE_BUTTON));
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].dispatchEvent(new MouseEvent('click', {bubbles:true, cancelable:true}));", saveBtn);
	}

	@Then("a success message {string} should be displayed")
    public void aSuccessMessageShouldBeDisplayed(String expectedMessage) {
        WebElement toast = wait.until(
            ExpectedConditions.visibilityOfElementLocated(SUCCESS_TOAST)
        );
        assertTrue(
            toast.getText().contains(expectedMessage),  "Success toast should contain: " + expectedMessage
        );
    }

	@And("the Comments column should display the exact text {string}")
	public void theCommentsColumnShouldDisplayTheExactText(String expectedComment) {
		// Wait for dialog to close and table to refresh
		wait.until(ExpectedConditions.invisibilityOfElementLocated(DIALOG_TITLE));

		WebElement commentsCell = wait.until(ExpectedConditions.visibilityOfElementLocated(COMMENTS_CELL));
		String actualComment = commentsCell.getText().trim();

		assertEquals("Comments column text should exactly match expected value", expectedComment, actualComment);
	}
}
