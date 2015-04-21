package br.ufjf.hydronode;

import org.zkoss.zul.PieModel;
import org.zkoss.zul.SimplePieModel;

public class SensorData {
	public static PieModel getModel() {
		PieModel model = new SimplePieModel();
		model.setValue("C/C++", new Double(21.2));
		model.setValue("VB", new Double(10.2));
		model.setValue("Java", new Double(40.4));
		model.setValue("PHP", new Double(28.2));
		return model;
	}
}
