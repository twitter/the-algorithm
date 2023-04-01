Feature: Favorite Tags
  In order to browse more efficiently
  As an archive user
  I should be able to list my favorite tags on my homepage

  Scenario: A user can add a canonical tag to their favorite tags
  Given a canonical fandom "Dallas (TV 2012)"
  When I am logged in as "bourbon" with password "andbranch"
    And I go to the homepage
  Then I should see "Find your favorites"
    And I should see "Browse fandoms by media or favorite up to 20 tags to have them listed here!"
  When I view the "Dallas (TV 2012)" works index
  Then I should see a "Favorite Tag" button
  When I press "Favorite Tag"
  Then I should see "You have successfully added Dallas (TV 2012) to your favorite tags."
  When I go to the homepage
  Then I should see "Dallas (TV 2012)"

  Scenario: A user can remove a tag from their favorite tags
  Given a canonical relationship "John Ross Ewing/Elena Ramos"
  When I am logged in as "bourbon" with password "andbranch"
    And I add "John Ross Ewing/Elena Ramos" to my favorite tags
  When I view the "John Ross Ewing/Elena Ramos" works index
  Then I should see a "Unfavorite Tag" button
  When I press "Unfavorite Tag"
  Then I should see "You have successfully removed John Ross Ewing/Elena Ramos from your favorite tags."
  When I go to the homepage
  Then I should not see "John Ross Ewing/Elena Ramos"
    And I should see "Browse fandoms by media or favorite up to 20 tags to have them listed here!"

  Scenario: A tag that is decanonized should be removed from users' favorite tags
  Given a canonical character "Rebecca Sutter"
  When I am logged in as "bourbon" with password "andbranch"
    And I add "Rebecca Sutter" to my favorite tags
  When I go to the homepage
  Then I should see "Rebecca Sutter"
  When the tag "Rebecca Sutter" is decanonized
    And I go to the homepage
  Then I should not see "Rebecca Sutter"
