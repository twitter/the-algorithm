package com.tw ter.search.earlyb rd.arch ve;

 mport java. o. OExcept on;
 mport java.ut l.Date;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.base.Pred cate;

 mport org.apac .commons.lang.t  .FastDateFormat;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common. tr cs.SearchStatsRece ver;
 mport com.tw ter.search.common. tr cs.SearchStatsRece ver mpl;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndex ngEvent;
 mport com.tw ter.search.common.ut l. o.recordreader.RecordReader;
 mport com.tw ter.search.common.ut l.zktrylock.ZooKeeperTryLockFactory;
 mport com.tw ter.search.earlyb rd.Earlyb rd ndexConf g;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.docu nt.Docu ntFactory;
 mport com.tw ter.search.earlyb rd.docu nt.T etDocu nt;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd. ndex.Earlyb rdSeg ntFactory;
 mport com.tw ter.search.earlyb rd.part  on.Search ndex ng tr cSet;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntHdfsFlus r;
 mport com.tw ter.search.earlyb rd.part  on.Seg nt nfo;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntLoader;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntOpt m zer;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntSyncConf g;
 mport com.tw ter.search.earlyb rd.part  on.S mpleSeg nt ndexer;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdSearc rStats;

/**
 * G ven a seg nt, t  class c cks  f t  seg nt has an  ndex bu lt on HDFS:
 *    f not, use S mpleSeg nt ndexer to bu ld an  ndex
 *    f yes, load t  HDFS  ndex, bu ld a new  ndex for t  new status data wh ch has dates ne r
 *   than t  HDFS  ndex, t n append t  loaded HDFS  ndex.
 */
