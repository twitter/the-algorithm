package com.tw ter.search.common.relevance.features;

/**
 * Def nes relevance related constants that are used at both  ngest on t   and
 * earlyb rd scor ng t  .
 */
publ c f nal class RelevanceS gnalConstants {
  // user reputat on
  publ c stat c f nal byte UNSET_REPUTAT ON_SENT NEL = Byte.M N_VALUE;
  publ c stat c f nal byte MAX_REPUTAT ON = 100;
  publ c stat c f nal byte M N_REPUTAT ON = 0;
  // below overall CDF of ~10%, default value for new users,
  // g ven as a goodw ll value  n case    s unset
  publ c stat c f nal byte GOODW LL_REPUTAT ON = 17;

  // text score
  publ c stat c f nal byte UNSET_TEXT_SCORE_SENT NEL = Byte.M N_VALUE;
  // roughly at overall CDF of ~10%, g ven as a goodw ll value  n case    s unset
  publ c stat c f nal byte GOODW LL_TEXT_SCORE = 19;

  pr vate RelevanceS gnalConstants() {
  }

  // c ck w t r t  spec f ed user rep value  s val d
  publ c stat c boolean  sVal dUserReputat on( nt userRep) {
    return userRep != UNSET_REPUTAT ON_SENT NEL
           && userRep >= M N_REPUTAT ON
           && userRep < MAX_REPUTAT ON;
  }
}
