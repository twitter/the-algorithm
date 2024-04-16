package com.tw ter.search.earlyb rd.search.relevance.scor ng;

 mport java. o. OExcept on;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport org.apac .lucene.search.Explanat on;

 mport com.tw ter.search.common.relevance.features.RelevanceS gnalConstants;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadata;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadataOpt ons;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultsRelevanceStats;

publ c class SpamVectorScor ngFunct on extends Scor ngFunct on {
  pr vate stat c f nal  nt M N_TWEEPCRED_W TH_L NK =
      Earlyb rdConf g.get nt("m n_t epcred_w h_non_wh el sted_l nk", 25);

  // T  engage nt threshold that prevents us from f lter ng users w h low t epcred.
  pr vate stat c f nal  nt ENGAGEMENTS_NO_F LTER = 1;

  @V s bleForTest ng
  stat c f nal float NOT_SPAM_SCORE = 0.5f;
  @V s bleForTest ng
  stat c f nal float SPAM_SCORE = -0.5f;

  publ c SpamVectorScor ngFunct on( mmutableSc ma nterface sc ma) {
    super(sc ma);
  }

  @Overr de
  protected float score(float luceneQueryScore) throws  OExcept on {
     f (docu ntFeatures. sFlagSet(Earlyb rdF eldConstant.FROM_VER F ED_ACCOUNT_FLAG)) {
      return NOT_SPAM_SCORE;
    }

     nt t epCredThreshold = 0;
     f (docu ntFeatures. sFlagSet(Earlyb rdF eldConstant.HAS_L NK_FLAG)
        && !docu ntFeatures. sFlagSet(Earlyb rdF eldConstant.HAS_ MAGE_URL_FLAG)
        && !docu ntFeatures. sFlagSet(Earlyb rdF eldConstant.HAS_V DEO_URL_FLAG)
        && !docu ntFeatures. sFlagSet(Earlyb rdF eldConstant.HAS_NEWS_URL_FLAG)) {
      // Conta ns a non- d a non-news l nk, def n e spam vector.
      t epCredThreshold = M N_TWEEPCRED_W TH_L NK;
    }

     nt t epcred = ( nt) docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.USER_REPUTAT ON);

    // For new user, t epcred  s set to a sent nel value of -128, spec f ed at
    // src/thr ft/com/tw ter/search/common/ ndex ng/status.thr ft
     f (t epcred >= t epCredThreshold
        || t epcred == ( nt) RelevanceS gnalConstants.UNSET_REPUTAT ON_SENT NEL) {
      return NOT_SPAM_SCORE;
    }

    double ret etCount =
        docu ntFeatures.getUnnormal zedFeatureValue(Earlyb rdF eldConstant.RETWEET_COUNT);
    double replyCount =
        docu ntFeatures.getUnnormal zedFeatureValue(Earlyb rdF eldConstant.REPLY_COUNT);
    double favor eCount =
        docu ntFeatures.getUnnormal zedFeatureValue(Earlyb rdF eldConstant.FAVOR TE_COUNT);

    //  f t  t et has enough engage nts, do not mark   as spam.
     f (ret etCount + replyCount + favor eCount >= ENGAGEMENTS_NO_F LTER) {
      return NOT_SPAM_SCORE;
    }

    return SPAM_SCORE;
  }

  @Overr de
  protected Explanat on doExpla n(float luceneScore) {
    return null;
  }

  @Overr de
  publ c Thr ftSearchResult tadata getResult tadata(Thr ftSearchResult tadataOpt ons opt ons) {
    return null;
  }

  @Overr de
  publ c vo d updateRelevanceStats(Thr ftSearchResultsRelevanceStats relevanceStats) {
  }
}
