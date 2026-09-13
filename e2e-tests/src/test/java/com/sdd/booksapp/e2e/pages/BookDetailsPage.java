package com.sdd.booksapp.e2e.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class BookDetailsPage {
    private final Page page;

    public BookDetailsPage(Page page) {
        this.page = page;
    }

    public void waitForTitle(String title) {
        page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName(title)).waitFor();
    }

    public void clickDelete() {
        // Playwright handles dialogs (alerts, confirms) via events. 
        // The event listener should be setup before calling this method in the test.
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Delete")).click();
    }

    public void clickBack() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Back")).click();
    }
}
