package br.ufjf.hydronode.jsons.observationModel;

public class Geometry {

	String type;
	// VERIFICAR O FUNCIONAMENTO DISSO!!
	String coordinates;

	// Verificar se outros campos sao necessarios

	public Geometry(String type, String coordinates) {
		super();
		this.type = type;
		this.coordinates = coordinates;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

}
