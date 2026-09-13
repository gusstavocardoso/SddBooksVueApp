package com.sdd.booksapp.e2e;

import com.microsoft.playwright.*;
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
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
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
    void shouldLoadHomePageAndSeeBooks() {
        // Assuming frontend runs on 5173 (vite default)
        page.navigate("http://localhost:5173");
        
        // Wait for books to load
        page.waitForSelector(".book-card");
        
        // Verify title
        String title = page.title();
        assertTrue(title.contains("Vite") || title.contains("Books"));
        
        // Verify there is at least one book
        Locator bookCards = page.locator(".book-card");
        assertTrue(bookCards.count() > 0);
    }
}
