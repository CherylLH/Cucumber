package makers_bdd;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinitions {

    private final WebDriver driver = new FirefoxDriver();

    @Given("I am on the Makers FAQ page")
    public void I_visit_faq_page() {
        driver.get("https://faq.makers.tech/en/knowledge");
    }

    @When("I search for {string}")
    public void search_for(String query) throws InterruptedException {
        WebElement mainSearch = driver.findElement(By.className("kb-search__bar"));
        WebElement element = mainSearch.findElement(By.name("term"));
        element.sendKeys(query);
        element.submit();
        Thread.sleep(3000); // We should really use a dynamic wait!
    }

    @Then("the results header should mention {string}")
    public void checkTitle(String searchString) {
        WebElement h1 = driver.findElement(By.className("kb-search-results__heading"));
        assertTrue(h1.getText().contains(searchString));
    }

    @Then("the term {string} should appear in the URL")
    public void the_term_should_appear_in_the_url(String term) {
        //driver.get("https://faq.makers.tech/en/knowledge/kb-search-results?term=remote+pairing");
        String currentUrl = driver.getCurrentUrl();
        String expectedTermInUrl = term.replace(" ", "+");
        assertTrue(currentUrl.contains(expectedTermInUrl),
                "Expected term not found in URL. Actual URL: " + currentUrl);


    }

    @After
    public void closeBrowser(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "name");
        }
        driver.quit();
    }
}

