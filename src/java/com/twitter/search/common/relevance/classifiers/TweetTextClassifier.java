package com.tw ter.search.common.relevance.class f ers;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.L sts;

 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er;
 mport java.ut l.L st;

 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on;
 mport com.tw ter.search.common.relevance.conf g.T etProcess ngConf g;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssage;

/**
 * Class f er that focuses on t et text features and t  r correspond ng
 * qual y.
 */
publ c class T etTextClass f er extends T etClass f er {
  pr vate T etQual yFeatureExtractor featureExtractor = new T etQual yFeatureExtractor();
  pr vate T etTrendsExtractor trendsExtractor = null;

  /**
   * Constructor. Requ res a l st of T etQual yEvaluator objects.
   * @param evaluators l st of T etQual yEvaluator objects respons ble for qual y evaluat on.
   * @param serv ce dent f er T   dent f er of t  call ng serv ce.
   * @param supportedPengu nVers ons A l st of supported pengu n vers ons.
   */
  publ c T etTextClass f er(
      f nal  erable<T etEvaluator> evaluators,
      Serv ce dent f er serv ce dent f er,
      L st<Pengu nVers on> supportedPengu nVers ons) {
    Precond  ons.c ckNotNull(evaluators);
    setQual yEvaluators(evaluators);
    T etProcess ngConf g. n ();

     f (T etProcess ngConf g.getBool("extract_trends", false)) {
      trendsExtractor = new T etTrendsExtractor(serv ce dent f er, supportedPengu nVers ons);
    }
  }

  /**
   * Extract text features for t  spec f ed Tw ter ssage.
   *
   * @param t et Tw ter ssage to extract features from.
   */
  @Overr de
  protected vo d extractFeatures(Tw ter ssage t et) {
    extractFeatures(L sts.newArrayL st(t et));
  }

  /**
   * Extract text features for t  spec f ed l st of Tw ter ssages.
   *
   * @param t ets l st of Tw ter ssages to extract  nterest ng features for
   */
  @Overr de
  protected vo d extractFeatures( erable<Tw ter ssage> t ets) {
    Precond  ons.c ckNotNull(t ets);
    for (Tw ter ssage t et : t ets) {
      featureExtractor.extractT etTextFeatures(t et);
    }

    // Opt onally try to annotate trends for all t  t ets.
     f (T etProcess ngConf g.getBool("extract_trends", false) && trendsExtractor != null) {
      trendsExtractor.extractTrends(t ets);
    }
  }
}
