package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;


public class ProductsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By title = By.cssSelector("span.title");
    private final By addToCartButtonByProduct = By.cssSelector("button.btn_inventory");
    private final By shoppingCartBadge = By.cssSelector("a.shopping_cart_link .shopping_cart_badge");
    private final By cartLink = By.cssSelector("a.shopping_cart_link");


    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isAt() {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(title, "Products"));
    }

    public void addFirstProductToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButtonByProduct)).click();
    }

    public String getCartBadge() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(shoppingCartBadge)).getText();
        } catch (Exception e) {
            return "0";
        }
    }

    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
    }
}
