package br.ufjf.hydronode.jsons;

import org.zkoss.gmaps.Gmaps;

public class ZkUtils {

	/**
	 * Ajusta o zoom e centraliza o mapa. Fonte:
	 * http://forum.zkoss.org/question/
	 * 53822/google-maps-set-zoom-based-on-markers/
	 * 
	 * @param mapDisplay
	 *            Minimum of height or width of map in pixels
	 */
	public static void scaleMap(Gmaps map, int mapDisplay, Double minLat,
			Double maxLat, Double minLng, Double maxLng) {

		// Double minLat = null;
		// Double maxLat = null;
		// Double minLng = null;
		// Double maxLng = null;
		//
		// // Work out the minimum and maximmum latitude and longitude
		// for (Object o : map.getChildren()) {
		// if (o instanceof Gmarker) {
		// Gmarker g = (Gmarker) o;
		//
		// if ((minLat == null) || (g.getLat() < minLat)) {
		// minLat = g.getLat();
		// }
		//
		// if ((maxLat == null) || (g.getLat() > maxLat)) {
		// maxLat = g.getLat();
		// }
		//
		// if ((minLng == null) || (g.getLng() < minLng)) {
		// minLng = g.getLng();
		// }
		//
		// if ((maxLng == null) || (g.getLng() > maxLng)) {
		// maxLng = g.getLng();
		// }
		// }
		// }

		// No markers found, so don't bother scaling
		if (minLat == null) {
			return;
		}

		// Calculate the centre Lat and Long
		Double ctrLng = (maxLng + minLng) / 2;
		Double ctrLat = (maxLat + minLat) / 2;

		// The next calculation is sourced here
		// http://aiskahendra.blogspot.com/2009/01/set-zoom-level-of-google-map-base-on.html
		// I have no idea what it's actually doing !!!
		Double interval = 0.0;

		// Some sort of tweak !
		if ((maxLat - minLat) > (maxLng - minLng)) {
			interval = (maxLat - minLat) / 2;
			minLng = ctrLng - interval;
			maxLng = ctrLng + interval;
		} else {
			interval = (maxLng - minLng) / 2;
			minLat = ctrLat - interval;
			maxLat = ctrLat + interval;
		}

		Double dist = (6371 * Math
				.acos(Math.sin(minLat / 57.2958)
						* Math.sin(maxLat / 57.2958)
						+ (Math.cos(minLat / 57.2958)
								* Math.cos(maxLat / 57.2958) * Math
									.cos((maxLng / 57.2958)
											- (minLng / 57.2958)))));

		// Note ... original calc used 8, but I found it worked better with 7
		Double zoom = Math.floor(7
				- Math.log(1.6446 * dist
						/ Math.sqrt(2 * (mapDisplay * mapDisplay)))
				/ Math.log(2));

		// Centre the map
		map.setCenter(ctrLat, ctrLng);

		// Set appropriate zoom
		map.setZoom(zoom.intValue());
	}

}
