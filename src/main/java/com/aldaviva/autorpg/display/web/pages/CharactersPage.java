package com.aldaviva.autorpg.display.web.pages;

import java.util.List;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.aldaviva.autorpg.data.entities.Character;
import com.aldaviva.autorpg.data.entities.Item;

public class CharactersPage extends BasePage {

	public CharactersPage(){
		super(new Model<String>("Characters")); //TODO use properties file
		addCss();
		
		List<Character> characters = Character.findAllCharacters();
		
		PropertyListView<Character> characterList = new PropertyListView<Character>("character", characters) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Character> item) {
				item.add(new Label("name"));
				item.add(new Label("level"));
				item.add(new Label("designation"));
				item.add(new Label("experience"));
				
				
				RepeatingView itemRepeatingView = new RepeatingView("item");
				for(Item i : item.getModelObject().getItems()){
					Label itemLabel = new Label(itemRepeatingView.newChildId(), new PropertyModel<String>(i, "name"));
					if(i.getRare()){
						itemLabel.add(new AttributeAppender("class", new Model<String>("rare"), " "));
					}
					itemRepeatingView.add(itemLabel);
				}
				item.add(itemRepeatingView);
				
			}
		};
		
		add(characterList);
	}
}
