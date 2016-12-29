@browser
Feature: The Shopping Cart

  Scenario Outline: Add items to the cart
    Given I open target.com on an <device> running <platformVersion>
    And I search for <itemType>
    When I add <count> of <itemName> to my shopping cart
    Then I find <count> items in my cart

    Examples: 
      | device  | platformVersion | itemName                                  | itemType | count |
      | browser |      Firefox    | JBL Flip 3 Splashproof Bluetooth Speaker  | speakers |     2 |
      | browser |      Chrome     | JBL Flip 3 Splashproof Bluetooth Speaker  | speakers |     3 |
      | browser |      Safari     | JBL Flip 3 Splashproof Bluetooth Speaker  | speakers |     2 |
