package br.ufjf.hydronode.paginas.offering;

import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.sys.PageCtrl;

public class GeneralOffering extends GenericRichlet {
	// Richlet
	public void service(Page page) {

		// attach to page as root if parent is null
		Executions.createComponents("/proteger/index.zul", page.getFirstRoot(),
				null);
		
		

		// page.setTitle("Richlet Test");

		// final Window w = new Window("Richlet Test", "normal", false);
		// new Label("Hello World!").setParent(w);
		// final Label l = new Label();
		// l.setParent(w);
		//
		// final Button b = new Button("Change");
		// b.addEventListener(Events.ON_CLICK, new EventListener() {
		// int count;
		//
		// public void onEvent(Event evt) {
		// l.setValue("" + ++count);
		// }
		// });
		// b.setParent(w);
		//
		// w.setPage(page);
	}

	@Override
	public void init(RichletConfig config) {
		super.init(config);
		// initialize resources
	}

	@Override
	public void destroy() {
		super.destroy();
		// destroy resources
	}
}