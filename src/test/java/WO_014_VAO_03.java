import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 1-) Open the URL 2-) Click "WebOrder" button on top bar. 3-) Enter valid username
 * "Inar" and password "Academy". 4-) Navigate to the view all order page. 5-) Click "Add
 * More Data" "8" times. 6-) Click 1st, 3rd and 5th orders checkbox's. 7-) Click "Delete
 * All" button. 8-) Verify the orders are deleted.
 */

public class WO_014_VAO_03 extends Hooks {

	@Test
	void testAllFunctionalityInViewAllOrderPage() throws InterruptedException {
		// Click "WebOrder" button on top bar.
		WebElement webOrderTab = driver.findElement(By.cssSelector("a[href='/weborder']"));
		webOrderTab.click();

		// Enter valid username "Inar" and password "Academy and click Login Button".
		WebElement usernameTextField = driver.findElement(By.id("login-username-input"));
		WebElement passwordTextField = driver.findElement(By.id("login-password-input"));
		WebElement loginButton = driver.findElement(By.id("login-button"));

		usernameTextField.sendKeys("Inar");
		passwordTextField.sendKeys("Academy");
		loginButton.click();

		// Navigate to the view all order page.
		WebElement viewAllOrderTab = driver.findElement(By.xpath("//a[text()='View all orders']"));
		viewAllOrderTab.click();

		// Click "Add More Data" "8" times.
		WebElement addMoreDataButton = driver.findElement(By.xpath("//button[text()='Add More Data']"));

		for (int i = 0; i < 8; i++) {
			addMoreDataButton.click();
		}

		List<WebElement> allOrders = driver.findElements(By.xpath("//tbody/tr"));

		List<List<String>> allOrderData = new ArrayList<>();

		for (WebElement order : allOrders) {
			List<String> list = new ArrayList<>();
			for (WebElement data1 : order.findElements(By.xpath("td"))) {
				list.add(data1.getText());
			}
			allOrderData.add(list);
		}

		// Click 1st, 3rd and 5th orders checkbox's.
		List<WebElement> allCheckBoxes = driver.findElements(By.xpath("//input[@class='form-check-input']"));
		int count = 0;
		for (int i = 0; i < 6; i += 2) {
			allCheckBoxes.get(i).click();
			allOrderData.remove(i - count);
			count++;
		}

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scroll(0, 1000)");

		try {
			Thread.sleep(1000);
		}
		catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		// Click "Delete All" button.
		WebElement deleteAllButton = driver.findElement(By.xpath("//button[text()='Delete']"));
		deleteAllButton.click();

		jsExecutor.executeScript("window.scroll(0, -750)");

		Thread.sleep(2500);

		allOrders = driver.findElements(By.xpath("//tbody/tr"));

		// Verify the orders are deleted.
		for (int i = 0; i < allOrders.size(); i++) {
			for (int j = 0; j < allOrders.get(i).findElements(By.xpath("td")).size(); j++) {
				assertEquals(allOrders.get(i).findElements(By.xpath("td")).get(j).getText(),
						allOrderData.get(i).get(j));
			}
		}
	}

}
