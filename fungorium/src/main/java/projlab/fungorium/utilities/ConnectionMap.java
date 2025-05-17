package projlab.fungorium.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Segédosztály, a kétirányú kapcsolatok mentésére, és annak lekérésére, van-e
 * kapcsolat két pont között.
 * <p>
 * A bejegyzések Int-Int típusúak, ahol mindkét szám egy kapcsolati pont egyedi
 * ID-jét adja meg.
 * 
 * (GameObject-eknél: {@link models.GameObject#getID()})
 */
public class ConnectionMap {
	private Map<Integer, List<Integer>> connections;

	public ConnectionMap() {
		connections = new HashMap<>();
	}

	private boolean hasDirectedConnection(Integer a, Integer b) {
		return connections.containsKey(a) && connections.get(a).contains(b);
	}

	public boolean hasConnection(Integer a, Integer b) {
		return hasDirectedConnection(a, b) || hasDirectedConnection(b, a);
	}

	public void addConnection(Integer a, Integer b) {
		if (!connections.containsKey(a)) {
			connections.put(a, new ArrayList<>());
		}

		connections.get(a).add(b);
	}
}
