package com.example.client.util;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;
import com.gwtplatform.mvp.client.UiHandlers;
import com.gwtplatform.mvp.client.View;

public abstract class GXTPopupViewWithUiHandlers<C extends UiHandlers> extends
GXTPopupViewImpl implements HasUiHandlers<C> {
  private C uiHandlers;

  /**
   * The {@link PopupViewWithUiHandlers} class uses the {@link EventBus} to listen to
   * {@link com.gwtplatform.mvp.client.proxy.NavigationEvent} in order to automatically
   * close when this event is fired, if desired. See
   * {@link #setAutoHideOnNavigationEventEnabled(boolean)} for details.
   *
   * @param eventBus The {@link EventBus}.
   */
  protected GXTPopupViewWithUiHandlers(EventBus eventBus) {
    super(eventBus);
  }

  /**
   * Access the {@link UiHandlers} associated with this {@link View}.
   * <p>
   * <b>Important!</b> Never call {@link #getUiHandlers()} inside your constructor
   * since the {@link UiHandlers} are not yet set.
   *
   * @return The {@link UiHandlers}, or {@code null} if they are not yet set.
   */
  protected C getUiHandlers() {
    return uiHandlers;
  }

  public void setUiHandlers(C uiHandlers) {
    this.uiHandlers = uiHandlers;
  }
}

