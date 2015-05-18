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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.ufjf.hydronode.Config;

public class HttpUtils {

	private static Logger log = LoggerFactory.getLogger(HttpUtils.class);
	private final static String postURL = Config.urlSOS;

	public static String enviaRequisicaoJSON(String json) {

		StringEntity postingString;
		try {
			postingString = new StringEntity(json);
		} catch (UnsupportedEncodingException e) {
			log.error("Erro ao montar requisicao json:\n{}", e);
			return null;
		}

		HttpPost post = new HttpPost(postURL);
		post.setEntity(postingString);
		post.setHeader("content-type", "application/json");

		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response;
		try {
			response = httpClient.execute(post);
		} catch (IOException e) {
			httpClient.getConnectionManager().shutdown();
			log.error("Erro ao enviar post com json:\n{}", e);
			return null;
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
				log.error("IOException - Erro ao recever resposta json:\n{}", e);
				return null;
			} catch (RuntimeException ex) {
				// In case of an unexpected exception you may want to abort
				// the HTTP request in order to shut down the underlying
				// connection and release it back to the connection manager.
				post.abort();
				httpClient.getConnectionManager().shutdown();
				if (responseString == null) {
					responseString = "";
				}
				log.error(
						"RuntimeException - Erro ao recever resposta json:\n{}\nResposta:\n{}",
						ex, responseString);
				return null;
			}

			httpClient.getConnectionManager().shutdown();
			return responseString;
		} else {
			log.error("Erro entity == null (HttpUtils)");
			return null;
		}

	}

}
