package genericUtilityImplementation;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import genericLibraries.ExcelUtility;
import genericLibraries.IConstantPath;
import genericLibraries.JavaUtility;
import genericLibraries.PropertyFileUtility;
import genericLibraries.WebDriverUtility;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateNewContact {

	public static void main(String[] args) throws InterruptedException, IOException {
		JavaUtility java=new JavaUtility();
		WebDriverUtility webdriver=new WebDriverUtility();
		
		PropertyFileUtility property=new PropertyFileUtility();
		property.propertyFileInitialization(IConstantPath.PROPERTY_FILE_PATH);
		
		ExcelUtility excel=new ExcelUtility();
		excel.excelFileInitialization(IConstantPath.EXCEL_FILE_PATH);
		
		String url = property.getDataFromPropertyFile("url");
		String username=property.getDataFromPropertyFile("username");
		String password=property.getDataFromPropertyFile("password");
		String browser=property.getDataFromPropertyFile("browser");
		long time=Long.parseLong(property.getDataFromPropertyFile("timeouts"));
		
		WebDriver driver = webdriver.openBrowserAndApplication(browser, url, time);
		
		if(driver.getTitle().contains("vtiger"))
			System.out.println("Pass:Vtiger Login page is Displayed");
		else
			System.out.println("Fail:Vtiger Login page is not Displayed");
		
		driver.findElement(By.name("user_name")).sendKeys(username);
		driver.findElement(By.name("user_password")).sendKeys(password);
		driver.findElement(By.id("submitButton")).click();
		
		if(driver.getTitle().contains("Administrator"))
			System.out.println("Pass:Vtiger Login successful");
		else
			System.out.println("Fail:Vtiger Login not successful");
		
		driver.findElement(By.xpath("//a[.='Contacts']")).click();
		if(driver.getTitle().contains("Contacts "))
			System.out.println("Pass:Contacts page is Displayed");
		else
			System.out.println("Fail:Contacts page is not Displayed");
		
		driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();
		
		String createContactHeader = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		if(createContactHeader.contains("Creating New Contact"))
			System.out.println("Pass:Creating new Contact page is Displayed");
		else
			System.out.println("Fail:Creating new Contact page is not Displayed");
		
		Map<String, String> map = excel.fetchMultipleDataBasedOnKeyFromExcel("TestData", "Create Contact");
		
		WebElement salutation = driver.findElement(By.name("salutationtype"));
		webdriver.dropDown(salutation, map.get("First Name Salutation"));
		
		String lastname =map.get("Last Name")+java.generateRandomNumber(100);
		driver.findElement(By.name("lastname")).sendKeys(lastname);
		
		driver.findElement(By.xpath("//img[@title='Select']")).click();
		
		String parent = webdriver.getParentWindow();
		webdriver.handleChildBrowserPopup("Accounts&action");
	
		String requiredOrganisationName = map.get("Organization Name");
		List<WebElement> contents = driver.findElements(By.xpath("//div[@id='ListViewContents']/descendant::table[@cellpadding='5']/tbody/tr"));
		for(int i=2;i<=contents.size();i++)
		{
			WebElement organisation=driver.findElement(By.xpath("//div[@id='ListViewContents']/descendant::table[@cellpadding='5']/tbody/tr["+i+"]/td[1]/a"));
			String organisationName = organisation.getText();
			if(organisationName.equals(requiredOrganisationName))
			{
				organisation.click();
				break;
			}
		}	
		webdriver.switchToWindow(parent);
		
		WebElement contactImage = driver.findElement(By.xpath("//input[@name='imagename']"));
		contactImage.sendKeys(map.get("Contact Image"));
		driver.findElement(By.xpath("//input[contains(@value,'Save')]")).click();
		Thread.sleep(3000);
		
		String contactInfoPageHeader = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if (contactInfoPageHeader.contains(lastname))
			System.out.println("Pass :New contact created successfully");
		else
			System.out.println("Fail :contact is not created");
		
		driver.findElement(By.xpath("//a[@class='hdrLink']")).click();
		
		String contactPageHeader = driver.findElement(By.xpath("//a[@href='index.php?action=ListView&module=Contacts&parenttab=Marketing']")).getText();
		if (contactPageHeader.contains("Contacts"))
			System.out.println("Pass :Contacts page is Displayed");
		else
			System.out.println("Fail :Contacts page is not Displayed");
		
		String contact = driver.findElement(By.xpath("//table[@class='lvt small']/tbody/tr[last()]/td[4]/a")).getText();
		if(contact.equals(lastname))
		{
			System.out.println("Test case Passed");
			excel.writeDataIntoExcel("TestData", "Pass",IConstantPath.EXCEL_FILE_PATH, "Create Contact");
		}
		else
		{
			System.out.println("Test case Failed");
			excel.writeDataIntoExcel("TestData", "Fail",IConstantPath.EXCEL_FILE_PATH, "Create Contact");
		}
		WebElement Administrator = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		webdriver.mouseHoverToElement(Administrator);
		driver.findElement(By.xpath("//a[.='Sign Out']")).click();
		
		webdriver.closeBrowser();
		excel.closeExcel();
	}

}
