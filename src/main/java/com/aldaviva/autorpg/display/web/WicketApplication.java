package com.aldaviva.autorpg.display.web;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import com.aldaviva.autorpg.display.web.pages.BasePage;
import com.aldaviva.autorpg.display.web.pages.CharactersPage;
import com.aldaviva.autorpg.display.web.pages.ItemsPage;
import com.aldaviva.autorpg.display.web.pages.MapPage;

public class WicketApplication extends WebApplication {

	public Class<? extends WebPage> getHomePage() {
		return CharactersPage.class;
	}
	
	public static final List<Class<? extends BasePage>> pages = new ArrayList<Class<? extends BasePage>>(){
		private static final long serialVersionUID = 1L;
	{
		add(CharactersPage.class);
		add(ItemsPage.class);
		add(MapPage.class);
	}};

	@Override
	protected void init() {
		super.init();
		addComponentInstantiationListener(new SpringComponentInjector(this));

		getDebugSettings().setAjaxDebugModeEnabled(false);
		
		for (Class<? extends BasePage> pageClass : pages) {
			String path = "/"+StringUtils.lowerCase(StringUtils.removeEnd(pageClass.getSimpleName(), "Page"));
			mountBookmarkablePage(path, pageClass);
		}
		
		NumberFormat.getInstance().setGroupingUsed(true);
	}

}
