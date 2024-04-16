package com.tw ter.search.common.ut l.ml.pred ct on_eng ne;

 mport java.ut l.Map;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.search.common.features.thr ft.Thr ftSearchResultFeatures;
 mport com.tw ter.search.model ng.common.T etFeaturesUt ls;

/**
 * Score accumulator for sc ma-based features.
 */
publ c class Sc maBasedScoreAccumulator extends BaseScoreAccumulator<Thr ftSearchResultFeatures> {

  publ c Sc maBasedScoreAccumulator(L ght  ghtL nearModel model) {
    super(model);
    Precond  ons.c ckState(model. sSc maBased(),
        "Cannot create Sc maBasedScoreAccumulator w h a non-sc ma-based model: %s",
        model.getNa ());
  }

  @Overr de
  protected f nal vo d updateScoreW hFeatures(Thr ftSearchResultFeatures featureData) {
    // go through all features ava lable and apply all those ava lable  n t  model
    addSc maBooleanFeatures(featureData.getBoolValues());
    addSc maCont nuousFeatures(featureData.get ntValues());
    addSc maCont nuousFeatures(featureData.getLongValues());
    addSc maCont nuousFeatures(featureData.getDoubleValues());
  }

  pr vate vo d addSc maBooleanFeatures(Map< nteger, Boolean> booleanMap) {
     f (booleanMap == null || booleanMap. sEmpty()) {
      return;
    }
    for (Map.Entry< nteger, Boolean> entry : booleanMap.entrySet()) {
       f (entry.getValue()) {
        score += model.b naryFeaturesBy d.getOrDefault(entry.getKey(), 0.0);
      }
    }
  }

  pr vate vo d addSc maCont nuousFeatures(Map< nteger, ? extends Number> valueMap) {
     f (valueMap == null || valueMap. sEmpty()) {
      return;
    }
    for (Map.Entry< nteger, ? extends Number> entry : valueMap.entrySet()) {
       nteger  d = entry.getKey();
       f (T etFeaturesUt ls. sFeatureD screte( d)) {
        cont nue;  //   don't process any d screte features now
      }
      Double   ght = model.cont nuousFeaturesBy d.get( d);
       f (  ght != null) {
        // found non-d scret zed entry
        score +=   ght * entry.getValue().doubleValue();
      } else {
        D scret zedFeature d scret zedFeature = model.d scret zedFeaturesBy d.get( d);
         f (d scret zedFeature != null) {
          // Use only t    ght of t  d scret zed feature (t re's no need to mult ply  )
          score += d scret zedFeature.get  ght(entry.getValue().doubleValue());
        }
      }
    }
  }
}
