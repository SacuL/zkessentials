package br.ufjf.hydronode.requisicoes;

import br.ufjf.hydronode.Config;
import br.ufjf.hydronode.jsons.InsertSensorModel;
import br.ufjf.hydronode.jsons.HttpUtils;
import br.ufjf.hydronode.sensorml.Sensor;
import com.google.gson.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Textbox;

public class InsertSensor extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;

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
		String offering = Config.urlServidor + "/offering/"
				+ offeringName.getValue();
		String procedure = Config.urlServidor + "/procedure/"
				+ procedureName.getValue();
		String obsProperty = Config.urlServidor + "/observableProperty/"
				+ observableProperty.getValue();
		String sensor = Sensor.getSML(procedure, longName.getValue(),
				offeringName.getValue(), offering, localizacaoX.getValue(),
				localizacaoY.getValue(), localizacaoZ.getValue());
		modeloJson.setProcedureDescription(sensor);
		modeloJson.setObservableProperty(obsProperty);
		System.out.println("XML do sensor:\n" + sensor);

		String json = gson.toJson(modeloJson);

		System.out.println("JSON:\n" + json);

		String resposta = HttpUtils.enviaRequisicaoJSON(json);
		Clients.showNotification(resposta);
		System.out.println(resposta);
	}

}
