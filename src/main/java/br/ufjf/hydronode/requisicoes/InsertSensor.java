package br.ufjf.hydronode.requisicoes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import br.ufjf.hydronode.jsons.InsertSensorModel;
import br.ufjf.hydronode.sensorml.Sensor;

import com.google.gson.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

public class InsertSensor extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;

	String postURL = "http://localhost:8182/52n-sos-webapp/service";

	@Wire
	Textbox nome;
	@Wire
	Textbox identificador;
	@Wire
	Label oferta;
	@Wire
	Textbox localizacao;
	@Wire
	Textbox saidaOntologia;
	@Wire
	Textbox saidaDescricao;
	@Wire
	Textbox saidaUOM;

	@Listen("onClick=#enviar")
	public void enviar() {
		// montar o json e enviar

		// http://stackoverflow.com/questions/4147012/can-you-avoid-gson-converting-and-into-unicode-escape-sequences
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		HttpPost post = new HttpPost(postURL);

		InsertSensorModel modeloJson = new InsertSensorModel();

		String sensor = Sensor.getSML(identificador.getValue(),
				nome.getValue(), oferta.getValue(), localizacao.getValue(),
				saidaOntologia.getValue(), saidaDescricao.getValue(),
				saidaUOM.getValue());

		modeloJson.setProcedureDescription(sensor);
		System.out.println("XML do sensor:\n" + sensor);
		System.out.println("XML dentro do modelo:\n"
				+ modeloJson.getProcedureDescription());
		String json = gson.toJson(modeloJson);

		System.out.println("Json gerado:\n" + json);

		StringEntity postingString;
		try {
			postingString = new StringEntity(json);
		} catch (UnsupportedEncodingException e) {
			Clients.showNotification("Erro ao montar requisicao json.");
			return;
		}

		post.setEntity(postingString);
		post.setHeader("content-type", "application/json");

		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response;
		try {
			response = httpClient.execute(post);
		} catch (IOException e) {
			Clients.showNotification("Erro ao enviar post com json.");
			return;
		}

		String responseString;
		try {
			responseString = new BasicResponseHandler()
					.handleResponse(response);
		} catch (IOException e) {
			Clients.showNotification("Erro ao recever resposta json.");
			return;
		}

		Clients.showNotification("Enviado!\nResposta:\n" + responseString);
		System.out.println("Resposta:\n" + responseString);
	}

	@Listen("onChange=#identifier")
	public void atualizaOferta() {
		if (identificador.getValue() == null) {
			return;
		}
		oferta.setValue("hydronode.ufjf.br/oferta/" + identificador.getValue());
	}

}
