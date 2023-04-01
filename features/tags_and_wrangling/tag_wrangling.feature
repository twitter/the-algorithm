@users @tag_wrangling
Feature: Tag wrangling

  Scenario: Admin can create a tag wrangler using the interface

    Given I have loaded the "roles" fixture
    When I am logged in as "dizmo"
    Then I should not see "Tag Wrangling" within "#header"
    When I am logged in as a "tag_wrangling" admin
      And I go to the manage users page
      And I fill in "Name" with "dizmo"
      And I press "Find"
    Then I should see "dizmo" within "#admin_users_table"
    # admin making user tag wrangler
    When I check "user_roles_1"
      And I press "Update"
    Then I should see "User was successfully updated"
    # accessing wrangling pages
    When I am logged in as "dizmo"
      And I follow "Tag Wrangling" within "#header"
    Then I should see "Wrangling Home"
    # no access otherwise
    When I log out
    Then I should see "Sorry, you don't have permission"

  Scenario Outline: Tag wrangler navigation/sidebar
    Given the tag wrangling setup
      And I am logged in as a tag wrangler
    When I go to my wrangling page
    Then I should see "Wrangling Home"
      And I should see "Fandoms by media (3)"
      And I should see "Characters by fandom (2)"
      And I should see "Relationships by fandom (1)"
    When I follow <link_text>
    Then I should see <heading>

    Examples:
      | link_text                     | heading                            |
      | "Wranglers"                   | "Tag Wrangling Assignments"        |
      | "Wrangling Tools"             | "Tag Wrangling"                    |
      | "Characters by fandom (2)"    | "Mass Wrangle New/Unwrangled Tags" |
      | "Relationships by fandom (1)" | "Mass Wrangle New/Unwrangled Tags" |
      | "Fandoms by media (3)"        | "Mass Wrangle New/Unwrangled Tags" |

  Scenario: Edit tag page
    Given the tag wrangling setup
      And I am logged in as a tag wrangler
    When I go to the "Daniel Jackson" tag page
      And I follow "Edit" within ".header"
    Then I should see "Edit Daniel Jackson Tag"
      And I should see "Name"
      And I should see "Category"
      And I should see "Canonical"
      And I should see "Synonym"
      And I should see "Unwrangleable"
      And I should see "Fandom"
      And I should see "Meta"

  Scenario: Making a fandom canonical and assigning media to it
    Given the tag wrangling setup
      And I am logged in as a tag wrangler
    When I go to the "Stargate SG-1" tag edit page
    When I check "tag_canonical"
      And I fill in "tag_media_string" with "TV Shows"
      And I press "Save changes"
    Then I should see "Tag was updated"
      And the "Stargate SG-1" tag should be canonical

  Scenario: Assign wrangler to a fandom
    Given the tag wrangling setup
      And I have a canonical "TV Shows" fandom tag named "Stargate SG-1"
      And I am logged in as a tag wrangler
    When I go to my wrangling page
      And I follow "Wranglers"
      And I fill in "tag_fandom_string" with "Stargate SG-1"
      And I press "Assign"
    Then "Stargate SG-1" should be assigned to the wrangler "wrangler"
    When I follow "Wrangling Home"
    Then I should see "Stargate SG-1"
    When I follow "Wranglers"
    Then I should see "Stargate SG-1"
      And I should see "wrangler" within "ul.wranglers"

  Scenario: Making a character canonical and assiging it to a fandom
    Given the tag wrangling setup
      And I have a canonical "TV Shows" fandom tag named "Stargate SG-1"
      And I am logged in as a tag wrangler
    When I go to the "Daniel Jackson" tag edit page
      And I fill in "Fandoms" with "Stargate SG-1"
      And I check "tag_canonical"
      And I press "Save changes"
    Then I should see "Tag was updated"
      And the "Daniel Jackson" tag should be canonical
      And the "Daniel Jackson" tag should be in the "Stargate SG-1" fandom

  Scenario: Assigning a fandom to a non-canonical character
    Given the tag wrangling setup
      And I have a canonical "TV Shows" fandom tag named "Stargate SG-1"
      And I am logged in as a tag wrangler
    When I go to the "Daniel Jackson" tag edit page
      And I fill in "Fandoms" with "Stargate SG-1"
      And I press "Save changes"
    Then I should see "Tag was updated"
      And the "Daniel Jackson" tag should not be canonical
      And the "Daniel Jackson" tag should be in the "Stargate SG-1" fandom

  Scenario: Merging canonical and non-canonical character tags
    Given the tag wrangling setup
      And I have a canonical "TV Shows" fandom tag named "Stargate SG-1"
      And I add the fandom "Stargate SG-1" to the character "Jack O'Neil"
      And I am logged in as a tag wrangler
    When I go to the "Jack O'Neil" tag edit page
      And I fill in "Synonym of" with "Jack O'Neill"
      And I press "Save changes"
      And I follow "Jack O'Neill"
    Then I should see "Stargate SG-1"
    When I view the tag "Stargate SG-1"
    Then I should see "Jack O'Neil"
      And I should see "Jack O'Neill"

  Scenario Outline: Creating new non-canonical tags
    Given I am logged in as a tag wrangler
      And I go to my wrangling page
    When I follow "New Tag"
      And I fill in "Name" with "MyNewTag"
      And I choose <type>
      And I press "Create Tag"
    Then I should see "Tag was successfully created"
      And the "MyNewTag" tag should be a <type> tag
      And the "MyNewTag" tag should not be canonical

    Examples:
      | type        |
      | "Fandom"    |
      | "Character" |

  Scenario Outline: Creating new canonical tags
    Given I am logged in as a tag wrangler
      And I go to my wrangling page
    When I follow "New Tag"
      And I fill in "Name" with "MyNewTag"
      And I choose <type>
      And I check "Canonical"
      And I press "Create Tag"
    Then I should see "Tag was successfully created"
      And the "MyNewTag" tag should be a <type> tag
      And the "MyNewTag" tag should be canonical

    Examples:
      | type        |
      | "Fandom"    |
      | "Character" |

  Scenario: Trying to assign a non-canonical fandom to a character
    Given the tag wrangling setup
      And a non-canonical fandom "Stargate Atlantis"
      And I have a canonical "TV Shows" fandom tag named "Stargate SG-1"
      And I am logged in as a tag wrangler
    When I go to the "Jack O'Neil" tag edit page
      And I fill in "Fandoms" with "Stargate Atlantis"
    And I press "Save changes"
    Then I should see "Cannot add association to 'Stargate Atlantis':"
      And I should see "Parent tag is not canonical."
      And I should not see "Stargate Atlantis" within "form"

  Scenario: Assigning a fandom to a non-canonical relationship tag
    Given the tag wrangling setup
      And I have a canonical "TV Shows" fandom tag named "Stargate Atlantis"
      And I am logged in as a tag wrangler
    When I go to the "JackDaniel" tag edit page
      And I fill in "Fandoms" with "Stargate Atlantis"
      And I press "Save changes"
    Then I should see "Tag was updated"
    When I follow "JackDaniel"
    Then I should see "Stargate Atlantis"

  Scenario: Creating a canonical merger and adding characters to a non-canonical relationship
    Given I have a canonical "TV Shows" fandom tag named "RWBY"
      And a canonical character "Blake Belladonna" in fandom "RWBY"
      And a canonical character "Yang Xiao Long" in fandom "RWBY"
      And a non-canonical relationship "Bumbleby"
      And I am logged in as a tag wrangler
    When I go to the "Bumbleby" tag edit page
      And I fill in "Synonym of" with "Blake/Yang"
      And I fill in "Fandoms" with "RWBY"
      And I fill in "Characters" with "Blake Belladonna, Yang Xiao Long"
      And I press "Save changes"
    Then I should see "Tag was updated"
      And I should see "RWBY"
      And I should see "Blake/Yang"
      And I should see "Blake Belladonna"
      And I should see "Yang Xiao Long"
      And the "Blake/Yang" tag should be canonical

  Scenario: Check sidebar links and pages for wrangling within a fandom
    Given I have a canonical "TV Shows" fandom tag named "Stargate SG-1"
      And a canonical character "Samantha Carter" in fandom "Stargate SG-1"
      And a canonical character "Teal'c" in fandom "Stargate SG-1"
      And a synonym "Tealc" of the tag "Teal'c"
      And the tag wrangler "wrangler" with password "password" is wrangler of "Stargate SG-1"
      And I post the work "Test Work" with fandom "Stargate SG-1" with character "Janet Fraiser" with second character "Apophis"
    When I go to the "Apophis" tag edit page
      And I check "Unwrangleable"
      And I fill in "Fandoms" with "Stargate SG-1"
      And I press "Save changes"
    Then I should see "Tag was updated"
      And the "Apophis" tag should be unwrangleable
    When I am on my wrangling page
      And I follow "Stargate SG-1"
    Then I should see "Wrangle Tags for Stargate SG-1"
    When I follow "Characters (4)"
    Then I should see "Wrangle Tags for Stargate SG-1"
      And I should see "Showing All Character Tags"
      And I should see "Apophis"
      And I should see "Samantha Carter"
      And I should see "Teal'c"
      And I should see "Tealc"
    When I follow "Canonical"
    Then I should see "Showing Canonical Character Tags"
      And I should see "Samantha Carter"
      And I should see "Teal'c"
      But I should not see "Tealc"
    When I follow "Synonymous"
    Then I should see "Showing Synonymous Character Tags"
      And I should see "Teal'c"
      And I should see "Tealc" within "tbody th"
      And I should not see "Teal'c" within "tbody td"
      And I should not see "Samantha Carter"
    When I follow "Unwrangleable"
    Then I should see "Showing Unwrangleable Character Tags"
      And I should see "Apophis"
      And I should not see "Samantha Carter"
    When I follow "Unwrangled"
    Then I should see "Showing Unwrangled Character Tags"
      And I should see "Janet Fraiser"
      And I should not see "Samantha Carter"
    When I follow "Relationships (0)"
    Then I should see "Wrangle Tags for Stargate SG-1"
      And I should see "Showing All Relationship Tags"
    When I follow "Freeforms (0)"
    Then I should see "Wrangle Tags for Stargate SG-1"
      And I should see "Showing All Freeform Tags"
    When I follow "SubTags (0)"
    Then I should see "Wrangle Tags for Stargate SG-1"
      And I should see "Showing All Sub Tag Tags"
    When I follow "Mergers (0)"
    Then I should see "Wrangle Tags for Stargate SG-1"
      And I should see "Showing All Merger Tags"

  Scenario: Wrangler has option to troubleshoot a work

    Given the work "Indexing Issues"
      And I am logged in as a tag wrangler
     When I view the work "Indexing Issues"
     Then I should see "Troubleshoot"

  @javascript
  Scenario: AO3-1698 Sign up for a fandom from the edit fandom page,
    then from editing a child tag of a fandom

    Given a canonical fandom "'Allo 'Allo"
      And a canonical fandom "From Eroica with Love"
      And a canonical fandom "Cabin Pressure"
      And a noncanonical relationship "Dorian/Martin"

    # I want to sign up from the edit page of an unassigned fandom
    When I am logged in as a tag wrangler
      And I edit the tag "'Allo 'Allo"
    Then I should see "Sign Up"
    When I follow "Sign Up"
    Then I should see "Assign fandoms to yourself"
      And I should see "'Allo 'Allo" within ".autocomplete .added"
    When I press "Assign"
    Then I should see "Wranglers were successfully assigned"
    When I edit the tag "'Allo 'Allo"
    Then I should not see "Sign Up"
      And I should see the tag wrangler listed as an editor of the tag

    # I want to sign up from the edit page of a relationship that belongs to two unassigned fandoms
    When I edit the tag "Dorian/Martin"
    Then I should not see "Sign Up"
    When I fill in "Fandoms" with "From Eroica with Love, Cabin Pressure"
      And I press "Save changes"
    Then I should see "Tag was updated"
    When I follow "Sign Up"
      And I choose "Cabin Pressure" from the "Enter as many fandoms as you like." autocomplete
      And I choose "From Eroica with Love" from the "Enter as many fandoms as you like." autocomplete
      And I press "Assign"
    Then I should see "Wranglers were successfully assigned"
    When I edit the tag "From Eroica with Love"
    Then I should not see "Sign Up"
      And I should see the tag wrangler listed as an editor of the tag
    When I edit the tag "Cabin Pressure"
    Then I should not see "Sign Up"
      And I should see the tag wrangler listed as an editor of the tag

  Scenario: A user can not see the troubleshoot button on a tag page

    Given a canonical fandom "Cowboy Bebop"
      And I am logged in as a random user
    When I view the tag "Cowboy Bebop"
    Then I should not see "Troubleshoot"

  Scenario: A tag wrangler can see the troubleshoot button on a tag page

    Given a canonical fandom "Cowboy Bebop"
      And the tag wrangler "lain" with password "lainnial" is wrangler of "Cowboy Bebop"
    When I view the tag "Cowboy Bebop"
    Then I should see "Troubleshoot"

  Scenario: An admin can see the troubleshoot button on a tag page

    Given a canonical fandom "Cowboy Bebop"
      And I am logged in as an admin
    When I view the tag "Cowboy Bebop"
    Then I should see "Troubleshoot"

  Scenario: Can simultaneously add a grandparent metatag as a direct metatag and remove the parent metatag
    Given a canonical fandom "Grandparent"
      And a canonical fandom "Parent"
      And a canonical fandom "Child"
      And "Grandparent" is a metatag of the fandom "Parent"
      And "Parent" is a metatag of the fandom "Child"
      And I am logged in as a random user
      And I post the work "Oldest" with fandom "Grandparent"
      And I post the work "Middle" with fandom "Parent"
      And I post the work "Youngest" with fandom "Child"
      And I am logged in as a tag wrangler

    When I edit the tag "Child"
      And I check the 1st checkbox with id matching "MetaTag"
      And I fill in "tag_meta_tag_string" with "Grandparent"
      And I press "Save changes"
    Then I should see "Tag was updated"
      And I should see "Grandparent" within "#parent_MetaTag_associations_to_remove_checkboxes"
      But I should not see "Parent" within "#parent_MetaTag_associations_to_remove_checkboxes"

    When I view the tag "Child"
    Then I should see "Grandparent" within ".meta"
      But I should not see "Parent" within ".meta"

    When I go to the works tagged "Grandparent"
    Then I should see "Oldest"
      And I should see "Middle"
      And I should see "Youngest"

    When I go to the works tagged "Parent"
    Then I should see "Middle"
      But I should not see "Oldest"
      And I should not see "Youngest"

    When I go to the works tagged "Child"
    Then I should see "Youngest"
      But I should not see "Oldest"
      And I should not see "Middle"
