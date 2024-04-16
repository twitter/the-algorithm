package com.tw ter.search.earlyb rd_root.common;

 mport java.ut l.ArrayL st;
 mport java.ut l.L st;
 mport java.ut l.Set;

 mport javax.annotat on.Nullable;

 mport scala.Opt on;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect. mmutableSet;
 mport com.google.common.collect.Sets;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.context.thr ftscala.V e r;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common.features.thr ft.Thr ftSearchFeatureSc maSpec f er;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;

/**
 * A class that wraps a request and add  onal per-request data that should be passed to serv ces.
 *
 * T  class should be  mmutable. At t  very least,   must be thread-safe.  n pract ce, s nce
 * Earlyb rdRequest  s a mutable Thr ft structure, t  users of t  class need to make sure that
 * once a request  s used to create a RequestContext  nstance,    s not mod f ed.
 *
 *  f t  request needs to be mod f ed, a new RequestContext w h t  mod f ed Earlyb rdRequest
 * should be created.
 */
publ c f nal class Earlyb rdRequestContext {

  pr vate stat c f nal Str ng OVERR DE_T ER_CONF GS_DEC DER_KEY = "use_overr de_t er_conf gs";

  /**
   * Creates a new context w h t  prov ded earlyb rd request, and us ng t  g ven dec der.
   */
  publ c stat c Earlyb rdRequestContext newContext(
      Earlyb rdRequest request,
      SearchDec der dec der,
      Opt on<V e r> tw terContextV e r,
      Clock clock) throws QueryParserExcept on {

    // Try to capture created t   as early as poss ble. For example,   want to account for query
    // pars ng t  .
    long createdT  M ll s = clock.nowM ll s();

    boolean useOverr deT erConf g = dec der. sAva lable(OVERR DE_T ER_CONF GS_DEC DER_KEY);

    Query parsedQuery = QueryPars ngUt ls.getParsedQuery(request);

    return new Earlyb rdRequestContext(
        request,
        parsedQuery,
        useOverr deT erConf g,
        createdT  M ll s,
        tw terContextV e r);
  }

  /**
   *  ntersect on of t  user D and t  flock response, wh ch  s set  n t  follo dUser ds f eld.
   * T   s used for protected cluster.
   */
  publ c stat c Earlyb rdRequestContext newContextW hRestr ctFromUser dF lter64(
      Earlyb rdRequestContext requestContext) {
    Precond  ons.c ckArgu nt(requestContext.getRequest(). sSetFollo dUser ds());

    Earlyb rdRequest request = requestContext.getRequest().deepCopy();
    L st<Long> to ntersect = request.getFollo dUser ds();
    Thr ftSearchQuery searchQuery = request.getSearchQuery();
     f (!searchQuery. sSetFromUser DF lter64()) {
      searchQuery.setFromUser DF lter64(new ArrayL st<>(to ntersect));
    } else {
      Set<Long>  ntersect on = Sets. ntersect on(
          Sets.newHashSet(searchQuery.getFromUser DF lter64()),
          Sets.newHashSet(to ntersect));
      searchQuery.setFromUser DF lter64(new ArrayL st<>( ntersect on));
    }

    return new Earlyb rdRequestContext(requestContext, request, requestContext.getParsedQuery());
  }

  /**
   * Makes an exact copy of t  prov ded request context, by clon ng t  underly ng earlyb rd
   * request.
   */
  publ c stat c Earlyb rdRequestContext copyRequestContext(
      Earlyb rdRequestContext requestContext,
      Query parsedQuery) {
    return new Earlyb rdRequestContext(requestContext, parsedQuery);
  }

  /**
   * Creates a new context w h t  prov ded request, context and reset both t  feature sc mas
   * cac d  n cl ent and t  feature sc mas cac d  n t  local cac .
   */
  publ c stat c Earlyb rdRequestContext newContext(
      Earlyb rdRequest oldRequest,
      Earlyb rdRequestContext oldRequestContext,
      L st<Thr ftSearchFeatureSc maSpec f er> featureSc masAva lable nCac ,
      L st<Thr ftSearchFeatureSc maSpec f er> featureSc masAva lable nCl ent) {
    Earlyb rdRequest request = oldRequest.deepCopy();
    request.getSearchQuery().getResult tadataOpt ons()
        .setFeatureSc masAva lable nCl ent(featureSc masAva lable nCac );

     mmutableSet<Thr ftSearchFeatureSc maSpec f er> featureSc maSetAva lable nCl ent = null;
     f (featureSc masAva lable nCl ent != null) {
      featureSc maSetAva lable nCl ent =  mmutableSet.copyOf(featureSc masAva lable nCl ent);
    }

    return new Earlyb rdRequestContext(
        request,
        Earlyb rdRequestType.of(request),
        oldRequestContext.getParsedQuery(),
        oldRequestContext.useOverr deT erConf g(),
        oldRequestContext.getCreatedT  M ll s(),
        oldRequestContext.getTw terContextV e r(),
        featureSc maSetAva lable nCl ent);
  }

  publ c Earlyb rdRequestContext deepCopy() {
    return new Earlyb rdRequestContext(request.deepCopy(), parsedQuery, useOverr deT erConf g,
        createdT  M ll s, tw terContextV e r);
  }

  pr vate f nal Earlyb rdRequest request;
  // Earlyb rdRequestType should not change for a g ven request. Comput ng   once  re so that  
  // don't need to compute   from t  request every t     want to use  .
  pr vate f nal Earlyb rdRequestType earlyb rdRequestType;
  // T  parsed query match ng t  ser al zed query  n t  request. May be null  f t  request does
  // not conta n a ser al zed query.
  //  f a request's ser al zed query needs to be rewr ten for any reason, a new
  // Earlyb rdRequestContext should be created, w h a new Earlyb rdRequest (w h a new ser al zed
  // query), and a new parsed query (match ng t  new ser al zed query).
  @Nullable
  pr vate f nal Query parsedQuery;
  pr vate f nal boolean useOverr deT erConf g;
  pr vate f nal long createdT  M ll s;
  pr vate f nal Opt on<V e r> tw terContextV e r;

  @Nullable
  pr vate f nal  mmutableSet<Thr ftSearchFeatureSc maSpec f er> featureSc masAva lable nCl ent;

  pr vate Earlyb rdRequestContext(
      Earlyb rdRequest request,
      Query parsedQuery,
      boolean useOverr deT erConf g,
      long createdT  M ll s,
      Opt on<V e r> tw terContextV e r) {
    t (request,
        Earlyb rdRequestType.of(request),
        parsedQuery,
        useOverr deT erConf g,
        createdT  M ll s,
        tw terContextV e r,
        null);
  }

  pr vate Earlyb rdRequestContext(
      Earlyb rdRequest request,
      Earlyb rdRequestType earlyb rdRequestType,
      Query parsedQuery,
      boolean useOverr deT erConf g,
      long createdT  M ll s,
      Opt on<V e r> tw terContextV e r,
      @Nullable  mmutableSet<Thr ftSearchFeatureSc maSpec f er> featureSc masAva lable nCl ent) {
    t .request = Precond  ons.c ckNotNull(request);
    t .earlyb rdRequestType = earlyb rdRequestType;
    t .parsedQuery = parsedQuery;
    t .useOverr deT erConf g = useOverr deT erConf g;
    t .createdT  M ll s = createdT  M ll s;
    t .tw terContextV e r = tw terContextV e r;
    t .featureSc masAva lable nCl ent = featureSc masAva lable nCl ent;
  }

  pr vate Earlyb rdRequestContext(Earlyb rdRequestContext ot rContext, Query ot rParsedQuery) {
    t (ot rContext, ot rContext.getRequest().deepCopy(), ot rParsedQuery);
  }

  pr vate Earlyb rdRequestContext(Earlyb rdRequestContext ot rContext,
                                  Earlyb rdRequest ot rRequest,
                                  Query ot rParsedQuery) {
    t (ot rRequest,
        ot rContext.earlyb rdRequestType,
        ot rParsedQuery,
        ot rContext.useOverr deT erConf g,
        ot rContext.createdT  M ll s,
        ot rContext.tw terContextV e r,
        null);

    Precond  ons.c ckState(request. sSetSearchQuery());
    t .request.getSearchQuery().setSer al zedQuery(ot rParsedQuery.ser al ze());
  }

  publ c Earlyb rdRequest getRequest() {
    return request;
  }

  publ c boolean useOverr deT erConf g() {
    return useOverr deT erConf g;
  }

  publ c Earlyb rdRequestType getEarlyb rdRequestType() {
    return earlyb rdRequestType;
  }

  @Nullable
  publ c Query getParsedQuery() {
    return parsedQuery;
  }

  publ c long getCreatedT  M ll s() {
    return createdT  M ll s;
  }

  publ c Opt on<V e r> getTw terContextV e r() {
    return tw terContextV e r;
  }

  @Nullable
  publ c Set<Thr ftSearchFeatureSc maSpec f er> getFeatureSc masAva lable nCl ent() {
    return featureSc masAva lable nCl ent;
  }
}
