import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.deque.html.axecore.selenium.AxeReporter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestingWCAG {

	String reportJsonFile = "src/test/resources/jsonfile";
	String reportTextFile = "src/test/resources/textfile";

	@Test
	public void testOne() throws JsonIOException, JsonSyntaxException, FileNotFoundException {

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.amazon.com");

		AxeBuilder builder = new AxeBuilder();
		Results results = builder.analyze(driver);
		List<Rule> violations = results.getViolations();

		if (violations.size() == 0) {

			Assert.assertTrue(true, "No violations found");

		} else {

			AxeReporter.writeResultsToJsonFile(reportJsonFile, results);

			JsonElement jsonElement = JsonParser.parseReader(new FileReader(reportJsonFile + ".json"));
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String prettyJson = gson.toJson(jsonElement);
			AxeReporter.writeResultsToTextFile(reportTextFile, prettyJson);

			System.out.println("result" + prettyJson);

			Assert.assertEquals(violations.size(), 0, violations.size() + " violations found");

		}
	}

	private static List<String> tags = Arrays.asList("wcag2aa", "wcag241");

	@Test(enabled = false)
	public void testTwo() throws JsonIOException, JsonSyntaxException, FileNotFoundException {

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.amazon.com");

		AxeBuilder builder = new AxeBuilder();
		builder.withTags(tags);
		Results results = builder.analyze(driver);
		List<Rule> violations = results.getViolations();

		if (violations.size() == 0) {

			Assert.assertTrue(true, "No violations found");

		} else {

			AxeReporter.writeResultsToJsonFile(reportJsonFile, results);

			JsonElement jsonElement = JsonParser.parseReader(new FileReader(reportJsonFile + ".json"));
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String prettyJson = gson.toJson(jsonElement);
			AxeReporter.writeResultsToTextFile(reportTextFile, prettyJson);

			System.out.println("result" + prettyJson);

			Assert.assertEquals(violations.size(), 0, violations.size() + " violations found");

		}
	}

}
