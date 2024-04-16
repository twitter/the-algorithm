package com.tw ter.search.common.relevance.features;

 mport com.tw ter.search.common.encod ng.features.EncodedFeatures;

/**
 * Holds engage nt features for a part cular t et and encodes t m as a s ngle  nt.
 * T  features are: ret et count, favor e count,   et score, reply count.
 */
publ c class T etEngage ntFeatures extends EncodedFeatures {
  pr vate stat c f nal  nt RETWEET_COUNT_B T_SH FT = 0;
  pr vate stat c f nal long RETWEET_COUNT_ NVERSE_B T_MASK =  0xffffff00L;

  pr vate stat c f nal  nt  TWEET_SCORE_B T_SH FT = 8;
  pr vate stat c f nal long  TWEET_SCORE_ NVERSE_B T_MASK = 0xffff00ffL;

  pr vate stat c f nal  nt FAV_COUNT_B T_SH FT = 16;
  pr vate stat c f nal long FAV_COUNT_ NVERSE_B T_MASK =    0xff00ffffL;

  pr vate stat c f nal  nt REPLY_COUNT_B T_SH FT = 24;
  pr vate stat c f nal long REPLY_COUNT_ NVERSE_B T_MASK =    0x00ffffffL;

  publ c T etEngage ntFeatures setRet etCount(byte count) {
    setByte fGreater(count, RETWEET_COUNT_B T_SH FT, RETWEET_COUNT_ NVERSE_B T_MASK);
    return t ;
  }

  publ c  nt getRet etCount() {
    return getByte(RETWEET_COUNT_B T_SH FT);
  }

  publ c T etEngage ntFeatures set T etScore(byte score) {
    setByte fGreater(score,  TWEET_SCORE_B T_SH FT,  TWEET_SCORE_ NVERSE_B T_MASK);
    return t ;
  }

  publ c  nt get T etScore() {
    return getByte( TWEET_SCORE_B T_SH FT);
  }

  publ c T etEngage ntFeatures setFavCount(byte count) {
    setByte fGreater(count, FAV_COUNT_B T_SH FT, FAV_COUNT_ NVERSE_B T_MASK);
    return t ;
  }

  publ c  nt getFavCount() {
    return getByte(FAV_COUNT_B T_SH FT);
  }

  publ c T etEngage ntFeatures setReplyCount(byte count) {
    setByte fGreater(count, REPLY_COUNT_B T_SH FT, REPLY_COUNT_ NVERSE_B T_MASK);
    return t ;
  }

  publ c  nt getReplyCount() {
    return getByte(REPLY_COUNT_B T_SH FT);
  }
}
