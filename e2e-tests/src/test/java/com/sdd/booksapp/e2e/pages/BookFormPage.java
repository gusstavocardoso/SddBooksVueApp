package com.sdd.booksapp.e2e.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class BookFormPage {
    private final Page page;

    public BookFormPage(Page page) {
        this.page = page;
    }

    public void fillTitle(String title) {
        page.getByLabel("Title").fill(title);
    }

    public void fillAuthor(String author) {
        page.getByLabel("Author").fill(author);
    }

    public void fillCategory(String category) {
        page.getByLabel("Category").fill(category);
    }

    public void fillYear(String year) {
        page.getByLabel("Publication Year").fill(year);
    }

    public void submitForm() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save Book")).click();
    }

    public boolean isValidationErrorVisible(String message) {
        // Playwright will auto-wait and check for visibility
        return page.getByText(message).isVisible();
    }
}
