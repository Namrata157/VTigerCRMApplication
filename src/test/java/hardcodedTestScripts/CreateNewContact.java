package hardcodedTestScripts;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateNewContact {

	public static void main(String[] args) throws InterruptedException, IOException {
		Random random=new Random();
		int randomnumber=random.nextInt(100);
		
		WebDriverManager.chromedriver().setup();
		
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://localhost:8888/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		if(driver.getTitle().contains("vtiger"))
			System.out.println("Pass:Vtiger Login page is Displayed");
		else
			System.out.println("Fail:Vtiger Login page is not Displayed");
		
		driver.findElement(By.name("user_name")).sendKeys("admin");
		driver.findElement(By.name("user_password")).sendKeys("admin");
		driver.findElement(By.id("submitButton")).click();
		
		if(driver.getTitle().contains("Administrator"))
			System.out.println("Pass:Vtiger Login successful");
		else
			System.out.println("Fail:Vtiger Login not successful");
		
		driver.findElement(By.xpath("//a[.='Contacts']")).click();
		if(driver.getTitle().contains("Contacts "))
			System.out.println("Pass:Contacts page is Displayed");
		else
			System.out.println("Fail:Contacts page is not Displayed");
		
		driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();
		
		String createContactHeader = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		if(createContactHeader.contains("Creating New Contact"))
			System.out.println("Pass:Creating new Contact page is Displayed");
		else
			System.out.println("Fail:Creating new Contact page is not Displayed");
		
		WebElement salutation = driver.findElement(By.name("salutationtype"));
		Select s= new Select(salutation);
		s.selectByVisibleText("Ms.");
		String lastname = "Mahajan"+randomnumber;
		driver.findElement(By.name("lastname")).sendKeys(lastname);
		
		driver.findElement(By.xpath("//img[@title='Select']")).click();
		String parent = driver.getWindowHandle();
		Set<String> child = driver.getWindowHandles();
		for(String window:child) {
			driver.switchTo().window(window);
			String actualTitle=driver.getTitle();
			String expectedTitle="Accounts&action";
			if(actualTitle.contains(expectedTitle))
			{
				break;
			}
		}
		String requiredOrganisationName = "TYSS93";
		List<WebElement> contents = driver.findElements(By.xpath("//div[@id='ListViewContents']/descendant::table[@cellpadding='5']/tbody/tr"));
		for(int i=2;i<=contents.size();i++)
		{
			WebElement organisation=driver.findElement(By.xpath("//div[@id='ListViewContents']/descendant::table[@cellpadding='5']/tbody/tr["+i+"]/td[1]/a"));
			String organisationName = organisation.getText();
			if(organisationName.equals(requiredOrganisationName))
			{
				organisation.click();
				break;
			}
		}	
		
		driver.switchTo().window(parent);
		
		WebElement contactImage = driver.findElement(By.xpath("//input[@name='imagename']"));
		contactImage.sendKeys(
				"C:\\Users\\mahaj\\OneDrive\\Desktop\\passport size photo_1.jpg");
		driver.findElement(By.xpath("//input[contains(@value,'Save')]")).click();
		Thread.sleep(3000);
		
		String contactInfoPageHeader = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if (contactInfoPageHeader.contains(lastname))
			System.out.println("Pass :New contact created successfully");
		else
			System.out.println("Fail :contact is not created");
		
		driver.findElement(By.xpath("//a[@class='hdrLink']")).click();
		
		String contactPageHeader = driver.findElement(By.xpath("//a[@href='index.php?action=ListView&module=Contacts&parenttab=Marketing']")).getText();
		if (contactPageHeader.contains("Contacts"))
			System.out.println("Pass :Contacts page is Displayed");
		else
			System.out.println("Fail :Contacts page is not Displayed");
		
		String contact = driver.findElement(By.xpath("//table[@class='lvt small']/tbody/tr[last()]/td[4]/a")).getText();
		if(contact.equals(lastname))
			System.out.println("Test case Passed");
		else
			System.out.println("Test case Failed");
		
		WebElement Administrator = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		Actions a=new Actions(driver);
		a.moveToElement(Administrator).perform();
		driver.findElement(By.xpath("//a[.='Sign Out']")).click();
		driver.quit();
	}

}
