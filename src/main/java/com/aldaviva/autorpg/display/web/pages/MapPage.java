package com.aldaviva.autorpg.display.web.pages;

import java.util.List;

import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.time.Duration;

import com.aldaviva.autorpg.data.entities.Character;
import com.aldaviva.autorpg.data.entities.Configuration;
import com.aldaviva.autorpg.data.enums.ConfigurationKey;
import com.aldaviva.autorpg.game.GameState;

public class MapPage extends BasePage {
	
	public MapPage(){
		super();
		
		addPageSpecificCss();
		
		final WebMarkupContainer mapContainer = new WebMarkupContainer("map");
		mapContainer.setOutputMarkupId(true);
		add(mapContainer);
		
		mapContainer.add(new SimpleAttributeModifier("style", "background-image: url("+getImageUrl()+"); width: "+getWidth()+"px; height: "+getHeight()+"px;"));
		
		IModel<List<Character>> allCharactersModel = new LoadableDetachableModel<List<Character>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Character> load() {
				return Character.findAllCharacters();
			}
		};
		
		ListView<Character> characterList = new ListView<Character>("character", allCharactersModel){
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Character> item) {
				Character character = item.getModelObject();
				
				item.add(new Label("name", new PropertyModel<Character>(character, "name")));
				
				item.add(new PositionStyleAppender("left", new PropertyModel<Integer>(character, "location.x")));
				item.add(new PositionStyleAppender("top", new PropertyModel<Integer>(character, "location.y")));
			}
		};
		
		mapContainer.add(characterList);
		
		
		
		add(new AbstractAjaxTimerBehavior(Duration.seconds(GameState.getTickInterval())) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onTimer(AjaxRequestTarget target) {
				target.addComponent(mapContainer);
			}
		});
	}
	
	private String getImageUrl(){
		return Configuration.getValue(ConfigurationKey.MAP_IMAGE_URL);
	}
	
	private Integer getHeight(){
		return Integer.valueOf(Configuration.getValue(ConfigurationKey.MAP_HEIGHT));
	}
	
	private Integer getWidth(){
		return Integer.valueOf(Configuration.getValue(ConfigurationKey.MAP_WIDTH));
	}
	
	private static class PositionStyleAppender extends AttributeAppender {
		private static final long serialVersionUID = 1L;

		public PositionStyleAppender(final String cssProperty, final IModel<Integer> model) {
			super("style", true, new AbstractReadOnlyModel<String>() {
				private static final long serialVersionUID = 1L;
				@Override
				public String getObject() {
					return cssProperty + ": "+model.getObject()+"px;";
				}
			}, " ");
		}
		
	}
	
}