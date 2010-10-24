package com.aldaviva.autorpg.display.web.pages;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.aldaviva.autorpg.display.irc.Bot;

public class BotTest extends BasePage {

	@SpringBean
	protected Bot botService;

	public BotTest(){
		
		add(new Link<Void>("startBot") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				botService.start();
			}
			
		});
		
		add(new Link<Void>("stopBot") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				botService.stop();;
			}
			
		});
	}
	
}
