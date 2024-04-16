package com.tw ter.search.common.ut l.ml.pred ct on_eng ne;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.ml.ap .Feature;

/**
 * Score accumulator for legacy (non-sc ma-based) features.   prov des  thods to add features
 * us ng Feature objects.
 *
 * @deprecated T  class  s ret red and   suggest to sw ch to sc ma-based features.
 */
@Deprecated
publ c abstract class BaseLegacyScoreAccumulator<D> extends BaseScoreAccumulator<D> {

  publ c BaseLegacyScoreAccumulator(L ght  ghtL nearModel model) {
    super(model);
    Precond  ons.c ckState(!model. sSc maBased(),
        "Cannot create LegacyScoreAccumulator w h a sc ma-based model: %s", model.getNa ());
  }

  /**
   * Add to t  score t    ght of a b nary feature ( f  's present).
   *
   * @deprecated T  funct on  s ret red and   suggest to sw ch to addSc maBooleanFeatures  n
   * Sc maBasedScoreAccumulator.
   */
  @Deprecated
  protected BaseLegacyScoreAccumulator addB naryFeature(Feature<Boolean> feature,
                                                        boolean value) {
     f (value) {
      Double   ght = model.b naryFeatures.get(feature);
       f (  ght != null) {
        score +=   ght;
      }
    }
    return t ;
  }

  /**
   * Add to t  score t    ght of a cont nuous feature.
   * <p>
   *  f t  model uses real valued features,   mult pl es  s   ght by t  prov ded value.
   * Ot rw se,   tr es to f nd t  d scret zed feature and adds  s   ght to t  score.
   *
   * @deprecated T  funct on  s ret red and   suggest to sw ch to addSc maCont nuousFeatures  n
   * Sc maBasedScoreAccumulator.
   */
  @Deprecated
  protected BaseLegacyScoreAccumulator addCont nuousFeature(Feature<Double> feature,
                                                            double value) {
    Double   ghtFromCont nuous = model.cont nuousFeatures.get(feature);
     f (  ghtFromCont nuous != null) {
      score +=   ghtFromCont nuous * value;
    } else {
      D scret zedFeature d scret zedFeature = model.d scret zedFeatures.get(feature);
       f (d scret zedFeature != null) {
        // Use only t    ght of t  d scret zed feature (t re's no need to mult ply  )
        score += d scret zedFeature.get  ght(value);
      }
    }
    return t ;
  }
}
