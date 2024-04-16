package com.tw ter.search.common.ut l.ml;

 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Opt onal;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.Sets;

/**
 * Ut l  es for feature transformat on and extract on.
 */
publ c f nal class FeatureUt ls {

  pr vate FeatureUt ls() {
  }

  /**
   * Computes t  d fference bet en 2 values and returns t  rat o of t  d fference over t 
   * m n mum of both, accord ng to t se cases:
   *
   * 1.  f (a > b) return  a / b
   * 2.  f (a < b) return  - b / a
   * 3.  f (a == b == 0) return 0
   *
   * T  upper/lo r l m   s (-) maxRat o. For cases 1 and 2,  f t  denom nator  s 0,
   *   returns maxRat o.
   *
   * T   thod  s used to def ne a feature that tells how much larger or smaller  s t 
   * f rst value w h respect to t  second one..
   */
  publ c stat c float d ffRat o(float a, float b, float maxRat o) {
    float d ff = a - b;
     f (d ff == 0) {
      return 0;
    }
    float denom nator = Math.m n(a, b);
    float rat o = denom nator != 0 ? Math.abs(d ff / denom nator) : maxRat o;
    return Math.copyS gn(Math.m n(rat o, maxRat o), d ff);
  }

  /**
   * Computes t  cos ne s m lar y bet en two maps that represent sparse vectors.
   */
  publ c stat c <K, V extends Number> double cos neS m lar y(
      Map<K, V> vector1, Map<K, V> vector2) {
     f (vector1 == null || vector1. sEmpty() || vector2 == null || vector2. sEmpty()) {
      return 0;
    }
    double squaredSum1 = 0;
    double squaredSum2 = 0;
    double squaredCrossSum = 0;

    for (K key : Sets.un on(vector1.keySet(), vector2.keySet())) {
      double value1 = 0;
      double value2 = 0;

      V optValue1 = vector1.get(key);
       f (optValue1 != null) {
        value1 = optValue1.doubleValue();
      }
      V optValue2 = vector2.get(key);
       f (optValue2 != null) {
        value2 = optValue2.doubleValue();
      }

      squaredSum1 += value1 * value1;
      squaredSum2 += value2 * value2;
      squaredCrossSum += value1 * value2;
    }

     f (squaredSum1 == 0 || squaredSum2 == 0) {
      return 0;
    } else {
      return squaredCrossSum / Math.sqrt(squaredSum1 * squaredSum2);
    }
  }

  /**
   * Computes t  cos ne s m lar y bet en two (dense) vectors.
   */
  publ c stat c <V extends Number> double cos neS m lar y(
      L st<V> vector1, L st<V> vector2) {
     f (vector1 == null || vector1. sEmpty() || vector2 == null || vector2. sEmpty()) {
      return 0;
    }

    Precond  ons.c ckArgu nt(vector1.s ze() == vector2.s ze());
    double squaredSum1 = 0;
    double squaredSum2 = 0;
    double squaredCrossSum = 0;
    for ( nt   = 0;   < vector1.s ze();  ++) {
      double value1 = vector1.get( ).doubleValue();
      double value2 = vector2.get( ).doubleValue();
      squaredSum1 += value1 * value1;
      squaredSum2 += value2 * value2;
      squaredCrossSum += value1 * value2;
    }

     f (squaredSum1 == 0 || squaredSum2 == 0) {
      return 0;
    } else {
      return squaredCrossSum / Math.sqrt(squaredSum1 * squaredSum2);
    }
  }

  /**
   * F nds t  key of t  map w h t  h g st value (compared  n natural order)
   */
  @SuppressWarn ngs("unc cked")
  publ c stat c <K, V extends Comparable> Opt onal<K> f ndMaxKey(Map<K, V> map) {
     f (map == null || map. sEmpty()) {
      return Opt onal.empty();
    }

    Opt onal<Map.Entry<K, V>> maxEntry = map.entrySet().stream().max(Map.Entry.compar ngByValue());
    return maxEntry.map(Map.Entry::getKey);
  }

}
