package br.ufjf.hydronode.jsons.observationModel;

public class Geometry {

	String type;
	// VERIFICAR O FUNCIONAMENTO DISSO!!
	double[] coordinates;

	// Verificar se outros campos sao necessarios

	public Geometry(String type, double[] coordinates) {
		super();
		this.type = type;
		if (coordinates.length != 2) {
			this.coordinates = new double[] { 0, 0 };
		} else {
			this.coordinates = coordinates;
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double[] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(double[] coordinates) {
		this.coordinates = coordinates;
	}

}
