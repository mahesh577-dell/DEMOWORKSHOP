package com.demowebpage.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.demowebpage.testcases.Baseclass;

public class LogoutPage extends Baseclass{

	WebDriver ldriver;

	public LogoutPage(WebDriver rdriver)
	{
		ldriver=rdriver;
		PageFactory.initElements(rdriver, this);


	}

	@FindBy(xpath="//a[@class='ico-logout']")
	@CacheLookup
	WebElement Logout;

	public void Loggingout()
	{
		clickOn(Logout, "logout from the web shop demo site");

	}



}
