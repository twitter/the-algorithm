package com.tw ter.search.common.relevance.features;

 mport java.ut l.concurrent.T  Un ;

 mport com.tw ter.search.common.encod ng.features.ByteNormal zer;
 mport com.tw ter.search.common.encod ng.features. ntNormal zer;
 mport com.tw ter.search.common.encod ng.features.Pred ct onScoreNormal zer;

/**
 *  nt value normal zers used to push feature values  nto earlyb rd db. For t 
 * 8-b  feature types, t  class wraps t 
 * com.tw ter.search.common.relevance.features.MutableFeatureNormal zers
 */
publ c f nal class  ntNormal zers {
  pr vate  ntNormal zers() {
  }

  publ c stat c f nal  ntNormal zer LEGACY_NORMAL ZER =
      val -> ByteNormal zer.uns gnedByteTo nt(
          MutableFeatureNormal zers.BYTE_NORMAL ZER.normal ze(val));

  publ c stat c f nal  ntNormal zer SMART_ NTEGER_NORMAL ZER =
      val -> ByteNormal zer.uns gnedByteTo nt(
          MutableFeatureNormal zers.SMART_ NTEGER_NORMAL ZER.normal ze(val));

  // T  PARUS_SCORE feature  s deprecated and  s never set  n    ndexes. Ho ver,   st ll need
  // t  normal zer for now, because so  models do not work properly w h "m ss ng" features, so
  // for now   st ll need to set t  PARUS_SCORE feature to 0.
  publ c stat c f nal  ntNormal zer PARUS_SCORE_NORMAL ZER = val -> 0;

  publ c stat c f nal  ntNormal zer BOOLEAN_NORMAL ZER =
      val -> val == 0 ? 0 : 1;

  publ c stat c f nal  ntNormal zer T MESTAMP_SEC_TO_HR_NORMAL ZER =
      val -> ( nt) T  Un .SECONDS.toH s((long) val);

  publ c stat c f nal Pred ct onScoreNormal zer PRED CT ON_SCORE_NORMAL ZER =
      new Pred ct onScoreNormal zer(3);
}
