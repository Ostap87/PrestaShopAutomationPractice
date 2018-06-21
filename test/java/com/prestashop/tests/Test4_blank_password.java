package com.prestashop.tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Test4_blank_password {

	WebDriver d;

	@BeforeClass
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "/Users/oleksandrostapchuk/Desktop/SELENIUM/chromedriver");
		d = new ChromeDriver();
		d.manage().window().maximize();
		d.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	}

	@BeforeMethod
	public void goToCurrentPage() {
		d.get("http://automationpractice.com");

	}

	@Test
	public void wrongCredentials() {
		d.findElement(By.xpath("//a[@class='login']")).click();
		WebElement login = d.findElement(By.xpath("//input[@id='email']"));
		WebElement psw = d.findElement(By.xpath("//input[@id='passwd']"));
		WebElement signInButton = d.findElement(By.xpath("//button[@id='SubmitLogin']"));

		login.sendKeys("appleFruit@yahoo.com");
		psw.sendKeys("");
		signInButton.click();

		WebElement statusFail = d.findElement(By.xpath("//li[contains(text(),'Password is required.')]"));
		Assert.assertTrue(statusFail.isDisplayed());

	}

	@AfterClass
	public void quit() {
		d.quit();
	}
	
	
	
}
