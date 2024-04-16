package com.tw ter.search.earlyb rd.common;

 mport java.ut l.concurrent.T  Un ;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchMov ngAverage;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.common.query.thr ftjava.CollectorParams;
 mport com.tw ter.search.common.query.thr ftjava.CollectorTerm nat onParams;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchRelevanceOpt ons;

publ c f nal class Earlyb rdRequestUt l {
  // T  logger  s setup to log to a separate set of log f les (request_ nfo) and use an
  // async logger so as to not block t  searc r thread. See search/earlyb rd/conf g/log4j.xml
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rdRequestUt l.class);

  @V s bleForTest ng
  stat c f nal SearchMov ngAverage REQUESTED_NUM_RESULTS_STAT =
      SearchMov ngAverage.export("requested_num_results");

  @V s bleForTest ng
  stat c f nal SearchMov ngAverage REQUESTED_MAX_H TS_TO_PROCESS_STAT =
      SearchMov ngAverage.export("requested_max_h s_to_process");

  @V s bleForTest ng
  stat c f nal SearchMov ngAverage REQUESTED_COLLECTOR_PARAMS_MAX_H TS_TO_PROCESS_STAT =
      SearchMov ngAverage.export("requested_collector_params_max_h s_to_process");

  @V s bleForTest ng
  stat c f nal SearchMov ngAverage REQUESTED_RELEVANCE_OPT ONS_MAX_H TS_TO_PROCESS_STAT =
      SearchMov ngAverage.export("requested_relevance_opt ons_max_h s_to_process");

  @V s bleForTest ng
  stat c f nal SearchCounter REQUESTED_MAX_H TS_TO_PROCESS_ARE_D FFERENT_STAT =
      SearchCounter.export("requested_max_h s_to_process_are_d fferent");

  pr vate stat c f nal SearchRateCounter REQUEST_W TH_MORE_THAN_2K_NUM_RESULTS_STAT =
      SearchRateCounter.export("request_w h_more_than_2k_num_result");
  pr vate stat c f nal SearchRateCounter REQUEST_W TH_MORE_THAN_4K_NUM_RESULTS_STAT =
      SearchRateCounter.export("request_w h_more_than_4k_num_result");

  // Stats for track ng clock skew bet en earlyb rd and t  cl ent-spec f ed request t  stamp.
  @V s bleForTest ng
  publ c stat c f nal SearchT  rStats CL ENT_CLOCK_D FF_ABS =
      SearchT  rStats.export("cl ent_clock_d ff_abs", T  Un .M LL SECONDS, false, true);
  @V s bleForTest ng
  publ c stat c f nal SearchT  rStats CL ENT_CLOCK_D FF_POS =
      SearchT  rStats.export("cl ent_clock_d ff_pos", T  Un .M LL SECONDS, false, true);
  @V s bleForTest ng
  publ c stat c f nal SearchT  rStats CL ENT_CLOCK_D FF_NEG =
      SearchT  rStats.export("cl ent_clock_d ff_neg", T  Un .M LL SECONDS, false, true);
  @V s bleForTest ng
  publ c stat c f nal SearchRateCounter CL ENT_CLOCK_D FF_M SS NG =
      SearchRateCounter.export("cl ent_clock_d ff_m ss ng");

  pr vate stat c f nal  nt MAX_NUM_RESULTS = 4000;
  pr vate stat c f nal  nt OLD_MAX_NUM_RESULTS = 2000;

  pr vate Earlyb rdRequestUt l() {
  }

  /**
   * Logs and f xes so  potent ally excess ve values  n t  g ven request.
   */
  publ c stat c vo d logAndF xExcess veValues(Earlyb rdRequest request) {
    Thr ftSearchQuery searchQuery = request.getSearchQuery();
     f (searchQuery != null) {
       nt maxH sToProcess = 0;
       nt numResultsToReturn = 0;

       f (searchQuery. sSetCollectorParams()) {
        numResultsToReturn = searchQuery.getCollectorParams().getNumResultsToReturn();

         f (searchQuery.getCollectorParams(). sSetTerm nat onParams()) {
          maxH sToProcess =
              searchQuery.getCollectorParams().getTerm nat onParams().getMaxH sToProcess();
        }
      }

       f (maxH sToProcess > 50000) {
        LOG.warn("Excess ve max h s  n " + request.toStr ng());
      }

      //   used to l m  number of results to 2000. T se two counters  lp us track  f   rece ve
      // too many requests w h large number of results set.
      Str ng warn ng ssageTemplate = "Exceed %d num result  n %s";
       f (numResultsToReturn > MAX_NUM_RESULTS) {
        LOG.warn(Str ng.format(warn ng ssageTemplate, MAX_NUM_RESULTS, request.toStr ng()));
        REQUEST_W TH_MORE_THAN_4K_NUM_RESULTS_STAT. ncre nt();
        searchQuery.getCollectorParams().setNumResultsToReturn(MAX_NUM_RESULTS);
      } else  f (numResultsToReturn > OLD_MAX_NUM_RESULTS) {
        LOG.warn(Str ng.format(warn ng ssageTemplate, OLD_MAX_NUM_RESULTS, request.toStr ng()));
        REQUEST_W TH_MORE_THAN_2K_NUM_RESULTS_STAT. ncre nt();
      }

      Thr ftSearchRelevanceOpt ons opt ons = searchQuery.getRelevanceOpt ons();
       f (opt ons != null) {
         f (opt ons.getMaxH sToProcess() > 50000) {
          LOG.warn("Excess ve max h s  n " + request.toStr ng());
        }
      }
    }
  }

  /**
   * Sets {@code request.searchQuery.collectorParams}  f t y are not already set.
   */
  publ c stat c vo d c ckAndSetCollectorParams(Earlyb rdRequest request) {
    Thr ftSearchQuery searchQuery = request.getSearchQuery();
     f (searchQuery == null) {
      return;
    }

     f (!searchQuery. sSetCollectorParams()) {
      searchQuery.setCollectorParams(new CollectorParams());
    }
     f (!searchQuery.getCollectorParams(). sSetNumResultsToReturn()) {
      searchQuery.getCollectorParams().setNumResultsToReturn(searchQuery.getNumResults());
    }
     f (!searchQuery.getCollectorParams(). sSetTerm nat onParams()) {
      CollectorTerm nat onParams term nat onParams = new CollectorTerm nat onParams();
       f (request. sSetT  outMs()) {
        term nat onParams.setT  outMs(request.getT  outMs());
      }
       f (request. sSetMaxQueryCost()) {
        term nat onParams.setMaxQueryCost(request.getMaxQueryCost());
      }
      searchQuery.getCollectorParams().setTerm nat onParams(term nat onParams);
    }
    setMaxH sToProcess(searchQuery);
  }

  // Early b rds w ll only look for maxH sToProcess  n CollectorPara ters.Term nat onPara ters.
  // Pr or y to set  CollectorPara ters.Term nat onPara ters.maxH sToProcess  s
  // 1 Collector para ters
  // 2 RelevancePara ters
  // 3 Thrf Query.maxH sToProcess
  pr vate stat c vo d setMaxH sToProcess(Thr ftSearchQuery thr ftSearchQuery) {
    CollectorTerm nat onParams term nat onParams = thr ftSearchQuery
        .getCollectorParams().getTerm nat onParams();
     f (!term nat onParams. sSetMaxH sToProcess()) {
       f (thr ftSearchQuery. sSetRelevanceOpt ons()
          && thr ftSearchQuery.getRelevanceOpt ons(). sSetMaxH sToProcess()) {
        term nat onParams.setMaxH sToProcess(
            thr ftSearchQuery.getRelevanceOpt ons().getMaxH sToProcess());
      } else {
        term nat onParams.setMaxH sToProcess(thr ftSearchQuery.getMaxH sToProcess());
      }
    }
  }

  /**
   * Creates a copy of t  g ven request and unsets t  b nary f elds to make t  logged l ne for
   * t  request look n cer.
   */
  publ c stat c Earlyb rdRequest copyAndClearUnnecessaryValuesForLogg ng(Earlyb rdRequest request) {
    Earlyb rdRequest cop edRequest = request.deepCopy();

     f (cop edRequest. sSetSearchQuery()) {
      // T se f elds are very large and t  b nary data doesn't play  ll w h formz
      cop edRequest.getSearchQuery().unsetTrustedF lter();
      cop edRequest.getSearchQuery().unsetD rectFollowF lter();
    }

    return cop edRequest;
  }

  /**
   * Updates so  h -related stats based on t  para ters  n t  g ven request.
   */
  publ c stat c vo d updateH sCounters(Earlyb rdRequest request) {
     f ((request == null) || !request. sSetSearchQuery()) {
      return;
    }

    Thr ftSearchQuery searchQuery = request.getSearchQuery();

     f (searchQuery. sSetNumResults()) {
      REQUESTED_NUM_RESULTS_STAT.addSample(searchQuery.getNumResults());
    }

     f (searchQuery. sSetMaxH sToProcess()) {
      REQUESTED_MAX_H TS_TO_PROCESS_STAT.addSample(searchQuery.getMaxH sToProcess());
    }

     nteger collectorParamsMaxH sToProcess = null;
     f (searchQuery. sSetCollectorParams()
        && searchQuery.getCollectorParams(). sSetTerm nat onParams()
        && searchQuery.getCollectorParams().getTerm nat onParams(). sSetMaxH sToProcess()) {
      collectorParamsMaxH sToProcess =
          searchQuery.getCollectorParams().getTerm nat onParams().getMaxH sToProcess();
      REQUESTED_COLLECTOR_PARAMS_MAX_H TS_TO_PROCESS_STAT
          .addSample(collectorParamsMaxH sToProcess);
    }

     nteger relevanceOpt onsMaxH sToProcess = null;
     f (searchQuery. sSetRelevanceOpt ons()
        && searchQuery.getRelevanceOpt ons(). sSetMaxH sToProcess()) {
      relevanceOpt onsMaxH sToProcess = searchQuery.getRelevanceOpt ons().getMaxH sToProcess();
      REQUESTED_RELEVANCE_OPT ONS_MAX_H TS_TO_PROCESS_STAT
          .addSample(relevanceOpt onsMaxH sToProcess);
    }

     f ((collectorParamsMaxH sToProcess != null)
        && (relevanceOpt onsMaxH sToProcess != null)
        && (collectorParamsMaxH sToProcess != relevanceOpt onsMaxH sToProcess)) {
      REQUESTED_MAX_H TS_TO_PROCESS_ARE_D FFERENT_STAT. ncre nt();
    }
  }

  publ c stat c boolean  sCach ngAllo d(Earlyb rdRequest request) {
    return !request. sSetCach ngParams() || request.getCach ngParams(). sCac ();
  }

  /**
   * Track t  clock d fference bet en t  server and  s cl ent's spec f ed request t  .
   * W n t re  s no clock dr ft bet en mach nes, t  w ll record t   nfl ght t   bet en t 
   * server and t  cl ent.
   *
   * @param request t   ncom ng earlyb rd request.
   */
  publ c stat c vo d recordCl entClockD ff(Earlyb rdRequest request) {
     f (request. sSetCl entRequestT  Ms()) {
      f nal long t  D ff = System.currentT  M ll s() - request.getCl entRequestT  Ms();
      f nal long t  D ffAbs = Math.abs(t  D ff);
       f (t  D ff >= 0) {
        CL ENT_CLOCK_D FF_POS.t  r ncre nt(t  D ffAbs);
      } else {
        CL ENT_CLOCK_D FF_NEG.t  r ncre nt(t  D ffAbs);
      }
      CL ENT_CLOCK_D FF_ABS.t  r ncre nt(t  D ffAbs);
    } else {
      CL ENT_CLOCK_D FF_M SS NG. ncre nt();
    }
  }
}
