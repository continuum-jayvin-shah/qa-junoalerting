package com.continuum.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import continuum.cucumber.WebdriverWrapper;

public class JunoAlertingUtils {
	/**
	 * To scroll into particular element
	 * 
	 * @param driver
	 *            -
	 * @param element
	 *            - the element to scroll to
	 */
	public static void scrollIntoView(final WebDriver driver, WebElement element) {
		try {
			String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
					+ "var elementTop = arguments[0].getBoundingClientRect().top;"
					+ "window.scrollBy(0, elementTop-(viewPortHeight/2));";
			((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, element);
			new WebDriverWait(driver, 20).pollingEvery(500, TimeUnit.MILLISECONDS)
					.ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
					.withMessage("Realize spinners/page not loading").until(ExpectedConditions.visibilityOf(element));
		} catch (Exception ex) {
			// Log.event("Moved to element..");
		}
	}
	
	  /**
	 * wait for page to load using jquery param
	 */
	public static void waitForPageLoad(int time, WebdriverWrapper wd) {
		   
		    (new WebDriverWait(wd.getWebdriver(), time)).until(new ExpectedCondition<Boolean>() {
		        public Boolean apply(WebDriver d) {
		            JavascriptExecutor js = (JavascriptExecutor) d;
		            String readyState = js.executeScript("return document.readyState").toString();
		          //  System.out.println("Ready State: " + readyState);
		            return (Boolean) readyState.equals("complete");
		        }
		    });
		}
}
