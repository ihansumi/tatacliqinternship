package org.tatacliq.testcases;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.tatacliq.base.BaseClass;
import org.tatacliq.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestCases extends BaseClass {
	
LoginPage loginpageobj;
	
	@BeforeClass
	public void objinit() 
	{
		loginpageobj=new LoginPage(driver);				
	}
	
	@AfterClass
	public void tearDown() 
	{  
	  driver.quit(); 
	}
	
	@Test(priority=1)
    public void verifyTataCliqHomePageElements_01() 
	{
        // 1. Verify logo
        Assert.assertTrue(loginpageobj.isLogoDisplayed(), "Tata CLiQ logo is not displayed!");

        // 2. Verify title
        String title = loginpageobj.getHomePageTitle();
        Assert.assertTrue(title.contains("Tata CLiQ"), "Homepage title does not contain 'Tata CLiQ'");
    }
	@Test(priority=2)
	public void verifyCategoryElement_02() 
	{
		String act_text=loginpageobj.verifyCategoryElement();
		Assert.assertEquals(act_text, Constants.Expected_text);
		loginpageobj.clickCategory();
	}

	@Test(priority=3)
	public void verifyGadgetmenu_03() 
	{
		String actual_gadtext=loginpageobj.verifyGadgetsbutton();
		Assert.assertEquals(actual_gadtext, Constants.Expected_gadtext);
		loginpageobj.gadbuttonClick();
	}
	
	
	@Test(priority=4)
	public void verifyElectronicsTitle_04() 
	{
		Assert.assertTrue(loginpageobj.isElectronicsTitleDisplayed(), "Title not displayed");
	}
	
	
	@Test(priority=5)
	public void searchAppleProduct_05() 
	{
	    loginpageobj.searchProduct("apple airpod pro 2nd");
	    Assert.assertTrue(loginpageobj.isAppleAirpodsProDisplayed(), 
	            "Apple AirPods Pro (2nd Generation) product is not displayed!");
	    System.out.println("Apple AirPods Pro (2nd Generation) is displayed.");
	    loginpageobj.appleAirpodsclick();
	    loginpageobj.switchToNewWindow();
	    String actualTitle = loginpageobj.getNewWindowTitle();
	    Assert.assertTrue(actualTitle.contains("Apple AirPods Pro"),
	        "Page title mismatch: " + actualTitle);

	    System.out.println("Verified page title: " + actualTitle);
	}
	
	
	@Test(priority = 6, dependsOnMethods = {"searchAppleProduct_05"})
	public void verifyProductPrice_06() 
	{	
		
		String actualPrice = loginpageobj.verifyPrice();	    
	    Assert.assertEquals(actualPrice, Constants.expected_Price);
	    System.out.println("Product price verified successfully: " + actualPrice);
	}

	@Test(priority = 7, dependsOnMethods = {"searchAppleProduct_05"})
	public void verifyAddToBag_07() {
	    loginpageobj.clickAddToBag();
	    loginpageobj.clickGoToBag(); 
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.urlContains("/cart"));

	    String actualUrl = loginpageobj.getCurrentUrl();
	    Assert.assertEquals(actualUrl, Constants.expected_Bag_Url, " Bag page URL mismatch!");

	    System.out.println("Product added to bag and navigated to cart successfully.");
	   
	}
	
	
	@Test(priority = 8, dependsOnMethods = {"verifyAddToBag_07"})
	public void verifyRemoveFromCart_08() {
	    loginpageobj.removeItemFromCart();

	    Assert.assertTrue(loginpageobj.isCartEmptyMessageDisplayed(),
	        "Product was not removed properly or cart is not empty.");

	    System.out.println("Verified: Cart is empty after removal.");

	}



}
	
	
	
	
	


