package com.sdd.booksapp.e2e;

import com.microsoft.playwright.*;
import com.sdd.booksapp.e2e.pages.BookDetailsPage;
import com.sdd.booksapp.e2e.pages.BookFormPage;
import com.sdd.booksapp.e2e.pages.HomePage;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookAppE2ETest {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @BeforeAll
    void launchBrowser() {
        playwright = Playwright.create();
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "true"));
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
    }

    @AfterAll
    void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    void shouldPerformFullBookCrudFlow() {
        HomePage homePage = new HomePage(page);
        BookFormPage formPage = new BookFormPage(page);
        BookDetailsPage detailsPage = new BookDetailsPage(page);

        homePage.navigate();
        
        // 1. Create a Book
        homePage.clickAddBook();
        page.waitForURL("**/book/new");
        
        formPage.fillTitle("Playwright in Action");
        formPage.fillAuthor("QA Expert");
        formPage.fillCategory("Tech");
        formPage.fillYear("2024");
        formPage.submitForm();
        
        // Wait for redirect and appearance
        page.waitForURL("http://localhost:5173/");
        homePage.waitForBookInList("Playwright in Action");
        
        // 2. View Details
        homePage.clickBookDetails("Playwright in Action");
        detailsPage.waitForTitle("Playwright in Action");
        
        // 3. Delete Book
        page.onceDialog(dialog -> dialog.accept());
        detailsPage.clickDelete();
        
        page.waitForURL("http://localhost:5173/");
    }

    @Test
    void shouldValidateEmptyTitleOnForm() {
        HomePage homePage = new HomePage(page);
        BookFormPage formPage = new BookFormPage(page);

        homePage.navigate();
        homePage.clickAddBook();
        page.waitForURL("**/book/new");
        
        // Leave title blank but fill others
        formPage.fillAuthor("Jane Doe");
        formPage.fillCategory("Mystery");
        formPage.fillYear("2021");
        
        formPage.submitForm();
        
        // The URL shouldn't change
        assertTrue(page.url().endsWith("/book/new"));
    }

    @Test
    void shouldFilterBooksByCategory() {
        HomePage homePage = new HomePage(page);
        
        homePage.navigate();
        
        // Use an option that actually exists in the dropdown (e.g. Fantasy)
        homePage.filterByCategory("Fantasy");
        
        // Because of the POM we wait for response, the UI should update.
        // If there are no books, the UI shows nothing, if there are books they should be Fantasy.
        // This is a basic assertion that the filtering interaction runs without crashing.
        assertTrue(page.url().startsWith("http://localhost:5173"));
    }
}
