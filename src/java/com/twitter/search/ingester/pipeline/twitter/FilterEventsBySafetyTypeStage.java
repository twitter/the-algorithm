package com.tw ter.search. ngester.p pel ne.tw ter;

 mport java.ut l.Map;
 mport java.ut l.concurrent.ConcurrentHashMap;
 mport java.ut l.concurrent.T  Un ;
 mport javax.annotat on.Nonnull;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .commons.p pel ne.val dat on.Consu dTypes;
 mport org.apac .commons.p pel ne.val dat on.ProducedTypes;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchDelayStats;
 mport com.tw ter.search.common.part  on ng.snowflakeparser.Snowflake dParser;
 mport com.tw ter.search. ngester.model. ngesterT etEvent;
 mport com.tw ter.search. ngester.p pel ne.ut l.P pel neStageRunt  Except on;
 mport com.tw ter.t etyp e.thr ftjava.T et;
 mport com.tw ter.t etyp e.thr ftjava.T etCreateEvent;
 mport com.tw ter.t etyp e.thr ftjava.T etEvent;
 mport com.tw ter.t etyp e.thr ftjava.T etEventData;
 mport com.tw ter.t etyp e.thr ftjava.T etEventFlags;

/**
 * Only lets through t  create events that match t  spec f ed safety type.
 * Also lets through all delete events.
 */
