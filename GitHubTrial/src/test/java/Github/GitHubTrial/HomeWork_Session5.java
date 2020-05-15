package Github.GitHubTrial;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomeWork_Session5 {

	WebDriver driver;

	@BeforeMethod

	public void launchBrowser() {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://techfios.com/test/billing/?ng=login/");
	}

	@Test

	public void userMakeDepositInBankAndCash() throws InterruptedException {

		By User_Name_Locator = By.id("username");
		By Password_Locator = By.name("password");
		By LogIn_Locator = By.name("login");
		By Bank_Cash_Locator = By.xpath("//span[text()='Bank & Cash']");
		By New_account_Locator = By.xpath("//a[text()='New Account']");
		By Initial_Balance_locator = By.id("balance");
		By Account_Submit_Locator = By.xpath("div[@class='col-md-6']/descendant::button");
		By List_Of_Acc_Locator = By.linkText("List Accounts");

		driver.findElement(User_Name_Locator).sendKeys("techfiosdemo@gmail.com");
		driver.findElement(Password_Locator).sendKeys("abc123");
		driver.findElement(LogIn_Locator).click();
		driver.findElement(Bank_Cash_Locator).click();
		Thread.sleep(2000);
		driver.findElement(New_account_Locator).click();
		Thread.sleep(5000);
		String account_Name = "KR personal Account" + new Random().nextInt(999);
		driver.findElement(By.id("account")).sendKeys(account_Name);
		Thread.sleep(2000);
		String description = "KR new income :" + new Random().nextInt(9999);
		Thread.sleep(2000);
		driver.findElement(By.id("description")).sendKeys(description);
		driver.findElement(Initial_Balance_locator).sendKeys("50000.00");

		// so finally I got my Xpath axis after 1.5 hours of trial and error method .

		driver.findElement(By.xpath("//div[@class='col-md-6']/descendant::button")).click();
		Thread.sleep(10000);
		// using explicit wait for Account Successfully created validation
		waitForElement(driver, 30, By.xpath("//div[@class='alert alert-success fade in']"));

		// driver.findElement(List_Of_Acc_Locator).click();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("scroll(0,25000)");
		// Validation
		List<WebElement> accountnamesElement = driver.findElements(By.xpath("//table/descendant::tr"));

		System.out.println(accountnamesElement.get(0).getText());
		System.out.println("KR personal Account" + new Random().nextInt(999));

		Assert.assertTrue(isAccountMatch(account_Name, accountnamesElement), "Account create unsuccessful!");

	}

	private boolean isAccountMatch(String account_Name, List<WebElement> accountnamesElement) {

		for (int i = 0; i < accountnamesElement.size(); i++) {
			if (account_Name.equalsIgnoreCase(accountnamesElement.get(i).getText())) {
				return true;

			}

		}

		return false;
	}

	private void waitForElement(WebDriver driver, int timeToWaitInSeconds, By ElementLocator) {
		WebDriverWait wait = new WebDriverWait(driver, timeToWaitInSeconds);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ElementLocator));

	}

	@AfterMethod

	public void close() {

		driver.close();
		driver.quit();
	}

}
