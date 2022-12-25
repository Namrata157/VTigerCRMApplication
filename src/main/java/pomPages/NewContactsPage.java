package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import genericLibraries.WebDriverUtility;

public class NewContactsPage {
	
	@FindBy(xpath = "//img[@src='themes/softed/images/btnL3Add.gif']")
	private WebElement plusbutton;
	
	@FindBy(xpath = "//a[@href='index.php?action=ListView&module=Contacts&parenttab=Marketing']")
	private WebElement contactPageHeader;
	
	@FindBy(xpath = "//table[@class='lvt small']/tbody/tr[last()]/td[4]/a")
	private WebElement contact;
	
	public NewContactsPage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}

	public String getContactPageHeader() {
		return contactPageHeader.getText();
	}

	public String getContact() {
		return contact.getText();
	}
	public void clickOnPlusButton()
	{
		plusbutton.click();
	}
	

}
