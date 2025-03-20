package testcases;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import pages.StartupPage;
import pages.verification_page;
import testBase.AppTestBase;
import testBase.UserActions;
import testdata.LocatorsFactory;
import org.testng.annotations.Parameters;
import org.testng.Assert;
import org.testng.annotations.Test;
import coreUtilities.testutils.ApiHelper;
import coreUtilities.utils.FileOperations;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

public class verification_testcase extends AppTestBase {
	Map<String, String> configData;
	Map<String, String> loginCredentials;
	String expectedDataFilePath = testDataFilePath + "expected_data.xlsx";
	String loginFilePath = loginDataFilePath + "Login.xlsx";
	StartupPage startupPage;
	String randomInvoiceNumber;
	LocatorsFactory locatorsFactoryInstance;
	UserActions userActionsInstance;
	verification_page verification_pageInstance;
	

	@Parameters({ "browser", "environment" })
	@BeforeClass(alwaysRun = true)
	public void initBrowser(String browser, String environment) throws Exception {
		configData = new FileOperations().readExcelPOI(config_filePath, environment);
		configData.put("url", configData.get("url").replaceAll("[\\\\]", ""));
		configData.put("browser", browser);

		boolean isValidUrl = new ApiHelper().isValidUrl(configData.get("url"));
		Assert.assertTrue(isValidUrl,
				configData.get("url") + " might be Server down at this moment. Please try after sometime.");
		initialize(configData);
		startupPage = new StartupPage(driver);
	}

	@Test(priority = 1, groups = {
			"sanity" }, description = "Precondition: User should be logged in and on the healthapp section\n"
					+ "1. Login in the healthapp application\n" + "2. Scroll down menu till verification\n"
					+ "3. Click on the verification")
	public void verifyVerificationModule() throws Exception {
		verification_pageInstance = new verification_page(driver);

		Map<String, String> verificationExpectedData = new FileOperations().readExcelPOI(expectedDataFilePath,
				"verification");
		Map<String, String> loginData = new FileOperations().readExcelPOI(loginFilePath, "credentials");

		Assert.assertTrue(verification_pageInstance.loginToHealthAppByGivenValidCredetial(loginData),
				"Login failed, Invalid credentials ! Please check manually");
		verification_pageInstance.scrollDownAndClickVerificationTab();
		System.out.println("Verification Page url : " + verificationExpectedData.get("URL"));
		Assert.assertEquals(verification_pageInstance.verifyVerificationPageUrl(), verificationExpectedData.get("URL"));
	}

	@Test(priority = 2, groups = {
			"sanity" }, description = "Precondition: User should be logged in and on the Verification section\\n\"\r\n"
					+ "	+ \"1. Click on the Verification Module drop-down arrow\\n\"\r\n" + "	+ \"2. Click on Order")

