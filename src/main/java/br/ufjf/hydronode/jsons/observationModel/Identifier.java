package br.ufjf.hydronode.jsons.observationModel;

public class Identifier {

	String value;
	String codespace;

	public Identifier(String value, String codespace) {
		super();
		this.value = value;
		this.codespace = codespace;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCodespace() {
		return codespace;
	}

	public void setCodespace(String codespace) {
		this.codespace = codespace;
	}

}
