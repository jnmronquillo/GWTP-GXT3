package com.example.client.util;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PopupViewCloseHandler;
import com.gwtplatform.mvp.client.PopupViewImpl;
import com.gwtplatform.mvp.client.ViewImpl;
import com.gwtplatform.mvp.client.proxy.NavigationEvent;
import com.gwtplatform.mvp.client.proxy.NavigationHandler;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;

public abstract class GXTPopupViewImpl extends ViewImpl implements PopupView {

	private HandlerRegistration autoHideHandler;

	  private HandlerRegistration closeHandlerRegistration;
	  private final EventBus eventBus;

	  /**
	   * The {@link PopupViewImpl} class uses the {@link EventBus} to listen to
	   * {@link NavigationEvent} in order to automatically close when this event is
	   * fired, if desired. See
	   * {@link #setAutoHideOnNavigationEventEnabled(boolean)} for details.
	   *
	   * @param eventBus The {@link EventBus}.
	   */
	  protected GXTPopupViewImpl(EventBus eventBus) {
	    this.eventBus = eventBus;
	  }

	  public void center() {
	    doCenter();
	    // We center again in a deferred command to solve a bug in IE where newly
	    // created window are sometimes not centered.
	    Scheduler.get().scheduleDeferred(new Command() {
	      public void execute() {
	        doCenter();
	      }
	    });
	  }

	  public void hide() {
	    asPopupPanel().hide();
	  }

	  public void setAutoHideOnNavigationEventEnabled(boolean autoHide) {
	    if (autoHide) {
	      if (autoHideHandler != null) {
	        return;
	      }
	      autoHideHandler = eventBus.addHandler(NavigationEvent.getType(),
	          new NavigationHandler() {
	            public void onNavigation(NavigationEvent navigationEvent) {
	              hide();
	            }
	          });
	    } else {
	      if (autoHideHandler != null) {
	        autoHideHandler.removeHandler();
	      }
	    }
	  }

	  public void setCloseHandler(final PopupViewCloseHandler popupViewCloseHandler) {
	    if (closeHandlerRegistration != null) {
	      closeHandlerRegistration.removeHandler();
	    }
	    if (popupViewCloseHandler == null) {
	      closeHandlerRegistration = null;
	    } else {
	      closeHandlerRegistration = asPopupPanel().addHideHandler(new HideHandler() {
			
			public void onHide(HideEvent event) {
				popupViewCloseHandler.onClose();
			}
		});
	      
	    }
	  }

	  public void setPosition(int left, int top) {
		  asPopupPanel().setPosition(left, top);
	  }

	  public void show() {
	    asPopupPanel().show();
	  }

	  /**
	   * Retrieves this view as a {@link PopupPanel}. See {@link #asWidget()}.
	   *
	   * @return This view as a {@link PopupPanel} object.
	   */
	  protected Window asPopupPanel() {
	    return (Window) asWidget();
	  }

	  /**
	   * This method centers the popup panel, temporarily making it visible if
	   * needed.
	   */
	  private void doCenter() {
	    boolean wasVisible = asPopupPanel().isVisible();
	    asPopupPanel().center();
	    if (!wasVisible) {
	      asPopupPanel().hide();
	    }
	  }
}
