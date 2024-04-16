package com.tw ter.search.earlyb rd_root.cach ng;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.cach ng.Cac ;
 mport com.tw ter.search.common.cach ng.TopT etsCac Ut l;
 mport com.tw ter.search.common.cach ng.f lter.Serv cePostProcessor;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;

 mport stat c com.google.common.base.Precond  ons.c ckNotNull;

publ c class TopT etsServ cePostProcessor
    extends Serv cePostProcessor<Earlyb rdRequestContext, Earlyb rdResponse> {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(TopT etsServ cePostProcessor.class);

  publ c stat c f nal  nt CACHE_AGE_ N_MS = 600000;
  publ c stat c f nal  nt NO_RESULT_CACHE_AGE_ N_MS = 300000;

  pr vate f nal Cac <Earlyb rdRequest, Earlyb rdResponse> cac ;

  publ c TopT etsServ cePostProcessor(Cac <Earlyb rdRequest, Earlyb rdResponse> cac ) {
    t .cac  = c ckNotNull(cac );
  }

  @Overr de
  publ c vo d processServ ceResponse(Earlyb rdRequestContext requestContext,
                                     Earlyb rdResponse serv ceResponse) {

    Earlyb rdRequest or g nalRequest = requestContext.getRequest();
    LOG.debug("Wr  ng to top t ets cac . Request: {}, Response: {}",
        or g nalRequest, serv ceResponse);
    TopT etsCac Ut l.cac Results(or g nalRequest,
        serv ceResponse,
        cac ,
        CACHE_AGE_ N_MS,
        NO_RESULT_CACHE_AGE_ N_MS);
  }
}
