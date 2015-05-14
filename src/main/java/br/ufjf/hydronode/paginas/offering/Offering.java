package br.ufjf.hydronode.paginas.offering;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.SimpleListModel;

import br.ufjf.hydronode.Config;
import br.ufjf.hydronode.sos.SOSModel;

public class Offering extends SelectorComposer<Component> {

	@Wire
	private Grid gridOffering;

	@Listen("onCreate= #mainContent")
	public void exibeOfertas() {

		ListModel<?> listModel = new SimpleListModel<Object>(
				SOSModel.getOfferings());
		gridOffering.setModel(listModel);
		gridOffering.setRowRenderer(new MyRowRenderer());

	}

	public class MyRowRenderer implements RowRenderer<Object> {

		public void render(Row row, Object data, int index) throws Exception {
			Content content = (Content) data;
			new Label(content.getName()).setParent(row);
			new Label(content.getProcedure().get(0)
					.replace(Config.urlServidor + "/procedure/", ""))
					.setParent(row);
			new Label(content.getObservableProperty().get(0)
					.replace(Config.urlServidor + "/observableProperty/", ""))
					.setParent(row);

		}

	}

}
