package com.tw ter.search.earlyb rd_root.common;

 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Set;
 mport java.ut l.TreeSet;
 mport java.ut l.concurrent.ConcurrentHashMap;

 mport javax.annotat on.concurrent.ThreadSafe;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect. mmutableL st;
 mport com.google.common.collect.Maps;

 mport org.apac .commons.lang.mutable.Mutable nt;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.features.thr ft.Thr ftSearchFeatureSc ma;
 mport com.tw ter.search.common.features.thr ft.Thr ftSearchFeatureSc maSpec f er;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchRank ngMode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;

@ThreadSafe
publ c class Earlyb rdFeatureSc ma rger {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rdFeatureSc ma rger.class);

  pr vate stat c f nal SearchLongGauge NUM_FEATURE_SCHEMAS_MAP = SearchLongGauge.export(
      "earlyb rd_feature_sc ma_cac d_cnt");

  pr vate class Stats {
    publ c f nal SearchCounter f eldFormatResponses;
    publ c f nal SearchCounter mapFormatResponses;
    publ c f nal SearchCounter mapFormatSavedSc maResponses;
    publ c f nal SearchCounter mapFormatAllDownstreamM ss ngSc ma;
    publ c f nal SearchCounter mapFormatOneDownstreamM ss ngSc ma;
    publ c f nal SearchCounter mapFormatSc maCac dM smatch;
    publ c f nal SearchCounter num nval dRank ngModeRequests;
    publ c f nal SearchCounter numEmptyResponses;

    publ c Stats(Str ng pref x) {
      t .f eldFormatResponses =
          SearchCounter.export(
              "earlyb rd_feature_sc ma_" + pref x + "_f eld_format_feature_responses");
      t .mapFormatResponses =
          SearchCounter.export(
              "earlyb rd_feature_sc ma_" + pref x + "_map_format_feature_responses");
      t .mapFormatSavedSc maResponses =
          SearchCounter.export(
              "earlyb rd_feature_sc ma_" + pref x + "_map_format_feature_saved_sc ma_responses");
      t .mapFormatAllDownstreamM ss ngSc ma =
          SearchCounter.export(
              "earlyb rd_feature_sc ma_" + pref x
                  + "_map_format_feature_all_downstream_m ss ng_sc ma_error");
      t .mapFormatOneDownstreamM ss ngSc ma =
          SearchCounter.export(
              "earlyb rd_feature_sc ma_" + pref x
                  + "_map_format_feature_one_downstream_m ss ng_sc ma_error");
      t .mapFormatSc maCac dM smatch =
          SearchCounter.export(
              "earlyb rd_feature_sc ma_" + pref x
                  + "_map_format_feature_sc ma_cac d_m smatch_error");
      t .num nval dRank ngModeRequests =
          SearchCounter.export(
              "earlyb rd_feature_sc ma_" + pref x + "_num_ nval d_rank ng_mode_requests");
      t .numEmptyResponses =
          SearchCounter.export(
              "earlyb rd_feature_sc ma_" + pref x
                  + "_num_empty_response_w hout_sc ma");
    }
  }

  pr vate f nal ConcurrentHashMap<Thr ftSearchFeatureSc maSpec f er, Thr ftSearchFeatureSc ma>
      featureSc mas = new ConcurrentHashMap<>();
  pr vate f nal ConcurrentHashMap<Str ng, Stats>  rgeStats = new ConcurrentHashMap<>();

  /**
   * Get all ava lable cac  sc ma l st  nd cated by t  sc ma spec f er.
   * @return  dent f ers for all t  cac d sc ma
   */
  publ c L st<Thr ftSearchFeatureSc maSpec f er> getAva lableSc maL st() {
    return  mmutableL st.copyOf(featureSc mas.keySet());
  }

  /**
   *  erate all t  responses and collect and cac  feature sc mas from response.
   * Set t  feature sc ma for t  response  n searchResults  f needed.
   * (T   s done  ns de earlyb rd roots)
   *
   * @param searchResults t  response
   * @param requestContext t  request, wh ch should record t  cl ent cac d feature sc mas
   * @param statPref x t  stats pref x str ng
   * @param successfulResponses all successfull responses from downstream
   */
  publ c vo d collectAndSetFeatureSc ma nResponse(
      Thr ftSearchResults searchResults,
      Earlyb rdRequestContext requestContext,
      Str ng statPref x,
      L st<Earlyb rdResponse> successfulResponses) {
    Stats stats = getOrCreate rgeStat(statPref x);
    Earlyb rdRequest request = requestContext.getRequest();
     f (!request. sSetSearchQuery()
          || !request.getSearchQuery(). sSetResult tadataOpt ons()
          || !request.getSearchQuery().getResult tadataOpt ons(). sReturnSearchResultFeatures()) {
      //  f t  cl ent does not want to get all features  n map format, do not do anyth ng.
      stats.f eldFormatResponses. ncre nt();
      return;
    }

    // F nd t  most occurred sc ma from per- rge responses and return    n t  post- rge
    // response.
    Thr ftSearchFeatureSc maSpec f er sc maMostOccurred = f ndMostOccurredSc ma(
        stats, request, successfulResponses);
     f (sc maMostOccurred == null) {
      return;
    }

    Set<Thr ftSearchFeatureSc maSpec f er> ava lableSc mas nCl ent =
        requestContext.getFeatureSc masAva lable nCl ent();
     f (ava lableSc mas nCl ent != null && ava lableSc mas nCl ent.conta ns(sc maMostOccurred)) {
      // T  cl ent already knows t  sc ma that   used for t  response, so   don't need to
      // send   t  full sc ma, just t  Thr ftSearchFeatureSc maSpec f er.
      Thr ftSearchFeatureSc ma sc ma = new Thr ftSearchFeatureSc ma();
      sc ma.setSc maSpec f er(sc maMostOccurred);
      searchResults.setFeatureSc ma(sc ma);
      stats.mapFormatResponses. ncre nt();
      stats.mapFormatSavedSc maResponses. ncre nt();
    } else {
      Thr ftSearchFeatureSc ma sc ma = featureSc mas.get(sc maMostOccurred);
       f (sc ma != null) {
        Precond  ons.c ckState(sc ma. sSetEntr es());
        Precond  ons.c ckState(sc ma. sSetSc maSpec f er());
        searchResults.setFeatureSc ma(sc ma);
        stats.mapFormatResponses. ncre nt();
      } else {
        stats.mapFormatSc maCac dM smatch. ncre nt();
        LOG.error("T  feature sc ma cac  m sses t  sc ma entry {}   should cac  for {}",
            sc maMostOccurred, request);
      }
    }
  }

  /**
   *  rge t  feature sc ma from each cluster's response and return   to t  cl ent.
   * (T   s done  ns de superroot)
   * @param requestContext t  search request context
   * @param  rgedResponse t   rged result  ns de t  superroot
   * @param realt  Response t  realt   t er resposne
   * @param protectedResponse t  protected t er response
   * @param fullArch veResponse t  full arch ve t er response
   * @param statsPref x
   */
  publ c vo d  rgeFeatureSc maAcrossClusters(
      Earlyb rdRequestContext requestContext,
      Earlyb rdResponse  rgedResponse,
      Str ng statsPref x,
      Earlyb rdResponse realt  Response,
      Earlyb rdResponse protectedResponse,
      Earlyb rdResponse fullArch veResponse) {
    Stats superrootStats = getOrCreate rgeStat(statsPref x);

    // Only try to  rge feature sc ma  f t re are search results.
    Thr ftSearchResults  rgedResults = Precond  ons.c ckNotNull(
         rgedResponse.getSearchResults());
     f ( rgedResults.getResults(). sEmpty()) {
       rgedResults.unsetFeatureSc ma();
      superrootStats.numEmptyResponses. ncre nt();
      return;
    }

    Earlyb rdRequest request = requestContext.getRequest();
     f (!request. sSetSearchQuery()
        || !request.getSearchQuery(). sSetResult tadataOpt ons()
        || !request.getSearchQuery().getResult tadataOpt ons(). sReturnSearchResultFeatures()) {
       rgedResults.unsetFeatureSc ma();

      //  f t  cl ent does not want to get all features  n map format, do not do anyth ng.
      superrootStats.f eldFormatResponses. ncre nt();
      return;
    }
     f (request.getSearchQuery().getRank ngMode() != Thr ftSearchRank ngMode.RELEVANCE
        && request.getSearchQuery().getRank ngMode() != Thr ftSearchRank ngMode.TOPTWEETS
        && request.getSearchQuery().getRank ngMode() != Thr ftSearchRank ngMode.RECENCY) {
       rgedResults.unsetFeatureSc ma();

      // Only RELEVANCE, TOPTWEETS and RECENCY requests m ght need a feature sc ma  n t  response.
      superrootStats.num nval dRank ngModeRequests. ncre nt();
      LOG.warn("Request asked for feature sc ma, but has  ncorrect rank ng mode: {}", request);
      return;
    }
    superrootStats.mapFormatResponses. ncre nt();

    Thr ftSearchFeatureSc ma sc ma = updateReturnSc maForClusterResponse(
        null, realt  Response, request, superrootStats);
    sc ma = updateReturnSc maForClusterResponse(
        sc ma, protectedResponse, request, superrootStats);
    sc ma = updateReturnSc maForClusterResponse(
        sc ma, fullArch veResponse, request, superrootStats);

     f (sc ma != null) {
       f (requestContext.getFeatureSc masAva lable nCl ent() != null
          && requestContext.getFeatureSc masAva lable nCl ent().conta ns(
          sc ma.getSc maSpec f er())) {
         rgedResults.setFeatureSc ma(
            new Thr ftSearchFeatureSc ma().setSc maSpec f er(sc ma.getSc maSpec f er()));
      } else {
         rgedResults.setFeatureSc ma(sc ma);
      }
    } else {
      superrootStats.mapFormatAllDownstreamM ss ngSc ma. ncre nt();
      LOG.error("T  response for request {}  s m ss ng feature sc ma from all clusters", request);
    }
  }

  /**
   * Add t  sc ma to both t  sc ma map and and t  sc ma l st  f    s not t re yet.
   *
   * @param sc ma t  feature sc ma for search results
   */
  pr vate vo d addNewSc ma(Thr ftSearchFeatureSc ma sc ma) {
     f (!sc ma. sSetEntr es()
        || !sc ma. sSetSc maSpec f er()
        || featureSc mas.conta nsKey(sc ma.getSc maSpec f er())) {
      return;
    }

    synchron zed (t ) {
      Str ng oldExportedSc maNa  = null;
       f (!featureSc mas. sEmpty()) {
        oldExportedSc maNa  = getExportSc masNa ();
      }

       f (featureSc mas.put fAbsent(sc ma.getSc maSpec f er(), sc ma) == null) {
        LOG. nfo("Add new feature sc ma {}  nto t  l st", sc ma);
        NUM_FEATURE_SCHEMAS_MAP.set(featureSc mas.s ze());

         f (oldExportedSc maNa  != null) {
          SearchLongGauge.export(oldExportedSc maNa ).reset();
        }
        SearchLongGauge.export(getExportSc masNa ()).set(1);
        LOG. nfo("Expanded feature sc ma: {}",  mmutableL st.copyOf(featureSc mas.keySet()));
      }
    }
  }

  pr vate Str ng getExportSc masNa () {
    Str ngBu lder bu lder = new Str ngBu lder("earlyb rd_feature_sc ma_cac d");
    TreeSet<Str ng> exportedVers ons = new TreeSet<>();

    //   do not need c cksum for exported vars as all cac d sc mas are from t  major y of t 
    // responses.
    featureSc mas.keySet().stream().forEach(key -> exportedVers ons.add(key.getVers on()));
    exportedVers ons.stream().forEach(vers on -> {
      bu lder.append('_');
      bu lder.append(vers on);
    });
    return bu lder.toStr ng();
  }

  // Get t  updated t  feature sc ma based on t  earlyb rd response from t  search cluster.
  // .  f t  ex st ngSc ma  s not null, t  funct on would return t  ex st ng sc ma.  Under t 
  //   s uat on,   would st ll c ck w t r t  feature  n earlyb rd response  s val d.
  // . Ot rw se, t  funct on would extract t  feature sc ma from t  earlyb rd response.
  pr vate Thr ftSearchFeatureSc ma updateReturnSc maForClusterResponse(
      Thr ftSearchFeatureSc ma ex st ngSc ma,
      Earlyb rdResponse clusterResponse,
      Earlyb rdRequest request,
      Stats stats) {
    //  f t re  s no response or search result for t  cluster, do not update returned sc ma.
     f ((clusterResponse == null) || !clusterResponse. sSetSearchResults()) {
      return ex st ngSc ma;
    }
    Thr ftSearchResults results = clusterResponse.getSearchResults();
     f (results.getResults(). sEmpty()) {
      return ex st ngSc ma;
    }

     f (!results. sSetFeatureSc ma() || !results.getFeatureSc ma(). sSetSc maSpec f er()) {
      stats.mapFormatOneDownstreamM ss ngSc ma. ncre nt();
      LOG.error("T  downstream response {}  s m ss ng feature sc ma for request {}",
          clusterResponse, request);
      return ex st ngSc ma;
    }

    Thr ftSearchFeatureSc ma sc ma = results.getFeatureSc ma();

    // Even  f ex st ngSc ma  s already set,   would st ll try to cac  t  returned sc ma.
    //  n t  way, t  next t   earlyb rd roots don't have to send t  full sc ma back aga n.
     f (sc ma. sSetEntr es()) {
      addNewSc ma(sc ma);
    } else  f (featureSc mas.conta nsKey(sc ma.getSc maSpec f er())) {
      stats.mapFormatSavedSc maResponses. ncre nt();
    } else {
      stats.mapFormatSc maCac dM smatch. ncre nt();
      LOG.error(
          "T  feature sc ma cac  m sses t  sc ma entry {},   should cac  {}  n {}",
          sc ma.getSc maSpec f er(), request, clusterResponse);
    }

    Thr ftSearchFeatureSc ma updatedSc ma = ex st ngSc ma;
     f (updatedSc ma == null) {
      updatedSc ma = featureSc mas.get(sc ma.getSc maSpec f er());
       f (updatedSc ma != null) {
        Precond  ons.c ckState(updatedSc ma. sSetEntr es());
        Precond  ons.c ckState(updatedSc ma. sSetSc maSpec f er());
      }
    }
    return updatedSc ma;
  }

  pr vate Thr ftSearchFeatureSc maSpec f er f ndMostOccurredSc ma(
      Stats stats,
      Earlyb rdRequest request,
      L st<Earlyb rdResponse> successfulResponses) {
    boolean hasResults = false;
    Map<Thr ftSearchFeatureSc maSpec f er, Mutable nt> sc maCount =
        Maps.newHashMapW hExpectedS ze(successfulResponses.s ze());
    for (Earlyb rdResponse response : successfulResponses) {
       f (!response. sSetSearchResults()
          || response.getSearchResults().getResultsS ze() == 0) {
        cont nue;
      }

      hasResults = true;
       f (response.getSearchResults(). sSetFeatureSc ma()) {
        Thr ftSearchFeatureSc ma sc ma = response.getSearchResults().getFeatureSc ma();
         f (sc ma. sSetSc maSpec f er()) {
          Mutable nt cnt = sc maCount.get(sc ma.getSc maSpec f er());
           f (cnt != null) {
            cnt. ncre nt();
          } else {
            sc maCount.put(sc ma.getSc maSpec f er(), new Mutable nt(1));
          }

           f (sc ma. sSetEntr es()) {
            addNewSc ma(sc ma);
          }
        }
      } else {
        stats.mapFormatOneDownstreamM ss ngSc ma. ncre nt();
        LOG.error("T  downstream response {}  s m ss ng feature sc ma for request {}",
            response, request);
      }
    }

     nt numMostOccurred = 0;
    Thr ftSearchFeatureSc maSpec f er sc maMostOccurred = null;
    for (Map.Entry<Thr ftSearchFeatureSc maSpec f er, Mutable nt> entry : sc maCount.entrySet()) {
       f (entry.getValue().to nteger() > numMostOccurred) {
        numMostOccurred = entry.getValue().to nteger();
        sc maMostOccurred = entry.getKey();
      }
    }

     f (sc maMostOccurred == null && hasResults) {
      stats.mapFormatAllDownstreamM ss ngSc ma. ncre nt();
      LOG.error("None of t  downstream host returned feature sc ma for {}", request);
    }
    return sc maMostOccurred;
  }

  pr vate Stats getOrCreate rgeStat(Str ng statPref x) {
    Stats stats =  rgeStats.get(statPref x);
     f (stats == null) {
      Stats newStats = new Stats(statPref x);
      stats =  rgeStats.put fAbsent(statPref x, newStats);
       f (stats == null) {
        stats = newStats;
      }
    }
    return stats;
  }
}
