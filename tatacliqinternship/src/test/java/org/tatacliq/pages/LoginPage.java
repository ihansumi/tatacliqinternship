package org.tatacliq.pages;

import java.time.Duration;
import org.openqa.selenium.TimeoutException;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    WebDriver driver;

    // Constructor to initialize PageFactory elements
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ==== ELEMENTS ====
    @FindBy(xpath = "//div[@class='DesktopHeader__logoHolder']")
    private WebElement logo;
    
    @FindBy(xpath = "//div[@class='DesktopHeader__categoryAndBrand' and contains(text(),'Categories')]")
    private WebElement category;
    
    @FindBy(xpath = "//div[@class='DesktopHeader__categoryDetailsValue' and contains(text(),'Gadgets')]")
    private WebElement gadgetsButton;

    @FindBy(xpath = "//div[@class='Plp__headerHeading']/h1[text()='Electronics Online']")
    private WebElement electronicsTitle;
    
    @FindBy(id = "search-text-input")
    private WebElement searchInput;

    @FindBy(id = "moe-dontallow_button")
    private WebElement noThanksButton;

    @FindBy(xpath = "//h2[contains(text(),'Apple AirPods Pro (2nd Generation)')]")
    private WebElement airpodsProduct;
       
    @FindBy(xpath = "//h3[contains(text(),'₹')]")
    private WebElement priceElement;
    
    @FindBy(xpath = "//div[@data-test='button-main-div' and .//span[text()='ADD TO BAG']]")
    private WebElement addToBagButton;

    @FindBy(xpath = "//div[@data-test='button-main-div' and .//span[text()='GO TO BAG']]")
    private WebElement goToBagButton;
    
    @FindBy(xpath = "//div[@class='CartItemForDesktop__removeLabelForCartPage' and text()='Remove']")
    private WebElement removeButton;

    @FindBy(xpath = "//div[contains(text(), 'Your bag is empty!')]")
    private WebElement emptyCartMessage;
    

    // ==== METHODS ====
    public String getHomePageTitle() {
        return driver.getTitle();
    }

    public boolean isLogoDisplayed() {
        return logo.isDisplayed();
    }
    public String verifyCategoryElement() {
        return category.getText();
    }

    public void clickCategory() {
        category.click();
    }
    public String verifyGadgetsbutton() {
        return gadgetsButton.getText();
    }

    public void gadbuttonClick() {
        gadgetsButton.click();
    }

    public boolean isElectronicsTitleDisplayed() {
        return electronicsTitle.isDisplayed();
    }
    
    public void dismissPopupIfPresent() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(noThanksButton)).click();
            System.out.println("Popup dismissed: Clicked 'No, Thanks'");
        } catch (TimeoutException e) {
            System.out.println("No popup appeared.");
        }
    }

    public void searchProduct(String productName) {
        dismissPopupIfPresent();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(searchInput));

        try {
            input.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", input);
        }

        input.clear();
        input.sendKeys(productName);
        input.sendKeys(Keys.ENTER);
    }

    public boolean isAppleAirpodsProDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            return wait.until(ExpectedConditions.visibilityOf(airpodsProduct)).isDisplayed();
        } catch (StaleElementReferenceException e) {
            System.out.println("Stale element. Retrying...");
            return isAppleAirpodsProDisplayed();
        } catch (TimeoutException e) {
            System.out.println("Apple AirPods Pro title not found within timeout.");
            return false;
        }
    }

    public void appleAirpodsclick() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.visibilityOf(airpodsProduct));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", airpodsProduct);
            Thread.sleep(500);

            wait.until(ExpectedConditions.elementToBeClickable(airpodsProduct));

            try {
                airpodsProduct.click();
                System.out.println("Native click on product successful.");
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", airpodsProduct);
                System.out.println("Fallback to JS click.");
            }
        } catch (Exception e) {
            System.out.println("Error in clicking Apple AirPods product: " + e.getMessage());
        }
    }

    public void switchToNewWindow() {
        String originalWindow = driver.getWindowHandle();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(driver -> driver.getWindowHandles().size() > 1);

        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                wait.until(d -> !d.getTitle().isEmpty());
                break;
            }
        }
    }

    public String getNewWindowTitle() {
        return driver.getTitle();
    }
   
    public String verifyPrice() {
        // Ensure correct window
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            if (driver.getTitle().contains("AirPods")) break;
        }

        new Actions(driver).moveByOffset(0, 0).perform();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(priceElement));
        
        return priceElement.getText(); // e.g., "₹21999"
    }
    
    public void clickAddToBag() {
        Actions actions = new Actions(driver);
        actions.moveByOffset(0, 0).perform();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(addToBagButton));

        try {
            addToBagButton.click();
            System.out.println("'ADD TO BAG' button clicked successfully.");
        } catch (Exception e) {
            System.out.println("Native click failed, trying JavaScript click.");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToBagButton);
        }
    }

    public void clickGoToBag() {
        Actions actions = new Actions(driver);
        actions.moveByOffset(0, 0).perform();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(goToBagButton));

        try {
            goToBagButton.click();
            System.out.println("'GO TO BAG' button clicked successfully.");
        } catch (Exception e) {
            System.out.println("Native click failed, using JavaScript click.");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", goToBagButton);
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    public void removeItemFromCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(removeButton)).click();
        System.out.println("Remove button clicked.");
    }

    public boolean isCartEmptyMessageDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(emptyCartMessage));
        return emptyCartMessage.isDisplayed();
    }
}



