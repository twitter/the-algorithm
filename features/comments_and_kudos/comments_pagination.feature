@comments
Feature: Comments should be paginated

Scenario: One-chapter work with many comments
  Given the work with 34 comments setup
  When I view the work "Blabla"
    And I follow "Comments"
  Then I should see "2" within ".pagination"
  When I follow "Next" within ".pagination"
  Then I should see "1" within ".pagination"

Scenario: Multi-chapter work with many comments per chapter

  Given the chaptered work with 6 chapters with 50 comments "Epic WIP"
  When I view the work "Epic WIP"
  Then I should see "Comments (50)"
  When I follow "Comments"
  Then I should see "2" within ".pagination"
    And I should not see "3" within ".pagination"
  When I follow "Next" within ".pagination"
  Then I should see "1" within ".pagination"
  # All those comments were on the first chapter. Now put some more on
  When I am logged in
    And I view the work "Epic WIP"
    And I view the 3rd chapter
    And I post a comment "The third chapter is especially good."
    And I post a comment "I loved the cliffhanger in chapter 3"
  Then I should see "Comments (2)"
  # Going to the work shows first chapter and only those comments
  When I view the work "Epic WIP"
  Then I should see "Comments (50)"
  # Entire work shows all comments
  When I follow "Entire Work"
  Then I should see "Comments (52)"
  When I follow "Comments (52)"
  Then I should see "3" within ".pagination"
