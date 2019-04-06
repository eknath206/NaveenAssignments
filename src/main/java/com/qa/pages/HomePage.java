package com.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.qa.base.TestBase;

public class HomePage extends TestBase {

	@FindBy(xpath = "//span[contains(text(), 'For sale')]")
	WebElement forSaleTab;

	@FindBy(xpath = "//span[contains(text(), 'e.g. Oxford, NW3 or Waterloo Station')]")
	WebElement searchField;

	@FindBy(id = "search-submit")
	WebElement searchButton;

	@FindBy(xpath = "//select[@name='results_sort']")
	WebElement resultsSortDropdown;

	@FindBy(xpath = "//*[@id=\"dp-sticky-element\"]/div/div[1]/a/div[2]/h4")
	WebElement agentNameLink;

	@FindBy(xpath = "//*[@id='content']/div/h1/b[1]")
	WebElement agentNameText;

	public HomePage() {
		PageFactory.initElements(driver, this);
	}

	public void searchLocation() throws InterruptedException {
		forSaleTab.click();

		Actions actions = new Actions(driver);
		actions.moveToElement(searchField);
		actions.click();
		actions.sendKeys("London");
		actions.build().perform();

		searchButton.click();

		Select select = new Select(resultsSortDropdown);
		select.selectByVisibleText("Highest price");

		Thread.sleep(5000);

		List<WebElement> allPrices = driver
				.findElements(By.xpath("//ul[@class='listing-results clearfix js-gtm-list']/li/div/div[2]/a"));

		for (WebElement ele : allPrices) {
			String pr = ele.getText();
			System.out.println(pr);
		}

		// WebElement reqdPrice = allPrices.get(4);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,2000)");
		// js.executeScript("arguments[0].scrollIntoView();", reqdPrice);

		Thread.sleep(2000);

		allPrices.get(4).click();

		String expectedAgentNameText = agentNameLink.getText();

		agentNameLink.click();

		String actualAgentNameText = agentNameText.getText();

		Assert.assertEquals(actualAgentNameText, expectedAgentNameText);

	}

}
