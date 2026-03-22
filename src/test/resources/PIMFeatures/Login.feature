Feature: Login scenarios for OrangeHRM
  This feature contains positive, negative and data-driven tests for the
  OrangeHRM demo login page: https://opensource-demo.orangehrmlive.com/web/index.php/auth/login
#Background: Open the login page
 #  Given The browser is launched, the URL is opened

  @smoke @positive
  Scenario: Successful login with valid credentials
    When user enters username "Admin" and password "admin123" and clicks login
    Then the user should be redirected to the dashboard and see the text "Dashboard"

  @negative
  Scenario: Verify error message appears when user enters invalid credentials
    When user enters invalid credentials
    Then check if the error message appears

  @data @login
  Scenario Outline: Data-driven login tests
    When user enters username "<username>" and password "<password>" and clicks login
    Then the user should see "<expectedOutcome>"

    Examples: Valid and invalid credential combinations
      | username | password  | expectedOutcome          |
      | Admin    | admin123  | Dashboard                |
      | wrong    | admin123  | Invalid credentials      |
      | Admin    | wrongpass | Invalid credentials      |
      |          |           | Username cannot be empty |
      | Admin    |          | Password cannot be empty |