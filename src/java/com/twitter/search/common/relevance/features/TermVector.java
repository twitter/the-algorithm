package com.tw ter.search.common.relevance.features;

 mport java.ut l.Map;

 mport com.google.common.collect. mmutableMap;
 mport com.google.common.collect.Maps;

 mport com.tw ter.common.base.Funct on;

/**
 * Class to keep Str ng-Double of term vectors
 *   can calculate magn ude, dot product, and cos ne s m lar y
 */
publ c class TermVector {
  pr vate stat c f nal double M N_MAGN TUDE = 0.00001;
  pr vate f nal double magn ude;
  pr vate f nal  mmutableMap<Str ng, Double> term  ghts;

  /** Creates a new TermVector  nstance. */
  publ c TermVector(Map<Str ng, Double> term  ghts) {
    t .term  ghts =  mmutableMap.copyOf(term  ghts);
    double sum = 0.0;
    for (Map.Entry<Str ng, Double> entry : term  ghts.entrySet()) {
      double value = entry.getValue();
      sum += value * value;
    }
    magn ude = Math.sqrt(sum);
  }

  publ c  mmutableMap<Str ng, Double> getTerm  ghts() {
    return term  ghts;
  }

  publ c double getMagn ude() {
    return magn ude;
  }

  /**
   * Normal ze term vector  nto un  magn ude
   * @return           t  un  normal zed TermVector w h magn ude equals 1
   *                   return null  f magn ude  s very low
   */
  publ c TermVector getUn Normal zed() {
     f (magn ude < M N_MAGN TUDE) {
      return null;
    }
    return new TermVector(
        Maps.transformValues(term  ghts, (Funct on<Double, Double>)   ght ->   ght / magn ude));
  }

  /**
   * Calculate t  dot product w h anot r term vector
   * @param ot r      t  ot r term vector
   * @return           t  dot product of t  two vectors
   */
  publ c double getDotProduct(TermVector ot r) {
    double sum = 0.0;
    for (Map.Entry<Str ng, Double> entry : term  ghts.entrySet()) {
      Double value2 = ot r.term  ghts.get(entry.getKey());
       f (value2 != null) {
        sum += entry.getValue() * value2;
      }
    }
    return sum;
  }

  /**
   * Calculate t  cos ne s m lar y of w h anot r term vector
   * @param ot r     t  ot r term vector
   * @return          t  cos ne s m lar y.
   *                   f e  r has very small magn ude,   returns 0 (dotProduct close to 0)
   */
  publ c double getCos neS m lar y(TermVector ot r) {
     f (magn ude < M N_MAGN TUDE || ot r.magn ude < M N_MAGN TUDE) {
      return 0;
    }
    return getDotProduct(ot r) / (magn ude * ot r.magn ude);
  }
}
