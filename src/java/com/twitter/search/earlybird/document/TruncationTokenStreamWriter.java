package com.tw ter.search.earlyb rd.docu nt;

 mport com.tw ter.common.text.token.TokenProcessor;
 mport com.tw ter.common.text.token.Tw terTokenStream;
 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.search.common.dec der.Dec derUt l;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.search.common.sc ma.Sc maDocu ntFactory;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;

publ c class Truncat onTokenStreamWr er  mple nts Sc maDocu ntFactory.TokenStreamRewr er {
  pr vate stat c f nal  nt NEVER_TRUNCATE_CHARS_BELOW_POS T ON = 140;
  pr vate stat c f nal Str ng TRUNCATE_LONG_TWEETS_DEC DER_KEY_PREF X =
      "truncate_long_t ets_ n_";
  pr vate stat c f nal Str ng NUM_TWEET_CHARACTERS_SUPPORTED_DEC DER_KEY_PREF X =
      "num_t et_characters_supported_ n_";

  pr vate stat c f nal SearchCounter NUM_TWEETS_TRUNCATED =
      SearchCounter.export("num_t ets_truncated");
  pr vate stat c f nal SearchLongGauge NUM_TWEET_CHARACTERS_SUPPORTED =
      SearchLongGauge.export("num_t et_characters_supported");

  pr vate f nal Dec der dec der;
  pr vate f nal Str ng truncateLongT etsDec derKey;
  pr vate f nal Str ng numCharsSupportedDec derKey;

  /**
   * Creates a Truncat onTokenStreamWr er
   */
  publ c Truncat onTokenStreamWr er(Earlyb rdCluster cluster, Dec der dec der) {
    t .dec der = dec der;

    t .truncateLongT etsDec derKey =
        TRUNCATE_LONG_TWEETS_DEC DER_KEY_PREF X + cluster.na ().toLo rCase();
    t .numCharsSupportedDec derKey =
        NUM_TWEET_CHARACTERS_SUPPORTED_DEC DER_KEY_PREF X + cluster.na ().toLo rCase();
  }

  @Overr de
  publ c Tw terTokenStream rewr e(Sc ma.F eld nfo f eld nfo, Tw terTokenStream stream) {
     f (Earlyb rdF eldConstant.TEXT_F ELD.getF eldNa ().equals(f eld nfo.getNa ())) {
      f nal  nt maxPos  on = getTruncatePos  on();
      NUM_TWEET_CHARACTERS_SUPPORTED.set(maxPos  on);
       f (maxPos  on >= NEVER_TRUNCATE_CHARS_BELOW_POS T ON) {
        return new TokenProcessor(stream) {
          @Overr de
          publ c f nal boolean  ncre ntToken() {
             f ( ncre nt nputStream()) {
               f (offset() < maxPos  on) {
                return true;
              }
              NUM_TWEETS_TRUNCATED. ncre nt();
            }

            return false;
          }
        };
      }
    }

    return stream;
  }

  /**
   * Get t  truncat on pos  on.
   *
   * @return t  truncat on pos  on or -1  f truncat on  s d sabled.
   */
  pr vate  nt getTruncatePos  on() {
     nt maxPos  on;
     f (!Dec derUt l. sAva lableForRandomRec p ent(dec der, truncateLongT etsDec derKey)) {
      return -1;
    }
    maxPos  on = Dec derUt l.getAva lab l y(dec der, numCharsSupportedDec derKey);

     f (maxPos  on < NEVER_TRUNCATE_CHARS_BELOW_POS T ON) {
      // Never truncate below NEVER_TRUNCATE_CHARS_BELOW_POS T ON chars
      maxPos  on = NEVER_TRUNCATE_CHARS_BELOW_POS T ON;
    }

    return maxPos  on;
  }
}
