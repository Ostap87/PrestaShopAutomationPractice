package com.prestashop.tests;

import java.util.Random;
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

import com.github.javafaker.Faker;

public class LoginTest {
/**
 * Prestashop login positive scenarios
 * All test cases for positive login scenarios should be in a separate class. Each scenario will be a different test method. Name of the method will be the name of the scenario.

test scenario: login test
go to http://automationpractice.com (Links to an external site.)Links to an external site.

register a new user. You have to generate a new email, first name, last name every time

Sign out once the registration is complete

Log back using the same information

Verify that correct first name and last name is displayed on the top right
 */
	WebDriver d;
	Faker f;
	Random rApt;
	Random states;
	Random postCode;
	Random rStateClk;
	Random rMonthBirth;
	Random rDayBirth;
	Random rYearBirth;

	@BeforeClass
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "/Users/oleksandrostapchuk/Desktop/SELENIUM/chromedriver");
		d = new ChromeDriver();

	}

	@BeforeMethod
	public void goToCurrentPage() {
		d.get("http://automationpractice.com");
		d.manage().window().fullscreen();
		d.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	}

	@Test
	public void loginTest() throws InterruptedException {
		f = new Faker();
		rApt = new Random(3);
		rStateClk = new Random();
		rMonthBirth = new Random();
		rDayBirth = new Random();
		rYearBirth = new Random();
		// phoneNums = new Random();
		// long r = 0;
		// r = 999900000 + phoneNums.nextInt(100000);

		String randomEmail = f.name().username() + "@yahoo.com";
		String firstName = f.name().firstName();
		String lastName = f.name().lastName();
		String companyName = firstName + "_$_" + lastName + "LLC";
		String homeAddress = f.address().streetAddress();
		String randomCity = f.address().city();
		String zipCode = f.address().zipCode().substring(0, 5);
		String addInformation = f.chuckNorris().fact();
		String pWord = f.internet().password(5, 10);
		String firstLastName = firstName + " " + lastName;
		int stateIndex = rStateClk.nextInt(53) + 1;
		int monthIndex = rMonthBirth.nextInt(12)+1;
		int dayIndex = rDayBirth.nextInt(31)+1;
		int yearIndex = rYearBirth.nextInt(119)+1;
		
		// Sign in and go to next page
		d.findElement(By.xpath("//a[@class='login']")).click();

		// add email and click create and account
		d.findElement(By.id("email_create")).sendKeys(randomEmail);
		d.findElement(By.xpath("//span/i[@class='icon-user left']")).click();

		// Click < Mr > --> radioButton
		d.findElement(By.id("id_gender1")).click();

		/*
		 * Fill up sign up form 1) First name 2) Last name 3) Add random password
		 */
		WebElement customerFirstName = d.findElement(By.id("customer_firstname"));
		customerFirstName.sendKeys(firstName);

		WebElement customerLastName = d.findElement(By.id("customer_lastname"));
		customerLastName.sendKeys(lastName);

		WebElement password = d.findElement(By.id("passwd"));
		password.sendKeys(pWord);

		// Click on day/month/year
		Thread.sleep(1000);
		WebElement daySelect = d.findElement(By.xpath("//select[@id='days']/option[" + dayIndex + "]"));
		daySelect.click();
		Thread.sleep(000);
		WebElement monthSelect = d.findElement(By.xpath("//select[@id='months']/option[" + monthIndex + "]"));
		monthSelect.click();
		Thread.sleep(1000);
		WebElement yearSelect = d.findElement(By.xpath("//select[@id='years']/option[" + yearIndex + "]"));
		yearSelect.click();

//		d.findElement(By.xpath("//select[@id='days']/option[28]")).click();
//		d.findElement(By.xpath("//select[@id='months']/option[9]")).click();
//		d.findElement(By.xpath("//select[@id='years']/option[30]")).click();
		
		// Click on both checkboxes
		d.findElement(By.id("newsletter")).click();
		d.findElement(By.id("optin")).click();

		// Add random company name
		WebElement company = d.findElement(By.id("company"));
		company.sendKeys(companyName);

		// Add random street
		WebElement address = d.findElement(By.id("address1"));
		address.sendKeys(homeAddress + " Street");

		// Add random apartment number
		WebElement aptNum = d.findElement(By.id("address2"));
		aptNum.sendKeys(f.address().buildingNumber());

		// Add random city name
		WebElement city = d.findElement(By.id("city"));
		city.sendKeys("" + randomCity);

		// Select state
		d.findElement(By.xpath("//select[@id='id_state']")).click();
		Thread.sleep(1000);
		WebElement stateSelect = d
				.findElement(By.xpath("//select[@id='id_state']/option[@value='" + stateIndex + "']"));
		stateSelect.click();

		// Add Zip code
		WebElement zip = d.findElement(By.id("postcode"));
		zip.sendKeys(zipCode);


		// Add country as Default

		// Add additional information
		WebElement addInfo = d.findElement(By.cssSelector("#other"));
		addInfo.sendKeys(addInformation);

		// Add home phone and Cell
		WebElement housePhone = d.findElement(By.cssSelector("#phone"));
		housePhone.sendKeys(f.phoneNumber().cellPhone());

		WebElement cellPhone = d.findElement(By.cssSelector("#phone_mobile"));
		cellPhone.sendKeys(f.phoneNumber().cellPhone());

		// Add alias address
		WebElement aliasAddress = d.findElement(By.cssSelector("#alias"));
		aliasAddress.sendKeys(homeAddress);

		// Click register
		Thread.sleep(1000);
		d.findElement(By.xpath("//span[contains(text(),'Register')]")).click();

		// Log out
		d.findElement(By.xpath("//div[@class='header_user_info'][2]/a")).click();

		// Log in again with already created credentials
		Thread.sleep(1000);

		d.findElement(By.cssSelector("[id='email']")).sendKeys(randomEmail);
		d.findElement(By.cssSelector("[id='passwd']")).sendKeys(pWord);
		d.findElement(By.cssSelector("[id='SubmitLogin']")).click();

		// Verify that correct first name and last name is displayed on the top right
		Thread.sleep(1000);

		WebElement curName = d.findElement(By.xpath("//div[@class='header_user_info']/a/span"));
		String currentName = curName.getText();
		Assert.assertEquals(firstLastName, currentName);

	}

	@AfterClass
	public void quit() {
		d.quit();
	}
}
