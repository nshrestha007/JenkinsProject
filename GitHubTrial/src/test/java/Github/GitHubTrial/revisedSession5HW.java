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

public class revisedSession5HW {
	
	public class AddNewAccount {

		WebDriver driver;
		
		@BeforeMethod
		public void addNewAccount() {
			System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get("http://techfios.com/test/billing/?ng=admin/");
			driver.manage().window().maximize();
			driver.findElement(By.xpath("//input[@placeholder='Email Address']")).sendKeys("techfiosdemo@gmail.com");
			driver.findElement(By.xpath("//input[@id='password']")).sendKeys("abc123");
			driver.findElement(By.xpath("//button[contains(text(),'Sign')]")).click();
		}
		@Test
		public void addNewAccountForm() throws InterruptedException {
			
			By BANKCASH_ELEMENT_LOCATOR = By.xpath("//span[ contains(text(),'Bank & Cash')]");
			By NEWACCOUNT_ELEMENT_LOCATOR = By.linkText("New Account");
			By ACCOUNTTITLE_ELEMENT_LOCATOR = By.id("account");
			//By DESCRIPTION_ELEMENT_LOCATOR = By.id("description");
			By INITIALBALANCE_ELEMENT_LOCATOR = By.id("balance");

			By SUBMIT_ELEMENT_LOCATOR = By.xpath("//i[@class=\"fa fa-check\"]");
			
			driver.findElement(BANKCASH_ELEMENT_LOCATOR).click();
			driver.findElement(NEWACCOUNT_ELEMENT_LOCATOR).click();
			
			Random rnd = new Random();
			int rndNumber = rnd.nextInt(999);		
			
			String expectedAccountTitle = "Salaries" + rndNumber;
			//String initialBalance = ""+ rndNumber;
			
			driver.findElement(ACCOUNTTITLE_ELEMENT_LOCATOR).sendKeys(expectedAccountTitle);
			driver.findElement(By.id("description")).sendKeys("operating expenses");
			driver.findElement(INITIALBALANCE_ELEMENT_LOCATOR).sendKeys("1000");
			driver.findElement(SUBMIT_ELEMENT_LOCATOR).click();
			//calling the explicit wait method
			waitForElement(By.xpath("//div[@class='alert alert-success fade in']"), driver, 10);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("scroll(0,50000)");
			
			List<WebElement> accountnamesElement = driver.findElements(By.xpath("//table/descendant::td"));
			Assert.assertTrue(isAccountNameMatch(expectedAccountTitle,accountnamesElement),"Transaction Unsuccessful!");
			Thread.sleep(3000);
			driver.findElement(By.xpath("//td[text()='"+expectedAccountTitle+"']/parent::tr/descendant::i[2]")).click();
			driver.findElement(By.xpath("//button[@class='btn btn-primary']")).click();
			Thread.sleep(3000);
			js.executeScript("scroll(0,-50000)");
			waitForElement(By.xpath("//div[@class='alert alert-success fade in']"), driver, 10);
		}			
	private boolean isAccountNameMatch(String expectedAccountTitle, List<WebElement> accountnamesElement) {
			for(int i =0; i <accountnamesElement.size();i++) {
				if(expectedAccountTitle.equalsIgnoreCase(accountnamesElement.get(i).getText())) {
					return true;
				}
			}
		
		return false;
		}
	private void waitForElement(By locator, WebDriver driver, int time) {
			WebDriverWait wait = new WebDriverWait(driver,time);
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}
	

		@AfterMethod
		public void closeEverything() {
			driver.close();
			driver.quit();
		}

		
		
	}
}