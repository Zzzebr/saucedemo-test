package tests;

import tests.BaseTest;
import pages.CartPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdditionalTests extends BaseTest {

    @Test(description = "Performance glitch user: страница товаров загружается (увеличенный таймаут)")
    public void testPerformanceGlitchUserLoadsProducts() {
        LoginPage login = new LoginPage(driver);
        login.open();
        login.login("performance_glitch_user", "secret_sauce");

        ProductsPage products = new ProductsPage(driver);
        Assert.assertTrue(products.isAt(), "Ожидаем, что Products page загрузится для performance_glitch_user");
    }

    @Test(description = "Problem user: проверка наличия ссылок/изображений у товаров (проверка наличия src у img)")
    public void testProblemUserProductImagesVisible() {
        LoginPage login = new LoginPage(driver);
        login.open();
        login.login("problem_user", "secret_sauce");

        ProductsPage products = new ProductsPage(driver);
        Assert.assertTrue(products.isAt(), "Ожидаем страницу Products для problem_user");

        int images = products.countVisibleProductImages();
        Assert.assertTrue(images > 0, "Ожидаем, что у товаров есть src у изображений (images count > 0)");
    }

    @Test(description = "Checkout: валидация - пропущено поле First Name (негативный кейс)")
    public void testCheckoutValidationMissingFirstName() {
        LoginPage login = new LoginPage(driver);
        login.open();
        login.login("standard_user", "secret_sauce");

        ProductsPage products = new ProductsPage(driver);
        products.addFirstProductToCart();
        products.openCart();

        CartPage cart = new CartPage(driver);
        Assert.assertTrue(cart.isAt(), "Ожидаем страницу корзины");
        cart.clickCheckout();

        CheckoutPage checkout = new CheckoutPage(driver);
        // Передаём пустое имя, ожидаем видимое сообщение об ошибке
        checkout.fillInfoAndContinue("", "Ivanov", "12345");
        String err = checkout.getErrorMessage();
        Assert.assertTrue(err.toLowerCase().contains("first") || err.toLowerCase().contains("is required"),
                "Ожидаем сообщение об ошибке для пропущенного имени");
    }

    @Test(description = "Products: сортировка Name (Z to A) — проверка порядка")
    public void testProductSortZToA() {
        LoginPage login = new LoginPage(driver);
        login.open();
        login.login("standard_user", "secret_sauce");

        ProductsPage products = new ProductsPage(driver);
        Assert.assertTrue(products.isAt(), "Ожидаем страницу Products");

        List<String> before = products.getAllProductNames();
        Assert.assertFalse(before.isEmpty(), "Список товаров не должен быть пустым");

        products.selectSortOption("Name (Z to A)");

        List<String> after = products.getAllProductNames();
        List<String> sorted = new ArrayList<>(after);
        Collections.sort(sorted, Comparator.reverseOrder());
        Assert.assertEquals(after, sorted, "Ожидаем, что после сортировки Name (Z to A) порядок будет убывающим");
    }
}