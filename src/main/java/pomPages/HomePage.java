package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import genericLibraries.TabNames;
import genericLibraries.WebDriverUtility;

public class HomePage {
	private String dynamicPath = "//a[.='%s']";
	
	@FindBy(xpath = "//img[@src='themes/softed/images/user.PNG']")
	private WebElement administratorIcon;
	
	public HomePage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}
	
	
	public void mousehoverToAdministratoricon(WebDriverUtility webdriver)
	{
		webdriver.mouseHoverToElement(administratorIcon);
	}

	public void clickRequiredTab(WebDriverUtility webdriver,TabNames tabname)
	{
		webdriver.convertStringToDynamicXpath(dynamicPath,tabname.getTabName()).click();
	}

}
