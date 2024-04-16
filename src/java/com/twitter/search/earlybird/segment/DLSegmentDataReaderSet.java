package com.tw ter.search.earlyb rd.seg nt;

 mport java. o. OExcept on;
 mport java.ut l.HashMap;
 mport java.ut l.Map;
 mport java.ut l.Opt onal;
 mport java.ut l.concurrent.T  Un ;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Funct on;
 mport com.google.common.base.Precond  ons;

 mport org.apac .thr ft.TExcept on;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchCustomGauge;
 mport com.tw ter.search.common. tr cs.SearchRequestStats;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdThr ftDocu ntUt l;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndex ngEvent;
 mport com.tw ter.search.common.ut l. o.ReaderW hStatsFactory;
 mport com.tw ter.search.common.ut l. o.Transform ngRecordReader;
 mport com.tw ter.search.common.ut l. o.dl.DLMult StreamReader;
 mport com.tw ter.search.common.ut l. o.dl.DLReaderWr erFactory;
 mport com.tw ter.search.common.ut l. o.dl.DLT  stampedReaderFactory;
 mport com.tw ter.search.common.ut l. o.dl.Seg ntDLUt l;
 mport com.tw ter.search.common.ut l. o.recordreader.RecordReader;
 mport com.tw ter.search.common.ut l. o.recordreader.RecordReaderFactory;
 mport com.tw ter.search.common.ut l.thr ft.Thr ftUt ls;
 mport com.tw ter.search.earlyb rd.Earlyb rd ndexConf g;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.docu nt.Docu ntFactory;
 mport com.tw ter.search.earlyb rd.docu nt.T etDocu nt;
 mport com.tw ter.search.earlyb rd.part  on.Seg nt nfo;

