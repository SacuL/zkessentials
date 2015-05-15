package br.ufjf.hydronode.paginas.offering;

import java.util.ArrayList;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gpolygon;
import org.zkoss.gmaps.LatLng;
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
			localizacaoX, localizacaoY, texto;
	@Wire
	private Gmaps mapa;
	// @Wire
	// private Ginfo mapaInfo;
	// @Wire
	// private Gmarker mapaMarker;
	@Wire
	private Gpolygon mapaPoligono;

	private Object url;

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

		Content oferta = SOSModel.buscaOffering(urlOferta);

		if (oferta == null) {
			log.error("Erro ao buscar a oferta");
			return;
		}

		identifier.setValue(oferta.getIdentifier());
		name.setValue(oferta.getName());
		procedure.setValue(oferta.getProcedure().get(0));
		observableProperty.setValue(oferta.getObservableProperty().get(0));
		// localizacaoX.setValue(Double.toString(oferta.getObservedArea().get(0)
		// .getLowerLeft()[0]));
		// localizacaoX.setValue(Double.toString(oferta.getObservedArea().get(0)
		// .getLowerLeft()[1]));

		if (oferta.getObservedArea() == null) {
			mapa.setVisible(false);
			texto.setVisible(true);
			return;
		} else {
			// so pra garantir
			mapa.setVisible(true);
			texto.setVisible(false);
		}
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

		// localizacaoX.setValue(Double.toString(latLL));
		// localizacaoY.setValue(Double.toString(lonLL));

		// mapaMarker.setLat(latLL);
		// mapaMarker.setLng(lonLL);

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

		// Calculo do centro do retangulo
		Double centroX, centroY;
		if (latUR > latLL) {
			centroX = ((latUR - latLL) / 2) + latLL;
		} else {
			centroX = ((latLL - latUR) / 2) + latUR;
		}
		if (lonUR > lonLL) {
			centroY = ((lonUR - lonLL) / 2) + lonLL;
		} else {
			centroY = ((lonLL - lonUR) / 2) + lonUR;
		}

		mapa.panTo(centroX, centroY);
		// mapa.setZoom(5);

	}

	@Listen("onCreate= #mainContent")
	public void testes() {

	}
}
