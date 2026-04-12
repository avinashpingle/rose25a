Feature: This feature file contains test scenarios related to the Leave module of the OrangeHRM application.

 Scenario: Add a comment to a leave request and verify it in the Comments column
    Given I login with username "Admin" and password "admin123"
    And I should be on the Dashboard page
    When I click on "Leave" menu from the sidebar
    And I click on the "Search" button to load leave records
    Then I should see "(1) Record Found" in the leave list
    When I click on the three dots action menu of the first record
    And I click on "Add Comment" from the dropdown
    Then the "Leave Request Comments" dialog should be displayed
    When I enter the comment "Your leave request is approved" in the text area
    And I click the "Save" button
    Then a success message "Successfully Saved" should be displayed
    And the Comments column should display the exact text "Your leave request is approved"