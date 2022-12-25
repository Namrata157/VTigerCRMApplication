package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrganizationInfoPage {
	
	@FindBy(xpath = "//span[@class='dvHeaderText']")
	private WebElement organizationInfoPageHeader;
	@FindBy(xpath = "//a[@href='index.php?action=ListView&module=Accounts&parenttab=Marketing']")
	private WebElement organizationsLink;
	
	public OrganizationInfoPage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}

	public String getOrganizationInfoPageHeader() {
		return organizationInfoPageHeader.getText();
	}
	
	public void clickOnorganizationsLink()
	{
		organizationsLink.click();
	}

}
