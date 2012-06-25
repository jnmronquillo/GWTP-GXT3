package com.example.client.view;

import java.util.ArrayList;
import java.util.List;

import com.example.client.Messages;
import com.example.client.presenter.ColaboradorPresenter;
import com.example.shared.proxy.ColaboradorProxy;
import com.example.shared.service.AppRequestFactory;
import com.example.shared.service.ColaboradorService;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.data.shared.loader.RequestFactoryProxy;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.NumericFilter;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

public class ColaboradorView extends ViewImpl implements
		ColaboradorPresenter.MyView {
	
	
	
	interface ColaboradorProxyProperties extends PropertyAccess<ColaboradorProxy> {
	    ModelKeyProvider<ColaboradorProxy> id();
	    ValueProvider<ColaboradorProxy, String> codigo();
	    ValueProvider<ColaboradorProxy, String> nombres();
	    ValueProvider<ColaboradorProxy, String> apellidos();
	    ValueProvider<ColaboradorProxy, Integer> edad();
	}

	private final Widget widget;
		
	public interface Binder extends UiBinder<Widget, ColaboradorView> {
	}
	
	private ListStore<ColaboradorProxy> store;
	private PagingLoader<FilterPagingLoadConfig, PagingLoadResult<ColaboradorProxy>> loader;
	
	private final AppRequestFactory factory;
	
	@UiField(provided = true)
	Grid<ColaboradorProxy> grid;
	
	@UiField(provided = true)
	PagingToolBar pagToolBar;
	
	@UiField
	ContentPanel panel;
	
	@UiField
	TextButton add;
	
	@UiField
	TextButton edit;
	 
	@UiField
	TextButton delete;
		
	@Inject
	public ColaboradorView(final Binder binder, Messages messages, Provider<AppRequestFactory> provider) {
		
		this.factory = provider.get();
		
		RequestFactoryProxy<FilterPagingLoadConfig, PagingLoadResult<ColaboradorProxy>> rfproxy = new RequestFactoryProxy<FilterPagingLoadConfig, PagingLoadResult<ColaboradorProxy>>() {
		    
			 @Override
			 public void load(FilterPagingLoadConfig loadConfig,
			   Receiver<? super PagingLoadResult<ColaboradorProxy>> receiver) {
			   ColaboradorService cs = factory.colaboradorService();			   
			   List<SortInfo> sortInfo = createRequestSortInfo(cs, loadConfig.getSortInfo());
		       
			   List<FilterConfig> filterConfig = createRequestFilterConfig(cs, loadConfig.getFilters());
			   
			   cs.list(loadConfig.getOffset(), loadConfig.getLimit(), sortInfo, filterConfig).to(receiver);
			   
			   cs.fire();    
			 }
			 
		};
		loader = new PagingLoader<FilterPagingLoadConfig, PagingLoadResult<ColaboradorProxy>>(rfproxy){
			@Override
		    protected FilterPagingLoadConfig newLoadConfig() {
		      return new FilterPagingLoadConfigBean();
		    }
		};
		loader.setRemoteSort(true);
		ColaboradorProxyProperties props = GWT.create(ColaboradorProxyProperties.class);
		
		store = new ListStore<ColaboradorProxy>(props.id());
		loader.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, ColaboradorProxy, PagingLoadResult<ColaboradorProxy>>(store));
				
		pagToolBar = new PagingToolBar(2);
		pagToolBar.bind(loader);
		pagToolBar.getElement().getStyle().setProperty("borderBottom", "none");
		
		IdentityValueProvider<ColaboradorProxy> identity = new IdentityValueProvider<ColaboradorProxy>();
		final CheckBoxSelectionModel<ColaboradorProxy> sm = new CheckBoxSelectionModel<ColaboradorProxy>(identity);
		sm.setSelectionMode(SelectionMode.MULTI);
		
		ColumnConfig<ColaboradorProxy, String> codigoColumn = new ColumnConfig<ColaboradorProxy, String>(props.codigo(), 80, messages.code());
		ColumnConfig<ColaboradorProxy, String> nombresColumn = new ColumnConfig<ColaboradorProxy, String>(props.nombres(), 150, messages.firstName());
		ColumnConfig<ColaboradorProxy, String> apellidosColumn = new ColumnConfig<ColaboradorProxy, String>(props.apellidos(), 150, messages.lastName());
		ColumnConfig<ColaboradorProxy, Integer> edadColumn = new ColumnConfig<ColaboradorProxy, Integer>(props.edad(), 80, messages.age());
		 
		List<ColumnConfig<ColaboradorProxy, ?>> l = new ArrayList<ColumnConfig<ColaboradorProxy, ?>>();
		l.add(sm.getColumn());
		l.add(codigoColumn);
		l.add(nombresColumn);
		l.add(apellidosColumn);
		l.add(edadColumn);
		 
		ColumnModel<ColaboradorProxy> cm = new ColumnModel<ColaboradorProxy>(l);
		 
		grid = new Grid<ColaboradorProxy>(store, cm) {
		    @Override
		    protected void onAfterFirstAttach() {
		      super.onAfterFirstAttach();
		      Scheduler.get().scheduleDeferred(new ScheduledCommand() {
		        public void execute() {
		          loader.load();
		        }
		      });
		    }
		};
		 
		grid.setLoader(loader);
		grid.setSelectionModel(sm);		    
	    grid.getView().setStripeRows(true);
		
		GridFilters<ColaboradorProxy> filters = new GridFilters<ColaboradorProxy>(loader);
	    filters.initPlugin(grid);
	    filters.setLocal(false);
	    filters.addFilter(new StringFilter<ColaboradorProxy>(props.codigo()));
	    filters.addFilter(new StringFilter<ColaboradorProxy>(props.nombres()));
	    filters.addFilter(new StringFilter<ColaboradorProxy>(props.apellidos()));
	    filters.addFilter(new NumericFilter<ColaboradorProxy, Integer>(props.edad(), new NumberPropertyEditor.IntegerPropertyEditor()));
		 
		grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<ColaboradorProxy>() {
		
		  public void onSelectionChanged(
		    SelectionChangedEvent<ColaboradorProxy> event) {
		   int size = event.getSelection().size();
		   if(size == 0){     
		     edit.setEnabled(false);
		     delete.setEnabled(false);
		   }else if(size == 1){      
		     edit.setEnabled(true);
		     delete.setEnabled(true);
		   }else if(size > 1){
		     edit.setEnabled(false);
		     delete.setEnabled(true);
		   }       
		    
		  }
		 });
		
		widget = binder.createAndBindUi(this);
	}

	public Widget asWidget() {
		return widget;
	}
	
	public TextButton getAdd() {
		return add;
	}
	
	public TextButton getEdit() {
		return edit;
	}
	
	public TextButton getDelete() {
		return delete;
	}
	
	public void load(){
		loader.load();
	}
	
	public Grid<ColaboradorProxy> getGrid() {
		return grid;
	}
	
	public ContentPanel getPanel() {
		return panel;
	}
}
