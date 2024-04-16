package com.tw ter.search.common.query;

 mport org.apac .lucene.search.BoostQuery;
 mport org.apac .lucene.search.Query;

/**
 * A class of ut l  es related to query boosts.
 */
publ c f nal class BoostUt ls {
  pr vate BoostUt ls() {
  }

  /**
   * Wraps t  g ven query  nto a BoostQuery,  f {@code boost}  s not equal to 1.0f.
   *
   * @param query T  query.
   * @param boost T  boost.
   * @return  f {@code boost}  s equal to 1.0f, t n {@code query}  s returned; ot rw se,
   *         {@code query}  s wrapped  nto a {@code BoostQuery}  nstance w h t  g ven boost.
   */
  publ c stat c Query maybeWrap nBoostQuery(Query query, float boost) {
     f (boost == 1.0f) {
      return query;
    }
    return new BoostQuery(query, boost);
  }
}
