package com.example.client.util;

import com.google.gwt.editor.client.Editor;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.gwtplatform.mvp.client.View;

/**
 * Implemented by views that edit beans.
 *
 * @param <B> the type of the bean
 */
public interface BeanEditView<B> extends View, Editor<B> {

  /**
   * @return a new {@link RequestFactoryEditorDriver} initialized to run
   *         this editor
   */
  RequestFactoryEditorDriver<B, ?> createEditorDriver();
}