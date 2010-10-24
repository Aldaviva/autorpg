package com.aldaviva.autorpg.web.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class HomePage extends WebPage {

	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {

		add(new Label("message", "If you see this message wicket is properly configured and running"));

		add(new BookmarkablePageLink<BotTest>("linkBotTest", BotTest.class));
	}
}
