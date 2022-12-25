package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LeadInfoPage {
	@FindBy(xpath = "//span[@class='dvHeaderText']")
	private WebElement leadInfoPageHeader;
	@FindBy(xpath = "//input[@title='Duplicate [Alt+U]']")
	private WebElement DuplicateButton;
	@FindBy(xpath = "//a[@class='hdrLink']")
	private WebElement leadsLink;
	
	public LeadInfoPage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}

	public String getLeadInfoPageHeader() {
		return leadInfoPageHeader.getText();
	}
	
	public void clickOnDuplicateButton()
	{
		DuplicateButton.click();
	}
	
	public void clickOnLeadsLink()
	{
		leadsLink.click();
	}

}
