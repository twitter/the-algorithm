package com.tw ter.search.common.relevance.class f ers;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssage;

/**
 *  nterface to perform feature class f cat on for a s ngle
 * @Tw ter ssage object, or a group of t m.
 *
 * Class f cat on  ncludes two steps: feature extract on, and
 * qual y evaluat on. Dur ng feature extract on, any  nterest ng
 * feature that  s dee d useful for subsequent qual y analys s
 *  s extracted from t  @Tw ter ssage object. Qual y evaluat on
 *  s t n done by a group of @T etEvaluator objects assoc ated
 * w h t  class f er, by us ng t  var ous features extracted  n t 
 * prev ous step.
 *
 * Feature extract on and qual y evaluat on results are stored  n
 * @T etFeatures f eld of t  @Tw ter ssage object, wh ch  s def ned
 *  n src/ma n/thr ft/class f er.thr ft.
 */
publ c abstract class T etClass f er {
  /**
   * A l st of T etQual yEvaluators wh ch are  nvoked after
   * feature extract on  s done.  f null, no qual y evaluat on
   *  s done.
   */
  protected  erable<T etEvaluator> qual yEvaluators = null;

  /**
   * Passed  n Tw ter ssage  s exam ned and any extractable
   * features are saved  n T etFeatures f eld of Tw ter ssage.
   * T n T etQual yEvaluators are appl ed to compute var ous
   * qual y values.
   *
   * @param t et Tw ter ssage to perform class f cat on on.
   */
  publ c vo d class fyT et(f nal Tw ter ssage t et) {
    Precond  ons.c ckNotNull(t et);

    // extract features
    extractFeatures(t et);

    // compute qual y
    evaluate(t et);
  }

  /**
   * Class fy a group of Tw ter ssages and store features  n t  r correspond ng
   * T etFeatures f elds.
   *
   * T  default  mple ntat on just  erates through t  map and class f es each
   *  nd v dual t et. Batch ng for better performance,  f appl cable, can be  mple nted by
   * concrete subclasses.
   *
   * @param t ets Tw ter ssages to perform class f cat on on.
   */
  publ c vo d class fyT ets(f nal  erable<Tw ter ssage> t ets) {
    extractFeatures(t ets);
    evaluate(t ets);
  }

  /**
   * Use t  spec f ed l st of T etQual yEvaluators for t  class f er.
   *
   * @param evaluators l st of T etQual yEvaluators to be used w h t  class f er.
   */
  protected vo d setQual yEvaluators(f nal  erable<T etEvaluator> qual yEvaluators) {
    Precond  ons.c ckNotNull(qual yEvaluators);
    t .qual yEvaluators = qual yEvaluators;
  }


  /**
   * Extract  nterest ng features from a s ngle Tw ter ssage for class f cat on.
   *
   * @param t et Tw ter ssage to extract  nterest ng features for
   */
  protected abstract vo d extractFeatures(f nal Tw ter ssage t et);

  /**
   * Extract  nterest ng features from a l st of Tw ter ssages for class f cat on.
   * @param t ets l st of Tw ter ssages to extract  nterest ng features for
   */
  protected vo d extractFeatures(f nal  erable<Tw ter ssage> t ets) {
    for (Tw ter ssage t et: t ets) {
      extractFeatures(t et);
    }
  }

  /**
   * G ven a Tw ter ssage wh ch already has  s features extracted,
   * perform qual y evaluat on.
   *
   * @param t et Tw ter ssage to perform qual y evaluat on for
   */
  protected vo d evaluate(f nal Tw ter ssage t et) {
     f (qual yEvaluators == null) {
      return;
    }
    for (T etEvaluator evaluator : qual yEvaluators) {
      evaluator.evaluate(t et);
    }
  }

  /**
   * G ven a l st of Tw ter ssages wh ch already have t  r features extracted,
   * perform qual y evaluat on.
   *
   * @param t ets l st of Tw ter ssages to perform qual y evaluat on for
   */
  protected vo d evaluate(f nal  erable<Tw ter ssage> t ets) {
    for (Tw ter ssage t et: t ets) {
      evaluate(t et);
    }
  }
}
