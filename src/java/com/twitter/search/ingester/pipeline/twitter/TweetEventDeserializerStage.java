package com.tw ter.search. ngester.p pel ne.tw ter;
 mport com.google.common.annotat ons.V s bleForTest ng;
 mport org.apac .commons.p pel ne.StageExcept on;
 mport org.apac .commons.p pel ne.val dat on.Consu dTypes;
 mport org.apac .commons.p pel ne.val dat on.ProducedTypes;
 mport org.apac .thr ft.TDeser al zer;
 mport org.apac .thr ft.TExcept on;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;
 mport com.tw ter.search.common.debug.DebugEventUt l;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search. ngester.model. ngesterT etEvent;
 mport com.tw ter.search. ngester.model.KafkaRawRecord;
 mport com.tw ter.search. ngester.p pel ne.ut l.P pel neStageRunt  Except on;

/**
 * Deser al zes {@l nk KafkaRawRecord}  nto  ngesterT etEvent and em s those.
 */
@Consu dTypes(KafkaRawRecord.class)
@ProducedTypes( ngesterT etEvent.class)
publ c class T etEventDeser al zerStage extends Tw terBaseStage
    <KafkaRawRecord,  ngesterT etEvent> {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(T etEventDeser al zerStage.class);

  // L m  how much t  logs get polluted
  pr vate stat c f nal  nt MAX_OOM_SER AL ZED_BYTES_LOGGED = 5000;
  pr vate stat c f nal char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

  pr vate f nal TDeser al zer deser al zer = new TDeser al zer();

  pr vate SearchCounter outOf moryErrors;
  pr vate SearchCounter outOf moryErrors2;
  pr vate SearchCounter totalEventsCount;
  pr vate SearchCounter val dEventsCount;
  pr vate SearchCounter deser al zat onErrorsCount;

  @Overr de
  publ c vo d  n Stats() {
    super. n Stats();
     nnerSetupStats();
  }

  @Overr de
  protected vo d  nnerSetupStats() {
    outOf moryErrors = SearchCounter.export(getStageNa Pref x() + "_out_of_ mory_errors");
    outOf moryErrors2 = SearchCounter.export(getStageNa Pref x() + "_out_of_ mory_errors_2");
    totalEventsCount = SearchCounter.export(getStageNa Pref x() + "_total_events_count");
    val dEventsCount = SearchCounter.export(getStageNa Pref x() + "_val d_events_count");
    deser al zat onErrorsCount =
        SearchCounter.export(getStageNa Pref x() + "_deser al zat on_errors_count");
  }

  @Overr de
  publ c vo d  nnerProcess(Object obj) throws StageExcept on {
     f (!(obj  nstanceof KafkaRawRecord)) {
      throw new StageExcept on(t , "Object  s not a KafkaRawRecord: " + obj);
    }

    KafkaRawRecord kafkaRecord = (KafkaRawRecord) obj;
     ngesterT etEvent t etEvent = tryDeser al zeRecord(kafkaRecord);

     f (t etEvent != null) {
      em AndCount(t etEvent);
    }
  }

  @Overr de
  protected  ngesterT etEvent  nnerRunStageV2(KafkaRawRecord kafkaRawRecord) {
     ngesterT etEvent  ngesterT etEvent = tryDeser al zeRecord(kafkaRawRecord);
     f ( ngesterT etEvent == null) {
      throw new P pel neStageRunt  Except on("fa led to deser al ze KafkaRawRecord : "
          + kafkaRawRecord);
    }
    return  ngesterT etEvent;
  }

  pr vate  ngesterT etEvent tryDeser al zeRecord(KafkaRawRecord kafkaRecord) {
    try {
      totalEventsCount. ncre nt();
       ngesterT etEvent t etEvent = deser al ze(kafkaRecord);
      val dEventsCount. ncre nt();
      return t etEvent;
    } catch (OutOf moryError e) {
      try {
        outOf moryErrors. ncre nt();
        byte[] bytes = kafkaRecord.getData();
         nt l m  = Math.m n(bytes.length, MAX_OOM_SER AL ZED_BYTES_LOGGED);
        Str ngBu lder sb = new Str ngBu lder(2 * l m  + 100)
            .append("OutOf moryError deser al z ng ").append(bytes.length).append(" bytes: ");
        appendBytesAs x(sb, bytes, MAX_OOM_SER AL ZED_BYTES_LOGGED);
        LOG.error(sb.toStr ng(), e);
      } catch (OutOf moryError e2) {
        outOf moryErrors2. ncre nt();
      }
    }

    return null;

  }

  pr vate  ngesterT etEvent deser al ze(KafkaRawRecord kafkaRecord) {
    try {
       ngesterT etEvent  ngesterT etEvent = new  ngesterT etEvent();
      synchron zed (t ) {
        deser al zer.deser al ze( ngesterT etEvent, kafkaRecord.getData());
      }
      // Record t  created_at t   and t n   f rst saw t  t et  n t   ngester for track ng
      // down t   ngest on p pel ne.
      addDebugEventsTo ncom ngT et( ngesterT etEvent, kafkaRecord.getReadAtT  stampMs());
      return  ngesterT etEvent;
    } catch (TExcept on e) {
      LOG.error("Unable to deser al ze T etEventData", e);
      deser al zat onErrorsCount. ncre nt();
    }
    return null;
  }

  pr vate vo d addDebugEventsTo ncom ngT et(
       ngesterT etEvent  ngesterT etEvent, long readAtT  stampMs) {
    DebugEventUt l.setCreatedAtDebugEvent(
         ngesterT etEvent,  ngesterT etEvent.getFlags().getT  stamp_ms());
    DebugEventUt l.setProcess ngStartedAtDebugEvent( ngesterT etEvent, readAtT  stampMs);

    // T  T etEventDeser al zerStage takes  n a byte[] representat on of a t et, so debug events
    // are not automat cally appended by Tw terBaseStage.   do that expl c ly  re.
    DebugEventUt l.addDebugEvent( ngesterT etEvent, getFullStageNa (), clock.nowM ll s());
  }

  @V s bleForTest ng
  stat c vo d appendBytesAs x(Str ngBu lder sb, byte[] bytes,  nt maxLength) {
     nt l m  = Math.m n(bytes.length, maxLength);
    for ( nt j = 0; j < l m ; j++) {
      sb.append(HEX_ARRAY[(bytes[j] >>> 4) & 0x0F]);
      sb.append(HEX_ARRAY[bytes[j] & 0x0F]);
    }
  }
}
