Feature: Browsing from a non-standard user agent
As a Playstation user
In order to browse the AO3
I want to be able to browse from my PSP

Scenario: user agent requests a weird header

  Given I use a PSP browser
  When I make a request for "/works"
  Then the response should be "200"
