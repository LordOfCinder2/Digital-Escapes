import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class CreateUserAccountAlreadyRegisteredTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void createUserAccountAlreadyRegistered() {
    driver.get("http://127.0.0.1:5173/home");
    driver.manage().window().setSize(new Dimension(1366, 720));
    driver.findElement(By.linkText("Crear Cuenta")).click();
    driver.findElement(By.name("name")).click();
    driver.findElement(By.name("name")).sendKeys("Matias");
    driver.findElement(By.name("username")).click();
    driver.findElement(By.name("username")).sendKeys("mati");
    driver.findElement(By.name("phone")).click();
    driver.findElement(By.name("phone")).sendKeys("1234567");
    driver.findElement(By.name("email")).click();
    driver.findElement(By.name("email")).sendKeys("matias.dilisa@gmail.com");
    driver.findElement(By.name("password")).click();
    driver.findElement(By.name("password")).sendKeys("123456");
    driver.findElement(By.cssSelector("input:nth-child(1)")).click();
    driver.findElement(By.cssSelector(".\\_submitButton_c04k1_86")).click();
    assertThat(driver.findElement(By.cssSelector(".\\_error_c04k1_64")).getText(), is("Ya existe un usuario con ese email: matias.dilisa@gmail.com"));
  }
}