publ c class DLSeg ntDataReaderSet  mple nts Seg ntDataReaderSet {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(DLSeg ntDataReaderSet.class);

  publ c stat c f nal SearchRequestStats STATUS_DL_READ_STATS =
      SearchRequestStats.export("status_dlreader", T  Un .M CROSECONDS, false);
  pr vate stat c f nal SearchRequestStats UPDATE_EVENT_DL_READ_STATS =
      SearchRequestStats.export("update_events_dlreader", T  Un .M CROSECONDS, false);
  // T  number of t ets not  ndexed because t y fa led deser al zat on.
  pr vate stat c f nal SearchCounter STATUS_SK PPED_DUE_TO_FA LED_DESER AL ZAT ON_COUNTER =
      SearchCounter.export("statuses_sk pped_due_to_fa led_deser al zat on");

  @V s bleForTest ng
  publ c stat c f nal  nt FRESH_READ_THRESHOLD = ( nt) T  Un .M NUTES.toM ll s(1);

  pr vate f nal  nt docu ntReadFreshnessThreshold =
      Earlyb rdConf g.get nt("docu nts_reader_freshness_threshold_m ll s", 10000);
  pr vate f nal  nt updateReadFreshnessThreshold =
      Earlyb rdConf g.get nt("updates_freshness_threshold_m ll s", FRESH_READ_THRESHOLD);
  pr vate f nal  nt dlReaderVers on = Earlyb rdConf g.get nt("dl_reader_vers on");

  pr vate f nal DLReaderWr erFactory dlFactory;
  pr vate f nal RecordReaderFactory<byte[]> dlUpdateEventsFactory;
  pr vate f nal Earlyb rd ndexConf g  ndexConf g;
  pr vate f nal Clock clock;

  pr vate RecordReader<T etDocu nt> docu ntReader;

  // RecordReaders for update events that span all l ve seg nts.
  pr vate f nal RecordReader<Thr ftVers onedEvents> updateEventsReader;
  pr vate f nal DLMult StreamReader updateEventsMult Reader;
  pr vate f nal Map<Long, RecordReader<Thr ftVers onedEvents>> updateEventReaders = new HashMap<>();

  DLSeg ntDataReaderSet(
      DLReaderWr erFactory dlFactory,
      f nal Earlyb rd ndexConf g  ndexConf g,
      Clock clock) throws  OExcept on {
    t .dlFactory = dlFactory;
    t . ndexConf g =  ndexConf g;
    t .clock = clock;

    t .dlUpdateEventsFactory = new ReaderW hStatsFactory(
        new DLT  stampedReaderFactory(dlFactory, clock, updateReadFreshnessThreshold),
        UPDATE_EVENT_DL_READ_STATS);
    t .updateEventsMult Reader =
        new DLMult StreamReader("update_events", dlUpdateEventsFactory, true, clock);
    t .updateEventsReader =
        new Transform ngRecordReader<>(updateEventsMult Reader, record ->
            (record != null) ? deser al zeTVE(record.getBytes()) : null);

    SearchCustomGauge.export("open_dl_update_events_streams", updateEventReaders::s ze);
  }

  pr vate Thr ftVers onedEvents deser al zeTVE(byte[] bytes) {
    Thr ftVers onedEvents event = new Thr ftVers onedEvents();
    try {
      Thr ftUt ls.fromCompactB naryFormat(bytes, event);
      return event;
    } catch (TExcept on e) {
      LOG.error("error deser al z ng TVE", e);
      return null;
    }
  }

  @Overr de
  publ c vo d attachDocu ntReaders(Seg nt nfo seg nt nfo) throws  OExcept on {
    // Close any docu nt reader left open before.
     f (docu ntReader != null) {
      LOG.warn("Prev ous docu ntReader not closed: {}", docu ntReader);
      completeSeg ntDocs(seg nt nfo);
    }
    docu ntReader = newDocu ntReader(seg nt nfo);
  }

  @Overr de
  publ c vo d attachUpdateReaders(Seg nt nfo seg nt nfo) throws  OExcept on {
     f (updateEventsMult Reader == null) {
      return;
    }

    Str ng seg ntNa  = seg nt nfo.getSeg ntNa ();
     f (getUpdateEventsReaderForSeg nt(seg nt nfo) != null) {
      LOG. nfo("Update events reader for seg nt {}  s already attac d.", seg ntNa );
      return;
    }

    long updateEventStreamOffsetT  stamp = seg nt nfo.getUpdatesStreamOffsetT  stamp();
    LOG. nfo("Attach ng update events reader for seg nt {} w h t  stamp: {}.",
             seg ntNa , updateEventStreamOffsetT  stamp);

    Str ng top c = Seg ntDLUt l.getDLTop cForUpdateEvents(seg ntNa , dlReaderVers on);
    RecordReader<byte[]> recordReader =
        dlUpdateEventsFactory.newRecordReaderForT  stamp(top c, updateEventStreamOffsetT  stamp);
    updateEventsMult Reader.addRecordReader(recordReader, top c);
    updateEventReaders.put(seg nt nfo.getT  Sl ce D(),
        new Transform ngRecordReader<>(recordReader, t ::deser al zeTVE));
  }

  @Overr de
  publ c vo d stopAll() {
     f (docu ntReader != null) {
      docu ntReader.close();
    }
     f (updateEventsReader != null) {
      updateEventsReader.close();
    }
    try {
      dlFactory.close();
    } catch ( OExcept on e) {
      LOG.error("Except on wh le clos ng DL factory", e);
    }
  }

  @Overr de
  publ c vo d completeSeg ntDocs(Seg nt nfo seg nt nfo) {
     f (docu ntReader != null) {
      docu ntReader.close();
      docu ntReader = null;
    }
  }

  @Overr de
  publ c vo d stopSeg ntUpdates(Seg nt nfo seg nt nfo) {
     f (updateEventsMult Reader != null) {
      updateEventsMult Reader.removeStream(
          Seg ntDLUt l.getDLTop cForUpdateEvents(seg nt nfo.getSeg ntNa (), dlReaderVers on));
      updateEventReaders.remove(seg nt nfo.getT  Sl ce D());
    }
  }

  @Overr de
  publ c RecordReader<T etDocu nt> newDocu ntReader(Seg nt nfo seg nt nfo) throws  OExcept on {
    Str ng top c = Seg ntDLUt l.getDLTop cForT ets(seg nt nfo.getSeg ntNa (),
        Earlyb rdConf g.getPengu nVers on(), dlReaderVers on);
    f nal long t  Sl ce d = seg nt nfo.getT  Sl ce D();
    f nal Docu ntFactory<Thr ft ndex ngEvent> docFactory =  ndexConf g.createDocu ntFactory();

    // Create t  underly ng DLRecordReader wrapped w h t  t et reader stats.
    RecordReader<byte[]> dlReader = new ReaderW hStatsFactory(
        new DLT  stampedReaderFactory(
            dlFactory,
            clock,
            docu ntReadFreshnessThreshold),
        STATUS_DL_READ_STATS)
        .newRecordReader(top c);

    // Create t  wrapped reader wh ch transforms ser al zed byte[] to T etDocu nt.
    return new Transform ngRecordReader<>(
        dlReader,
        new Funct on<byte[], T etDocu nt>() {
          @Overr de
          publ c T etDocu nt apply(byte[]  nput) {
            Thr ft ndex ngEvent event = new Thr ft ndex ngEvent();
            try {
              Thr ftUt ls.fromCompactB naryFormat( nput, event);
            } catch (TExcept on e) {
              LOG.error("Could not deser al ze status docu nt", e);
              STATUS_SK PPED_DUE_TO_FA LED_DESER AL ZAT ON_COUNTER. ncre nt();
              return null;
            }

            Precond  ons.c ckNotNull(event.getDocu nt());
            return new T etDocu nt(
                docFactory.getStatus d(event),
                t  Sl ce d,
                Earlyb rdThr ftDocu ntUt l.getCreatedAtMs(event.getDocu nt()),
                docFactory.newDocu nt(event));
          }
        });
  }

  @Overr de
  publ c RecordReader<T etDocu nt> getDocu ntReader() {
    return docu ntReader;
  }

  @Overr de
  publ c RecordReader<Thr ftVers onedEvents> getUpdateEventsReader() {
    return updateEventsReader;
  }

  @Overr de
  publ c RecordReader<Thr ftVers onedEvents> getUpdateEventsReaderForSeg nt(
      Seg nt nfo seg nt nfo) {
    return updateEventReaders.get(seg nt nfo.getT  Sl ce D());
  }

  @Overr de
  publ c Opt onal<Long> getUpdateEventsStreamOffsetForSeg nt(Seg nt nfo seg nt nfo) {
    Str ng top c =
        Seg ntDLUt l.getDLTop cForUpdateEvents(seg nt nfo.getSeg ntNa (), dlReaderVers on);
    return updateEventsMult Reader.getUnderly ngOffsetForSeg ntW hTop c(top c);
  }

  @Overr de
  publ c boolean allCaughtUp() {
    return ((getDocu ntReader() == null) || getDocu ntReader(). sCaughtUp())
        && ((getUpdateEventsReader() == null) || getUpdateEventsReader(). sCaughtUp());
  }
}
