package br.ufjf.hydronode.requisicoes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import br.ufjf.hydronode.jsons.InsertObservationModel;
import br.ufjf.hydronode.jsons.InsertSensorModel;
import br.ufjf.hydronode.jsons.observationModel.FeatureOfInterest;
import br.ufjf.hydronode.jsons.observationModel.Geometry;
import br.ufjf.hydronode.jsons.observationModel.Identifier;
import br.ufjf.hydronode.jsons.observationModel.Name;
import br.ufjf.hydronode.jsons.observationModel.Observation;
import br.ufjf.hydronode.jsons.observationModel.Result;
import br.ufjf.hydronode.sensorml.Sensor;

import com.google.gson.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

public class InsertObservation extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;

	String postURL = "http://localhost:8182/52n-sos-webapp/service";
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

	public void teste() {
		// montar o json e enviar

		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		HttpPost post = new HttpPost(postURL);

		Geometry geo = new Geometry("tipo teste", new double[] { 10, 20 });

		String jsonTeste = gson.toJson(geo);
		System.out.println("geometry: " + jsonTeste);
		InsertSensorModel modeloJson = new InsertSensorModel();

		// String sensor = Sensor.getSML(identificador.getValue(),
		// nome.getValue(), oferta.getValue(), localizacao.getValue(),
		// saidaOntologia.getValue(), saidaDescricao.getValue(),
		// saidaUOM.getValue());
		//
		// modeloJson.setProcedureDescription(sensor);
		// System.out.println("XML do sensor:\n" + sensor);
		// System.out.println("XML dentro do modelo:\n"
		// + modeloJson.getProcedureDescription());
		// String json = gson.toJson(modeloJson);
		//
		// System.out.println("Json gerado:\n" + json);
		//
		// StringEntity postingString;
		// try {
		// postingString = new StringEntity(json);
		// } catch (UnsupportedEncodingException e) {
		// Clients.showNotification("Erro ao montar requisicao json.");
		// return;
		// }
		//
		// post.setEntity(postingString);
		// post.setHeader("content-type", "application/json");
		//
		// HttpClient httpClient = new DefaultHttpClient();
		// HttpResponse response;
		// try {
		// response = httpClient.execute(post);
		// } catch (IOException e) {
		// Clients.showNotification("Erro ao enviar post com json.");
		// return;
		// }
		//
		// String responseString;
		// try {
		// responseString = new BasicResponseHandler()
		// .handleResponse(response);
		// } catch (IOException e) {
		// Clients.showNotification("Erro ao recever resposta json.");
		// return;
		// }
		//
		// Clients.showNotification("Enviado!\nResposta:\n" + responseString);
		// System.out.println("Resposta:\n" + responseString);
	}

	@Listen("onClick=#enviar")
	public void enviar() {
		// montar o json e enviar

		// criar o objeto que sera transformado em json
		InsertObservationModel iom = montaInsertObservation();

		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(iom);
		System.out.println("JSON:");
		System.out.println(json);
		// HttpPost post = new HttpPost(postURL);
		//
		// InsertSensorModel modeloJson = new InsertSensorModel();
		//
		// String sensor = Sensor.getSML(identificador.getValue(),
		// nome.getValue(), oferta.getValue(), localizacao.getValue(),
		// saidaOntologia.getValue(), saidaDescricao.getValue(),
		// saidaUOM.getValue());
		//
		// modeloJson.setProcedureDescription(sensor);
		// System.out.println("XML do sensor:\n" + sensor);
		// System.out.println("XML dentro do modelo:\n"
		// + modeloJson.getProcedureDescription());
		// String json = gson.toJson(modeloJson);
		//
		// System.out.println("Json gerado:\n" + json);
		//
		// StringEntity postingString;
		// try {
		// postingString = new StringEntity(json);
		// } catch (UnsupportedEncodingException e) {
		// Clients.showNotification("Erro ao montar requisicao json.");
		// return;
		// }
		//
		// post.setEntity(postingString);
		// post.setHeader("content-type", "application/json");
		//
		// HttpClient httpClient = new DefaultHttpClient();
		// HttpResponse response;
		// try {
		// response = httpClient.execute(post);
		// } catch (IOException e) {
		// Clients.showNotification("Erro ao enviar post com json.");
		// return;
		// }
		//
		// String responseString;
		// try {
		// responseString = new BasicResponseHandler()
		// .handleResponse(response);
		// } catch (IOException e) {
		// Clients.showNotification("Erro ao recever resposta json.");
		// return;
		// }
		//
		// Clients.showNotification("Enviado!\nResposta:\n" + responseString);
		// System.out.println("Resposta:\n" + responseString);
	}

	private InsertObservationModel montaInsertObservation() {
		Geometry featureGeo = new Geometry("Point", new double[] {
				localizacaoX.getValue(), localizacaoY.getValue() });
		Name featureName = new Name(featureOfInterest.getValue(),
				codespaceVazio);
		Identifier featureIdentifier = new Identifier(
				featureOfInterest.getValue(), codespaceVazio);
		Identifier obsIdentifier = new Identifier(
				observationIdentifier.getValue(), codespaceVazio);
		Result resultado = new Result(uom.getValue(), value.getValue());

		FeatureOfInterest foi = new FeatureOfInterest(featureIdentifier,
				featureName, featureGeo);

		Observation obs = new Observation(obsIdentifier, observationType,
				procedure.getValue(), observedProperty.getValue(), foi,
				phenomenonTime.getValue(), phenomenonTime.getValue(), resultado);

		InsertObservationModel iom = new InsertObservationModel();
		iom.setOffering(oferta.getValue());
		iom.setObservation(obs);
		return iom;
	}
}
