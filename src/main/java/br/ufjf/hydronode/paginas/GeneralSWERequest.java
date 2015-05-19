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

		// /offering/asdasdas
		if (!requestPath.substring(1).contains("/")) {
			// caminho invalido, redirecionar para outra pagina
		}
		String operacao = requestPath.substring(0, requestPath.substring(1)
				.indexOf("/") + 2);

		// mudar para uma pagina padrao (ou dar um foward?)
		String pagina = "";

		if (operacao.equalsIgnoreCase(Config.offering)) {
			pagina = "/proteger/offering.zul";
		} else if (operacao.equalsIgnoreCase(Config.procedure)) {
			pagina = "/proteger/procedure.zul";
		} else if (operacao.equalsIgnoreCase("observedProperty")) {
			pagina = "/proteger/observedproperty.zul";
		}

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