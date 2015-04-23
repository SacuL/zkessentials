package br.ufjf.hydronode.jsons.observationModel;

public class FeatureOfInterest {

	Identifier identifier;
	Name name;
	Geometry geometry;

	public FeatureOfInterest(Identifier identifier, Name name, Geometry geometry) {
		super();
		this.identifier = identifier;
		this.name = name;
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

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

}
