package br.ufjf.hydronode.jsons.observationModel;

public class FeatureOfInterest {

	Identifier identifier;
	Name name;
	String sampledFeature;
	Geometry geometry;

	public FeatureOfInterest(Identifier identifier, Name name,
			String sampledFeature, Geometry geometry) {
		super();
		this.identifier = identifier;
		this.name = name;
		this.sampledFeature = sampledFeature;
		this.geometry = geometry;
	}

	public Identifier getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public String getSampledFeature() {
		return sampledFeature;
	}

	public void setSampledFeature(String sampledFeature) {
		this.sampledFeature = sampledFeature;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

}
