package com.tw ter.search.earlyb rd.seg nt;

 mport java. o. OExcept on;
 mport java.ut l.Opt onal;

 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;
 mport com.tw ter.search.common.ut l. o.recordreader.RecordReader;
 mport com.tw ter.search.earlyb rd.docu nt.T etDocu nt;
 mport com.tw ter.search.earlyb rd.part  on.Seg nt nfo;

/**
 * Seg ntDataReaderSet prov des an  nterface to create and manage t  var ous
 * RecordReaders used to  ndex Earlyb rd seg nts.
 */
publ c  nterface Seg ntDataReaderSet {
  /**
   *  nstruct t  docu nt RecordReaders ( .e. docu nt, geo, ... as appropr ate) to read from t 
   * seg nt.
   */
  vo d attachDocu ntReaders(Seg nt nfo seg nt nfo) throws  OExcept on;

  /**
   *  nstruct t  reader set to add seg nt to non-docu nt RecordReaders (deletes, features, etc.)
   */
  vo d attachUpdateReaders(Seg nt nfo seg nt nfo) throws  OExcept on;

  /**
   * Mark a seg nt as "complete", denot ng that   are done read ng docu nt records from  .
   *
   * T   nstructs t  reader set to stop read ng docu nts from t  seg nt ( f   hasn't
   * already), although for now geo-docu nt  records can st ll be read.  Updates RecordReaders
   * (deletes, etc.) may cont nue to read entr es for t  seg nt.
   */
  vo d completeSeg ntDocs(Seg nt nfo seg nt nfo);

  /**
   * T   nstructs t  reader set to stop read ng updates for t  Seg nt.   
   * should remove t  seg nt from all non-docu nt RecordReaders (deletes, etc.)
   */
  vo d stopSeg ntUpdates(Seg nt nfo seg nt nfo);

  /**
   * Stops all RecordReaders and closes all res ces.
   */
  vo d stopAll();

  /**
   * Returns true  f all RecordReaders are 'caught up' w h t  data s ces t y
   * are read ng from.  T  m ght  an that t  end of a f le has been reac d,
   * or that   are wa  ng/poll ng for new records from an append-only database.
   */
  boolean allCaughtUp();

  /**
   * Create a new Docu ntReader for t  g ven seg nt that  s not managed by t  set.
   */
  RecordReader<T etDocu nt> newDocu ntReader(Seg nt nfo seg nt nfo) throws Except on;

  /**
   * Returns t  docu nt reader for t  current seg nt.
   */
  RecordReader<T etDocu nt> getDocu ntReader();

  /**
   * Returns a comb ned update events reader for all seg nts.
   */
  RecordReader<Thr ftVers onedEvents> getUpdateEventsReader();

  /**
   * Returns t  update events reader for t  g ven seg nt.
   */
  RecordReader<Thr ftVers onedEvents> getUpdateEventsReaderForSeg nt(Seg nt nfo seg nt nfo);

  /**
   * Returns t  offset  n t  update events stream for t  g ven seg nt that t  earlyb rd should
   * start  ndex ng from.
   */
  Opt onal<Long> getUpdateEventsStreamOffsetForSeg nt(Seg nt nfo seg nt nfo);
}
