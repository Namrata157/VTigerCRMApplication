package pomPages;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import genericLibraries.ExcelUtility;
import genericLibraries.JavaUtility;
import genericLibraries.WebDriverUtility;

public class CreatingNewOrganizations {
	@FindBy(xpath = "//span[@class='lvtHeaderText']")
	private WebElement createOrganizationHeader;
	@FindBy(name="accountname")
	private WebElement organisation;
	@FindBy(name="industry")
	private WebElement industryDropdown;
	@FindBy(xpath = "//input[@value='T']")
	private WebElement groupradiobutton;
	@FindBy(name = "assigned_group_id")
	private WebElement groupdropdown;
	@FindBy(name="button")
	private WebElement saveButton;
	
	public CreatingNewOrganizations(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}

	public String getCreateOrganizationHeader() {
		return createOrganizationHeader.getText();
	}
	
	public String createOrganization(ExcelUtility excel,WebDriverUtility webdriver,JavaUtility java)
	{
		Map<String, String> map = excel.fetchMultipleDataBasedOnKeyFromExcel("TestData", "Create Organization");
		String organisationName = map.get("Organization Name")+java.generateRandomNumber(100);
		organisation.sendKeys(organisationName);
		webdriver.dropDown(industryDropdown,map.get("Industry"));
		groupradiobutton.click();
		webdriver.dropDown(groupdropdown,map.get("Group"));
		saveButton.click();
		return organisationName;
	}
	
}
