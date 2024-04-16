package com.tw ter.search.earlyb rd.seg nt;

 mport java.ut l.Opt onal;

 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;
 mport com.tw ter.search.common.ut l. o.EmptyRecordReader;
 mport com.tw ter.search.common.ut l. o.recordreader.RecordReader;
 mport com.tw ter.search.earlyb rd.docu nt.T etDocu nt;
 mport com.tw ter.search.earlyb rd.part  on.Seg nt nfo;

/**
 * A Seg ntDataReaderSet that returns no data. Uses a Docu ntReader that  s
 * always caught up, but never gets exhausted.
 * Can be used for br ng ng up an earlyb rd aga nst a stat c set of seg nts,
 * and w ll not  ncorporate any new updates.
 */
publ c class EmptySeg ntDataReaderSet  mple nts Seg ntDataReaderSet {
  publ c stat c f nal EmptySeg ntDataReaderSet  NSTANCE = new EmptySeg ntDataReaderSet();

  @Overr de
  publ c vo d attachDocu ntReaders(Seg nt nfo seg nt nfo) {
  }

  @Overr de
  publ c vo d attachUpdateReaders(Seg nt nfo seg nt nfo) {
  }

  @Overr de
  publ c vo d completeSeg ntDocs(Seg nt nfo seg nt nfo) {
  }

  @Overr de
  publ c vo d stopSeg ntUpdates(Seg nt nfo seg nt nfo) {
  }

  @Overr de
  publ c vo d stopAll() {
  }

  @Overr de
  publ c boolean allCaughtUp() {
    // ALWAYS CAUGHT UP
    return true;
  }

  @Overr de
  publ c RecordReader<T etDocu nt> newDocu ntReader(Seg nt nfo seg nt nfo)
      throws Except on {
    return null;
  }

  @Overr de
  publ c RecordReader<T etDocu nt> getDocu ntReader() {
    return new EmptyRecordReader<>();
  }

  @Overr de
  publ c RecordReader<Thr ftVers onedEvents> getUpdateEventsReader() {
    return null;
  }

  @Overr de
  publ c RecordReader<Thr ftVers onedEvents> getUpdateEventsReaderForSeg nt(
      Seg nt nfo seg nt nfo) {
    return null;
  }

  @Overr de
  publ c Opt onal<Long> getUpdateEventsStreamOffsetForSeg nt(Seg nt nfo seg nt nfo) {
    return Opt onal.of(0L);
  }
}
