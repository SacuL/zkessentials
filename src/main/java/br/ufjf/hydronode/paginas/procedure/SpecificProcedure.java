package br.ufjf.hydronode.paginas.procedure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.gmaps.Gpolygon;
import org.zkoss.gmaps.LatLng;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Vlayout;

import br.ufjf.hydronode.Config;
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

		procedure.setValue(ofertas.get(0).getProcedure().get(0)
				.replace(Config.urlServidor + Config.procedure, ""));
		nSensores.setValue(Integer.toString(ofertas.size()));

		Double latLL = null;
		Double lonLL = null;
		Double latUR = null;
		Double lonUR = null;

		// Set usado para guardar, sem repeticao, as propriedades observadas
		Set<String> propriedades = new HashSet<String>();

		for (Content c : ofertas) {

			propriedades.add(c.getObservableProperty().get(0));

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

		// Para cada propriedade observada cria um botao com o link
		for (String p : propriedades) {
			Button b = new Button(p.replace(Config.urlServidor
					+ Config.observableProperty, ""));
			b.setHref("http://" + p);
			observableProperties.appendChild(b);
		}

		if (latLL == null || lonLL == null || latUR == null || lonUR == null) {
			log.info("As ofertas desse procedure nao possuem leituras");
			mapa.setVisible(false);
			texto.setVisible(true);
			return;
		}

		if (latLL.equals(latUR) && (lonLL.equals(lonUR))) {
			log.info("Eh um ponto e nao uma area");
			Gmarker mapaMarker = new Gmarker();
			mapaMarker.setLat(latLL);
			mapaMarker.setLng(lonLL);
			mapaMarker.setParent(mapa);
		} else {

			LatLng pontoLL = new LatLng(latLL, lonLL);
			LatLng pontoUL = new LatLng(latLL, lonUR);
			LatLng pontoLR = new LatLng(latUR, lonLL);
			LatLng pontoUR = new LatLng(latUR, lonUR);

			mapaPoligono = new Gpolygon();

			mapaPoligono.addPath(pontoLL);
			mapaPoligono.addPath(pontoUL);
			mapaPoligono.addPath(pontoUR);
			mapaPoligono.addPath(pontoLR);

			mapaPoligono.setParent(mapa);
		}

		log.warn("Ponto LL = {} , {}", latLL, lonLL);
		log.warn("Ponto UR = {} , {}", latUR, lonUR);
		log.warn("Ponto UL = {} , {}", latLL, lonUR);
		log.warn("Ponto LR = {} , {}", latUR, lonLL);

		ZkUtils.scaleMap(mapa, 300, latLL, latUR + 0.003, lonLL, lonUR + 0.003);

	}

}
