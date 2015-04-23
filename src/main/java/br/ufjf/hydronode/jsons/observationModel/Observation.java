	package br.ufjf.hydronode.jsons.observationModel;

public class Observation {

	Identifier identifier;
	String type;
	String procedure;
	String observedProperty;
	FeatureOfInterest featureOfInterest;
	String phenomenonTime;
	String resultTime;
	Result result;

	public Observation(Identifier identifier, String type, String procedure,
			String observedProperty, FeatureOfInterest featureOfInterest,
			String phenomenonTime, String resultTime, Result result) {
		super();
		this.identifier = identifier;
		this.type = type;
		this.procedure = procedure;
		this.observedProperty = observedProperty;
		this.featureOfInterest = featureOfInterest;
		this.phenomenonTime = phenomenonTime;
		this.resultTime = resultTime;
		this.result = result;
	}

	public Identifier getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProcedure() {
		return procedure;
	}

	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}

	public String getObservedProperty() {
		return observedProperty;
	}

	public void setObservedProperty(String observedProperty) {
		this.observedProperty = observedProperty;
	}

	public FeatureOfInterest getFeatureOfInterest() {
		return featureOfInterest;
	}

	public void setFeatureOfInterest(FeatureOfInterest featureOfInterest) {
		this.featureOfInterest = featureOfInterest;
	}

	public String getPhenomenonTime() {
		return phenomenonTime;
	}

	public void setPhenomenonTime(String phenomenonTime) {
		this.phenomenonTime = phenomenonTime;
	}

	public String getResultTime() {
		return resultTime;
	}

	public void setResultTime(String resultTime) {
		this.resultTime = resultTime;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

}
