package br.ufjf.hydronode.paginas.offering;

import java.util.ArrayList;

public class Content {
	private String identifier;
	private String name;
	private ArrayList<String> procedure;
	private ArrayList<String> observableProperty;

	public String getIdentifier() {
		return identifier;
	}

	public String getName() {
		return name;
	}

	public ArrayList<String> getProcedure() {
		return procedure;
	}

	public ArrayList<String> getObservableProperty() {
		return observableProperty;
	}

}
