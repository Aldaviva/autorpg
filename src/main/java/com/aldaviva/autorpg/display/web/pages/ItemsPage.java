package com.aldaviva.autorpg.display.web.pages;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.Model;

import com.aldaviva.autorpg.data.entities.Item;

public class ItemsPage extends BasePage {

	public ItemsPage(){
		super();
		
		add(new PropertyListView<Item>("item", Item.findAllItems()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final ListItem<Item> item) {
				item.add(new Label("name"));
				item.add(new Label("level"));
				item.add(new Label("rarity", new Model<String>(){
					private static final long serialVersionUID = 1L;

					@Override
					public String getObject() {
						return item.getModelObject().getRare() ? "rare" : "not rare";
					}
					
				}));
			}
		});
	}
	
}
