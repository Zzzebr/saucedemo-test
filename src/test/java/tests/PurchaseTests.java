package tests;

import tests.BaseTest;
import pages.CartPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;


public class PurchaseTests extends BaseTest {


    @Test(description = "Покупка: добавить товар, оформить заказ и завершить")
    public void testCompletePurchaseFlow() {
        LoginPage login = new LoginPage(driver);
        login.open();
        login.login("standard_user", "secret_sauce");


        ProductsPage products = new ProductsPage(driver);
        Assert.assertTrue(products.isAt(), "Ожидаем страницу Products");


        products.addFirstProductToCart();
        Assert.assertEquals(products.getCartBadge(), "1", "В корзине должен быть 1 товар");


        products.openCart();
        CartPage cart = new CartPage(driver);
        Assert.assertTrue(cart.isAt(), "Ожидаем страницу Your Cart");


        cart.clickCheckout();
        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.fillInfoAndContinue("Ivan", "Ivanov", "12345");
        checkout.finishCheckout();
        Assert.assertTrue(checkout.isComplete(), "Ожидаем страницу с подтверждением заказа");
    }


    @Test(description = "Cart: добавить и удалить товар из корзины")
    public void testAddAndRemoveFromCart() {
        LoginPage login = new LoginPage(driver);
        login.open();
        login.login("standard_user", "secret_sauce");


        ProductsPage products = new ProductsPage(driver);
        products.addFirstProductToCart();
        Assert.assertEquals(products.getCartBadge(), "1", "В корзине должен быть 1 товар");


        products.openCart();
        CartPage cart = new CartPage(driver);
        Assert.assertTrue(cart.isAt(), "Ожидаем страницу Your Cart");
        cart.removeFirstItem();
    }
}
