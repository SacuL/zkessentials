package br.ufjf.hydronode.sos;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.ufjf.hydronode.jsons.JsonUtils;
import br.ufjf.hydronode.paginas.offering.Content;
import br.ufjf.hydronode.paginas.offering.OfferingModel;
import com.google.gson.Gson;

public class SOSModel {

	static Logger log = LoggerFactory.getLogger(SOSModel.class);

	private static String getCapabilities() {

		String r = JsonUtils
				.enviaRequisicaoJSON("{\"request\": \"GetCapabilities\", \"service\": \"SOS\", \"sections\": [ \"Contents\" ]}");

		log.warn("Resposta getCapabilities: {}", r);
		return r;

	}

	public static String buscaObservationsPorOfferring(String offering) {
		String json = "{\"request\": \"GetObservation\", \"service\": \"SOS\", \"version\": \"2.0.0\", \"offering\": \""
				+ offering + "\" }";

		return JsonUtils.enviaRequisicaoJSON(json);
	}

	public static ArrayList<Content> getOfferings() {

		OfferingModel capabilities = new Gson().fromJson(getCapabilities(),
				OfferingModel.class);

		return capabilities.getContents();
	}

	/**
	 * Esse metodo deve ser otimizado.
	 */
	public static Content buscaOffering(String offering) {

		ArrayList<Content> ofertas = getOfferings();

		log.warn("O numero de ofertas cadastradas no banco eh: {}",
				ofertas.size());

		for (Content c : ofertas) {
			if (c.getIdentifier().equals(offering)) {
				log.warn(
						"Oferta encontrada! \nID: {} \nName: {} \nProcedure: {} \nObsProp {} \nObsArea",
						c.getIdentifier(), c.getName(), c.getProcedure(), c
								.getObservableProperty().size(), c
								.getObservedArea().size());
				return c;
			}
		}
		log.error("Nenhuma oferta encontrada!");

		return null;
	}

}
