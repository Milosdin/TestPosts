package cubes.tests;

import static org.junit.Assert.*;
import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cubes.webpages.LoginPage;
import cubes.webpages.AddPostFormPage;
import cubes.webpages.PostsFormPage;

  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
  public class TestPosts {

	private static WebDriver driver;
	private static WebDriverWait driverWait;
	private static LoginPage loginPage;
	private static AddPostFormPage addPost;
	private static PostsFormPage posts;
	private static final String EMAIL = "kursqa@cubes.edu.rs";
	private static final String PASSWORD = "cubesqa";
	private static final String TITLE_VALID = "MilosMilosMilosMilos";
	private static final String TITLE_VALID_FOR_DELETE = "DELETE_MilosMilosMilosMilos";
	private static final String TITLE_VALID_IMPORTANT_YES_DISABLE = "IMPORTANT_YES_DISABLE_MilosMilosMilosMilos";
	private static final String TITLE_VALID_IMPORTANT_NO_DISABLE = "IMPORTANT_NO_DISABLE_MilosMilosMilosMilos";
	private static final String DESCRIPTION_VALID = "descriptiodescriptiodescriptiodescriptiodescriptio";
	private static final String TAG = "Default TAG NE BRISATI";
	private static final String CONTENT = "content";
	private static final String CATEGORY = "EastEvafurt";
	private static final String AUTHOR = "Polaznik Kursa";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Korisnik\\Desktop\\webdriverCromeFire\\chromedriver-win64\\chromedriver.exe");
		driver = new ChromeDriver();
		driverWait = new WebDriverWait(driver, Duration.ofSeconds(7));
		driver.manage().window().maximize();

		loginPage = new LoginPage(driver, driverWait);
		addPost = new AddPostFormPage(driver, driverWait);
		posts = new PostsFormPage(driver, driverWait);

		loginPage.login(EMAIL, PASSWORD);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.quit();
	}

	@Before
	public void setUp() throws Exception {
		posts.postPage();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void tc006TestBlogSidebarLinks() {
		checkMenu("Sliders");
		checkMenu("Post Categories");
		checkMenu("Tags");
		checkMenu("Posts");
		checkMenu("Comments");
		checkMenu("Users");
	}
	
	@Test
	public void tc007TestResponsiveNavigationBar() {
		addPost.clickOnResponsiveNavigationBarButton();
		
		assertEquals(addPost.miniSideBarCollapse(), true);
	}
	
	@Test
	public void tc008TestProfileIconDropDownMenu() {
		addPost.dropDownMenuClick();
		
		assertEquals(addPost.dropDownMenuShow(), true);
	}
	
	@Test
	public void tc009TestHomeLink() {
		addPost.homeMenuClick();
		
		assertEquals(driver.getCurrentUrl(),"https://testblog.kurs-qa.cubes.edu.rs/admin");
	}

	@Test
	public void tc010TestTitleSearch() {
		posts.titleSearch(TITLE_VALID);

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
	}

	@Test
	public void tc011TestTitleSearchTagSearch() {
		posts.titleSearch(TITLE_VALID);
		posts.withTag(TAG);

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.tagConfirm(TAG), true);
	}

	@Test
	public void tc012TestTagSearch() {
		posts.titleSearch("");
		posts.withTag(TAG);

		assertEquals(addPost.tagConfirm(TAG), true);
	}
	
	@Test
	public void tc013TestTitleSearchTagSearchAuthorSearch() {
		posts.titleSearch(TITLE_VALID);
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
	}

	@Test
	public void tc014TestTitleSearchTagSearchAuthorSearchCategorySearch() {
		posts.titleSearch(TITLE_VALID);
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
	}

	@Test
	public void tc015TestTitleSearchTagSearchAuthorSearchImportantNo() {
		posts.titleSearch(TITLE_VALID);
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.importantNo();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.importantNoConfirm(), true);
	}

	@Test
	public void tc016TestTitleSearchTagSearchAuthorSearchImportantYes() {
		posts.titleSearch(TITLE_VALID);
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.importantYes();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.importantYesConfirm(), true);
	}

	@Test
	public void tc017TestTitleSearchTagSearchAuthorSearchStatusEnabled() {
		posts.titleSearch(TITLE_VALID);
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.statusEnabled();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}

	@Test
	public void tc018TestTitleSearchTagSearchAuthorSearchStatusDisabled() {
		titleValidImportantYesStatusDisabled();
		disablePost(TITLE_VALID_IMPORTANT_YES_DISABLE);
		
		titleValidImportantNoStatusDisabled();
		disablePost(TITLE_VALID_IMPORTANT_NO_DISABLE);
		
		posts.postPage();
		
		posts.titleSearch(TITLE_VALID_IMPORTANT_NO_DISABLE);
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.statusDisabled();

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_NO_DISABLE), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}
	
	@Test
	public void tc019TestTitleSearchTagSearchAuthorSearchCategorySearchImportantNo() {
		posts.titleSearch(TITLE_VALID);
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);
		posts.importantNo();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantNoConfirm(), true);
	}

	@Test
	public void tc020TestTitleSearchTagSearchAuthorSearchCategorySearchImportantYes() {
		posts.titleSearch(TITLE_VALID);
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);
		posts.importantYes();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.importantYesConfirm(), true);
	}

	@Test
	public void tc021TestTitleSearchTagSearchAuthorSearchCategorySearchStatusEnabled() {
		posts.titleSearch(TITLE_VALID);
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);
		posts.statusEnabled();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}

	@Test
	public void tc022TestTitleSearchTagSearchAuthorSearchCategorySearchStatusDisabled() {
		posts.titleSearch(TITLE_VALID_IMPORTANT_NO_DISABLE);
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);
		posts.statusDisabled();

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_NO_DISABLE), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}

	@Test
	public void tc023TestTitleSearchTagSearchAuthorSearchImportantNoStatusEnabled() {
		posts.titleSearch(TITLE_VALID);
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.importantNo();
		posts.statusEnabled();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.importantNoConfirm(), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}

	@Test
	public void tc024TestTitleSearchTagSearchAuthorSearchImportantNoStatusDisabled() {
		posts.titleSearch(TITLE_VALID_IMPORTANT_NO_DISABLE);
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.importantNo();
		posts.statusDisabled();

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_NO_DISABLE), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.importantNoConfirm(), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}
	
	@Test
	public void tc025TestTitleSearchTagSearchAuthorSearchImportantYesStatusEnabled() {
		posts.titleSearch(TITLE_VALID);
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.importantYes();
		posts.statusEnabled();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.importantYesConfirm(), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}

	@Test
	public void tc026TestTitleSearchTagSearchAuthorSearchImportantYesStatusDisabled() {
		
		posts.titleSearch(TITLE_VALID_IMPORTANT_YES_DISABLE);
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.importantYes();
		posts.statusDisabled();

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_YES_DISABLE), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.importantYesConfirm(), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}

	@Test
	public void tc027TestTitleSearchTagSearchCategorySearch() {
		posts.titleSearch(TITLE_VALID);
		posts.withTag(TAG);
		posts.categorySearch(CATEGORY);

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
	}

	@Test
	public void tc028TestTitleSearchTagSearchCategorySearchImportantYes() {
		posts.titleSearch(TITLE_VALID);
		posts.withTag(TAG);
		posts.categorySearch(CATEGORY);
		posts.importantYes();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantYesConfirm(), true);
	}
	
	@Test
	public void tc029TestTitleSearchTagSearchCategorySearchImportantNo() {
		posts.titleSearch(TITLE_VALID);
		posts.withTag(TAG);
		posts.categorySearch(CATEGORY);
		posts.importantNo();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantNoConfirm(), true);
	}

	@Test
	public void tc030TestTitleSearchTagSearchCategorySearchStatusEnabled() {
		posts.titleSearch(TITLE_VALID);
		posts.withTag(TAG);
		posts.categorySearch(CATEGORY);
		posts.statusEnabled();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}

	@Test
	public void tc031TestTitleSearchTagSearchCategorySearchStatusDisabled() {
		posts.titleSearch(TITLE_VALID_IMPORTANT_NO_DISABLE);
		posts.withTag(TAG);
		posts.categorySearch(CATEGORY);
		posts.statusDisabled();

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_NO_DISABLE), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}

	@Test
	public void tc032TestTitleSearchTagSearchCategorySearchImportantYesStatusEnabled() {
		posts.titleSearch(TITLE_VALID);
		posts.withTag(TAG);
		posts.categorySearch(CATEGORY);
		posts.importantYes();
		posts.statusEnabled();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantYesConfirm(), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}
	
	@Test
	public void tc033TestTitleSearchTagSearchCategorySearchImportantYesStatusDisabled() {
		posts.titleSearch(TITLE_VALID_IMPORTANT_YES_DISABLE);
		posts.withTag(TAG);
		posts.categorySearch(CATEGORY);
		posts.importantYes();
		posts.statusDisabled();

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_YES_DISABLE), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantYesConfirm(), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}

	@Test
	public void tc034TestTitleSearchTagSearchCategorySearchImportantNoStatusEnabled() {
		posts.titleSearch(TITLE_VALID);
		posts.withTag(TAG);
		posts.categorySearch(CATEGORY);
		posts.importantNo();
		posts.statusEnabled();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantNoConfirm(), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}
	
	@Test
	public void tc035TestTitleSearchTagSearchCategorySearchImportantNoStatusDisabled() {
		
		posts.titleSearch(TITLE_VALID_IMPORTANT_NO_DISABLE);
		posts.withTag(TAG);
		posts.categorySearch(CATEGORY);
		posts.importantNo();
		posts.statusDisabled();

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_NO_DISABLE), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantNoConfirm(), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}

	@Test
	public void tc036TestTitleSearchTagSearchImportantYes() {
		posts.titleSearch(TITLE_VALID);
		posts.withTag(TAG);
		posts.importantYes();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.importantYesConfirm(), true);
	}
	
	@Test
	public void tc037TestTitleSearchTagSearchImportantNo() {
		posts.titleSearch(TITLE_VALID);
		posts.withTag(TAG);
		posts.importantNo();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.importantNoConfirm(), true);
	}

	@Test
	public void tc038TestTitleSearchTagSearchStatusEnabled() {
		posts.titleSearch(TITLE_VALID);
		posts.withTag(TAG);
		posts.statusEnabled();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}
	
	@Test
	public void tc039TestTitleSearchTagSearchStatusDisabled() {
		posts.titleSearch(TITLE_VALID_IMPORTANT_NO_DISABLE);
		posts.withTag(TAG);
		posts.statusDisabled();

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_NO_DISABLE), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}

	@Test
	public void tc040TestTitleSearchTagSearchImportantYesStatusEnabled() {
		posts.titleSearch(TITLE_VALID);
		posts.withTag(TAG);
		posts.importantYes();
		posts.statusEnabled();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.importantYesConfirm(), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}

	@Test
	public void tc041TestTitleSearchTagSearchImportantYesStatusDisabled() {
		posts.titleSearch(TITLE_VALID_IMPORTANT_YES_DISABLE);
		posts.withTag(TAG);
		posts.importantYes();
		posts.statusDisabled();

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_YES_DISABLE), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.importantYesConfirm(), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}
	
	@Test
	public void tc042TestTitleSearchTagSearchImportantNoStatusEnabled() {
		posts.titleSearch(TITLE_VALID);
		posts.withTag(TAG);
		posts.importantNo();
		posts.statusEnabled();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.importantNoConfirm(), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}

	@Test
	public void tc043TestTitleSearchTagSearchImportantNoStatusDisabled() {
		posts.titleSearch(TITLE_VALID_IMPORTANT_NO_DISABLE);
		posts.withTag(TAG);
		posts.importantNo();
		posts.statusDisabled();

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_NO_DISABLE), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.importantNoConfirm(), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}
	
	@Test
	public void tc044TestTitleSearchTagSearchAuthorSearchCategorySearchImportantNoStatusDisabled() {
		posts.titleSearch(TITLE_VALID_IMPORTANT_NO_DISABLE);
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);
		posts.importantNo();
		posts.statusDisabled();

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_NO_DISABLE), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantNoConfirm(), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}
	@Test
	public void tc045TestTitleSearchTagSearchAuthorSearchCategorySearchImportantYesStatusDisabled() {
		posts.titleSearch(TITLE_VALID_IMPORTANT_YES_DISABLE);
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);
		posts.importantYes();
		posts.statusDisabled();

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_YES_DISABLE), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantYesConfirm(), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}
	
	@Test
	public void tc046TestTitleSearchTagSearchAuthorSearchCategorySearchImportantYesStatusEnabled() {
		posts.titleSearch(TITLE_VALID);
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);
		posts.importantYes();
		posts.statusEnabled();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantYesConfirm(), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}
	
	@Test
	public void tc047TestTitleSearchTagSearchAuthorSearchCategorySearchImportantNoStatusEnabled() {
		posts.titleSearch(TITLE_VALID);
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);
		posts.importantNo();
		posts.statusEnabled();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantNoConfirm(), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}
	
	@Test
	public void tc048TestTitleSearchAuthorSearch() {
		posts.titleSearch(TITLE_VALID);
		posts.authorSearch(AUTHOR);

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
	}
	
	@Test
	public void tc049TestTitleSearchAuthorSearchCategorySearch() {
		posts.titleSearch(TITLE_VALID);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
	}
	
	@Test
	public void tc050TestTitleSearchAuthorSearchImportantNo() {
		posts.titleSearch(TITLE_VALID);
		posts.authorSearch(AUTHOR);
		posts.importantNo();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.importantNoConfirm(), true);
	}
	@Test
	public void tc051TestTitleSearchAuthorSearchImportantYes() {
		posts.titleSearch(TITLE_VALID);
		posts.authorSearch(AUTHOR);
		posts.importantYes();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.importantYesConfirm(), true);
	}

	@Test
	public void tc052TestTitleSearchAuthorSearchStatusEnabled() {
		posts.titleSearch(TITLE_VALID);
		posts.authorSearch(AUTHOR);
		posts.statusEnabled();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}

	@Test
	public void tc053TestTitleSearchAuthorSearchStatusDisabled() {
		posts.titleSearch(TITLE_VALID_IMPORTANT_NO_DISABLE);
		posts.authorSearch(AUTHOR);
		posts.statusDisabled();

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_NO_DISABLE), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}
	
	@Test
	public void tc054TestTitleSearchAuthorSearchCategorySearchImportantNo() {
		posts.titleSearch(TITLE_VALID);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);
		posts.importantNo();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantNoConfirm(), true);
	}

	@Test
	public void tc055TestTitleSearchAuthorSearchCategorySearchImportantYes() {
		posts.titleSearch(TITLE_VALID);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);
		posts.importantYes();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.importantYesConfirm(), true);
	}

	@Test
	public void tc056TestTitleSearchAuthorSearchCategorySearchStatusEnabled() {
		posts.titleSearch(TITLE_VALID);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);
		posts.statusEnabled();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}

	@Test
	public void tc057TestTitleSearchAuthorSearchCategorySearchStatusDisabled() {
		posts.titleSearch(TITLE_VALID_IMPORTANT_NO_DISABLE);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);
		posts.statusDisabled();

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_NO_DISABLE), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}

	@Test
	public void tc058TestTitleSearchAuthorSearchImportantNoStatusEnabled() {
		posts.titleSearch(TITLE_VALID);
		posts.authorSearch(AUTHOR);
		posts.importantNo();
		posts.statusEnabled();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.importantNoConfirm(), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}

	@Test
	public void tc059TestTitleSearchAuthorSearchImportantNoStatusDisabled() {
		posts.titleSearch(TITLE_VALID_IMPORTANT_NO_DISABLE);
		posts.authorSearch(AUTHOR);
		posts.importantNo();
		posts.statusDisabled();

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_NO_DISABLE), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.importantNoConfirm(), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}
	
	@Test
	public void tc060TestTitleSearchAuthorSearchImportantYesStatusEnabled() {
		posts.titleSearch(TITLE_VALID);
		posts.authorSearch(AUTHOR);
		posts.importantYes();
		posts.statusEnabled();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.importantYesConfirm(), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}

	@Test
	public void tc061TestTitleSearchAuthorSearchImportantYesStatusDisabled() {
		posts.titleSearch(TITLE_VALID_IMPORTANT_YES_DISABLE);
		posts.authorSearch(AUTHOR);
		posts.importantYes();
		posts.statusDisabled();

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_YES_DISABLE), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.importantYesConfirm(), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}

	@Test
	public void tc062TestTitleSearchCategorySearch() {
		posts.titleSearch(TITLE_VALID);
		posts.categorySearch(CATEGORY);

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
	}

	@Test
	public void tc063TestTitleSearchCategorySearchImportantYes() {
		posts.titleSearch(TITLE_VALID);
		posts.categorySearch(CATEGORY);
		posts.importantYes();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantYesConfirm(), true);
	}
	
	@Test
	public void tc064TestTitleSearchCategorySearchImportantNo() {
		posts.titleSearch(TITLE_VALID);
		posts.categorySearch(CATEGORY);
		posts.importantNo();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantNoConfirm(), true);
	}

	@Test
	public void tc065TestTitleSearchCategorySearchStatusEnabled() {
		posts.titleSearch(TITLE_VALID);
		posts.categorySearch(CATEGORY);
		posts.statusEnabled();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}

	@Test
	public void tc066TestTitleSearchCategorySearchStatusDisabled() {
		posts.titleSearch(TITLE_VALID_IMPORTANT_NO_DISABLE);
		posts.categorySearch(CATEGORY);
		posts.statusDisabled();

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_NO_DISABLE), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}

	@Test
	public void tc067TestTitleSearchCategorySearchImportantYesStatusEnabled() {
		posts.titleSearch(TITLE_VALID);
		posts.categorySearch(CATEGORY);
		posts.importantYes();
		posts.statusEnabled();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantYesConfirm(), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}
	
	@Test
	public void tc068TestTitleSearchCategorySearchImportantYesStatusDisabled() {
		posts.titleSearch(TITLE_VALID_IMPORTANT_YES_DISABLE);
		posts.categorySearch(CATEGORY);
		posts.importantYes();
		posts.statusDisabled();

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_YES_DISABLE), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantYesConfirm(), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}

	@Test
	public void tc069TestTitleSearchCategorySearchImportantNoStatusEnabled() {
		posts.titleSearch(TITLE_VALID);
		posts.categorySearch(CATEGORY);
		posts.importantNo();
		posts.statusEnabled();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantNoConfirm(), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}
	
	@Test
	public void tc070TestTitleSearchCategorySearchImportantNoStatusDisabled() {
		posts.titleSearch(TITLE_VALID_IMPORTANT_NO_DISABLE);
		posts.categorySearch(CATEGORY);
		posts.importantNo();
		posts.statusDisabled();

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_NO_DISABLE), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantNoConfirm(), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}

	@Test
	public void tc071TestTitleSearchImportantYes() {
		posts.titleSearch(TITLE_VALID);
		posts.importantYes();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.importantYesConfirm(), true);
	}
	
	@Test
	public void tc072TestTitleSearchImportantNo() {
		posts.titleSearch(TITLE_VALID);
		posts.importantNo();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.importantNoConfirm(), true);
	}

	@Test
	public void tc073TestTitleSearchStatusEnabled() {
		posts.titleSearch(TITLE_VALID);
		posts.statusEnabled();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}
	
	@Test
	public void tc074TestTitleSearchStatusDisabled() {
		posts.titleSearch(TITLE_VALID_IMPORTANT_NO_DISABLE);
		posts.statusDisabled();

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_NO_DISABLE), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}

	@Test
	public void tc075TestTitleSearchImportantYesStatusEnabled() {
		posts.titleSearch(TITLE_VALID);
		posts.importantYes();
		posts.statusEnabled();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.importantYesConfirm(), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}

	@Test
	public void tc076TestTitleSearchImportantYesStatusDisabled() {
		posts.titleSearch(TITLE_VALID_IMPORTANT_YES_DISABLE);
		posts.importantYes();
		posts.statusDisabled();

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_YES_DISABLE), true);
		assertEquals(addPost.importantYesConfirm(), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}
	
	@Test
	public void tc077TestTitleSearchImportantNoStatusEnabled() {
		posts.titleSearch(TITLE_VALID);
		posts.importantNo();
		posts.statusEnabled();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.importantNoConfirm(), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}

	@Test
	public void tc078TestTitleSearchImportantNoStatusDisabled() {
		posts.titleSearch(TITLE_VALID_IMPORTANT_NO_DISABLE);
		posts.importantNo();
		posts.statusDisabled();

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_NO_DISABLE), true);
		assertEquals(addPost.importantNoConfirm(), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}
	
	@Test
	public void tc079TestTitleSearchAuthorSearchCategorySearchImportantNoStatusDisabled() {
		posts.titleSearch(TITLE_VALID_IMPORTANT_NO_DISABLE);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);
		posts.importantNo();
		posts.statusDisabled();

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_NO_DISABLE), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantNoConfirm(), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}
	@Test
	public void tc080TestTitleSearchAuthorSearchCategorySearchImportantYesStatusDisabled() {
		posts.titleSearch(TITLE_VALID_IMPORTANT_YES_DISABLE);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);
		posts.importantYes();
		posts.statusDisabled();

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_YES_DISABLE), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantYesConfirm(), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}
	
	@Test
	public void tc081TestTitleSearchAuthorSearchCategorySearchImportantYesStatusEnabled() {
		posts.titleSearch(TITLE_VALID);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);
		posts.importantYes();
		posts.statusEnabled();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantYesConfirm(), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}
	
	@Test
	public void tc082TestTitleSearchAuthorSearchCategorySearchImportantNoStatusEnabled() {
		posts.titleSearch(TITLE_VALID);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);
		posts.importantNo();
		posts.statusEnabled();

		assertEquals(posts.titleConfirm(TITLE_VALID), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantNoConfirm(), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}
	
	@Test
	public void tc083TestTagSearchAuthorSearch() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
	}

	@Test
	public void tc084TestTagSearchAuthorSearchCategorySearch() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
	}

	@Test
	public void tc085TestTagSearchAuthorSearchImportantNo() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.importantNo();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.importantNoConfirm(), true);
	}

	@Test
	public void tc086TestTagSearchAuthorSearchImportantYes() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.importantYes();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.importantYesConfirm(), true);
	}

	@Test
	public void tc087TestTagSearchAuthorSearchStatusEnabled() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.statusEnabled();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}

	@Test
	public void tc088TestTagSearchAuthorSearchStatusDisabled() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.statusDisabled();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}
	
	@Test
	public void tc089TestTagSearchAuthorSearchCategorySearchImportantNo() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);
		posts.importantNo();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantNoConfirm(), true);
	}

	@Test
	public void tc090TestTagSearchAuthorSearchCategorySearchImportantYes() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);
		posts.importantYes();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.importantYesConfirm(), true);
	}

	@Test
	public void tc091TestTagSearchAuthorSearchCategorySearchStatusEnabled() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);
		posts.statusEnabled();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}

	@Test
	public void tc092TestTagSearchAuthorSearchCategorySearchStatusDisabled() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);
		posts.statusDisabled();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}

	@Test
	public void tc093TestTagSearchAuthorSearchImportantNoStatusEnabled() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.importantNo();
		posts.statusEnabled();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.importantNoConfirm(), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}

	@Test
	public void tc094TestTagSearchAuthorSearchImportantNoStatusDisabled() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.importantNo();
		posts.statusDisabled();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.importantNoConfirm(), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}
	
	@Test
	public void tc095TestTagSearchAuthorSearchImportantYesStatusEnabled() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.importantYes();
		posts.statusEnabled();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.importantYesConfirm(), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}

	@Test
	public void tc096TestTagSearchAuthorSearchImportantYesStatusDisabled() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.importantYes();
		posts.statusDisabled();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.importantYesConfirm(), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}

	@Test
	public void tc097TestTagSearchCategorySearch() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.categorySearch(CATEGORY);

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
	}

	@Test
	public void tc098TestTagSearchCategorySearchImportantYes() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.categorySearch(CATEGORY);
		posts.importantYes();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantYesConfirm(), true);
	}
	
	@Test
	public void tc099TestTagSearchCategorySearchImportantNo() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.categorySearch(CATEGORY);
		posts.importantNo();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantNoConfirm(), true);
	}

	@Test
	public void tc100TestTagSearchCategorySearchStatusEnabled() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.categorySearch(CATEGORY);
		posts.statusEnabled();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}

	@Test
	public void tc101TestTagSearchCategorySearchStatusDisabled() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.categorySearch(CATEGORY);
		posts.statusDisabled();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}

	@Test
	public void tc102TestTagSearchCategorySearchImportantYesStatusEnabled() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.categorySearch(CATEGORY);
		posts.importantYes();
		posts.statusEnabled();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantYesConfirm(), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}
	
	@Test
	public void tc103TestTagSearchCategorySearchImportantYesStatusDisabled() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.categorySearch(CATEGORY);
		posts.importantYes();
		posts.statusDisabled();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantYesConfirm(), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}

	@Test
	public void tc104TestTagSearchCategorySearchImportantNoStatusEnabled() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.categorySearch(CATEGORY);
		posts.importantNo();
		posts.statusEnabled();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantNoConfirm(), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}
	
	@Test
	public void tc105TestTagSearchCategorySearchImportantNoStatusDisabled() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.categorySearch(CATEGORY);
		posts.importantNo();
		posts.statusDisabled();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantNoConfirm(), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}

	@Test
	public void tc106TestTagSearchImportantYes() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.importantYes();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.importantYesConfirm(), true);
	}
	
	@Test
	public void tc107TestTagSearchImportantNo() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.importantNo();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.importantNoConfirm(), true);
	}

	@Test
	public void tc108TestTagSearchStatusEnabled() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.statusEnabled();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}
	
	@Test
	public void tc109TestTagSearchStatusDisabled() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.statusDisabled();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}

	@Test
	public void tc110TestTagSearchImportantYesStatusEnabled() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.importantYes();
		posts.statusEnabled();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.importantYesConfirm(), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}

	@Test
	public void tc111TestTagSearchImportantYesStatusDisabled() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.importantYes();
		posts.statusDisabled();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.importantYesConfirm(), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}
	
	@Test
	public void tc112TestTagSearchImportantNoStatusEnabled() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.importantNo();
		posts.statusEnabled();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.importantNoConfirm(), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}

	@Test
	public void tc113TestTagSearchImportantNoStatusDisabled() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.importantNo();
		posts.statusDisabled();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.importantNoConfirm(), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}
	
	@Test
	public void tc114TestTagSearchAuthorSearchCategorySearchImportantNoStatusDisabled() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);
		posts.importantNo();
		posts.statusDisabled();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantNoConfirm(), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}
	@Test
	public void tc115TestTagSearchAuthorSearchCategorySearchImportantYesStatusDisabled() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);
		posts.importantYes();
		posts.statusDisabled();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantYesConfirm(), true);
		assertEquals(addPost.statusDisabledConfirm(), true);
	}
	
	@Test
	public void tc116TestTagSearchAuthorSearchCategorySearchImportantYesStatusEnabled() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);
		posts.importantYes();
		posts.statusEnabled();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantYesConfirm(), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}
	
	@Test
	public void tc117TestTagSearchAuthorSearchCategorySearchImportantNoStatusEnabled() {
		posts.titleSearch("");
		posts.withTag(TAG);
		posts.authorSearch(AUTHOR);
		posts.categorySearch(CATEGORY);
		posts.importantNo();
		posts.statusEnabled();

		assertEquals(addPost.tagConfirm(TAG), true);
		assertEquals(addPost.authorConfirm(AUTHOR), true);
		assertEquals(addPost.categoryConfirm(CATEGORY), true);
		assertEquals(addPost.importantNoConfirm(), true);
		assertEquals(addPost.statusEnabledConfirm(), true);
	}
	
	@Test
	public void tc118TestClickOnTheAddNewPostButton() {
		posts.clickOnTheAddNewPostButton();

		assertEquals(driver.getCurrentUrl(), "https://testblog.kurs-qa.cubes.edu.rs/admin/posts/add");

	}

	@Test
	public void tc119TestClickOnTheShow10EntriesSelectButton() {
		posts.clickOnTheShow10EntriesSelectButton();
		
		assertEquals(posts.rowsSize(), 10);
	}

	@Test
	public void tc120TestClickOnTheShow25EntriesSelectButton() {
		posts.clickOnTheShow25EntriesSelectButton();

		assertEquals(posts.rowsSize(), 25);
	}

	@Test
	public void tc121TestClickOnTheShow50EntriesSelectButton() {
		posts.clickOnTheShow50EntriesSelectButton();

		assertEquals(posts.rowsSize(), 50);
	}

	@Test
	public void tc122TestClickOnTheShow100EntriesSelectButton() {
		addPost.clickOnTheShow100EntriesSelectButton();

		assertEquals(posts.rowsSize(), 100);
	}

	@Test
	public void tc123TestSearchFieldTitleSearching() {
		posts.searchField(TITLE_VALID_IMPORTANT_YES_DISABLE);

		assertEquals(posts.titleConfirm(TITLE_VALID_IMPORTANT_YES_DISABLE), true);
	}
	
	@Test
	public void tc124TestNumberingColumnDESCSortButton() {
		posts.clickOnNumberingColumnWithDESCSortButton();
		
		assertEquals(posts.clickOnNumberingColumnDESCSortButtonConfirm(),true);
	}
	
	@Test
	public void tc125TestNumberingColumnASCSortButton() {
		posts.clickOnNumberingColumnWithDESCSortButton();
		
		posts.clickOnNumberingColumnWithASCSortButton();;
		
		assertEquals(posts.clickOnNumberingColumnASCSortButtonConfirm(),true);
	}
	
	@Test
	public void tc126TestTitleColumnASCSortButton() {
		posts.clickOnColumnSortButton("Title");

		assertEquals(posts.clickOnColumnASCSortButtonConfirm("Title"),true);
	}
	@Test
	public void tc127TestTitleColumnDESCSortButton() {
		posts.clickOnColumnSortButton("Title");
		
		posts.clickOnColumnSortButton("Title");
		
		assertEquals(posts.clickOnColumnDESCSortButtonConfirm("Title"),true);
	}
	
	@Test
	public void tc128TestAuthorColumnASCSortButton() {
		posts.clickOnColumnSortButton("Author");
		
		assertEquals(posts.clickOnColumnASCSortButtonConfirm("Author"),true);
	}
	
	@Test
	public void tc129TestAuthorColumnDESCSortButton() {
		posts.clickOnColumnSortButton("Author");
		
		posts.clickOnColumnSortButton("Author");
		
		assertEquals(posts.clickOnColumnDESCSortButtonConfirm("Author"),true);
	}
	
	@Test
	public void tc130TestCategoryColumnASCSortButton() {
		posts.clickOnColumnSortButton("Category");
		
		assertEquals(posts.clickOnColumnASCSortButtonConfirm("Category"),true);
	}
	
	@Test
	public void tc131TestCategoryColumnDESCSortButton() {
		posts.clickOnColumnSortButton("Category");
		
		posts.clickOnColumnSortButton("Category");
		
		assertEquals(posts.clickOnColumnDESCSortButtonConfirm("Category"),true);
	}
	@Test
	public void tc132TestCreatedAtColumnASCSortButton() {
		posts.clickOnColumnSortButton("Created At");
		
		assertEquals(posts.clickOnColumnASCSortButtonConfirm("Created At"),true);
	}
	
	@Test
	public void tc133TestCreatedAtColumnDESCSortButton() {
		posts.clickOnColumnSortButton("Created At");
	
		posts.clickOnColumnSortButton("Created At");
		
		assertEquals(posts.clickOnColumnDESCSortButtonConfirm("Created At"),true);
	}

	@Test
	public void tc134TestClickOnViewPostButton() throws InterruptedException {
		viewPost(TITLE_VALID);
		driver.getTitle();
		

		assertEquals(posts.blogPostConfirm(TITLE_VALID),true);
	}

	@Test
	public void tc135TestClickOnDisablePostButton() throws InterruptedException {
		disablePost(TITLE_VALID);
		
		assertEquals(addPost.statusDisabledConfirm(), true);
	}

	@Test
	public void tc136TestClickOnEnablePostButton() throws InterruptedException {
		enablePost(TITLE_VALID);
		
		assertEquals(addPost.statusEnabledConfirm(), true);
	}

	@Test
	public void tc137TestClickOnImportantPostButton() throws InterruptedException {
		importantPost(TITLE_VALID);
		
		assertEquals(addPost.importantYesConfirm(), true);
	}
	
	@Test
	public void tc138TestClickOnUnimportantPostButton() throws InterruptedException {
		unimportantPost(TITLE_VALID);
		
		assertEquals(addPost.importantNoConfirm(), true);
	}

	@Test
	public void tc139TestClickOnEditPostButton() throws InterruptedException {
		editPost(TITLE_VALID);

		assertEquals(posts.editPostPageConfirm(), "Edit Post");
	}
	
	@Test
	public void tc140TestClickOnDeletePostButton() throws InterruptedException {
	    deletePost(TITLE_VALID_FOR_DELETE);
	    
		assertEquals(addPost.isTitleInList(TITLE_VALID_FOR_DELETE),false);
	}
	
	@Test
	public void tc141TestNextPageButton() throws InterruptedException {
		posts.clickOnNextPageButton();

		assertEquals(posts.nextButtonConfirm(),true);
	}
	
	@Test
	public void tc142TestPreviousPageButton() throws InterruptedException {
		posts.clickOnPreviousPageButton();

		assertEquals(posts.previousButtonConfirm(),true);
	}
	
	@Test
	public void tc143TestClickOnCubesSchoolLink() {
		addPost.clickOnCubesSchoolLink();

		assertEquals(driver.getCurrentUrl(), "https://cubes.edu.rs/");

	}

	
	public void viewPost(String view) {
		addPost.clickOnTheShow100EntriesSelectButton();
		
		posts.clickOnPage2();
		
		posts.viewPostButton(view);
	}
	
	public void disablePost(String disable) {
		addPost.clickOnTheShow100EntriesSelectButton();
		
		posts.clickOnPage2();

		posts.disablePostButton(disable);
		
		posts.dialogDisable();
	}
	
	public void enablePost(String enable) {
        addPost.clickOnTheShow100EntriesSelectButton();
		
		posts.clickOnPage2();
		
		posts.enablePostButton(enable);
		
		posts.dialogEnable();
	}
	
	public void unimportantPost(String unimportant) {
		addPost.clickOnTheShow100EntriesSelectButton();
		
		posts.clickOnPage2();

		posts.unimportantPostButton(unimportant);
		
		posts.dialogUnimportantPost();
	}
	public void importantPost(String unimportant) {
		addPost.clickOnTheShow100EntriesSelectButton();
		
		posts.clickOnPage2();

		posts.importantPostButton(unimportant);
		
		posts.dialogImportantPost();
	}
	public void editPost(String edit) {
		addPost.clickOnTheShow100EntriesSelectButton();
		
		posts.clickOnPage2();

		posts.editPostButton(edit);
	}
	public void deletePost(String delete) {
		addPost.clickOnTheShow100EntriesSelectButton();
		
		posts.clickOnPage2();

		posts.deletePostButton(delete);
		
		posts.dialogDeletePost();
	}
	public void titleValidImportantNoStatusDisabled() {
		addPost.addPostPage();
		
		addPost.categoryInput(CATEGORY);
		addPost.titleInput(TITLE_VALID_IMPORTANT_NO_DISABLE);
		addPost.descriptionInput(DESCRIPTION_VALID);
		addPost.contentInput(CONTENT);
		addPost.tagsClick(TAG);
		addPost.addButton();
	}
	public void titleValidImportantYesStatusDisabled() {
		addPost.addPostPage();
		
		addPost.categoryInput(CATEGORY);
		addPost.titleInput(TITLE_VALID_IMPORTANT_YES_DISABLE);
		addPost.descriptionInput(DESCRIPTION_VALID);
		addPost.importantYes();
		addPost.contentInput(CONTENT);
		addPost.tagsClick(TAG);
		addPost.addButton();
	}
	 public void checkMenu(String title) {
			
	    addPost.openMenu(title);
			
		assertEquals(addPost.menuConfirm(title),true);
			
		posts.postPage();
	}
	
}
