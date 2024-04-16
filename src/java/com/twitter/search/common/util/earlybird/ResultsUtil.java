package com.tw ter.search.common.ut l.earlyb rd;

 mport java.ut l.Map;

 mport com.google.common.base.Funct on;
 mport com.google.common.collect. erables;
 mport com.google.common.collect.Maps;

/**
 * Ut l y class used to  lp  rg ng results.
 */
publ c f nal class ResultsUt l {
  pr vate ResultsUt l() { }

  /**
   * Aggregate a l st of responses  n t  follow ng way.
   * 1. For each response, mapGetter can turn t  response  nto a map.
   * 2. Dump all entr es from t  above map  nto a "total" map, wh ch accumulates entr es from
   *    all t  responses.
   */
  publ c stat c <T, V> Map<T,  nteger> aggregateCountMap(
           erable<V> responses,
          Funct on<V, Map<T,  nteger>> mapGetter) {
    Map<T,  nteger> total = Maps.newHashMap();
    for (Map<T,  nteger> map :  erables.transform(responses, mapGetter)) {
       f (map != null) {
        for (Map.Entry<T,  nteger> entry : map.entrySet()) {
          T key = entry.getKey();
          total.put(key, total.conta nsKey(key)
              ? total.get(key) + entry.getValue() : entry.getValue());
        }
      }
    }
    return total;
  }
}
