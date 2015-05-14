package br.ufjf.hydronode.paginas.offering;

import java.util.ArrayList;

public class OfferingModel {
	private ArrayList<Content> contents = new ArrayList<Content>();

	public ArrayList<Content> getContents() {
		return contents;
	}

	public void setContents(ArrayList<Content> contents) {
		this.contents = contents;
	}

}
