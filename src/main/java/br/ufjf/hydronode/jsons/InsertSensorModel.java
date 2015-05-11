package br.ufjf.hydronode.jsons;

public class InsertSensorModel {
	String request;
	String service;
	String version;
	String procedureDescriptionFormat;
	String procedureDescription;
	String observableProperty;
	String observationType;
	String featureOfInterestType;

	public InsertSensorModel() {
		this.request = "InsertSensor";
		this.service = "SOS";
		this.version = "2.0.0";
		this.procedureDescriptionFormat = "http://www.opengis.net/sensorML/1.0.1";
		this.observationType = "http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_Measurement";
		this.featureOfInterestType = "http://www.opengis.net/def/samplingFeatureType/OGC-OM/2.0/SF_SamplingPoint";

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

	public String getProcedureDescriptionFormat() {
		return procedureDescriptionFormat;
	}

	public void setProcedureDescriptionFormat(String procedureDescriptionFormat) {
		this.procedureDescriptionFormat = procedureDescriptionFormat;
	}

	public String getProcedureDescription() {
		return procedureDescription;
	}

	public void setProcedureDescription(String procedureDescription) {
		this.procedureDescription = procedureDescription;
	}

	public String getObservableProperty() {
		return observableProperty;
	}

	public void setObservableProperty(String observableProperty) {
		this.observableProperty = observableProperty;
	}

	public String getObservationType() {
		return observationType;
	}

	public void setObservationType(String observationType) {
		this.observationType = observationType;
	}

	public String getFeatureOfInterestType() {
		return featureOfInterestType;
	}

	public void setFeatureOfInterestType(String featureOfInterestType) {
		this.featureOfInterestType = featureOfInterestType;
	}

}
