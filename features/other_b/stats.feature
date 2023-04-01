@stats
Feature: User statistics
  In order to know more about my works
  As a user
  The statistics page needs to show me information about my works

  Scenario: A user with no works should see a message
  Given I am logged in as "lurker"
  When I go to my stats page
  Then I should see "You currently have no works posted to the Archive. If you add some, you'll find information on this page about hits, kudos, comments, and bookmarks of your works."
    And I should see "Users can also see how many subscribers they have, but not the names of their subscribers or identifying information about other users who have viewed or downloaded their works."
  
  Scenario: Show only posted works on stats page
  
  Given I am logged in as "NUMB3RSfan"
    And I post the work "Don Solves Crime"
    And I post the work "Don Solves More Crime"
    And I set up the draft "Charlie Helps"
  When I am logged in as "reader"
    And I view the work "Don Solves Crime"
    And I am logged in as "NUMB3RSfan"
    And I go to my stats page
  Then "Don Solves Crime" should appear before "Don Solves More Crime"
    And I should not see "Charlie Helps"
  When I follow "Date"
  Then "Don Solves More Crime" should appear before "Don Solves Crime"
  When I follow "Date"
  Then "Don Solves Crime" should appear before "Don Solves More Crime"
