package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage{
	
	public AccountRegistrationPage(WebDriver driver)
	{
		super(driver);
	}
	
	@FindBy(xpath = "//input[@id='input-firstname']")
	WebElement txtfirstname;

	@FindBy(xpath = "//input[@id='input-lastname']")
	WebElement txtlastname;

	@FindBy(xpath = "//input[@id='input-email']")
	WebElement txtemail;
	
	@FindBy(xpath="//input[@id='input-telephone']")
	WebElement txtTelephone;

	@FindBy(xpath = "//input[@id='input-password']")
	WebElement txtpassword;
	
	@FindBy(xpath="//input[@id='input-confirm']")
	WebElement txtconfirmpassword;
	
	@FindBy(xpath = "//input[@name='agree']")
	WebElement ChkdPolicy;

	@FindBy(xpath="//input[@value='Continue']")
	WebElement btnContinue;

	@FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement msgConfirmation;
	
	
	public void setFirstName(String fname)
	{
		txtfirstname.sendKeys(fname);		
	}
	
	public void setLastName(String Lname)
	{
		txtlastname.sendKeys(Lname);		
	}
	
	public void setEmail(String Email)
	{
		txtemail.sendKeys(Email);		
	}
	
	
	public void setTelephone(String Telephone)
	{
		txtTelephone.sendKeys(Telephone);		
	}
	
	public void setPassword(String pwd)
	{
		txtpassword.sendKeys(pwd);		
	}
	
	public void setConfirmPassword(String confirmpwd)
	{
		txtconfirmpassword.sendKeys(confirmpwd);		
	}
	
	public void setPrivacyPolicy()
	{
		ChkdPolicy.click();;		
	}
	
	
	public void clickContinue()
	{
		btnContinue.click();
	}
	
	
	public String getConfirmationMsg()
	{
		try {
			return (msgConfirmation.getText());
			
		} catch (Exception e) {
			return (e.getMessage());
		}
	}

}
