package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactInfoPage {
	@FindBy(xpath = "//span[@class='dvHeaderText']")
	private WebElement contactInfoPageHeader;
	@FindBy(xpath = "//a[@class='hdrLink']")
	private WebElement contactsLink;
	
	public ContactInfoPage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}

	public String getContactInfoPageHeader() {
		return contactInfoPageHeader.getText();
	}
	
	public void clickOnContactsLink()
	{
		contactsLink.click();
	}
	

}
