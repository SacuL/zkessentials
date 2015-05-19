package br.ufjf.hydronode.requisicoes;

import br.ufjf.hydronode.Config;
import br.ufjf.hydronode.jsons.InsertObservationModel;
import br.ufjf.hydronode.jsons.HttpUtils;
import br.ufjf.hydronode.jsons.observationModel.FeatureOfInterest;
import br.ufjf.hydronode.jsons.observationModel.Geometry;
import br.ufjf.hydronode.jsons.observationModel.Identifier;
import br.ufjf.hydronode.jsons.observationModel.Name;
import br.ufjf.hydronode.jsons.observationModel.Observation;
import br.ufjf.hydronode.jsons.observationModel.Result;
import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Textbox;

public class InsertObservation extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;

	static Logger log = LoggerFactory.getLogger(InsertObservation.class);

	String codespaceVazio = "http://www.opengis.net/def/nil/OGC/0/unknown";
	String observationType = "http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_Measurement";

	@Wire
	Textbox oferta;
	@Wire
	Textbox observationIdentifier;
	@Wire
	Textbox procedure;
	@Wire
	Textbox observedProperty;
	@Wire
	Textbox featureOfInterest;
	@Wire
	Doublebox localizacaoX, localizacaoY;
	@Wire
	Textbox phenomenonTime;
	@Wire
	Doublebox value;
	@Wire
	Textbox uom;

	@Listen("onClick=#enviar")
	public void enviar() {

		InsertObservationModel iom = montaInsertObservation();

		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(iom);

		log.info("JSON:\n{}", json);

		String resposta = HttpUtils.enviaRequisicaoJSON(json);
		if (resposta == null) {
			Clients.showNotification("Um erro ocorreu");
			return;
		}
		Clients.showNotification(resposta);

	}

	private InsertObservationModel montaInsertObservation() {
		Geometry featureGeo = new Geometry("Point", new double[] {
				localizacaoX.getValue(), localizacaoY.getValue() });
		Name featureName = new Name(featureOfInterest.getValue(),
				codespaceVazio);
		Identifier featureIdentifier = new Identifier(Config.urlServidor
				+ Config.featureOfInterest + featureOfInterest.getValue(),
				codespaceVazio);
		Identifier obsIdentifier = new Identifier(Config.urlServidor
				+ Config.observation + observationIdentifier.getValue(),
				codespaceVazio);
		Result resultado = new Result(uom.getValue(), value.getValue());

		FeatureOfInterest foi = new FeatureOfInterest(featureIdentifier,
				featureName, featureGeo);

		String obsProperty = Config.urlServidor + Config.observableProperty
				+ observedProperty.getValue();

		Observation obs = new Observation(obsIdentifier, observationType,
				Config.urlServidor + Config.procedure + procedure.getValue(),
				obsProperty, foi, phenomenonTime.getValue(),
				phenomenonTime.getValue(), resultado);

		InsertObservationModel iom = new InsertObservationModel();
		iom.setOffering(Config.urlServidor + Config.offering
				+ oferta.getValue());
		iom.setObservation(obs);
		return iom;
	}
}
