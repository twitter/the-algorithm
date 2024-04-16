package com.tw ter.search.common.ut l.ml;

 mport java.ut l.Map;

/**
 * An  nterface for l near models that are backed by so  sort of map
 */
publ c  nterface MapBasedL nearModel<K> {
  /**
   * Evaluate us ng t  model g ven a feature vector.
   * @param  nstance T  feature vector  n format of a hashmap.
   * @return
   */
  boolean class fy(Map<K, Float>  nstance);

  /**
   * Evaluate us ng t  model g ven a class f cat on threshold and a feature vector.
   * @param threshold Score threshold used for class f cat on.
   * @param  nstance T  feature vector  n format of a hashmap.
   * @return
   */
  boolean class fy(float threshold, Map<K, Float>  nstance);

  /**
   * Computes t  score of an  nstance as a l near comb nat on of t  features and t  model
   *   ghts. 0  s used as default value for features or   ghts that are not present.
   *
   * @param  nstance T  feature vector  n format of a hashmap.
   * @return T   nstance score accord ng to t  model.
   */
  float score(Map<K, Float>  nstance);
}
