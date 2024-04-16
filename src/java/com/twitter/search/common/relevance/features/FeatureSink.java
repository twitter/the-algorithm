package com.tw ter.search.common.relevance.features;

 mport java.ut l.Map;

 mport com.google.common.collect.Maps;

 mport com.tw ter.search.common.encod ng.features. ntegerEncodedFeatures;
 mport com.tw ter.search.common.sc ma.base.FeatureConf gurat on;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdEncodedFeatures;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;

/**
 * FeatureS nk  s used to wr e features based on feature conf gurat on or feature na .  After
 * all feature  s wr ten, t  class can return t  base f eld  nteger array values.
 *
 * T  class  s not thread-safe.
 */
publ c class FeatureS nk {
  pr vate  mmutableSc ma nterface sc ma;
  pr vate f nal Map<Str ng,  ntegerEncodedFeatures> encodedFeatureMap;

  /** Creates a new FeatureS nk  nstance. */
  publ c FeatureS nk( mmutableSc ma nterface sc ma) {
    t .sc ma = sc ma;
    t .encodedFeatureMap = Maps.newHashMap();
  }

  pr vate  ntegerEncodedFeatures getFeatures(Str ng baseF eldNa ) {
     ntegerEncodedFeatures features = encodedFeatureMap.get(baseF eldNa );
     f (features == null) {
      features = Earlyb rdEncodedFeatures.newEncodedT etFeatures(sc ma, baseF eldNa );
      encodedFeatureMap.put(baseF eldNa , features);
    }
    return features;
  }

  /** Sets t  g ven nu r c value for t  f eld. */
  publ c FeatureS nk setNu r cValue(Earlyb rdF eldConstant f eld,  nt value) {
    return setNu r cValue(f eld.getF eldNa (), value);
  }

  /** Sets t  g ven nu r c value for t  feature w h t  g ven na . */
  publ c FeatureS nk setNu r cValue(Str ng featureNa ,  nt value) {
    f nal FeatureConf gurat on featureConf g = sc ma.getFeatureConf gurat onByNa (featureNa );
     f (featureConf g != null) {
      getFeatures(featureConf g.getBaseF eld()).setFeatureValue(featureConf g, value);
    }
    return t ;
  }

  /** Sets t  g ven boolean value for t  g ven f eld. */
  publ c FeatureS nk setBooleanValue(Earlyb rdF eldConstant f eld, boolean value) {
    return setBooleanValue(f eld.getF eldNa (), value);
  }

  /** Sets t  g ven boolean value for t  feature w h t  g ven na . */
  publ c FeatureS nk setBooleanValue(Str ng featureNa , boolean value) {
    f nal FeatureConf gurat on featureConf g = sc ma.getFeatureConf gurat onByNa (featureNa );
     f (featureConf g != null) {
      getFeatures(featureConf g.getBaseF eld()).setFlagValue(featureConf g, value);
    }
    return t ;
  }

  /** Returns t  features for t  g ven base f eld. */
  publ c  ntegerEncodedFeatures getFeaturesForBaseF eld(Earlyb rdF eldConstant baseF eld) {
    return getFeaturesForBaseF eld(baseF eld.getF eldNa ());
  }

  /** Returns t  features for t  g ven base f eld. */
  publ c  ntegerEncodedFeatures getFeaturesForBaseF eld(Str ng baseF eldNa ) {
    return encodedFeatureMap.get(baseF eldNa );
  }
}
