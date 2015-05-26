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
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Center;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Vlayout;

import br.ufjf.hydronode.Config;
import br.ufjf.hydronode.jsons.SensorRowRenderer;
import br.ufjf.hydronode.jsons.ZkUtils;
import br.ufjf.hydronode.paginas.offering.Content;
import br.ufjf.hydronode.sos.SOSModel;

public class SpecificProcedure extends SelectorComposer<Component> {

	static Logger log = LoggerFactory.getLogger(SpecificProcedure.class);

	@Wire
	private Label titulo, procedure, nSensores, localizacaoX, localizacaoY,
			texto, tituloPagina;

	@Wire
	private Vlayout observableProperties;

	@Wire
	private Gmaps mapa;

	@Wire
	private Grid gridSensores;

	@Wire
	private Center mainContent;

	@Wire
	private Tabbox meuTabbox;

	private Gpolygon mapaPoligono;

	private Object url;

	private List<Content> ofertas;

	@Override
	public ComponentInfo doBeforeCompose(Page page, Component parent,
			ComponentInfo compInfo) {

		// Pega o atributo url que foi passado como argumento
		url = Executions.getCurrent().getArg().get("url");
		log.warn("URL before compose: {}", url);
		if (url == null || url.getClass() != String.class) {
			log.error("Erro ao ler Arg da Page (vazio ou tipo incorreto).");
			redirecionaAoIndex();
			return null;
		}

		// Busca os sensores da estação no banco de dados
		String urlEstacao = (String) url;
		ofertas = SOSModel.buscaProcedure(urlEstacao);
		if (ofertas == null) {
			log.error("Erro ao buscar os sensores da estação");
			redirecionaAoIndex();
			return null;
		}

		return super.doBeforeCompose(page, parent, compInfo);
	}

	@SuppressWarnings("unchecked")
	@Listen("onCreate= #mainContent")
	public void exibeOferta() {

		// Pega o nome da estacao
		String nome = ofertas.get(0).getProcedure().get(0)
				.replace(Config.urlServidor + Config.procedure, "");

		// Preenche o titulo da pagina
		tituloPagina.setValue(nome);

		// Preenche o grid com as informações da estação
		procedure.setValue(nome);
		nSensores.setValue(Integer.toString(ofertas.size()));

		Double latLL = null;
		Double lonLL = null;
		Double latUR = null;
		Double lonUR = null;

		// Set usado para guardar, sem repetição, as propriedades observadas
		Set<String> propriedades = new HashSet<String>();

		// Para cada sensor da estação...
		for (Content c : ofertas) {

			// Adiciona a propriedade observada ao Set
			propriedades.add(c.getObservableProperty().get(0));

			// Pega a area observada
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

		// Para cada propriedade observada cria um link
		for (String p : propriedades) {
			A a = new A(p.replace(Config.urlServidor
					+ Config.observableProperty, ""));
			a.setHref("http://" + p);
			observableProperties.appendChild(a);
		}

		if (latLL == null || lonLL == null || latUR == null || lonUR == null) {
			log.info("As ofertas desse procedure nao possuem leituras");
			mapa.setVisible(false);
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

		// Centraliza e escala o mapa
		ZkUtils.scaleMap(mapa, 300, latLL, latUR + 0.003, lonLL, lonUR + 0.003);

		// Cria um ListModel para preencher a lista de sensores
		ListModel<?> listModel = new SimpleListModel<Object>(ofertas);
		gridSensores.setModel(listModel);
		gridSensores.setRowRenderer(new SensorRowRenderer());

	}

	// @Listen("onSelect = tabbox#meuTabbox")
	// public void invalida() {
	// meuTabbox.setHeight(null);
	// }

	/**
	 * Redireciona para a página inicial das estações.
	 */
	private void redirecionaAoIndex() {
		Executions.sendRedirect("/swe/estacao/");
	}

}
