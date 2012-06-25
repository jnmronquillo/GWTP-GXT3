package com.example.client.presenter;

import java.util.List;

import com.example.client.Messages;
import com.example.client.events.SaveEvent;
import com.example.client.events.SaveEvent.SaveHandler;
import com.example.client.place.NameTokens;
import com.example.shared.proxy.ColaboradorProxy;
import com.example.shared.service.AppRequestFactory;
import com.example.shared.service.ColaboradorService;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.ShowEvent;
import com.sencha.gxt.widget.core.client.event.ShowEvent.ShowHandler;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
//http://stackoverflow.com/questions/10694589/how-to-use-gwts-editor-framework-with-gwt-platform
public class ColaboradorPresenter extends
		Presenter<ColaboradorPresenter.MyView, ColaboradorPresenter.MyProxy> {
	
	@Inject Messages messages;
	
	public interface MyView extends View {
		public TextButton getAdd();
		public TextButton getEdit();
		public TextButton getDelete();
		public void load();
		public Grid<ColaboradorProxy> getGrid();		
		public ContentPanel getPanel();
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.colaboradores)
	public interface MyProxy extends ProxyPlace<ColaboradorPresenter> {
	}
	
	private final SaveHandler saveHandler = new SaveHandler() {
		
		public void onSave(SaveEvent event) {
		  RequestContext context = editorDriver.flush();
		  if(!editorDriver.hasErrors()){			   
		   context.fire();		   
		  }		  
		}
	};
	
	private ColaboradorEditorPresenter editorPresenter;
	private final AppRequestFactory factory;
	private ColaboradorProxy colaborador;
	private RequestFactoryEditorDriver<ColaboradorProxy, ?> editorDriver;

	@Inject
	public ColaboradorPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy, Provider<AppRequestFactory> provider, ColaboradorEditorPresenter editorPresenter) {
		
		super(eventBus, view, proxy);
		factory = provider.get();
		this.editorPresenter = editorPresenter;
		editorDriver = editorPresenter.getView().createEditorDriver();

	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, LayoutPresenter.SLOT_content, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		
		registerHandler(getEventBus().addHandler(SaveEvent.getType(), saveHandler));
			
		editorPresenter.getView().getDialog().addHideHandler(new HideHandler() {
			
			public void onHide(HideEvent event) {
				getView().getPanel().unmask();
			}
		});
		editorPresenter.getView().getDialog().addShowHandler(new ShowHandler() {
			
			public void onShow(ShowEvent event) {
				getView().getPanel().mask();
			}
		});

		getView().getAdd().addSelectHandler(new SelectHandler() {
			
			public void onSelect(SelectEvent event) {
				final ColaboradorService cs = factory.colaboradorService();
				colaborador = cs.create(ColaboradorProxy.class);
				
				cs.persist(colaborador).to(new Receiver<ColaboradorProxy>() {

					@Override
					public void onSuccess(ColaboradorProxy response) {
						Info.display(messages.titlePanel(), messages.saveSuccessful());
						getView().load();					    
					}
				  });
				editorDriver.edit(colaborador, cs);
				
				editorPresenter.getView().clearFields();
				editorPresenter.getView().setHeadingText(messages.addColaborador());

				addToPopupSlot(editorPresenter);
				
			}
		});
		
		getView().getEdit().addSelectHandler(new SelectHandler() {
			
			public void onSelect(SelectEvent event) {
				ColaboradorService cs = factory.colaboradorService();
				colaborador = getView().getGrid().getSelectionModel().getSelectedItem();
				cs.persist(colaborador).to(new Receiver<ColaboradorProxy>() {

					@Override
					public void onSuccess(ColaboradorProxy response) {
						Info.display(messages.titlePanel(), messages.saveSuccessful());
						getView().load();					    
					}
				  });

				editorDriver.edit(colaborador, cs);
				
				editorPresenter.getView().clearFields();
				editorPresenter.getView().setHeadingText(messages.editColaborador());
				
				addToPopupSlot(editorPresenter);
			}
		});
		
		getView().getDelete().addSelectHandler(new SelectHandler() {
			
			public void onSelect(SelectEvent event) {
				List<ColaboradorProxy> colaboradores = getView().getGrid().getSelectionModel().getSelectedItems();
				 
				 String mensaje;
				 if(colaboradores.size() == 1)
				   mensaje = messages.deleteConfirm(colaboradores.get(0).getNombres());
				 else
				   mensaje = messages.deleteAllConfirm();
				 
				 ConfirmMessageBox box = new ConfirmMessageBox(messages.titlePanel(), mensaje);
				 box.addHideHandler(new HideHandler() {
				  
				  public void onHide(HideEvent event) {
				   Dialog btn = (Dialog) event.getSource();
				   if(!"No".equals(btn.getHideButton().getText())){
					ColaboradorService cs = factory.colaboradorService();
					List<ColaboradorProxy> colaboradores = getView().getGrid().getSelectionModel().getSelectedItems();
					
				    cs.remove(colaboradores).fire(new Receiver<Void>() {
				      
				     @Override
				     public void onSuccess(Void response) {
				      Info.display(messages.titlePanel(), messages.deleteSuccessful());
				      getView().load();
				     }
				    });

				   }
				  }
				 });
				 box.show();
			}
		});
	}
	
	@Override
	protected void onReset() {
		super.onReset();
		
	}
}
