package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrganizationsPage {
	
	@FindBy(xpath = "//a[@class='hdrLink']")
	private WebElement organizationsPageHeader;
	
	@FindBy(xpath = "//img[@src='themes/softed/images/btnL3Add.gif']")
	private WebElement plusbtn;
	
	@FindBy(xpath = "//table[@class='lvt small']/descendant::tr[last()]/td[3]/a")
	private WebElement organization;
	
	
	public OrganizationsPage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}
	
	public String getOrganizationsPageHeader() {
		return organizationsPageHeader.getText();
	}
	
	public void clickOnCreateButton()
	{
		plusbtn.click();
	}
	
	public String getOrganization() {
		return organization.getText();
	}


}
