package com.tw ter.search.common.search.term nat on;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common.search.Doc dTracker;
 mport com.tw ter.search.common.search.EarlyTerm nat onState;
 mport com.tw ter.search.common.search.Term nat onTracker;

/**
 * QueryT  out mpl prov des a  thod for early term nat on of quer es based on t  .
 */
publ c class QueryT  out mpl  mple nts QueryT  out {
  pr vate f nal Str ng cl ent d;
  pr vate f nal Term nat onTracker tracker;
  pr vate f nal Clock clock;

  pr vate f nal SearchRateCounter shouldTerm nateCounter;

  publ c QueryT  out mpl(Str ng cl ent d, Term nat onTracker tracker, Clock clock) {
    t .cl ent d = Precond  ons.c ckNotNull(cl ent d);
    t .tracker = Precond  ons.c ckNotNull(tracker);
    t .clock = Precond  ons.c ckNotNull(clock);
    shouldTerm nateCounter =
        SearchRateCounter.export("query_t  out_should_term nate_" + cl ent d);
  }

  /**
   * Returns true w n t  clock's t   has  t or exceeded t  tracker's t  out end.
   */
  publ c boolean shouldEx () {
     f (clock.nowM ll s() >= tracker.getT  outEndT  W hReservat on()) {
      tracker.setEarlyTerm nat onState(EarlyTerm nat onState.TERM NATED_T ME_OUT_EXCEEDED);
      shouldTerm nateCounter. ncre nt();
      return true;
    }
    return false;
  }

  @Overr de
  publ c vo d reg sterDoc dTracker(Doc dTracker doc dTracker) {
    tracker.addDoc dTracker(doc dTracker);
  }

  @Overr de
  publ c Str ng getCl ent d() {
    return cl ent d;
  }

  @Overr de
  publ c  nt hashCode() {
    return cl ent d.hashCode() * 13 + tracker.hashCode();
  }

  @Overr de
  publ c boolean equals(Object obj) {
     f (!(obj  nstanceof QueryT  out mpl)) {
      return false;
    }

    QueryT  out mpl queryT  out = QueryT  out mpl.class.cast(obj);
    return cl ent d.equals(queryT  out.cl ent d) && tracker.equals(queryT  out.tracker);
  }
}
