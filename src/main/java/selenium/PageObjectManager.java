package selenium;

import org.openqa.selenium.WebDriver;
import pagObjects.GitHubHomePage;

public class PageObjectManager {
    private GitHubHomePage homePage;
    private WebDriver driver;
    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
    }

    public GitHubHomePage getGitHubHomePage(){
        return (homePage == null) ? homePage = new GitHubHomePage(driver) : homePage;
    }


}
