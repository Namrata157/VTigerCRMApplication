package pomImplementation;

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
import genericLibraries.TabNames;
import genericLibraries.WebDriverUtility;
import io.github.bonigarcia.wdm.WebDriverManager;
import pomPages.CreatingNewOrganizations;
import pomPages.HomePage;
import pomPages.LoginPage;
import pomPages.OrganizationInfoPage;
import pomPages.OrganizationsPage;

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
		
		LoginPage login=new LoginPage(driver);
		HomePage home=new HomePage(driver);
		OrganizationsPage organizationPage=new OrganizationsPage(driver);
		CreatingNewOrganizations createOrganization=new CreatingNewOrganizations(driver);
		OrganizationInfoPage organizationInfo=new OrganizationInfoPage(driver);
		
		if(driver.getTitle().contains("vtiger"))
			System.out.println("Pass:Vtiger Login page is Displayed");
		else
			System.out.println("Fail:Vtiger Login page is not Displayed");
		
		login.loginToApplication(username, password);
		
		if(driver.getTitle().contains("Administrator"))
			System.out.println("Pass:Vtiger Login successful");
		else
			System.out.println("Fail:Vtiger Login not successful");
		
		home.clickRequiredTab(webdriver,TabNames.ORGANIZATIONS);
		
		if(driver.getTitle().contains("Organizations"))
			System.out.println("Pass:Organizations page is Displayed");
		else
			System.out.println("Fail:Organizations page is not Displayed");
		
		organizationPage.clickOnCreateButton();

		if(createOrganization.getCreateOrganizationHeader().contains("Creating New Organization"))
			System.out.println("Pass:Creating new Organization page is Displayed");
		else
			System.out.println("Fail:Creating new Organization page is not Displayed");
		
		String organisationName = createOrganization.createOrganization(excel, webdriver, java);
		
		Thread.sleep(2000);
		
		if (organizationInfo.getOrganizationInfoPageHeader().contains(organisationName))
			System.out.println("Pass :New organization created successfully");
		else
			System.out.println("Fail :Organization is not created");
		
		organizationInfo.clickOnorganizationsLink();
		
		if (organizationPage.getOrganizationsPageHeader().contains("Organizations"))
			System.out.println("Pass :Organizations page is Displayed");
		else
			System.out.println("Fail :Organizations page is not Displayed");
		
		if(organizationPage.getOrganization().equals(organisationName)) 
		{
			System.out.println("Test case Passed");
			excel.writeDataIntoExcel("TestData", "Pass",IConstantPath.EXCEL_FILE_PATH, "Create Organization");
		}
		else
		{
			System.out.println("Test case Failed");
			excel.writeDataIntoExcel("TestData", "Fail",IConstantPath.EXCEL_FILE_PATH, "Create Organization");
		}
		home.mousehoverToAdministratoricon(webdriver);
		home.clickRequiredTab(webdriver,TabNames.SIGNOUT);
		
		webdriver.closeBrowser();
		excel.closeExcel();
		
		

	}

}
