/*********************************************
 * FileName: Main.Java
 * Programmer: Marcin Czajkowski
 * Purpose: To present techniques of controlling a webpage in Java environment
 *********************************************/
package marcin.selenium.test.parkcalc;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class Main {
	
	public static void main(String[] args) {

		//****Create a FireFox web driver****
		WebDriver driver = new FirefoxDriver();
		
		//****Web page under test: http://adam.goucher.ca/parkcalc/****
		String getWebAddress = "http://adam.goucher.ca/parkcalc/";
		//Display the selected web address - debugging
		//System.out.println(getWebAddress);
		
		//****Navigate to the web page under test****
		driver.get(getWebAddress); 
		//Maximize window
		driver.manage().window().maximize(); 
		
		//****Setting up general variables****
		//Lot type
		Select lotType = new Select(driver.findElement(By.id("Lot")));
		//Parking entry time and date inputs
		WebElement entryTime = driver.findElement(By.id("EntryTime"));
		List<WebElement> entryAMPM = driver.findElements(By.name("EntryTimeAMPM"));
		WebElement entryDate = driver.findElement(By.id("EntryDate"));
		WebElement entryCalendar = driver.findElement(By.cssSelector("a[href*='EntryDate'"));
		//Parking entry time and date inputs
		WebElement exitTime = driver.findElement(By.id("ExitTime"));
		List<WebElement> exitAMPM = driver.findElements(By.name("ExitTimeAMPM"));
		WebElement exitDate = driver.findElement(By.id("ExitDate"));
		WebElement exitCalendar = driver.findElement(By.cssSelector("a[href*='ExitDate'"));
		//Form submit button
		WebElement submitButton = driver.findElement(By.name("Submit"));
		
		//----Test case - happy path submission----
		//****Setup entry time and date**** 
		lotType.selectByIndex(3);
		entryTime.clear(); //clear the text field or the sendKeys will append
		entryTime.sendKeys("03:00"); //set entry time
		entryAMPM.get(1).click(); //0 for AM, 1 for PM
		//Parking entry date - manual calendar entry MM/DD/YYYY
		entryDate.clear(); //clear the text box
		entryDate.sendKeys("02/11/2016"); //enter date
		
		//****Setup exit time and date****
		exitTime.clear(); //clear the text box
		exitTime.sendKeys("01:30"); //set time
		exitAMPM.get(0); //set for AM 
		//Parking exit date - entering date through the java script calendar 
		exitCalendar.click(); //invoke pop up calendar
		Set<String> allPopUpsHandles = driver.getWindowHandles(); //get the set of all current windows/tabs
		Iterator<String> itrPopUpsHandles = allPopUpsHandles.iterator();
		String mainWindow = itrPopUpsHandles.next(); //first 'next' - get handle of the parent window
		String calendarWindow = itrPopUpsHandles.next(); //second 'next' - get handle of the calendar window
		driver.switchTo().window(calendarWindow); //switch driver to calendar window
		//Choose a month
		Select calendarMonth = new Select(driver.findElement(By.name("MonthSelector")));
		calendarMonth.selectByIndex(1); //February index 1 (January index 0)
		//Choose a year, target 2020
		int targetExitYear = 2020;
		//Absolute XPath example. CSS equivalent would be: "html body form table tbody tr td table tbody tr td a b font"
		int currentExitYear = Integer.parseInt(driver.findElement(By.xpath("/html/body/form/table/tbody/tr[1]/td/table/tbody/tr/td[2]/font/b")).getText());
		int yearDif = targetExitYear - currentExitYear;
		for (int i = 0 ; i < yearDif ; i++){
			WebElement incYear = driver.findElement(By.cssSelector("a[href*='IncYear'"));  //handle to increment a year in calendar 
			incYear.click();
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS); //wait for the javascript to increment the year in calendar
		}
		//Click on a day in a month to close the calendar and enter the date to text field
		int exitDay = 11;
		WebElement baseTable = driver.findElement(By.cssSelector("table"));
		List<WebElement> tableRows = baseTable.findElements(By.cssSelector("a[href*='window.close'"));
		for (int index = 0 ; index < tableRows.size() ; index++ ){
			try{
				int calendarDay = Integer.parseInt(tableRows.get(index).getText());
				if (calendarDay == exitDay){
					tableRows.get(index).click(); 
					break;
				}
			}
			catch(Exception e){
				//System.out.println(e.toString()); //Not-a-number found
			}
		}
		
		//****Submit the requisition and gather results 
		driver.switchTo().window(mainWindow); //return webdriver handle to main window
		submitButton.click(); //submit the entry and exit times
		//Results/error message can be found under the following tag
		String returnMessage = driver.findElement(By.cssSelector("span.SubHead")).getText();
		System.out.println("Form submission return: " +  returnMessage);
		
		//Cleanup
		driver.close(); //close the webpage and release the webdriver
		
	}

}