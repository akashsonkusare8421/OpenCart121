package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {
	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class,groups="Datadriven") // getting data provider from different
																				// class
	public void verify_loginDDT(String email, String pwd, String exp) {
		
		logger.info("******Starting TC003_LoginDDT******");
		try {
		// Home page
		HomePage Hp = new HomePage(driver);
		Hp.clickMyAccount();
		Hp.clicklogin();

		// Login page
		LoginPage Lp = new LoginPage(driver);
		Lp.setEmail(email);
		Lp.setPassword(pwd);
		Lp.clickLogin();

		// My Account page
		MyAccountPage MyAcc = new MyAccountPage(driver);
		boolean TargetPage = MyAcc.isMyAccountpageExists();
		
		Thread.sleep(2000);

		/*
		 * Condition1
		 *  Data is vaild - login success - test pass - log out Data is vaild
		 * - login fail - test fail
		 * 
		 * 
		 * Condition2 
		 * Data is invaild - login success - test fail - log out Data is
		 * invaild - login success - test pass
		 */

		// Condition1
		if (exp.equalsIgnoreCase("Valid")) {
			if (TargetPage == true) {
				MyAcc.ClicklogOut();
				Assert.assertTrue(true);

			} else {
				Assert.assertTrue(false);
			}
		}

		// Condition2
		if (exp.equalsIgnoreCase("InValid")) {
			if (TargetPage == true) {
				MyAcc.ClicklogOut();
				Assert.assertTrue(false);

			} else {
				Assert.assertTrue(true);
			}
		}
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		logger.info("******ending TC003_LoginDDT******");

	}

}
