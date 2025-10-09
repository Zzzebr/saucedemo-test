package tests;

import tests.BaseTest;
import pages.LoginPage;
import pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;


public class LoginTests extends BaseTest {


    @Test(description = "Позитивный сценарий: успешный вход стандартного пользователя")
    public void testSuccessfulLogin() {
        LoginPage login = new LoginPage(driver);
        login.open();
        login.login("standard_user", "secret_sauce");


        ProductsPage products = new ProductsPage(driver);
        Assert.assertTrue(products.isAt(), "Ожидаем страницу Products после логина");
    }


    @Test(description = "Негативный сценарий: неверные креды")
    public void testInvalidLogin() {
        LoginPage login = new LoginPage(driver);
        login.open();
        login.login("invalid_user", "invalid_pass");
        String error = login.getErrorMessage();
        Assert.assertTrue(error.toLowerCase().contains("username and password do not match" ) ||
                error.toLowerCase().contains("username is required") ||
                error.toLowerCase().contains("epic sadface"), "Ожидаем сообщение об ошибке авторизации");
    }


    @Test(description = "Негативный сценарий: locked_out_user")
    public void testLockedOutUser() {
        LoginPage login = new LoginPage(driver);
        login.open();
        login.login("locked_out_user", "secret_sauce");
        String error = login.getErrorMessage();
        Assert.assertTrue(error.toLowerCase().contains("locked out") || error.toLowerCase().contains("sorry"),
                "Ожидаем сообщение о заблокированном пользователе");
    }
}
