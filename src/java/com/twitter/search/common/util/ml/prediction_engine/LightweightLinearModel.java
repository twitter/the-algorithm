package com.tw ter.search.common.ut l.ml.pred ct on_eng ne;

 mport java. o.BufferedReader;
 mport java. o.F leReader;
 mport java. o. OExcept on;
 mport java.ut l.Map;
 mport javax.annotat on.Nullable;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.ml.ap .Feature;
 mport com.tw ter.search.common.f le.AbstractF le;

/**
 * Prov des an  nterface to t    ghts assoc ated to t  features of a l near model tra ned
 * w h Pred ct on Eng ne.
 *
 * T  class  s used along w h ScoreAccumulator to eff c ently score  nstances.   supports only
 * a l m ed set of features:
 *
 * - Only l near models are supported.
 * - Only b nary and cont nuous features ( .e.   doesn't support d screte/categor cal features).
 * -   supports t  MDL d scret zer (but not t  one based on trees).
 * -   doesn't support feature cross ngs.
 *
 *  nstances of t  class should be created us ng only t  load  thods (loadFromHdfs and
 * loadFromLocalF le).
 *
 *  MPORTANT:
 *
 * Use t  class, and ScoreAccumulator, ONLY w n runt    s a major concern. Ot rw se, cons der
 * us ng Pred ct on Eng ne as a l brary.  deally,   should access d rectly t  structures that
 * Pred ct on Eng ne creates w n   loads a model,  nstead of pars ng a text f le w h t 
 * feature   ghts.
 *
 * T  d scret zed feature b ns created by MDL may be too f ne to be d splayed properly  n t 
 * parsed text f le and t re may be b ns w h t  sa  m n value. A b nary search f nd ng t 
 * b n for a sa  feature value t refore may end up w h d fferent b ns/scores  n d fferent runs,
 * produc ng unstable scores. See SEARCHQUAL-15957 for more deta l.
 *
 * @see com.tw ter.ml.tool.pred ct on.Model nterpreter
 */
