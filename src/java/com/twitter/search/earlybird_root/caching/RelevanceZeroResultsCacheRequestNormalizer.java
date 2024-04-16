package com.tw ter.search.earlyb rd_root.cach ng;

 mport com.google.common.base.Opt onal;

 mport com.tw ter.search.common.cach ng.Cac Ut l;
 mport com.tw ter.search.common.cach ng.SearchQueryNormal zer;
 mport com.tw ter.search.common.cach ng.f lter.Cac RequestNormal zer;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;

publ c class RelevanceZeroResultsCac RequestNormal zer
    extends Cac RequestNormal zer<Earlyb rdRequestContext, Earlyb rdRequest> {
  @Overr de
  publ c Opt onal<Earlyb rdRequest> normal zeRequest(Earlyb rdRequestContext requestContext) {
    //  f t  query  s not personal zed,    ans that:
    //   - RelevanceCac RequestNormal zer has already normal zed    nto a cac able query.
    //   - RelevanceCac F lter could not f nd a response for t  query  n t  cac .
    //
    // So  f   try to normal ze    re aga n,   w ll succeed, but t n
    // RelevanceZeroResultsCac F lter w ll do t  sa  look up  n t  cac , wh ch w ll aga n
    // result  n a cac  m ss. T re  s no need to do t  look up tw ce, so  f t  query  s not
    // personal zed, return Opt onal.absent().
    //
    //  f t  query  s personal zed, str p all personal zat on f elds and normal ze t  request.
     f (!SearchQueryNormal zer.query sPersonal zed(requestContext.getRequest().getSearchQuery())) {
      return Opt onal.absent();
    }
    return Opt onal.fromNullable(
        Cac Ut l.normal zeRequestForCac (requestContext.getRequest(), true));
  }
}
