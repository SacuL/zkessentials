package br.ufjf.hydronode;

public class Config {

	// Configurações para develop
	// public static final String urlSOS =
	// "http://localhost:8182/52n-sos-webapp/service";
	// public static final String urlServidor = "localhost:8080/essentials/swe";

	// Configurações para produção
	public static final String urlSOS = "http://localhost:8080/52n-sos-webapp/service";
	public static final String urlServidor = "200.131.55.60/swe";
	public static final String observation = "/leitura/";
	public static final String offering = "/sensor/";
	public static final String procedure = "/estacao/";
	public static final String featureOfInterest = "/caracteristica/";
	public static final String observableProperty = "/propriedadeobservada/";

	// inserir a key restringir para somente o ip do servidor
	public static final String google_public_api_key = "";

}
