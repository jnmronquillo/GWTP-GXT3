package com.example.client.view;

import com.example.client.presenter.ColaboradorEditorPresenter;
import com.example.client.uihandlers.ColaboradorEditorUiHandlers;
import com.example.client.util.GXTPopupViewWithUiHandlers;
import com.example.shared.proxy.ColaboradorProxy;
import com.example.shared.service.AppRequestFactory;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;

public class ColaboradorEditorView extends GXTPopupViewWithUiHandlers<ColaboradorEditorUiHandlers>  implements
		ColaboradorEditorPresenter.MyView, Editor<ColaboradorProxy>{

	public interface Driver extends RequestFactoryEditorDriver<ColaboradorProxy, ColaboradorEditorView>{  
	}
	
	private final Widget widget;

	public interface Binder extends UiBinder<Widget, ColaboradorEditorView> {
	}
		
	@UiField
	TextField codigo;
	
	@UiField
	TextField nombres;
	 
	@UiField
	TextField apellidos;
	
	@UiField(provided = true)
	NumberField<Integer> edad;
		
	@UiField
	Window dialog;
	
	private final AppRequestFactory factory;
	private final Driver driver;

	@Inject
	public ColaboradorEditorView(final EventBus eventBus, final Binder binder, Provider<AppRequestFactory> provider, Driver driver) {
		super(eventBus);
		this.factory = provider.get();
		this.driver = driver;
		
		edad = new NumberField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
			
		widget = binder.createAndBindUi(this);
	}

	public Widget asWidget() {
		return widget;
	}
	
	@UiHandler("cancel")
	public void onCancel(SelectEvent event){
		if (getUiHandlers() != null) {
		  getUiHandlers().onCancel();
		}
	}
	
	@UiHandler("save")
	public void onSave(SelectEvent event){
		if (getUiHandlers() != null) {
		  getUiHandlers().onSave();
		}
	}
	
	public void clearFields() {
		nombres.clearInvalid();
		apellidos.clearInvalid();		
	}
	
	public void setHeadingText(String text){
		dialog.setHeadingText(text);
	}
	
	public Window getDialog() {
		return dialog;
	}

	public RequestFactoryEditorDriver<ColaboradorProxy, ?> createEditorDriver() {
		driver.initialize(factory, this);
		return driver;
	}

}