	public void verifyVerificationSubModules() {
		try {
			verification_pageInstance = new verification_page(driver);

			Assert.assertTrue(verification_pageInstance.highlightAndVerifyWhetherElementIsDisplayed(
					locatorsFactoryInstance.getPageBarFixedLocator("Inventory")));

			Assert.assertTrue(verification_pageInstance.highlightAndVerifyWhetherElementIsDisplayed(
					locatorsFactoryInstance.getPageBarFixedLocator("Pharmacy")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 3, groups = {
			"sanity" }, description = "Precondition: User should be logged in and on the Verification section\\n\"\r\n"
					+ "	+ \"1. Click on the Verification Module drop-down arrow\\n\"\r\n"
					+ "	+ \"2. Click on Inventory" + "	+ \"3. Click on Requisition")

	public void verifyInventoryTabsAndButtonsAreDisplayed() throws Exception {

		locatorsFactoryInstance = new LocatorsFactory(driver);

		Assert.assertTrue(verification_pageInstance.highlightAndVerifyWhetherElementIsDisplayed(
				locatorsFactoryInstance.getSubNavTabLocator("Requisition")));

		Assert.assertTrue(verification_pageInstance.highlightAndVerifyWhetherElementIsDisplayed(
				locatorsFactoryInstance.getSubNavTabLocator("Purchase Request")));

		Assert.assertTrue(verification_pageInstance.highlightAndVerifyWhetherElementIsDisplayed(
				locatorsFactoryInstance.getSubNavTabLocator("Purchase Order")));

		Assert.assertTrue(verification_pageInstance.highlightAndVerifyWhetherElementIsDisplayed(
				locatorsFactoryInstance.getSubNavTabLocator("GR Quality Inspection")));

		Assert.assertTrue(verification_pageInstance.highlightAndClickOnElement(
				locatorsFactoryInstance.getSubNavTabLocator("Requisition"), "Requisition"));

		Assert.assertTrue(verification_pageInstance.highlightAndVerifyWhetherElementIsDisplayed(
				locatorsFactoryInstance.getRadioButtonsLocator("pending")));

		Assert.assertTrue(verification_pageInstance.highlightAndVerifyWhetherElementIsDisplayed(
				locatorsFactoryInstance.getRadioButtonsLocator("approved")));

		Assert.assertTrue(verification_pageInstance.highlightAndVerifyWhetherElementIsDisplayed(
				locatorsFactoryInstance.getRadioButtonsLocator("rejected")));

		Assert.assertTrue(verification_pageInstance
				.highlightAndVerifyWhetherElementIsDisplayed(locatorsFactoryInstance.getRadioButtonsLocator("all")));

		Assert.assertTrue(verification_pageInstance
				.highlightAndVerifyWhetherElementIsDisplayed(locatorsFactoryInstance.favouriteOrStarIcon()));

		Assert.assertTrue(verification_pageInstance
				.highlightAndVerifyWhetherElementIsDisplayed(locatorsFactoryInstance.getOkButtonLocator()));

		Assert.assertTrue(verification_pageInstance
				.highlightAndVerifyWhetherElementIsDisplayed(locatorsFactoryInstance.searchBarId()));

		Assert.assertTrue(verification_pageInstance.highlightAndVerifyWhetherElementIsDisplayed(
				locatorsFactoryInstance.getButtonLocatorsBytext("Print")));

		Assert.assertTrue(verification_pageInstance.highlightAndVerifyWhetherElementIsDisplayed(
				locatorsFactoryInstance.getButtonLocatorsBytext("First")));

		Assert.assertTrue(verification_pageInstance.highlightAndVerifyWhetherElementIsDisplayed(
				locatorsFactoryInstance.getButtonLocatorsBytext("Previous")));

		Assert.assertTrue(verification_pageInstance.highlightAndVerifyWhetherElementIsDisplayed(
				locatorsFactoryInstance.getButtonLocatorsBytext("Next")));

		Assert.assertTrue(verification_pageInstance.highlightAndVerifyWhetherElementIsDisplayed(
				locatorsFactoryInstance.getButtonLocatorsBytext("Last")));
	}

	@Test(priority = 4, groups = {
			"sanity" }, description = "Pre condition: User should be logged in and it is on verification module\r\n"
					+ "1. Click on the inventory  \r\n" + "2. Click on the pharmacy \r\n"
					+ "3. User should navigate to the pharmacy section from the inventory section ")

	public void verifyNavigationToAnotherSubModuleAfterOpeningTheInventorySection() throws Exception {
		locatorsFactoryInstance = new LocatorsFactory(driver);

		Assert.assertTrue(verification_pageInstance.verifySelectedTabIsActiveOrNot(
				locatorsFactoryInstance.getPageBarFixedLocator("Pharmacy")), "Pharmacy page is not active");
	}

	@Test(priority = 5, groups = {
			"sanity" }, description = "Pre condition: User should be logged in and it is on verification module\r\n"
					+ "1. Click on the inventory  \r\n" + "2. Click on the pharmacy \r\n"
					+ "3. User should navigate to the pharmacy section from the inventory section ")

	public void verifyNavigationOfTabs() throws Exception {
		verification_pageInstance = new verification_page(driver);

		Assert.assertTrue(verification_pageInstance.verifyNavigationOfTabs(), "Purchase Request Tab is not active");
	}

	@Test(priority = 6, groups = {
			"sanity" }, description = "Pre condition: User should be logged in and it is on Requisition section"
					+ "1. Click on the \"From\" date" + "2. Select the \"From\" date" + "3. Click on the \"To\" date"
					+ "4. Select \"To\" date" + "5. Click on \"OK\" button")
	public void verifySearchDataByDateFilter() throws Exception {
		verification_pageInstance = new verification_page(driver);

		LocalDate currentDate = LocalDate.now();
		LocalDate date7DaysAgo = currentDate.minusDays(7);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String toDate = currentDate.format(formatter);
		String fromDate = date7DaysAgo.format(formatter);
		Assert.assertTrue(
				verification_pageInstance.verifyTheResultsDateRangeIsWithinTheSelectedRange(fromDate, toDate));
	}

	@Test(priority = 7, groups = {
			"sanity" }, description = "Pre condition: User should be logged in and it is on Inventory > Requisition section \r\n"
					+ "1. Hover the mouse over the star/favourite icon. \r\n"
					+ "2. Verify that a tooltip with the text \"Remember this date\" appears when hovering over the star.")
	public void verifyToolTipText() throws Exception {
		verification_pageInstance = new verification_page(driver);

		Map<String, String> pharmacyExpectedData = new FileOperations().readExcelPOI(expectedDataFilePath, "verification");
		Assert.assertEquals(verification_pageInstance.verifyToolTipText(), pharmacyExpectedData.get("favouriteIcon"));
	}

	@Test(priority = 8, groups = {
			"sanity" }, description = "Pre condition: User should be logged in and it is on Inventory > Requisition section \r\n"
					+ "1. Hover the mouse over the star/favourite icon. \r\n"
					+ "2. Verify that a tooltip with the text \"Remember this date\" appears when hovering over the star.")
	public void verifyDatesAreRemeberedCorrectly() throws Exception {
		verification_pageInstance = new verification_page(driver);

		LocalDate currentDate = LocalDate.now();
		LocalDate date50DaysAgo = currentDate.minusDays(50);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String toDate = currentDate.format(formatter);
		String fromDate = date50DaysAgo.format(formatter);

		System.out.println("From Date : " + fromDate + ", To Date : " + toDate);
		Assert.assertTrue(verification_pageInstance.verifyDatesAreRememberedCorrectly(fromDate, toDate));
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		System.out.println("before closing the browser");
		browserTearDown();
	}

	@AfterMethod
	public void retryIfTestFails() throws Exception {
		startupPage.navigateToUrl(configData.get("url"));
	}
}
