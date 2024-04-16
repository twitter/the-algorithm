package com.tw ter.search.earlyb rd.common;

 mport java.ut l.EnumMap;
 mport java.ut l.Map;

 mport scala.Opt on;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.collect.Maps;

 mport com.tw ter.context.Tw terContext;
 mport com.tw ter.context.thr ftscala.V e r;
 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.f nagle.thr ft.Cl ent d;
 mport com.tw ter.f nagle.thr ft.Cl ent d$;
 mport com.tw ter.search.Tw terContextPerm ;
 mport com.tw ter.search.common.constants.thr ftjava.Thr ftQueryS ce;
 mport com.tw ter.search.common.dec der.Dec derUt l;
 mport com.tw ter.search.common.logg ng.RPCLogger;
 mport com.tw ter.search.common. tr cs.Fa lureRat oCounter;
 mport com.tw ter.search.common. tr cs.T  r;
 mport com.tw ter.search.common.ut l.earlyb rd.TermStat st csUt l;
 mport com.tw ter.search.common.ut l.earlyb rd.Thr ftSearchResultUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetF eldRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ft togramSett ngs;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftTermStat st csRequest;

 mport stat c com.tw ter.search.common.ut l.earlyb rd.Earlyb rdResponseUt l
    .responseCons deredFa led;


