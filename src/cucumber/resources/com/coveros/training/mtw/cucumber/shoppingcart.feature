Feature: The Shopping Cart
Scenario Outline: Add items to the cart

  Given I open target.com in <browser>
  And I search for <itemType>
  When I add <count> of <itemName> to my shopping cart
  Then I find the right number of items in my cart

Examples:

| browser | itemName                                             | itemType   | count |
| firefox | Acoustic Research Mainstreet Outdoor Bluetooth®...   | speakers   | 3     |
| firefox | KEF 5-1/4" 2-Way Outdoor Speaker (Each) - Black      | speakers   | 2     |
| firefox | Samsung 32" Class 720p 60Hz...                       | television | 3     |