@Consu dTypes( ngesterT etEvent.class)
@ProducedTypes( ngesterT etEvent.class)
publ c class F lterEventsBySafetyTypeStage extends Tw terBaseStage
        < ngesterT etEvent,  ngesterT etEvent> {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(F lterEventsBySafetyTypeStage.class);

  pr vate SearchCounter totalEventsCount;
  pr vate SearchCounter createEventsCount;
  pr vate SearchCounter createPubl cEventsCount;
  pr vate SearchCounter createProtectedEventsCount;
  pr vate SearchCounter createRestr ctedEventsCount;
  pr vate SearchCounter create nval dSafetyTypeCount;
  pr vate SearchCounter deleteEventsCount;
  pr vate SearchCounter deletePubl cEventsCount;
  pr vate SearchCounter deleteProtectedEventsCount;
  pr vate SearchCounter deleteRestr ctedEventsCount;
  pr vate SearchCounter delete nval dSafetyTypeCount;
  pr vate SearchCounter ot rEventsCount;

  pr vate SearchDelayStats t etCreateDelayStats;

  pr vate long t etCreateLatencyLogThresholdM ll s = -1;
  pr vate SafetyType safetyType = null;
  pr vate Map<Str ng, Map<Str ng, SearchCounter>>  nval dSafetyTypeByEventTypeStatMap =
          new ConcurrentHashMap<>();

  publ c F lterEventsBySafetyTypeStage() { }

  publ c F lterEventsBySafetyTypeStage(Str ng safetyType, long t etCreateLatencyThresholdM ll s) {
    setSafetyType(safetyType);
    t .t etCreateLatencyLogThresholdM ll s = t etCreateLatencyThresholdM ll s;
  }

  /**
   * To be called by XML conf g. Can be made pr vate after   delete ACP code.
   */
  publ c vo d setSafetyType(@Nonnull Str ng safetyTypeStr ng) {
    t .safetyType = SafetyType.valueOf(safetyTypeStr ng);
     f (t .safetyType == SafetyType. NVAL D) {
      throw new UnsupportedOperat onExcept on(
              "Can't create a stage that perm s ' NVAL D' safetytypes");
    }
  }

  @Overr de
  protected vo d  n Stats() {
    super. n Stats();
     nnerSetupStats();
  }

  @Overr de
  protected vo d  nnerSetupStats() {
    totalEventsCount = SearchCounter.export(getStageNa Pref x() + "_total_events_count");
    createEventsCount = SearchCounter.export(getStageNa Pref x() + "_create_events_count");
    createPubl cEventsCount =
            SearchCounter.export(getStageNa Pref x() + "_create_publ c_events_count");
    createProtectedEventsCount =
            SearchCounter.export(getStageNa Pref x() + "_create_protected_events_count");
    createRestr ctedEventsCount =
            SearchCounter.export(getStageNa Pref x() + "_create_restr cted_events_count");
    create nval dSafetyTypeCount =
            SearchCounter.export(getStageNa Pref x() + "_create_m ss ng_or_unknown_safetytype");
    deleteEventsCount =
            SearchCounter.export(getStageNa Pref x() + "_delete_events_count");
    deletePubl cEventsCount =
            SearchCounter.export(getStageNa Pref x() + "_delete_publ c_events_count");
    deleteProtectedEventsCount =
            SearchCounter.export(getStageNa Pref x() + "_delete_protected_events_count");
    deleteRestr ctedEventsCount =
            SearchCounter.export(getStageNa Pref x() + "_delete_restr cted_events_count");
    delete nval dSafetyTypeCount =
            SearchCounter.export(getStageNa Pref x() + "_delete_m ss ng_or_unknown_safetytype");
    ot rEventsCount =
            SearchCounter.export(getStageNa Pref x() + "_ot r_events_count");

    t etCreateDelayStats = SearchDelayStats.export(
            "create_ togram_" + getStageNa Pref x(), 90,
            T  Un .SECONDS, T  Un .M LL SECONDS);
  }

  @Overr de
  publ c vo d  nnerProcess(Object obj) throws StageExcept on {
     f (obj  nstanceof  ngesterT etEvent) {
       ngesterT etEvent t etEvent = ( ngesterT etEvent) obj;
       f (tryToRecordCreateLatency(t etEvent)) {
        em AndCount(t etEvent);
      }
    } else {
      throw new StageExcept on(t , "Object  s not a  ngesterT etEvent: " + obj);
    }
  }

  @Overr de
  protected  ngesterT etEvent  nnerRunStageV2( ngesterT etEvent t etEvent) {
     f (!tryToRecordCreateLatency(t etEvent)) {
      throw new P pel neStageRunt  Except on("Event does not have to pass to t  next stage.");
    }
    return t etEvent;
  }

  pr vate boolean tryToRecordCreateLatency( ngesterT etEvent t etEvent) {
     ncre ntCounters(t etEvent);
    boolean shouldEm  = shouldEm (t etEvent);
     f (shouldEm ) {
       f ( sCreateEvent(t etEvent.getData())) {
        recordCreateLatency(t etEvent.getData().getT et_create_event());
      }
    }
    return shouldEm ;
  }

  pr vate vo d  ncre ntCounters(@Nonnull T etEvent t etEvent) {
    totalEventsCount. ncre nt();
    SafetyType eventSafetyType = getEventSafetyType(t etEvent);

     f ( sCreateEvent(t etEvent.getData())) {
      createEventsCount. ncre nt();
      sw ch (eventSafetyType) {
        case PUBL C:
          createPubl cEventsCount. ncre nt();
          break;
        case PROTECTED:
          createProtectedEventsCount. ncre nt();
          break;
        case RESTR CTED:
          createRestr ctedEventsCount. ncre nt();
          break;
        default:
          create nval dSafetyTypeCount. ncre nt();
           ncre nt nval dSafetyTypeStatMap(t etEvent, "create");
      }
    } else  f ( sDeleteEvent(t etEvent.getData())) {
      deleteEventsCount. ncre nt();
      sw ch (eventSafetyType) {
        case PUBL C:
          deletePubl cEventsCount. ncre nt();
          break;
        case PROTECTED:
          deleteProtectedEventsCount. ncre nt();
          break;
        case RESTR CTED:
          deleteRestr ctedEventsCount. ncre nt();
          break;
        default:
          delete nval dSafetyTypeCount. ncre nt();
           ncre nt nval dSafetyTypeStatMap(t etEvent, "delete");
      }
    } else {
      ot rEventsCount. ncre nt();
    }
  }

  pr vate vo d  ncre nt nval dSafetyTypeStatMap(T etEvent t etEvent, Str ng eventType) {
    com.tw ter.t etyp e.thr ftjava.SafetyType thr ftSafetyType =
            t etEvent.getFlags().getSafety_type();
    Str ng safetyTypeStr ng =
            thr ftSafetyType == null ? "null" : thr ftSafetyType.toStr ng().toLo rCase();
     nval dSafetyTypeByEventTypeStatMap.put fAbsent(eventType, new ConcurrentHashMap<>());
    SearchCounter stat =  nval dSafetyTypeByEventTypeStatMap.get(eventType).compute fAbsent(
            safetyTypeStr ng,
            safetyTypeStr -> SearchCounter.export(
                    getStageNa Pref x()
                            + Str ng.format("_%s_m ss ng_or_unknown_safetytype_%s",
                            eventType, safetyTypeStr)));
    stat. ncre nt();
  }

  @V s bleForTest ng
  boolean shouldEm (@Nonnull T etEvent t etEvent) {
    // Do not em  any undelete events.
     f ( sUndeleteEvent(t etEvent.getData())) {
      return false;
    }

    SafetyType eventSafetyType = getEventSafetyType(t etEvent);
    // Custom log c for REALT ME_CG cluster
     f (safetyType == SafetyType.PUBL C_OR_PROTECTED) {
      return eventSafetyType == SafetyType.PUBL C || eventSafetyType == SafetyType.PROTECTED;
    } else {
      return eventSafetyType == safetyType;
    }
  }

  pr vate SafetyType getEventSafetyType(@Nonnull T etEvent t etEvent) {
    T etEventFlags t etEventFlags = t etEvent.getFlags();
    return SafetyType.fromThr ftSafetyType(t etEventFlags.getSafety_type());
  }

  pr vate boolean  sCreateEvent(@Nonnull T etEventData t etEventData) {
    return t etEventData. sSet(T etEventData._F elds.TWEET_CREATE_EVENT);
  }

  pr vate boolean  sDeleteEvent(@Nonnull T etEventData t etEventData) {
    return t etEventData. sSet(T etEventData._F elds.TWEET_DELETE_EVENT);
  }

  pr vate boolean  sUndeleteEvent(@Nonnull T etEventData t etEventData) {
    return t etEventData. sSet(T etEventData._F elds.TWEET_UNDELETE_EVENT);
  }

  pr vate vo d recordCreateLatency(T etCreateEvent t etCreateEvent) {
    T et t et = t etCreateEvent.getT et();
     f (t et != null) {
      long t etCreateLatency =
              clock.nowM ll s() - Snowflake dParser.getT  stampFromT et d(t et.get d());
      t etCreateDelayStats.recordLatency(t etCreateLatency, T  Un .M LL SECONDS);
       f (t etCreateLatency < 0) {
        LOG.warn("Rece ved a t et created  n t  future: {}", t et);
      } else  f (t etCreateLatencyLogThresholdM ll s > 0
              && t etCreateLatency > t etCreateLatencyLogThresholdM ll s) {
        LOG.debug("Found late  ncom ng t et: {}. Create latency: {}ms. T et: {}",
                t et.get d(), t etCreateLatency, t et);
      }
    }
  }

  publ c vo d setT etCreateLatencyLogThresholdM ll s(long t etCreateLatencyLogThresholdM ll s) {
    LOG. nfo("Sett ng t etCreateLatencyLogThresholdM ll s to {}.",
            t etCreateLatencyLogThresholdM ll s);
    t .t etCreateLatencyLogThresholdM ll s = t etCreateLatencyLogThresholdM ll s;
  }

  publ c enum SafetyType {
    PUBL C,
    PROTECTED,
    RESTR CTED,
    PUBL C_OR_PROTECTED,
     NVAL D;

    /** Converts a t etyp e SafetyType  nstance to an  nstance of t  enum. */
    @Nonnull
    publ c stat c SafetyType fromThr ftSafetyType(
            com.tw ter.t etyp e.thr ftjava.SafetyType safetyType) {
       f (safetyType == null) {
        return  NVAL D;
      }
      sw ch(safetyType) {
        case PR VATE:
          return PROTECTED;
        case PUBL C:
          return PUBL C;
        case RESTR CTED:
          return RESTR CTED;
        default:
          return  NVAL D;
      }
    }
  }
}
