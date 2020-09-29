package com.demowebpage.testcases;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;

import org.testng.annotations.BeforeSuite;

import com.demowebpage.utilities.ReadConfig;

import freemarker.log.Logger;

public class Baseclass {

	ReadConfig configwe = new ReadConfig();
	public String baseurl=configwe.getapplicationurl();
	public String username=configwe.getusername();
	public String password=configwe.getpassword();
	public static WebDriver driver;
	public static Logger logger;
	public int elementloadWaitTime=20;




	@BeforeSuite
	public void setup()
	{

		logger = Logger.getLogger("ebanking");
		PropertyConfigurator.configure("Log4j.properties");

		System.setProperty("webdriver.chrome.driver", configwe.getchromepath());
		driver = new ChromeDriver();

		logger.info("CHROME DRIVER LAUNCHED");


		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		driver.manage().window().maximize();
		logger.info("CHROME DRIVER MAXIMIZED");


	}

	@AfterSuite
	public void teardown()
	{
		driver.close();
		logger.info("CHROME DRIVER CLOSED");

	}

	/**
	 * Check if the element is present in the page
	 * @param element WebElement need to check
	 * @return True/False
	 */
	protected boolean isElementPresent(WebElement element){
		try{
			new WebDriverWait(driver, 2).until(ExpectedConditions
					.elementToBeClickable(element));
			if(element.isDisplayed()){
				return true;
			}else{
				return false;
			}
		}catch(Exception ex){
			return false;		
		}
	}


	/**
	 * Check if the element is present in the page
	 * @param Element locator of type By
	 * @return True/False
	 */
	public boolean isElementPresent(By by){
		try{
			new WebDriverWait(driver, 2).until(ExpectedConditions
					.elementToBeClickable(by));
			if(driver.findElement(by).isDisplayed()){
				return true;
			}else{
				return false;
			}
		}catch(Exception ex){
			return false;		
		}
	}


	/**
	 * Check if the element is present in the page and report
	 * @param element
	 * @param Name of the Element
	 * @param Name of the page
	 */
	protected void isElementPresentReport(WebElement element,String elemName,String pageName) {
		waitForIsClickable(element);
		if(isElementPresent(element)){
			logger.info(elemName + "Element is displayed in "+pageName+" page" );
		}else{
			logger.info(elemName + "Element is not displayed in "+pageName+" page" );
		}
	}


	/***
	 * Method to switch to child window
	 * @param : parentWindow
	 ***/
	public void navigateToWindowWithPageTitle(String pageTitle,int expectedNumberOfWindows) {
		boolean blnNavigate=false;
		try{				
			Set<String> handles = driver.getWindowHandles();			
			if(waitForNewWindow(expectedNumberOfWindows)){
				for (String windowHandle : handles) {					
					String strActTitle = driver.switchTo().window(windowHandle).getTitle();
					if(strActTitle.contains(pageTitle)){
						blnNavigate = true;
						driver.manage().window().maximize();
						sleep(5000);	

						logger.info("Navigated to the page -"+pageTitle+"- successfully");	
						break;
					}					
				}
				if(!blnNavigate){
					logger.info("Unable to Navigate to the page -"+pageTitle);
				}
			}else{
				logger.info("New window the with page Title "+pageTitle+" is not loaded");
			}
		}
		catch(RuntimeException ex){
			logger.info("Unable to Navigate to the page -"+pageTitle+" Exception is->"+ex.getMessage());
		}
	}

	/***
	 * Method to switch to parent window
	 * @param : parentWindow
	 ***/
	public void navigatoToParentWindow(String parentWindow) {
		try{
			driver.switchTo().window(parentWindow);
		}catch(Exception ex){
			logger.info("Unable to Navigate to Parent Window");
		}
	}

	public void jsmoveToElement(WebElement elem){
		try {
			String str = elem.toString();
			if(isElementPresent(elem)){
				String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript(mouseOverScript, elem);
			}else{
				logger.info("Element is not displayed to mousehover ->"+str);
			}
		}catch(Exception ex){
			logger.info("Unable to mouse hover on the element,Exception is->"+ex.getMessage());
		}
	}

	/***
	 * Method to close a webpage
	 * @return      : 
	 ***/
	public void closeCurrentPage(){
		String str=null;
		try {
			driver.getTitle();
			driver.close();
			sleep(1000);			
			Set<String> windows=driver.getWindowHandles();
			for(String window:windows){
				driver.switchTo().window(window);
			}
			sleep(5000);
			logger.info("Closed the current page with title->"+str);
		} catch (Exception e) {
			logger.info("Unable to Close the current page with title->"+str);
		}
	}


