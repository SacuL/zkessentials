package br.ufjf.hydronode.paginas.procedure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.zkoss.zul.Vlayout;

import br.ufjf.hydronode.jsons.ZkUtils;
import br.ufjf.hydronode.paginas.offering.Content;
import br.ufjf.hydronode.sos.SOSModel;

public class SpecificProcedure extends SelectorComposer<Component> {

	static Logger log = LoggerFactory.getLogger(SpecificProcedure.class);

	@Wire
	private Label titulo, procedure, nSensores, localizacaoX, localizacaoY,
			texto;

	@Wire
	private Vlayout observableProperties;

	@Wire
	private Gmaps mapa;

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

		List<Content> ofertas = SOSModel.buscaProcedure(urlOferta);

		if (ofertas == null) {
			log.error("Erro ao buscar as ofertas da procedure.");
			return;
		}

		procedure.setValue(ofertas.get(0).getProcedure().get(0));
		nSensores.setValue(Integer.toString(ofertas.size()));
		mapa.setVisible(false);
		texto.setVisible(true);

		Double latLL = null;
		Double lonLL = null;
		Double latUR = null;
		Double lonUR = null;

		Map<String, Label> labels = new HashMap<String, Label>();

		for (Content c : ofertas) {
			Label label = new Label(c.getObservableProperty().get(0));
			label.setWidth("400px");
			labels.put(c.getObservableProperty().get(0), label);

			if (c.getObservedArea() != null) {

				Map<String, Object> loc = c.getObservedArea();

				Object coordLLeft = loc.get("lowerLeft");
				Object coordURight = loc.get("upperRight");

				if ((!(coordLLeft instanceof ArrayList))
						|| (!(coordURight instanceof ArrayList))) {
					log.error("Erro ao ler as coordenadas do sensor");
					return;
				}

				ArrayList<Double> tempLL = (ArrayList<Double>) coordLLeft;
				ArrayList<Double> tempUR = (ArrayList<Double>) coordURight;

				Double tempLatLL = tempLL.get(0);
				Double tempLonLL = tempLL.get(1);
				Double tempLatUR = tempUR.get(0);
				Double tempLonUR = tempUR.get(1);

				if (latLL == null || tempLatLL < latLL) {
					latLL = tempLatLL;
				}
				if (lonLL == null || tempLonLL < lonLL) {
					lonLL = tempLonLL;
				}
				if (latUR == null || tempLatUR > latUR) {
					latUR = tempLatUR;
				}
				if (lonUR == null || tempLonUR > lonUR) {
					lonUR = tempLonUR;
				}

			}
		}

		for (Map.Entry<String, Label> l : labels.entrySet()) {
			observableProperties.appendChild(l.getValue());
		}

		if (latLL == null || lonLL == null || latUR == null || lonUR == null) {
			log.info("As ofertas desse procedure nao possuem leituras");
			return;
		}

		mapa.setVisible(true);
		texto.setVisible(false);

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

		// mapa.panTo(centroX, centroY);
		// mapa.setZoom(5);
		ZkUtils.scaleMap(mapa, 300, latLL, latUR, lonLL, lonUR);

	}

}
