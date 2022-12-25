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
import pomPages.CreatingNewLeadPage;
import pomPages.DuplicatingLeadPage;
import pomPages.HomePage;
import pomPages.LeadInfoPage;
import pomPages.LeadsPage;
import pomPages.LoginPage;

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
		
		LoginPage login=new LoginPage(driver);
		HomePage home=new HomePage(driver);
		LeadsPage leadPage=new LeadsPage(driver);
		CreatingNewLeadPage createLead=new CreatingNewLeadPage(driver);
		LeadInfoPage leadInfo=new LeadInfoPage(driver);
		DuplicatingLeadPage duplicatePage=new DuplicatingLeadPage(driver);
		
		if(driver.getTitle().contains("vtiger"))
			System.out.println("Pass:Vtiger Login page is Displayed");
		else
			System.out.println("Fail:Vtiger Login page is not Displayed");
		
		login.loginToApplication(username, password);
		
		if(driver.getTitle().contains("Administrator"))
			System.out.println("Pass:Vtiger Login successful");
		else
			System.out.println("Fail:Vtiger Login not successful");
		
		home.clickRequiredTab(webdriver,TabNames.LEADS);
		
		if(driver.getTitle().contains("Leads "))
			System.out.println("Pass:Leads page is Displayed");
		else
			System.out.println("Fail:Leads page is not Displayed");
		
		leadPage.clickOnPlusButton();
		
		if(createLead.getCreateLeadHeader().contains("Creating New Lead"))
			System.out.println("Pass:Creating new Lead page is Displayed");
		else
			System.out.println("Fail:Creating new Lead page is not Displayed");
		
		String lastname = createLead.createNewLead(excel, webdriver, java);
		
		if (leadInfo.getLeadInfoPageHeader().contains(lastname))
			System.out.println("Pass :New lead created successfully");
		else
			System.out.println("Fail :lead is not created");
		
		leadInfo.clickOnDuplicateButton();
		
		if (duplicatePage.getDuplicatePage().contains(lastname))
			System.out.println("Pass :Duplicate page is displayed");
		else
			System.out.println("Fail :Duplicate page is not displayed");
		
		String newlastname = duplicatePage.ModifyLead(excel, webdriver, java);
		
		if (leadInfo.getLeadInfoPageHeader().contains(newlastname))
			System.out.println("Pass :New lead created successfully");
		else
			System.out.println("Fail :lead is not created");
		Thread.sleep(3000);
		
		leadInfo.clickOnLeadsLink();
		
		if (leadPage.getLeadPageHeader().contains("Leads"))
			System.out.println("Pass :Leads page is Displayed");
		else
			System.out.println("Fail :Leads page is not Displayed");
		
		if(leadPage.getLead().equals(newlastname))
		{
			System.out.println("Test case Passed");
			excel.writeDataIntoExcel("TestData", "Pass",IConstantPath.EXCEL_FILE_PATH, "Create Lead");
		}
		else
		{
			System.out.println("Test case Failed");
			excel.writeDataIntoExcel("TestData", "Fail",IConstantPath.EXCEL_FILE_PATH, "Create Lead");
		}
		
		home.mousehoverToAdministratoricon(webdriver);
		home.clickRequiredTab(webdriver,TabNames.SIGNOUT);
		
		webdriver.closeBrowser();
		excel.closeExcel();
	}

}
