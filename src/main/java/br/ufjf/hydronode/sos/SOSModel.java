package br.ufjf.hydronode.sos;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.ufjf.hydronode.jsons.HttpUtils;
import br.ufjf.hydronode.jsons.observationModel.Observation;
import br.ufjf.hydronode.jsons.observationModel.ObservationModel;
import br.ufjf.hydronode.paginas.offering.Content;
import br.ufjf.hydronode.paginas.offering.OfferingModel;

import com.google.gson.Gson;

public class SOSModel {

	static Logger log = LoggerFactory.getLogger(SOSModel.class);

	private static String getCapabilitiesContent() {

		String r = HttpUtils
				.enviaRequisicaoJSON("{\"request\": \"GetCapabilities\", \"service\": \"SOS\", \"sections\": [ \"Contents\" ]}");

		// log.warn("Resposta getCapabilities: {}", r);
		return r;

	}

	public static List<Observation> getObservationsPorOfferring(String offering) {
		String json = "{\"request\": \"GetObservation\", \"service\": \"SOS\", \"version\": \"2.0.0\", \"offering\": \""
				+ offering + "\" }";

		String resposta = HttpUtils.enviaRequisicaoJSON(json);

		ObservationModel obsModel = new Gson().fromJson(resposta,
				ObservationModel.class);

		return obsModel.getObservations();
	}

	public static ArrayList<Content> getOfferings() {

		OfferingModel capabilities = new Gson().fromJson(
				getCapabilitiesContent(), OfferingModel.class);

		log.warn("O numero de ofertas cadastradas no banco eh: {}",
				capabilities.getContents().size());

		return capabilities.getContents();
	}

	/**
	 * Esse metodo deve ser otimizado.
	 */
	public static Content buscaOffering(String offering) {

		ArrayList<Content> ofertas = getOfferings();

		for (Content c : ofertas) {
			if (c.getIdentifier().equals(offering)) {
				log.debug("Oferta encontrada!");
				return c;
			}
		}
		log.warn("Nenhuma oferta encontrada!");

		return null;
	}

	/**
	 * Retorna todas as offerings de uma procedure. Retorna null caso nao
	 * encontre nenhuma offering.
	 */
	public static List<Content> buscaProcedure(String urlOferta) {

		ArrayList<Content> ofertas = getOfferings();

		ArrayList<Content> ofertasDaProcedure = new ArrayList<Content>();

		for (Content c : ofertas) {
			log.warn("Analisando oferta com procedure: {}", c.getProcedure());
			if (c.getProcedure().get(0).equals(urlOferta)) {
				ofertasDaProcedure.add(c);
			}
		}

		if (ofertasDaProcedure.isEmpty()) {
			log.warn("Nenhuma oferta encontrada!");
			return null;
		}

		return ofertasDaProcedure;
	}

	public static String describeSensor(String procedure) {
		String json = "{\"request\": \"DescribeSensor\",\"service\": \"SOS\",\"version\": \"2.0.0\",  \"procedure\": \"+procedure+\",\"procedureDescriptionFormat\": \"http://www.opengis.net/sensorML/1.0.1\"}";
		return HttpUtils.enviaRequisicaoJSON(json);
	}

}
