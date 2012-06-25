package com.example.client.view;

import com.example.client.presenter.InicioPresenter;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class InicioView extends ViewImpl implements InicioPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, InicioView> {
	}

	@Inject
	public InicioView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	public Widget asWidget() {
		return widget;
	}
}
