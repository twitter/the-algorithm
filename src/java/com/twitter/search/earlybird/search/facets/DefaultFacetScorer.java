package com.tw ter.search.earlyb rd.search.facets;

 mport java. o. OExcept on;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.constants.thr ftjava.Thr ftLanguage;
 mport com.tw ter.search.common.rank ng.thr ftjava.Thr ftFacetEarlyb rdSort ngMode;
 mport com.tw ter.search.common.rank ng.thr ftjava.Thr ftFacetRank ngOpt ons;
 mport com.tw ter.search.common.relevance.features.Earlyb rdDocu ntFeatures;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.common.ut l.lang.Thr ftLanguageUt l;
 mport com.tw ter.search.core.earlyb rd.facets.FacetAccumulator;
 mport com.tw ter.search.core.earlyb rd.facets.FacetCount erator;
 mport com.tw ter.search.core.earlyb rd.facets.FacetLabelProv der;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.earlyb rd.search.Ant Gam ngF lter;
 mport com.tw ter.search.earlyb rd.search.facets.FacetResultsCollector.Accumulator;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;

publ c class DefaultFacetScorer extends FacetScorer {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(FacetScorer.class.getNa ());
  pr vate stat c f nal double DEFAULT_FEATURE_WE GHT = 0.0;
  pr vate stat c f nal byte DEFAULT_PENALTY = 1;

  pr vate stat c f nal byte DEFAULT_REPUTAT ON_M N = 45;

  pr vate f nal Ant Gam ngF lter ant Gam ngF lter;

  // t epcreds below t  value w ll not be counted at all
  pr vate f nal byte reputat onM nF lterThresholdVal;

  // t epcreds bet en reputat onM nF lterThresholdVal and t  value w ll be counted
  // w h a score of 1
  pr vate f nal byte reputat onM nScoreVal;

  pr vate f nal double userRep  ght;
  pr vate f nal double favor es  ght;
  pr vate f nal double parus  ght;
  pr vate f nal double parusBase;
  pr vate f nal double query ndependentPenalty  ght;

  pr vate f nal Thr ftLanguage u Lang;
  pr vate f nal double langEngl shU Boost;
  pr vate f nal double langEngl shFacetBoost;
  pr vate f nal double langDefaultBoost;

  pr vate f nal  nt ant gam ngPenalty;
  pr vate f nal  nt offens veT etPenalty;
  pr vate f nal  nt mult pleHashtagsOrTrendsPenalty;

  pr vate f nal  nt maxScorePerT et;
  pr vate f nal Thr ftFacetEarlyb rdSort ngMode sort ngMode;

  pr vate Earlyb rd ndexSeg ntAtom cReader reader;
  pr vate Earlyb rdDocu ntFeatures features;

  /**
   * Creates a new facet scorer.
   */
  publ c DefaultFacetScorer(Thr ftSearchQuery searchQuery,
                            Thr ftFacetRank ngOpt ons rank ngOpt ons,
                            Ant Gam ngF lter ant Gam ngF lter,
                            Thr ftFacetEarlyb rdSort ngMode sort ngMode) {
    t .sort ngMode = sort ngMode;
    t .ant Gam ngF lter = ant Gam ngF lter;

    maxScorePerT et =
        rank ngOpt ons. sSetMaxScorePerT et()
        ? rank ngOpt ons.getMaxScorePerT et()
        :  nteger.MAX_VALUE;

    // f lters
    reputat onM nF lterThresholdVal =
        rank ngOpt ons. sSetM nT epcredF lterThreshold()
        ? (byte) (rank ngOpt ons.getM nT epcredF lterThreshold() & 0xFF)
        : DEFAULT_REPUTAT ON_M N;

    //   ghts
    // reputat onM nScoreVal must be >= reputat onM nF lterThresholdVal
    reputat onM nScoreVal =
        (byte) Math.max(rank ngOpt ons. sSetReputat onParams()
        ? (byte) rank ngOpt ons.getReputat onParams().getM n()
        : DEFAULT_REPUTAT ON_M N, reputat onM nF lterThresholdVal);

    parus  ght =
        rank ngOpt ons. sSetParusScoreParams() && rank ngOpt ons.getParusScoreParams(). sSet  ght()
        ? rank ngOpt ons.getParusScoreParams().get  ght()
        : DEFAULT_FEATURE_WE GHT;
    // compute t  once so that base ** parusScore  s backwards-compat ble
    parusBase = Math.sqrt(1 + parus  ght);

    userRep  ght =
        rank ngOpt ons. sSetReputat onParams() && rank ngOpt ons.getReputat onParams(). sSet  ght()
        ? rank ngOpt ons.getReputat onParams().get  ght()
        : DEFAULT_FEATURE_WE GHT;

    favor es  ght =
        rank ngOpt ons. sSetFavor esParams() && rank ngOpt ons.getFavor esParams(). sSet  ght()
        ? rank ngOpt ons.getFavor esParams().get  ght()
        : DEFAULT_FEATURE_WE GHT;

    query ndependentPenalty  ght =
        rank ngOpt ons. sSetQuery ndependentPenalty  ght()
        ? rank ngOpt ons.getQuery ndependentPenalty  ght()
        : DEFAULT_FEATURE_WE GHT;

    // penalty  ncre nt
    ant gam ngPenalty =
        rank ngOpt ons. sSetAnt gam ngPenalty()
        ? rank ngOpt ons.getAnt gam ngPenalty()
        : DEFAULT_PENALTY;

    offens veT etPenalty =
        rank ngOpt ons. sSetOffens veT etPenalty()
        ? rank ngOpt ons.getOffens veT etPenalty()
        : DEFAULT_PENALTY;

    mult pleHashtagsOrTrendsPenalty =
        rank ngOpt ons. sSetMult pleHashtagsOrTrendsPenalty()
        ? rank ngOpt ons.getMult pleHashtagsOrTrendsPenalty()
        : DEFAULT_PENALTY;

    // query  nformat on
     f (!searchQuery. sSetU Lang() || searchQuery.getU Lang(). sEmpty()) {
      u Lang = Thr ftLanguage.UNKNOWN;
    } else {
      u Lang = Thr ftLanguageUt l.getThr ftLanguageOf(searchQuery.getU Lang());
    }
    langEngl shU Boost = rank ngOpt ons.getLangEngl shU Boost();
    langEngl shFacetBoost = rank ngOpt ons.getLangEngl shFacetBoost();
    langDefaultBoost = rank ngOpt ons.getLangDefaultBoost();
  }

  @Overr de
  protected vo d startSeg nt(Earlyb rd ndexSeg ntAtom cReader seg ntReader) throws  OExcept on {
    reader = seg ntReader;
    features = new Earlyb rdDocu ntFeatures(reader);
     f (ant Gam ngF lter != null) {
      ant Gam ngF lter.startSeg nt(reader);
    }
  }

  @Overr de
  publ c vo d  ncre ntCounts(Accumulator accumulator,  nt  nternalDoc D) throws  OExcept on {
    FacetCount erator. ncre ntData data = accumulator.accessor. ncre ntData;
    data.accumulators = accumulator.accumulators;
    features.advance( nternalDoc D);

    // Also keep track of t  t et language of t et t mselves.
    data.language d = ( nt) features.getFeatureValue(Earlyb rdF eldConstant.LANGUAGE);

     f (ant gam ngPenalty > 0
        && ant Gam ngF lter != null
        && !ant Gam ngF lter.accept( nternalDoc D)) {
      data.  ghtedCount ncre nt = 0;
      data.penalty ncre nt = ant gam ngPenalty;
      data.t epCred = 0;
      accumulator.accessor.collect( nternalDoc D);
      return;
    }

     f (offens veT etPenalty > 0 && features. sFlagSet(Earlyb rdF eldConstant. S_OFFENS VE_FLAG)) {
      data.  ghtedCount ncre nt = 0;
      data.penalty ncre nt = offens veT etPenalty;
      data.t epCred = 0;
      accumulator.accessor.collect( nternalDoc D);
      return;
    }

    byte userRep = (byte) features.getFeatureValue(Earlyb rdF eldConstant.USER_REPUTAT ON);

     f (userRep < reputat onM nF lterThresholdVal) {
      // don't penal ze
      data.  ghtedCount ncre nt = 0;
      data.penalty ncre nt = 0;
      data.t epCred = 0;
      accumulator.accessor.collect( nternalDoc D);
      return;
    }

    // Ot r non-term nat ng penalt es
     nt penalty = 0;
     f (mult pleHashtagsOrTrendsPenalty > 0
        && features. sFlagSet(Earlyb rdF eldConstant.HAS_MULT PLE_HASHTAGS_OR_TRENDS_FLAG)) {
      penalty += mult pleHashtagsOrTrendsPenalty;
    }

    double parus = 0xFF & (byte) features.getFeatureValue(Earlyb rdF eldConstant.PARUS_SCORE);

    double score = Math.pow(1 + userRep  ght, Math.max(0, userRep - reputat onM nScoreVal));

     f (parus > 0) {
      score += Math.pow(parusBase, parus);
    }

     nt favor eCount =
        ( nt) features.getUnnormal zedFeatureValue(Earlyb rdF eldConstant.FAVOR TE_COUNT);
     f (favor eCount > 0) {
      score += favor eCount * favor es  ght;
    }

    // Language preferences
     nt t etL nkLang d = ( nt) features.getFeatureValue(Earlyb rdF eldConstant.L NK_LANGUAGE);
     f (t etL nkLang d == Thr ftLanguage.UNKNOWN.getValue()) {
      // fall back to use t  t et language  self.
      t etL nkLang d = ( nt) features.getFeatureValue(Earlyb rdF eldConstant.LANGUAGE);
    }
     f (u Lang != Thr ftLanguage.UNKNOWN && u Lang.getValue() != t etL nkLang d) {
       f (u Lang == Thr ftLanguage.ENGL SH) {
        score *= langEngl shU Boost;
      } else  f (t etL nkLang d == Thr ftLanguage.ENGL SH.getValue()) {
        score *= langEngl shFacetBoost;
      } else {
        score *= langDefaultBoost;
      }
    }

    // make sure a s ngle t et can't contr bute too h gh a score
     f (score > maxScorePerT et) {
      score = maxScorePerT et;
    }

    data.  ghtedCount ncre nt = ( nt) score;
    data.penalty ncre nt = penalty;
    data.t epCred = userRep & 0xFF;
    accumulator.accessor.collect( nternalDoc D);
  }

  @Overr de
  publ c FacetAccumulator getFacetAccumulator(FacetLabelProv der labelProv der) {
    return new Hash ngAndPrun ngFacetAccumulator(labelProv der, query ndependentPenalty  ght,
            Hash ngAndPrun ngFacetAccumulator.getComparator(sort ngMode));
  }
}
