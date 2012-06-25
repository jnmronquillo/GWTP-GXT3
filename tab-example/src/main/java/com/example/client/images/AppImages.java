package com.example.client.images;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface AppImages extends ClientBundle {
	
	@Source("add.gif")
	 ImageResource add();
	  
	 @Source("delete.gif")
	 ImageResource delete();
	  
	 @Source("update.png")
	 ImageResource update();
	  
	 @Source("save.png")
	 ImageResource save();
	  
	 @Source("cancel.png")
	 ImageResource cancel();
	 
	 @Source("table.png")
	 ImageResource table();
}
