package com.example.client.gin;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.web.bindery.event.shared.EventBus;
import com.example.client.presenter.LayoutPresenter;
import com.example.client.presenter.InicioPresenter;
import com.example.client.presenter.ColaboradorPresenter;

@GinModules(ClientModule.class)
public interface ClientGinjector extends Ginjector {

	EventBus getEventBus();

	PlaceManager getPlaceManager();

	AsyncProvider<LayoutPresenter> getLayoutPresenter();

	AsyncProvider<InicioPresenter> getInicioPresenter();

	AsyncProvider<ColaboradorPresenter> getColaboradorPresenter();
}
