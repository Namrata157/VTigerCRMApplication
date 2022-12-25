package genericUtilityImplementation;

import java.time.Duration;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
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

public class CreateNewLead {

	public static void main(String[] args) throws InterruptedException {
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
		
		driver.findElement(By.xpath("//a[.='Leads']")).click();
		
		if(driver.getTitle().contains("Leads "))
			System.out.println("Pass:Leads page is Displayed");
		else
			System.out.println("Fail:Leads page is not Displayed");
		
		driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();
		
		String createLeadHeader = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		if(createLeadHeader.contains("Creating New Lead"))
			System.out.println("Pass:Creating new Lead page is Displayed");
		else
			System.out.println("Fail:Creating new Lead page is not Displayed");
		
		Map<String, String> map = excel.fetchMultipleDataBasedOnKeyFromExcel("TestData", "Create Lead");
		WebElement salutation = driver.findElement(By.name("salutationtype"));
		webdriver.dropDown(salutation,map.get("First Name Salutation"));
		
		String lastname = map.get("Last Name")+java.generateRandomNumber(100);
		driver.findElement(By.name("lastname")).sendKeys(lastname);
		driver.findElement(By.name("company")).sendKeys(map.get("Company"));
		driver.findElement(By.xpath(" //input[contains(@value,'Save')]")).click();
		
		String leadInfoPageHeader = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if (leadInfoPageHeader.contains(lastname))
			System.out.println("Pass :New lead created successfully");
		else
			System.out.println("Fail :lead is not created");
		
		driver.findElement(By.xpath("//input[@title='Duplicate [Alt+U]']")).click();
		String duplicatePage = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		if (duplicatePage.contains(lastname))
			System.out.println("Pass :Duplicate page is displayed");
		else
			System.out.println("Fail :Duplicate page is not displayed");
		
		String newlastname = map.get("New Last Name")+java.generateRandomNumber(100);
		driver.findElement(By.name("lastname")).clear();
		driver.findElement(By.name("lastname")).sendKeys(newlastname);
		driver.findElement(By.xpath(" //input[contains(@value,'Save')]")).click();
		
		String leadInfoPage = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if (leadInfoPage.contains(newlastname))
			System.out.println("Pass :New lead created successfully");
		else
			System.out.println("Fail :lead is not created");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@class='hdrLink']")).click();
		String LeadPageHeader = driver.findElement(By.xpath("//a[@href='index.php?action=ListView&module=Leads&parenttab=Marketing']")).getText();
		if (LeadPageHeader.contains("Leads"))
			System.out.println("Pass :Leads page is Displayed");
		else
			System.out.println("Fail :Leads page is not Displayed");
		
		String lead = driver.findElement(By.xpath("//table[@class='lvt small']/tbody/tr[last()]/td[3]/a")).getText();
		if(lead.equals(newlastname))
		{
			System.out.println("Test case Passed");
			excel.writeDataIntoExcel("TestData", "Pass",IConstantPath.EXCEL_FILE_PATH, "Create Lead");
		}
		else
		{
			System.out.println("Test case Failed");
			excel.writeDataIntoExcel("TestData", "Fail",IConstantPath.EXCEL_FILE_PATH, "Create Lead");
		}
		
		WebElement Administrator = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		webdriver.mouseHoverToElement(Administrator);
		driver.findElement(By.xpath("//a[.='Sign Out']")).click();
		
		webdriver.closeBrowser();
		excel.closeExcel();
	}

}
