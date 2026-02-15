package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

	@Test(groups={"Regression","Master"})
	public void verify_account_registration() {

		logger.info("****** Starting  TC001_AccountRegistrationTest ****");

		try {

			HomePage HP = new HomePage(driver);
			HP.clickMyAccount();
			logger.info("****** Clicked on my account ****");
			HP.clickRegister();
			logger.info("****** Clicked on Register link****");

			AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
			logger.info("****** providing the customer details ****");
			regpage.setFirstName(randomString().toUpperCase());
			regpage.setLastName(randomString().toUpperCase());
			regpage.setEmail(randomString() + "@gamil.com");
			regpage.setTelephone(randomNumber());
			String password = randomAlphaNumeric();
			regpage.setPassword(password);
			regpage.setConfirmPassword(password);
			regpage.setPrivacyPolicy();
			regpage.clickContinue();

			logger.info("****** validating expected message ****");
			String confmsg = regpage.getConfirmationMsg();

			if (confmsg.equals("Your Account Has Been Created!")) {
				Assert.assertTrue(true);
			} else {
				logger.error("**Test Failed**");
				logger.debug("Debug logs..");
				Assert.fail();
			}
		} 
		catch (Exception e) {
			Assert.fail();
		}
		logger.info("****** finished TC001_AccountRegistrationTest ****");

	}

}
