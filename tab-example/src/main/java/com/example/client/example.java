package com.example.client;

import com.example.client.gin.ClientGinjector;
import com.example.client.util.CustomUncaughtExceptionHandler;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

public class example implements EntryPoint {
  
  private final ClientGinjector ginjector = GWT.create(ClientGinjector.class);
	
  public void onModuleLoad() {
	 //register custom exception handler for RequestFactory
	 GWT.setUncaughtExceptionHandler(new CustomUncaughtExceptionHandler());
	  
	// This is required for Gwt-Platform proxy's generator
	DelayedBindRegistry.bind(ginjector);
	
	ginjector.getPlaceManager().revealCurrentPlace();
  }
}
