package com.tw ter.search. ngester.p pel ne.tw ter.kafka;

 mport java.t  .Durat on;
 mport java.ut l.ArrayL st;
 mport java.ut l.Collect ons;
 mport java.ut l.L st;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport org.apac .commons.p pel ne.P pel ne;
 mport org.apac .commons.p pel ne.StageDr ver;
 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .kafka.cl ents.consu r.Consu rRecords;
 mport org.apac .kafka.cl ents.consu r.KafkaConsu r;
 mport org.apac .kafka.common.Top cPart  on;
 mport org.apac .kafka.common.errors.SaslAut nt cat onExcept on;
 mport org.apac .kafka.common.errors.Ser al zat onExcept on;
 mport org.apac .kafka.common.ser al zat on.Deser al zer;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;
 mport com.tw ter.search.common.dec der.Dec derUt l;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search. ngester.p pel ne.tw ter.Tw terBaseStage;
 mport com.tw ter.search. ngester.p pel ne.ut l.P pel neStageExcept on;
 mport com.tw ter.search. ngester.p pel ne.ut l.P pel neUt l;

/**
 * A stage to read Thr ft payloads from a Kafka top c.
 */
publ c abstract class KafkaConsu rStage<R> extends Tw terBaseStage<Vo d, R> {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(KafkaConsu rStage.class);
  pr vate stat c f nal Str ng SHUT_DOWN_ON_AUTH_FA L = "shut_down_on_aut nt cat on_fa l";
  pr vate Str ng kafkaCl ent d;
  pr vate Str ng kafkaTop cNa ;
  pr vate Str ng kafkaConsu rGroup d;
  pr vate Str ng kafkaClusterPath;
  pr vate  nt maxPollRecords = 1;
  pr vate  nt pollT  outMs = 1000;
  pr vate boolean part  oned;
  pr vate Str ng dec derKey;
  pr vate f nal Deser al zer<R> deser al zer;
  pr vate SearchCounter pollCount;
  pr vate SearchCounter deser al zat onErrorCount;
  pr vate SearchRateCounter dropped ssages;

  pr vate KafkaConsu r<Long, R> kafkaConsu r;

  protected KafkaConsu rStage(Str ng kafkaCl ent d, Str ng kafkaTop cNa ,
                            Str ng kafkaConsu rGroup d, Str ng kafkaClusterPath,
                               Str ng dec derKey, Deser al zer<R> deser al zer) {

    t .kafkaCl ent d = kafkaCl ent d;
    t .kafkaTop cNa  = kafkaTop cNa ;
    t .kafkaConsu rGroup d = kafkaConsu rGroup d;
    t .kafkaClusterPath = kafkaClusterPath;
    t .dec derKey = dec derKey;
    t .deser al zer = deser al zer;
  }

  protected KafkaConsu rStage(Deser al zer<R> deser al zer) {
    t .deser al zer = deser al zer;
  }

  @Overr de
  protected vo d  n Stats() {
    super. n Stats();
    common nnerSetupStats();
  }

  pr vate vo d common nnerSetupStats() {
    pollCount = SearchCounter.export(getStageNa Pref x() + "_poll_count");
    deser al zat onErrorCount =
        SearchCounter.export(getStageNa Pref x() + "_deser al zat on_error_count");
    dropped ssages =
        SearchRateCounter.export(getStageNa Pref x() + "_dropped_ ssages");
  }

  @Overr de
  protected vo d  nnerSetupStats() {
    common nnerSetupStats();
  }

  @Overr de
  protected vo d do nnerPreprocess() {
    common nnerSetup();
    P pel neUt l.feedStartObjectToStage(t );
  }

  pr vate vo d common nnerSetup() {
    Precond  ons.c ckNotNull(kafkaCl ent d);
    Precond  ons.c ckNotNull(kafkaClusterPath);
    Precond  ons.c ckNotNull(kafkaTop cNa );

    kafkaConsu r = w reModule.newKafkaConsu r(
        kafkaClusterPath,
        deser al zer,
        kafkaCl ent d,
        kafkaConsu rGroup d,
        maxPollRecords);
     f (part  oned) {
      kafkaConsu r.ass gn(Collect ons.s ngletonL st(
          new Top cPart  on(kafkaTop cNa , w reModule.getPart  on())));
    } else {
      kafkaConsu r.subscr be(Collect ons.s ngleton(kafkaTop cNa ));
    }
  }

  @Overr de
  protected vo d  nnerSetup() {
    common nnerSetup();
  }

  @Overr de
  publ c vo d  nnerProcess(Object obj) throws StageExcept on {
    StageDr ver dr ver = ((P pel ne) stageContext).getStageDr ver(t );
    wh le (dr ver.getState() == StageDr ver.State.RUNN NG) {
      pollAndEm ();
    }

    LOG. nfo("StageDr ver state  s no longer RUNN NG, clos ng Kafka consu r.");
    closeKafkaConsu r();
  }

  @V s bleForTest ng
  vo d pollAndEm () throws StageExcept on {
    try {
      L st<R> records = poll();
      for (R record : records) {
        em AndCount(record);
      }
    } catch (P pel neStageExcept on e) {
      throw new StageExcept on(t , e);
    }
  }

  /***
   * Poll Kafka and get t   ems from t  top c. Record stats.
   * @return
   * @throws P pel neStageExcept on
   */
  publ c L st<R> pollFromTop c() throws P pel neStageExcept on {
    long start ngT   = startProcess ng();
    L st<R> polled ems = poll();
    endProcess ng(start ngT  );
    return polled ems;
  }

  pr vate L st<R> poll() throws P pel neStageExcept on  {
    L st<R> recordsFromKafka = new ArrayL st<>();
    try {
      Consu rRecords<Long, R> records = kafkaConsu r.poll(Durat on.ofM ll s(pollT  outMs));
      pollCount. ncre nt();
      records. erator().forEachRema n ng(record -> {
         f (dec derKey == null || Dec derUt l. sAva lableForRandomRec p ent(dec der, dec derKey)) {
          recordsFromKafka.add(record.value());
        } else {
          dropped ssages. ncre nt();
        }
      });

    } catch (Ser al zat onExcept on e) {
      deser al zat onErrorCount. ncre nt();
      LOG.error("Fa led to deser al ze t  value.", e);
    } catch (SaslAut nt cat onExcept on e) {
       f (Dec derUt l. sAva lableForRandomRec p ent(dec der, SHUT_DOWN_ON_AUTH_FA L)) {
        w reModule.getP pel neExcept onHandler()
            .logAndShutdown("Aut nt cat on error connect ng to Kafka broker: " + e);
      } else {
        throw new P pel neStageExcept on(t , "Kafka Aut nt cat on Error", e);
      }
    } catch (Except on e) {
      throw new P pel neStageExcept on(e);
    }

    return recordsFromKafka;
  }

  @V s bleForTest ng
  vo d closeKafkaConsu r() {
    try {
      kafkaConsu r.close();
      LOG. nfo("Kafka kafkaConsu r for {} was closed", getFullStageNa ());
    } catch (Except on e) {
      log.error("Fa led to close Kafka kafkaConsu r", e);
    }
  }

  @Overr de
  publ c vo d release() {
    closeKafkaConsu r();
    super.release();
  }

  @Overr de
  publ c vo d cleanupStageV2() {
    closeKafkaConsu r();
  }

  @SuppressWarn ngs("unused")  // set from p pel ne conf g
  publ c vo d setKafkaCl ent d(Str ng kafkaCl ent d) {
    t .kafkaCl ent d = kafkaCl ent d;
  }

  @SuppressWarn ngs("unused")  // set from p pel ne conf g
  publ c vo d setKafkaTop cNa (Str ng kafkaTop cNa ) {
    t .kafkaTop cNa  = kafkaTop cNa ;
  }

  @SuppressWarn ngs("unused")  // set from p pel ne conf g
  publ c vo d setKafkaConsu rGroup d(Str ng kafkaConsu rGroup d) {
    t .kafkaConsu rGroup d = kafkaConsu rGroup d;
  }

  @SuppressWarn ngs("unused")  // set from p pel ne conf g
  publ c vo d setMaxPollRecords( nt maxPollRecords) {
    t .maxPollRecords = maxPollRecords;
  }

  @SuppressWarn ngs("unused")  // set from p pel ne conf g
  publ c vo d setPollT  outMs( nt pollT  outMs) {
    t .pollT  outMs = pollT  outMs;
  }

  @SuppressWarn ngs("unused")  // set from p pel ne conf g
  publ c vo d setPart  oned(boolean part  oned) {
    t .part  oned = part  oned;
  }

  @SuppressWarn ngs("unused")  // set from p pel ne conf g
  publ c vo d setDec derKey(Str ng dec derKey) {
    t .dec derKey = dec derKey;
  }

  @V s bleForTest ng
  KafkaConsu r<Long, R> getKafkaConsu r() {
    return kafkaConsu r;
  }

  @SuppressWarn ngs("unused")  // set from p pel ne conf g
  publ c vo d setKafkaClusterPath(Str ng kafkaClusterPath) {
    t .kafkaClusterPath = kafkaClusterPath;
  }
}
