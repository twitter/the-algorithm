package com.tw ter.search.common.ut l.ml.pred ct on_eng ne;

 mport java.ut l.Map;

 mport com.google.common.collect.HashMult map;
 mport com.google.common.collect.Maps;
 mport com.google.common.collect.Mult map;

 mport com.tw ter.ml.ap .Feature;
 mport com.tw ter.ml.ap .FeatureContext;
 mport com.tw ter.ml.ap .FeatureParser;
 mport com.tw ter.ml.ap .transform.D scret zerTransform;

/**
 * T  bu lder for a model based on t  legacy (non-sc ma-based) features.
 * See also Sc maBasedModelBu lder.
 */
publ c f nal class LegacyModelBu lder extends BaseModelBu lder {

  pr vate f nal Map<Str ng, Feature> featuresByNa ;
  // for legacy features
  pr vate f nal Map<Feature<Boolean>, Double> b naryFeatures;
  pr vate f nal Map<Feature<Double>, Double> cont nuousFeatures;
  pr vate f nal Mult map<Feature<Double>, D scret zedFeatureRange> d scret zedFeatureRanges;

  LegacyModelBu lder(Str ng modelNa , FeatureContext context) {
    super(modelNa );
    featuresByNa  = getFeaturesByNa (context);
    b naryFeatures = Maps.newHashMap();
    cont nuousFeatures = Maps.newHashMap();
    d scret zedFeatureRanges = HashMult map.create();
  }

  pr vate stat c Map<Str ng, Feature> getFeaturesByNa (FeatureContext featureContext) {
    Map<Str ng, Feature> featuresByNa  = Maps.newHashMap();
    for (Feature<?> feature : featureContext.getAllFeatures()) {
      featuresByNa .put(feature.getFeatureNa (), feature);
    }
    return featuresByNa ;
  }

  @Overr de
  protected vo d addFeature(Str ng baseNa , double   ght, FeatureParser parser) {
    Feature feature = featuresByNa .get(baseNa );
     f (feature != null) {
      sw ch (feature.getFeatureType()) {
        case B NARY:
          b naryFeatures.put(feature,   ght);
          break;
        case CONT NUOUS:
          cont nuousFeatures.put(feature,   ght);
          break;
        default:
          throw new  llegalArgu ntExcept on(
              Str ng.format("Unsupported feature type: %s", feature));
      }
    } else  f (baseNa .endsW h(D SCRET ZER_NAME_SUFF X)
        && parser.getExtens on().conta nsKey(D scret zerTransform.DEFAULT_RANGE_EXT)) {

      Str ng featureNa  =
          baseNa .substr ng(0, baseNa .length() - D SCRET ZER_NAME_SUFF X.length());

      feature = featuresByNa .get(featureNa );
       f (feature == null) {
        return;
      }

      Str ng rangeSpec = parser.getExtens on().get(D scret zerTransform.DEFAULT_RANGE_EXT);
      d scret zedFeatureRanges.put(feature, new D scret zedFeatureRange(  ght, rangeSpec));
    }
  }

  @Overr de
  publ c L ght  ghtL nearModel bu ld() {
    Map<Feature<Double>, D scret zedFeature> d scret zedFeatures = Maps.newHashMap();
    for (Feature<Double> feature : d scret zedFeatureRanges.keySet()) {
      D scret zedFeature d scret zedFeature =
          BaseModelBu lder.bu ldFeature(d scret zedFeatureRanges.get(feature));
       f (!d scret zedFeature.allValuesBelowThreshold(M N_WE GHT)) {
        d scret zedFeatures.put(feature, d scret zedFeature);
      }
    }
    return L ght  ghtL nearModel.createForLegacy(
        modelNa , b as, b naryFeatures, cont nuousFeatures, d scret zedFeatures);
  }
}
