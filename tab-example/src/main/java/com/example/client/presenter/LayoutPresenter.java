package com.example.client.presenter;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class LayoutPresenter extends
		Presenter<LayoutPresenter.MyView, LayoutPresenter.MyProxy> {

	@ContentSlot public static final Type<RevealContentHandler<?>> SLOT_content = new Type<RevealContentHandler<?>>();
	
	public static final Object SLOT_menu = new Object();
	
	public interface MyView extends View {
		
	}
	
	@Inject MenuPresenter menuPresenter;

	@ProxyCodeSplit
	public interface MyProxy extends Proxy<LayoutPresenter> {
	}
	
	
	@Inject
	public LayoutPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}
	
	@Override
	protected void onReset() {		
		super.onReset();
		
		setInSlot(SLOT_menu, menuPresenter);
	}
}