	//*****************************************************************************************************************//
	//Start Alert pop ups
	//*****************************************************************************************************************//


	/***
	 * Method to accept and close alert and return the text within the alert
	 * @return :alert message
	 ***/
	public String closeAlertAndReturnText(){
		String alertMessage=null;
		try{		
			if(waitForAlert()){
				Alert alert = driver.switchTo().alert();
				alertMessage=alert.getText();			
				alert.accept();
				logger.info("Closed the alert successfully with text->"+alertMessage);
			}
		}catch(Exception Ex){
			logger.info("Exception Caught while accepting the alert, Message is->"+Ex.getMessage());
		}
		return alertMessage;
	}


	/***
	 * Method to check for an alert for 20 seconds
	 * @param       : Element Name
	 * @return      : 
	 * Modified By  :  
	 ***/

	public boolean isAlertPresent(){
		try{
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		}catch(Exception e){			
			return false;
		}
	}

	//*****************************************************************************************************************//
	//End Alert pop ups
	//*****************************************************************************************************************//


	//*****************************************************************************************************************//
	//Start wait
	//*****************************************************************************************************************//


	/**
	 * Method to wait for element to load in the page
	 * @param WebElement
	 */
	protected Boolean waitForIsClickable(WebElement we) {
		String str = null;
		try {
			str = we.toString();
			new WebDriverWait(driver, elementloadWaitTime).until(ExpectedConditions
					.elementToBeClickable(we));			
			if(isElementPresent(we)){
				return true;
			}else{
				logger.info("Element is not visible after waiting for elementloadWaitTime + Seconds");
				return false;
			}			
		} catch (Exception ex) {
			logger.info("Element is not visible after waiting for "+elementloadWaitTime +" Seconds, : "+str);			
			return false;
		}    	
	}


	/**
	 * Method to wait for element to load in the page
	 * @param WebElement
	 */
	protected Boolean waitForElementPresent(WebElement we) {
		String str = null;
		try {
			str = we.toString();
			new WebDriverWait(driver, elementloadWaitTime).until(ExpectedConditions
					.visibilityOf(we));			
			if(isElementPresent(we)){
				return true;
			}else{
				logger.info("Element is not visible after waiting for "+elementloadWaitTime+" Seconds");
				return false;
			}			
		} catch (Exception ex) {
			logger.info("Element is not visible after waiting for "+elementloadWaitTime +" Seconds, : "+str);			
			return false;
		}    	
	}


	/**
	 * Method to wait for element to load in the page
	 * @param WebElement
	 */
	protected Boolean waitForElementPresent(By by) {
		String str = null;
		try {
			str = by.toString();
			new WebDriverWait(driver, elementloadWaitTime).until(ExpectedConditions
					.visibilityOfElementLocated(by));			
			if(isElementPresent(by)){
				return true;
			}else{
				logger.info("Element is not visible after waiting for "+elementloadWaitTime+" Seconds");
				return false;
			}			
		} catch (Exception ex) {
			logger.info("Element is not visible after waiting for "+elementloadWaitTime+" Seconds, : "+str);			
			return false;
		}    	
	}



	/**
	 * Method to wait for element to load in the page
	 * @param WebElement
	 */
	protected Boolean waitForIsClickable(By by) {
		String str = null;
		try {
			str = by.toString();
			new WebDriverWait(driver, elementloadWaitTime).until(ExpectedConditions
					.elementToBeClickable(by));			
			if(isElementPresent(by)){
				return true;
			}else{
				logger.info("Element is not visible after waiting for "+elementloadWaitTime+" Seconds");
				return false;
			}			
		} catch (Exception ex) {
			logger.info("Element is not visible after waiting for "+elementloadWaitTime+" Seconds, : "+str);			
			return false;
		}    	
	}



	/**
	 * Method to wait for element to load in the page
	 * @param by
	 */
	protected Boolean waitAndSwitchToFrame(By by) {
		String str = null;
		try {
			str = by.toString();
			new WebDriverWait(driver, elementloadWaitTime).until(ExpectedConditions
					.frameToBeAvailableAndSwitchToIt(by));
			return true;
		} catch (Exception ex) {
			logger.info("Frame is not displayed after waiting for "+elementloadWaitTime+" Seconds, : "+str);			
			return false;
		}    	
	}


	/**
	 * Method to wait for element to load in the page
	 * @param Frame Index
	 */
	protected Boolean waitAndSwitchToFrame(int intFrameNum) {		
		try {			
			new WebDriverWait(driver, elementloadWaitTime).until(ExpectedConditions
					.frameToBeAvailableAndSwitchToIt(intFrameNum));
			return true;
		} catch (Exception ex) {
			logger.info("Frame is not displayed after waiting for "+elementloadWaitTime+" Seconds, : Frame Index->"+intFrameNum);			
			return false;
		}    	
	}


