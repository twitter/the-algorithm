package com.tw ter.search.earlyb rd_root.cach ng;

 mport com.tw ter.search.common.cach ng.Cac ;
 mport com.tw ter.search.common.cach ng.Cac Ut l;
 mport com.tw ter.search.common.cach ng.f lter.Serv cePostProcessor;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;

publ c class RelevanceServ cePostProcessor
    extends Serv cePostProcessor<Earlyb rdRequestContext, Earlyb rdResponse> {
  pr vate f nal Cac <Earlyb rdRequest, Earlyb rdResponse> cac ;

  publ c RelevanceServ cePostProcessor(
      Cac <Earlyb rdRequest, Earlyb rdResponse> cac ) {
    t .cac  = cac ;
  }

  @Overr de
  publ c vo d processServ ceResponse(Earlyb rdRequestContext requestContext,
                                     Earlyb rdResponse serv ceResponse) {
    Cac Ut l.cac Results(cac , requestContext.getRequest(), serv ceResponse,  nteger.MAX_VALUE);
  }
}
