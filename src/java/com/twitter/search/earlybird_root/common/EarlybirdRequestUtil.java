package com.tw ter.search.earlyb rd_root.common;

 mport com.google.common.base.Opt onal;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.part  on ng.snowflakeparser.Snowflake dParser;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.search.queryparser.ut l. dT  Ranges;

publ c f nal class Earlyb rdRequestUt l {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rdRequestUt l.class);

  pr vate Earlyb rdRequestUt l() {
  }

  /**
   * Returns t  max  D spec f ed  n t  query. T  max  D  s determ ned based on t  max_ d
   * operator, and t  returned value  s an  nclus ve max  D (that  s, t  returned response  s
   * allo d to have a t et w h t   D).
   *
   *  f t  query  s null, could not be parsed or does not have a max_ d operator, Opt onal.absent()
   *  s returned.
   *
   * @param query T  query.
   * @return T  max  D spec f ed  n t  g ven query (based on t  max_ d operator).
   */
  publ c stat c Opt onal<Long> getRequestMax d(Query query) {
     f (query == null) {
      return Opt onal.absent();
    }

     dT  Ranges  dT  Ranges = null;
    try {
       dT  Ranges =  dT  Ranges.fromQuery(query);
    } catch (QueryParserExcept on e) {
      LOG.warn("Except on wh le gett ng max_ d/unt l_t   from query: " + query, e);
    }

     f ( dT  Ranges == null) {
      // An except on was thrown or t  query doesn't accept t  boundary operators.
      return Opt onal.absent();
    }

    return  dT  Ranges.getMax D nclus ve();
  }

  /**
   * Returns t  max  D spec f ed  n t  query, based on t  unt l_t   operator. T  returned  D
   *  s  nclus ve (that  s, t  returned response  s allo d to have a t et w h t   D).
   *
   *  f t  query  s null, could not be parsed or does not have an unt l_t   operator,
   * Opt onal.absent()  s returned.
   *
   * @param query T  query.
   * @return T  max  D spec f ed  n t  g ven query (based on t  unt l_t   operator).
   */
  publ c stat c Opt onal<Long> getRequestMax dFromUnt lT  (Query query) {
     f (query == null) {
      return Opt onal.absent();
    }

     dT  Ranges  dT  Ranges = null;
    try {
       dT  Ranges =  dT  Ranges.fromQuery(query);
    } catch (QueryParserExcept on e) {
      LOG.warn("Except on wh le gett ng max_ d/unt l_t   from query: " + query, e);
    }

     f ( dT  Ranges == null) {
      // An except on was thrown or t  query doesn't accept t  boundary operators.
      return Opt onal.absent();
    }

    Opt onal< nteger> queryUnt lT  Exclus ve =  dT  Ranges.getUnt lT  Exclus ve();
    Opt onal<Long> max d = Opt onal.absent();
     f (queryUnt lT  Exclus ve. sPresent()) {
      long t  stampM ll s = queryUnt lT  Exclus ve.get() * 1000L;
       f (Snowflake dParser. sUsableSnowflakeT  stamp(t  stampM ll s)) {
        // Convert t  stampM ll s to an  D, and subtract 1, because t  unt l_t   operator  s
        // exclus ve, and   need to return an  nclus ve max  D.
        max d = Opt onal.of(Snowflake dParser.generateVal dStatus d(t  stampM ll s, 0) - 1);
      }
    }
    return max d;
  }

  /**
   * Creates a copy of t  g ven Earlyb rdRequest and unsets all f elds that are used
   * only by t  SuperRoot.
   */
  publ c stat c Earlyb rdRequest unsetSuperRootF elds(
      Earlyb rdRequest request, boolean unsetFollo dUser ds) {
    Earlyb rdRequest newRequest = request.deepCopy();
    newRequest.unsetGetOlderResults();
    newRequest.unsetGetProtectedT etsOnly();
     f (unsetFollo dUser ds) {
      newRequest.unsetFollo dUser ds();
    }
    newRequest.unsetAdjustedProtectedRequestParams();
    newRequest.unsetAdjustedFullArch veRequestParams();
    return newRequest;
  }
}
