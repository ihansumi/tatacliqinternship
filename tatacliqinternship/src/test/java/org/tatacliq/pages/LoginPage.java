package org.tatacliq.pages;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class LoginPage {

WebDriver driver;
	
	public LoginPage(WebDriver driver) 
	{
		this.driver=driver;
    }
	
	// Method to get the homepage title
    public String getHomePageTitle() {
        return driver.getTitle();
    }
	// Method to verify the Tata CLiQ homepage logo
    public boolean isLogoDisplayed() {
    	 WebElement logo = driver.findElement(By.xpath("//div[@class='DesktopHeader__logoHolder']"));
         return logo.isDisplayed();
    }
    public String verifyCategoryElement() {
    	WebElement category=driver.findElement(By.xpath("//div[@class='DesktopHeader__categoryAndBrand' and contains(text(),'Categories')]"));
    	return category.getText();   	
    }
    public void clickCategory() {
    	WebElement categorybut=driver.findElement(By.xpath("//div[@class='DesktopHeader__categoryAndBrand' and contains(text(),'Categories')]"));
    	categorybut.click();
    }
    public String verifyGadgetsbutton() {
    	WebElement gadgetbutt=driver.findElement(By.xpath("//div[@class='DesktopHeader__categoryDetailsValue' and contains(text(),'Gadgets')]"));
    	return gadgetbutt.getText();
    }
    public void gadbuttonClick() {
    	WebElement gadbuttonclick=driver.findElement(By.xpath("//div[@class='DesktopHeader__categoryDetailsValue' and contains(text(),'Gadgets')]"));
    	gadbuttonclick.click();
    }
    public boolean isElectronicsTitleDisplayed() {
        WebElement title = driver.findElement(By.xpath("//div[@class='Plp__headerHeading']/h1[text()='Electronics Online']"));
        return title.isDisplayed();
    }
    public void searchBarLocate() {
    	driver.findElement(By.xpath("//input[@id='search-text-input']")).click();
    	   	
    }
    public void searchAppleProd(String prod) {
    	WebElement appleprod=driver.findElement(By.xpath("//input[@id='search-text-input']"));
    	appleprod.sendKeys(prod);
    }
    public void dismissPopupIfPresent() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            WebElement noThanksButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("moe-dontallow_button")));
            noThanksButton.click();
            System.out.println("Popup dismissed: Clicked 'No, Thanks'");
        } catch (TimeoutException e) {
            System.out.println("No popup appeared.");
        }
    }
    public void searchProduct(String productName) {
        dismissPopupIfPresent(); // <-- Handle the popup first

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("search-text-input")));

        try {
            searchInput.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchInput);
        }

        searchInput.clear();
        searchInput.sendKeys(productName);
        searchInput.sendKeys(Keys.ENTER);
    }
    public boolean isAppleAirpodsProDisplayed() {     
    	 try {
    	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    	        WebElement element = wait.until(ExpectedConditions.refreshed(
    	            ExpectedConditions.visibilityOfElementLocated(
    	                By.xpath("//h2[contains(text(),'Apple AirPods Pro (2nd Generation)')]")
    	            )
    	        ));
    	        return element.isDisplayed();
    	    } catch (StaleElementReferenceException e) {
    	        System.out.println("Stale element reference. Retrying...");
    	        return isAppleAirpodsProDisplayed(); // Retry once
    	    } catch (TimeoutException e) {
    	        System.out.println("Apple AirPods Pro title not found within timeout.");
    	        return false;
    	    }     
    }
    public void appleAirpodsclick() {
    	try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            By productTitleLocator = By.xpath("//h2[contains(text(),'Apple AirPods Pro (2nd Generation)')]");

            WebElement productTitle = wait.until(ExpectedConditions.presenceOfElementLocated(productTitleLocator));

            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", productTitle);
            Thread.sleep(500);

            // Wait again for it to be clickable
            wait.until(ExpectedConditions.elementToBeClickable(productTitleLocator));

            try {
                // Try normal click first
                productTitle.click();
                System.out.println("Native click on product title successful.");
            } catch (Exception clickException) {
                // Fallback: Use JS click
                System.out.println("Native click failed. Trying JavaScript click.");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", productTitle);
            }

        } catch (Exception e) {
            System.out.println("Error in clicking Apple AirPods product: " + e.getMessage());
        }
    }
   
    	public String verifyPrice() {
    		// (Optional) ensure we are in the right window
    	    for (String handle : driver.getWindowHandles()) {
    	        driver.switchTo().window(handle);
    	        if (driver.getTitle().contains("AirPods")) break;
    	    }

    	    // Avoid zoom overlay
    	    new Actions(driver).moveByOffset(0, 0).perform();

    	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	    WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
    	        By.xpath("//h3[contains(text(),'₹21999')]")));

    	    return priceElement.getText();
    	}
    	    	    	     	
    	public void switchToNewWindow() {
    		String originalWindow = driver.getWindowHandle();
    	    System.out.println("Original Window: " + originalWindow);
    	    System.out.println("Original Title: " + driver.getTitle());

    	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    	    wait.until(driver -> driver.getWindowHandles().size() > 1);

    	    Set<String> windowHandles = driver.getWindowHandles();

    	    for (String handle : windowHandles) {
    	        if (!handle.equals(originalWindow)) {
    	            driver.switchTo().window(handle);
    	            System.out.println("Switched to new window: " + handle);
    	            break;
    	        }
    	    }
    	    // Extra wait to ensure page is loaded
    	    new WebDriverWait(driver, Duration.ofSeconds(10))
    	            .until(d -> !d.getTitle().isEmpty());

    	    System.out.println("New Window Title: " + driver.getTitle());
    	}
    	   	
    	public String getNewWindowTitle() {
    		String title = driver.getTitle();
    	    System.out.println("Page title after zoom workaround: " + title);
    	    return title;
    	}
    	public void clickAddToBag() {
    	    Actions actions = new Actions(driver);

    	    // Move mouse to top-left corner to avoid zoom effect
    	    actions.moveByOffset(0, 0).perform();

    	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    	    WebElement addToBagButton = wait.until(ExpectedConditions.elementToBeClickable(
    	        By.xpath("//div[@data-test='button-main-div' and .//span[text()='ADD TO BAG']]")));

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

    	    // Move mouse to avoid zoom effects or overlays
    	    actions.moveByOffset(0, 0).perform();

    	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    	    WebElement goToBagButton = wait.until(ExpectedConditions.elementToBeClickable(
    	        By.xpath("//div[@data-test='button-main-div' and .//span[text()='GO TO BAG']]")));

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
    	    WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(
    	        By.xpath("//div[@class='CartItemForDesktop__removeLabelForCartPage' and text()='Remove']")));

    	    removeButton.click();
    	    System.out.println("Remove button clicked.");
    	}
    	
    	public boolean isCartEmptyMessageDisplayed() {
    	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	    WebElement emptyMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
    	        By.xpath("//div[contains(text(), 'Your bag is empty!')]")));
    	    return emptyMsg.isDisplayed();
    	}
    	
    	
    }



