package br.ufjf.hydronode.jsons;

import br.ufjf.hydronode.jsons.observationModel.Observation;

public class InsertObservationModel {
	String request;
	String service;
	String version;
	String offering;
	Observation observation;

	public InsertObservationModel() {
		this.request = "InsertObservation";
		this.service = "SOS";
		this.version = "2.0.0";

	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOffering() {
		return offering;
	}

	public void setOffering(String offering) {
		this.offering = offering;
	}

	public Observation getObservation() {
		return observation;
	}

	public void setObservation(Observation observation) {
		this.observation = observation;
	}

}
