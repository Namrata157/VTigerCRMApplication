package testNgImplementation;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import genericLibraries.BaseClass;
import genericLibraries.IConstantPath;
import genericLibraries.TabNames;

public class CreateNewContact extends BaseClass{
	@Test
	public void createNewContactTest()
	{
		SoftAssert s=new SoftAssert();
		
		home.clickRequiredTab(webdriver,TabNames.CONTACTS);
		s.assertTrue(driver.getTitle().contains("Contacts "),"Fail:Contacts page is not Displayed");
		
		contactPage.clickOnPlusButton();
		s.assertTrue(createContact.getCreateContactHeader().contains("Creating New Contact"),"Fail:Creating new Contact page is not Displayed");
		
		String lastname = createContact.creatingNewContact(excel, java, webdriver, driver);
		s.assertTrue(contactInfo.getContactInfoPageHeader().contains(lastname),"Fail :contact is not created");
		
		contactInfo.clickOnContactsLink();
		s.assertTrue(contactPage.getContactPageHeader().contains("Contacts"),"Fail :Contacts page is not Displayed");
		
		if(contactPage.getContact().equals(lastname))
			excel.writeDataIntoExcel("TestData", "Pass",IConstantPath.EXCEL_FILE_PATH, "Create Contact");
		else
			excel.writeDataIntoExcel("TestData", "Fail",IConstantPath.EXCEL_FILE_PATH, "Create Contact");
		
		s.assertTrue(contactPage.getContact().equals(lastname),"Test case Failed");
		s.assertAll();
	}

}
