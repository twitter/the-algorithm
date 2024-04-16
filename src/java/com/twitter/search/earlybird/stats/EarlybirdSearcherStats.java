package com.tw ter.search.earlyb rd.stats;

 mport java.ut l.EnumMap;
 mport java.ut l.Map;
 mport java.ut l.concurrent.ConcurrentHashMap;
 mport java.ut l.concurrent.T  Un ;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.Search tr cT  rOpt ons;
 mport com.tw ter.search.common. tr cs.SearchStatsRece ver;
 mport com.tw ter.search.common. tr cs.SearchT  r;
 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.common.rank ng.thr ftjava.Thr ftRank ngParams;
 mport com.tw ter.search.common.rank ng.thr ftjava.Thr ftScor ngFunct onType;
 mport com.tw ter.search.earlyb rd.Earlyb rdSearc r;
 mport com.tw ter.search.earlyb rd.common.Cl ent dUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchRelevanceOpt ons;

/**
 * Manages counter and t  r stats for Earlyb rdSearc r.
 */
publ c class Earlyb rdSearc rStats {
  pr vate stat c f nal T  Un  T ME_UN T = T  Un .M CROSECONDS;

  pr vate f nal SearchStatsRece ver earlyb rdServerStatsRece ver;

  publ c f nal SearchCounter thr ftQueryW hSer al zedQuery;
  publ c f nal SearchCounter thr ftQueryW hLuceneQuery;
  publ c f nal SearchCounter thr ftQueryW houtTextQuery;
  publ c f nal SearchCounter addedF lterBadUserRep;
  publ c f nal SearchCounter addedF lterFromUser ds;
  publ c f nal SearchCounter addedF lterT et ds;
  publ c f nal SearchCounter unsetF ltersForSoc alF lterTypeQuery;
  publ c f nal SearchCounter querySpec f cS gnalMapTotalS ze;
  publ c f nal SearchCounter querySpec f cS gnalQuer esUsed;
  publ c f nal SearchCounter querySpec f cS gnalQuer esErased;
  publ c f nal SearchCounter authorSpec f cS gnalMapTotalS ze;
  publ c f nal SearchCounter authorSpec f cS gnalQuer esUsed;
  publ c f nal SearchCounter authorSpec f cS gnalQuer esErased;
  publ c f nal SearchCounter nullcastT etsForceExcluded;
  publ c f nal SearchCounter nullcastUnexpectedResults;
  publ c f nal SearchCounter nullcastUnexpectedQuer es;
  publ c f nal SearchCounter relevanceAnt Gam ngF lterUsed;
  publ c f nal SearchCounter relevanceAnt Gam ngF lterNotRequested;
  publ c f nal SearchCounter relevanceAnt Gam ngF lterSpec f edT etsAndFromUser ds;
  publ c f nal SearchCounter relevanceAnt Gam ngF lterSpec f edT ets;
  publ c f nal SearchCounter relevanceAnt Gam ngF lterSpec f edFromUser ds;
  publ c f nal SearchCounter numCollectorAdjustedM nSearc dStatus D;

  publ c f nal Map<Earlyb rdSearc r.QueryMode, SearchCounter> numRequestsW hBlankQuery;
  pr vate f nal Map<Thr ftScor ngFunct onType, SearchT  rStats> latencyByScor ngFunct onType;
  pr vate f nal Map<Thr ftScor ngFunct onType,
      Map<Str ng, SearchT  rStats>> latencyByScor ngFunct onTypeAndCl ent;
  pr vate f nal Map<Str ng, SearchT  rStats> latencyByTensorflowModel;

  publ c Earlyb rdSearc rStats(SearchStatsRece ver earlyb rdServerStatsRece ver) {
    t .earlyb rdServerStatsRece ver = earlyb rdServerStatsRece ver;

    t .thr ftQueryW hLuceneQuery =
        earlyb rdServerStatsRece ver.getCounter("thr ft_query_w h_lucene_query");
    t .thr ftQueryW hSer al zedQuery =
        earlyb rdServerStatsRece ver.getCounter("thr ft_query_w h_ser al zed_query");
    t .thr ftQueryW houtTextQuery =
        earlyb rdServerStatsRece ver.getCounter("thr ft_query_w hout_text_query");

    t .addedF lterBadUserRep =
        earlyb rdServerStatsRece ver.getCounter("added_f lter_bad_user_rep");
    t .addedF lterFromUser ds =
        earlyb rdServerStatsRece ver.getCounter("added_f lter_from_user_ ds");
    t .addedF lterT et ds =
        earlyb rdServerStatsRece ver.getCounter("added_f lter_t et_ ds");

    t .unsetF ltersForSoc alF lterTypeQuery =
        earlyb rdServerStatsRece ver.getCounter("unset_f lters_for_soc al_f lter_type_query");
    t .querySpec f cS gnalMapTotalS ze =
        earlyb rdServerStatsRece ver.getCounter("query_spec f c_s gnal_map_total_s ze");
    t .querySpec f cS gnalQuer esUsed =
        earlyb rdServerStatsRece ver.getCounter("query_spec f c_s gnal_quer es_used");
    t .querySpec f cS gnalQuer esErased =
        earlyb rdServerStatsRece ver.getCounter("query_spec f c_s gnal_quer es_erased");
    t .authorSpec f cS gnalMapTotalS ze =
        earlyb rdServerStatsRece ver.getCounter("author_spec f c_s gnal_map_total_s ze");
    t .authorSpec f cS gnalQuer esUsed =
        earlyb rdServerStatsRece ver.getCounter("author_spec f c_s gnal_quer es_used");
    t .authorSpec f cS gnalQuer esErased =
        earlyb rdServerStatsRece ver.getCounter("author_spec f c_s gnal_quer es_erased");
    t .nullcastT etsForceExcluded =
        earlyb rdServerStatsRece ver.getCounter("force_excluded_nullcast_result_count");
    t .nullcastUnexpectedResults =
        earlyb rdServerStatsRece ver.getCounter("unexpected_nullcast_result_count");
    t .nullcastUnexpectedQuer es =
        earlyb rdServerStatsRece ver.getCounter("quer es_w h_unexpected_nullcast_results");
    t .numCollectorAdjustedM nSearc dStatus D =
        earlyb rdServerStatsRece ver.getCounter("collector_adjusted_m n_searc d_status_ d");

    t .relevanceAnt Gam ngF lterUsed = earlyb rdServerStatsRece ver
        .getCounter("relevance_ant _gam ng_f lter_used");
    t .relevanceAnt Gam ngF lterNotRequested = earlyb rdServerStatsRece ver
        .getCounter("relevance_ant _gam ng_f lter_not_requested");
    t .relevanceAnt Gam ngF lterSpec f edT etsAndFromUser ds = earlyb rdServerStatsRece ver
        .getCounter("relevance_ant _gam ng_f lter_spec f ed_t ets_and_from_user_ ds");
    t .relevanceAnt Gam ngF lterSpec f edT ets = earlyb rdServerStatsRece ver
        .getCounter("relevance_ant _gam ng_f lter_spec f ed_t ets");
    t .relevanceAnt Gam ngF lterSpec f edFromUser ds = earlyb rdServerStatsRece ver
        .getCounter("relevance_ant _gam ng_f lter_spec f ed_from_user_ ds");

    t .latencyByScor ngFunct onType = new EnumMap<>(Thr ftScor ngFunct onType.class);
    t .latencyByScor ngFunct onTypeAndCl ent = new EnumMap<>(Thr ftScor ngFunct onType.class);
    t .latencyByTensorflowModel = new ConcurrentHashMap<>();

    for (Thr ftScor ngFunct onType type : Thr ftScor ngFunct onType.values()) {
      t .latencyByScor ngFunct onType.put(type, getT  rStatsByNa (getStatsNa ByType(type)));
      t .latencyByScor ngFunct onTypeAndCl ent.put(type, new ConcurrentHashMap<>());
    }

    t .numRequestsW hBlankQuery = new EnumMap<>(Earlyb rdSearc r.QueryMode.class);

    for (Earlyb rdSearc r.QueryMode queryMode : Earlyb rdSearc r.QueryMode.values()) {
      Str ng counterNa  =
          Str ng.format("num_requests_w h_blank_query_%s", queryMode.na ().toLo rCase());

      t .numRequestsW hBlankQuery.put(
          queryMode, earlyb rdServerStatsRece ver.getCounter(counterNa ));
    }
  }

  /**
   * Records t  latency for a request for t  appl cable stats.
   * @param t  r A stopped t  r that t  d t  request.
   * @param request T  request that was t  d.
   */
  publ c vo d recordRelevanceStats(SearchT  r t  r, Earlyb rdRequest request) {
    Precond  ons.c ckNotNull(t  r);
    Precond  ons.c ckNotNull(request);
    Precond  ons.c ckArgu nt(!t  r. sRunn ng());

    Thr ftSearchRelevanceOpt ons relevanceOpt ons = request.getSearchQuery().getRelevanceOpt ons();

    // Only record rank ng searc s w h a set type.
     f (!relevanceOpt ons. sSetRank ngParams()
        || !relevanceOpt ons.getRank ngParams(). sSetType()) {
      return;
    }

    Thr ftRank ngParams rank ngParams = relevanceOpt ons.getRank ngParams();
    Thr ftScor ngFunct onType scor ngFunct onType = rank ngParams.getType();

    latencyByScor ngFunct onType.get(scor ngFunct onType).stoppedT  r ncre nt(t  r);

     f (request.getCl ent d() != null) {
      getT  rStatsByCl ent(scor ngFunct onType, request.getCl ent d())
          .stoppedT  r ncre nt(t  r);
    }

     f (scor ngFunct onType != Thr ftScor ngFunct onType.TENSORFLOW_BASED) {
      return;
    }

    Str ng modelNa  = rank ngParams.getSelectedTensorflowModel();

     f (modelNa  != null) {
      getT  rStatsByTensorflowModel(modelNa ).stoppedT  r ncre nt(t  r);
    }
  }

  /**
   * Creates a search t  r w h opt ons spec f ed by T etsEarlyb rdSearc rStats.
   * @return A new SearchT  r.
   */
  publ c SearchT  r createT  r() {
    return new SearchT  r(new Search tr cT  rOpt ons.Bu lder()
        .w hT  Un (T ME_UN T)
        .bu ld());
  }

  pr vate SearchT  rStats getT  rStatsByCl ent(
      Thr ftScor ngFunct onType type,
      Str ng cl ent d) {
    Map<Str ng, SearchT  rStats> latencyByCl ent = latencyByScor ngFunct onTypeAndCl ent.get(type);

    return latencyByCl ent.compute fAbsent(cl ent d,
        c d -> getT  rStatsByNa (getStatsNa ByCl entAndType(type, c d)));
  }

  pr vate SearchT  rStats getT  rStatsByTensorflowModel(Str ng modelNa ) {
    return latencyByTensorflowModel.compute fAbsent(modelNa ,
        mn -> getT  rStatsByNa (getStatsNa ByTensorflowModel(mn)));
  }

  pr vate SearchT  rStats getT  rStatsByNa (Str ng na ) {
    return earlyb rdServerStatsRece ver.getT  rStats(
        na , T ME_UN T, false, true, false);
  }

  publ c stat c Str ng getStatsNa ByType(Thr ftScor ngFunct onType type) {
    return Str ng.format(
        "search_relevance_scor ng_funct on_%s_requests", type.na ().toLo rCase());
  }

  publ c stat c Str ng getStatsNa ByCl entAndType(
      Thr ftScor ngFunct onType type,
      Str ng cl ent d) {
    return Str ng.format("%s_%s", Cl ent dUt l.formatCl ent d(cl ent d), getStatsNa ByType(type));
  }

  publ c stat c Str ng getStatsNa ByTensorflowModel(Str ng modelNa ) {
    return Str ng.format(
        "model_%s_%s", modelNa , getStatsNa ByType(Thr ftScor ngFunct onType.TENSORFLOW_BASED));
  }
}
