package br.ufjf.hydronode;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.CategoryModel;

import br.ufjf.hydronode.ChartData;

public class ChartsVM {

	CategoryModel model;
	String type;

	@Init
	public void init() {
		// prepare chart data
		type = "line";
		model = ChartData.getModel();
	}

	public CategoryModel getModel() {
		return model;
	}

	public String getType() {
		return type;
	}

	@GlobalCommand("configChanged")
	@NotifyChange("type")
	public void onConfigChanged(@BindingParam("type") String type) {
		this.type = type;
	}
}