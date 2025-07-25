Feature: FAQs Page

  Scenario: FAQ search term displayed on results page
    Given I am on the Makers FAQ page
    When I search for "hybrid"
    Then the results header should mention "hybrid"

  Scenario Outline: Performing an FAQ search for different character types
    Given I am on the Makers FAQ page
    When I search for "<search_term>"
    Then the results header should mention '<text>'
    Examples:
      | search_term | text                    |
      | 17%         | Results for "17%"       |
      | 42%         | No results for "42%"    |
      | cliché      | No results for "cliché" |
      | 😄          | No results for "😄"     |

  Scenario Outline: performs a search for a two-word search term
    Given I am on the Makers FAQ page
    When I search for "<search_term>"
    Then the results header should mention '<text>'
    Then the results header should mention '<text>'
    And the term '<search_term>' should appear in the URL
    Examples:
      | search_term    | text                         |
      | remote pairing | Results for "remote pairing" |
