package com.tw ter.search.earlyb rd_root;

 mport javax. nject. nject;
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
 mport com.tw ter.search.earlyb rd_root.f lters.Cl ent dArch veAccessF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.Cl ent dQuotaF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.Cl ent dTrack ngF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.Cl entRequestT  F lter;
 mport com.tw ter.search.earlyb rd_root.f lters.Deadl neT  outStatsF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.D sableCl entByT erF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.Earlyb rdFeatureSc maAnnotateF lter;
 mport com.tw ter.search.earlyb rd_root.f lters. n  al zeRequestContextF lter;
 mport com.tw ter.search.earlyb rd_root.f lters. tadataTrack ngF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.Na dMult TermD sjunct onStatsF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.NullcastTrack ngF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.PreCac RequestTypeCountF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.QueryLangStatF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.QueryOperatorStatF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.QueryToken zerF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.RequestResultStatsF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.RequestSuccessStatsF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.ResponseCodeStatF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.SearchPayloadS zeLocalContextF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.RejectRequestsByQueryS ceF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.StratoAttr but onCl ent dF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.TopLevelExcept onHandl ngF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.VeryRecentT etsF lter;
 mport com.tw ter.ut l.Future;

@S ngleton
class SuperRootServ ce  mple nts Earlyb rdServ ce.Serv ce face {
  pr vate f nal Serv ce<Earlyb rdRequest, Earlyb rdResponse> fullSearch thod;

  @ nject
  publ c SuperRootServ ce(
      TopLevelExcept onHandl ngF lter topLevelExcept onHandl ngF lter,
      ResponseCodeStatF lter responseCodeStatF lter,
      Logg ngF lter<Earlyb rdRequest, Earlyb rdResponse> logg ngF lter,
      Na dMult TermD sjunct onStatsF lter na dMult TermD sjunct onStatsF lter,
      RequestVal dat onF lter<Earlyb rdRequest, Earlyb rdResponse> val dat onF lter,
      MtlsServerSess onTrackerF lter<Earlyb rdRequest, Earlyb rdResponse> mtlsF lter,
      F nagleCl entStatsF lter<Earlyb rdRequest, Earlyb rdResponse> f nagleStatsF lter,
       n  al zeF lter  n  al zeF lter,
       n  al zeRequestContextF lter  n  al zeRequestContextF lter,
      QueryLangStatF lter queryLangStatF lter,
      QueryOperatorStatF lter queryOperatorStatF lter,
      RequestResultStatsF lter requestResultStatsF lter,
      PreCac RequestTypeCountF lter preCac RequestTypeCountF lter,
      Cl ent dArch veAccessF lter cl ent dArch veAccessF lter,
      D sableCl entByT erF lter d sableCl entByT erF lter,
      Cl ent dTrack ngF lter cl ent dTrack ngF lter,
      Cl ent dQuotaF lter quotaF lter,
      RejectRequestsByQueryS ceF lter rejectRequestsByQueryS ceF lter,
       tadataTrack ngF lter  tadataTrack ngF lter,
      VeryRecentT etsF lter veryRecentT etsF lter,
      RequestSuccessStatsF lter requestSuccessStatsF lter,
      NullcastTrack ngF lter nullcastTrack ngF lter,
      QueryToken zerF lter queryToken zerF lter,
      Cl entRequestT  F lter cl entRequestT  F lter,
      Deadl neT  outStatsF lter deadl neT  outStatsF lter,
      SuperRootRequestTypeRouter superRootSearchServ ce,
      Earlyb rdFeatureSc maAnnotateF lter featureSc maAnnotateF lter,
      SearchPayloadS zeLocalContextF lter searchPayloadS zeLocalContextF lter,
      StratoAttr but onCl ent dF lter stratoAttr but onCl ent dF lter) {
    t .fullSearch thod =
        logg ngF lter
            .andT n(topLevelExcept onHandl ngF lter)
            .andT n(stratoAttr but onCl ent dF lter)
            .andT n(cl entRequestT  F lter)
            .andT n(searchPayloadS zeLocalContextF lter)
            .andT n(requestSuccessStatsF lter)
            .andT n(requestResultStatsF lter)
            .andT n(responseCodeStatF lter)
            .andT n(val dat onF lter)
            .andT n(mtlsF lter)
            .andT n(f nagleStatsF lter)
            .andT n(d sableCl entByT erF lter)
            .andT n(cl ent dTrack ngF lter)
            .andT n(quotaF lter)
            .andT n(cl ent dArch veAccessF lter)
            .andT n(rejectRequestsByQueryS ceF lter)
            .andT n(na dMult TermD sjunct onStatsF lter)
            .andT n( tadataTrack ngF lter)
            .andT n(veryRecentT etsF lter)
            .andT n( n  al zeF lter)
            .andT n( n  al zeRequestContextF lter)
            .andT n(deadl neT  outStatsF lter)
            .andT n(queryLangStatF lter)
            .andT n(nullcastTrack ngF lter)
            .andT n(queryOperatorStatF lter)
            .andT n(preCac RequestTypeCountF lter)
            .andT n(queryToken zerF lter)
            .andT n(featureSc maAnnotateF lter)
            .andT n(superRootSearchServ ce);
  }

  @Overr de
  publ c Future<Str ng> getNa () {
    return Future.value("superroot");
  }

  @Overr de
  publ c Future<Earlyb rdStatusResponse> getStatus() {
    throw new UnsupportedOperat onExcept on("not supported");
  }

  @Overr de
  publ c Future<Earlyb rdResponse> search(Earlyb rdRequest request) {
    return fullSearch thod.apply(request);
  }
}
