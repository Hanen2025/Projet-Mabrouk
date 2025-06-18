#Author:Hanen
Feature: Mabrouk Product Purchase
  As a user, I want to be able to navigate the Mabrouk website,
  select a product, add it to the cart, and verify it's added successfully.

  Scenario: Successfully add a product to the cart
    Given I am on the Mabrouk homepage
    When I close the popup if displayed
    And I hover over the "Femme" menu
    And I click on "Chemises & Blouses"
    And I click on the product "chemise-jeliora"
    And I select quantity "2", color "noir", and size "M"
    And I click the "Ajouter au panier" button
    Then the product should be added to the cart