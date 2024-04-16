package com.tw ter.search.earlyb rd_root.cach ng;

 mport com.google.common.base.Opt onal;
 mport com.google.common.base.Precond  ons;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.cach ng.Cac Ut l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.search.queryparser.ut l. dT  Ranges;

publ c class RecencyAndRelevanceCac PostProcessor extends Earlyb rdCac PostProcessor {

  pr vate stat c f nal Logger LOG =
      LoggerFactory.getLogger(RecencyAndRelevanceCac PostProcessor.class);

  protected Opt onal<Earlyb rdResponse> postProcessCac Response(
      Earlyb rdRequest earlyb rdRequest,
      Earlyb rdResponse earlyb rdResponse, long s nce D, long max D) {
    return Cac Ut l.postProcessCac Result(
        earlyb rdRequest, earlyb rdResponse, s nce D, max D);
  }

  @Overr de
  publ c f nal Opt onal<Earlyb rdResponse> processCac Response(
      Earlyb rdRequestContext requestContext,
      Earlyb rdResponse cac Response) {
    Earlyb rdRequest or g nalRequest = requestContext.getRequest();
    Precond  ons.c ckArgu nt(or g nalRequest. sSetSearchQuery());

     dT  Ranges ranges;
    Query query = requestContext.getParsedQuery();
     f (query != null) {
      try {
        ranges =  dT  Ranges.fromQuery(query);
      } catch (QueryParserExcept on e) {
        LOG.error(
            "Except on w n pars ng s nce and max  Ds. Request: {} Response: {}",
            or g nalRequest,
            cac Response,
            e);
        return Opt onal.absent();
      }
    } else {
      ranges = null;
    }

    Opt onal<Long> s nce D;
    Opt onal<Long> max D;
     f (ranges != null) {
      s nce D = ranges.getS nce DExclus ve();
      max D = ranges.getMax D nclus ve();
    } else {
      s nce D = Opt onal.absent();
      max D = Opt onal.absent();
    }

    return postProcessCac Response(
        or g nalRequest, cac Response, s nce D.or(0L), max D.or(Long.MAX_VALUE));
  }
}
