package com.example.client.presenter;

import com.example.client.events.SaveEvent;
import com.example.client.uihandlers.ColaboradorEditorUiHandlers;
import com.example.client.util.BeanEditView;
import com.example.shared.proxy.ColaboradorProxy;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.sencha.gxt.widget.core.client.Window;

public class ColaboradorEditorPresenter extends
		PresenterWidget<ColaboradorEditorPresenter.MyView>
		implements ColaboradorEditorUiHandlers{
	
	public interface MyView extends PopupView, BeanEditView<ColaboradorProxy>, HasUiHandlers<ColaboradorEditorUiHandlers> {
		public void clearFields();
		public void setHeadingText(String text);
		public Window getDialog();
	}
	
	private EventBus eventBus;
	
	@Inject
	public ColaboradorEditorPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.eventBus = eventBus;
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	public void onSave() {
		SaveEvent saveEvent = new SaveEvent();
		eventBus.fireEvent(saveEvent);
		getView().hide();
	}

	public void onCancel() {
		getView().hide();
	}

}
