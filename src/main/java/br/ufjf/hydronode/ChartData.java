package br.ufjf.hydronode;

import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.SimpleCategoryModel;

public class ChartData {

	public static CategoryModel getModel() {
		SimpleCategoryModel model = new SimpleCategoryModel();
		model.setValue("Alpha", "2006", new Integer(100));
		model.setValue("Alpha", "2007", new Integer(143));
		model.setValue("Alpha", "2008", new Integer(223));
		model.setValue("Alpha", "2009", new Integer(378));

		model.setValue("Beta", "2006", new Integer(174));
		model.setValue("Beta", "2007", new Integer(244));
		model.setValue("Beta", "2008", new Integer(124));
		model.setValue("Beta", "2009", new Integer(174));

		model.setValue("Gamma", "2006", new Integer(156));
		model.setValue("Gamma", "2007", new Integer(226));
		model.setValue("Gamma", "2008", new Integer(186));
		model.setValue("Gamma", "2009", new Integer(286));

		model.setValue("Delta", "2006", new Integer(137));
		model.setValue("Delta", "2007", new Integer(297));
		model.setValue("Delta", "2008", new Integer(307));
		model.setValue("Delta", "2009", new Integer(317));
		return model;
	}
}