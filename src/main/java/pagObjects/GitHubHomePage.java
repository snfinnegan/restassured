package pagObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.BrowserFactory;

public class GitHubHomePage {

    @FindBy(className="HeaderMenu-details")
    private WebElement whyGitHub;

    @FindBy(how = How.XPATH, using = "//a[contains(text(),'Sign In')]")
    private WebElement signInLink;

    private WebDriver driver;
    private WebDriverWait driverWait;

    public GitHubHomePage(WebDriver driver) {
        this.driver = BrowserFactory.getDriver();
        this.driverWait = new WebDriverWait(driver, 10);
        PageFactory.initElements(this.driver, this);
    }

    public void isWhyGitHubPresent() {
        System.out.println("isWhyGitHubPresent()");
//        try {
//            Thread.sleep(3000);
//        } catch(InterruptedException ex){
//            System.out.print(ex);
//        }
        //this.whyGitHub.click();
        //this.driverWait.until(ExpectedConditions.visibilityOf(this.signInLink));
        //return this.whyGitHub.isDisplayed();
    }

}