	/**
	 * Method to wait for element to load in the page
	 * @param by
	 */
	protected Boolean waitAndSwitchToFrame(String strFrameName) {		
		try {			
			new WebDriverWait(driver, elementloadWaitTime).until(ExpectedConditions
					.frameToBeAvailableAndSwitchToIt(strFrameName));
			return true;
		} catch (Exception ex) {
			logger.info("Frame is not displayed after waiting for "+elementloadWaitTime+" Seconds, : Frame Name->"+strFrameName);			
			return false;
		}    	
	}

	/**
	 * Method to wait for element to load in the page
	 * @param WebElement
	 */
	protected Boolean waitAndSwitchToFrame(WebElement frame) {
		String str = null;
		try {
			str = frame.toString();
			new WebDriverWait(driver, elementloadWaitTime).until(ExpectedConditions
					.frameToBeAvailableAndSwitchToIt(frame));
			return true;
		} catch (Exception ex) {
			logger.info("Frame is not displayed after waiting for "+elementloadWaitTime+" Seconds, : "+str);			
			return false;
		}    	
	}


	/***
	 * Method to wait for the list of elements to be displayed
	 * @param       : List<WebElement>
	 * @return      : 
	 * Modified By  :  
	 ***/
	public boolean waitForElementList(List<WebElement> elems){
		try{
			WebDriverWait wait = new WebDriverWait(driver, elementloadWaitTime);
			wait.until(ExpectedConditions.visibilityOfAllElements(elems));			
			return true;
		}catch(Exception Ex){
			logger.info("Element List is not visible after waiting for "+elementloadWaitTime+" Seconds");
			return false;
		}
	}


	/**
	 * method to make a thread sleep for customized time in milliseconds
	 * @param milliseconds
	 */
	protected void sleep(int milliseconds){
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to wait for Alert present in the page
	 * @param 
	 */
	protected Boolean waitForAlert(){
		try{
			new WebDriverWait(driver, elementloadWaitTime).until(ExpectedConditions.alertIsPresent());
			return true;
		}catch(Exception Ex){
			logger.info("Alert is not displayed after waiting for "+elementloadWaitTime+" Seconds");
			return false;
		}
	}


	/**
	 * Method to wait for Alert present in the page
	 * @param 
	 */
	protected Boolean waitForNewWindow(int expectedNumberOfWindows){
		try{
			new WebDriverWait(driver, elementloadWaitTime).until(ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindows));
			return true;
		}catch(Exception Ex){
			logger.info("New "+expectedNumberOfWindows+"th Window is not displayed after waiting for "+elementloadWaitTime +" Seconds");
			return false;
		}
	}

	/***
	 * Method to wait till the page contains expected text
	 * @param       : txt
	 * @return      : 
	 * Modified By  :  
	 ***/
	public void waitForText(String txt)
	{
		waitForText(txt, elementloadWaitTime);
	}


	/***
	 * Method to wait till the page contains expected text
	 * @param       : txt,timeout
	 * @return      : 
	 * Modified By  :  
	 ***/
	public void waitForText(String txt, int timeout){
		for (int second = 0; second < timeout; second++){
			if (second == timeout - 1) {
				logger.info("The text '" + txt + "' is not found within " + elementloadWaitTime + " seconds timeout");
				break;
			}
			try{
				if (driver.getPageSource().contains(txt)) {
					logger.info("Text: '" + txt + "' is present");
				}
			}
			catch (Exception localException){
				sleep(1000);
			}
		}
	}


	public boolean waitForDropDown(WebElement weDropDown){
		try{
			String str= weDropDown.toString();
			if(waitForIsClickable(weDropDown)){		
				for (int second = 0;; second++) {
					if (second >= 20){
						logger.info("Values in dropdown are not loaded after waiting for 20 seconds");
						return false;
					}
					try { 
						Select droplist = new Select(weDropDown);
						if(!droplist.getOptions().isEmpty()){
							return true;						
						}
					} catch (Exception e) {
						logger.info("Exception Caught while waiting for dropdown loading,Message is->"+e.getMessage());
						return false;
					}
					sleep(1000);
				}
			}else{
				logger.info("Dropdown Element is not visible, Expected Property of DropDown is->"+str);
				return false;
			}
		}catch(Exception ex){
			logger.info("Exception Caught while waiting for dropdown loading,Message is->"+ex.getMessage());
			return false;
		}
	}

