package com.aldaviva.autorpg.display.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import com.aldaviva.autorpg.display.web.pages.CharactersPage;

public class WicketApplication extends WebApplication {

	public Class<? extends WebPage> getHomePage() {
		return CharactersPage.class;
	}

	@Override
	protected void init() {
		super.init();
		addComponentInstantiationListener(new SpringComponentInjector(this));

		// Enable wicket ajax debug
		getDebugSettings().setAjaxDebugModeEnabled(true);
		
		// Mount pages
//		mountBookmarkablePage("/", HomePage.class);
		mountBookmarkablePage("/characters", CharactersPage.class);

	}

}
