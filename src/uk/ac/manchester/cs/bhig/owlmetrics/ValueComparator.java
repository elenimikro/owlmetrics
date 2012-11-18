package uk.ac.manchester.cs.bhig.owlmetrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObject;

public class ValueComparator {

	public static LinkedHashMap<OWLEntity, Integer> sortOWLEntityMapByValues(HashMap<OWLEntity, Integer> passedMap) {
		List<OWLEntity> mapKeys = new ArrayList<OWLEntity>(passedMap.keySet());
		List<Integer> mapValues = new ArrayList<Integer>(passedMap.values());

		Collections.sort(mapValues);
		Collections.reverse(mapValues);
		Collections.sort(mapKeys);

		LinkedHashMap<OWLEntity, Integer> sortedMap = new LinkedHashMap<OWLEntity, Integer>();

		Iterator<Integer> valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			Object val = valueIt.next();
			Iterator<OWLEntity> keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				Object key = keyIt.next();
				String comp1 = passedMap.get(key).toString();
				String comp2 = val.toString();

				if (comp1.equals(comp2)) {
					passedMap.remove(key);
					mapKeys.remove(key);
					sortedMap.put((OWLEntity) key, (Integer) val);
					break;
				}

			}

		}
		return sortedMap;
	}

	public static LinkedHashMap<OWLObject, Integer> sortHashMapByValues(HashMap<? extends OWLObject, Integer> passedMap) {
		List<? extends OWLObject> mapKeys = new ArrayList<OWLObject>(passedMap.keySet());
		List<Integer> mapValues = new ArrayList<Integer>(passedMap.values());

		Collections.sort(mapValues);
		Collections.reverse(mapValues);
		Collections.sort(mapKeys);

		LinkedHashMap<OWLObject, Integer> sortedMap = new LinkedHashMap<OWLObject, Integer>();

		Iterator<Integer> valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			Object val = valueIt.next();
			Iterator<OWLObject> keyIt = (Iterator<OWLObject>) mapKeys.iterator();

			while (keyIt.hasNext()) {
				Object key = keyIt.next();
				String comp1 = passedMap.get(key).toString();
				String comp2 = val.toString();

				if (comp1.equals(comp2)) {
					passedMap.remove(key);
					mapKeys.remove(key);
					sortedMap.put((OWLObject) key, (Integer) val);
					break;
				}

			}

		}
		return sortedMap;
	}
}
