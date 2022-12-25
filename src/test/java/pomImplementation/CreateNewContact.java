package pomImplementation;

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
import genericLibraries.TabNames;
import genericLibraries.WebDriverUtility;
import io.github.bonigarcia.wdm.WebDriverManager;
import pomPages.ContactInfoPage;
import pomPages.CreatingNewContactPage;
import pomPages.CreatingNewOrganizations;
import pomPages.HomePage;
import pomPages.LoginPage;
import pomPages.NewContactsPage;
import pomPages.OrganizationInfoPage;
import pomPages.OrganizationsPage;

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
		
		LoginPage login=new LoginPage(driver);
		HomePage home=new HomePage(driver);
		NewContactsPage contactPage=new NewContactsPage(driver);
		CreatingNewContactPage createContact=new CreatingNewContactPage(driver);
		ContactInfoPage contactInfo=new ContactInfoPage(driver);
		
		if(driver.getTitle().contains("vtiger"))
			System.out.println("Pass:Vtiger Login page is Displayed");
		else
			System.out.println("Fail:Vtiger Login page is not Displayed");
		
		login.loginToApplication(username, password);
		
		if(driver.getTitle().contains("Administrator"))
			System.out.println("Pass:Vtiger Login successful");
		else
			System.out.println("Fail:Vtiger Login not successful");
		
		home.clickRequiredTab(webdriver,TabNames.CONTACTS);
		
		if(driver.getTitle().contains("Contacts "))
			System.out.println("Pass:Contacts page is Displayed");
		else
			System.out.println("Fail:Contacts page is not Displayed");
		
		contactPage.clickOnPlusButton();
		
		if(createContact.getCreateContactHeader().contains("Creating New Contact"))
			System.out.println("Pass:Creating new Contact page is Displayed");
		else
			System.out.println("Fail:Creating new Contact page is not Displayed");
		
		String lastname = createContact.creatingNewContact(excel, java, webdriver, driver);
		Thread.sleep(3000);
		
		if (contactInfo.getContactInfoPageHeader().contains(lastname))
			System.out.println("Pass :New contact created successfully");
		else
			System.out.println("Fail :contact is not created");
		
		contactInfo.clickOnContactsLink();
		
		if (contactPage.getContactPageHeader().contains("Contacts"))
			System.out.println("Pass :Contacts page is Displayed");
		else
			System.out.println("Fail :Contacts page is not Displayed");
		
		if(contactPage.getContact().equals(lastname))
		{
			System.out.println("Test case Passed");
			excel.writeDataIntoExcel("TestData", "Pass",IConstantPath.EXCEL_FILE_PATH, "Create Contact");
		}
		else
		{
			System.out.println("Test case Failed");
			excel.writeDataIntoExcel("TestData", "Fail",IConstantPath.EXCEL_FILE_PATH, "Create Contact");
		}
		home.mousehoverToAdministratoricon(webdriver);
		home.clickRequiredTab(webdriver,TabNames.SIGNOUT);
		
		webdriver.closeBrowser();
		excel.closeExcel();
	}

}
