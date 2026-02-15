package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {

	@Test(groups={"sanity","Master"})
	public void verity_login() {
		logger.info("****Starting TC002_LoginTest****");
        
		try
		{
		// Home page
		HomePage Hp = new HomePage(driver);
		Hp.clickMyAccount();
		Hp.clicklogin();

		// Login page
		LoginPage Lp = new LoginPage(driver);
		Lp.setEmail(p.getProperty("email"));
		Lp.setPassword(p.getProperty("password"));
		Lp.clickLogin();

		// My Account page
		MyAccountPage MyAcc = new MyAccountPage(driver);
		boolean TargetPage = MyAcc.isMyAccountpageExists();
		
	//	Assert.assertEquals(TargetPage, true, "Login Failed");
		Assert.assertTrue(TargetPage);
		logger.info("****finished TC002_LoginTest****");
		}
		catch (Exception e) 
		{
			Assert.fail();
		}
	}

}
