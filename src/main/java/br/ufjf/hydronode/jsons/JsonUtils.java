package br.ufjf.hydronode.jsons;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import br.ufjf.hydronode.Config;

public class JsonUtils {

	private final static String postURL = Config.urlSOS;

	public static String enviaRequisicaoJSON(String json) {

		StringEntity postingString;
		try {
			postingString = new StringEntity(json);
		} catch (UnsupportedEncodingException e) {
			return "Erro ao montar requisicao json.\n" + e;
		}
		System.out.println("Tamanho do posting string: "
				+ postingString.getContentLength());

		System.out.println("Eh repetivel? " + postingString.isRepeatable());

		HttpPost post = new HttpPost(postURL);
		post.setEntity(postingString);
		post.setHeader("content-type", "application/json");

		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response;
		try {
			response = httpClient.execute(post);
		} catch (IOException e) {
			httpClient.getConnectionManager().shutdown();
			return "Erro ao enviar post com json.\n" + e;
		}

		HttpEntity entity = response.getEntity();
		// If the response does not enclose an entity, there is no need
		// to worry about connection release
		if (entity != null) {
			String responseString = null;
			try {
				responseString = new BasicResponseHandler()
						.handleResponse(response);
			} catch (IOException e) {
				httpClient.getConnectionManager().shutdown();
				return "IOException - Erro ao recever resposta json:\n" + e;
			} catch (RuntimeException ex) {
				// In case of an unexpected exception you may want to abort
				// the HTTP request in order to shut down the underlying
				// connection and release it back to the connection manager.
				post.abort();
				httpClient.getConnectionManager().shutdown();
				if (responseString == null) {
					responseString = "";
				}
				return "RuntimeException - Erro ao recever resposta json:\n" + ex + "Reposta: "
						+ responseString;
			}

			httpClient.getConnectionManager().shutdown();
			return "Enviado!\nResposta:\n" + responseString;
		} else {
			return "entity == null (JsonUtils)";
		}

	}
}
