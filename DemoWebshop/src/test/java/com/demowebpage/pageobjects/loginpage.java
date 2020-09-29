package com.demowebpage.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class loginpage {
	
	WebDriver ldriver;
	
	public loginpage(WebDriver rdriver)
	{
		ldriver=rdriver;
		PageFactory.initElements(rdriver, this);
		
		
	}
	
	@FindBy(xpath="//a[@class='ico-login']")
	@CacheLookup
	WebElement LoginLink;
	
	@FindBy(xpath="/html/body/div[4]/div[1]/div[4]/div[2]/div/div[1]/h1")
	@CacheLookup
	WebElement Validationsignin;

	public void Login()
	{
		LoginLink.click();
	}
	
	public void signintextvalidation()
	{
		
		String exepected="Welcome, !";
		String actual = Validationsignin.getText();
		if(actual.equals(exepected))
		{
			System.out.println("sign in validation passed");
		}
		else
		{
			System.out.println("signin validation failed");
		}
	}
	
}