	//*****************************************************************************************************************//
	//End wait
	//*****************************************************************************************************************//


	//*****************************************************************************************************************//
	//Start Click 
	//*****************************************************************************************************************//

	/***
	 * Method to click on a link(WebElement button)
	 * @param : WebElement
	 * @param : Element Name
	 ***/
	public void clickOn(WebElement we,String elemName) {		
		try{
			waitForIsClickable(we);
			String strProp = we.toString();
			if (isElementPresent(we)){
				we.click();				
				logger.info("Clicked on WebElement-"+ elemName );	
			}else{
				logger.info("Unable to click on Element "+elemName+", Element with following property is not displayed->"+strProp);
			}
		}catch (Exception ex) {
			logger.info("Uanble to click on Element-"+ elemName +", Exception is->"+ex.getMessage());
		} 
	}


	/**
	 * Method to click on a link(WebElement link)
	 * @param : WebElement
	 * @param : Element Name
	 */
	protected void jsClick(WebElement we,String elemName) {		
		try{			
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",we);
			logger.info("Clicked on -"+ elemName +"- Element");			
		}catch (RuntimeException ex) {
			logger.info("Uanble to click on Element-"+ elemName +", Exception is->"+ex.getMessage());
		} 
	}

	/***
	 * Method to enter text in a textbox
	 * @param : WebElement - Textbox
	 * @param : Text to be entered
	 * @return :
	 ***/
	public boolean enterText(WebElement we,String text){
		try{
			waitForIsClickable(we);
			if(isElementPresent(we)){
				we.clear();
				we.sendKeys(text);
				return true;
			}else{
				logger.info("Element is not displayed, Unable to enter text->"+ text);
				return false;
			}
		}
		catch (RuntimeException ex) {			
			logger.info("Unable to enter text in the text field->"+ text);
			return false;
		} 
	}

	/***
	 * Method to clear text in a textbox
	 * 
	 * @param : Element Name
	 * @return :
	 ***/
	public boolean clearText(WebElement we){
		try{
			waitForIsClickable(we);
			if(isElementPresent(we)){
				we.clear();			
				return true;
			}else{
				logger.info("Element is not displayed, Unable to Clear text->");
				return false;
			}
		}catch(RuntimeException ex){
			logger.info("Unable to clear text in the text field");
			return false;
		}
	}


	/***
	 * Method to select the checkbox
	 * @param       : cbElement
	 * @return      : 
	 * Modified By  : 
	 ***/
	public boolean selectCheckBox(WebElement cbElement){
		waitForIsClickable(cbElement);
		if (isElementPresent(cbElement)){
			try{
				if (!cbElement.isSelected()){
					cbElement.click();
				}
				logger.info("Selected the Checkbox Successfully");
				return true;
			}catch (Exception e){
				logger.info("Unable to Select the checkbox->"+e.getMessage());
				return false;
			}
		}else{
			logger.info("Unable to Select the checkbox(Element is not displayed)");
			return false;
		}
	}


	/***
	 * Method to UnSelect the checkbox
	 * @param       : cbElement
	 * @return      : 
	 * Modified By  : 
	 ***/
	public boolean unSelectCheckBox(WebElement cbElement)
	{
		waitForIsClickable(cbElement);
		if (isElementPresent(cbElement)) {
			try{
				if (cbElement.isSelected()){
					cbElement.click();
				}
				logger.info("Unchecked the checkbox");
				return true;
			}catch (Exception e){
				logger.info("Unable to check the checkbox->"+e.getMessage());
				return false;
			}
		}else{
			logger.info("Unable to UnSelect the checkbox(Element is not displayed)");
			return false;
		}
	}

	/***
	 * Method to hover over an element
	 * @param       : weMainMenuElement,weSubMenuElement
	 * @return      : 
	 * Modified By  :  
	 ***/
	public void clickOnSubMenu(WebElement weMain,WebElement weSub ){		
		try{
			String strMain = weMain.toString();
			if(isElementPresent(weMain)){
				Actions action = new Actions(driver);
				action.moveToElement(weMain).click().perform();			
				logger.info("Hover over the Main menu item successfully");
			}else{
				logger.info("Unabel to hover Main menu(Element is not displayed), Expected Property of element is->"+strMain);
			}
		}catch(Exception Ex){
			logger.info("Exception Caught while hoverOver the main menu Item,Message is->"+Ex.getMessage());
		}
		try{
			String strSub = weSub.toString();
			waitForIsClickable(weSub);
			if(isElementPresent(weSub)){				
				weSub.click();
				logger.info("Clicked on the Sub menu item successfully");
			}else{
				logger.info("Sub Menu Element is not displayed, Expected Property of element is->"+strSub);
			}
		}catch(Exception ex){
			logger.info("Unable to Click on Sub menu Item,Exception is->"+ex.getMessage());
		}		
	}


