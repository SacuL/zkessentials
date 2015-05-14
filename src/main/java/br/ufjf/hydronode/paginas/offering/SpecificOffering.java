package br.ufjf.hydronode.paginas.offering;

import java.util.ArrayList;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;

import br.ufjf.hydronode.sos.SOSModel;

public class SpecificOffering extends SelectorComposer<Component> {

	static Logger log = LoggerFactory.getLogger(SpecificOffering.class);

	@Wire
	private Label titulo, identifier, name, procedure, observableProperty,
			localizacaoX, localizacaoY;

	private Object url;

	@Override
	public void doAfterCompose(Component window) throws Exception {
		url = Executions.getCurrent().getArg().get("url");
		log.warn("URL after compose: {}", url);
		super.doAfterCompose(window);
	}

	@Listen("onCreate= #mainContent")
	public void exibeOferta() {

		if (url == null || url.getClass() != String.class) {
			log.error("Erro ao ler Arg da Page (vazio ou tipo incorreto).");
			return;
		}

		String urlOferta = (String) url;

		Content oferta = SOSModel.buscaOffering(urlOferta);

		if (oferta == null) {
			log.error("Erro ao buscar a oferta");
			return;
		}

		titulo.setValue("Sensor " + oferta.getIdentifier());

		identifier.setValue(oferta.getIdentifier());
		name.setValue(oferta.getName());
		procedure.setValue(oferta.getProcedure().get(0));
		observableProperty.setValue(oferta.getObservableProperty().get(0));
		// localizacaoX.setValue(Double.toString(oferta.getObservedArea().get(0)
		// .getLowerLeft()[0]));
		// localizacaoX.setValue(Double.toString(oferta.getObservedArea().get(0)
		// .getLowerLeft()[1]));

		Map<String, Object> loc = oferta.getObservedArea();

		Object coord = loc.get("lowerLeft");

		if (!(coord instanceof ArrayList)) {
			log.error("Erro ao ler as coordenadas do sensor");
			return;
		}
		@SuppressWarnings("unchecked")
		ArrayList<Double> coordenadas = (ArrayList<Double>) coord;

		// for (Map.Entry<String, Object> entry : loc.entrySet()) {
		// log.warn("Entry do Map: {} / {}", entry.getKey(), entry.getValue());
		// }

		localizacaoX.setValue(Double.toString(coordenadas.get(0)));
		localizacaoY.setValue(Double.toString(coordenadas.get(1)));

	}

}