publ c class L ght  ghtL nearModel {
  protected f nal double b as;
  protected f nal boolean sc maBased;
  protected f nal Str ng na ;

  // for legacy  tadata based model
  protected f nal Map<Feature<Boolean>, Double> b naryFeatures;
  protected f nal Map<Feature<Double>, Double> cont nuousFeatures;
  protected f nal Map<Feature<Double>, D scret zedFeature> d scret zedFeatures;

  // for sc ma-based model
  protected f nal Map< nteger, Double> b naryFeaturesBy d;
  protected f nal Map< nteger, Double> cont nuousFeaturesBy d;
  protected f nal Map< nteger, D scret zedFeature> d scret zedFeaturesBy d;

  pr vate stat c f nal Str ng SCHEMA_BASED_SUFF X = ".sc ma_based";

  L ght  ghtL nearModel(
      Str ng modelNa ,
      double b as,
      boolean sc maBased,
      @Nullable Map<Feature<Boolean>, Double> b naryFeatures,
      @Nullable Map<Feature<Double>, Double> cont nuousFeatures,
      @Nullable Map<Feature<Double>, D scret zedFeature> d scret zedFeatures,
      @Nullable Map< nteger, Double> b naryFeaturesBy d,
      @Nullable Map< nteger, Double> cont nuousFeaturesBy d,
      @Nullable Map< nteger, D scret zedFeature> d scret zedFeaturesBy d) {

    t .na  = modelNa ;
    t .b as = b as;
    t .sc maBased = sc maBased;

    // legacy feature maps
    t .b naryFeatures =
        sc maBased ? null : Precond  ons.c ckNotNull(b naryFeatures);
    t .cont nuousFeatures =
        sc maBased ? null : Precond  ons.c ckNotNull(cont nuousFeatures);
    t .d scret zedFeatures =
        sc maBased ? null : Precond  ons.c ckNotNull(d scret zedFeatures);

    // sc ma based feature maps
    t .b naryFeaturesBy d =
        sc maBased ? Precond  ons.c ckNotNull(b naryFeaturesBy d) : null;
    t .cont nuousFeaturesBy d =
        sc maBased ? Precond  ons.c ckNotNull(cont nuousFeaturesBy d) : null;
    t .d scret zedFeaturesBy d =
        sc maBased ? Precond  ons.c ckNotNull(d scret zedFeaturesBy d) : null;
  }

  publ c Str ng getNa () {
    return na ;
  }

  /**
   * Create model for legacy features
   */
  protected stat c L ght  ghtL nearModel createForLegacy(
      Str ng modelNa ,
      double b as,
      Map<Feature<Boolean>, Double> b naryFeatures,
      Map<Feature<Double>, Double> cont nuousFeatures,
      Map<Feature<Double>, D scret zedFeature> d scret zedFeatures) {
    return new L ght  ghtL nearModel(modelNa , b as, false,
        b naryFeatures, cont nuousFeatures, d scret zedFeatures,
        null, null, null);
  }

  /**
   * Create model for sc ma-based features
   */
  protected stat c L ght  ghtL nearModel createForSc maBased(
      Str ng modelNa ,
      double b as,
      Map< nteger, Double> b naryFeaturesBy d,
      Map< nteger, Double> cont nuousFeaturesBy d,
      Map< nteger, D scret zedFeature> d scret zedFeaturesBy d) {
    return new L ght  ghtL nearModel(modelNa , b as, true,
        null, null, null,
        b naryFeaturesBy d, cont nuousFeaturesBy d, d scret zedFeaturesBy d);
  }

  publ c boolean  sSc maBased() {
    return sc maBased;
  }

  /**
   * Loads a model from a text f le.
   *
   * See t  javadoc of t  constructor for more deta ls on how to create t  f le from a tra ned
   * Pred ct on Eng ne model.
   *
   *  f sc maBased  s true, t  featureContext  s  gnored.
   */
  publ c stat c L ght  ghtL nearModel load(
      Str ng modelNa ,
      BufferedReader reader,
      boolean sc maBased,
      Compos eFeatureContext featureContext) throws  OExcept on {

    ModelBu lder bu lder = sc maBased
        ? new Sc maBasedModelBu lder(modelNa , featureContext.getFeatureSc ma())
        : new LegacyModelBu lder(modelNa , featureContext.getLegacyContext());
    Str ng l ne;
    wh le ((l ne = reader.readL ne()) != null) {
      bu lder.parseL ne(l ne);
    }
    return bu lder.bu ld();
  }

  /**
   * Loads a model from a local text f le.
   *
   * See t  javadoc of t  constructor for more deta ls on how to create t  f le from a tra ned
   * Pred ct on Eng ne model.
   */
  publ c stat c L ght  ghtL nearModel loadFromLocalF le(
      Str ng modelNa ,
      Compos eFeatureContext featureContext,
      Str ng f leNa ) throws  OExcept on {
    try (BufferedReader reader = new BufferedReader(new F leReader(f leNa ))) {
      boolean sc maBased = modelNa .endsW h(SCHEMA_BASED_SUFF X);
      return load(modelNa , reader, sc maBased, featureContext);
    }
  }

  /**
   * Loads a model from a f le  n t  local f lesystem or  n HDFS.
   *
   * See t  javadoc of t  constructor for more deta ls on how to create t  f le from a tra ned
   * Pred ct on Eng ne model.
   */
  publ c stat c L ght  ghtL nearModel load(
      Str ng modelNa , Compos eFeatureContext featureContext, AbstractF le modelF le)
      throws  OExcept on {
    try (BufferedReader reader = modelF le.getCharS ce().openBufferedStream()) {
      boolean sc maBased = modelNa .endsW h(SCHEMA_BASED_SUFF X);
      return load(modelNa , reader, sc maBased, featureContext);
    }
  }

  publ c Str ng toStr ng() {
    return Str ng.format("L ght  ghtL nearModel. {b as=%s b nary=%s cont nuous=%s d screte=%s}",
        t .b as, t .b naryFeatures, t .cont nuousFeatures, t .d scret zedFeatures);
  }
}
