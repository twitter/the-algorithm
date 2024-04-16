package com.tw ter.search. ngester.p pel ne.tw ter.kafka;

 mport java.ut l.Collect on;
 mport java.ut l.Map;

 mport javax.nam ng.Nam ngExcept on;

 mport scala.runt  .BoxedUn ;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.Maps;

 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .kafka.cl ents.producer.ProducerRecord;
 mport org.apac .kafka.cl ents.producer.Record tadata;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.f natra.kafka.producers.Block ngF nagleKafkaProducer;
 mport com.tw ter.search.common.debug.DebugEventUt l;
 mport com.tw ter.search.common.debug.thr ftjava.DebugEvents;
 mport com.tw ter.search.common.dec der.Dec derUt l;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;
 mport com.tw ter.search.common. tr cs.Percent le;
 mport com.tw ter.search.common. tr cs.Percent leUt l;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndex ngEvent;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndex ngEventType;
 mport com.tw ter.search.common.ut l. o.kafka.CompactThr ftSer al zer;
 mport com.tw ter.search. ngester.model. ngesterThr ftVers onedEvents;
 mport com.tw ter.search. ngester.p pel ne.tw ter.Tw terBaseStage;
 mport com.tw ter.search. ngester.p pel ne.ut l.P pel neStageExcept on;
 mport com.tw ter.search. ngester.p pel ne.w re. ngesterPart  oner;
 mport com.tw ter.ut l.Awa ;
 mport com.tw ter.ut l.Future;

