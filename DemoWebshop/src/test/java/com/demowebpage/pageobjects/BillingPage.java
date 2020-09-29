package com.demowebpage.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.demowebpage.testcases.Baseclass;

public class BillingPage extends Baseclass {

	WebDriver ldriver;

	public BillingPage(WebDriver rdriver)
	{
		ldriver=rdriver;
		PageFactory.initElements(rdriver, this);


	}

	@FindBy(xpath="//select[@name='billing_address_id']")
	@CacheLookup
	WebElement selectbillingadress;

	@FindBy(xpath="//input[@class='text-box single-line'][@id='BillingNewAddress_Company']")
	@CacheLookup
	WebElement company;

	@FindBy(xpath="//select[@name='BillingNewAddress.CountryId']")
	@CacheLookup
	WebElement country;

	@FindBy(xpath="//input[@class='text-box single-line'][@id='BillingNewAddress_City']")
	@CacheLookup
	WebElement city;

	@FindBy(xpath="//input[@id='BillingNewAddress_Address1']")
	@CacheLookup
	WebElement adress1;

	@FindBy(xpath="//input[@name='BillingNewAddress.Address2']")
	@CacheLookup
	WebElement adress2;

	@FindBy(xpath="//input[@id='BillingNewAddress_ZipPostalCode']")
	@CacheLookup
	WebElement postalcode;

	@FindBy(xpath="//input[@id='BillingNewAddress_PhoneNumber']")
	@CacheLookup
	WebElement phonenumber;

	@FindBy(xpath="//input[@title='Continue'][@class='button-1 new-address-next-step-button'][@onclick='Billing.save()']")
	@CacheLookup
	WebElement contbutton;

	public void billing()
	{
		selectByVisisbleText(selectbillingadress, "select billing adress from dropdown menu", "New Address");

	}

	public void entercompanyname()
	{
		enterText(company, "PLANIT");
	}

	public void entercountryname()
	{
		selectByVisisbleText(country, "select country from dropdown menu", "Canada");
	}

	public void city()
	{
		enterText(city,"HYDERABAD");

	}

	public void enteradress1()
	{
		enterText(adress1,"RAM NAGAR");

	}

	public void enteradress2()
	{
		enterText(adress2,"1/342");

	}

	public void enterpostal()
	{
		enterText(postalcode,"515001");

	}

	public void phone()
	{
		enterText(phonenumber,"7032501797");

	}

	public void continueButton()
	{
		clickOn(contbutton,"click on the  continue button in billing page ");

	}
}
