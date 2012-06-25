package com.example.client.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.UmbrellaException;

public class CustomUncaughtExceptionHandler implements UncaughtExceptionHandler {

  public void onUncaughtException(Throwable e) {
    // Get rid of UmbrellaException
    Throwable exceptionToDisplay = getExceptionToDisplay( e );
    // Replace with your favorite message dialog, e.g. GXT's MessageBox
    if(exceptionToDisplay != null){
    	Logger logger = Logger.getLogger("AppException");
    	logger.log(Level.SEVERE, exceptionToDisplay.getMessage(), exceptionToDisplay);
    	
    	Window.alert(exceptionToDisplay.getMessage());
    }
  }

  private static Throwable getExceptionToDisplay( Throwable throwable ) {
    Throwable result = throwable;
    if (throwable instanceof UmbrellaException && ((UmbrellaException) throwable).getCauses().size() == 1) {
      result = ((UmbrellaException) throwable).getCauses().iterator().next();
    }
    return result;
  }
}