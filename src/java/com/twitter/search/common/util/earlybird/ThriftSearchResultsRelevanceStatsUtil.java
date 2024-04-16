package com.tw ter.search.common.ut l.earlyb rd;

 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultsRelevanceStats;

publ c f nal class Thr ftSearchResultsRelevanceStatsUt l {
  pr vate Thr ftSearchResultsRelevanceStatsUt l() { }

  /**
   * Add ng Thr ftSearchResultsRelevanceStats from one set of results onto a base set.
   * Assu s all values are set on both of t   nputs.
   *
   * @param base t  stats to add to.
   * @param delta t  stats to be added.
   */
  publ c stat c vo d addRelevanceStats(Thr ftSearchResultsRelevanceStats base,
                                       Thr ftSearchResultsRelevanceStats delta) {
    base.setNumScored(base.getNumScored() + delta.getNumScored());
    base.setNumSk pped(base.getNumSk pped() + delta.getNumSk pped());
    base.setNumSk ppedForAnt Gam ng(
            base.getNumSk ppedForAnt Gam ng() + delta.getNumSk ppedForAnt Gam ng());
    base.setNumSk ppedForLowReputat on(
            base.getNumSk ppedForLowReputat on() + delta.getNumSk ppedForLowReputat on());
    base.setNumSk ppedForLowTextScore(
            base.getNumSk ppedForLowTextScore() + delta.getNumSk ppedForLowTextScore());
    base.setNumSk ppedForSoc alF lter(
            base.getNumSk ppedForSoc alF lter() + delta.getNumSk ppedForSoc alF lter());
    base.setNumSk ppedForLowF nalScore(
            base.getNumSk ppedForLowF nalScore() + delta.getNumSk ppedForLowF nalScore());
     f (delta.getOldestScoredT etAge nSeconds() > base.getOldestScoredT etAge nSeconds()) {
      base.setOldestScoredT etAge nSeconds(delta.getOldestScoredT etAge nSeconds());
    }

    base.setNumFromD rectFollows(base.getNumFromD rectFollows() + delta.getNumFromD rectFollows());
    base.setNumFromTrustedC rcle(base.getNumFromTrustedC rcle() + delta.getNumFromTrustedC rcle());
    base.setNumRepl es(base.getNumRepl es() + delta.getNumRepl es());
    base.setNumRepl esTrusted(base.getNumRepl esTrusted() + delta.getNumRepl esTrusted());
    base.setNumRepl esOutOfNetwork(
            base.getNumRepl esOutOfNetwork() + delta.getNumRepl esOutOfNetwork());
    base.setNumSelfT ets(base.getNumSelfT ets() + delta.getNumSelfT ets());
    base.setNumW h d a(base.getNumW h d a() + delta.getNumW h d a());
    base.setNumW hNews(base.getNumW hNews() + delta.getNumW hNews());
    base.setNumSpamUser(base.getNumSpamUser() + delta.getNumSpamUser());
    base.setNumOffens ve(base.getNumOffens ve() + delta.getNumOffens ve());
    base.setNumBot(base.getNumBot() + delta.getNumBot());
  }
}
