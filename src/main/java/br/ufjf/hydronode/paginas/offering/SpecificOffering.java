package br.ufjf.hydronode.paginas.offering;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.gmaps.Gpolygon;
import org.zkoss.gmaps.LatLng;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import br.ufjf.hydronode.Config;
import br.ufjf.hydronode.jsons.ZkUtils;
import br.ufjf.hydronode.jsons.observationModel.Observation;
import br.ufjf.hydronode.sos.SOSModel;

public class SpecificOffering extends SelectorComposer<Component> {

	static Logger log = LoggerFactory.getLogger(SpecificOffering.class);

	@Wire
	private Label titulo, name, localizacaoX, localizacaoY, texto;

	@Wire
	private Button procedure, observableProperty;

	@Wire
	private Gmaps mapa;

	@Wire
	private Gpolygon mapaPoligono;

	@Wire
	private Gmarker mapaMarker;

	@Wire
	private Button botaoLeituras;

	@Wire
	private Grid gridLeituras;

	private Object url;

	private Content oferta;

	@Override
	public void doAfterCompose(Component window) throws Exception {
		url = Executions.getCurrent().getArg().get("url");
		log.warn("URL after compose: {}", url);
		super.doAfterCompose(window);
	}

	@SuppressWarnings("unchecked")
	@Listen("onCreate= #mainContent")
	public void exibeOferta() {

		if (url == null || url.getClass() != String.class) {
			log.error("Erro ao ler Arg da Page (vazio ou tipo incorreto).");
			return;
		}

		String urlOferta = (String) url;

		oferta = SOSModel.buscaOffering(urlOferta);

		if (oferta == null) {
			log.error("Erro ao buscar a oferta");
			botaoLeituras.setVisible(false);
			texto.setVisible(false);
			return;
		}

		name.setValue(oferta.getName());
		procedure.setHref(oferta.getProcedure().get(0));
		procedure.setLabel(oferta.getProcedure().get(0)
				.replace(Config.urlServidor + Config.procedure, ""));
		observableProperty.setHref(oferta.getObservableProperty().get(0));
		observableProperty.setLabel(oferta.getObservableProperty().get(0)
				.replace(Config.urlServidor + Config.observableProperty, ""));

		if (oferta.getObservedArea() == null) {
			// ainda nao ha leituras
			mapa.setVisible(false);
			texto.setVisible(true);
			botaoLeituras.setVisible(false);
			return;
		} else {
			// so pra garantir
			mapa.setVisible(true);
			texto.setVisible(false);
			botaoLeituras.setVisible(true);
		}

		// ///////
		// Mapa //
		// ///////
		Map<String, Object> loc = oferta.getObservedArea();

		Object coordLLeft = loc.get("lowerLeft");
		Object coordURight = loc.get("upperRight");

		if ((!(coordLLeft instanceof ArrayList))
				|| (!(coordURight instanceof ArrayList))) {
			log.error("Erro ao ler as coordenadas do sensor");
			return;
		}

		ArrayList<Double> coordenadasLL = (ArrayList<Double>) coordLLeft;
		ArrayList<Double> coordenadasUR = (ArrayList<Double>) coordURight;

		Double latLL = coordenadasLL.get(0);
		Double lonLL = coordenadasLL.get(1);
		Double latUR = coordenadasUR.get(0);
		Double lonUR = coordenadasUR.get(1);

		if (latLL.equals(latUR) && (lonLL.equals(lonUR))) {
			log.info("Eh um ponto e nao uma area");
			mapaMarker.setLat(latLL);
			mapaMarker.setLng(lonLL);
			mapaMarker.setVisible(true);
			mapaPoligono.setVisible(false);
		} else {

			mapaMarker.setVisible(false);
			mapaPoligono.setVisible(true);

			LatLng pontoLL = new LatLng(latLL, lonLL);
			LatLng pontoUL = new LatLng(latLL, lonUR);
			LatLng pontoLR = new LatLng(latUR, lonLL);
			LatLng pontoUR = new LatLng(latUR, lonUR);

			mapaPoligono.addPath(pontoLL);
			mapaPoligono.addPath(pontoUL);
			mapaPoligono.addPath(pontoUR);
			mapaPoligono.addPath(pontoLR);

			log.warn("Ponto LL = {} , {}", latLL, lonLL);
			log.warn("Ponto UR = {} , {}", latUR, lonUR);
			log.warn("Ponto UL = {} , {}", latLL, lonUR);
			log.warn("Ponto LR = {} , {}", latUR, lonLL);

			// // Calculo do centro do retangulo
			// Double centroX, centroY;
			// if (latUR > latLL) {
			// centroX = ((latUR - latLL) / 2) + latLL;
			// } else {
			// centroX = ((latLL - latUR) / 2) + latUR;
			// }
			// if (lonUR > lonLL) {
			// centroY = ((lonUR - lonLL) / 2) + lonLL;
			// } else {
			// centroY = ((lonLL - lonUR) / 2) + lonUR;
			// }
			//
			// mapa.panTo(centroX, centroY);
			// mapa.setZoom(5);
		}

		ZkUtils.scaleMap(mapa, 300, latLL, latUR, lonLL, lonUR);
		montaGrid();

	}

	// @Listen("onClick = button#botaoLeituras")
	public void montaGrid() {
		if (oferta == null || oferta.getIdentifier() == null
				|| oferta.getIdentifier().isEmpty()) {
			log.info("Oferta vazia (null) ou inválida.");
			Clients.showNotification("Erro: Esse sensor é inválido. Recarregue a página e tente novamente.");
			return;
		}
		List<Observation> leituras = SOSModel
				.getObservationsPorOfferring(oferta.getIdentifier());

		log.info("O sensor {} possui {} leituras.", oferta.getName(),
				leituras.size());

		mapaMarker.setContent("Esse sensor possui " + leituras.size()
				+ " leituras registradas");

		ListModel<Object> listModel = new ListModelList<Object>(leituras);
		gridLeituras.setModel(listModel);
		gridLeituras.setRowRenderer(new RowRenderer<Object>() {

			@SuppressWarnings("unchecked")
			public void render(Row row, Object observation, int index) {
				if (!(observation instanceof Observation)) {
					return;
				}
				Observation obs = (Observation) observation;
				// ID
				String id = obs.getIdentifier().getValue();
				new Label(id.substring(id.lastIndexOf("/") + 1, id.length()))
						.setParent(row);

				// Localizacao
				double[] loc = obs.getFeatureOfInterest().getGeometry()
						.getCoordinates();
				new Label(Double.toString(loc[0]) + "  "
						+ Double.toString(loc[1])).setParent(row);

				// Data/Hora
				new Label(obs.getResultTime()).setParent(row);

				// Valor
				new Label(obs.getResult().getValue() + " "
						+ obs.getResult().getUom()).setParent(row);

				// Detalhes
				Image img = new Image("/imagens/lupa-32.png");
				img.setWidth("20px");
				img.setHeight("20px");
				img.setParent(row);
				img.setStyle("cursor: pointer;");

				// Link para a pagina da leitura
				img.addEventListener("onClick", new MyListener());
			}

		});

		botaoLeituras.setVisible(false);
		gridLeituras.setVisible(true);
	}

	@SuppressWarnings("rawtypes")
	public class MyListener implements EventListener {
		public void onEvent(Event event) {
			Clients.alert("Nome do evento: " + event.getName()
					+ "\nClasse do evento: " + event.getClass().toString());

		}
	}

}
