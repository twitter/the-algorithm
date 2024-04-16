package com.tw ter.search.common.relevance.class f ers;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssage;

/**
 *  nterface to perform qual y evaluat on for a s ngle @Tw ter ssage
 * object or a group of t m.
 *
 */
publ c abstract class T etEvaluator {
  /**
   * Passed  n Tw ter ssage  s exam ned and any extractable
   * features are stored  n T etFeatures f eld of Tw ter ssage.
   *
   * @param t et Tw ter ssage to perform class f cat on on.
   */
  publ c abstract vo d evaluate(f nal Tw ter ssage t et);

  /**
   * Class fy a group of Tw ter ssages and store t  features  n t  r correspond ng
   * T etFeatures f elds.
   *
   * T  default  mple ntat on just  erates through t  map and class f es each
   *  nd v dual t et. Batch ng for better performance,  f appl cable, can be  mple nted by
   * concrete subclasses.
   *
   * @param t ets Tw ter ssages to perform class f cat on on.
   */
   publ c vo d evaluate(f nal  erable<Tw ter ssage> t ets) {
    Precond  ons.c ckNotNull(t ets);
    for (Tw ter ssage t et: t ets) {
      evaluate(t et);
    }
  }
}
