package org.tatacliq.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeTest;

public class BaseClass {
	public WebDriver driver;
	Properties prop;
	
	public void propload() throws IOException  {
		
		FileInputStream propobj=new FileInputStream("src\\test\\resources\\config.properties");
		prop=new Properties();
		prop.load(propobj);
	}
	@BeforeTest
	public void configurations() throws IOException {
		propload();
		String browser = prop.getProperty("browser");
		String url = prop.getProperty("url");
		
		// Basic if-else structure to choose   browser
	       if (browser.equalsIgnoreCase("chrome")) {
	           driver = new ChromeDriver();
	       } else if (browser.equalsIgnoreCase("firefox")) {
	           driver = new FirefoxDriver();
	       } else if (browser.equalsIgnoreCase("edge")) {
	           driver = new EdgeDriver();
	       } else {
	           System.out.println("Invalid browser name in config file.");
	           return;
	       }
	       driver.get(url);
	       driver.manage().window().maximize();
	       driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}
	


}
