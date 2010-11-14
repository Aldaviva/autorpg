package com.aldaviva.autorpg.display.web;

import java.text.NumberFormat;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import com.aldaviva.autorpg.display.web.pages.CharactersPage;
import com.aldaviva.autorpg.display.web.pages.ItemsPage;

public class WicketApplication extends WebApplication {

	public Class<? extends WebPage> getHomePage() {
		return CharactersPage.class;
	}

	@Override
	protected void init() {
		super.init();
		addComponentInstantiationListener(new SpringComponentInjector(this));

		getDebugSettings().setAjaxDebugModeEnabled(true);
		
		mountBookmarkablePage("/characters", CharactersPage.class);
		mountBookmarkablePage("/items", ItemsPage.class);
		
		NumberFormat.getInstance().setGroupingUsed(true);
	}

}
