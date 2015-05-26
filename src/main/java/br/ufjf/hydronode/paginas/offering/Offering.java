package br.ufjf.hydronode.paginas.offering;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.SimpleListModel;

import br.ufjf.hydronode.jsons.SensorRowRenderer;
import br.ufjf.hydronode.sos.SOSModel;

public class Offering extends SelectorComposer<Component> {

	@Wire
	private Grid gridOffering;

	@Listen("onCreate= #mainContent")
	public void exibeOfertas() {

		ListModel<?> listModel = new SimpleListModel<Object>(
				SOSModel.getOfferings());
		gridOffering.setModel(listModel);
		gridOffering.setRowRenderer(new SensorRowRenderer());

	}

}
