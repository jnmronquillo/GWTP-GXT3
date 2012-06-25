package com.example.server.requestfactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.web.bindery.requestfactory.server.ExceptionHandler;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

/*Thanks to 
 * http://cleancodematters.com/2011/05/29/improved-exceptionhandling-with-gwts-requestfactory/
 * */
public class LoquaciousExceptionHandler implements ExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger( LoquaciousExceptionHandler.class );

    public ServerFailure createServerFailure(Throwable throwable) {
      LOG.error( "Server error", throwable );
      return new ServerFailure( throwable.getMessage(), throwable.getClass().getName(), null, true );
    }
}