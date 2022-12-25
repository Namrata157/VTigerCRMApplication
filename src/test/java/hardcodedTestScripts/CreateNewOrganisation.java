package hardcodedTestScripts;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateNewOrganisation {

	public static void main(String[] args) throws InterruptedException {
		
		Random random = new Random();
		int randomNumber = random.nextInt(100);
		
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
		
		driver.findElement(By.xpath("//a[.='Organizations']")).click();
		
		if(driver.getTitle().contains("Organizations"))
			System.out.println("Pass:Organizations page is Displayed");
		else
			System.out.println("Fail:Organizations page is not Displayed");
		
		driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();
		
		String createOrganizationHeader = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		if(createOrganizationHeader.contains("Creating New Organization"))
			System.out.println("Pass:Creating new Organization page is Displayed");
		else
			System.out.println("Fail:Creating new Organization page is not Displayed");
			
		String organisationName = "QspiderMah"+randomNumber;
		driver.findElement(By.name("accountname")).sendKeys(organisationName);
		
		WebElement industryDropdown = driver.findElement(By.name("industry"));
		Select s=new Select(industryDropdown);
		s.selectByVisibleText("Education");
		
		driver.findElement(By.xpath("//input[@value='T']")).click();
		
		WebElement groupdropdown = driver.findElement(By.name("assigned_group_id"));
		Select s1=new Select(groupdropdown);
		s1.selectByVisibleText("Support Group");
		driver.findElement(By.name("button")).click();
		Thread.sleep(2000);
		String organizationInfoPageHeader = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if (organizationInfoPageHeader.contains(organisationName))
			System.out.println("Pass :New organization created successfully");
		else
			System.out.println("Fail :Organization is not created");
		
		driver.findElement(By.xpath("//a[@href='index.php?action=ListView&module=Accounts&parenttab=Marketing']")).click();
		String organizationsPageHeader = driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		if (organizationsPageHeader.contains("Organizations"))
			System.out.println("Pass :Organizations page is Displayed");
		else
			System.out.println("Fail :Organizations page is not Displayed");
		
		String organisation = driver.findElement(By.xpath("//table[@class='lvt small']/descendant::tr[last()]/td[3]/a")).getText();
		if(organisation.equals(organisationName))
		
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
