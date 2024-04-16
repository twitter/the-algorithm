package com.tw ter.search.earlyb rd_root;

 mport scala.runt  .AbstractFunct on0;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.f nagle.thr ft.Cl ent d;
 mport com.tw ter.search.common.cach ng.thr ftjava.Cach ngParams;
 mport com.tw ter.search.common.query.thr ftjava.CollectorParams;
 mport com.tw ter.search.common.rank ng.thr ftjava.Thr ftRank ngParams;
 mport com.tw ter.search.common.rank ng.thr ftjava.Thr ftScor ngFunct onType;
 mport com.tw ter.search.common.root.SearchRootWarmup;
 mport com.tw ter.search.common.root.WarmupConf g;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdServ ce;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchRank ngMode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchRelevanceOpt ons;
 mport com.tw ter.ut l.Future;

/**
 * Warm-up log c for Earlyb rd Roots.
 * Sends 60 rounds of requests w h a 1 second t  out bet en each round.
 * T  actual number of requests sent by each round can be conf gured.
 */
publ c class Earlyb rdWarmup extends
    SearchRootWarmup<Earlyb rdServ ce.Serv ce face, Earlyb rdRequest, Earlyb rdResponse> {

  pr vate stat c f nal  nt WARMUP_NUM_RESULTS = 20;

  pr vate stat c f nal Str ng CL ENT_ D = "earlyb rd_root_warmup";

  publ c Earlyb rdWarmup(Clock clock, WarmupConf g conf g) {
    super(clock, conf g);
  }

  @Overr de
  protected Earlyb rdRequest createRequest( nt request d) {
    Str ng query = "(* " + "warmup" + request d + ")";

    return new Earlyb rdRequest()
        .setSearchQuery(
            new Thr ftSearchQuery()
                .setNumResults(WARMUP_NUM_RESULTS)
                .setCollectorParams(
                    new CollectorParams().setNumResultsToReturn(WARMUP_NUM_RESULTS))
                .setRank ngMode(Thr ftSearchRank ngMode.RELEVANCE)
                .setRelevanceOpt ons(new Thr ftSearchRelevanceOpt ons()
                    .setRank ngParams(new Thr ftRank ngParams()
                        .setType(Thr ftScor ngFunct onType.L NEAR)))
                .setSer al zedQuery(query))
        .setCach ngParams(new Cach ngParams().setCac (false))
        .setCl ent d(CL ENT_ D);
  }

  @Overr de
  protected Future<Earlyb rdResponse> callServ ce(
      f nal Earlyb rdServ ce.Serv ce face serv ce,
      f nal Earlyb rdRequest request) {

    return Cl ent d.apply(CL ENT_ D).asCurrent(
        new AbstractFunct on0<Future<Earlyb rdResponse>>() {
          @Overr de
          publ c Future<Earlyb rdResponse> apply() {
            return serv ce.search(request);
          }
        });
  }
}
