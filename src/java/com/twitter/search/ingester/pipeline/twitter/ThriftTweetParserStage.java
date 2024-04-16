package com.tw ter.search. ngester.p pel ne.tw ter;

 mport java.ut l.L st;
 mport java.ut l.Map;
 mport javax.annotat on.Nonnull;
 mport javax.annotat on.Nullable;
 mport javax.nam ng.Nam ngExcept on;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Maps;

 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .commons.p pel ne.val dat on.Consu dTypes;
 mport org.apac .commons.p pel ne.val dat on.ProducedTypes;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on;
 mport com.tw ter.search.common.debug.thr ftjava.DebugEvents;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssage;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search. ngester.model. ngesterT etEvent;
 mport com.tw ter.search. ngester.model. ngesterTw ter ssage;
 mport com.tw ter.search. ngester.p pel ne.tw ter.thr ftparse.Thr ftT etPars ngExcept on;
 mport com.tw ter.search. ngester.p pel ne.tw ter.thr ftparse.T etEventParse lper;
 mport com.tw ter.t etyp e.thr ftjava.T etCreateEvent;
 mport com.tw ter.t etyp e.thr ftjava.T etDeleteEvent;
 mport com.tw ter.t etyp e.thr ftjava.T etEventData;

@Consu dTypes( ngesterT etEvent.class)
@ProducedTypes( ngesterTw ter ssage.class)
publ c class Thr ftT etParserStage extends Tw terBaseStage< ngesterT etEvent, Tw ter ssage> {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Thr ftT etParserStage.class);

  // T etEventData  s a un on of all poss ble t et event types. T etEventData._F elds  s an enum
  // that corresponds to t  f elds  n that un on. So essent ally, T etEventData._F elds tells us
  // wh ch t et event  're gett ng  ns de T etEventData.   want to keep track of how many t et
  // events of each type  're gett ng.
  pr vate f nal Map<T etEventData._F elds, SearchCounter> t etEventCounters =
      Maps.newEnumMap(T etEventData._F elds.class);

  pr vate f nal L st<Str ng> t etCreateEventBranc s = L sts.newArrayL st();
  pr vate f nal L st<Str ng> t etDeleteEventBranc s = L sts.newArrayL st();

  pr vate boolean should ndexProtectedT ets;
  pr vate SearchCounter totalEventsCount;
  pr vate SearchCounter thr ftPars ngErrorsCount;

  pr vate L st<Pengu nVers on> supportedPengu nVers ons;

  @Overr de
  protected vo d  n Stats() {
    super. n Stats();

    for (T etEventData._F elds f eld : T etEventData._F elds.values()) {
      t etEventCounters.put(
          f eld,
          t .makeStageCounter(f eld.na ().toLo rCase() + "_count"));
    }
    totalEventsCount = t .makeStageCounter("total_events_count");
    thr ftPars ngErrorsCount = t .makeStageCounter("thr ft_pars ng_errors_count");
  }

  @Overr de
  protected vo d do nnerPreprocess() throws StageExcept on, Nam ngExcept on {
    supportedPengu nVers ons = w reModule.getPengu nVers ons();
    LOG. nfo("Supported pengu n vers ons: {}", supportedPengu nVers ons);

    should ndexProtectedT ets = earlyb rdCluster == Earlyb rdCluster.PROTECTED
        || earlyb rdCluster == Earlyb rdCluster.REALT ME_CG;

    Precond  ons.c ckState(!t etDeleteEventBranc s. sEmpty(),
                             "At least one delete branch must be spec f ed.");
  }

  @Overr de
  publ c vo d  nnerProcess(Object obj) throws StageExcept on {
     f (!(obj  nstanceof T etEventData || obj  nstanceof  ngesterT etEvent)) {
      LOG.error("Object  s not a T etEventData or  ngesterT etEvent: {}", obj);
      throw new StageExcept on(t , "Object  s not a T etEventData or  ngesterT etEvent");
    }

    supportedPengu nVers ons = w reModule.getCurrentlyEnabledPengu nVers ons();

    try {
       ngesterT etEvent  ngesterT etEvent = ( ngesterT etEvent) obj;
      T etEventData t etEventData =  ngesterT etEvent.getData();
      DebugEvents debugEvents =  ngesterT etEvent.getDebugEvents();

      // Determ ne  f t   ssage  s a t et delete event before t  next stages mutate  .
       ngesterTw ter ssage  ssage = getTw ter ssage(t etEventData, debugEvents);
      boolean shouldEm  ssage =  ssage != null
          &&  ssage. s ndexable(should ndexProtectedT ets);

       f (shouldEm  ssage) {
         f (! ssage. sDeleted()) {
          em AndCount( ssage);

          for (Str ng t etCreateEventBranch : t etCreateEventBranc s) {
            //  f   need to send t   ssage to anot r branch,   need to make a copy.
            // Ot rw se,  'll have mult ple stages mutat ng t  sa  object  n parallel.
             ngesterTw ter ssage t etCreateEventBranch ssage =
                getTw ter ssage(t etEventData, debugEvents);
            em ToBranchAndCount(t etCreateEventBranch, t etCreateEventBranch ssage);
          }
        } else {
          for (Str ng t etDeleteEventBranch : t etDeleteEventBranc s) {
            //  f   need to send t   ssage to anot r branch,   need to make a copy.
            // Ot rw se,  'll have mult ple stages mutat ng t  sa  object  n parallel.
             ngesterTw ter ssage t etDeleteEventBranch ssage =
                getTw ter ssage(t etEventData, debugEvents);
            em ToBranchAndCount(t etDeleteEventBranch, t etDeleteEventBranch ssage);
          }
        }
      }
    } catch (Thr ftT etPars ngExcept on e) {
      thr ftPars ngErrorsCount. ncre nt();
      LOG.error("Fa led to parse Thr ft t et event: " + obj, e);
      throw new StageExcept on(t , e);
    }
  }

  @Nullable
  pr vate  ngesterTw ter ssage getTw ter ssage(
      @Nonnull T etEventData t etEventData,
      @Nullable DebugEvents debugEvents)
      throws Thr ftT etPars ngExcept on {
    totalEventsCount. ncre nt();

    // T etEventData  s a un on of all poss ble t et event types. T etEventData._F elds  s an
    // enum that corresponds to all T etEventData f elds. By call ng T etEventData.getSetF eld(),
    //   can determ ne wh ch f eld  s set.
    T etEventData._F elds t etEventDataF eld = t etEventData.getSetF eld();
    Precond  ons.c ckNotNull(t etEventDataF eld);
    t etEventCounters.get(t etEventDataF eld). ncre nt();

     f (t etEventDataF eld == T etEventData._F elds.TWEET_CREATE_EVENT) {
      T etCreateEvent t etCreateEvent = t etEventData.getT et_create_event();
      return T etEventParse lper.getTw ter ssageFromCreat onEvent(
          t etCreateEvent, supportedPengu nVers ons, debugEvents);
    }
     f (t etEventDataF eld == T etEventData._F elds.TWEET_DELETE_EVENT) {
      T etDeleteEvent t etDeleteEvent = t etEventData.getT et_delete_event();
      return T etEventParse lper.getTw ter ssageFromDelet onEvent(
          t etDeleteEvent, supportedPengu nVers ons, debugEvents);
    }
    return null;
  }

  /**
   * Sets t  branc s to wh ch all T etDeleteEvents should be em ted.
   *
   * @param t etDeleteEventBranchNa s A comma-separated l st of branc s.
   */
  publ c vo d setT etDeleteEventBranchNa s(Str ng t etDeleteEventBranchNa s) {
    parseBranc s(t etDeleteEventBranchNa s, t etDeleteEventBranc s);
  }

  /**
   * Sets t  add  onal branc s to wh ch all T etCreateEvents should be em ted.
   *
   * @param t etCreateEventBranchNa s A comma-separated l st of branc s.
   */
  publ c vo d setT etCreateEventBranchNa s(Str ng t etCreateEventBranchNa s) {
    parseBranc s(t etCreateEventBranchNa s, t etCreateEventBranc s);
  }

  pr vate vo d parseBranc s(Str ng branchNa s, L st<Str ng> branc s) {
    branc s.clear();
    for (Str ng branch : branchNa s.spl (",")) {
      Str ng tr m dBranch = branch.tr m();
      Precond  ons.c ckState(!tr m dBranch. sEmpty(), "Branc s cannot be empty str ngs.");
      branc s.add(tr m dBranch);
    }
  }
}
