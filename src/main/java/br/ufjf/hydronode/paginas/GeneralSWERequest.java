package br.ufjf.hydronode.paginas;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.*;

import br.ufjf.hydronode.Config;

public class GeneralSWERequest extends GenericRichlet {

	static Logger log = LoggerFactory.getLogger(GeneralSWERequest.class);

	// Richlet
	public void service(Page page) {

		// Pega a parte da url que eh a requisicao ( /swe/... )
		// Remove '/swe'
		String requestPath = page.getRequestPath().substring(4);

		log.warn("RequestPath: {}", requestPath);

		Map<String, String> args = new HashMap<String, String>();
		args.put("url", Config.urlServidor + requestPath);

		if (!requestPath.substring(1).contains("/")) {
			// A pagina requisitada foi /swe/algoinvalido
			// caminho invalido, redirecionar para index da swe
			log.warn("Requisicao swe invalida");
			Executions.createComponents("", page.getFirstRoot(), args);
			return;
		}

		String operacao = requestPath.substring(0, requestPath.substring(1)
				.indexOf("/") + 2);

		boolean vazia = false;
		if (requestPath.replace(operacao + "index.zul", "").isEmpty()) {
			log.warn("Requisicao vazia");
			vazia = true;
		}

		// mudar para uma pagina padrao (ou dar um foward?)
		String pagina = "";

		if (operacao.equalsIgnoreCase(Config.offering)) {
			if (vazia) {
				pagina = "/sensor/";
			} else {
				pagina = "/proteger/offering.zul";
			}
		} else if (operacao.equalsIgnoreCase(Config.procedure)) {
			if (vazia) {
				pagina = "/estacao/";
			} else {
				pagina = "/proteger/procedure.zul";
			}
		} else if (operacao.equalsIgnoreCase("observedProperty")) {
			pagina = "/proteger/observedproperty.zul";
		} else {
			log.warn("requisicao contem apenas /swe/");
		}

		log.warn("Pagina: {}", pagina);
		// attach to page as root if parent is null
		Executions.createComponents(pagina, page.getFirstRoot(), args);

	}

	@Override
	public void init(RichletConfig config) {
		super.init(config);
		// initialize resources
	}

	@Override
	public void destroy() {
		super.destroy();
		// destroy resources
	}
}