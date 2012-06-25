package com.example.client.presenter;

import java.util.List;

import com.example.client.ui.MenuDto;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.sencha.gxt.widget.core.client.tree.Tree;

public class MenuPresenter extends PresenterWidget<MenuPresenter.MyView> {

	public interface MyView extends View {
		Tree<MenuDto, String> getTree();
	}

	@Inject
	public MenuPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}
	
	@Inject
	PlaceManager placeManager;
	
	@Override
	protected void onReset() {
		super.onReset();
		
		getView().getTree().getSelectionModel().addSelectionHandler(new SelectionHandler<MenuDto>() {
			
			public void onSelection(SelectionEvent<MenuDto> event) {
				MenuDto mnu = event.getSelectedItem();
				if(!mnu.getToken().equals("")){
					PlaceRequest request = new PlaceRequest(mnu.getToken());
					placeManager.revealPlace(request);
				}				
			}
		});
		String token = placeManager.getCurrentPlaceRequest().getNameToken();
		List<MenuDto> mnus = getView().getTree().getStore().getAll();
		for(MenuDto mnu: mnus){
			if(mnu.getToken().equals(token)){
				getView().getTree().getSelectionModel().select(mnu, true);
				break;
			}
		}
	}
}
