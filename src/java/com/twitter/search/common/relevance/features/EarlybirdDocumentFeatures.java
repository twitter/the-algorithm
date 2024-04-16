package com.tw ter.search.common.relevance.features;

 mport java. o. OExcept on;
 mport java.ut l.Map;
 mport java.ut l.funct on.Funct on;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.Maps;

 mport org.apac .lucene. ndex.LeafReader;
 mport org.apac .lucene. ndex.Nu r cDocValues;

 mport com.tw ter.search.common.features.thr ft.Thr ftSearchResultFeatures;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.sc ma.base.FeatureConf gurat on;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftCSFType;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftFeatureNormal zat onType;

publ c class Earlyb rdDocu ntFeatures {
  pr vate stat c f nal Map< nteger, SearchCounter> FEATURE_CONF G_ S_NULL_MAP = Maps.newHashMap();
  pr vate stat c f nal Map< nteger, SearchCounter> FEATURE_OUTPUT_TYPE_ S_NULL_MAP =
      Maps.newHashMap();
  pr vate stat c f nal Map< nteger, SearchCounter> NO_SCHEMA_F ELD_FOR_FEATURE_MAP =
      Maps.newHashMap();
  pr vate stat c f nal Str ng FEATURE_CONF G_ S_NULL_COUNTER_PATTERN =
      "null_feature_conf g_for_feature_ d_%d";
  pr vate stat c f nal Str ng FEATURE_OUTPUT_TYPE_ S_NULL_COUNTER_PATTERN =
      "null_output_type_for_feature_ d_%d";
  pr vate stat c f nal Str ng NO_SCHEMA_F ELD_FOR_FEATURE_COUNTER_PATTERN =
      "no_sc ma_f eld_for_feature_ d_%d";
  pr vate stat c f nal SearchCounter UNKNOWN_FEATURE_OUTPUT_TYPE_COUNTER =
      SearchCounter.export("unknown_feature_output_type");

  pr vate f nal Map<Str ng, Nu r cDocValues> nu r cDocValues = Maps.newHashMap();
  pr vate f nal LeafReader leafReader;
  pr vate  nt doc d = -1;

  /**
   * Creates a new Earlyb rdDocu ntFeatures  nstance that w ll return feature values based on t 
   * Nu r cDocValues stored  n t  g ven LeafReader for t  g ven docu nt.
   */
  publ c Earlyb rdDocu ntFeatures(LeafReader leafReader) {
    t .leafReader = Precond  ons.c ckNotNull(leafReader);
  }

  /**
   * Advances t   nstance to t  g ven doc  D. T  new doc  D must be greater than or equal to t 
   * current doc  D stored  n t   nstance.
   */
  publ c vo d advance( nt target) {
    Precond  ons.c ckArgu nt(
        target >= 0,
        "Target (%s) cannot be negat ve.",
        target);
    Precond  ons.c ckArgu nt(
        target >= doc d,
        "Target (%s) smaller than current doc  D (%s).",
        target,
        doc d);
    Precond  ons.c ckArgu nt(
        target < leafReader.maxDoc(),
        "Target (%s) cannot be greater than or equal to t  max doc  D (%s).",
        target,
        leafReader.maxDoc());
    doc d = target;
  }

  /**
   * Returns t  feature value for t  g ven f eld.
   */
  publ c long getFeatureValue(Earlyb rdF eldConstant f eld) throws  OExcept on {
    // T   ndex m ght not have a Nu r cDocValues  nstance for t  feature.
    // T  m ght happen  f   dynam cally update t  feature sc ma, for example.
    //
    // Cac  t  Nu r cDocValues  nstances for all accessed features, even  f t y're null.
    Str ng f eldNa  = f eld.getF eldNa ();
    Nu r cDocValues docValues;
     f (nu r cDocValues.conta nsKey(f eldNa )) {
      docValues = nu r cDocValues.get(f eldNa );
    } else {
      docValues = leafReader.getNu r cDocValues(f eldNa );
      nu r cDocValues.put(f eldNa , docValues);
    }
    return docValues != null && docValues.advanceExact(doc d) ? docValues.longValue() : 0L;
  }

  /**
   * Determ nes  f t  g ven flag  s set.
   */
  publ c boolean  sFlagSet(Earlyb rdF eldConstant f eld) throws  OExcept on {
    return getFeatureValue(f eld) != 0;
  }

  /**
   * Returns t  unnormal zed value for t  g ven f eld.
   */
  publ c double getUnnormal zedFeatureValue(Earlyb rdF eldConstant f eld) throws  OExcept on {
    long featureValue = getFeatureValue(f eld);
    Thr ftFeatureNormal zat onType normal zat onType = f eld.getFeatureNormal zat onType();
     f (normal zat onType == null) {
      normal zat onType = Thr ftFeatureNormal zat onType.NONE;
    }
    sw ch (normal zat onType) {
      case NONE:
        return featureValue;
      case LEGACY_BYTE_NORMAL ZER:
        return MutableFeatureNormal zers.BYTE_NORMAL ZER.unnormLo rBound((byte) featureValue);
      case LEGACY_BYTE_NORMAL ZER_W TH_LOG2:
        return MutableFeatureNormal zers.BYTE_NORMAL ZER.unnormAndLog2((byte) featureValue);
      case SMART_ NTEGER_NORMAL ZER:
        return MutableFeatureNormal zers.SMART_ NTEGER_NORMAL ZER.unnormUpperBound(
            (byte) featureValue);
      case PRED CT ON_SCORE_NORMAL ZER:
        return  ntNormal zers.PRED CT ON_SCORE_NORMAL ZER.denormal ze(( nt) featureValue);
      default:
        throw new  llegalArgu ntExcept on(
            "Unsupported normal zat on type " + normal zat onType + " for feature "
                + f eld.getF eldNa ());
    }
  }

  /**
   * Creates a Thr ftSearchResultFeatures  nstance populated w h values for all ava lable features
   * that have a non-zero value set.
   */
  publ c Thr ftSearchResultFeatures getSearchResultFeatures( mmutableSc ma nterface sc ma)
      throws  OExcept on {
    return getSearchResultFeatures(sc ma, (feature d) -> true);
  }

  /**
   * Creates a Thr ftSearchResultFeatures  nstance populated w h values for all ava lable features
   * that have a non-zero value set.
   *
   * @param sc ma T  sc ma.
   * @param shouldCollectFeature d A pred cate that determ nes wh ch features should be collected.
   */
  publ c Thr ftSearchResultFeatures getSearchResultFeatures(
       mmutableSc ma nterface sc ma,
      Funct on< nteger, Boolean> shouldCollectFeature d) throws  OExcept on {
    Map< nteger, Boolean> boolValues = Maps.newHashMap();
    Map< nteger, Double> doubleValues = Maps.newHashMap();
    Map< nteger,  nteger>  ntValues = Maps.newHashMap();
    Map< nteger, Long> longValues = Maps.newHashMap();

    Map< nteger, FeatureConf gurat on>  dToFeatureConf gMap = sc ma.getFeature dToFeatureConf g();
    for ( nt feature d : sc ma.getSearchFeatureSc ma().getEntr es().keySet()) {
       f (!shouldCollectFeature d.apply(feature d)) {
        cont nue;
      }

      FeatureConf gurat on featureConf g =  dToFeatureConf gMap.get(feature d);
       f (featureConf g == null) {
        FEATURE_CONF G_ S_NULL_MAP.compute fAbsent(
            feature d,
            (f d) -> SearchCounter.export(
                Str ng.format(FEATURE_CONF G_ S_NULL_COUNTER_PATTERN, f d))). ncre nt();
        cont nue;
      }

      Thr ftCSFType outputType = featureConf g.getOutputType();
       f (outputType == null) {
        FEATURE_OUTPUT_TYPE_ S_NULL_MAP.compute fAbsent(
            feature d,
            (f d) -> SearchCounter.export(
                Str ng.format(FEATURE_OUTPUT_TYPE_ S_NULL_COUNTER_PATTERN, f d))). ncre nt();
        cont nue;
      }

       f (!Earlyb rdF eldConstants.hasF eldConstant(feature d)) {
        // Should only happen for features that  re dynam cally added to t  sc ma.
        NO_SCHEMA_F ELD_FOR_FEATURE_MAP.compute fAbsent(
            feature d,
            (f d) -> SearchCounter.export(
                Str ng.format(NO_SCHEMA_F ELD_FOR_FEATURE_COUNTER_PATTERN, f d))). ncre nt();
        cont nue;
      }

      Earlyb rdF eldConstant f eld = Earlyb rdF eldConstants.getF eldConstant(feature d);
      sw ch (outputType) {
        case BOOLEAN:
           f ( sFlagSet(f eld)) {
            boolValues.put(feature d, true);
          }
          break;
        case BYTE:
          //  's unclear why   don't add t  feature to a separate byteValues map...
          byte byteFeatureValue = (byte) getFeatureValue(f eld);
           f (byteFeatureValue != 0) {
             ntValues.put(feature d, ( nt) byteFeatureValue);
          }
          break;
        case  NT:
           nt  ntFeatureValue = ( nt) getFeatureValue(f eld);
           f ( ntFeatureValue != 0) {
             ntValues.put(feature d,  ntFeatureValue);
          }
          break;
        case LONG:
          long longFeatureValue = getFeatureValue(f eld);
           f (longFeatureValue != 0) {
            longValues.put(feature d, longFeatureValue);
          }
          break;
        case FLOAT:
          //  's unclear why   don't add t  feature to a separate floatValues map...
          float floatFeatureValue = (float) getFeatureValue(f eld);
           f (floatFeatureValue != 0) {
            doubleValues.put(feature d, (double) floatFeatureValue);
          }
          break;
        case DOUBLE:
          double doubleFeatureValue = getUnnormal zedFeatureValue(f eld);
           f (doubleFeatureValue != 0) {
            doubleValues.put(feature d, doubleFeatureValue);
          }
          break;
        default:
          UNKNOWN_FEATURE_OUTPUT_TYPE_COUNTER. ncre nt();
      }
    }

    return new Thr ftSearchResultFeatures()
        .setBoolValues(boolValues)
        .set ntValues( ntValues)
        .setLongValues(longValues)
        .setDoubleValues(doubleValues);
  }
}
