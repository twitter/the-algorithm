Feature: Kudos Pagination

  Background:
    Given the maximum number of kudos to show is 3

  Scenario: Viewing one kudo
    Given a work "Popular (1)" with 1 kudo
    When I view the work "Popular (1)"
    Then I should see "fan1 left kudos on this work!"

  Scenario: Viewing two kudos
    Given a work "Popular (2)" with 2 kudos
    When I view the work "Popular (2)"
    Then I should see "fan2 and fan1 left kudos on this work!"

  Scenario: Viewing three kudos
    Given a work "Popular (3)" with 3 kudos
    When I view the work "Popular (3)"
    Then I should see "fan3, fan2, and fan1 left kudos on this work!"

  @javascript
  Scenario: Multiple pages of kudos with 1 kudo on the last page
    Given a work "Popular (4)" with 4 kudos
    When I view the work "Popular (4)"
    Then I should see "fan4, fan3, fan2, and 1 more user left kudos on this work!"
    When I follow "1 more user"
    Then I should see "fan4, fan3, fan2, and fan1 left kudos on this work!"

  @javascript
  Scenario: Multiple pages of kudos with 2 kudos on the last page
    Given a work "Popular (5)" with 5 kudos
    When I view the work "Popular (5)"
    Then I should see "fan5, fan4, fan3, and 2 more users left kudos on this work!"
    When I follow "2 more users"
    Then I should see "fan5, fan4, fan3, fan2, and fan1 left kudos on this work!"

  @javascript
  Scenario: Multiple pages of kudos with 3 kudos on the last page
    Given a work "Popular (6)" with 6 kudos
    When I view the work "Popular (6)"
    Then I should see "fan6, fan5, fan4, and 3 more users left kudos on this work!"
    When I follow "3 more users"
    Then I should see "fan6, fan5, fan4, fan3, fan2, and fan1 left kudos on this work!"

  @javascript
  Scenario: Three pages of kudos
    Given a work "Popular (9)" with 9 kudos
    When I view the work "Popular (9)"
    Then I should see "fan9, fan8, fan7, and 6 more users left kudos on this work!"
    When I follow "6 more users"
    Then I should see "fan9, fan8, fan7, fan6, fan5, fan4, and 3 more users left kudos on this work!"
    When I follow "3 more users"
    Then I should see "fan9, fan8, fan7, fan6, fan5, fan4, fan3, fan2, and fan1 left kudos on this work!"

  Scenario: Three pages of kudos without javascript
    Given a work "Popular (9)" with 9 kudos
    When I view the work "Popular (9)"
    Then I should see "fan9, fan8, fan7, and 6 more users left kudos on this work!"
    When I follow "6 more users"
    Then I should see "1 - 3 of 9 Users Who Left Kudos on Popular (9)"
      And I should see "fan9, fan8, and fan7 left kudos on this work!"
    When I follow "Next"
    Then I should see "4 - 6 of 9 Users Who Left Kudos on Popular (9)"
      And I should see "fan6, fan5, and fan4 left kudos on this work!"
    When I follow "Next"
    Then I should see "7 - 9 of 9 Users Who Left Kudos on Popular (9)"
      And I should see "fan3, fan2, and fan1 left kudos on this work!"
