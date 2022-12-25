package pomPages;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import genericLibraries.ExcelUtility;
import genericLibraries.JavaUtility;
import genericLibraries.WebDriverUtility;

public class CreatingNewLeadPage {
	@FindBy(xpath = "//span[@class='lvtHeaderText']")
	private WebElement createLeadHeader;
	@FindBy(name = "salutationtype")
	private WebElement salutation;
	@FindBy(name = "lastname")
	private WebElement lastname;
	@FindBy(name = "company")
	private WebElement company;
	@FindBy(xpath = " //input[contains(@value,'Save')]")
	private WebElement saveButton;
	
	public CreatingNewLeadPage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}

	public String getCreateLeadHeader() {
		return createLeadHeader.getText();
	}
	
	public String createNewLead(ExcelUtility excel,WebDriverUtility webdriver,JavaUtility java)
	{
		Map<String, String> map = excel.fetchMultipleDataBasedOnKeyFromExcel("TestData","Create Lead");
		webdriver.dropDown(salutation, map.get("First Name Salutation"));
		String Leadlastname = map.get("Last Name")+java.generateRandomNumber(100);
		lastname.sendKeys(Leadlastname);
		company.sendKeys(map.get("Company"));
		saveButton.click();
		return Leadlastname;
	}
	

}
