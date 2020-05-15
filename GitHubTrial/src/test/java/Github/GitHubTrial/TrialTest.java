package Github.GitHubTrial;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TrialTest {
	
	WebDriver driver;

	@Before
	public void launchBrowser() {
	System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
	driver = new ChromeDriver();
	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	driver.get("http://techfios.com/test/billing/?ng=admin/");
	}

	@Test
	public void userShouldBeAbleToAddDeposite() throws InterruptedException {
	driver.findElement(By.xpath("//*[@type='text']")).sendKeys("techfiosdemo@gmail.com");
	driver.findElement(By.xpath("//*[contains(@placeholder, 'assword') and contains(@class, 'form-')]"))
	.sendKeys("abc123");
	driver.findElement(By.xpath("//*[contains(text(), 'Sign') and @name='login']")).click();

	String expectedTitle =  "Dashboard- TechFios Test Application - Billing";
	Assert.assertEquals(driver.getTitle(), expectedTitle, "Dashboard Page did not display!");
	By TRANSCACTIONS_MENU_LOCATOR = By.xpath("//ul[@id='side-menu']/descendant::span[text()='Transactions']");
	By NEW_DEPOSIT_PAGE_LOCATOR = By.linkText("New Deposit");
	driver.findElement(TRANSCACTIONS_MENU_LOCATOR).click();
	waitForElement(NEW_DEPOSIT_PAGE_LOCATOR, driver, 30);
	driver.findElement(NEW_DEPOSIT_PAGE_LOCATOR).click();

	//Select an account from DropDown
	Select select = new Select(driver.findElement(By.cssSelector("select#account")));
	select.selectByVisibleText("Swimming");
	String expectedDescription = "AutomationTest" + new Random().nextInt(999);
	System.out.println("Expected: " + expectedDescription);
	driver.findElement(By.id("description")).sendKeys(expectedDescription);
	driver.findElement(By.id("amount")).sendKeys("100,000");
	driver.findElement(By.id("submit")).click();

	new WebDriverWait(driver, 60).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(@class, 'blockUI')]")));
	waitForElement(By.linkText(expectedDescription), driver, 60);	//Thread.sleep(5000);

	
	}

	private boolean isDescriptionMatch(String expectedDescription, List<WebElement> descriptionElements) {
	for (int i=0; i < descriptionElements.size(); i++) {
	if(expectedDescription.equalsIgnoreCase(descriptionElements.get(i).getText())) {
	return true;
	}
	}
	return false;
	}

	private void waitForElement(By locator, WebDriver driver1, int time) {
	new WebDriverWait(driver1, time).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	@After
	public void closeEverything() {
	driver.close();
	driver.quit();
	}

}
