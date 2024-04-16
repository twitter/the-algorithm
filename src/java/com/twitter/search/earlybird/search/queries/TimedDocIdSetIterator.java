package com.tw ter.search.earlyb rd.search.quer es;

 mport java. o. OExcept on;
 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport org.apac .lucene.search.Doc dSet erator;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.search.EarlyTerm nat onState;
 mport com.tw ter.search.common.search.Term nat onTracker;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;

/**
 * Doc dSet erator whose nextDoc() and advance() w ll early term nate by return ng NO_MORE_DOCS
 * after t  g ven deadl ne.
 */
publ c class T  dDoc dSet erator extends Doc dSet erator {
  // c ck deadl ne every NEXT_CALL_T MEOUT_CHECK_PER OD calls to nextDoc()
  @V s bleForTest ng
  protected stat c f nal  nt NEXT_CALL_T MEOUT_CHECK_PER OD =
      Earlyb rdConf g.get nt("t  d_doc_ d_set_next_doc_deadl ne_c ck_per od", 1000);


  // c ck deadl ne every ADVANCE_CALL_T MEOUT_CHECK_PER OD calls to advance()
  pr vate stat c f nal  nt ADVANCE_CALL_T MEOUT_CHECK_PER OD =
      Earlyb rdConf g.get nt("t  d_doc_ d_set_advance_deadl ne_c ck_per od", 100);

  pr vate f nal Clock clock;
  pr vate f nal Doc dSet erator  nner erator;
  pr vate f nal SearchCounter t  outCountStat;

  @Nullable
  pr vate f nal Term nat onTracker term nat onTracker;
  pr vate f nal long deadl neM ll sFromEpoch;

  pr vate  nt doc d = -1;
  pr vate  nt nextCounter = 0;
  pr vate  nt advanceCounter = 0;

  publ c T  dDoc dSet erator(Doc dSet erator  nner erator,
                               @Nullable Term nat onTracker term nat onTracker,
                               f nal long t  outOverr de,
                               @Nullable SearchCounter t  outCountStat) {
    t ( nner erator, term nat onTracker, t  outOverr de, t  outCountStat, Clock.SYSTEM_CLOCK);
  }

  protected T  dDoc dSet erator(Doc dSet erator  nner erator,
                                  @Nullable Term nat onTracker term nat onTracker,
                                  f nal long t  outOverr de,
                                  @Nullable SearchCounter t  outCountStat,
                                  Clock clock) {
    t .clock = clock;
    t . nner erator =  nner erator;
    t .t  outCountStat = t  outCountStat;
    t .term nat onTracker = term nat onTracker;

     f (term nat onTracker == null) {
      deadl neM ll sFromEpoch = -1;
    } else {
       f (t  outOverr de > 0) {
        deadl neM ll sFromEpoch = term nat onTracker.getCl entStartT  M ll s() + t  outOverr de;
      } else {
        deadl neM ll sFromEpoch = term nat onTracker.getT  outEndT  W hReservat on();
      }
    }
  }

  @V s bleForTest ng
  protected T  dDoc dSet erator(Doc dSet erator  nner erator,
          f nal long deadl ne,
          @Nullable SearchCounter t  outCountStat,
          Clock clock) {
    t .clock = clock;
    t . nner erator =  nner erator;
    t .t  outCountStat = t  outCountStat;
    t .term nat onTracker = null;

    t .deadl neM ll sFromEpoch = deadl ne;
  }


  @Overr de
  publ c  nt doc D() {
    return doc d;
  }

  @Overr de
  publ c  nt nextDoc() throws  OExcept on {
     f (++nextCounter % NEXT_CALL_T MEOUT_CHECK_PER OD == 0
        && clock.nowM ll s() > deadl neM ll sFromEpoch) {
       f (t  outCountStat != null) {
        t  outCountStat. ncre nt();
      }
       f (term nat onTracker != null) {
        term nat onTracker.setEarlyTerm nat onState(
            EarlyTerm nat onState.TERM NATED_T ME_OUT_EXCEEDED);
      }

      return doc d = NO_MORE_DOCS;
    }
    return doc d =  nner erator.nextDoc();
  }

  @Overr de
  publ c  nt advance( nt target) throws  OExcept on {
     f (++advanceCounter % ADVANCE_CALL_T MEOUT_CHECK_PER OD == 0
        && clock.nowM ll s() > deadl neM ll sFromEpoch) {
       f (t  outCountStat != null) {
        t  outCountStat. ncre nt();
      }
       f (term nat onTracker != null) {
        term nat onTracker.setEarlyTerm nat onState(
            EarlyTerm nat onState.TERM NATED_T ME_OUT_EXCEEDED);
      }
      return doc d = NO_MORE_DOCS;
    }

    return doc d =  nner erator.advance(target);
  }

  @Overr de
  publ c long cost() {
    return  nner erator.cost();
  }
}
