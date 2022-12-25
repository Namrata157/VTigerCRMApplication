package testNgImplementation;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import genericLibraries.BaseClass;
import genericLibraries.IConstantPath;
import genericLibraries.TabNames;

public class CreateNewLead extends BaseClass{
	@Test
	public void createNewLeadTest()
	{
		SoftAssert s=new SoftAssert();
		
		home.clickRequiredTab(webdriver,TabNames.LEADS);
		s.assertTrue(driver.getTitle().contains("Leads "),"Fail:Leads page is not Displayed");
		
		leadPage.clickOnPlusButton();
		s.assertTrue(createLead.getCreateLeadHeader().contains("Creating New Lead"),"Fail:Creating new Lead page is not Displayed");
		
		String lastname = createLead.createNewLead(excel, webdriver, java);
		s.assertTrue(leadInfo.getLeadInfoPageHeader().contains(lastname),"Fail :lead is not created");
		
		leadInfo.clickOnDuplicateButton();
		s.assertTrue(duplicatePage.getDuplicatePage().contains(lastname),"Fail :Duplicate page is not displayed");
		
		String newlastname = duplicatePage.ModifyLead(excel, webdriver, java);
		s.assertTrue(leadInfo.getLeadInfoPageHeader().contains(newlastname),"Fail :lead is not created");
		
		leadInfo.clickOnLeadsLink();
		s.assertTrue(leadPage.getLeadPageHeader().contains("Leads"),"Fail :Leads page is not Displayed");
		
		if(leadPage.getLead().equals(newlastname))
			excel.writeDataIntoExcel("TestData", "Pass",IConstantPath.EXCEL_FILE_PATH, "Create Lead");
		else
			excel.writeDataIntoExcel("TestData", "Fail",IConstantPath.EXCEL_FILE_PATH, "Create Lead");
		
		s.assertTrue(leadPage.getLead().equals(newlastname),"Test case Failed");
		s.assertAll();
	}

}
