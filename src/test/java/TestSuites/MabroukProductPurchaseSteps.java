package TestSuites;

import Pages.MabroukHomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.junit.Assert;

public class MabroukProductPurchaseSteps {

    private MabroukHomePage mabroukHomePage;
    private final String BASE_URL = "https://mabrouk.tn/";

    @Before
    public void setup() {
        mabroukHomePage = new MabroukHomePage();
    }

    @Given("I am on the Mabrouk homepage")
    public void i_am_on_the_mabrouk_homepage() {
        mabroukHomePage.navigateToHomePage(BASE_URL);
        System.out.println("Navigated to " + BASE_URL);
    }

    @When("I close the popup if displayed")
    public void i_close_the_popup_if_displayed() {
        mabroukHomePage.closePopupIfDisplayed();
    }

    @When("I hover over the {string} menu")
    public void i_hover_over_the_menu(String menuText) {
        mabroukHomePage.hoverOverMenu(menuText);
    }

    @When("I click on {string}")
    public void i_click_on_sub_menu_item(String subMenuText) {
        mabroukHomePage.clickSubMenuItem(subMenuText);
    }

    @When("I click on the product {string}")
    public void i_click_on_the_product(String productName) {
        mabroukHomePage.clickProduct(productName);
    }

    @When("I select quantity {string}, color {string}, and size {string}")
    public void i_select_quantity_color_and_size(String quantity, String color, String size) {
        mabroukHomePage.selectProductOptions(quantity, color, size);
    }

    @When("I click the {string} button")
    public void i_click_the_button(String buttonText) {
        if (buttonText.equals("Ajouter au panier")) {
            mabroukHomePage.clickAddToCartButton();
        } else {
            System.out.println("Button text not recognized for this step: " + buttonText);
        }
    }

    @Then("the product should be added to the cart")
    public void the_product_should_be_added_to_the_cart() {
        Assert.assertTrue("Product was not successfully added to the cart.", mabroukHomePage.isProductAddedToCart());
        System.out.println("Product successfully added to the cart (verified by confirmation message).");
    }

    @After
    public void tearDown() {
        mabroukHomePage.tearDownBrowser();
    }
}
