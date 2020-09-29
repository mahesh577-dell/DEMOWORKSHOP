package com.demowebpage.testcases;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.Alert;
import org.testng.annotations.Test;

import com.demowebpage.pageobjects.BillingPage;
import com.demowebpage.pageobjects.Books;
import com.demowebpage.pageobjects.LogoutPage;
import com.demowebpage.pageobjects.Shipping;
import com.demowebpage.pageobjects.ShoppingCart;
import com.demowebpage.pageobjects.insideloginpage;
import com.demowebpage.pageobjects.loginpage;
import com.demowebpage.utilities.ExcelReader;

public class TC_001_WEBSHOPDEMO extends Baseclass {

	/*
	 * loginpage login = new loginpage(driver); insideloginpage insidelogin = new
	 * insideloginpage(driver); Books booklink = new Books(driver); ShoppingCart
	 * shopcartlink = new ShoppingCart(driver); BillingPage billing = new
	 * BillingPage(driver); Shipping shipcart=new Shipping(driver); LogoutPage
	 * logoutlink = new LogoutPage(driver);
	 */

	@Test(priority=1)
	public void Login()
	{
		logger.info("LAUNCH THE WEBSITE URL");
		driver.get(baseurl);
		logger.info("WEB SHOP DEMO SITE LAUNCHED");
		loginpage login = new loginpage(driver);

		logger.info("CLICK ON THE LOGIN LINK");
		login.Login();	
		logger.info("LOGIN LINK CLICKED");



		login.signintextvalidation();


	}

	/*@Test(dataProvider="LoginData")
	public void loginDDT(String user,String pwd) throws InterruptedException
	{
		loginPage lp =new loginPage(driver);
		lp.setUserName(user);
		logger.info("user name provided");
		lp.setPassword(pwd);
		logger.info("password provided");
		lp.clickSubmit();

		@DataProvider(name="LoginData")
		String [][] getData() throws IOException
		{
			String path=System.getProperty("C:\\Users\\Public\\enetbanking\\src\\test\\java\\com\\enetbanking\\testdata\\LoginData.xlsx");

			int rownum=ExcelReader.getRowCount(path, "Sheet1");
			int colcount=ExcelReader.getCellCount(path,"Sheet1",1);

			String logindata[][]=new String[rownum][colcount];

			for(int i=1;i<=rownum;i++)
			{
				for(int j=0;j<colcount;j++)
				{
					logindata[i-1][j]=ExcelReader.getCellData(path,"Sheet1", i,j);//1 0
				}

			}

		}


	}*/
	@Test(priority=2,dependsOnMethods="Login")
	public void submit()

	{

		insideloginpage insidelogin = new insideloginpage(driver);


		insidelogin.UserName(username);
		logger.info("USER NAME ENTERED");
		insidelogin.Password(password);
		logger.info("PASSWORD ENTERED");
		insidelogin.submit();
		logger.info("SUBMIT BUTTON CLICKED");

	}

	@Test(priority=3,dependsOnMethods="submit")

	public void BooksLink()
	{
		Books booklink = new Books(driver);
		booklink.ClickBooksLink();

		logger.info("BOOKS LINK FROM CATEGERIES SELECTED");


		booklink.SelectBook();

		logger.info("RESPECTIVE BOOK SELECTED FROM THE DISPLAYED BOOKS");

		booklink.AddItem();

		logger.info("ITEM ADDED TO THE CART");
	}


	@Test(priority=4,dependsOnMethods="BooksLink")
	public void clickableshopcart()
	{
		ShoppingCart shopcartlink = new ShoppingCart(driver);
		shopcartlink.ClickOnShopcartlink();

		shopcartlink.Checktheagreeterms();

		shopcartlink.Checkout();


	}

	@Test(priority=5,dependsOnMethods="clickableshopcart")
	public void billing()
	{
		BillingPage billing = new BillingPage(driver);
		billing.billing();
		billing.entercompanyname();
		billing.entercountryname();
		billing.city();
		billing.enteradress1();
		billing.enteradress2();
		billing.enterpostal();
		billing.phone();
		billing.continueButton();
	}

	@Test(priority=6,dependsOnMethods="billing")
	public void shippingfinal() {
		Shipping shipcart=new Shipping(driver);
		shipcart.shippingdrop();
		shipcart.click1();
		shipcart.DOR();
		shipcart.click2();
		shipcart.click3();
		shipcart.click4();

		shipcart.finalconfirm();
		shipcart.Ordernumber();
		shipcart.verifyOrdeSstatus();
	}

	@Test(priority=7,dependsOnMethods="shippingfinal")
	public void Logout()
	{
		LogoutPage logoutlink = new LogoutPage(driver);
		logoutlink.Loggingout();

	}
	{

	}
}