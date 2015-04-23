package br.ufjf.hydronode.jsons.observationModel;

public class Result {

	String uom;
	Double value;

	public Result(String uom, Double value) {
		super();
		this.uom = uom;
		this.value = value;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}
