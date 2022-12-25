package pomPages;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import genericLibraries.ExcelUtility;
import genericLibraries.JavaUtility;
import genericLibraries.WebDriverUtility;

public class CreatingNewContactPage {
	@FindBy(xpath = "//span[@class='lvtHeaderText']")
	private WebElement createContactHeader;
	@FindBy(name = "salutationtype")
	private WebElement salutation;
	@FindBy(name = "lastname")
	private WebElement contactlastname;
	@FindBy(xpath = "//img[@title='Select']")
	private WebElement organizationsPlusbutton;
	@FindBy(xpath = "//div[@id='ListViewContents']/descendant::table[@cellpadding='5']/tbody/tr")
	private List<WebElement> contents;
	private String organization="//div[@id='ListViewContents']/descendant::table[@cellpadding='5']/tbody/tr[%d]/td[1]/a";
	@FindBy(xpath = "//input[@name='imagename']")
	private WebElement contactImage;
	@FindBy(xpath = "//input[contains(@value,'Save')]")
	private WebElement saveButton;
	
	public CreatingNewContactPage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}

	public String getCreateContactHeader() {
		return createContactHeader.getText();
	}
	
	public String creatingNewContact(ExcelUtility excel,JavaUtility java,WebDriverUtility webdriver,WebDriver driver)
	{
		Map<String, String> map = excel.fetchMultipleDataBasedOnKeyFromExcel("TestData", "Create Contact");
		webdriver.dropDown(salutation,map.get("First Name Salutation"));
		String lastname = map.get("Last Name")+java.generateRandomNumber(100);
		contactlastname.sendKeys(lastname);
		organizationsPlusbutton.click();
		
		String parent = webdriver.getParentWindow();
		webdriver.handleChildBrowserPopup("Accounts&action");
		String requiredOrganisationName = map.get("Organization Name");
		
		for(int i=2;i<=contents.size();i++)
		{
			String requiredPath=String.format(organization, i);
			WebElement organisation=driver.findElement(By.xpath(requiredPath));
			String organisationName = organisation.getText();
			if(organisationName.equals(requiredOrganisationName))
			{
				organisation.click();
				break;
			}
		}	
		webdriver.switchToWindow(parent);
		contactImage.sendKeys(map.get("Contact Image"));
		saveButton.click();
		
		return lastname;
	}
	
}
