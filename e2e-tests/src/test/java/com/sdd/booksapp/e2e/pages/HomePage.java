package com.sdd.booksapp.e2e.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class HomePage {
    private final Page page;

    private final Locator addBookButton;
    private final Locator categoryFilter;
    private final Locator bookList;

    public HomePage(Page page) {
        this.page = page;
        this.addBookButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("+ Add Book"));
        this.categoryFilter = page.getByLabel("Category Filter");
        this.bookList = page.locator(".book-list"); // Assuming there's a container or just locators
    }

    public void navigate() {
        page.navigate("http://localhost:5173");
        page.waitForLoadState();
    }

    public void clickAddBook() {
        addBookButton.click();
    }

    public void filterByCategory(String category) {
        categoryFilter.selectOption(category);
        // Wait for response / network idle after filtering
        page.waitForResponse("**/api/books?category=**", () -> {});
    }

    public Locator getBookCardByTitle(String title) {
        return page.locator(".book-card", new Page.LocatorOptions().setHasText(title));
    }

    public void clickBookDetails(String title) {
        getBookCardByTitle(title).getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Details")).click();
    }
    
    public void waitForBookInList(String title) {
        page.getByText(title).waitFor();
    }
}
