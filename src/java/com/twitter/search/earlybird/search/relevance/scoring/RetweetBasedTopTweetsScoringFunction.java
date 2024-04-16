package com.tw ter.search.earlyb rd.search.relevance.scor ng;

 mport java. o. OExcept on;

 mport org.apac .lucene.search.Explanat on;

 mport com.tw ter.search.common.relevance.features.MutableFeatureNormal zers;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadata;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadataOpt ons;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultType;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultsRelevanceStats;

/**
 * A topt ets query cac   ndex select on scor ng funct on that  s based purely on ret et counts.
 * T  goal of t  scor ng functon  s to deprecate   et score  n ent rety.
 *
 * Once all legacy   et scores are dra ned from ex st ng earlyb rd  ndex, new parus score replaces
 * ex st ng   et score pos  on, t n t  class w ll be deprecated, a new scor ng funct on
 * us ng parus score shall replace t .
 *
 * t  scor ng funct on  s only used  n Query Cac  for mark ng top t ets
 *  n t  background. W n searc d, those t ets are st ll ranked w h l near or model-based
 * scor ng funct on.
 *
 */
publ c class Ret etBasedTopT etsScor ngFunct on extends Scor ngFunct on {
  pr vate stat c f nal double DEFAULT_RECENCY_SCORE_FRACT ON = 0.1;
  pr vate stat c f nal double DEFAULT_S GMO D_APLHA = 0.008;
  pr vate stat c f nal  nt DEFAULT_RECENCY_CENTER_M NUTES = 1080;

  //  f   update t  default cut off, make sure   update t  query cac  f lter  n
  // querycac .yml
  //
  //   know currently each t   sl ce, each part  on has about 10K entr es  n topt ets query
  // cac . T se are un que t ets. Look ng at ret et updates, each t   sl ce, each part  on has
  // about 650K un que t ets that rece ved ret et. To create roughly s m lar number of entr es  n
  // query cac ,   need top 2% of such t ets, and that sets to m n ret et count to 4.
  //  n t  l near scor ng funct on,   w ll rescale ret et count to [0, 1] range,
  // w h an  nput range of [0, 20]. G ven t  realt   factor's   ght of 0.1, that g ve  
  // m n mal ret et score threshold to: 4/20 * 0.9 = 0.18.
  // Test ng on prod sho d much h g r volu  due to t  generous sett ng of max value of 20,
  // (h g st   have seen  s 14). Adjusted to 0.21 wh ch gave us s m lar volu .
  pr vate stat c f nal double DEFAULT_CUT_OFF_SCORE = 0.21;

  // Normal ze ret et counts from [0, 20] range to [0, 1] range
  pr vate stat c f nal double MAX_RETWEET_COUNT = 20.0;
  pr vate stat c f nal double M N_USER_REPUTAT ON = 40.0;  // matc s   et system threshold

  /**
   * T  scores for t  ret et based top t ets have to be  n t  [0, 1]  nterval. So   can't use
   * SK P_H T as t  lo st poss ble score, and  nstead have to use Float.M N_VALUE.
   *
   *  's OK to use d fferent values for t se constants, because t y do not  nterfere w h each
   * ot r. T  constant  s only used  n Ret etBasedTopT etsScor ngFunct on, wh ch  s only used
   * to f lter t  h s for t  [score_f lter ret ets m nScore maxScore] operator. So t  scores
   * returned by Ret etBasedTopT etsScor ngFunct on.score() do not have any  mpact on t  f nal
   * h  score.
   *
   * See Earlyb rdLuceneQueryV s or.v s ScoredF lterOperator() and ScoreF lterQuery for more deta ls.
   */
  pr vate stat c f nal float RETWEET_BASED_TOP_TWEETS_LOWEST_SCORE = Float.M N_VALUE;

  pr vate f nal double recencyScoreFract on;
  pr vate f nal double s gmo dAlpha;
  pr vate f nal double cutOffScore;
  pr vate f nal  nt recencyCenterM nutes;
  pr vate f nal double maxRecency;

  pr vate f nal  nt currentT  Seconds;

  pr vate Thr ftSearchResult tadata  tadata = null;
  pr vate double score;
  pr vate double ret etCount;

  publ c Ret etBasedTopT etsScor ngFunct on( mmutableSc ma nterface sc ma) {
    t (sc ma, DEFAULT_RECENCY_SCORE_FRACT ON,
         DEFAULT_S GMO D_APLHA,
         DEFAULT_CUT_OFF_SCORE,
         DEFAULT_RECENCY_CENTER_M NUTES);
  }

  /**
   * Creates a no decay scor ng funct on (used by top arch ve).
   * Ot rw se sa  as default constructor.
   * @param nodecay   f no decay  s set to true. Alpha  s set to 0.0.
   */
  publ c Ret etBasedTopT etsScor ngFunct on( mmutableSc ma nterface sc ma, boolean nodecay) {
    t (sc ma, DEFAULT_RECENCY_SCORE_FRACT ON,
         nodecay ? 0.0 : DEFAULT_S GMO D_APLHA,
         DEFAULT_CUT_OFF_SCORE,
         DEFAULT_RECENCY_CENTER_M NUTES);
  }

  publ c Ret etBasedTopT etsScor ngFunct on( mmutableSc ma nterface sc ma,
                                              double recencyScoreFract on, double s gmo dAlpha,
                                              double cutOffScore,  nt recencyCenterM nutes) {
    super(sc ma);
    t .recencyScoreFract on = recencyScoreFract on;
    t .s gmo dAlpha = s gmo dAlpha;
    t .cutOffScore = cutOffScore;
    t .recencyCenterM nutes = recencyCenterM nutes;
    t .maxRecency = computeS gmo d(0);
    t .currentT  Seconds = ( nt) (System.currentT  M ll s() / 1000);
  }

  @Overr de
  protected float score(float luceneQueryScore) throws  OExcept on {
    // Reset t  data for each t et!!!
     tadata = null;
     f (docu ntFeatures. sFlagSet(Earlyb rdF eldConstant. S_OFFENS VE_FLAG)
        || (docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.USER_REPUTAT ON)
            < M N_USER_REPUTAT ON)) {
      score = RETWEET_BASED_TOP_TWEETS_LOWEST_SCORE;
    } else {
      // Note that  re   want t  post log2 value, as t  MAX_RETWEET_COUNT was actually
      // set up for that.
      ret etCount = MutableFeatureNormal zers.BYTE_NORMAL ZER.unnormAndLog2(
          (byte) docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.RETWEET_COUNT));
      f nal double recencyScore = computeTopT etRecencyScore();

      score = (ret etCount / MAX_RETWEET_COUNT) * (1 - recencyScoreFract on)
          + recencyScoreFract on * recencyScore;

       f (score < t .cutOffScore) {
        score = RETWEET_BASED_TOP_TWEETS_LOWEST_SCORE;
      }
    }

    return (float) score;
  }

  pr vate double computeS gmo d(double x) {
    return 1.0f / (1.0f + Math.exp(s gmo dAlpha * (x - recencyCenterM nutes)));
  }

  pr vate double computeTopT etRecencyScore() {
    double d ffM nutes =
        Math.max(0, currentT  Seconds - t  Mapper.getT  (getCurrentDoc D())) / 60.0;
    return computeS gmo d(d ffM nutes) / maxRecency;
  }

  @Overr de
  protected Explanat on doExpla n(float luceneScore) {
    return null;
  }

  @Overr de
  publ c Thr ftSearchResult tadata getResult tadata(Thr ftSearchResult tadataOpt ons opt ons) {
     f ( tadata == null) {
       tadata = new Thr ftSearchResult tadata()
          .setResultType(Thr ftSearchResultType.POPULAR)
          .setPengu nVers on(Earlyb rdConf g.getPengu nVers onByte());
       tadata.setRet etCount(( nt) ret etCount);
       tadata.setScore(score);
    }
    return  tadata;
  }

  @Overr de
  publ c vo d updateRelevanceStats(Thr ftSearchResultsRelevanceStats relevanceStats) {
  }
}
