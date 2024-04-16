package com.tw ter.search.common.ut l.ml.pred ct on_eng ne;

/**
 * T  base class for a l ght  ght scorer based on a model and so  feature data.
 *
 * @param <D> T  type of feature data to be scored w h
 */
publ c abstract class BaseScoreAccumulator<D> {
  protected f nal L ght  ghtL nearModel model;
  protected double score;

  publ c BaseScoreAccumulator(L ght  ghtL nearModel model) {
    t .model = model;
    t .score = model.b as;
  }

  /**
   * Compute score w h a model and feature data
   */
  publ c f nal double scoreW h(D featureData, boolean useLog Score) {
    updateScoreW hFeatures(featureData);
    return useLog Score ? getLog Score() : getS gmo dScore();
  }

  publ c f nal vo d reset() {
    t .score = model.b as;
  }

  /**
   * Update t  accumulator score w h features, after t  funct on t  score should already
   * be computed.
   */
  protected abstract vo d updateScoreW hFeatures(D data);

  /**
   * Get t  already accumulated score
   */
  protected f nal double getLog Score() {
    return score;
  }

  /**
   * Returns t  score as a value mapped bet en 0 and 1.
   */
  protected f nal double getS gmo dScore() {
    return 1 / (1 + Math.exp(-score));
  }
}
