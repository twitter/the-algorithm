package com.tw ter.search.common.search;

 mport javax.annotat on.Nonnull;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.search.common. tr cs.SearchCounter;

/**
 * T   s not an enum to allow d fferent clusters to def ne t  r own EarlyTerm nat onStates.
 */
publ c f nal class EarlyTerm nat onState {
  pr vate stat c f nal Str ng STATS_PREF X = "early_term nat on_";

  publ c stat c f nal EarlyTerm nat onState COLLECT NG =
      new EarlyTerm nat onState("no_early_term nat on", false);
  publ c stat c f nal EarlyTerm nat onState TERM NATED_T ME_OUT_EXCEEDED =
      new EarlyTerm nat onState("term nated_t  out_exceeded", true);
  publ c stat c f nal EarlyTerm nat onState TERM NATED_MAX_QUERY_COST_EXCEEDED =
      new EarlyTerm nat onState("term nated_max_query_cost_exceeded", true);
  publ c stat c f nal EarlyTerm nat onState TERM NATED_MAX_H TS_EXCEEDED =
      new EarlyTerm nat onState("term nated_max_h s_exceeded", true);
  publ c stat c f nal EarlyTerm nat onState TERM NATED_NUM_RESULTS_EXCEEDED =
      new EarlyTerm nat onState("term nated_num_results_exceeded", true);


  // T  str ng can be returned as a part of a search response, to tell t  searc r
  // why t  search got early term nated.
  pr vate f nal Str ng term nat onReason;
  pr vate f nal boolean term nated;
  pr vate f nal SearchCounter count;

  publ c EarlyTerm nat onState(@Nonnull Str ng term nat onReason, boolean term nated) {
    t .term nat onReason = Precond  ons.c ckNotNull(term nat onReason);
    t .term nated = term nated;
    count = SearchCounter.export(STATS_PREF X + term nat onReason + "_count");

  }

  publ c boolean  sTerm nated() {
    return term nated;
  }

  publ c Str ng getTerm nat onReason() {
    return term nat onReason;
  }

  publ c vo d  ncre ntCount() {
    count. ncre nt();
  }
}
