package com.aldaviva.autorpg.display.web.pages;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

import com.aldaviva.autorpg.display.web.style.CssScope;

/*
 * 	Base page, nothing here right now but this can be used to add
 * 	common stuff like JavaScripts to all pages and so on.
 */
public class BasePage extends WebPage {
	private static final String DEFAULT_TITLE = "IdleRPG";
	
	private static final ResourceReference CSS_RESET = new ResourceReference(CssScope.class, "Reset.css");
	private static final ResourceReference CSS_GLOBAL = new ResourceReference(CssScope.class, "Global.css");

	private BasePage(String pageTitle){
		add(new Label("pageTitle", pageTitle));
		
		add(CSSPackageResource.getHeaderContribution(CSS_RESET));
		add(CSSPackageResource.getHeaderContribution(CSS_GLOBAL));
	}
	
	public BasePage() {
		this(DEFAULT_TITLE);
	}
	
	public BasePage(IModel<String> pageTitle){
		this(pageTitle.getObject() + " - " + DEFAULT_TITLE);
	}
	
	protected void addCss(){
		add(CSSPackageResource.getHeaderContribution(CssScope.class, getClass().getSimpleName()+".css"));
	}
}
