package com.example.client.util;

import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.widget.core.client.Popup;

public class BusyIndicator extends Popup {
	private Label cargandoLabel;

	public BusyIndicator(){
		setSize("100", "25");
		setBorders(true);
		//setShadow(true);
		setAutoHide(false);
		
		setConstrainViewport(true);//mantener visible el popup dentro del viewport
		
		cargandoLabel = new Label("Cargando...");
		add(cargandoLabel);
		
		setStylePrimaryName("busy-indicator");		
	}
}
