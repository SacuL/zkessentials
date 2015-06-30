package br.ufjf.hydronode;

import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.SimpleCategoryModel;

public class ChartData {

	public static CategoryModel getModel() {
		SimpleCategoryModel model = new SimpleCategoryModel();
		model.setValue("2013", "Janeiro", new Double(23.2));
		model.setValue("2013", "Fevereiro", new Double(23.9));
		model.setValue("2013", "Março", new Double(20.3));
		model.setValue("2013", "Abril", new Double(21.0));
		model.setValue("2013", "Maio", new Double(23.5));
		model.setValue("2013", "Junho", new Double(25.2));
		model.setValue("2013", "Julho", new Double(27.8));
		model.setValue("2013", "Agosto", new Double(21.1));
		model.setValue("2013", "Setembro", new Double(19.3));
		model.setValue("2013", "Outubro", new Double(20.0));
		model.setValue("2013", "Novembro", new Double(19.7));
		model.setValue("2013", "Dezembro", new Double(22.8));
		
		model.setValue("2014", "Janeiro", new Double(22.1));
		model.setValue("2014", "Fevereiro", new Double(24.0));
		model.setValue("2014", "Março", new Double(18.9));
		model.setValue("2014", "Abril", new Double(22.1));
		model.setValue("2014", "Maio", new Double(24.5));
		model.setValue("2014", "Junho", new Double(24.6));
		model.setValue("2014", "Julho", new Double(25.0));
		model.setValue("2014", "Agosto", new Double(24.2));
		model.setValue("2014", "Setembro", new Double(22.5));
		model.setValue("2014", "Outubro", new Double(20.9));
		model.setValue("2014", "Novembro", new Double(21.3));
		model.setValue("2014", "Dezembro", new Double(22.4));
		
		

//		model.setValue("Beta", "2006", new Integer(174));
//		model.setValue("Beta", "2007", new Integer(244));
//		model.setValue("Beta", "2008", new Integer(124));
//		model.setValue("Beta", "2009", new Integer(174));

//		model.setValue("Gamma", "2006", new Integer(156));
//		model.setValue("Gamma", "2007", new Integer(226));
//		model.setValue("Gamma", "2008", new Integer(186));
//		model.setValue("Gamma", "2009", new Integer(286));
//
//		model.setValue("Delta", "2006", new Integer(137));
//		model.setValue("Delta", "2007", new Integer(297));
//		model.setValue("Delta", "2008", new Integer(307));
//		model.setValue("Delta", "2009", new Integer(317));
		return model;
	}
}