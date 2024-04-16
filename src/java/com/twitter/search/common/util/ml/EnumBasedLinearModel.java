package com.tw ter.search.common.ut l.ml;

 mport java. o. OExcept on;
 mport java.ut l.EnumMap;
 mport java.ut l.EnumSet;
 mport java.ut l.Map;
 mport java.ut l.Set;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.base.Pred cates;
 mport com.google.common.collect. mmutableMap;
 mport com.google.common.collect.Maps;

 mport com.tw ter.search.common.f le.AbstractF le;
 mport com.tw ter.search.common.ut l. o.TextF leLoad ngUt ls;

/**
 * Represents a l near model for scor ng and class f cat on.
 *
 * T  l st of features  s def ned by an Enum class. T  model   ghts and  nstances are
 * represented as maps that must conta n an entry for all t  values of t  enum.
 *
 */
publ c class EnumBasedL nearModel<K extends Enum<K>>  mple nts MapBasedL nearModel<K> {

  pr vate f nal EnumSet<K> features;
  pr vate f nal EnumMap<K, Float>   ghts;

  /**
   * Creates a model from a map of   ghts.
   *
   * @param enumType Enum used for t  keys
   * @param   ghts Feature   ghts.
   */
  publ c EnumBasedL nearModel(Class<K> enumType, Map<K, Float>   ghts) {
    features = EnumSet.allOf(enumType);
    EnumMap<K, Float> enum  ghts =
        new EnumMap<>(Maps.f lterValues(  ghts, Pred cates.notNull()));
    Precond  ons.c ckArgu nt(features.equals(enum  ghts.keySet()),
        "T  model does not  nclude   ghts for all t  ava lable features");

    t .  ghts = enum  ghts;
  }

  publ c  mmutableMap<K, Float> get  ghts() {
    return Maps. mmutableEnumMap(  ghts);
  }

  @Overr de
  publ c float score(Map<K, Float>  nstance) {
    float total = 0;
    for (Map.Entry<K, Float>   ghtEntry :   ghts.entrySet()) {
      Float feature =  nstance.get(  ghtEntry.getKey());
       f (feature != null) {
        total +=   ghtEntry.getValue() * feature;
      }
    }
    return total;
  }

  /**
   * Determ nes w t r an  nstance  s pos  ve.
   */
  @Overr de
  publ c boolean class fy(float threshold, Map<K, Float>  nstance) {
    return score( nstance) > threshold;
  }

  @Overr de
  publ c boolean class fy(Map<K, Float>  nstance) {
    return class fy(0,  nstance);
  }

  @Overr de
  publ c Str ng toStr ng() {
    return Str ng.format("EnumBasedL nearModel[%s]",   ghts);
  }

  /**
   * Creates a model w re all t  features have t  sa    ght.
   * T   thod  s useful for generat ng t  feature vectors for tra n ng a new model.
   */
  publ c stat c <T extends Enum<T>> EnumBasedL nearModel<T> createW hEqual  ght(Class<T> enumType,
                                                                                  Float   ght) {
    EnumSet<T> features = EnumSet.allOf(enumType);
    EnumMap<T, Float>   ghts = Maps.newEnumMap(enumType);
    for (T feature : features) {
        ghts.put(feature,   ght);
    }
    return new EnumBasedL nearModel<>(enumType,   ghts);
  }

  /**
   * Loads t  model from a TSV f le w h t  follow ng format:
   *
   *    feature_na   \t    ght
   */
  publ c stat c <T extends Enum<T>> EnumBasedL nearModel<T> createFromF le(
      Class<T> enumType, AbstractF le path) throws  OExcept on {
    return new EnumBasedL nearModel<>(enumType, load  ghts(enumType, path, true));
  }

  /**
   * Loads t  model from a TSV f le, us ng a default   ght of 0 for m ss ng features.
   *
   * F le format:
   *
   *     feature_na   \t    ght
   */
  publ c stat c <T extends Enum<T>> EnumBasedL nearModel<T> createFromF leSafe(
      Class<T> enumType, AbstractF le path) throws  OExcept on {
    return new EnumBasedL nearModel<>(enumType, load  ghts(enumType, path, false));
  }

  /**
   * Creates a map of (feature_na ,   ght) from a TSV f le.
   *
   *  f str ctMode  s true,   w ll throw an except on  f t  f le doesn't conta n all t 
   * features declared  n t  enum. Ot rw se,   w ll use zero as default value.
   *
   */
  pr vate stat c <T extends Enum<T>> EnumMap<T, Float> load  ghts(
      Class<T> enumType, AbstractF le f leHandle, boolean str ctMode) throws  OExcept on {
    Map<Str ng, Float>   ghtsFromF le =
      TextF leLoad ngUt ls.loadMapFromF le(f leHandle,  nput -> Float.parseFloat( nput));
    EnumMap<T, Float>   ghts = Maps.newEnumMap(enumType);
    Set<T> expectedFeatures = EnumSet.allOf(enumType);
     f (!str ctMode) {
      for (T feature : expectedFeatures) {
          ghts.put(feature, 0f);
      }
    }
    for (Str ng featureNa  :   ghtsFromF le.keySet()) {
      Float   ght =   ghtsFromF le.get(featureNa );
        ghts.put(Enum.valueOf(enumType, featureNa .toUpperCase()),   ght);
    }
    Precond  ons.c ckArgu nt(expectedFeatures.equals(  ghts.keySet()),
        "Model does not conta n   ghts for all t  features");
    return   ghts;
  }
}
