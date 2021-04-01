package org.restassured.testng.testrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(features = "features",
                strict = true,
                glue = {"stepdefinitions"},
                plugin = {"pretty","html:target/cucumber-htmlreport","json:target/cucumber-report.json", "junit:target/cucumber-report.xml"},
                tags = {"@UI"}
                )

public class TestRunner extends AbstractTestNGCucumberTests{

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios(){
        return super.scenarios();
    }
}
