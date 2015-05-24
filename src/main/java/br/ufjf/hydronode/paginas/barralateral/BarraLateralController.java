package br.ufjf.hydronode.paginas.barralateral;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

public class BarraLateralController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	@Wire
	Grid gridBarraLateral;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		Rows rows = gridBarraLateral.getRows();

		rows.appendChild(constructSidebarRow("Sensores", "/imagens/sensor.png",
				"/swe/sensor/"));
		rows.appendChild(constructSidebarRow("Estações",
				"/imagens/estacao.png", "/swe/estacao/"));
		rows.appendChild(constructSidebarRow("Propriedades Observadas",
				"/imagens/propriedade.png", "/swe/sensor/"));
		rows.appendChild(constructSidebarRow("Características",
				"/imagens/caracteristica.png", "/swe/sensor/"));

	}

	private Row constructSidebarRow(String label, String imageSrc,
			final String locationUri) {

		// construct component and hierarchy
		Row row = new Row();
		Image image = new Image(imageSrc);
		image.setHeight("32px");
		image.setWidth("32px");
		Label lab = new Label(label);

		row.appendChild(image);
		row.appendChild(lab);

		// set style attribute
		row.setSclass("sidebar-fn");

		// create and register event listener
		EventListener<Event> actionListener = new SerializableEventListener<Event>() {
			private static final long serialVersionUID = 1L;

			public void onEvent(Event event) throws Exception {
				// redirect current url to new location
				Executions.getCurrent().sendRedirect(locationUri);
			}
		};

		row.addEventListener(Events.ON_CLICK, actionListener);

		return row;
	}
}
