package marcin.selenium.test.parkcalc;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class Main {
	
	public static void main(String[] args) {

		//****Create a FireFox web driver****
		WebDriver driver = new FirefoxDriver();
		
		//****Ask user for the web address - default for ParkCalc****
		Scanner reader = new Scanner(System.in);
		//System.out.println("Enter web page: ");
		//String getWebAddress = reader.nextLine();
		//if (getWebAddress.equals("default"))
		//{
		//	getWebAddress = "http://adam.goucher.ca/parkcalc/";
		//}
		
		//****Web page under test: http://adam.goucher.ca/parkcalc/****
		String getWebAddress = "http://adam.goucher.ca/parkcalc/";
		//Display the selected web address - debugging
		System.out.println(getWebAddress);
		
		//****Navigate to the web page under test****
		driver.get(getWebAddress); 
		//Maximize window
		driver.manage().window().maximize(); 
		
		//****Setting up variables****
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
		
		//****Test case 1 - happy path****
		lotType.selectByIndex(3);
		entryTime.clear(); //clear the text field or the sendKeys will append
		entryTime.sendKeys("03:00");
		entryAMPM.get(1).click(); //0 for AM, 1 for PM
		//Parking entry date - manual calendar entry MM/DD/YYYY
		entryDate.clear(); //clear the text box
		entryDate.sendKeys("02/11/2016");
		//Parking entry date - entering date through the java script calendar 
		exitCalendar.click(); //click to pop up calendars
		Set<String> allPopUpsHandles = driver.getWindowHandles(); //get the set of all current windows/tabs
		Iterator<String> itrPopUpsHandles = allPopUpsHandles.iterator();
		String mainWindow = itrPopUpsHandles.next(); //first 'next' - get handle of the parent window
		String calendarWindow = itrPopUpsHandles.next(); //second 'next' - get handle of the calendar window
		driver.switchTo().window(calendarWindow); //switch driver to calendar window
		//Choose a month
		Select calendarMonth = new Select(driver.findElement(By.name("MonthSelector")));
		calendarMonth.selectByIndex(2); //February (2)
		
		
		System.out.println("Current webpage handles:");
		System.out.println("Parent: " + mainWindow);
		System.out.println("Calendar: " + calendarWindow);
		//String pressEnter = reader.nextLine();
		
		//Cleanup
		//reader.close();
		//driver.close();
		
	}

}
