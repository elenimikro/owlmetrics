package uk.ac.manchester.cs.bhig.owlmetrics.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class ValueComparator {

	// Map base;
	// public ValueComparator(Map base) {
	// this.base = base;
	// }
	//
	// public int compare(Object a, Object b) {
	//
	// if((Integer)base.get(a) < (Integer)base.get(b)) {
	// return 1;
	// } else if((Integer)base.get(a) == (Integer)base.get(b)) {
	// return 0;
	// } else {
	// return -1;
	// }
	// }

	public static LinkedHashMap<String, Integer> sortHashMapByValues(HashMap<String, Integer> passedMap) {
		List<String> mapKeys = new ArrayList<String>(passedMap.keySet());
		List<Integer> mapValues = new ArrayList<Integer>(passedMap.values());

		Collections.sort(mapValues);
		Collections.reverse(mapValues);
		Collections.sort(mapKeys);

		LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();

		Iterator<Integer> valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			Object val = valueIt.next();
			Iterator<String> keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				Object key = keyIt.next();
				String comp1 = passedMap.get(key).toString();
				String comp2 = val.toString();

				if (comp1.equals(comp2)) {
					passedMap.remove(key);
					mapKeys.remove(key);
					sortedMap.put((String) key, (Integer) val);
					break;
				}

			}

		}
		return sortedMap;
	}
}
