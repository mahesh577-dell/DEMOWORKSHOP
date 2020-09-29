package com.demowebpage.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class insideloginpage {
	
WebDriver ldriver;
	
	public insideloginpage(WebDriver rdriver)
	{
		ldriver=rdriver;
		PageFactory.initElements(rdriver, this);
		
		
	}
	
	@FindBy(xpath="//input[@class='email']")
	@CacheLookup
	WebElement UserName;
	
	@FindBy(xpath="//input[@class='button-1 login-button']")
	@CacheLookup
	WebElement loginButton;
	
	
	@FindBy(xpath="//input[@id='Password']")
	@CacheLookup
	WebElement Password;
	
	public void UserName(String username)
	{
		UserName.sendKeys(username);
	}
	
	public void Password(String password)
	{
		Password.sendKeys(password);
		
	}
	
	public void submit()
	{
		loginButton.click();
		
	}


}
