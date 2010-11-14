package com.aldaviva.autorpg.display.web.pages;

import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.StringResourceModel;

import com.aldaviva.autorpg.display.web.pages.panels.Navigation;

/*
 * 	Base page, nothing here right now but this can be used to add
 * 	common stuff like JavaScripts to all pages and so on.
 */
public class BasePage extends WebPage {
	public  static final String CSS_DIR_PREFIX = "css/";
	
	public BasePage(){
		add(CSSPackageResource.getHeaderContribution(CSS_DIR_PREFIX+"Reset.css"));
		add(CSSPackageResource.getHeaderContribution(CSS_DIR_PREFIX+"Global.css"));
		
		Navigation navigation = new Navigation("navigation");
		add(navigation);
		
		add(new Label("pageTitle", new StringResourceModel("navLinks.text."+getClass().getSimpleName(), navigation, null)));
		
		add(new Link<BasePage>("logo"){
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(getApplication().getHomePage());
			}
		});
	}
	
	protected void addPageSpecificCss(){
		add(CSSPackageResource.getHeaderContribution(CSS_DIR_PREFIX+getClass().getSimpleName()+".css"));
	}
}
