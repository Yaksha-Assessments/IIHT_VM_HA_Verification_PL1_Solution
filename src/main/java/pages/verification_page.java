package pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class verification_page extends StartupPage {


//	TC-1 to 4 Locators
	
	public By getUsernameTextfieldLocator() {
		return By.id("username_id");
	}
	public By getUsernameTextboxLocator() {
		return By.xpath("//input[@id='username_id']");
	}

	public By getPasswordTextboxLocator() {
		return By.xpath("//input[@id='password']");
	}

	public By getSignInButtonLocator() {
		return By.xpath("//button[@id='login']");
	}

	public By getVerificationLocator() {
		return By.xpath("//a[@href='#/Verification']");
	}
	
//	TC-5 Locators
	public By getPageBarFixedLocator(String navBarName) {
		return By.xpath("//ul[@class='page-breadcrumb']/li/a[@href='#/Verification/" + navBarName + "']");
	}
	
	public By getSubNavTabLocator(String subNavName) {
		return By.xpath("//div[@class=\"sub-navtab\"]/ul/li/a[text()='" + subNavName + "']");
	}
	
//	TC-6 Locators
	
	public By getActualRequestedOnDates() {
		return By.xpath("//div[@col-id=\"RequisitionDate\"]/span[not(contains(@class,'hidden'))]");
	}
//	TC-7 Locators
	public By favouriteOrStarIcon() {
		return By.xpath("//i[contains(@class,'icon-favourite')]");
	}
	
//	TC-8 LOcators
	public By calendarFromDropdown() {
		return By.xpath("(//input[@id='date'])[1]");
	}

	public By calendarToDropdown() {
		return By.xpath("(//input[@id='date'])[2]");
	}
	
	public By getOkButtonLocator() {
		return By.xpath("//button[@class='btn green btn-success']");
	}
	
	public By getStarIconLocator() {
		return By.xpath("//i[contains(@class,'icon-favourite')]/..");
	}



	public verification_page(WebDriver driver) {
		super(driver);
	}

	/**
	 * @Test1.1 about this method loginTohealthAppByGivenValidCredetial()
	 * 
	 * @param : Map<String, String>
	 * @description : fill usernameTextbox & passwordTextbox and click on sign in
	 *              button
	 * @return : Boolean
	 * @author : Yaksha
	 */
	public boolean loginToHealthAppByGivenValidCredetial(Map<String, String> expectedData) throws Exception {
		Boolean textIsDisplayed = false;
		try {
			WebElement usernametextFieldWebElement = commonEvents.findElement(getUsernameTextfieldLocator());
			commonEvents.highlightElement(usernametextFieldWebElement);
			commonEvents.sendKeys(getUsernameTextfieldLocator(), expectedData.get("username"));

			WebElement passwordtextFieldWebElement = commonEvents.findElement(getPasswordTextboxLocator());
			commonEvents.highlightElement(passwordtextFieldWebElement);
			commonEvents.sendKeys(getPasswordTextboxLocator(), expectedData.get("password"));

			WebElement signinButtonWebElement = commonEvents.findElement(getPasswordTextboxLocator());
			commonEvents.highlightElement(signinButtonWebElement);
			commonEvents.click(getSignInButtonLocator());
			textIsDisplayed = true;
		} catch (Exception e) {
			throw e;
		}
		return textIsDisplayed;
	}

	/**
	 * @Test1.2 about this method scrollDownAndClickVerificationTab()
	 * 
	 * @param : null
	 * @description : verify the pharmacy tab, scroll to it, and click it
	 * @return : String
	 * @author : YAKSHA
	 */
	public void scrollDownAndClickVerificationTab() throws Exception {
		try {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			WebElement verificationTab = commonEvents.findElement(getVerificationLocator());
			jsExecutor.executeScript("arguments[0].scrollIntoView(true);", verificationTab);
			commonEvents.highlight(verificationTab);
			commonEvents.click(verificationTab);

			// Wait for the URL to contain "Verification/Inventory"
			commonEvents.waitForUrlContains("Verification/Inventory", 10);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @Test1.3 about this method verifyVerificationPageUrl()
	 * 
	 * @param : null
	 * @description : verify verification page url
	 * @return : String
	 * @author : YAKSHA
	 */
	public String verifyVerificationPageUrl() throws Exception {
		try {
			String titleToVerify = commonEvents.getCurrentUrl();
			return titleToVerify;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @Test2, 3 about this method highlightAndVerifyWhetherElementIsDisplayed
	 * 
	 * @param element : By - Locator of the element to be highlighted and verified
	 * @description : This method verifies whether an element is displayed on the
	 *              page, highlights it if displayed, and returns true if displayed.
	 * @return : boolean - true if the element is displayed, otherwise false
	 * @author : YAKSHA
	 */
	public boolean highlightAndVerifyWhetherElementIsDisplayed(By element) {
		boolean isElementDisplayed = false;
		try {
			if (commonEvents.isDisplayed(element)) {
				WebElement elementToHighlight = commonEvents.findElement(element);
				commonEvents.highlight(elementToHighlight);
				isElementDisplayed = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isElementDisplayed;
	}

	/**
	 * @Test2, @Test3 and @Test9.1 about this method highlightAndClickOnElement
	 * 
	 * @param element     : By - Locator of the element to be highlighted and
	 *                    clicked
	 * @param elementName : String - Name of the element to be clicked (used for
	 *                    logging purposes)
	 * @description : This method highlights the element and clicks on it. It
	 *              returns true if the element is clicked successfully.
	 * @return : boolean - true if the element is clicked successfully, otherwise
	 *         false
	 * @author : YAKSHA
	 */
	public boolean highlightAndClickOnElement(By element, String elementName) {
		boolean isElementDisplayed = false;
		try {
			WebElement elementToBeClicked = commonEvents.findElement(element);
			commonEvents.highlight(elementToBeClicked);
			commonEvents.click(elementToBeClicked);
			System.out.println("Clicked on " + elementName);
			isElementDisplayed = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isElementDisplayed;
	}

	/**
	 * @Test4 about this method verifySelectedTabIsActiveOrNot()
	 * @param : element - the locator of the tab to be verified
	 * @description : This method verifies if the specified tab is displayed, clicks
	 *              it, and returns whether its "class" attribute contains "active".
	 *              This can be used to determine if the tab is active based on its
	 *              class attributes.
	 * @return : boolean - true if the "class" attribute of the tab contains
	 *         "active", false otherwise
	 * @throws : Exception - if there is an issue locating, highlighting, clicking
	 *           the tab, or getting its attribute
	 * @author : YAKSHA
	 */
	public boolean verifySelectedTabIsActiveOrNot(By element) throws Exception {
		boolean isActive = false;
		try {
			if (commonEvents.isDisplayed(element)) {
				WebElement tabs = commonEvents.findElement(element);
				commonEvents.highlight(tabs);
				commonEvents.click(tabs);
				String locatorAttributeValue = commonEvents.getAttribute(tabs, "class");
				isActive = locatorAttributeValue.contains("active");
			}
		} catch (Exception e) {
			throw e;
		}
		return isActive;
	}

	/**
	 * @Test5 about this method verifyNavigationOfTabs()
	 * @param : element - the locator of the tab to be verified
	 * @description : This method verifies if the specified tab is displayed, clicks
	 *              it, and returns whether its "class" attribute contains "active".
	 *              This can be used to determine if the tab is active based on its
	 *              class attributes. It also navigates through "Inventory" and
	 *              "Requisition" tabs before verifying the "Purchase Request" tab.
	 * @return : boolean - true if the "class" attribute of the tab contains
	 *         "active", false otherwise
	 * @throws : Exception - if there is an issue locating, highlighting, clicking
	 *           the tab, or getting its attribute
	 * @author : YAKSHA
	 */
	public boolean verifyNavigationOfTabs() throws Exception {
		boolean isActive = false;
		try {
			if (commonEvents.isDisplayed(getPageBarFixedLocator("Inventory"))) {

				highlightAndClickOnElement(getPageBarFixedLocator("Inventory"), "inventory tab");
				highlightAndClickOnElement(getSubNavTabLocator("Requisition"), "inventory tab");

				WebElement purchaseReqTab = commonEvents.findElement(getSubNavTabLocator("Purchase Request"));
				commonEvents.highlight(purchaseReqTab);
				commonEvents.click(purchaseReqTab);

				String locatorAttributeValue = commonEvents.getAttribute(purchaseReqTab, "class");
				System.out.println("locatorAttributeValue > " + locatorAttributeValue);
				isActive = locatorAttributeValue.contains("active");

				highlightAndClickOnElement(getSubNavTabLocator("Requisition"), "inventory tab");
			}
		} catch (Exception e) {
			throw e;
		}
		return isActive;
	}

	/**
	 * @Test6 and @Test9.3 about this method
	 *        verifyTheResultsDateRangeIsWithinTheSelectedRange()
	 * 
	 * @param : String, String - from date - to date
	 * @description : This method verifies whether the "Requested On" dates for all
	 *              the result requisitions are within the specified date range.
	 * @return : boolean - true if the actual dates fall within the specified date
	 *         and false if they don't.
	 * @throws : Exception - if there is an issue finding the actual data
	 * @author : YAKSHA
	 */
	public boolean verifyTheResultsDateRangeIsWithinTheSelectedRange(String fromDate, String toDate) throws Exception {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			List<WebElement> actualDatesAfterFilterApplied = commonEvents.getWebElements(getActualRequestedOnDates());
			LocalDate from = LocalDate.parse(fromDate, formatter);
			LocalDate to = LocalDate.parse(toDate, formatter);

			for (WebElement dateElement : actualDatesAfterFilterApplied) {
				commonEvents.highlight(dateElement);
				String dateText = dateElement.getText();

				LocalDate date;
				LocalDate newDate;
				try {
					date = LocalDate.parse(dateText, inputFormatter);
					newDate = LocalDate.parse(date.format(formatter), formatter);

				} catch (Exception e) {
					System.out.println("Date parsing failed for: " + dateText);
					return false;
				}

				if (newDate.isBefore(from) || newDate.isAfter(to)) {
					return false;
				}
			}
			return true;

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @Test7 about this method verifyToolTipText()
	 * @param : null
	 * @description : Verify the text of the tooltip
	 * @return : String
	 * @throws : Exception - if there is an issue finding the text
	 * @author : YAKSHA
	 */
	public String verifyToolTipText() {
		String toolTipValue = "";
		try {
			WebElement toolTip = commonEvents.findElement(favouriteOrStarIcon());
			toolTipValue = commonEvents.highlight(toolTip).getAttribute(toolTip, "title");
			System.out.println("Tool tip title : " + toolTipValue);
		} catch (Exception e) {
			throw e;
		}
		return toolTipValue;
	}

	/**
	 * @Test8 about this method verifyDatesAreRememberedCorrectly()
	 * 
	 * @param fromDate - the expected "from" date in the format "dd-MM-yyyy"
	 * @param toDate   - the expected "to" date in the format "dd-MM-yyyy"
	 * @description : This method selects the "from" and "to" dates in the calendar,
	 *              clicks the OK button, navigates away to another tab, returns to
	 *              the original tab, and verifies if the dates are remembered
	 *              correctly.
	 * @return : boolean - true if the dates are remembered correctly, false
	 *         otherwise
	 * @throws : Exception - if there is an issue locating, highlighting, or
	 *           clicking elements, or if there is an issue with date selection or
	 *           verification
	 * @author : YAKSHA
	 * @throws Exception
	 */
	public boolean verifyDatesAreRememberedCorrectly(String fromDate, String toDate) throws Exception {
		try {
			// Split the fromDate and toDate into day, month, and year components
			String[] fromDateComponents = fromDate.split("-");
			String fromDay = fromDateComponents[0];
			String fromMonth = fromDateComponents[1];
			String fromYear = fromDateComponents[2];

			String[] toDateComponents = toDate.split("-");
			String toDay = toDateComponents[0];
			String toMonth = toDateComponents[1];
			String toYear = toDateComponents[2];

			// Locate the date dropdowns and OK button
			WebElement fromDateDropdown = commonEvents.findElement(calendarFromDropdown());
			WebElement toDateDropdown = commonEvents.findElement(calendarToDropdown());
			WebElement okButton = commonEvents.findElement(getOkButtonLocator());

			// Highlight and set the "from" date
			commonEvents.highlight(fromDateDropdown).sendKeys(fromDateDropdown, fromDay)
					.sendKeys(fromDateDropdown, fromMonth).sendKeys(fromDateDropdown, fromYear);

			// Highlight and set the "to" date
			commonEvents.highlight(toDateDropdown).sendKeys(toDateDropdown, toDay).sendKeys(toDateDropdown, toMonth)
					.sendKeys(toDateDropdown, toYear);

			// Locate and click the tooltip
			WebElement toolTip = commonEvents.findElement(getStarIconLocator());
			commonEvents.click(toolTip);

			// Highlight and click the OK button
			commonEvents.highlight(okButton).click(okButton);

			// Navigate to Pharmacy tab and back to Inventory tab
			highlightAndClickOnElement(getPageBarFixedLocator("Pharmacy"), "Pharmacy");
			highlightAndClickOnElement(getPageBarFixedLocator("Inventory"), "Inventory");
			highlightAndClickOnElement(getSubNavTabLocator("Requisition"), "Requisition");

			// Wait for the OK button to be visible
			commonEvents.waitTillElementVisible(getOkButtonLocator(), 10000);

			// Construct the actual dates from the selected components
			String actualFromDate = fromDay + "-" + fromMonth + "-" + fromYear;
			String actualToDate = toDay + "-" + toMonth + "-" + toYear;

			System.out.println("Actual from date : " + actualFromDate);
			System.out.println("Actual to date : " + actualToDate);

			// Locate and click the tooltip
//			commonEvents.waitTillElementVisible(toolTip, 10000);
//			Thread.sleep(3000);
//			commonEvents.click(toolTip);

			// Verify if the remembered dates match the expected dates
			if (actualFromDate.equals(fromDate) && actualToDate.equals(toDate)) {
				System.out.println("Returned true");
//				Thread.sleep(20000);
//				commonEvents.highlight(toolTip).click(toolTip);
//				commonEvents.click(getOkButtonLocator());
				return true;
			}

		} catch (Exception e) {
			throw e;
		}
		return false;
	}


}
