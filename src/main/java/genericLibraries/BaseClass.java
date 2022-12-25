package genericLibraries;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import pomPages.ContactInfoPage;
import pomPages.CreatingNewContactPage;
import pomPages.CreatingNewLeadPage;
import pomPages.CreatingNewOrganizations;
import pomPages.DuplicatingLeadPage;
import pomPages.HomePage;
import pomPages.LeadInfoPage;
import pomPages.LeadsPage;
import pomPages.LoginPage;
import pomPages.NewContactsPage;
import pomPages.OrganizationInfoPage;
import pomPages.OrganizationsPage;

public class BaseClass {
	
	protected JavaUtility java;
	protected WebDriverUtility webdriver;
	protected PropertyFileUtility property;
	protected ExcelUtility excel;
	protected WebDriver driver;
	protected LoginPage login;
	protected HomePage home;
	protected OrganizationsPage organizationPage;
	protected CreatingNewOrganizations createOrganization;
	protected OrganizationInfoPage organizationInfo;
	protected NewContactsPage contactPage;
	protected CreatingNewContactPage createContact;
	protected ContactInfoPage contactInfo;
	protected LeadsPage leadPage;
	protected CreatingNewLeadPage createLead;
	protected LeadInfoPage leadInfo;
	protected DuplicatingLeadPage duplicatePage;
	
	//@BeforeSuite
	
	@BeforeTest
	public void testSetup()
	{
		java=new JavaUtility();
		webdriver=new WebDriverUtility();
		property=new PropertyFileUtility();
		excel=new ExcelUtility();
	}
	
	@BeforeClass
	public void classSetup()
	{
		property.propertyFileInitialization(IConstantPath.PROPERTY_FILE_PATH);
		excel.excelFileInitialization(IConstantPath.EXCEL_FILE_PATH);
		String url = property.getDataFromPropertyFile("url");
		String browser=property.getDataFromPropertyFile("browser");
		long time=Long.parseLong(property.getDataFromPropertyFile("timeouts"));
		
		driver = webdriver.openBrowserAndApplication(browser, url, time);
		
		login=new LoginPage(driver);
		Assert.assertTrue(driver.getTitle().contains("vtiger"),"Fail:Vtiger Login page is not Displayed");
//		if(driver.getTitle().contains("vtiger"))
//			System.out.println("Pass:Vtiger Login page is Displayed");
//		else
//			System.out.println("Fail:Vtiger Login page is not Displayed");
	}
	
	@BeforeMethod
	public void methodSetup()
	{
		home=new HomePage(driver);
		organizationPage=new OrganizationsPage(driver);
		createOrganization=new CreatingNewOrganizations(driver);
	    organizationInfo=new OrganizationInfoPage(driver);
		contactPage=new NewContactsPage(driver);
		createContact=new CreatingNewContactPage(driver);
		contactInfo=new ContactInfoPage(driver);
		leadPage=new LeadsPage(driver);
		createLead=new CreatingNewLeadPage(driver);
		leadInfo=new LeadInfoPage(driver);
		duplicatePage=new DuplicatingLeadPage(driver);
		
		String username=property.getDataFromPropertyFile("username");
		String password=property.getDataFromPropertyFile("password");
		login.loginToApplication(username, password);
		
		Assert.assertTrue(driver.getTitle().contains("Administrator"),"Fail:Vtiger Login not successful");
//		if(driver.getTitle().contains("Administrator"))
//			System.out.println("Pass:Vtiger Login successful");
//		else
//			System.out.println("Fail:Vtiger Login not successful");
	}
	
	@AfterMethod
	public void methodTearDown()
	{
		home.mousehoverToAdministratoricon(webdriver);
		home.clickRequiredTab(webdriver,TabNames.SIGNOUT);
	}
	
	@AfterClass
	public void classTearDown()
	{
		webdriver.closeBrowser();
	}
	
	@AfterTest
	public void testTearDown()
	{
		excel.closeExcel();
	}
	
	//@AfterSuite

}