publ c class Earlyb rdRequestLogger extends RPCLogger {
  protected enum ExtraF elds {
    QUERY_MAX_H TS_TO_PROCESS,
    COLLECTOR_PARAMS_MAX_H TS_TO_PROCESS,
    RELEVANCE_OPT ONS_MAX_H TS_TO_PROCESS,
    NUM_H TS_PROCESSED,
    QUERY_COST,
    CPU_TOTAL,
    QUERY_SOURCE,
    CL ENT_ D,
    F NAGLE_CL ENT_ D
  }

  protected enum ShardOnlyExtraF elds {
    NUM_SEARCHED_SEGMENTS,
    SCOR NG_T ME_NANOS
  }

  protected enum RootOnlyExtraF elds {
    CACH NG_ALLOWED,
    DEBUG_MODE,
    CACHE_H T,
    USER_AGENT,
    // See J RA APPSEC-2303 for  P addresses logg ng
  }

  pr vate stat c f nal Str ng LOG_FULL_REQUEST_DETA LS_ON_ERROR_DEC DER_KEY =
      "log_full_request_deta ls_on_error";
  pr vate stat c f nal Str ng LOG_FULL_REQUEST_DETA LS_RANDOM_FRACT ON_DEC DER_KEY =
      "log_full_request_deta ls_random_fract on";
  pr vate stat c f nal Str ng LOG_FULL_SLOW_REQUEST_DETA LS_RANDOM_FRACT ON_DEC DER_KEY =
      "log_full_slow_request_deta ls_random_fract on";
  pr vate stat c f nal Str ng SLOW_REQUEST_LATENCY_THRESHOLD_MS_DEC DER_KEY =
      "slow_request_latency_threshold_ms";

  pr vate f nal Dec der dec der;
  pr vate f nal boolean enableLogUnknownCl entRequests;

  pr vate stat c f nal Map<Thr ftQueryS ce, Fa lureRat oCounter>
      FA LURE_RAT O_COUNTER_BY_QUERY_SOURCE = preBu ldFa lureRat oCounters();
  pr vate stat c f nal Fa lureRat oCounter NO_QUERY_SOURCE_FA LURE_RAT O_COUNTER =
      new Fa lureRat oCounter("earlyb rd_logger", "query_s ce", "not_set");

  stat c Earlyb rdRequestLogger bu ldForRoot(
      Str ng loggerNa ,  nt latencyWarnThreshold, Dec der dec der) {

    return new Earlyb rdRequestLogger(loggerNa , latencyWarnThreshold,
        dec der, true, RPCLogger.F elds.values(), ExtraF elds.values(),
        RootOnlyExtraF elds.values());
  }

  stat c Earlyb rdRequestLogger bu ldForShard(
      Str ng loggerNa ,  nt latencyWarnThreshold, Dec der dec der) {

    return new Earlyb rdRequestLogger(loggerNa , latencyWarnThreshold,
        dec der, false, RPCLogger.F elds.values(), ExtraF elds.values(),
        ShardOnlyExtraF elds.values());
  }

  @V s bleForTest ng
  Earlyb rdRequestLogger(Str ng loggerNa ,  nt latencyWarnThreshold, Dec der dec der) {
    t (loggerNa , latencyWarnThreshold, dec der, false, RPCLogger.F elds.values(),
        ExtraF elds.values(), RootOnlyExtraF elds.values(), ShardOnlyExtraF elds.values());
  }

  pr vate Earlyb rdRequestLogger(Str ng loggerNa ,  nt latencyWarnThreshold, Dec der dec der,
                                 boolean enableLogUnknownCl entRequests, Enum[]... f eldEnums) {
    super(loggerNa , f eldEnums);
    t .dec der = dec der;
    t .enableLogUnknownCl entRequests = enableLogUnknownCl entRequests;
    setLatencyWarnThreshold(latencyWarnThreshold);
  }

  /**
   * Logs t  g ven earlyb rd request and response.
   *
   * @param request T  earlyb rd request.
   * @param response T  earlyb rd response.
   * @param t  r T  t     took to process t  request.
   */
  publ c vo d logRequest(Earlyb rdRequest request, Earlyb rdResponse response, T  r t  r) {
    try {
      LogEntry entry = newLogEntry();

      setRequestLogEntr es(entry, request);
      setResponseLogEntr es(entry, response);
       f (t  r != null) {
        entry.setF eld(ExtraF elds.CPU_TOTAL, Long.toStr ng(t  r.getElapsedCpuTotal()));
      }

      boolean wasError = response != null && responseCons deredFa led(response.getResponseCode());

      long responseT   = response != null ? response.getResponseT  () : 0L;

      Str ng logL ne = wr eLogL ne(entry, responseT  , wasError);

      // T  code path  s called for pre/post logg ng
      // Prevent sa  request show ng up tw ce by only logg ng on post logg ng
       f (response != null && Dec derUt l. sAva lableForRandomRec p ent(
          dec der, LOG_FULL_REQUEST_DETA LS_RANDOM_FRACT ON_DEC DER_KEY)) {
        Base64RequestResponseForLogg ng.randomRequest(logL ne, request, response).log();
      }

      // Unknown cl ent request logg ng only appl es to pre-logg ng.
       f (enableLogUnknownCl entRequests && response == null) {
        UnknownCl entRequestForLogg ng unknownCl entRequestLogger =
            UnknownCl entRequestForLogg ng.unknownCl entRequest(logL ne, request);
         f (unknownCl entRequestLogger != null) {
          unknownCl entRequestLogger.log();
        }
      }

       f (wasError
          && Dec derUt l. sAva lableForRandomRec p ent(
          dec der, LOG_FULL_REQUEST_DETA LS_ON_ERROR_DEC DER_KEY)) {
        new RequestResponseForLogg ng(request, response).logFa ledRequest();
        Base64RequestResponseForLogg ng.fa ledRequest(logL ne, request, response).log();
      }

      boolean wasSlow = response != null
          && responseT   >= Dec derUt l.getAva lab l y(
              dec der, SLOW_REQUEST_LATENCY_THRESHOLD_MS_DEC DER_KEY);
       f (wasSlow
          && Dec derUt l. sAva lableForRandomRec p ent(
              dec der, LOG_FULL_SLOW_REQUEST_DETA LS_RANDOM_FRACT ON_DEC DER_KEY)) {
        Base64RequestResponseForLogg ng.slowRequest(logL ne, request, response).log();
      }

      Fa lureRat oCounter fa lureRat oCounter =
          FA LURE_RAT O_COUNTER_BY_QUERY_SOURCE.get(request.getQueryS ce());
       f (fa lureRat oCounter != null) {
        fa lureRat oCounter.requestF n s d(!wasError);
      } else {
        NO_QUERY_SOURCE_FA LURE_RAT O_COUNTER.requestF n s d(!wasError);
      }

    } catch (Except on e) {
      LOG.error("Except on bu ld ng log entry ", e);
    }
  }

  pr vate vo d setRequestLogEntr es(LogEntry entry, Earlyb rdRequest request) {
    entry.setF eld(F elds.CL ENT_HOST, request.getCl entHost());
    entry.setF eld(F elds.CL ENT_REQUEST_ D, request.getCl entRequest D());
    entry.setF eld(F elds.REQUEST_TYPE, requestTypeForLog(request));

     f (request. sSetSearchQuery()) {
      Thr ftSearchQuery searchQuery = request.getSearchQuery();
      entry.setF eld(F elds.QUERY, searchQuery.getSer al zedQuery());

       f (searchQuery. sSetMaxH sToProcess()) {
        entry.setF eld(ExtraF elds.QUERY_MAX_H TS_TO_PROCESS,
                        nteger.toStr ng(searchQuery.getMaxH sToProcess()));
      }

       f (searchQuery. sSetCollectorParams()
          && searchQuery.getCollectorParams(). sSetTerm nat onParams()
          && searchQuery.getCollectorParams().getTerm nat onParams(). sSetMaxH sToProcess()) {
        entry.setF eld(ExtraF elds.COLLECTOR_PARAMS_MAX_H TS_TO_PROCESS,
                        nteger.toStr ng(searchQuery.getCollectorParams().getTerm nat onParams()
                                        .getMaxH sToProcess()));
      }

       f (searchQuery. sSetRelevanceOpt ons()
          && searchQuery.getRelevanceOpt ons(). sSetMaxH sToProcess()) {
        entry.setF eld(ExtraF elds.RELEVANCE_OPT ONS_MAX_H TS_TO_PROCESS,
                        nteger.toStr ng(searchQuery.getRelevanceOpt ons().getMaxH sToProcess()));
      }
    }

    entry.setF eld(F elds.NUM_REQUESTED,  nteger.toStr ng(numRequestedForLog(request)));

     f (request. sSetQueryS ce()) {
      entry.setF eld(ExtraF elds.QUERY_SOURCE, request.getQueryS ce().na ());
    }

     f (request. sSetCl ent d()) {
      entry.setF eld(ExtraF elds.CL ENT_ D, request.getCl ent d());
    }

    entry.setF eld(RootOnlyExtraF elds.CACH NG_ALLOWED,
                   Boolean.toStr ng(Earlyb rdRequestUt l. sCach ngAllo d(request)));

    entry.setF eld(RootOnlyExtraF elds.DEBUG_MODE, Byte.toStr ng(request.getDebugMode()));

    Opt on<Cl ent d> cl ent dOpt on = Cl ent d$.MODULE$.current();
     f (cl ent dOpt on. sDef ned()) {
      entry.setF eld(ExtraF elds.F NAGLE_CL ENT_ D, cl ent dOpt on.get().na ());
    }

    setLogEntr esFromTw terContext(entry);
  }

  @V s bleForTest ng
  Opt on<V e r> getTw terContext() {
    return Tw terContext.acqu re(Tw terContextPerm .get()).apply();
  }

  pr vate vo d setLogEntr esFromTw terContext(LogEntry entry) {
    Opt on<V e r> v e rOpt on = getTw terContext();
     f (v e rOpt on.nonEmpty()) {
      V e r v e r = v e rOpt on.get();

       f (v e r.userAgent().nonEmpty()) {
        Str ng userAgent = v e r.userAgent().get();

        //   only replace t  comma  n t  user-agent w h %2C to make   eas ly parseable,
        // spec ally w h command l ne tools l ke cut/sed/awk
        userAgent = userAgent.replace(",", "%2C");

        entry.setF eld(RootOnlyExtraF elds.USER_AGENT, userAgent);
      }
    }
  }

  pr vate vo d setResponseLogEntr es(LogEntry entry, Earlyb rdResponse response) {
     f (response != null) {
      entry.setF eld(F elds.NUM_RETURNED,  nteger.toStr ng(numResultsForLog(response)));
      entry.setF eld(F elds.RESPONSE_CODE, Str ng.valueOf(response.getResponseCode()));
      entry.setF eld(F elds.RESPONSE_T ME_M CROS, Long.toStr ng(response.getResponseT  M cros()));
       f (response. sSetSearchResults()) {
        entry.setF eld(ExtraF elds.NUM_H TS_PROCESSED,
             nteger.toStr ng(response.getSearchResults().getNumH sProcessed()));
        entry.setF eld(ExtraF elds.QUERY_COST,
            Double.toStr ng(response.getSearchResults().getQueryCost()));
         f (response.getSearchResults(). sSetScor ngT  Nanos()) {
          entry.setF eld(ShardOnlyExtraF elds.SCOR NG_T ME_NANOS,
              Long.toStr ng(response.getSearchResults().getScor ngT  Nanos()));
        }
      }
       f (response. sSetCac H ()) {
        entry.setF eld(RootOnlyExtraF elds.CACHE_H T, Str ng.valueOf(response. sCac H ()));
      }
       f (response. sSetNumSearc dSeg nts()) {
        entry.setF eld(ShardOnlyExtraF elds.NUM_SEARCHED_SEGMENTS,
             nteger.toStr ng(response.getNumSearc dSeg nts()));
      }
    }
  }

  pr vate stat c  nt numRequestedForLog(Earlyb rdRequest request) {
     nt num = 0;
     f (request. sSetFacetRequest() && request.getFacetRequest(). sSetFacetF elds()) {
      for (Thr ftFacetF eldRequest f eld : request.getFacetRequest().getFacetF elds()) {
        num += f eld.getNumResults();
      }
    } else  f (request. sSetTermStat st csRequest()) {
      num = request.getTermStat st csRequest().getTermRequestsS ze();
    } else  f (request. sSetSearchQuery()) {
      num =  request.getSearchQuery(). sSetCollectorParams()
          ? request.getSearchQuery().getCollectorParams().getNumResultsToReturn() : 0;
       f (request.getSearchQuery().getSearchStatus dsS ze() > 0) {
        num = Math.max(num, request.getSearchQuery().getSearchStatus dsS ze());
      }
    }
    return num;
  }

  /**
   * Returns t  number of results  n t  g ven response.  f t  response  s a term stats response,
   * t n t  returned value w ll be t  number of term results.  f t  response  s a facet
   * response, t n t  returned value w ll be t  number of facet results. Ot rw se, t  returned
   * value w ll be t  number of search results.
   */
  publ c stat c  nt numResultsForLog(Earlyb rdResponse response) {
     f (response == null) {
      return 0;
    } else  f (response. sSetFacetResults()) {
      return Thr ftSearchResultUt l.numFacetResults(response.getFacetResults());
    } else  f (response. sSetTermStat st csResults()) {
      return response.getTermStat st csResults().getTermResultsS ze();
    } else {
      return Thr ftSearchResultUt l.numResults(response.getSearchResults());
    }
  }

  pr vate stat c Str ng requestTypeForLog(Earlyb rdRequest request) {
    Str ngBu lder requestType = new Str ngBu lder(64);
     f (request. sSetFacetRequest()) {
      requestType.append("FACETS");
       nt numF elds = request.getFacetRequest().getFacetF eldsS ze();
       f (numF elds > 0) {
        // For 1 or 2 f elds, just put t m  n t  request type.  For more, just log t  number.
         f (numF elds <= 2) {
          for (Thr ftFacetF eldRequest f eld : request.getFacetRequest().getFacetF elds()) {
            requestType.append(":").append(f eld.getF eldNa ().toUpperCase());
          }
        } else {
          requestType.append(":MULT -").append(numF elds);
        }
      }
    } else  f (request. sSetTermStat st csRequest()) {
      Thr ftTermStat st csRequest termStatsRequest = request.getTermStat st csRequest();
      requestType.append("TERMSTATS-")
          .append(termStatsRequest.getTermRequestsS ze());

      Thr ft togramSett ngs  toSett ngs = termStatsRequest.get togramSett ngs();
       f ( toSett ngs != null) {
        Str ng b nS zeVal = Str ng.valueOf(TermStat st csUt l.determ neB nS ze( toSett ngs));
        Str ng numB nsVal = Str ng.valueOf( toSett ngs.getNumB ns());
        requestType.append(":NUMB NS-").append(numB nsVal).append(":B NS ZE-").append(b nS zeVal);
      }
    } else  f (request. sSetSearchQuery()) {
      requestType.append("SEARCH:");
      requestType.append(request.getSearchQuery().getRank ngMode().na ());
      // Denote w n a from user  d  s present.
       f (request.getSearchQuery(). sSetFromUser DF lter64()) {
        requestType.append(":NETWORK-")
            .append(request.getSearchQuery().getFromUser DF lter64S ze());
      }
      // Denote w n requ red status  ds are present.
       f (request.getSearchQuery().getSearchStatus dsS ze() > 0) {
        requestType.append(": DS-").append(request.getSearchQuery().getSearchStatus dsS ze());
      }
    }
    return requestType.toStr ng();
  }

  pr vate stat c Map<Thr ftQueryS ce, Fa lureRat oCounter> preBu ldFa lureRat oCounters() {
    Map<Thr ftQueryS ce, Fa lureRat oCounter> counterByQueryS ce =
        new EnumMap<>(Thr ftQueryS ce.class);

    for (Thr ftQueryS ce thr ftQueryS ce : Thr ftQueryS ce.values()) {
      Fa lureRat oCounter counter = new Fa lureRat oCounter("earlyb rd_logger", "query_s ce",
          thr ftQueryS ce.toStr ng());
      counterByQueryS ce.put(thr ftQueryS ce, counter);
    }

    return Maps. mmutableEnumMap(counterByQueryS ce);
  }
}
