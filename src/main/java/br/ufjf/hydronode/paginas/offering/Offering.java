package br.ufjf.hydronode.paginas.offering;

import java.lang.reflect.Type;
import java.util.List;

import org.zkoss.util.CollectionsX.ArrayList;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.SimpleListModel;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import br.ufjf.hydronode.Config;
import br.ufjf.hydronode.jsons.JsonUtils;

public class Offering extends SelectorComposer<Component> {

	@Wire
	private Grid gridOffering;

	// @Listen("onClick=#enviar")
	@Listen("onCreate= #mainContent")
	public void teste() {
		String resposta = JsonUtils
				.enviaRequisicaoJSON("{\"request\": \"GetCapabilities\", \"service\": \"SOS\", \"sections\": [ \"Contents\" ]}");

		if (resposta.substring(0, 4).equals("Erro")) {
			return;
		}

		OfferingModel targetObject = new Gson().fromJson(resposta,
				OfferingModel.class);

		ListModel listModel = new SimpleListModel(targetObject.getContents());
		gridOffering.setModel(listModel);
		gridOffering.setRowRenderer(new MyRowRenderer());

	}

	public class MyRowRenderer implements RowRenderer {

		public void render(Row row, Object data, int index) throws Exception {
			Content content = (Content) data;
			new Label(content.getName()).setParent(row);
			new Label(content.getProcedure().get(0)
					.replace(Config.urlServidor + "/procedure/", ""))
					.setParent(row);
			new Label(content.getObservableProperty().get(0)
					.replace(Config.urlServidor + "/observableProperty/", ""))
					.setParent(row);

		}

	}

}
