package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.Set;

 mport com.google.common.base.Jo ner;

 mport org.apac .thr ft.TExcept on;
 mport org.slf4j.Logger;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.ut l.thr ft.Thr ftUt ls;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.ut l.Future;
 mport com.tw ter.ut l.FutureEventL stener;

/**
 * T  general fra work for earlyb rd root to track sens  ve results.
 */
publ c abstract class Sens  veResultsTrack ngF lter
    extends S mpleF lter<Earlyb rdRequestContext, Earlyb rdResponse> {

  /**
   * T  type na   s used to d st ngu sh d fferent k nds of sens  ve results  n log.
   */
  pr vate f nal Str ng typeNa ;

  /**
   * T  mark  s to control w t r to log expens ve  nformat on.
   */
  pr vate f nal boolean logDeta ls;

  /**
   * Constructor  lps d st ngu sh d fferent sens  ve content trackers.
   * @param typeNa  T  sens  ve content's na  (e.g. nullcast)
   * @param logDeta ls W t r to log deta ls such as ser al zed requests and responses
   */
  publ c Sens  veResultsTrack ngF lter(f nal Str ng typeNa , boolean logDeta ls) {
    super();
    t .typeNa  = typeNa ;
    t .logDeta ls = logDeta ls;
  }

  /**
   * Get t  LOG that t  sens  ve results can wr e to.
   */
  protected abstract Logger getLogger();

  /**
   * T  counter wh ch counts t  number of quer es w h sens  ve results.
   */
  protected abstract SearchCounter getSens  veQueryCounter();

  /**
   * T  counter wh ch counts t  number of sens  ve results.
   */
  protected abstract SearchCounter getSens  veResultsCounter();

  /**
   * T   thod def nes how t  sens  ve results are  dent f ed.
   */
  protected abstract Set<Long> getSens  veResults(
      Earlyb rdRequestContext requestContext,
      Earlyb rdResponse earlyb rdResponse) throws Except on;

  /**
   * Get a set of t ets wh ch should be exclude from t  sens  ve results set.
   */
  protected abstract Set<Long> getExceptedResults(Earlyb rdRequestContext requestContext);

  @Overr de
  publ c f nal Future<Earlyb rdResponse> apply(
      f nal Earlyb rdRequestContext requestContext,
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> serv ce) {
    Future<Earlyb rdResponse> response = serv ce.apply(requestContext);

    response.addEventL stener(new FutureEventL stener<Earlyb rdResponse>() {
      @Overr de
      publ c vo d onSuccess(Earlyb rdResponse earlyb rdResponse) {
        try {
           f (earlyb rdResponse.responseCode == Earlyb rdResponseCode.SUCCESS
              && earlyb rdResponse. sSetSearchResults()
              && requestContext.getParsedQuery() != null) {
            Set<Long> status ds = getSens  veResults(requestContext, earlyb rdResponse);
            Set<Long> excepted ds = getExceptedResults(requestContext);
            status ds.removeAll(excepted ds);

             f (status ds.s ze() > 0) {
              getSens  veQueryCounter(). ncre nt();
              getSens  veResultsCounter().add(status ds.s ze());
              logContent(requestContext, earlyb rdResponse, status ds);
            }
          }
        } catch (Except on e) {
          getLogger().error("Caught except on wh le try ng to log sens  ve results for query: {}",
                            requestContext.getParsedQuery().ser al ze(), e);
        }
      }

      @Overr de
      publ c vo d onFa lure(Throwable cause) {
      }
    });

    return response;
  }

  pr vate vo d logContent(
      f nal Earlyb rdRequestContext requestContext,
      f nal Earlyb rdResponse earlyb rdResponse,
      f nal Set<Long> status ds) {

     f (logDeta ls) {
      Str ng base64Request;
      try {
        base64Request = Thr ftUt ls.toBase64EncodedStr ng(requestContext.getRequest());
      } catch (TExcept on e) {
        base64Request = "Fa led to parse base 64 request";
      }
      getLogger().error("Found " + typeNa 
              + ": {} | "
              + "parsedQuery: {} | "
              + "request: {} | "
              + "base 64 request: {} | "
              + "response: {}",
          Jo ner.on(",").jo n(status ds),
          requestContext.getParsedQuery().ser al ze(),
          requestContext.getRequest(),
          base64Request,
          earlyb rdResponse);
    } else {
      getLogger().error("Found " + typeNa  + ": {} for parsedQuery {}",
          Jo ner.on(",").jo n(status ds),
          requestContext.getParsedQuery().ser al ze());
    }
  }
}
