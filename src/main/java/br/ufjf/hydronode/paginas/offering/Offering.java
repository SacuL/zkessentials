package br.ufjf.hydronode.paginas.offering;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Grid;
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

			// Nome
			A aNome = new A(content.getName());
			aNome.setHref("http://" + content.getIdentifier());
			aNome.setParent(row);

			// Procedimento
			A aProcedimento = new A(content.getProcedure().get(0)
					.replace(Config.urlServidor + Config.procedure, ""));
			aProcedimento.setHref("http://" + content.getProcedure().get(0));
			aProcedimento.setParent(row);

			// Propriedade Observada
			A aPropObs = new A(
					content.getObservableProperty()
							.get(0)
							.replace(
									Config.urlServidor
											+ Config.observableProperty, ""));
			aPropObs.setHref("http://" + content.getObservableProperty().get(0));
			aPropObs.setParent(row);

		}

	}

}
