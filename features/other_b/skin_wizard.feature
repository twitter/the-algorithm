@skins
Feature: Skin wizard

  Scenario: User should be able to toggle between the wizard and the form
  Given I am logged in
  When I go to the new skin page
  Then I should see "CSS" within "form#new_skin"
  When I follow "Use Wizard"
  Then I should see "Site Skin Wizard"
    And I should not see "CSS" within "form"
  When I follow "Write Custom CSS"
  Then I should see "Create New Skin"
    And I should see "CSS"

  Scenario: Users should be able to create and use a wizard skin to adjust work margins,
  and they should be able to edit the skin while they are using it
  Given I am logged in as "skinner"
    And I am on the new wizard skin page
  When I fill in "Title" with "Wide margins"
    And I fill in "Description" with "Layout skin"
    And I fill in "Work margin width" with "text"
    And I submit
  Then I should see a save error message
    And I should see "Margin is not a number"
  When I fill in "Work margin width" with "5"
    And I submit
  Then I should see "Skin was successfully created"
    And I should see "Work margin width: 5%"
  When I am on skinner's preferences page
    And I select "Wide margins" from "preference_skin_id"
    And I submit
  Then I should see "Your preferences were successfully updated."
    And I should see "margin: auto 5%; max-width: 100%" in the page style
    # Make sure that the creation/update cache keys are different:
    And I wait 1 second
  When I edit the skin "Wide margins" with the wizard
    And I fill in "Work margin width" with "4.5"
    And I submit
  # TODO: Think about whether rounding to 4 is actually the right behaviour or not
  Then I should see an update confirmation message
    And I should see "Work margin width: 4%"
    And I should not see "Work margin width: 4.5%"
    And I should see "margin: auto 4%;" in the page style
  When I am on skinner's preferences page
  Then "Wide margins" should be selected within "preference_skin_id"

  Scenario: Users should be able to create and use a wizard skin with multiple wizard
  settings
  Given I am logged in as "skinner"
    And I am on the new wizard skin page
  When I fill in "Title" with "Many changes"
    And I fill in "Description" with "Layout skin"
    And I fill in "Font" with "'Times New Roman', Garamond, serif"
    And I fill in "Background color" with "#ccccff"
    And I fill in "Text color" with "red"
    And I fill in "Percent of browser font size" with "120"
    And I fill in "Vertical gap between paragraphs" with "5"
    And I submit
  Then I should see "Skin was successfully created"
    And I should see "Font: 'Times New Roman', Garamond, serif"
    And I should see "Background color: #ccccff"
    And I should see "Text color: red"
    And I should see "Percent of browser font size: 120%"
    And I should see "Vertical gap between paragraphs: 5.0em"
  When I press "Use"
  Then I should see "Your preferences were successfully updated."
    And I should see "background: #ccccff;" in the page style
    And I should see "color: red;" in the page style
    And I should see "font-family: 'Times New Roman', Garamond, serif;" in the page style
    And I should see "font-size: 120%;" in the page style
    And I should see "margin: 5.0em auto;" in the page style
  When I am on skinner's preferences page
  Then "Many changes" should be selected within "preference_skin_id"

  Scenario: Users should be able to adjust their wizard skin by adding custom CSS
  Given I am logged in as "skinner"
    And I create and use a skin to make the header pink
    # Make sure that the creation/update cache keys are different:
    And I wait 1 second
  When I edit my pink header skin to have a purple logo
  Then I should see an update confirmation message
    And I should see a pink header
    And I should see a purple logo

  Scenario: Change the accent color
  Given I am logged in as "skinner"
  When I create and use a skin to change the accent color
  Then I should see a different accent color
