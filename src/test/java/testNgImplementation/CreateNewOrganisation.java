package testNgImplementation;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import genericLibraries.BaseClass;
import genericLibraries.IConstantPath;
import genericLibraries.TabNames;

@Listeners(genericLibraries.ListenerImplementation.class)
public class CreateNewOrganisation extends BaseClass {
	@Test
	public void createNewOrganisationTest()
	{
		SoftAssert s=new SoftAssert();
		
		home.clickRequiredTab(webdriver,TabNames.ORGANIZATIONS);
		s.assertTrue(driver.getTitle().contains("Organizations"),"Fail:Organizations page is not Displayed");
		
		organizationPage.clickOnCreateButton();
		s.assertTrue(createOrganization.getCreateOrganizationHeader().contains("Creating New Organization"),"Fail:Creating new Organization page is not Displayed");
		
		String organisationName = createOrganization.createOrganization(excel, webdriver, java);
		s.assertTrue(organizationInfo.getOrganizationInfoPageHeader().contains(organisationName),"Fail :Organization is not created");
		
		organizationInfo.clickOnorganizationsLink();
		s.assertTrue(organizationPage.getOrganizationsPageHeader().contains("Organizations"),"Fail :Organizations page is not Displayed");
		
		if(organizationPage.getOrganization().equals(organisationName)) 
			excel.writeDataIntoExcel("TestData", "Pass",IConstantPath.EXCEL_FILE_PATH, "Create Organization");
		else
			excel.writeDataIntoExcel("TestData", "Fail",IConstantPath.EXCEL_FILE_PATH, "Create Organization");
	
		s.assertTrue(organizationPage.getOrganization().equals(organisationName),"Test case Failed");
		s.assertAll();
	}

}
