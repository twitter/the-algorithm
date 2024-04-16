package com.tw ter.search. ngester.p pel ne.tw ter.kafka;

 mport javax.nam ng.Nam ngExcept on;

 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .commons.p pel ne.val dat on.Consu dTypes;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.debug.DebugEventUt l;
 mport com.tw ter.search.common.debug.thr ftjava.DebugEvents;
 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.search. ngester.model. ngesterThr ftVers onedEvents;
 mport com.tw ter.search. ngester.p pel ne.ut l.P pel neStageExcept on;

/**
 * Kafka producer stage to wr e t et  ndex ng data as {@code Thr ftVers onedEvents}. T  stage
 * also handles extra debug event process ng.
 */
@Consu dTypes( ngesterThr ftVers onedEvents.class)
publ c class T etThr ftVers onedEventsKafkaProducerStage extends KafkaProducerStage
    < ngesterThr ftVers onedEvents> {
  pr vate stat c f nal  nt PROCESS NG_LATENCY_THRESHOLD_FOR_UPDATES_M LL S = 30000;

  pr vate stat c f nal Logger LOG =
      LoggerFactory.getLogger(T etThr ftVers onedEventsKafkaProducerStage.class);

  pr vate long processedT etCount = 0;

  pr vate SearchLongGauge kafkaProducerLag;

  pr vate  nt debugEventLogPer od = -1;

  publ c T etThr ftVers onedEventsKafkaProducerStage(Str ng kafkaTop c, Str ng cl ent d,
                                            Str ng clusterPath) {
    super(kafkaTop c, cl ent d, clusterPath);
  }

  publ c T etThr ftVers onedEventsKafkaProducerStage() {
    super();
  }

  @Overr de
  protected vo d  n Stats() {
    super. n Stats();
    setupCommonStats();
  }

  @Overr de
  protected vo d  nnerSetupStats() {
    super. nnerSetupStats();
    setupCommonStats();
  }

  pr vate vo d setupCommonStats() {
    kafkaProducerLag = SearchLongGauge.export(
        getStageNa Pref x() + "_kafka_producer_lag_m ll s");
  }

  @Overr de
  protected vo d  nnerSetup() throws P pel neStageExcept on, Nam ngExcept on {
    super. nnerSetup();
  }

  @Overr de
  protected vo d do nnerPreprocess() throws StageExcept on, Nam ngExcept on {
    super.do nnerPreprocess();
    common nnerSetup();
  }

  pr vate vo d common nnerSetup() {
    setProcess ngLatencyThresholdM ll s(PROCESS NG_LATENCY_THRESHOLD_FOR_UPDATES_M LL S);
  }

  @Overr de
  publ c vo d  nnerProcess(Object obj) throws StageExcept on {
     f (!(obj  nstanceof  ngesterThr ftVers onedEvents)) {
      throw new StageExcept on(t , "Object  s not  ngesterThr ftVers onedEvents: " + obj);
    }

     ngesterThr ftVers onedEvents events = ( ngesterThr ftVers onedEvents) obj;
     nnerRunF nalStageOfBranchV2(events);
  }

  @Overr de
  protected vo d  nnerRunF nalStageOfBranchV2( ngesterThr ftVers onedEvents events) {
     f ((debugEventLogPer od > 0)
        && (processedT etCount % debugEventLogPer od == 0)
        && (events.getDebugEvents() != null)) {
      LOG. nfo("DebugEvents for t et {}: {}",
          events.getT et d(), DebugEventUt l.debugEventsToStr ng(events.getDebugEvents()));
    }
    processedT etCount++;

    DebugEvents debugEvents = events.getDebugEvents();
     f ((debugEvents != null) && debugEvents. sSetProcess ngStartedAt()) {
      kafkaProducerLag.set(
          clock.nowM ll s() - debugEvents.getProcess ngStartedAt().getEventT  stampM ll s());
    }

    super.tryToSendEventsToKafka(events);
  }

  @SuppressWarn ngs("unused")  // set from p pel ne conf g
  publ c vo d setDebugEventLogPer od( nt debugEventLogPer od) {
    t .debugEventLogPer od = debugEventLogPer od;
  }
}
