package com.aldaviva.autorpg.display.web.pages;

import java.text.NumberFormat;
import java.util.List;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
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
		super();
		addPageSpecificCss();
		
		List<Character> characters = Character.findAllCharactersOrderByOnlineAndExperience();
		
		PropertyListView<Character> characterList = new PropertyListView<Character>("character", characters) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final ListItem<Character> item) {
				item.add(new Label("name"));
				item.add(new Label("level"));
				item.add(new Label("designation"));
				Model<String> experienceModel = new Model<String>(){
					private static final long serialVersionUID = 1L;

					@Override
					public String getObject() {
						return NumberFormat.getInstance().format(item.getModelObject().getExperience());
					}
				};
				item.add(new Label("experience", experienceModel));
				
				
				WebMarkupContainer experienceProgress = new WebMarkupContainer("experienceProgress");
				experienceProgress.setRenderBodyOnly(false);
				experienceProgress.add(new AttributeAppender("style", true, new Model<String>(){
					private static final long serialVersionUID = 1L;

					@Override
					public String getObject() {
						double percent = item.getModelObject().getProgressTowardsNextLevel();
						String width = NumberFormat.getPercentInstance().format(percent);
						return "width: "+width+";";
					}
				}, " "));
				item.add(experienceProgress);
				
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
