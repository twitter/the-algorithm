package com.tw ter.search.feature_update_serv ce;

 mport java.ut l.Arrays;
 mport java.ut l.Collect ons;
 mport java.ut l.L st;
 mport javax. nject. nject;
 mport javax. nject.Na d;

 mport scala.runt  .BoxedUn ;

 mport com.google.common.collect. mmutableMap;
 mport com.google.common.collect.L sts;

 mport org.apac .kafka.cl ents.producer.ProducerRecord;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on;
 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.f nagle.mux.Cl entD scardedRequestExcept on;
 mport com.tw ter.f nagle.thr ft.Cl ent d;
 mport com.tw ter.f natra.kafka.producers.Block ngF nagleKafkaProducer;
 mport com.tw ter. nject.annotat ons.Flag;
 mport com.tw ter.search.common.dec der.Dec derUt l;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndex ngEvent;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndex ngEventType;
 mport com.tw ter.search.feature_update_serv ce.modules.Earlyb rdUt lModule;
 mport com.tw ter.search.feature_update_serv ce.modules.F nagleKafkaProducerModule;
 mport com.tw ter.search.feature_update_serv ce.stats.FeatureUpdateStats;
 mport com.tw ter.search.feature_update_serv ce.thr ftjava.FeatureUpdateRequest;
 mport com.tw ter.search.feature_update_serv ce.thr ftjava.FeatureUpdateResponse;
 mport com.tw ter.search.feature_update_serv ce.thr ftjava.FeatureUpdateResponseCode;
 mport com.tw ter.search.feature_update_serv ce.thr ftjava.FeatureUpdateServ ce;
 mport com.tw ter.search.feature_update_serv ce.ut l.FeatureUpdateVal dator;
 mport com.tw ter.search. ngester.model. ngesterThr ftVers onedEvents;
 mport com.tw ter.t etyp e.thr ftjava.GetT etF eldsOpt ons;
 mport com.tw ter.t etyp e.thr ftjava.GetT etF eldsRequest;
 mport com.tw ter.t etyp e.thr ftjava.T et nclude;
 mport com.tw ter.t etyp e.thr ftjava.T etServ ce;
 mport com.tw ter.t etyp e.thr ftjava.T etV s b l yPol cy;
 mport com.tw ter.ut l.ExecutorServ ceFuturePool;
 mport com.tw ter.ut l.Funct on;
 mport com.tw ter.ut l.Future;
 mport com.tw ter.ut l.Futures;

 mport stat c com.tw ter.t etyp e.thr ftjava.T et._F elds.CORE_DATA;

