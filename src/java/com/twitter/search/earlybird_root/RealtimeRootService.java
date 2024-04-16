package com.tw ter.search.earlyb rd_root;

 mport javax. nject. nject;
 mport javax. nject.Na d;
 mport javax. nject.S ngleton;


 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.mtls.author zat on.server.MtlsServerSess onTrackerF lter;
 mport com.tw ter.search.common.cl entstats.F nagleCl entStatsF lter;
 mport com.tw ter.search.common.root.Logg ngF lter;
 mport com.tw ter.search.common.root.RequestVal dat onF lter;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdServ ce;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdStatusResponse;
 mport com.tw ter.search.earlyb rd_root.cach ng.FacetsCac F lter;
 mport com.tw ter.search.earlyb rd_root.cach ng.RecencyCac F lter;
 mport com.tw ter.search.earlyb rd_root.cach ng.RelevanceCac F lter;
 mport com.tw ter.search.earlyb rd_root.cach ng.RelevanceZeroResultsCac F lter;
 mport com.tw ter.search.earlyb rd_root.cach ng.Str ctRecencyCac F lter;
 mport com.tw ter.search.earlyb rd_root.cach ng.TermStatsCac F lter;
 mport com.tw ter.search.earlyb rd_root.cach ng.TopT etsCac F lter;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.f lters.Cl ent dTrack ngF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.Cl entRequestT  F lter;
 mport com.tw ter.search.earlyb rd_root.f lters.Deadl neT  outStatsF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.DropAllProtectedOperatorF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.Earlyb rdFeatureSc maAnnotateF lter;
 mport com.tw ter.search.earlyb rd_root.f lters. n  al zeRequestContextF lter;
 mport com.tw ter.search.earlyb rd_root.f lters. tadataTrack ngF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.NullcastTrack ngF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.PostCac RequestTypeCountF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.PreCac RequestTypeCountF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.QueryLangStatF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.QueryOperatorStatF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.RequestResultStatsF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.ResponseCodeStatF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.SearchPayloadS zeLocalContextF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.StratoAttr but onCl ent dF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.TopLevelExcept onHandl ngF lter;
 mport com.tw ter.ut l.Future;

@S ngleton
publ c class Realt  RootServ ce  mple nts Earlyb rdServ ce.Serv ce face {

  pr vate f nal Serv ce<Earlyb rdRequest, Earlyb rdResponse> allF ltersAndServ ce;

  @ nject
  publ c Realt  RootServ ce(
      TopLevelExcept onHandl ngF lter topLevelExcept onHandl ngF lter,
      ResponseCodeStatF lter responseCodeStatF lter,
      Logg ngF lter<Earlyb rdRequest, Earlyb rdResponse> logg ngF lter,
      RequestVal dat onF lter<Earlyb rdRequest, Earlyb rdResponse> val dat onF lter,
      MtlsServerSess onTrackerF lter<Earlyb rdRequest, Earlyb rdResponse> mtlsF lter,
      F nagleCl entStatsF lter<Earlyb rdRequest, Earlyb rdResponse> f nagleStatsF lter,
       n  al zeF lter  n  al zeF lter,
       n  al zeRequestContextF lter  n  al zeRequestContextF lter,
      QueryLangStatF lter queryLangStatF lter,
      DropAllProtectedOperatorF lter dropAllProtectedOperatorF lter,
      QueryOperatorStatF lter queryOperatorStatF lter,
      RequestResultStatsF lter requestResultStatsF lter,
      PreCac RequestTypeCountF lter preCac CountF lter,
      RecencyCac F lter recencyCac F lter,
      RelevanceCac F lter relevanceCac F lter,
      RelevanceZeroResultsCac F lter relevanceZeroResultsCac F lter,
      Str ctRecencyCac F lter str ctRecencyCac F lter,
      FacetsCac F lter facetsCac F lter,
      TermStatsCac F lter termStatsCac F lter,
      TopT etsCac F lter topT etsCac F lter,
      PostCac RequestTypeCountF lter postCac CountF lter,
      Cl ent dTrack ngF lter cl ent dTrack ngF lter,
       tadataTrack ngF lter  tadataTrack ngF lter,
      NullcastTrack ngF lter nullcastTrack ngF lter,
      Cl entRequestT  F lter cl entRequestT  F lter,
      Deadl neT  outStatsF lter deadl neT  outStatsF lter,
      Earlyb rdFeatureSc maAnnotateF lter featureSc maAnnotateF lter,
      SearchPayloadS zeLocalContextF lter searchPayloadS zeLocalContextF lter,
      @Na d(ProtectedScatterGat rModule.NAMED_SCATTER_GATHER_SERV CE)
          Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> scatterGat rServ ce,
      StratoAttr but onCl ent dF lter stratoAttr but onCl ent dF lter) {
    t .allF ltersAndServ ce =
        logg ngF lter
            .andT n(topLevelExcept onHandl ngF lter)
            .andT n(stratoAttr but onCl ent dF lter)
            .andT n(cl entRequestT  F lter)
            .andT n(searchPayloadS zeLocalContextF lter)
            .andT n(responseCodeStatF lter)
            .andT n(requestResultStatsF lter)
            .andT n(val dat onF lter)
            .andT n(mtlsF lter)
            .andT n(f nagleStatsF lter)
            .andT n(cl ent dTrack ngF lter)
            .andT n( tadataTrack ngF lter)
            .andT n( n  al zeF lter)
            .andT n( n  al zeRequestContextF lter)
            .andT n(deadl neT  outStatsF lter)
            .andT n(queryLangStatF lter)
            .andT n(nullcastTrack ngF lter)
            .andT n(dropAllProtectedOperatorF lter)
            .andT n(queryOperatorStatF lter)
            .andT n(preCac CountF lter)
            .andT n(recencyCac F lter)
            .andT n(relevanceCac F lter)
            .andT n(relevanceZeroResultsCac F lter)
            .andT n(str ctRecencyCac F lter)
            .andT n(facetsCac F lter)
            .andT n(termStatsCac F lter)
            .andT n(topT etsCac F lter)
            .andT n(postCac CountF lter)
            .andT n(featureSc maAnnotateF lter)
            .andT n(scatterGat rServ ce);
  }

  @Overr de
  publ c Future<Str ng> getNa () {
    return Future.value("realt   root");
  }

  @Overr de
  publ c Future<Earlyb rdStatusResponse> getStatus() {
    throw new UnsupportedOperat onExcept on("not supported");
  }

  @Overr de
  publ c Future<Earlyb rdResponse> search(Earlyb rdRequest request) {
    return allF ltersAndServ ce.apply(request);
  }
}
