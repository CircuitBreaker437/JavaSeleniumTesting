package marcin.selenium.test.parkcalc;

import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Main {
	
	public static void main(String[] args) {

		//Create a FireFox web driver
		WebDriver driver = new FirefoxDriver();

		
		//Ask user for the web address - default for ParkCalc
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter webapge: ");
		String getWebAddress = reader.nextLine();
		if (getWebAddress.equals("default"))
		{
			getWebAddress = "http://adam.goucher.ca/parkcalc/";
		}
		
		//Display the selected web address - debugging
		System.out.println(getWebAddress);
		
		//navigate to the address
		driver.get(getWebAddress);
		//Maximize window
		driver.manage().window().maximize(); 
		
		//Lot type
		WebElement lotType = driver.findElement(By.id("Lot"));
		
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
		
		
		
		//Cleanup
		reader.close();
		driver.close();
		
	}

}
