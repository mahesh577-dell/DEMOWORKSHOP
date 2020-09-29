package com.demowebpage.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.demowebpage.testcases.Baseclass;

public class ShoppingCart extends Baseclass{


	WebDriver ldriver;

	public ShoppingCart(WebDriver rdriver)

	{
		ldriver=rdriver;
		PageFactory.initElements(rdriver, this);


	}

	@FindBy(xpath="//*[@id=\"topcartlink\"]/a/span[1]")
	@CacheLookup
	WebElement shopcartLink;

	@FindBy(xpath="//button[@id='checkout']")
	@CacheLookup
	WebElement checkout;

	@FindBy(xpath="//input[@id='termsofservice']")
	@CacheLookup
	WebElement termsagree;


	public void ClickOnShopcartlink()
	{
		clickOn(shopcartLink, "click on the shop cart link");
	}

	public void Checkout()
	{
		clickOn(checkout, "click on the checkout button");
	}

	public void Checktheagreeterms()
	{
		clickOn(termsagree, "click  into the sign in terms & agrements");
	}


}
