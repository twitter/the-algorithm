@users
Feature: First login help banner

  Scenario: New user sees the banner
  
  Given I am logged in as "newname"
  When I am on newname's user page
  Then I should see the first login banner
    And I should see "For more guidance on using the site, check out our FAQ! If you need technical support, you can contact us through our Support and Feedback form. If you experience harassment or have specific questions about the Terms of Service, you can contact our Policy & Abuse team."
  When I follow "check out our FAQ"
  Then I should be on the faq page
  When I am on newname's user page
    And I follow "contact us through our Support and Feedback form"
  Then I should be on the support page
  When I am on newname's user page
    And I follow "Terms of Service"
  Then I should be on the tos page
  When I am on newname's user page
    And I follow "contact our Policy & Abuse team"
  Then I should see "Report Abuse"

  Scenario: Popup details can be viewed
  
  Given I am logged in as "newname"
  When I am on newname's user page
  When I follow "Learn some tips and tricks"
  Then I should see the first login popup

  Scenario: Turn off first login help banner directly

  Given I am logged in as "newname2"
  When I am on newname2's user page
  When I press "Dismiss permanently"
  Then I should not see the first login banner
  
  Scenario: Banner stays off after logout and login if turned off directly
  
  Given I am logged in as "newname2"
  When I am on newname2's user page
  When I press "Dismiss permanently"
  When I am logged out
    And I am logged in as "newname2"
  Then I should not see the first login banner
  When I am on newname2's user page
  Then I should not see the first login banner
  
  Scenario: Hide banner using X
  
  Given I am logged in as "newname2"
  When I am on newname2's user page
  # Note this is "&times;" and not a letter "x"
  When I follow "×" within "div#main"
  
  Scenario: Banner comes back if turned off using X
  
  Given I am logged in as "newname2"
  When I am on newname2's user page
  # Note this is "&times;" and not a letter "x"
  When I follow "×" within "div#main"
  When I am logged out
    And I am logged in as "newname2"
    And I am on my user page
  Then I should see the first login banner
