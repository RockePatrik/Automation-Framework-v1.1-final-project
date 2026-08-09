package com.store.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductPage extends BasePage {

    private final By successMessage =
            By.cssSelector(".message-success");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    /*
     * Localiza el producto por SKU, coloca el cursor sobre
     * su tarjeta y selecciona Add to Cart.
     */
    @Step("Agregar producto con SKU: {sku}")
    public void addProductToCart(String sku) {

        By productFormLocator = By.cssSelector(
                "form[data-product-sku='" + sku + "']"
        );

        WebElement productForm = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        productFormLocator
                )
        );

        WebElement productCard = productForm.findElement(
                By.xpath(
                        "./ancestor::li[contains(@class,'product-item')]"
                )
        );

        //  muestra Add to Cart al colocar el cursor
        new Actions(driver)
                .moveToElement(productCard)
                .perform();

        WebElement addToCartButton = productForm.findElement(
                By.xpath(
                        ".//button[.//span[normalize-space()='Add to Cart']]"
                )
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        addToCartButton
                )
        ).click();
    }

    @Step("Obtener confirmación del producto agregado")
    public String getSuccessMessage() {
        return text(successMessage);
    }
}