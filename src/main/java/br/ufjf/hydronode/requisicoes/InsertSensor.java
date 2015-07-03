package br.ufjf.hydronode.requisicoes;

import br.ufjf.hydronode.Config;
import br.ufjf.hydronode.jsons.InsertSensorModel;
import br.ufjf.hydronode.jsons.HttpUtils;
import br.ufjf.hydronode.sensorml.Sensor;

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

public class InsertSensor extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;

	static Logger log = LoggerFactory.getLogger(InsertSensor.class);

	String postURL = Config.urlSOS;

	@Wire
	Textbox procedureName;
	@Wire
	Textbox longName;
	@Wire
	Textbox offeringName;
	@Wire
	Doublebox localizacaoX;
	@Wire
	Doublebox localizacaoY;
	@Wire
	Doublebox localizacaoZ;
	@Wire
	Textbox observableProperty;

	@Listen("onClick=#enviar")
	public void enviar() {
		// montar o json e enviar

		// http://stackoverflow.com/questions/4147012/can-you-avoid-gson-converting-and-into-unicode-escape-sequences
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();

		InsertSensorModel modeloJson = new InsertSensorModel();
		String offering = Config.urlServidor + Config.offering
				+ offeringName.getValue();
		String procedure = Config.urlServidor + Config.procedure
				+ procedureName.getValue();
		String obsProperty = observableProperty.getValue();
		String sensor = Sensor.getSML(procedure, longName.getValue(),
				offeringName.getValue(), offering, localizacaoX.getValue(),
				localizacaoY.getValue(), localizacaoZ.getValue());
		modeloJson.setProcedureDescription(sensor);
		modeloJson.setObservableProperty(obsProperty);

		log.warn("XML do sensor:\n{}", sensor);

		String json = gson.toJson(modeloJson);

		log.warn("JSON:\n{}", json);

		String resposta = HttpUtils.enviaRequisicaoJSON(json);
		if (resposta == null) {
			Clients.showNotification("Um erro ocorreu.");
			return;
		}
		Clients.showNotification("Sensor registrado com sucesso!");

		log.warn("Resposta:\n{}", resposta);
	}

}
