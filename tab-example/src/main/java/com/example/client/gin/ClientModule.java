package com.example.client.gin;

import com.example.client.Messages;
import com.example.client.images.AppImages;
import com.example.client.place.ClientPlaceManager;
import com.example.client.place.DefaultPlace;
import com.example.client.place.NameTokens;
import com.example.client.presenter.ColaboradorEditorPresenter;
import com.example.client.presenter.ColaboradorPresenter;
import com.example.client.presenter.InicioPresenter;
import com.example.client.presenter.LayoutPresenter;
import com.example.client.presenter.MenuPresenter;
import com.example.client.util.BusyIndicator;
import com.example.client.util.MyDefaultRequestTransport;
import com.example.client.view.ColaboradorEditorView;
import com.example.client.view.ColaboradorView;
import com.example.client.view.InicioView;
import com.example.client.view.LayoutView;
import com.example.client.view.MenuView;
import com.example.shared.service.AppRequestFactory;
import com.google.gwt.core.client.GWT;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

public class ClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		install(new DefaultModule(ClientPlaceManager.class));
		
		bind(AppImages.class).in(Singleton.class);
		bind(Messages.class).in(Singleton.class);
		bind(MyDefaultRequestTransport.class).in(Singleton.class);
		bind(BusyIndicator.class).in(Singleton.class);

		bindPresenter(LayoutPresenter.class, LayoutPresenter.MyView.class,
				LayoutView.class, LayoutPresenter.MyProxy.class);
		
		bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.inicio);

		bindPresenter(InicioPresenter.class, InicioPresenter.MyView.class,
				InicioView.class, InicioPresenter.MyProxy.class);

		bindPresenterWidget(MenuPresenter.class, MenuPresenter.MyView.class,
				MenuView.class);

		bindPresenter(ColaboradorPresenter.class,
				ColaboradorPresenter.MyView.class, ColaboradorView.class,
				ColaboradorPresenter.MyProxy.class);

		bindPresenterWidget(ColaboradorEditorPresenter.class,
				ColaboradorEditorPresenter.MyView.class,
				ColaboradorEditorView.class);
	}
	
	@Provides
    @Singleton
    public AppRequestFactory createRequestFactory(EventBus eventBus, MyDefaultRequestTransport requestTransport) {
	  AppRequestFactory factory = GWT.create(AppRequestFactory.class);
      factory.initialize(eventBus, requestTransport);
      return factory;
    }
}
