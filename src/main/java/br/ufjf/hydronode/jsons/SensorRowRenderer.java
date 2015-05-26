package br.ufjf.hydronode.jsons;

import org.zkoss.zul.A;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

import br.ufjf.hydronode.Config;
import br.ufjf.hydronode.paginas.offering.Content;

/**
 * Classe usada para renderizar uma row de Sensor em uma grid
 * 
 * @author Lucas
 * 
 */
public class SensorRowRenderer implements RowRenderer<Object> {

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
		A aPropObs = new A(content.getObservableProperty().get(0)
				.replace(Config.urlServidor + Config.observableProperty, ""));
		aPropObs.setHref("http://" + content.getObservableProperty().get(0));
		aPropObs.setParent(row);

	}

}