publ c class Arch veSeg ntUpdater {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Arch veSeg ntUpdater.class);

  pr vate f nal Seg ntSyncConf g sync;
  pr vate f nal Earlyb rd ndexConf g earlyb rd ndexConf g;
  pr vate f nal ZooKeeperTryLockFactory zkTryLockFactory;
  pr vate f nal SearchStatsRece ver statsRece ver = new SearchStatsRece ver mpl();
  pr vate f nal Search ndex ng tr cSet search ndex ng tr cSet =
      new Search ndex ng tr cSet(statsRece ver);
  pr vate f nal Earlyb rdSearc rStats searc rStats =
      new Earlyb rdSearc rStats(statsRece ver);
  pr vate f nal SearchRateCounter  ndexNewSeg nt =
      new SearchRateCounter(" ndex_new_seg nt");
  pr vate f nal SearchRateCounter updateEx st ngSeg nt =
      new SearchRateCounter("update_ex st ng_seg nt");
  pr vate f nal SearchRateCounter sk pEx st ngSeg nt =
      new SearchRateCounter("sk p_ex st ng_seg nt");
  pr vate Clock clock;

  publ c Arch veSeg ntUpdater(ZooKeeperTryLockFactory zooKeeperTryLockFactory,
                               Seg ntSyncConf g sync,
                               Earlyb rd ndexConf g earlyb rd ndexConf g,
                               Clock clock) {
    t .sync = sync;
    t .earlyb rd ndexConf g = earlyb rd ndexConf g;
    t .zkTryLockFactory = zooKeeperTryLockFactory;
    t .clock = clock;
  }

  pr vate boolean canUpdateSeg nt(Seg nt nfo seg nt nfo) {
     f (!(seg nt nfo.getSeg nt()  nstanceof Arch veSeg nt)) {
      LOG. nfo("only Arch veSeg nt  s ava lable for updat ng now: "
          + seg nt nfo);
      return false;
    }

     f (!seg nt nfo. sEnabled()) {
      LOG.debug("Seg nt  s d sabled: " + seg nt nfo);
      return false;
    }

     f (seg nt nfo. sComplete() || seg nt nfo. s ndex ng()
        || seg nt nfo.getSync nfo(). sLoaded()) {
      LOG.debug("Cannot update already  ndexed seg nt: " + seg nt nfo);
      return false;
    }

    return true;
  }

  /**
   * G ven a seg nt, c cks  f t  seg nt has an  ndex bu lt on HDFS:
   *    f not, use S mpleSeg nt ndexer to bu ld an  ndex
   *    f yes, load t  HDFS  ndex, bu ld a new  ndex for t  new status data wh ch has dates ne r
   *   than t  HDFS  ndex, t n append t  loaded HDFS  ndex.
   *
   * Returns w t r t  seg nt was successfully updated.
   */
  publ c boolean updateSeg nt(Seg nt nfo seg nt nfo) {
    Precond  ons.c ckArgu nt(seg nt nfo.getSeg nt()  nstanceof Arch veSeg nt);
     f (!canUpdateSeg nt(seg nt nfo)) {
      return false;
    }

     f (seg nt nfo. s ndex ng()) {
      LOG.error("Seg nt  s already be ng  ndexed: " + seg nt nfo);
      return false;
    }

    f nal Date hdfsEndDate = Arch veHDFSUt ls.getSeg ntEndDateOnHdfs(sync, seg nt nfo);
     f (hdfsEndDate == null) {
       ndexNewSeg nt. ncre nt();
       f (! ndexSeg nt(seg nt nfo, Arch veSeg nt.MATCH_ALL_DATE_PRED CATE)) {
        return false;
      }
    } else {
      f nal Date curEndDate = ((Arch veSeg nt) seg nt nfo.getSeg nt()).getDataEndDate();
       f (!hdfsEndDate.before(curEndDate)) {
        sk pEx st ngSeg nt. ncre nt();
        LOG. nfo("Seg nt  s up-to-date: " + seg nt nfo.getSeg nt().getT  Sl ce D()
            + " Found flus d seg nt on HDFS w h end date: "
            + FastDateFormat.get nstance("yyyyMMdd").format(hdfsEndDate));
        seg nt nfo.setComplete(true);
        seg nt nfo.getSync nfo().setFlus d(true);
        return true;
      }

      updateEx st ngSeg nt. ncre nt();
      LOG. nfo("Updat ng seg nt: " + seg nt nfo.getSeg nt().getT  Sl ce D()
          + "; new endDate w ll be " + FastDateFormat.get nstance("yyyyMMdd").format(curEndDate));

       f (!updateSeg nt(seg nt nfo, hdfsEndDate)) {
        return false;
      }
    }

    boolean success = Seg ntOpt m zer.opt m ze(seg nt nfo);
     f (!success) {
      // Clean up t  seg nt d r on local d sk
      seg nt nfo.deleteLocal ndexedSeg ntD rectory m d ately();
      LOG. nfo("Error opt m z ng seg nt: " + seg nt nfo);
      return false;
    }

    // Ver fy seg nt before upload ng.
    success = Arch veSeg ntVer f er.ver fySeg nt(seg nt nfo);
     f (!success) {
      seg nt nfo.deleteLocal ndexedSeg ntD rectory m d ately();
      LOG. nfo("Seg nt not uploaded to HDFS because   d d not pass ver f cat on: " + seg nt nfo);
      return false;
    }

    // upload t   ndex to HDFS
    success = new Seg ntHdfsFlus r(zkTryLockFactory, sync, false)
        .flushSeg ntToD skAndHDFS(seg nt nfo);
     f (success) {
      Arch veHDFSUt ls.deleteHdfsSeg ntD r(sync, seg nt nfo, false, true);
    } else {
      // Clean up t  seg nt d r on hdfs
      Arch veHDFSUt ls.deleteHdfsSeg ntD r(sync, seg nt nfo, true, false);
      LOG. nfo("Error upload ng seg nt to HDFS: " + seg nt nfo);
    }
    seg nt nfo.deleteLocal ndexedSeg ntD rectory m d ately();

    return success;
  }

  /**
   * Bu ld  ndex for t  g ven seg nt nfo. Only those statuses pass ng t  dateF lter are  ndexed.
   */
  pr vate boolean  ndexSeg nt(f nal Seg nt nfo seg nt nfo, Pred cate<Date> dateF lter) {
    Precond  ons.c ckArgu nt(seg nt nfo.getSeg nt()  nstanceof Arch veSeg nt);

    RecordReader<T etDocu nt> docu ntReader = null;
    try {
      Arch veSeg nt arch veSeg nt = (Arch veSeg nt) seg nt nfo.getSeg nt();
      Docu ntFactory<Thr ft ndex ngEvent> docu ntFactory =
          earlyb rd ndexConf g.createDocu ntFactory();
      docu ntReader = arch veSeg nt.getStatusRecordReader(docu ntFactory, dateF lter);

      // Read and  ndex t  statuses
      boolean success = new S mpleSeg nt ndexer(docu ntReader, search ndex ng tr cSet)
          . ndexSeg nt(seg nt nfo);
       f (!success) {
        // Clean up seg nt d r on local d sk
        seg nt nfo.deleteLocal ndexedSeg ntD rectory m d ately();
        LOG. nfo("Error  ndex ng seg nt: " + seg nt nfo);
      }

      return success;
    } catch ( OExcept on e) {
      seg nt nfo.deleteLocal ndexedSeg ntD rectory m d ately();
      LOG. nfo("Except on wh le  ndex ng seg nt: " + seg nt nfo, e);
      return false;
    } f nally {
       f (docu ntReader != null) {
        docu ntReader.stop();
      }
    }
  }

  /**
   * Load t   ndex bu lt on HDFS for t  g ven seg nt nfo,  ndex t  new data and append t 
   * HDFS  ndex to t  new  ndexed seg nt
   */
  pr vate boolean updateSeg nt(f nal Seg nt nfo seg nt nfo, f nal Date hdfsEndDate) {
    Seg nt nfo hdfsSeg nt nfo = loadSeg ntFromHdfs(seg nt nfo, hdfsEndDate);
     f (hdfsSeg nt nfo == null) {
      return  ndexSeg nt(seg nt nfo, Arch veSeg nt.MATCH_ALL_DATE_PRED CATE);
    }

    boolean success =  ndexSeg nt(seg nt nfo,  nput -> {
      //  're updat ng t  seg nt - only  ndex days after t  old end date,
      // and  're sure that t  prev ous days have already been  ndexed.
      return  nput.after(hdfsEndDate);
    });
     f (!success) {
      LOG.error("Error  ndex ng new data: " + seg nt nfo);
      return  ndexSeg nt(seg nt nfo, Arch veSeg nt.MATCH_ALL_DATE_PRED CATE);
    }

    // Now, append t   ndex loaded from hdfs
    try {
      seg nt nfo.get ndexSeg nt().append(hdfsSeg nt nfo.get ndexSeg nt());
      hdfsSeg nt nfo.deleteLocal ndexedSeg ntD rectory m d ately();
      LOG. nfo("Deleted local seg nt d rector es w h end date " + hdfsEndDate + " : "
          + seg nt nfo);
    } catch ( OExcept on e) {
      LOG.warn("Caught  OExcept on wh le append ng seg nt " + hdfsSeg nt nfo.getSeg ntNa (), e);
      hdfsSeg nt nfo.deleteLocal ndexedSeg ntD rectory m d ately();
      seg nt nfo.deleteLocal ndexedSeg ntD rectory m d ately();
      return false;
    }

    seg nt nfo.setComplete(true);
    return true;
  }

  /**
   * Load t   ndex bu lt on HDFS for t  g ven seg nt nfo and end date
   */
  pr vate Seg nt nfo loadSeg ntFromHdfs(f nal Seg nt nfo seg nt nfo, f nal Date hdfsEndDate) {
    Precond  ons.c ckArgu nt(seg nt nfo.getSeg nt()  nstanceof Arch veSeg nt);

    Arch veSeg nt seg nt = new Arch veSeg nt(
        seg nt nfo.getT  Sl ce D(),
        Earlyb rdConf g.getMaxSeg ntS ze(),
        seg nt nfo.getNumPart  ons(),
        seg nt nfo.getSeg nt().getHashPart  on D(),
        hdfsEndDate);
    Earlyb rdSeg ntFactory factory = new Earlyb rdSeg ntFactory(
        earlyb rd ndexConf g,
        search ndex ng tr cSet,
        searc rStats,
        clock);

    Seg nt nfo hdfsSeg nt nfo;

    try {
      hdfsSeg nt nfo = new Seg nt nfo(seg nt,  factory, sync);
      Cr  calExcept onHandler cr  calExcept onHandler =
          new Cr  calExcept onHandler();

      boolean success = new Seg ntLoader(sync, cr  calExcept onHandler)
          .load(hdfsSeg nt nfo);
       f (!success) {
        //  f not successful, seg ntLoader has already cleaned up t  local d r.
        LOG. nfo("Error load ng hdfs seg nt " + hdfsSeg nt nfo
            + ", bu ld ng seg nt from scratch.");
        hdfsSeg nt nfo = null;
      }
    } catch ( OExcept on e) {
      LOG.error("Except on wh le load ng seg nt from hdfs: " + seg nt nfo, e);
      hdfsSeg nt nfo = null;
    }

    return hdfsSeg nt nfo;
  }
}