	/***
	 * Method to hover over an element
	 * @param       : WebElement we
	 * @return      : 
	 * Modified By  :  
	 ***/
	public boolean moveToElement(WebElement we){				
		try {
			String strMain = we.toString();
			if(isElementPresent(we)){
				Actions action = new Actions(driver);
				action.moveToElement(we).build().perform();
				return true;
			}else{
				logger.info("Unable to move to element as element is not displayed, Expected Property of element is->"+strMain);
				return false;
			}
		} catch (Exception e) {
			logger.info("Error Occurred while Move to Element --> "+e.getMessage());
			return false;
		}
	}

	/***
	 * Method to drag and drop from source element to destination element
	 * @param       : weSource,weDestination
	 * @return      : 
	 * Modified By  :  
	 ***/
	public void dragAndDrop(WebElement weSource, WebElement weDestination)
	{	
		String strSource = weSource.toString();
		String strDest = weDestination.toString();
		if(!isElementPresent(weSource)){
			logger.info("Unable to perform DragAndDrop(Source element is not displayed), Expected Property of element is->"+strSource);
			return;
		}
		if(!isElementPresent(weDestination)){
			logger.info("Unable to perform DragAndDrop(Destination element is not displayed), Expected Property of element is->"+strSource);
			return;
		}
		try{	     
			new Actions(driver).dragAndDrop(weSource, weDestination).perform();			
			logger.info("Draged Source element and droped on Destination Element Successfully");
		}catch (Exception e){			
			logger.info("Exception Caught while performing DragAndDrop, Mesage is->"+e.getMessage());
		}
	}

	//*****************************************************************************************************************//
	//End Click 
	//*****************************************************************************************************************//


	/***
	 * Method to get current time in minutes
	 * @param : Element Name
	 * @return :
	 * Modified By :
	 ***/
	public int getTimeInMin (String time) {
		//String time=new SimpleDateFormat("HH:mm").format(new Date());
		String[] splitTime=time.split(":");
		int hr=Integer.parseInt(splitTime[0]);
		int mn=Integer.parseInt(splitTime[1].substring(0,2));
		if(hr>12){
			hr=hr-12;
		}
		int timStamp=(hr*60)+mn;
		return timStamp;
	}


	/***
	 * Method to switch to default content
	 * @param       : 
	 * @return      : 
	 * Modified By  : 
	 ***/
	public void UnSelectFrame()
	{
		try{
			logger.info("Switching to default content frame ");
			driver.switchTo().defaultContent();
			logger.info("Switched Back from frame to default page successfully");
		}catch (Exception e){
			logger.info("Exception caught while Switching back to default page from Frame, Message is->"+e.getMessage());
		}
	}


	/***
	 * Method to Select value from dropdown by visible text
	 * @param       : we,strElemName,strVisibleText
	 * @return      : 
	 * @author      : 
	 * Modified By  :  
	 ***/

	public void selectByVisisbleText(WebElement we,String strElemName,String strVisibleText){
		try{
			if(waitForDropDown(we)){
				Select sel = new Select(we);
				sel.selectByVisibleText(strVisibleText);
				logger.info("Selected value -"+strVisibleText +" from dropdown->"+strElemName);
			}
		}catch(Exception Ex){
			logger.info("Unable to select value from the dropdown "+Ex.getMessage());
		}
	}

	/***
	 * Method to Select value from dropdown by index
	 * @param       : we,strElemName,index
	 * @return      : 
	 * Modified By  :  
	 ***/

	public void selectByIndex(WebElement we,String strElemName,int index){
		try{
			if(waitForDropDown(we)){
				Select sel = new Select( we);
				sel.selectByIndex(index);
				logger.info("Selected "+index +"option from dropdown->"+strElemName);
			}
		}catch(Exception Ex){
			logger.info("Unable to select value from the dropdown "+Ex.getMessage());
		}
	}



	public void selectByVisisbleValue(WebElement we,String strElemName,String strVisibleValue){
		try{
			if(waitForDropDown(we)){
				Select sel = new Select( we);
				sel.selectByValue(strVisibleValue);
				logger.info("Selected value -"+strVisibleValue +" from dropdown->"+strElemName);
			}
		}catch(Exception Ex){
			logger.info("Unable to select value from the dropdown "+Ex.getMessage());
		}
	}
}
