package com.tw ter.search.common.relevance.scorers;

 mport java.ut l.Map;
 mport java.ut l.concurrent.ConcurrentMap;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.Maps;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on;
 mport com.tw ter.search.common. tr cs.RelevanceStats;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common.relevance.conf g.T etProcess ngConf g;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssage;
 mport com.tw ter.search.common.relevance.features.T etFeatures;
 mport com.tw ter.search.common.relevance.features.T etTextFeatures;
 mport com.tw ter.search.common.relevance.features.T etTextQual y;

/**
 * Compute a text score for Tw ter ssage based on  s offens veness,
 * shoutness, length, readab l y and hashtag propert es extracted from
 * t et text.
 * <p/>
 * Formula:
 * text_score = offens ve_text_damp ng * offens ve_userna _damp ng *
 * S gma(feature_score_  ght * feature_score)
 * <p/>
 * scored features are: length, readab l y, shout, entropy, l nks
 */
publ c class T etTextScorer extends T etScorer {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(T etTextScorer.class);

  pr vate stat c f nal double DEFAULT_OFFENS VE_TERM_DAMP NG = 0.2d;
  pr vate stat c f nal double DEFAULT_OFFENS VE_NAME_DAMP NG = 0.2d;

  // S gma of all   ghts = 1.0d
  pr vate stat c f nal double DEFAULT_LENGTH_WE GHT = 0.5d;
  pr vate stat c f nal double DEFAULT_READAB L TY_WE GHT = 0.1d;
  pr vate stat c f nal double DEFAULT_SHOUT_WE GHT = 0.1d;
  pr vate stat c f nal double DEFAULT_ENTROPY_WE GHT = 0.25d;
  pr vate stat c f nal double DEFAULT_L NK_WE GHT = 0.05d;

  pr vate stat c f nal double DEFAULT_NO_DAMP NG = 1.0d;

  // S gmo d alpha values for normal zat on
  pr vate stat c f nal double DEFAULT_READAB L TY_ALPHA = 0.05d;
  pr vate stat c f nal double DEFAULT_ENTROPY_ALPHA = 0.5d;
  pr vate stat c f nal double DEFAULT_LENGTH_ALPHA = 0.03d;

  pr vate stat c f nal ConcurrentMap<Str ng, SearchRateCounter> RATE_COUNTERS =
      Maps.newConcurrentMap();
  pr vate stat c f nal ConcurrentMap<Pengu nVers on, Map< nteger, SearchRateCounter>>
      SCORE_H STOGRAMS = Maps.newConcurrentMap();

  pr vate double offens veTermDamp ng = DEFAULT_OFFENS VE_TERM_DAMP NG;
  pr vate double offens veNa Damp ng = DEFAULT_OFFENS VE_NAME_DAMP NG;

  pr vate double length  ght = DEFAULT_LENGTH_WE GHT;
  pr vate double readab l y  ght = DEFAULT_READAB L TY_WE GHT;
  pr vate double shout  ght = DEFAULT_SHOUT_WE GHT;
  pr vate double entropy  ght = DEFAULT_ENTROPY_WE GHT;
  pr vate double l nk  ght = DEFAULT_L NK_WE GHT;

  pr vate double readab l yAlpha = DEFAULT_READAB L TY_ALPHA;
  pr vate double entropyAlpha = DEFAULT_ENTROPY_ALPHA;
  pr vate double lengthAlpha = DEFAULT_LENGTH_ALPHA;

  /** Conf gure from a conf g f le, val date t  conf gurat on. */
  publ c T etTextScorer(Str ng conf gF le) {
    T etProcess ngConf g. n (conf gF le);

    // get damp ngs
    c ck  ghtRange(offens veTermDamp ng = T etProcess ngConf g
        .getDouble("offens ve_term_damp ng", DEFAULT_OFFENS VE_TERM_DAMP NG));
    c ck  ghtRange(offens veNa Damp ng = T etProcess ngConf g
        .getDouble("offens ve_na _damp ng", DEFAULT_OFFENS VE_NAME_DAMP NG));

    // get   ghts
    c ck  ghtRange(length  ght = T etProcess ngConf g
        .getDouble("length_  ght", DEFAULT_LENGTH_WE GHT));
    c ck  ghtRange(readab l y  ght = T etProcess ngConf g
        .getDouble("readab l y_  ght", DEFAULT_READAB L TY_WE GHT));
    c ck  ghtRange(shout  ght = T etProcess ngConf g
        .getDouble("shout_  ght", DEFAULT_SHOUT_WE GHT));
    c ck  ghtRange(entropy  ght = T etProcess ngConf g
        .getDouble("entropy_  ght", DEFAULT_ENTROPY_WE GHT));
    c ck  ghtRange(l nk  ght = T etProcess ngConf g
        .getDouble("l nk_  ght", DEFAULT_L NK_WE GHT));

    // c ck s gma of   ghts
    Precond  ons.c ckArgu nt(
        length  ght + readab l y  ght + shout  ght + entropy  ght + l nk  ght == 1.0d);

    readab l yAlpha = T etProcess ngConf g
        .getDouble("readab l y_alpha", DEFAULT_READAB L TY_ALPHA);
    entropyAlpha = T etProcess ngConf g.getDouble("entropy_alpha", DEFAULT_ENTROPY_ALPHA);
    lengthAlpha = T etProcess ngConf g.getDouble("length_alpha", DEFAULT_LENGTH_ALPHA);
  }

  /** Creates a new T etTextScorer  nstance. */
  publ c T etTextScorer() {
  }

  /** Scores t  g ven t et. */
  publ c vo d scoreT et(f nal Tw ter ssage t et) {
    Precond  ons.c ckNotNull(t et);

    for (Pengu nVers on pengu nVers on : t et.getSupportedPengu nVers ons()) {
      T etFeatures features = Precond  ons.c ckNotNull(t et.getT etFeatures(pengu nVers on));
      T etTextFeatures textFeatures = Precond  ons.c ckNotNull(features.getT etTextFeatures());
      T etTextQual y textQual y = Precond  ons.c ckNotNull(features.getT etTextQual y());
      boolean  sOffens veText = textQual y.hasBoolQual y(
          T etTextQual y.BooleanQual yType.OFFENS VE);
      boolean  sOffens veScreenNa  = textQual y.hasBoolQual y(
          T etTextQual y.BooleanQual yType.OFFENS VE_USER);
      double shoutScore = DEFAULT_NO_DAMP NG - textQual y.getShout();
      double lengthScore = normal ze(textFeatures.getLength(), lengthAlpha);
      double readab l yScore = normal ze(textQual y.getReadab l y(), readab l yAlpha);
      double entropyScore = normal ze(textQual y.getEntropy(), entropyAlpha);

      double score = ( sOffens veText ? offens veTermDamp ng : DEFAULT_NO_DAMP NG)
        * ( sOffens veScreenNa  ? offens veNa Damp ng : DEFAULT_NO_DAMP NG)
        * (length  ght * lengthScore
           + readab l y  ght * readab l yScore
           + shout  ght * shoutScore
           + entropy  ght * entropyScore
           + l nk  ght * (t et.getExpandedUrlMapS ze() > 0 ? 1 : 0));

      // scale to [0, 100] byte
      textQual y.setTextScore((byte) (score * 100));

      updateStats(
           sOffens veText,
           sOffens veScreenNa ,
          textFeatures,
          score,
          getRateCounterStat("num_offens ve_text_", pengu nVers on),
          getRateCounterStat("num_offens ve_user_", pengu nVers on),
          getRateCounterStat("num_no_trends_", pengu nVers on),
          getRateCounterStat("num_has_trends_", pengu nVers on),
          getRateCounterStat("num_too_many_trends_", pengu nVers on),
          getRateCounterStat("num_scored_t ets_", pengu nVers on),
          getScore togram(pengu nVers on));

       f (LOG. sDebugEnabled()) {
        LOG.debug(Str ng.format(
            "T et length [%.2f]   ghted length [%.2f], readab l y [%.2f] "
            + "  ghted readab l y [%.2f], shout [%.2f]   ghted shout [%.2f], "
            + "entropy [%.2f],   ghted entropy [%.2f], "
            + "score [%.2f], text [%s], pengu n vers on [%s]",
            lengthScore,
            length  ght * lengthScore,
            readab l yScore,
            readab l y  ght * readab l yScore,
            shoutScore,
            shout  ght * shoutScore,
            entropyScore,
            entropy  ght * entropyScore,
            score,
            t et.getText(),
            pengu nVers on));
      }
    }
  }

  pr vate vo d updateStats(boolean  sOffens veText,
                           boolean  sOffens veScreenNa ,
                           T etTextFeatures textFeatures,
                           double score,
                           SearchRateCounter offens veTextCounter,
                           SearchRateCounter offens veUserNa Counter,
                           SearchRateCounter noTrendsCounter,
                           SearchRateCounter hasTrendsCounter,
                           SearchRateCounter tooManyTrendsHashtagsCounter,
                           SearchRateCounter scoredT ets,
                           Map< nteger, SearchRateCounter> score togram) {
    // set stats
     f ( sOffens veText) {
      offens veTextCounter. ncre nt();
    }
     f ( sOffens veScreenNa ) {
      offens veUserNa Counter. ncre nt();
    }
     f (textFeatures.getTrend ngTermsS ze() == 0) {
      noTrendsCounter. ncre nt();
    } else {
      hasTrendsCounter. ncre nt();
    }
     f (Tw ter ssage.hasMult pleHashtagsOrTrends(textFeatures)) {
      tooManyTrendsHashtagsCounter. ncre nt();
    }
    scoredT ets. ncre nt();

     nt bucket = ( nt) Math.floor(score * 10) * 10;
    score togram.get(bucket). ncre nt();
  }

  // normal ze t  passed  n value to smoot d [0, 1.0d] range
  pr vate stat c double normal ze(double value, double alpha) {
    return 2 * (1.0d / (1.0d + Math.exp(-(alpha * value))) - 0.5);
  }

  // Make sure   ght values are w h n t  range of [0.0, 1.0]
  pr vate vo d c ck  ghtRange(double value) {
    Precond  ons.c ckArgu nt(value >= 0.0d && value <= 1.0d);
  }

  pr vate Map< nteger, SearchRateCounter> getScore togram(Pengu nVers on pengu nVers on) {
    Map< nteger, SearchRateCounter> score togram = SCORE_H STOGRAMS.get(pengu nVers on);
     f (score togram == null) {
      score togram = Maps.newHashMap();
      Str ng statsNa  = "num_text_score_%d_%s";

      for ( nt   = 0;   <= 100;   += 10) {
        score togram.put( , RelevanceStats.exportRate(
                               Str ng.format(statsNa ,  , pengu nVers on.na ().toLo rCase())));
      }

      score togram = SCORE_H STOGRAMS.put fAbsent(pengu nVers on, score togram);
       f (score togram == null) {
        score togram = SCORE_H STOGRAMS.get(pengu nVers on);
      }
    }

    return score togram;
  }

  pr vate SearchRateCounter getRateCounterStat(Str ng statPref x, Pengu nVers on pengu nVers on) {
    Str ng statNa  = statPref x + pengu nVers on.na ().toLo rCase();
    SearchRateCounter rateCounter = RATE_COUNTERS.get(statNa );
     f (rateCounter == null) {
      // Only one RateCounter  nstance  s created for each stat na . So   don't need to worry
      // that anot r thread m ght've created t   nstance  n t   ant  :   can just create/get
      //  , and store    n t  map.
      rateCounter = RelevanceStats.exportRate(statNa );
      RATE_COUNTERS.put(statNa , rateCounter);
    }
    return rateCounter;
  }
}
