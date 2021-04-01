package selenium;

//import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class BrowserFactory {

    private static WebDriver driver;

    public static WebDriver getDriver() {
        String seleniumHubUrl;
        if(driver == null) {
            try {
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                if(System.getProperty("HUB_HOST") != null){
                    seleniumHubUrl = "http://" + System.getProperty("HUB_HOST") + ":4444/wd/hub";
                }   else {
                    seleniumHubUrl = "http://localhost:4444/wd/hub";
                }


                driver = new RemoteWebDriver(new URL(seleniumHubUrl), capabilities);
            } catch (MalformedURLException ex){
                System.out.println(ex);
            }
        }
        return driver; }
}
