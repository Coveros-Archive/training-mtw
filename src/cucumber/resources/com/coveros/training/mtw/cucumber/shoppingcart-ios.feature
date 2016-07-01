Feature: The Shopping Cart
Scenario Outline: Add items to the cart

  Given I open target.com on an <device> running <platformVersion>
  And I search for <itemType>
  When I add <count> of <itemName> to my shopping cart
  Then I find the right number of items in my cart

Examples:

| device    | platformVersion | itemName                                             | itemType   | count |
| iPhone 4s | 9.3             | Radio Flyer Grandstand 3-in-1 Wagon  | radios      | 3     |
| iPhone 6  | 9.3             | Innovative Technologies Premium Bluetooth | speakers | 3     |
| iPhone 5  | 8.1             | Latte Communications DeLite LED Lantern | speakers   | 2     |

