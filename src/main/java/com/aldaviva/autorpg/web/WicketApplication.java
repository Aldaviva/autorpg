package com.aldaviva.autorpg.web;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import com.aldaviva.autorpg.web.pages.HomePage;
import com.aldaviva.autorpg.web.pages.VeryNiceExceptionPage;

public class WicketApplication extends WebApplication {

	public Class<HomePage> getHomePage() {
		return HomePage.class;
	}

	@Override
	protected void init() {
		super.init();
		addComponentInstantiationListener(new SpringComponentInjector(this));

		// Enable wicket ajax debug
		getDebugSettings().setAjaxDebugModeEnabled(true);
		
		// Mount pages
		mountBookmarkablePage("/home", HomePage.class);
		mountBookmarkablePage("/exceptionPage", VeryNiceExceptionPage.class);
		//mount(new IndexedParamUrlCodingStrategy("/view_params", ParamPage.class));*/

	}

}
