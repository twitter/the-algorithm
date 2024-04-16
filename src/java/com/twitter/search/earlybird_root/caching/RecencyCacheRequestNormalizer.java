package com.tw ter.search.earlyb rd_root.cach ng;

 mport com.google.common.base.Opt onal;

 mport com.tw ter.search.common.cach ng.Cac Ut l;
 mport com.tw ter.search.common.cach ng.f lter.Cac RequestNormal zer;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;

publ c class RecencyCac RequestNormal zer extends
    Cac RequestNormal zer<Earlyb rdRequestContext, Earlyb rdRequest> {
  @Overr de
  publ c Opt onal<Earlyb rdRequest> normal zeRequest(Earlyb rdRequestContext requestContext) {
    return Opt onal.fromNullable(Cac Ut l.normal zeRequestForCac (requestContext.getRequest()));
  }
}
