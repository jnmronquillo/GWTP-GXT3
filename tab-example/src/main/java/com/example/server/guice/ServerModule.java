package com.example.server.guice;

import com.google.gwt.logging.server.RemoteLoggingServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ServerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(RemoteLoggingServiceImpl.class).in(Singleton.class);
	}

}
