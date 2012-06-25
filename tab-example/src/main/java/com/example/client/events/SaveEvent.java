package com.example.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HasHandlers;

public class SaveEvent extends GwtEvent<SaveEvent.SaveHandler> {

	public static Type<SaveHandler> TYPE = new Type<SaveHandler>();

	public interface SaveHandler extends EventHandler {
		void onSave(SaveEvent event);
	}

	public SaveEvent() {
	}

	@Override
	protected void dispatch(SaveHandler handler) {
		handler.onSave(this);
	}

	@Override
	public Type<SaveHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<SaveHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source) {
		source.fireEvent(new SaveEvent());
	}
}
