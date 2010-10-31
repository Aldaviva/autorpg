package com.aldaviva.autorpg.display.web.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;

public class HomePage extends BasePage {

	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super();
		
		add(new Label("message", "Welcome to IdleRPG."));

	}
}
