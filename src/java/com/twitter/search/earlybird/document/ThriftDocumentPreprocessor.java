package com.tw ter.search.earlyb rd.docu nt;

 mport java. o. OExcept on;
 mport java.ut l.L st;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchTruthTableCounter;
 mport com.tw ter.search.common.sc ma.base.F eldNa To dMapp ng;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.base.Thr ftDocu ntUt l;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdEncodedFeatures;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdEncodedFeaturesUt l;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdThr ftDocu ntUt l;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftDocu nt;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftF eld;

 mport geo.google.datamodel.GeoAddressAccuracy;

/**
 * Used to preprocess a Thr ftDocu nt before  ndex ng.
 */
publ c f nal class Thr ftDocu ntPreprocessor {
  pr vate stat c f nal F eldNa To dMapp ng  D_MAP = new Earlyb rdF eldConstants();
  pr vate stat c f nal Str ng F LTER_L NK_VALUE = Earlyb rdThr ftDocu ntUt l.formatF lter(
      Earlyb rdF eldConstant.L NKS_F ELD.getF eldNa ());
  pr vate stat c f nal Str ng HAS_L NK_VALUE = Earlyb rdF eldConstant.getFacetSk pF eldNa (
      Earlyb rdF eldConstant.L NKS_F ELD.getF eldNa ());

  pr vate Thr ftDocu ntPreprocessor() {
  }

  /**
   * Processes t  g ven docu nt.
   */
  publ c stat c Thr ftDocu nt preprocess(
      Thr ftDocu nt doc, Earlyb rdCluster cluster,  mmutableSc ma nterface sc ma)
      throws  OExcept on {
    patchArch veThr ftDocu ntAccuracy(doc, cluster);
    patchArch veHasL nks(doc, cluster);
    addAllM ss ngM nEngage ntF elds(doc, cluster, sc ma);
    return doc;
  }

  pr vate stat c f nal SearchCounter GEO_SCRUBBED_COUNT =
      SearchCounter.export("geo_scrubbed_count");
  pr vate stat c f nal SearchCounter GEO_ARCH VE_PATCHED_ACCURACY_COUNT =
      SearchCounter.export("geo_arch ve_patc d_accuracy_count");
  pr vate stat c f nal SearchCounter GEO_M SS NG_COORD NATE_COUNT =
      SearchCounter.export("geo_m ss ng_coord nate_count");
  pr vate stat c f nal SearchCounter ARCH VED_L NKS_F ELD_PATCHED_COUNT =
      SearchCounter.export("l nks_f eld_patc d_count");

  /**
   * Counter for all t  comb nat ons of nullcast b  set and nullcast f lter set.
   *
   * Sum over `Thr ftDocu ntPreprocessor_nullcast_doc_stats__nullcastB Set_true_*` to get all docs
   * w h nullcast b  set to true.
   */
  pr vate stat c f nal SearchTruthTableCounter NULLCAST_DOC_STATS =
      SearchTruthTableCounter.export(
          "Thr ftDocu ntPreprocessor_nullcast_doc_stats",
          "nullcastB Set",
          "nullcastF lterSet");

  /***
   * See J RA SEARCH-7329
   */
  pr vate stat c vo d patchArch veThr ftDocu ntAccuracy(Thr ftDocu nt doc,
                                                         Earlyb rdCluster cluster) {
    Thr ftF eld geoF eld = Thr ftDocu ntUt l.getF eld(
        doc,
        Earlyb rdF eldConstant.GEO_HASH_F ELD.getF eldNa (),
         D_MAP);
     f (geoF eld != null) {
       f (!geoF eld.getF eldData(). sSetGeoCoord nate()) {
        GEO_M SS NG_COORD NATE_COUNT. ncre nt();
        return;
      }

      // -1  ans that t  data  s geo scrubbed.
       f (geoF eld.getF eldData().getGeoCoord nate().getAccuracy() == -1) {
        doc.getF elds().remove(geoF eld);
        GEO_SCRUBBED_COUNT. ncre nt();
      } else  f (Earlyb rdCluster. sArch ve(cluster)) {
        //  n arch ve  ndex ng,   base prec s on on SearchArch veStatus.getPrec s on, wh ch  s not
        //  n t  scale   want.    always use PO NT_LEVEL scale for now.
        geoF eld.getF eldData().getGeoCoord nate().setAccuracy(
            GeoAddressAccuracy.PO NT_LEVEL.getCode());
        GEO_ARCH VE_PATCHED_ACCURACY_COUNT. ncre nt();
      }
    }
  }

  /**
   * See SEARCH-9635
   * T  patch  s used to replace
   *   ("f eld":" nternal","term":"__f lter_l nks") w h
   *   ("f eld":" nternal","term":"__has_l nks").
   */
  pr vate stat c vo d patchArch veHasL nks(Thr ftDocu nt doc, Earlyb rdCluster cluster) {
     f (!Earlyb rdCluster. sArch ve(cluster)) {
      return;
    }

    L st<Thr ftF eld> f eldL st = Thr ftDocu ntUt l.getF elds(doc,
        Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa (),
         D_MAP);
    for (Thr ftF eld f eld : f eldL st) {
       f (f eld.getF eldData().getStr ngValue().equals(F LTER_L NK_VALUE)) {
        f eld.getF eldData().setStr ngValue(HAS_L NK_VALUE);
        ARCH VED_L NKS_F ELD_PATCHED_COUNT. ncre nt();
        break;
      }
    }
  }

  /**
   * C ck w t r t  nullcast b  and nullcast f lter are cons stent  n t  g ven doc.
   */
  publ c stat c boolean  sNullcastB AndF lterCons stent(Thr ftDocu nt doc,
                                                          mmutableSc ma nterface sc ma) {
    return  sNullcastB AndF lterCons stent(doc, sc ma, NULLCAST_DOC_STATS);
  }

  @V s bleForTest ng
  stat c boolean  sNullcastB AndF lterCons stent(
      Thr ftDocu nt doc,  mmutableSc ma nterface sc ma, SearchTruthTableCounter nullCastStats) {
    f nal boolean  sNullcastB Set = Earlyb rdThr ftDocu ntUt l. sNullcastB Set(sc ma, doc);
    f nal boolean  sNullcastF lterSet = Earlyb rdThr ftDocu ntUt l. sNullcastF lterSet(doc);

    // Track stats.
    nullCastStats.record( sNullcastB Set,  sNullcastF lterSet);

    return  sNullcastB Set ==  sNullcastF lterSet;
  }

  @V s bleForTest ng
  stat c vo d addAllM ss ngM nEngage ntF elds(
      Thr ftDocu nt doc, Earlyb rdCluster cluster,  mmutableSc ma nterface sc ma
  ) throws  OExcept on {
     f (!Earlyb rdCluster. sArch ve(cluster)) {
      return;
    }
    Earlyb rdF eldConstants.Earlyb rdF eldConstant encodedFeatureF eldConstant =
        Earlyb rdF eldConstant.ENCODED_TWEET_FEATURES_F ELD;
    byte[] encodedFeaturesBytes = Thr ftDocu ntUt l.getBytesValue(doc,
        encodedFeatureF eldConstant.getF eldNa (),  D_MAP);
     f (encodedFeaturesBytes == null) {
      return;
    }
    Earlyb rdEncodedFeatures encodedFeatures = Earlyb rdEncodedFeaturesUt l.fromBytes(
        sc ma,
        Earlyb rdF eldConstant.ENCODED_TWEET_FEATURES_F ELD,
        encodedFeaturesBytes,
        0);
    for (Str ng f eld: Earlyb rdF eldConstants.M N_ENGAGEMENT_F ELD_TO_CSF_NAME_MAP.keySet()) {
      Earlyb rdF eldConstant csfEngage ntF eld = Earlyb rdF eldConstants
          .M N_ENGAGEMENT_F ELD_TO_CSF_NAME_MAP.get(f eld);
      Precond  ons.c ckState(csfEngage ntF eld != null);
       nt engage ntCounter = encodedFeatures.getFeatureValue(csfEngage ntF eld);
      Earlyb rdThr ftDocu ntUt l.addNormal zedM nEngage ntF eld(doc, f eld, engage ntCounter);
    }
  }
}
