@admin @wrangling_guidelines
Feature: Wrangling Guidelines
  In order to help people understand the wrangling system
  As an admin
  I want to be able to maintain wrangling guidelines

  Scenario: Post a Wrangling Guideline

  Given I am logged in as an admin
    And I am on the wrangling guidelines page
  And I follow "New Wrangling Guideline"
    And I fill in "Guideline text" with "This series of documents (Wrangling Guidelines) are intended to help tag wranglers remain consistent as they go about the business of wrangling tags by providing a set of formatting guidelines."
    And I fill in "Title" with "Intro and General Concepts"
  When I press "Post"
  Then I should see "Wrangling Guideline was successfully created"
  When I go to the wrangling_guidelines page
    And I follow "Intro and General Concepts"
  Then I should see "This series of documents (Wrangling Guidelines) are intended to help tag wranglers remain consistent as they go about the business of wrangling tags by providing a set of formatting guidelines." within ".userstuff"

  Scenario: Edit Wrangling Guideline

  Given I have posted a Wrangling Guideline
    And I am on the wrangling guidelines page
  When I follow "Edit"
    And I fill in "Guideline text" with "These guidelines are an in-progress affair, subject to change."
  When I press "Post"
  Then I should see "Wrangling Guideline was successfully updated"
    And I should see "These guidelines are an in-progress affair, subject to change."

  Scenario: Reorder Wrangling Guidelines

  Given I am logged in as an admin
    And 3 Wrangling Guidelines exist
  When I go to the Wrangling Guidelines reorder page
    And I fill in "wrangling_guidelines_1" with "3"
    And I fill in "wrangling_guidelines_2" with "1"
    And I fill in "wrangling_guidelines_3" with "2"
  When I press "Update Positions"
  Then I should see "Wrangling Guidelines order was successfully updated"
  When I follow "Reorder Wrangling Guidelines"
  Then I should see "1. The 2 Wrangling Guideline"
    And I should see "2. The 3 Wrangling Guideline"
    And I should see "3. The 1 Wrangling Guideline"

  Scenario: Delete Wrangling Guideline

  Given I am logged in as an admin
    And I have posted a Wrangling Guideline titled "Relationship Tags"
  When I go to the Wrangling Guidelines page
    And I follow "Delete"
  Then I should see "Wrangling Guideline was successfully deleted"
    And I should not see "Relationship Tags"
