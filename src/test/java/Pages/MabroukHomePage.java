package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class MabroukHomePage extends BasePage {


    public MabroukHomePage() {
        if (driver == null) {
            setupBrowser("chrome");
        }
    }

  

    private By popupCloseButton = By.xpath("//div[@data-testid='POPUP']//button[contains(@aria-label,'Close')]");
    private By popupModal = By.xpath("//div[@data-testid='POPUP']//button[contains(@aria-label,'Close')]");
    private By femmeMenu = By.xpath("//span[text()='Femme']//parent::node()");
    private By chemisesBlousesSubMenu = By.xpath("//span[text()='Chemises & Blouses']//parent::node()");

    private By productTitle = By.xpath("//h1[@class='woo_c-product-details-title product_title entry-title']");
    private By panierTitle = By.xpath("//div[@class='variations_button']//a[text()='Voir le panier']");
    private By quantityInput = By.xpath("//input[@title='Qte']");
    private By colorOptions = By.xpath("//span[@class='color-item ']");
    private By sizeOptions = By.xpath("//span[@class='size-item']");
    private By addToCartButton = By.xpath("//div[@class='variations_button']//a[normalize-space()='Ajouter au panier']");
    private By cartConfirmationMessage = By.xpath("//div[@class='submenu_cart cart visible']");
    private By blockOverlay = By.cssSelector(".blockUI.blockOverlay");
    private By voirLePanierLink = By.xpath("//a[normalize-space()='Voir le panier']");

    public By getProductLink(String productName) {
        return By.xpath("//h3[@class='font-titles']/a[@href='https://mabrouk.tn/produit/" + productName + "/']//parent::node()//parent::node()//parent::node()");
    }



    public void navigateToHomePage(String url) {
        driver.get(url);
    }

    public void closePopupIfDisplayed() {
        try {
            wait.withTimeout(Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(popupModal));
            System.out.println("Popup modal is displayed.");
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(popupCloseButton));
            closeButton.click();
            System.out.println("Popup closed.");
            wait.until(ExpectedConditions.invisibilityOfElementLocated(popupModal));
        } catch (TimeoutException e) {
            System.out.println("Popup did not appear within the specified time. Continuing...");
        } catch (NoSuchElementException e) {
            System.out.println("Popup close button not found. Continuing...");
        }
    }

    public void hoverOverMenu(String menuText) {
        WebElement menuElement;
        if ("Femme".equals(menuText)) {
            menuElement = wait.until(ExpectedConditions.visibilityOfElementLocated(femmeMenu));
        } else {
            throw new IllegalArgumentException("Unsupported menu text for hover: " + menuText);
        }

        Actions actions = new Actions(driver);
        actions.moveToElement(menuElement).perform();
        System.out.println("Hovered over menu: " + menuText);
    }

    public void clickSubMenuItem(String subMenuText) {
        WebElement subMenuElement;
        if ("Chemises & Blouses".equals(subMenuText)) {
            subMenuElement = wait.until(ExpectedConditions.elementToBeClickable(chemisesBlousesSubMenu));
        } else {
            throw new IllegalArgumentException("Unsupported sub-menu text for click: " + subMenuText);
        }
        subMenuElement.click();
        System.out.println("Clicked sub-menu item: " + subMenuText);
    }

    public void clickProduct(String productName) {
        By productLink = getProductLink(productName);
        WebElement productElement = wait.until(ExpectedConditions.elementToBeClickable(productLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", productElement);
        productElement.click();
        System.out.println("Clicked product: " + productName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(productTitle));
    }

    public void selectProductOptions(String quantity, String color, String size) {
        
        WebElement selectedColorElement = null;
        List<WebElement> colors = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(colorOptions));
        for (WebElement col : colors) {

            if (col.getAttribute("data-option") != null && col.getAttribute("data-option").equalsIgnoreCase(color)) {
                selectedColorElement = col;
                break;
            } else if (col.getText().trim().equalsIgnoreCase(color)) {
                selectedColorElement = col;
                break;
            }
        }
        
        if (selectedColorElement != null) {
            wait.until(ExpectedConditions.elementToBeClickable(selectedColorElement));
            selectedColorElement.click();
            System.out.println("Selected color: " + color);
        } else {
            System.out.println("Color option '" + color + "' not found.");
            throw new NoSuchElementException("Color option '" + color + "' not found.");
        }

        WebElement selectedSizeElement = null;
        List<WebElement> sizes = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(sizeOptions));
        for (WebElement sz : sizes) {
            if (sz.getText().trim().equalsIgnoreCase(size)) {
                selectedSizeElement = sz;
                break;
            }
        }

        if (selectedSizeElement != null) {
            wait.until(ExpectedConditions.elementToBeClickable(selectedSizeElement));
            selectedSizeElement.click();
            System.out.println("Selected size: " + size);
        } else {
            System.out.println("Size option '" + size + "' not found.");
            throw new NoSuchElementException("Size option '" + size + "' not found.");
        }

    }

    public void clickAddToCartButton() {
    	
    	WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addButton);

        try {
            addButton.click();
            System.out.println("Clicked 'Ajouter au panier' button using standard click.");
        } catch (ElementClickInterceptedException e) {
            System.out.println("ElementClickInterceptedException caught for Add to Cart. Attempting JavaScript click.");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addButton);
            System.out.println("Clicked 'Ajouter au panier' button using JavaScript click.");
        }

        wait.until(ExpectedConditions.or(
            ExpectedConditions.visibilityOfElementLocated(cartConfirmationMessage),
            ExpectedConditions.visibilityOfElementLocated(voirLePanierLink)
        ));
    }

    public boolean isProductAddedToCart() {
    	try {
            WebElement voirPanier = wait.until(ExpectedConditions.visibilityOfElementLocated(voirLePanierLink));
            System.out.println("'Voir le panier' link is displayed.");

            wait.until(ExpectedConditions.visibilityOfElementLocated(cartConfirmationMessage));
            System.out.println("Right-side cart popup is displayed.");
            return true;
        } catch (TimeoutException e) {
            try {
                WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(cartConfirmationMessage));
                System.out.println("Cart confirmation message: " + message.getText());
                return message.getText().contains("a été ajouté à votre panier") || message.getText().contains("Revoir le panier");
            } catch (TimeoutException ex) {
                System.out.println("Neither cart confirmation message nor 'Voir le panier' link nor right-side cart popup found.");
                return false;
            }
        }
    }
}
