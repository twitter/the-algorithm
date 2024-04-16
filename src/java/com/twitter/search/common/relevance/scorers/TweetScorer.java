package com.tw ter.search.common.relevance.scorers;

 mport com.tw ter.search.common.relevance.class f ers.T etClass f er;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssage;

/**
 *  nterface to compute feature scores for a s ngle @Tw ter ssage
 * object, or a group of t m, after t y have been processed by
 * feature class f ers.
 *
 *  ntent onally kept Scorers separate from Class f ers, s nce t y
 * may be run at d fferent stages and  n d fferent batch ng manners.
 * Conven ence  thods are prov ded to run class f cat on and scor ng
 *  n one call.
 */
publ c abstract class T etScorer {
  /**
   * Compute and store feature score  n Tw ter ssage based on  s
   * T etFeatures.
   *
   * @param t et t et  ssage to compute and store score to.
   */
  publ c abstract vo d scoreT et(f nal Tw ter ssage t et);

  /**
   * Score a group of Tw ter ssages based on t  r correspond ng T etFeatures
   * and store feature scores  n Tw ter ssages.
   *
   * T  default  mple ntat on just  erates through t  map and scores each
   *  nd v dual t et. Batch ng for better performance,  f appl cable, can be  mple nted by
   * concrete subclasses.
   *
   * @param t ets Tw ter ssages to score.
   */
  publ c vo d scoreT ets( erable<Tw ter ssage> t ets) {
    for (Tw ter ssage t et: t ets) {
      scoreT et(t et);
    }
  }

  /**
   * Conven ence  thod.
   * Class fy t et us ng t  spec f ed l st of class f ers, t n compute score.
   *
   * @param class f er l st of class f ers to use for class f cat on.
   * @param t et t et to class fy and score
   */
  publ c vo d class fyAndScoreT et(T etClass f er class f er, Tw ter ssage t et) {
    class f er.class fyT et(t et);
    scoreT et(t et);
  }

  /**
   * Conven ence  thod.
   * Class fy t ets us ng t  spec f ed l st of class f ers, t n compute score.
   *
   * @param class f er class f er to use for class f cat on.
   * @param t ets t ets to class fy and score
   */
  publ c vo d class fyAndScoreT ets(T etClass f er class f er,  erable<Tw ter ssage> t ets) {
    for (Tw ter ssage t et: t ets) {
      class fyAndScoreT et(class f er, t et);
    }
  }
}