publ c class FeatureUpdateController  mple nts FeatureUpdateServ ce.Serv ce face {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(FeatureUpdateController.class);
  pr vate stat c f nal Logger REQUEST_LOG =
      LoggerFactory.getLogger("feature_update_serv ce_requests");
  pr vate stat c f nal Str ng KAFKA_SEND_COUNT_FORMAT = "kafka_%s_part  on_%d_send_count";
  pr vate stat c f nal Str ng WR TE_TO_KAFKA_DEC DER_KEY = "wr e_events_to_kafka_update_events";
  pr vate stat c f nal Str ng WR TE_TO_KAFKA_DEC DER_KEY_REALT ME_CG =
          "wr e_events_to_kafka_update_events_realt  _cg";

  pr vate f nal SearchRateCounter droppedKafkaUpdateEvents =
      SearchRateCounter.export("dropped_kafka_update_events");

  pr vate f nal SearchRateCounter droppedKafkaUpdateEventsRealt  Cg =
          SearchRateCounter.export("dropped_kafka_update_events_realt  _cg");
  pr vate f nal Clock clock;
  pr vate f nal Dec der dec der;
  pr vate f nal Block ngF nagleKafkaProducer<Long, Thr ftVers onedEvents> kafkaProducer;
  pr vate f nal Block ngF nagleKafkaProducer<Long, Thr ftVers onedEvents> kafkaProducerRealt  Cg;

  pr vate f nal L st<Pengu nVers on> pengu nVers ons;
  pr vate f nal FeatureUpdateStats stats;
  pr vate f nal Str ng kafkaUpdateEventsTop cNa ;
  pr vate f nal Str ng kafkaUpdateEventsTop cNa Realt  Cg;
  pr vate f nal ExecutorServ ceFuturePool futurePool;
  pr vate f nal T etServ ce.Serv ce face t etServ ce;

  @ nject
  publ c FeatureUpdateController(
      Clock clock,
      Dec der dec der,
      @Na d("KafkaProducer")
      Block ngF nagleKafkaProducer<Long, Thr ftVers onedEvents> kafkaProducer,
      @Na d("KafkaProducerRealt  Cg")
      Block ngF nagleKafkaProducer<Long, Thr ftVers onedEvents> kafkaProducerRealt  Cg,
      @Flag(Earlyb rdUt lModule.PENGU N_VERS ONS_FLAG) Str ng pengu nVers ons,
      FeatureUpdateStats stats,
      @Flag(F nagleKafkaProducerModule.KAFKA_TOP C_NAME_UPDATE_EVENTS_FLAG)
      Str ng kafkaUpdateEventsTop cNa ,
      @Flag(F nagleKafkaProducerModule.KAFKA_TOP C_NAME_UPDATE_EVENTS_FLAG_REALT ME_CG)
      Str ng kafkaUpdateEventsTop cNa Realt  Cg,
      ExecutorServ ceFuturePool futurePool,
      T etServ ce.Serv ce face t etServ ce
  ) {
    t .clock = clock;
    t .dec der = dec der;
    t .kafkaProducer = kafkaProducer;
    t .kafkaProducerRealt  Cg = kafkaProducerRealt  Cg;
    t .pengu nVers ons = getPengu nVers ons(pengu nVers ons);
    t .stats = stats;
    t .kafkaUpdateEventsTop cNa  = kafkaUpdateEventsTop cNa ;
    t .kafkaUpdateEventsTop cNa Realt  Cg = kafkaUpdateEventsTop cNa Realt  Cg;
    t .futurePool = futurePool;
    t .t etServ ce = t etServ ce;
  }

  @Overr de
  publ c Future<FeatureUpdateResponse> process(FeatureUpdateRequest featureUpdate) {
    long requestStartT  M ll s = clock.nowM ll s();

    // Export overall and per-cl ent request rate stats
    f nal Str ng requestCl ent d;
     f (featureUpdate.getRequestCl ent d() != null
        && !featureUpdate.getRequestCl ent d(). sEmpty()) {
      requestCl ent d = featureUpdate.getRequestCl ent d();
    } else  f (Cl ent d.current().nonEmpty()) {
      requestCl ent d =  Cl ent d.current().get().na ();
    } else {
      requestCl ent d = "unknown";
    }
    stats.cl entRequest(requestCl ent d);
    REQUEST_LOG. nfo("{} {}", requestCl ent d, featureUpdate);

    FeatureUpdateResponse errorResponse = FeatureUpdateVal dator.val date(featureUpdate);
     f (errorResponse != null) {
      stats.cl entResponse(requestCl ent d, errorResponse.getResponseCode());
      LOG.warn("cl ent error: cl ent D {} - reason: {}",
          requestCl ent d, errorResponse.getDeta l ssage());
      return Future.value(errorResponse);
    }

    Thr ft ndex ngEvent event = featureUpdate.getEvent();
    return wr eToKafka(event, requestStartT  M ll s)
        .map(responsesL st -> {
          stats.cl entResponse(requestCl ent d, FeatureUpdateResponseCode.SUCCESS);
          // only w n both Realt   & Realt  CG succeed, t n   w ll return a success flag
          return new FeatureUpdateResponse(FeatureUpdateResponseCode.SUCCESS);
        })
        .handle(Funct on.func(throwable -> {
          FeatureUpdateResponseCode responseCode;
          //  f e  r Realt   or Realt  CG throws an except on,   w ll return a fa lure
           f (throwable  nstanceof Cl entD scardedRequestExcept on) {
            responseCode = FeatureUpdateResponseCode.CL ENT_CANCEL_ERROR;
            LOG. nfo("Cl entD scardedRequestExcept on rece ved from cl ent: " + requestCl ent d,
                throwable);
          } else {
            responseCode = FeatureUpdateResponseCode.TRANS ENT_ERROR;
            LOG.error("Error occurred wh le wr  ng to output stream: "
                + kafkaUpdateEventsTop cNa  + ", "
                + kafkaUpdateEventsTop cNa Realt  Cg, throwable);
          }
          stats.cl entResponse(requestCl ent d, responseCode);
          return new FeatureUpdateResponse(responseCode)
              .setDeta l ssage(throwable.get ssage());
        }));
  }

  /**
   *  n wr eToKafka(),   use Futures.collect() to aggregate results for two RPC calls
   * Futures.collect()  ans that  f e  r one of t  Future fa ls t n   w ll return an Except on
   * only w n both Realt   & Realt  CG succeed, t n   w ll return a success flag
   * T  FeatureUpdateResponse  s more l ke an ACK  ssage, and t  upstream (feature update  ngester)
   * w ll not be affected much even  f   fa led (as long as t  kafka  ssage  s wr ten)
   */
  pr vate Future<L st<BoxedUn >> wr eToKafka(Thr ft ndex ngEvent event,
                                               long requestStartT  M ll s) {
    return Futures.collect(L sts.newArrayL st(
        wr eToKafka nternal(event, WR TE_TO_KAFKA_DEC DER_KEY, droppedKafkaUpdateEvents,
            kafkaUpdateEventsTop cNa , -1, kafkaProducer),
        Futures.flatten(getUser d(event.getU d()).map(
            user d -> wr eToKafka nternal(event, WR TE_TO_KAFKA_DEC DER_KEY_REALT ME_CG,
            droppedKafkaUpdateEventsRealt  Cg,
            kafkaUpdateEventsTop cNa Realt  Cg, user d, kafkaProducerRealt  Cg)))));

  }

  pr vate Future<BoxedUn > wr eToKafka nternal(Thr ft ndex ngEvent event, Str ng dec derKey,
     SearchRateCounter droppedStats, Str ng top cNa , long user d,
     Block ngF nagleKafkaProducer<Long, Thr ftVers onedEvents> producer) {
     f (!Dec derUt l. sAva lableForRandomRec p ent(dec der, dec derKey)) {
      droppedStats. ncre nt();
      return Future.Un ();
    }

    ProducerRecord<Long, Thr ftVers onedEvents> producerRecord = new ProducerRecord<>(
            top cNa ,
            convertToThr ftVers onedEvents(user d, event));

    try {
      return Futures.flatten(futurePool.apply(() ->
              producer.send(producerRecord)
                      .map(record -> {
                        SearchCounter.export(Str ng.format(
                          KAFKA_SEND_COUNT_FORMAT, record.top c(), record.part  on())). ncre nt();
                        return BoxedUn .UN T;
                      })));
    } catch (Except on e) {
      return Future.except on(e);
    }
  }

  pr vate L st<Pengu nVers on> getPengu nVers ons(Str ng pengu nVers onsStr) {
    Str ng[] tokens = pengu nVers onsStr.spl ("\\s*,\\s*");
    L st<Pengu nVers on> l stOfPengu nVers ons = L sts.newArrayL stW hCapac y(tokens.length);
    for (Str ng token : tokens) {
      l stOfPengu nVers ons.add(Pengu nVers on.valueOf(token.toUpperCase()));
    }
    LOG. nfo(Str ng.format("Us ng Pengu n Vers ons: %s", l stOfPengu nVers ons));
    return l stOfPengu nVers ons;
  }

  pr vate Future<Long> getUser d(long t et d) {
    T et nclude t et nclude = new T et nclude();
    t et nclude.setT etF eld d(CORE_DATA.getThr ftF eld d());
    GetT etF eldsOpt ons getT etF eldsOpt ons = new GetT etF eldsOpt ons().setT et_ ncludes(
        Collect ons.s ngleton(t et nclude)).setV s b l yPol cy(
        T etV s b l yPol cy.NO_F LTER NG);
    GetT etF eldsRequest getT etF eldsRequest = new GetT etF eldsRequest().setT et ds(
        Arrays.asL st(t et d)).setOpt ons(getT etF eldsOpt ons);
    try {
      return t etServ ce.get_t et_f elds(getT etF eldsRequest).map(
          t etF eldsResults -> t etF eldsResults.get(
              0).t etResult.getFound().t et.core_data.user_ d);
    } catch (Except on e) {
      return Future.except on(e);
    }
  }

  pr vate Thr ftVers onedEvents convertToThr ftVers onedEvents(
      long user d, Thr ft ndex ngEvent event) {
    Thr ft ndex ngEvent thr ft ndex ngEvent = event.deepCopy()
        .setEventType(Thr ft ndex ngEventType.PART AL_UPDATE);

     mmutableMap.Bu lder<Byte, Thr ft ndex ngEvent> vers onedEventsBu lder =
        new  mmutableMap.Bu lder<>();
    for (Pengu nVers on pengu nVers on : pengu nVers ons) {
      vers onedEventsBu lder.put(pengu nVers on.getByteValue(), thr ft ndex ngEvent);
    }

     ngesterThr ftVers onedEvents thr ftVers onedEvents =
        new  ngesterThr ftVers onedEvents(user d, vers onedEventsBu lder.bu ld());
    thr ftVers onedEvents.set d(thr ft ndex ngEvent.getU d());
    return thr ftVers onedEvents;
  }
}
