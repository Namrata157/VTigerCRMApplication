package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LeadsPage {
	@FindBy(xpath = "//img[@src='themes/softed/images/btnL3Add.gif']")
	private WebElement plusButton;
	@FindBy(xpath = "//a[@href='index.php?action=ListView&module=Leads&parenttab=Marketing']")
	private WebElement LeadPageHeader;
	@FindBy(xpath = "//table[@class='lvt small']/tbody/tr[last()]/td[3]/a")
	private WebElement lead;
	
	public LeadsPage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}

	public String getLeadPageHeader() {
		return LeadPageHeader.getText();
	}

	public String getLead() {
		return lead.getText();
	}
	
	public void clickOnPlusButton()
	{
		plusButton.click();
	}
	
	

}
