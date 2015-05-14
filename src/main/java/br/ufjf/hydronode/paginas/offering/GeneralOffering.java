package br.ufjf.hydronode.paginas.offering;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.*;

import br.ufjf.hydronode.Config;

public class GeneralOffering extends GenericRichlet {

	static Logger log = LoggerFactory.getLogger(GeneralOffering.class);

	// Richlet
	public void service(Page page) {

		// Pega a parte da url que eh a requisicao ( /swe/... )
		// Remove '/swe'
		String requestPath = page.getRequestPath().substring(4);

		log.warn("RequestPath: {}", requestPath);

		Map<String, String> args = new HashMap<String, String>();
		args.put("url", Config.urlServidor + requestPath);

		// attach to page as root if parent is null
		Executions.createComponents("/proteger/oferta.zul",
				page.getFirstRoot(), args);

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