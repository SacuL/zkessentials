package br.ufjf.hydronode.jsons.observationModel;

public class Result {

	String uom;
	String value;

	public Result(String uom, String value) {
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
