package com.tw ter.search. ngester.p pel ne.tw ter;

 mport java.ut l.L st;
 mport java.ut l.Set;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.HashBasedTable;
 mport com.google.common.collect.Sets;
 mport com.google.common.collect.Table;

 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common.sc ma.Sc maBu lder;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdEncodedFeatures;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdEncodedFeaturesUt l;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftF eld;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndex ngEvent;

/**
 * T  class exports counts of f elds that are present on processed t ets.    s used to ensure
 * that   are not m ss ng  mportant f elds.    s not threadsafe.
 */
publ c class F eldStatExporter {
  pr vate stat c f nal Str ng STAT_FORMAT = "%s_pengu n_%d_docu nts_w h_f eld_%s";
  pr vate stat c f nal Str ng UNKNOWN_F ELD = "%s_pengu n_%d_docu nts_w h_unknown_f eld_%d";
  pr vate f nal Str ng statPref x;
  pr vate f nal Sc ma sc ma;
  pr vate f nal Table<Pengu nVers on,  nteger, SearchRateCounter> f eldCounters
      = HashBasedTable.create();
  pr vate f nal Set<Earlyb rdF eldConstant> encodedT etFeaturesF elds;
  pr vate f nal Set<Earlyb rdF eldConstant> extendedEncodedT etFeaturesF elds;

  pr vate L st<Pengu nVers on> pengu nVers ons;

  F eldStatExporter(Str ng statPref x, Sc ma sc ma, L st<Pengu nVers on> pengu nVers ons) {
    t .statPref x = statPref x;
    t .sc ma = sc ma;
    t .pengu nVers ons = pengu nVers ons;
    t .encodedT etFeaturesF elds =
        getEncodedT etFeaturesF elds(Earlyb rdF eldConstant.ENCODED_TWEET_FEATURES_F ELD);
    t .extendedEncodedT etFeaturesF elds =
        getEncodedT etFeaturesF elds(Earlyb rdF eldConstant.EXTENDED_ENCODED_TWEET_FEATURES_F ELD);

    for (Pengu nVers on vers on : pengu nVers ons) {
      for (Sc ma.F eld nfo  nfo : sc ma.getF eld nfos()) {
        Str ng na  =
            Str ng.format(STAT_FORMAT, statPref x, vers on.getByteValue(),  nfo.getNa ());
        SearchRateCounter counter = SearchRateCounter.export(na );
        f eldCounters.put(vers on,  nfo.getF eld d(), counter);
      }
    }
  }

  /**
   * Exports stats count ng t  number of f elds that are present on each docu nt.
   */
  publ c vo d addF eldStats(Thr ftVers onedEvents event) {
    for (Pengu nVers on pengu nVers on : pengu nVers ons) {
      byte vers on = pengu nVers on.getByteValue();
      Thr ft ndex ngEvent  ndex ngEvent = event.getVers onedEvents().get(vers on);
      Precond  ons.c ckNotNull( ndex ngEvent);

      //   only want to count each f eld once per t et.
      Set< nteger> seenF elds = Sets.newHashSet();
      for (Thr ftF eld f eld :  ndex ngEvent.getDocu nt().getF elds()) {
         nt f eld d = f eld.getF eldConf g d();
         f (seenF elds.add(f eld d)) {
           f (f eld d == Earlyb rdF eldConstant.ENCODED_TWEET_FEATURES_F ELD.getF eld d()) {
            exportEncodedFeaturesStats(Earlyb rdF eldConstant.ENCODED_TWEET_FEATURES_F ELD,
                                       encodedT etFeaturesF elds,
                                       pengu nVers on,
                                       f eld);
          } else  f (f eld d
                     == Earlyb rdF eldConstant.EXTENDED_ENCODED_TWEET_FEATURES_F ELD.getF eld d()) {
            exportEncodedFeaturesStats(Earlyb rdF eldConstant.EXTENDED_ENCODED_TWEET_FEATURES_F ELD,
                                       extendedEncodedT etFeaturesF elds,
                                       pengu nVers on,
                                       f eld);
          } else  f ( sFeatureF eld(f eld)) {
            updateCounterForFeatureF eld(
                f eld.getF eldConf g d(), f eld.getF eldData().get ntValue(), pengu nVers on);
          } else {
            SearchRateCounter counter = f eldCounters.get(pengu nVers on, f eld d);
             f (counter == null) {
              counter = SearchRateCounter.export(
                  Str ng.format(UNKNOWN_F ELD, statPref x, vers on, f eld d));
              f eldCounters.put(pengu nVers on, f eld d, counter);
            }
            counter. ncre nt();
          }
        }
      }
    }
  }

  pr vate boolean  sFeatureF eld(Thr ftF eld f eld) {
    Str ng f eldNa  =
        Earlyb rdF eldConstants.getF eldConstant(f eld.getF eldConf g d()).getF eldNa ();
    return f eldNa .startsW h(Earlyb rdF eldConstants.ENCODED_TWEET_FEATURES_F ELD_NAME
                                + Sc maBu lder.CSF_V EW_NAME_SEPARATOR)
        || f eldNa .startsW h(Earlyb rdF eldConstants.EXTENDED_ENCODED_TWEET_FEATURES_F ELD_NAME
                                + Sc maBu lder.CSF_V EW_NAME_SEPARATOR);
  }

  pr vate Set<Earlyb rdF eldConstant> getEncodedT etFeaturesF elds(
      Earlyb rdF eldConstant featuresF eld) {
    Set<Earlyb rdF eldConstant> sc maFeatureF elds = Sets.newHashSet();
    Str ng baseF eldNa Pref x =
        featuresF eld.getF eldNa () + Sc maBu lder.CSF_V EW_NAME_SEPARATOR;
    for (Earlyb rdF eldConstant f eld : Earlyb rdF eldConstant.values()) {
       f (f eld.getF eldNa ().startsW h(baseF eldNa Pref x)) {
        sc maFeatureF elds.add(f eld);
      }
    }
    return sc maFeatureF elds;
  }

  pr vate vo d exportEncodedFeaturesStats(Earlyb rdF eldConstant featuresF eld,
                                          Set<Earlyb rdF eldConstant> sc maFeatureF elds,
                                          Pengu nVers on pengu nVers on,
                                          Thr ftF eld thr ftF eld) {
    byte[] encodedFeaturesBytes = thr ftF eld.getF eldData().getBytesValue();
    Earlyb rdEncodedFeatures encodedT etFeatures = Earlyb rdEncodedFeaturesUt l.fromBytes(
        sc ma.getSc maSnapshot(), featuresF eld, encodedFeaturesBytes, 0);
    for (Earlyb rdF eldConstant f eld : sc maFeatureF elds) {
      updateCounterForFeatureF eld(
          f eld.getF eld d(), encodedT etFeatures.getFeatureValue(f eld), pengu nVers on);
    }
  }

  pr vate vo d updateCounterForFeatureF eld( nt f eld d,  nt value, Pengu nVers on pengu nVers on) {
     f (value != 0) {
      SearchRateCounter counter = f eldCounters.get(pengu nVers on, f eld d);
       f (counter == null) {
        counter = SearchRateCounter.export(
            Str ng.format(UNKNOWN_F ELD, statPref x, pengu nVers on, f eld d));
        f eldCounters.put(pengu nVers on, f eld d, counter);
      }
      counter. ncre nt();
    }
  }

  publ c vo d updatePengu nVers ons(L st<Pengu nVers on> updatedPengu nVers ons) {
    pengu nVers ons = updatedPengu nVers ons;
  }
}
