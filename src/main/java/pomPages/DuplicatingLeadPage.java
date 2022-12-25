package pomPages;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import genericLibraries.ExcelUtility;
import genericLibraries.JavaUtility;
import genericLibraries.WebDriverUtility;

public class DuplicatingLeadPage {
	@FindBy(xpath = "//span[@class='lvtHeaderText']")
	private WebElement duplicatePage;
	@FindBy(name = "lastname")
	private WebElement lastname;
	@FindBy(xpath = " //input[contains(@value,'Save')]")
	private WebElement saveButton;
	
	public DuplicatingLeadPage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}

	public String getDuplicatePage() {
		return duplicatePage.getText();
	}
	
	public String ModifyLead(ExcelUtility excel,WebDriverUtility webdriver,JavaUtility java)
	{
		Map<String, String> map = excel.fetchMultipleDataBasedOnKeyFromExcel("TestData","Create Lead");
		String newlastname = map.get("New Last Name")+java.generateRandomNumber(100);
		lastname.clear();
		lastname.sendKeys(newlastname);
		saveButton.click();
		return newlastname;
	}

}
