package Github.GitHubTrial;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class addnewtest {
	@Test
	public void account() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		Random r = new Random();
		
		for (int i = 0; i < 2; i++) {
			
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get("http://techfios.com/test/billing/?ng=admin/");
			Thread.sleep(3000);
			driver.findElement(By.xpath("//*[@placeholder='Email Address']")).sendKeys("techfiosdemo@gmail.com");
			driver.findElement(By.xpath("//*[@type='password']")).sendKeys("abc123");

			driver.findElement(By.xpath("//button[contains(text(),'Sign in')]")).click();

			String dashboardTitleXpath = "//div[@id='page-wrapper']//descendant::h2[contains(text(),'Dashboard')]";
			String expectedTitle = "Dashboard- TechFios Test Application - Billing";
			// driver will get the title and store in actual title which is a string
			String actualTitle = driver.getTitle();
			/*
			 * if(expectedTitle.equalsIgnoreCase(actualTitle)) {
			 * System.out.println("Test pass"); } else {
			 * System.out.println("Dashboard Page didnot display !"); }
			 */
			// Explicit Wait
			WebDriverWait wait = new WebDriverWait(driver, 1);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dashboardTitleXpath)));
			driver.findElement(By.xpath("//a[contains(text(),'Add Deposit')]")).click();

//	driver.findElement(By.xpath("//input[@name='description']")).sendKeys("house payment");
			driver.findElement(By.xpath("//select[@id='account']")).click();
					
			driver.findElement(By.xpath("//input[@name='description']")).sendKeys("Football");

			int n = r.nextInt(999);
			driver.findElement(By.xpath("//input[@id='amount']")).sendKeys(Integer.toString(n));
			Thread.sleep(3000);

			driver.findElement(By.xpath("//button[@id='submit']")).click();
			Thread.sleep(3000);
		}
		driver.close();
        driver.quit();
	}
}
