package makers_bdd;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Set;

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


        @Given("I am on the Makers homepage")
        public void i_am_on_the_makers_homepage() {
            driver.get("https://makers.tech");
        }

    @When("I click the {string} link")
    public void i_click_the_link(String linkText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String originalWindow = driver.getWindowHandle();

        try {
            // Wait for the link to be present
            WebElement FAQLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(linkText)));

            // Scroll the element into view with JavaScript
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", FAQLink);

            // JavaScript click
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", FAQLink);

            // Wait for new tab to open
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));

            // Switch to the new tab
            for (String windowHandle : driver.getWindowHandles()) {
                if (!windowHandle.equals(originalWindow)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
        } catch (Exception e) {
            throw new AssertionError("Failed to click the link with text: " + linkText, e);
        }
    }


    @Then("I should be on the FAQs page")
        public void i_should_be_on_the_faqs_page() {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Wait until URL contains "faq.makers.tech"
            wait.until(ExpectedConditions.urlContains("faq.makers.tech"));

            String currentUrl = driver.getCurrentUrl();
            assertTrue(currentUrl.contains("faq.makers.tech"),
                    "Expected to be on the FAQs page, but was on: " + currentUrl);
        }
    @Given("I am on Makers homepage")
    public void I_am_on_Makers_homepage() {
        driver.get("https://makers.tech");
    }

    @When("I click the {string} navigation link")
    public void i_Click_the_link(String linkText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(linkText)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", link);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
        } catch (Exception e) {
            throw new AssertionError("Failed to click the link with text: " + linkText, e);
        }
    }


    @Then("the link will take me to {string}")
    public void the_link_will_take_me_to(String expectedUrl) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains(expectedUrl),
                "Expected to be on: " + expectedUrl + ", but was on: " + currentUrl
        );
    }

}
