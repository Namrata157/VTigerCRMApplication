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

public class CreateNewOrganisation {

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
		
		driver.findElement(By.xpath("//a[.='Organizations']")).click();
		
		if(driver.getTitle().contains("Organizations"))
			System.out.println("Pass:Organizations page is Displayed");
		else
			System.out.println("Fail:Organizations page is not Displayed");
		
		driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();
		
		String createOrganizationHeader = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		if(createOrganizationHeader.contains("Creating New Organization"))
			System.out.println("Pass:Creating new Organization page is Displayed");
		else
			System.out.println("Fail:Creating new Organization page is not Displayed");
		
		Map<String, String> map = excel.fetchMultipleDataBasedOnKeyFromExcel("TestData","Create Organization");
		String organisationName = map.get("Organization Name")+java.generateRandomNumber(100);
		driver.findElement(By.name("accountname")).sendKeys(organisationName);	
		
		WebElement industryDropdown = driver.findElement(By.name("industry"));
		webdriver.dropDown(industryDropdown, map.get("Industry"));
		driver.findElement(By.xpath("//input[@value='T']")).click();
		
		WebElement groupdropdown = driver.findElement(By.name("assigned_group_id"));
		webdriver.dropDown(groupdropdown, map.get("Group"));
		driver.findElement(By.name("button")).click();
		Thread.sleep(2000);
		String organizationInfoPageHeader = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if (organizationInfoPageHeader.contains(organisationName))
			System.out.println("Pass :New organization created successfully");
		else
			System.out.println("Fail :Organization is not created");
		
		driver.findElement(By.xpath("//a[@href='index.php?action=ListView&module=Accounts&parenttab=Marketing']")).click();
		String organizationsPageHeader = driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		if (organizationsPageHeader.contains("Organizations"))
			System.out.println("Pass :Organizations page is Displayed");
		else
			System.out.println("Fail :Organizations page is not Displayed");
		
		String organisation = driver.findElement(By.xpath("//table[@class='lvt small']/descendant::tr[last()]/td[3]/a")).getText();
		if(organisation.equals(organisationName)) 
		{
			System.out.println("Test case Passed");
			excel.writeDataIntoExcel("TestData", "Pass",IConstantPath.EXCEL_FILE_PATH, "Create Organization");
		}
		else
		{
			System.out.println("Test case Failed");
			excel.writeDataIntoExcel("TestData", "Fail",IConstantPath.EXCEL_FILE_PATH, "Create Organization");
		
		}
		WebElement Administrator = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		webdriver.mouseHoverToElement(Administrator);
		driver.findElement(By.xpath("//a[.='Sign Out']")).click();
		
		webdriver.closeBrowser();
		excel.closeExcel();
		
		

	}

}
