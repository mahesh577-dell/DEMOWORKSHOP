package com.demowebpage.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.demowebpage.testcases.Baseclass;

public class Books extends Baseclass {



	WebDriver ldriver;

	public Books(WebDriver rdriver)
	{
		ldriver=rdriver;
		PageFactory.initElements(rdriver, this);


	}
	//books link xpath
	///html/body/div[4]/div[1]/div[4]/div[1]/div[1]/div[2]/ul/li[1]/a
	@FindBy(xpath="//div[@class='listbox']//li/a[contains(text(),'Books')]")
	@CacheLookup
	WebElement BookLink;

	//health care book xpath
	@FindBy(xpath="//img[@alt='Picture of Health Book']")
	@CacheLookup
	WebElement SelectBook;

	@FindBy(xpath="//input[@type='button'][@id='add-to-cart-button-22']")
	@CacheLookup
	WebElement addcart;

	/*	@FindBy(xpath="//input[@class='qty-input valid']")
		@CacheLookup
		WebElement QuantityEnter;*/

	public void ClickBooksLink()
	{
		clickOn(BookLink, "click on bookslink from the books categeory");
	}

	public void SelectBook()
	{
		clickOn(SelectBook, "click the book from the list displayed");
	}

	public void AddItem() 
	{
		clickOn(addcart, "Add to cart button click");
	}


}