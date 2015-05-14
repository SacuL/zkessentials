package br.ufjf.hydronode.paginas.offering;

import java.util.ArrayList;
import java.util.Map;

public class Content {
	private String identifier;
	private String name;
	private ArrayList<String> procedure = new ArrayList<String>();
	private ArrayList<String> observableProperty = new ArrayList<String>();
	// private ArrayList<RelatedFeature> relatedFeature;
	private Map<String, Object> observedArea;

	// private ArrayList<ObservedArea> observedArea = new
	// ArrayList<ObservedArea>();

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

	// public ArrayList<RelatedFeature> getRelatedFeature() {
	// return relatedFeature;
	// }

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProcedure(ArrayList<String> procedure) {
		this.procedure = procedure;
	}

	public void setObservableProperty(ArrayList<String> observableProperty) {
		this.observableProperty = observableProperty;
	}

	// public void setObservedArea(ArrayList<ObservedArea> observedArea) {
	// this.observedArea = observedArea;
	// }
	//
	// public ArrayList<ObservedArea> getObservedArea() {
	// return observedArea;
	// }
	public void setObservedArea(Map<String, Object> observedArea) {
		this.observedArea = observedArea;
	}

	public Map<String, Object> getObservedArea() {
		return observedArea;
	}

}
