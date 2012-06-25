package com.example.server.guice;

import com.example.server.requestfactory.InjectedRequestFactoryModule;
import com.example.server.requestfactory.InjectedRequestFactoryServlet;
import com.google.gwt.logging.server.RemoteLoggingServiceImpl;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

public class MyGuiceServletConfig extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector( 
		  new ServletModule(){
			@Override
			protected void configureServlets() {				
				
				install(new JpaPersistModule("myFirstJpaUnit"));  // like we saw earlier.

			    filter("/*").through(PersistFilter.class);			 
			    
			    install(new InjectedRequestFactoryModule());
			    serve("/gwtRequest").with(InjectedRequestFactoryServlet.class);
			    
			    install(new ServerModule());
			    //GWT logging framework
			    serve("/example/remote_logging").with(RemoteLoggingServiceImpl.class);
				
				//filter("/*").through(MyFilter.class);
				//filter("*.css").through(MyCssFilter.class);
				// etc..
	
				//serve("*.html").with(MyServlet.class);
				//serve("/my/*").with(MyServlet.class);
				// etc..
			}
		});
	}

}
