Feature: Gift Exchange Signup Summary

  Scenario: Updating a live summary
    Given signup summaries are always visible
      And all signup summaries are live
      And I have created the gift exchange "Exchange"
      And I open signups for "Exchange"

    When I am logged in as "testuser1"
      And I sign up for "Exchange" with combination C
      And I view the sign-up summary for "Exchange"
    Then I should see "Stargate SG-1 1 1"

    When I am logged in as "testuser2"
      And I sign up for "Exchange" with combination D
      And I view the sign-up summary for "Exchange"
    Then I should see "Stargate SG-1 1 1"
      And I should see "Stargate Atlantis 1 1"

  Scenario: Updating a delayed summary
    Given signup summaries are always visible
      And all signup summaries are delayed
      And I have created the gift exchange "Exchange"
      And I open signups for "Exchange"

    When I am logged in as "testuser1"
      And I sign up for "Exchange" with combination C
      And I view the sign-up summary for "Exchange"
    Then I should see "Stargate SG-1 1 1"

    When I am logged in as "testuser2"
      And I sign up for "Exchange" with combination D
      And I view the sign-up summary for "Exchange"
    Then I should see "Stargate SG-1 1 1"
      But I should not see "Stargate Atlantis"

    When it is currently 61 minutes from now
      And I view the sign-up summary for "Exchange"
    Then I should see "Stargate SG-1 1 1"
      And I should see "Stargate Atlantis 1 1"
      And I jump in our Delorean and return to the present
