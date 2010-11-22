package com.aldaviva.autorpg.display.web.pages.panels;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.StringResourceModel;

import com.aldaviva.autorpg.data.entities.Configuration;
import com.aldaviva.autorpg.data.enums.ConfigurationKey;
import com.aldaviva.autorpg.display.web.WicketApplication;
import com.aldaviva.autorpg.display.web.pages.BasePage;

public class Navigation extends Panel {

	private static final long serialVersionUID = 1L;
	
	public Navigation(String id, BasePage currentPage) {
		super(id);
		
		add(CSSPackageResource.getHeaderContribution(BasePage.CSS_DIR_PREFIX+getClass().getSimpleName()+".css"));
		
		RepeatingView navLinks = new RepeatingView("navLink");
		
		for (final Class<? extends BasePage> pageClass : WicketApplication.pages) {
			
			Link<BasePage> link;
			link = new Link<BasePage>(navLinks.newChildId()){
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick() {
					setResponsePage(pageClass);
				}
			};
			link.add(new Label("navText", new StringResourceModel("navLinks.text."+pageClass.getSimpleName(), this, null)));
			if(pageClass.equals(currentPage.getClass())){
				link.add(new SimpleAttributeModifier("class", "current"));
			}
			navLinks.add(link);
		}
		
		navLinks.add(new ExternalLink(navLinks.newChildId(), Configuration.getValue(ConfigurationKey.TWITTER_URL)).add(new Label("navText", new StringResourceModel("navLinks.text.Twitter", this, null))));
		
		add(navLinks);
	}

}
