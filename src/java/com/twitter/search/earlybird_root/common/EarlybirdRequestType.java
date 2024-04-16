package com.tw ter.search.earlyb rd_root.common;

 mport javax.annotat on.Nonnull;

 mport com.tw ter.search.common.constants.thr ftjava.Thr ftQueryS ce;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchRank ngMode;

/**
 * Earlyb rd roots d st ngu sh t se types of requests and treat t m d fferently.
 */
publ c enum Earlyb rdRequestType {
  FACETS,
  RECENCY,
  RELEVANCE,
  STR CT_RECENCY,
  TERM_STATS,
  TOP_TWEETS;

  /**
   * Returns t  type of t  g ven requests.
   */
  @Nonnull
  publ c stat c Earlyb rdRequestType of(Earlyb rdRequest request) {
     f (request. sSetFacetRequest()) {
      return FACETS;
    } else  f (request. sSetTermStat st csRequest()) {
      return TERM_STATS;
    } else  f (request. sSetSearchQuery() && request.getSearchQuery(). sSetRank ngMode()) {
      Thr ftSearchRank ngMode rank ngMode = request.getSearchQuery().getRank ngMode();
      sw ch (rank ngMode) {
        case RECENCY:
           f (shouldUseStr ctRecency(request)) {
            return STR CT_RECENCY;
          } else {
            return RECENCY;
          }
        case RELEVANCE:
          return RELEVANCE;
        case TOPTWEETS:
          return TOP_TWEETS;
        default:
          throw new  llegalArgu ntExcept on();
      }
    } else {
      throw new UnsupportedOperat onExcept on();
    }
  }

  pr vate stat c boolean shouldUseStr ctRecency(Earlyb rdRequest request) {
    // For now,   dec de to do str ct  rg ng solely based on t  QueryS ce, and only for GN P.
    return request. sSetQueryS ce() && request.getQueryS ce() == Thr ftQueryS ce.GN P;
  }

  pr vate f nal Str ng normal zedNa ;

  Earlyb rdRequestType() {
    t .normal zedNa  = na ().toLo rCase();
  }

  /**
   * Returns t  "normal zed" na  of t  request type, that can be used for stat and dec der
   * na s.
   */
  publ c Str ng getNormal zedNa () {
    return normal zedNa ;
  }
}
