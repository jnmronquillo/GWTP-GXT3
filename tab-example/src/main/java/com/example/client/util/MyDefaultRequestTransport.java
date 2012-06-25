package com.example.client.util;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.gwt.client.DefaultRequestTransport;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.Style.AnchorAlignment;

//http://stackoverflow.com/questions/7608235/intercepting-gwt-requestfactory-requests
public class MyDefaultRequestTransport extends DefaultRequestTransport {
	
	@Inject
	private BusyIndicator indicator;
	
	@Override
    public void send(final String payload, final TransportReceiver receiver) {
        onIn();
        final TransportReceiver proxy = new TransportReceiver() {
            public void onTransportFailure(final ServerFailure failure) {
                onOut();
                receiver.onTransportFailure(failure);
            }

            public void onTransportSuccess(final String payload) {
            	onOut();
                receiver.onTransportSuccess(payload);
            }
        };

        super.send(payload, proxy);
	}
	
	private void onOut() {
		indicator.hide();	    
	}
	
	private void onIn() {	
	    //muestra gif
	    indicator.show(RootPanel.get().getElement(), new AnchorAlignment(Style.Anchor.TOP_LEFT, Style.Anchor.TOP_LEFT));	    
	}
}
