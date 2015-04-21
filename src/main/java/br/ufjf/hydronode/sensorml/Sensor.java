package br.ufjf.hydronode.sensorml;

/**
 * Classe temporaria para testar outras funcionalidades
 * 
 * @author Lucas
 * 
 */
public class Sensor {

	public static String getSML(String identificador, String longName,
			String oferta, String localizacao,
			String saidaOntologia, String saidaDescricao, String saidaUOM) {

		return "<sml:SensorML xmlns:oost=\"http://www.oostethys.org/schemas/0.1.0/oostethys\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:sml=\"http://www.opengis.net/sensorML/1.0.1\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:gml=\"http://www.opengis.net/gml\" xmlns:swe=\"http://www.opengis.net/swe/1.0.1\" xsi:schemaLocation=\"http://www.opengis.net/sensorML/1.0.1 http://schemas.opengis.net/sensorML/1.0.1/sensorML.xsd\" version=\"1.0.1\"> "
				+ "<sml:member xlink:href=\""+identificador+"\"> <sml:System> <sml:identification> <sml:IdentifierList> <sml:identifier name=\"Long Name\"> <sml:Term definition=\"http://mmisw.org/mmi/20080520/obs.owl#longName\"> "
				+ "<sml:value>"+longName+"</sml:value> </sml:Term> </sml:identifier> <sml:identifier name=\"Identifier\"> <sml:Term definition=\"http://mmisw.org/mmi/20080520/obs.owl#identifier\"> "
				+ "<sml:value>"+identificador+"</sml:value> </sml:Term> </sml:identifier> </sml:IdentifierList> </sml:identification> <sml:capabilities name=\"offerings\"> <swe:SimpleDataRecord> "
				+ "<swe:field name=\"Oferta do sensor "+identificador+"\"> <swe:Text definition=\"urn:ogc:def:identifier:OGC:offeringID\"> "
				+ "<swe:value>"+oferta+"</swe:value> </swe:Text> </swe:field> </swe:SimpleDataRecord> </sml:capabilities> <sml:location> <gml:Point srsName=\"http://www.opengis.net/def/crs/EPSG/0/4326\"> "
				+ "<gml:coordinates>"+localizacao+"</gml:coordinates> </gml:Point> </sml:location> <sml:outputs> <sml:OutputList> <sml:output name=\"pointDataRecord\"> <swe:DataArray> <swe:elementCount/> <swe:elementType name=\"SimpleDataArray\"> <swe:DataRecord> "
				+ "<swe:field name=\""+saidaDescricao+"\"> "
				+ "<swe:Quantity definition=\""+saidaOntologia+"\"> "
				+ "<swe:uom code=\""+saidaUOM+"\"/> </swe:Quantity> </swe:field> </swe:DataRecord> </swe:elementType> </swe:DataArray> </sml:output> </sml:OutputList> </sml:outputs> </sml:System> </sml:member> </sml:SensorML>";
	}

}
