package com.example.client.view;

import com.example.client.presenter.LayoutPresenter;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.widget.core.client.ContentPanel;

public class LayoutView extends ViewImpl implements LayoutPresenter.MyView {

	private final Widget widget;

	@UiField ContentPanel contentPanel;//for nested presenter
	@UiField ContentPanel menuPanel;//for presenter widget
	
	public interface Binder extends UiBinder<Widget, LayoutView> {
	}

	@Inject
	public LayoutView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	public Widget asWidget() {
		return widget;
	}
	

	
	@Override
	public void setInSlot(Object slot, Widget content) {
		if(slot == LayoutPresenter.SLOT_content){
			contentPanel.clear();
			if(content != null){
				contentPanel.add(content);
				contentPanel.forceLayout();
			}
			return;
		}
		if(slot == LayoutPresenter.SLOT_menu){
			menuPanel.clear();
			if(content != null){
				menuPanel.add(content);
			}
			return;
		}
		
		super.setInSlot(slot, content);
			
	}

}
