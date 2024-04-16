package com.tw ter.search.earlyb rd_root. rgers;

 mport java.ut l.ArrayL st;
 mport java.ut l.Arrays;
 mport java.ut l.Collect ons;
 mport java.ut l.HashMap;
 mport java.ut l.HashSet;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Set;
 mport java.ut l.concurrent.T  Un ;

 mport com.google.common.collect.Sets;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.logg ng.Debug ssageBu lder;
 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.common.rank ng.thr ftjava.Thr ftFacetRank ngOpt ons;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.common.ut l.earlyb rd.FacetsResultsUt ls;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetCount;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetCount tadata;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetF eldResults;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetResults;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.ut l.Future;

/**
 *  rger class to  rge facets Earlyb rdResponse objects
 */
publ c class FacetResponse rger extends Earlyb rdResponse rger {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(FacetResponse rger.class);

  pr vate stat c f nal SearchT  rStats T MER =
      SearchT  rStats.export(" rge_facets", T  Un .NANOSECONDS, false, true);

  pr vate stat c f nal double SUCCESSFUL_RESPONSE_THRESHOLD = 0.9;
  pr vate f nal Debug ssageBu lder debug ssageBu lder;


  /**
   * Constructor to create t   rger
   */
  publ c FacetResponse rger(Earlyb rdRequestContext requestContext,
                             L st<Future<Earlyb rdResponse>> responses,
                             ResponseAccumulator mode) {
    super(requestContext, responses, mode);
    debug ssageBu lder = response ssageBu lder.getDebug ssageBu lder();
    debug ssageBu lder.verbose("--- Request Rece ved: %s", requestContext.getRequest());
  }

  @Overr de
  protected SearchT  rStats get rgedResponseT  r() {
    return T MER;
  }

  @Overr de
  protected double getDefaultSuccessResponseThreshold() {
    return SUCCESSFUL_RESPONSE_THRESHOLD;
  }

  @Overr de
  protected Earlyb rdResponse  nternal rge(Earlyb rdResponse facetsResponse) {

    f nal Map<Str ng, FacetsResultsUt ls.FacetF eld nfo> facetF eld nfoMap =
        new HashMap<>();
    f nal Set<Long> user DWh el st = new HashSet<>();

    // F rst, parse t  responses and bu ld up   facet  nfo map.
    boolean termStatsF lter ngMode = FacetsResultsUt ls.prepareF eld nfoMap(
        requestContext.getRequest().getFacetRequest(), facetF eld nfoMap);
    //  erate through all futures and get results.
    collectResponsesAndPopulateMap(facetF eld nfoMap, user DWh el st);

    // Next, aggregate t  top facets and update t  blender response.
    facetsResponse
        .setFacetResults(new Thr ftFacetResults()
            .setFacetF elds(new HashMap<>())
            .setUser DWh el st(user DWh el st));

    // keep track of how many facets a user contr buted - t  map gets reset for every f eld
    Map<Long,  nteger> perF eldAnt Gam ngMap = new HashMap<>();

    // t  one  s used for  mages and tw mges
    Map<Long,  nteger>  magesAnt Gam ngMap = new HashMap<>();

    Set<Str ng> tw mgDedupSet = null;

    for (f nal Map.Entry<Str ng, FacetsResultsUt ls.FacetF eld nfo> entry
        : facetF eld nfoMap.entrySet()) {
      // reset for each f eld
      Str ng f eld = entry.getKey();
      f nal Map<Long,  nteger> ant Gam ngMap;
       f (f eld.equals(Earlyb rdF eldConstant. MAGES_FACET)
          || f eld.equals(Earlyb rdF eldConstant.TW MG_FACET)) {
        ant Gam ngMap =  magesAnt Gam ngMap;
      } else {
        perF eldAnt Gam ngMap.clear();
        ant Gam ngMap = perF eldAnt Gam ngMap;
      }

      Thr ftFacetF eldResults results = new Thr ftFacetF eldResults();
      FacetsResultsUt ls.FacetF eld nfo  nfo = entry.getValue();
      results.setTotalCount( nfo.totalCounts);
      results.setTopFacets(new ArrayL st<>());
      FacetsResultsUt ls.f llTopLanguages( nfo, results);
       f ( nfo.topFacets != null && ! nfo.topFacets. sEmpty()) {
        f llFacetF eldResults( nfo, ant Gam ngMap, results);
      }

       f (f eld.equals(Earlyb rdF eldConstant.TW MG_FACET)) {
         f (tw mgDedupSet == null) {
          tw mgDedupSet = Sets.newHashSet();
        }
        FacetsResultsUt ls.dedupTw mgFacet(tw mgDedupSet, results, debug ssageBu lder);
      }

      facetsResponse.getFacetResults().putToFacetF elds(entry.getKey(), results);
    }

     f (!termStatsF lter ngMode) {
      //  n term stats f lter ng mode,  f do ng    re would break term stats f lter ng
      FacetsResultsUt ls. rgeTw mgResults(
          facetsResponse.getFacetResults(),
          Collect ons.<Thr ftFacetCount>reverseOrder(
              FacetsResultsUt ls.getFacetCountComparator(
                  requestContext.getRequest().getFacetRequest())));
    }

    // Update t  numH sProcessed on Thr ftSearchResults.
     nt numH sProcessed = 0;
     nt numPart  onsEarlyTerm nated = 0;
    for (Earlyb rdResponse earlyb rdResponse: accumulatedResponses.getSuccessResponses()) {
      Thr ftSearchResults searchResults = earlyb rdResponse.getSearchResults();
       f (searchResults != null) {
        numH sProcessed += searchResults.getNumH sProcessed();
        numPart  onsEarlyTerm nated += searchResults.getNumPart  onsEarlyTerm nated();
      }
    }
    Thr ftSearchResults searchResults = new Thr ftSearchResults();
    searchResults.setResults(new ArrayL st<>());  // requ red f eld
    searchResults.setNumH sProcessed(numH sProcessed);
    searchResults.setNumPart  onsEarlyTerm nated(numPart  onsEarlyTerm nated);
    facetsResponse.setSearchResults(searchResults);

    LOG.debug("Facets call completed successfully: {}", facetsResponse);

    FacetsResultsUt ls.f xNat vePhotoUrl(facetsResponse);
    return facetsResponse;
  }

  pr vate vo d f llFacetF eldResults(FacetsResultsUt ls.FacetF eld nfo facetF eld nfo,
                                     Map<Long,  nteger> ant Gam ngMap,
                                     Thr ftFacetF eldResults results) {
     nt m n  ghtedCount = 0;
     nt m nS mpleCount = 0;
     nt maxPenaltyCount =  nteger.MAX_VALUE;
    double maxPenaltyCountRat o = 1;
    boolean excludePoss blySens  veFacets = false;
    boolean onlyReturnFacetsW hD splayT et = false;
     nt maxH sPerUser = -1;

    Earlyb rdRequest request = requestContext.getRequest();
     f (request.getFacetRequest() != null) {
      Thr ftFacetRank ngOpt ons rank ngOpt ons = request.getFacetRequest().getFacetRank ngOpt ons();

       f (request.getSearchQuery() != null) {
        maxH sPerUser = request.getSearchQuery().getMaxH sPerUser();
      }

       f (rank ngOpt ons != null) {
        LOG.debug("FacetsResponse rger: Us ng rank ngOpt ons={}", rank ngOpt ons);

         f (rank ngOpt ons. sSetM nCount()) {
          m n  ghtedCount = rank ngOpt ons.getM nCount();
        }
         f (rank ngOpt ons. sSetM nS mpleCount()) {
          m nS mpleCount = rank ngOpt ons.getM nS mpleCount();
        }
         f (rank ngOpt ons. sSetMaxPenaltyCount()) {
          maxPenaltyCount = rank ngOpt ons.getMaxPenaltyCount();
        }
         f (rank ngOpt ons. sSetMaxPenaltyCountRat o()) {
          maxPenaltyCountRat o = rank ngOpt ons.getMaxPenaltyCountRat o();
        }
         f (rank ngOpt ons. sSetExcludePoss blySens  veFacets()) {
          excludePoss blySens  veFacets = rank ngOpt ons. sExcludePoss blySens  veFacets();
        }
         f (rank ngOpt ons. sSetOnlyReturnFacetsW hD splayT et()) {
          onlyReturnFacetsW hD splayT et = rank ngOpt ons. sOnlyReturnFacetsW hD splayT et();
        }
      }
    } else {
      LOG.warn("earlyb rdRequest.getFacetRequest()  s null");
    }

    Thr ftFacetCount[] topFacetsArray = new Thr ftFacetCount[facetF eld nfo.topFacets.s ze()];

    facetF eld nfo.topFacets.values().toArray(topFacetsArray);
    Arrays.sort(topFacetsArray, Collect ons.<Thr ftFacetCount>reverseOrder(
        FacetsResultsUt ls.getFacetCountComparator(request.getFacetRequest())));

     nt numResults = capFacetF eldW dth(facetF eld nfo.f eldRequest.numResults);

     f (topFacetsArray.length < numResults) {
      numResults = topFacetsArray.length;
    }

     nt collected = 0;
    for ( nt   = 0;   < topFacetsArray.length; ++ ) {
      Thr ftFacetCount count = topFacetsArray[ ];

       f (onlyReturnFacetsW hD splayT et
          && (!count. sSet tadata() || !count.get tadata(). sSetStatus d()
              || count.get tadata().getStatus d() == -1)) {
        // status  d must be set
        cont nue;
      }

       f (excludePoss blySens  veFacets && count. sSet tadata()
          && count.get tadata(). sStatusPoss blySens  ve()) {
        // t  d splay t et may be offens ve or NSFW
         f (Debug ssageBu lder.DEBUG_VERBOSE <= debug ssageBu lder.getDebugLevel()) {
          debug ssageBu lder.verbose2("[%d] FacetsResponse rger EXCLUDED: offens ve or NSFW %s, "
                                           + "explanat on: %s",
                                        , facetCountSummary(count),
                                       count.get tadata().getExplanat on());
        }
        cont nue;
      }

      boolean f lterOutUser = false;
       f (maxH sPerUser != -1 && count. sSet tadata()) {
        Thr ftFacetCount tadata  tadata = count.get tadata();
         f (! tadata.dontF lterUser) {
          long tw terUser d =  tadata.getTw terUser d();
           nt numResultsFromUser = 1;
           f (tw terUser d != -1) {
             nteger perUser = ant Gam ngMap.get(tw terUser d);
             f (perUser != null) {
              numResultsFromUser = perUser + 1;
              f lterOutUser = numResultsFromUser > maxH sPerUser;
            }
            ant Gam ngMap.put(tw terUser d, numResultsFromUser);
          }
        }
      }

      // F lter facets those don't  et t  bas c cr er a.
       f (count.getS mpleCount() < m nS mpleCount) {
         f (Debug ssageBu lder.DEBUG_VERBOSE <= debug ssageBu lder.getDebugLevel()) {
          debug ssageBu lder.verbose2(
              "[%d] FacetsResponse rger EXCLUDED: s mpleCount:%d < m nS mpleCount:%d, %s",
               , count.getS mpleCount(), m nS mpleCount, facetCountSummary(count));
        }
        cont nue;
      }
       f (count.get  ghtedCount() < m n  ghtedCount) {
         f (Debug ssageBu lder.DEBUG_VERBOSE <= debug ssageBu lder.getDebugLevel()) {
          debug ssageBu lder.verbose2(
              "[%d] FacetsResponse rger EXCLUDED:   ghtedCount:%d < m n  ghtedCount:%d, %s",
               , count.get  ghtedCount(), m n  ghtedCount, facetCountSummary(count));
        }
        cont nue;
      }
       f (f lterOutUser) {
         f (Debug ssageBu lder.DEBUG_VERBOSE <= debug ssageBu lder.getDebugLevel()) {
          debug ssageBu lder.verbose2(
              "[%d] FacetsResponse rger EXCLUDED: ant Gam ng f lterd user: %d: %s",
               , count.get tadata().getTw terUser d(), facetCountSummary(count));
        }
        cont nue;
      }
       f (count.getPenaltyCount() > maxPenaltyCount) {
         f (Debug ssageBu lder.DEBUG_VERBOSE <= debug ssageBu lder.getDebugLevel()) {
          debug ssageBu lder.verbose2(
              "[%d] FacetsResponse rger EXCLUCED: penaltyCount:%.3f > maxPenaltyCount:%.3f, %s",
               , count.getPenaltyCount(), maxPenaltyCount, facetCountSummary(count));
        }
        cont nue;
      }
       f (((double) count.getPenaltyCount() / count.getS mpleCount()) > maxPenaltyCountRat o) {
         f (Debug ssageBu lder.DEBUG_VERBOSE <= debug ssageBu lder.getDebugLevel()) {
          debug ssageBu lder.verbose2(
              "[%d] FacetsResponse rger EXCLUDED: penaltyCountRat o: %.3f > "
                  + "maxPenaltyCountRat o:%.3f, %s",
               , (double) count.getPenaltyCount() / count.getS mpleCount(), maxPenaltyCountRat o,
              facetCountSummary(count));
        }
        cont nue;
      }
      results.addToTopFacets(count);

      collected++;
       f (collected >= numResults) {
        break;
      }
    }
  }

  pr vate stat c  nt capFacetF eldW dth( nt numResults) {
     nt ret = numResults;
     f (numResults <= 0) {
      // t   n t ory should not be allo d, but for now    ssue t  request w h goodw ll length
      ret = 10;  // default to 10 for future  rge code to term nate correctly
    }
     f (numResults >= 100) {
      ret = 100;
    }
    return ret;
  }

  pr vate stat c Str ng facetCountSummary(f nal Thr ftFacetCount count) {
     f (count. sSet tadata()) {
      return Str ng.format("Label: %s (s:%d, w:%d, p:%d, score:%.2f, s d:%d (%s))",
          count.getFacetLabel(), count.getS mpleCount(), count.get  ghtedCount(),
          count.getPenaltyCount(), count.getScore(), count.get tadata().getStatus d(),
          count.get tadata().getStatusLanguage());
    } else {
      return Str ng.format("Label: %s (s:%d, w:%d, p:%d, score:%.2f)", count.getFacetLabel(),
          count.getS mpleCount(), count.get  ghtedCount(), count.getPenaltyCount(),
          count.getScore());
    }
  }

  //  erate through t  backend responses and f ll up t  FacetF eld nfo map.
  pr vate vo d collectResponsesAndPopulateMap(
      f nal Map<Str ng, FacetsResultsUt ls.FacetF eld nfo> facetF eld nfoMap,
      f nal Set<Long> user DWh el st) {
    // Next,  erate through t  backend responses.
     nt   = 0;
    for (Earlyb rdResponse facetsResponse : accumulatedResponses.getSuccessResponses()) {
       f (facetsResponse. sSetFacetResults()) {
        LOG.debug("Facet response from earlyb rd {}  s {} ",  , facetsResponse.getFacetResults());
         ++;
        Thr ftFacetResults facetResults = facetsResponse.getFacetResults();
         f (facetResults. sSetUser DWh el st()) {
          user DWh el st.addAll(facetResults.getUser DWh el st());
        }
        FacetsResultsUt ls.f llFacetF eld nfo(
            facetResults, facetF eld nfoMap,
            user DWh el st);
      }
    }
    LOG.debug("Earlyb rd facet response total s ze {}",  );
  }
}

