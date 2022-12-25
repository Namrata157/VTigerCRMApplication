package pomPages;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	@FindBy(name="user_name")
	private WebElement usernametb;
	
	@FindBy(name="user_password")
	private WebElement passwordtb;
	
	@FindBy(id="submitButton")
	private WebElement loginbtn;
	
	public LoginPage(WebDriver driver)
	{
		
		PageFactory.initElements(driver,this);
	}
	
	public void loginToApplication(String username,String password)
	{
		usernametb.sendKeys(username);
		passwordtb.sendKeys(password);
		loginbtn.click();
	}
	
	

}
