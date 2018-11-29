package com.crm.qa.testcases;

import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.crm.qa.base.TestBase;
import com.crm.qa.pages.ContactsPage;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.util.TestUtil;

public class ContactsPageTest extends TestBase{

	LoginPage loginPage;
	HomePage homePage;
	TestUtil testUtil;
	ContactsPage contactsPage;
	
	String sheetName="contacts";
	
	public ContactsPageTest() {
		super();
	}
	
	@BeforeMethod
	public void setUp(){
		initialization();
		testUtil=new TestUtil();
		loginPage=new LoginPage();
		contactsPage=new ContactsPage();
		homePage=loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
		testUtil.switchToFrame();
		contactsPage=homePage.clickOnContactsLink();
	}
	
	@Test(priority=1)
	public void verifyContactPageLabel(){
		Assert.assertTrue("contacts label is missing on the page",contactsPage.verifyContactsLabel());
	}
	
	@Test(priority=2)
	public void selectSingleContactTest(){
		contactsPage.selectContactsByName("a1 b");
	}
	
	@Test(priority=3)
	public void selectMultipleContactTest(){
		contactsPage.selectContactsByName("a1 b");
		contactsPage.selectContactsByName("Alex Hales");
	}
	
	@DataProvider
	public Object[][] getCRMTestData(){
		Object data[][]=TestUtil.getTestData(sheetName);
		//System.out.println(data);
		return data;
	}
	
	@Test(priority=4, dataProvider="getCRMTestData")
	public void validateCreateNewContactLink(String title,String firstName,String lastName,String company){
		homePage.clickOnNewContactLink();
		contactsPage.createNewContact(title,firstName,lastName,company);
		//contactsPage.createNewContact("Mr.", "Tom", "peter", "Google");
	}
	
	
	
	@AfterMethod
	public void tearDown(){
		driver.quit();
	}
}
