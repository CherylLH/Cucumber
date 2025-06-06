Feature: Homepage

  Scenario: Can access the FAQs page from the homepage
    Given I am on the Makers homepage
    When I click the "FAQ" link
    Then I should be on the FAQs page

  Scenario Outline: Verify all of the links in the Makers homepage
    Given I am on Makers homepage
    When I click the "<link name>" navigation link
    Then the link will take me to "<Url>"
    Examples:
      | link name        | Url                   |
      | Code of Conduct  | https://makers.tech/  |
      | About Us         | https://makers.tech/  |
