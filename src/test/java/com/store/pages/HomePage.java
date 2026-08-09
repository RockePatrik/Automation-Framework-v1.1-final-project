package com.store.pages;

import com.store.reports.AllureUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    /*
     * Elementos principales de la página de inicio.
     */
    private final By imgLogo = By.cssSelector("a.logo");
    private final By inputSearch = By.id("search");
    private final By linkRegister = By.linkText("Create an Account");
    private final By linkLogin = By.linkText("Sign In");
    private final By pageTitle = By.cssSelector("h1.page-title");
    private final By footer = By.cssSelector("footer.page-footer");

    /*
     * Recibe el driver inicializado en BaseTest
     * y lo envía al constructor de BasePage.
     */
    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Verificar que el logo de Magento esté visible")
    public boolean verifyLogoIsDisplayed() {
        return isDisplayedElement(imgLogo);
    }

    @Step("Verificar el logo y adjuntar evidencia")
    public boolean verifyLogoIsDisplayedWithScreenShot() {
        boolean logoIsDisplayed = isDisplayedElement(imgLogo);

        AllureUtils.takeScreenshot(
                driver,
                "HomePage - Logo de Magento"
        );

        return logoIsDisplayed;
    }

    @Step("Verificar que el buscador esté disponible")
    public boolean verifyInputSearchIsDisplayed() {
        return find(inputSearch).isEnabled();
    }

    @Step("Ingresar a la opción Create an Account")
    public void clickRegister() {
        click(linkRegister);
    }

    @Step("Ingresar a la opción Sign In")
    public void clickLogin() {
        click(linkLogin);
    }

    @Step("Obtener el título de la página")
    public String getPageTitle() {
        return text(pageTitle);
    }

    @Step("Verificar que el footer esté visible")
    public boolean verifyFooterIsDisplayed() {
        return isDisplayedElement(footer);
    }
}