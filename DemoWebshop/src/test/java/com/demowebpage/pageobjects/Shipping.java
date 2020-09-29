package com.demowebpage.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.demowebpage.testcases.Baseclass;

public class Shipping extends Baseclass{

	WebDriver ldriver;

	public Shipping(WebDriver rdriver)
	{
		ldriver=rdriver;
		PageFactory.initElements(rdriver, this);


	}
	//li[contains(text(),'Order number')]

	@FindBy(xpath="//strong[text()='Your order has been successfully processed!']")
	@CacheLookup
	WebElement orderStatus;
	@FindBy(xpath="//li[contains(text(),'Order number')]")
	@CacheLookup
	WebElement ordernnumber;
	@FindBy(xpath="//select[@name='shipping_address_id']")
	@CacheLookup
	WebElement shipping;

	@FindBy(xpath="//input[@type='button'][@class='button-1 new-address-next-step-button'][@value='Continue']  \r\n" + 
			"\r\n" + 
			"[@onclick='Shipping.save()']")
	@CacheLookup
	WebElement cont1;

	@FindBy(xpath="//input[@id='shippingoption_1']")
	@CacheLookup
	WebElement Dayair;

	@FindBy(xpath="//input[@class='button-1 shipping-method-next-step-button']")
	@CacheLookup
	WebElement cont2;

	@FindBy(xpath="//input[@class='button-1 payment-method-next-step-button']")
	@CacheLookup
	WebElement cont3;

	@FindBy(xpath="//input[@class='button-1 payment-info-next-step-button']")
	@CacheLookup
	WebElement cont4;

	@FindBy(xpath="//input[@class='button-1 confirm-order-next-step-button']")
	@CacheLookup
	WebElement confirm;


	public void shippingdrop()
	{
		/*
		 * Select select = new Select(shipping); 
		 * select.selectByIndex(12);
		 */
		selectByIndex(shipping, "", 12);

	}

	public void click1()
	{
		clickOn(cont1, "click on the continue button");


	}

	public void DOR()
	{
		clickOn(Dayair, "click on the DOR radio button from the payment method");

	}

	public void click2()
	{
		clickOn(cont2, "click on the next continue button to proceed");

	}

	public void click3()
	{
		clickOn(cont3, "click on the next continue button");

	}

	public void click4()
	{
		clickOn(cont4, "click on next continue");

	}

	public void finalconfirm()
	{
		clickOn(confirm, "finally confirm the order");

	}
	public void verifyOrdeSstatus() {
		waitForElementPresent(orderStatus);
		if (isElementPresent(orderStatus)) {
			System.out.println("Status of Order" +orderStatus.getText());
		}
		else {
			System.out.println("Status of order failed");
		}
	}
	public void Ordernumber() {
		waitForElementPresent(ordernnumber);
		if (isElementPresent(ordernnumber)) {

			String OrderText=ordernnumber.getText();
			String [] orderNumber=OrderText.split(":");
			System.out.println("orderNumber " +orderNumber[1].trim());
		}
		else {
			System.out.println("Status of order failed");
		}
	}

}
