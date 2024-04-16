package com.tw ter.search.earlyb rd;

 mport scala.Opt on;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport com.tw ter.dec der.Dec der;

publ c f nal class RecentT etRestr ct on {
  pr vate stat c f nal Str ng RECENT_TWEETS_THRESHOLD = "recent_t ets_threshold";
  pr vate stat c f nal Str ng QUERY_CACHE_UNT L_T ME = "query_cac _unt l_t  ";

  @V s bleForTest ng
  publ c stat c f nal  nt DEFAULT_RECENT_TWEET_SECONDS = 15;

  pr vate RecentT etRestr ct on() {
  }

  /**
   * Returns t  po nt  n t   ( n seconds past t  un x epoch) before wh ch all t ets w ll be
   * completely  ndexed. T   s requ red by so  cl ents, because t y rely on Earlyb rd monoton cally
   *  ndex ng t ets by  D and that t ets are completely  ndexed w n t y see t m.
   *
   * @param lastT   T  t   at wh ch t  most recent t et was  ndexed,  n seconds s nce t  un x
   * epoch.
   */
  publ c stat c  nt recentT etsUnt lT  (Dec der dec der,  nt lastT  ) {
    return unt lT  Seconds(dec der, lastT  , RECENT_TWEETS_THRESHOLD);
  }

  /**
   * Returns t  po nt  n t   ( n seconds past t  un x epoch) before wh ch all t ets w ll be
   * completely  ndexed. T   s requ red by so  cl ents, because t y rely on Earlyb rd monoton cally
   *  ndex ng t ets by  D and that t ets are completely  ndexed w n t y see t m.
   *
   * @param lastT   T  t   at wh ch t  most recent t et was  ndexed,  n seconds s nce t  un x
   * epoch.
   */
  publ c stat c  nt queryCac Unt lT  (Dec der dec der,  nt lastT  ) {
    return unt lT  Seconds(dec der, lastT  , QUERY_CACHE_UNT L_T ME);
  }

  pr vate stat c  nt unt lT  Seconds(Dec der dec der,  nt lastT  , Str ng dec derKey) {
     nt recentT etSeconds = getRecentT etSeconds(dec der, dec derKey);

     f (recentT etSeconds == 0) {
      return 0;
    }

    return lastT   - recentT etSeconds;
  }

  pr vate stat c  nt getRecentT etSeconds(Dec der dec der, Str ng dec derKey) {
    Opt on<Object> dec derValue = dec der.getAva lab l y(dec derKey);
     f (dec derValue. sDef ned()) {
      return ( nt) dec derValue.get();
    }
    return DEFAULT_RECENT_TWEET_SECONDS;
  }
}