publ c class KafkaProducerStage<T> extends Tw terBaseStage<T, Vo d> {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(KafkaProducerStage.class);

  pr vate stat c f nal Logger LATE_EVENTS_LOG = LoggerFactory.getLogger(
      KafkaProducerStage.class.getNa () + ".LateEvents");

  pr vate f nal Map<Thr ft ndex ngEventType, Percent le<Long>> process ngLatenc esStats =
      Maps.newEnumMap(Thr ft ndex ngEventType.class);

  pr vate Str ng kafkaCl ent d;
  pr vate Str ng kafkaTop cNa ;
  pr vate Str ng kafkaClusterPath;
  pr vate SearchCounter sendCount;
  pr vate Str ng perPart  onSendCountFormat;
  pr vate Str ng dec derKey;

  protected Block ngF nagleKafkaProducer<Long, Thr ftVers onedEvents> kafkaProducer;

  pr vate  nt process ngLatencyThresholdM ll s = 10000;

  publ c KafkaProducerStage() { }

  publ c KafkaProducerStage(Str ng top cNa , Str ng cl ent d, Str ng clusterPath) {
    t .kafkaTop cNa  = top cNa ;
    t .kafkaCl ent d = cl ent d;
    t .kafkaClusterPath = clusterPath;
  }

  @Overr de
  protected vo d  n Stats() {
    super. n Stats();
    setupCommonStats();
  }

  pr vate vo d setupCommonStats() {
    sendCount = SearchCounter.export(getStageNa Pref x() + "_send_count");
    perPart  onSendCountFormat = getStageNa Pref x() + "_part  on_%d_send_count";
    for (Thr ft ndex ngEventType eventType : Thr ft ndex ngEventType.values()) {
      process ngLatenc esStats.put(
          eventType,
          Percent leUt l.createPercent le(
              getStageNa Pref x() + "_" + eventType.na ().toLo rCase()
                  + "_process ng_latency_ms"));
    }
  }

  @Overr de
  protected vo d  nnerSetupStats() {
   setupCommonStats();
  }

  pr vate boolean  sEnabled() {
     f (t .dec derKey != null) {
      return Dec derUt l. sAva lableForRandomRec p ent(dec der, dec derKey);
    } else {
      // No dec der  ans  's enabled.
      return true;
    }
  }

  @Overr de
  protected vo d do nnerPreprocess() throws StageExcept on, Nam ngExcept on {
    try {
       nnerSetup();
    } catch (P pel neStageExcept on e) {
      throw new StageExcept on(t , e);
    }
  }

  @Overr de
  protected vo d  nnerSetup() throws P pel neStageExcept on, Nam ngExcept on {
    Precond  ons.c ckNotNull(kafkaCl ent d);
    Precond  ons.c ckNotNull(kafkaClusterPath);
    Precond  ons.c ckNotNull(kafkaTop cNa );

    kafkaProducer = w reModule.newF nagleKafkaProducer(
        kafkaClusterPath,
        new CompactThr ftSer al zer<Thr ftVers onedEvents>(),
        kafkaCl ent d,
         ngesterPart  oner.class);

     nt numPart  ons = w reModule.getPart  onMapp ngManager().getNumPart  ons();
     nt numKafkaPart  ons = kafkaProducer.part  onsFor(kafkaTop cNa ).s ze();
     f (numPart  ons != numKafkaPart  ons) {
      throw new P pel neStageExcept on(Str ng.format(
          "Number of part  ons for Kafka top c %s (%d) != number of expected part  ons (%d)",
          kafkaTop cNa , numKafkaPart  ons, numPart  ons));
    }
  }


  @Overr de
  publ c vo d  nnerProcess(Object obj) throws StageExcept on {
     f (!(obj  nstanceof  ngesterThr ftVers onedEvents)) {
      throw new StageExcept on(t , "Object  s not  ngesterThr ftVers onedEvents: " + obj);
    }

     ngesterThr ftVers onedEvents events = ( ngesterThr ftVers onedEvents) obj;
    tryToSendEventsToKafka(events);
  }

  protected vo d tryToSendEventsToKafka( ngesterThr ftVers onedEvents events) {
     f (! sEnabled()) {
      return;
    }

    DebugEvents debugEvents = events.getDebugEvents();
    //   don't propagate debug events to Kafka, because t y take about 50%
    // of t  storage space.
    events.unsetDebugEvents();

    ProducerRecord<Long, Thr ftVers onedEvents> record = new ProducerRecord<>(
        kafkaTop cNa ,
        null,
        clock.nowM ll s(),
        null,
        events);

    sendRecordToKafka(record).ensure(() -> {
      updateEventProcess ngLatencyStats(events, debugEvents);
      return null;
    });
  }

  pr vate Future<Record tadata> sendRecordToKafka(
      ProducerRecord<Long, Thr ftVers onedEvents> record) {
    Future<Record tadata> result;
    try {
      result = kafkaProducer.send(record);
    } catch (Except on e) {
      // Even though KafkaProducer.send() returns a Future,   can throw a synchronous except on,
      // so   translate synchronous except ons  nto a Future.except on so   handle all except ons
      // cons stently.
      result = Future.except on(e);
    }

    return result.onSuccess(record tadata -> {
      sendCount. ncre nt();
      SearchCounter.export(
          Str ng.format(perPart  onSendCountFormat, record tadata.part  on())). ncre nt();
      return BoxedUn .UN T;
    }).onFa lure(e -> {
      stats. ncre ntExcept ons();
      LOG.error("Send ng a record fa led.", e);
      return BoxedUn .UN T;
    });
  }

  pr vate vo d updateEventProcess ngLatencyStats( ngesterThr ftVers onedEvents events,
                                                 DebugEvents debugEvents) {
     f ((debugEvents != null) && debugEvents. sSetProcess ngStartedAt()) {
      // Get t  one  ndex ng event out of all events  're send ng.
      Collect on<Thr ft ndex ngEvent>  ndex ngEvents = events.getVers onedEvents().values();
      Precond  ons.c ckState(! ndex ngEvents. sEmpty());
      Thr ft ndex ngEventType eventType =  ndex ngEvents. erator().next().getEventType();

      // C ck  f t  event took too much t   to get to t  current po nt.
      long process ngLatencyM ll s =
          clock.nowM ll s() - debugEvents.getProcess ngStartedAt().getEventT  stampM ll s();
      process ngLatenc esStats.get(eventType).record(process ngLatencyM ll s);

       f (process ngLatencyM ll s >= process ngLatencyThresholdM ll s) {
        LATE_EVENTS_LOG.warn("Event of type {} for t et {} was processed  n {}ms: {}",
            eventType.na (),
            events.getT et d(),
            process ngLatencyM ll s,
            DebugEventUt l.debugEventsToStr ng(debugEvents));
      }
    }
  }

  publ c vo d setProcess ngLatencyThresholdM ll s( nt process ngLatencyThresholdM ll s) {
    t .process ngLatencyThresholdM ll s = process ngLatencyThresholdM ll s;
  }

  @Overr de
  publ c vo d  nnerPostprocess() throws StageExcept on {
    try {
      commonCleanup();
    } catch (Except on e) {
      throw new StageExcept on(t , e);
    }
  }

  @Overr de
  publ c vo d cleanupStageV2()  {
    try {
      commonCleanup();
    } catch (Except on e) {
      LOG.error("Error try ng to clean up KafkaProducerStage.", e);
    }
  }

  pr vate vo d commonCleanup() throws Except on {
    Awa .result(kafkaProducer.close());
  }

  @SuppressWarn ngs("unused")  // set from p pel ne conf g
  publ c vo d setKafkaCl ent d(Str ng kafkaCl ent d) {
    t .kafkaCl ent d = kafkaCl ent d;
  }

  @SuppressWarn ngs("unused")  // set from p pel ne conf g
  publ c vo d setKafkaTop cNa (Str ng kafkaTop cNa ) {
    t .kafkaTop cNa  = kafkaTop cNa ;
  }

  @V s bleForTest ng
  publ c Block ngF nagleKafkaProducer<Long, Thr ftVers onedEvents> getKafkaProducer() {
    return kafkaProducer;
  }

  @SuppressWarn ngs("unused")  // set from p pel ne conf g
  publ c vo d setDec derKey(Str ng dec derKey) {
    t .dec derKey = dec derKey;
  }

  @SuppressWarn ngs("unused")  // set from p pel ne conf g
  publ c vo d setKafkaClusterPath(Str ng kafkaClusterPath) {
    t .kafkaClusterPath = kafkaClusterPath;
  }
}
