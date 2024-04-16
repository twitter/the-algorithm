package com.tw ter.search.earlyb rd.search.quer es;

 mport java. o. OExcept on;
 mport java.ut l.Objects;

 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene. ndex.Nu r cDocValues;

 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common.query.F lteredQuery;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserScrubGeoMap;
 mport com.tw ter.search.earlyb rd. ndex.T et DMapper;

/**
 * F lter that can be used w h searc s over geo f eld post ngs l sts  n order to f lter out t ets
 * that have been geo scrubbed. Determ nes  f a t et has been geo scrubbed by compar ng t 
 * t et's  d aga nst t  max scrubbed t et  d for that t et's author, wh ch  s stored  n t 
 * UserScrubGeoMap.
 *
 * See: go/realt  -geo-f lter ng
 */
publ c class UserScrubGeoF lter  mple nts F lteredQuery.Doc dF lterFactory {

  pr vate UserScrubGeoMap userScrubGeoMap;

  pr vate f nal SearchRateCounter totalRequestsUs ngF lterCounter =
      SearchRateCounter.export("user_scrub_geo_f lter_total_requests");

  publ c stat c F lteredQuery.Doc dF lterFactory getDoc dF lterFactory(
      UserScrubGeoMap userScrubGeoMap) {
    return new UserScrubGeoF lter(userScrubGeoMap);
  }

  publ c UserScrubGeoF lter(UserScrubGeoMap userScrubGeoMap) {
    t .userScrubGeoMap = userScrubGeoMap;
    totalRequestsUs ngF lterCounter. ncre nt();
  }

  @Overr de
  publ c F lteredQuery.Doc dF lter getDoc dF lter(LeafReaderContext context) throws  OExcept on {
    // To determ ne  f a g ven doc has been geo scrubbed   need two p eces of  nformat on about t 
    // doc: t  assoc ated t et  d and t  user  d of t  t et's author.   can get t  t et  d
    // from t  T et DMapper for t  seg nt   are currently search ng, and   can get t  user  d
    // of t  t et's author by look ng up t  doc  d  n t  Nu r cDocValues for t 
    // FROM_USER_ D_CSF.
    //
    // W h t   nformat on   can c ck t  UserScrubGeoMap to f nd out  f t  t et has been
    // geo scrubbed and f lter   out accord ngly.
    f nal Earlyb rd ndexSeg ntAtom cReader currTw terReader =
        (Earlyb rd ndexSeg ntAtom cReader) context.reader();
    f nal T et DMapper t et dMapper =
        (T et DMapper) currTw terReader.getSeg ntData().getDoc DToT et DMapper();
    f nal Nu r cDocValues fromUser dDocValues = currTw terReader.getNu r cDocValues(
        Earlyb rdF eldConstant.FROM_USER_ D_CSF.getF eldNa ());
    return (doc d) -> fromUser dDocValues.advanceExact(doc d)
        && !userScrubGeoMap. sT etGeoScrubbed(
            t et dMapper.getT et D(doc d), fromUser dDocValues.longValue());
  }

  @Overr de
  publ c Str ng toStr ng() {
    return "UserScrubGeoF lter";
  }

  @Overr de
  publ c boolean equals(Object obj) {
     f (!(obj  nstanceof UserScrubGeoMap)) {
      return false;
    }

    UserScrubGeoF lter f lter = UserScrubGeoF lter.class.cast(obj);
    // f lters are cons dered equal as long as t y are us ng t  sa  UserScrubGeoMap
    return Objects.equals(userScrubGeoMap, f lter.userScrubGeoMap);
  }

  @Overr de
  publ c  nt hashCode() {
    return userScrubGeoMap == null ? 0 : userScrubGeoMap.hashCode();
  }
}
