package Run;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/Feautures",
        glue = "TestSuites",
        plugin = {"pretty", "html:target/cucumber-reports/cucumber-html-report.html", "json:target/cucumber-reports/cucumber.json"}, // Reporters
        monochrome = true,
        dryRun = false
)
public class TestRunner {

}
