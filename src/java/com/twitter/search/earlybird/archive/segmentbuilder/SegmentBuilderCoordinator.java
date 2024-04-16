package com.tw ter.search.earlyb rd.arch ve.seg ntbu lder;

 mport java. o. OExcept on;
 mport java.ut l.Date;
 mport java.ut l.Opt onal;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport org.apac .hadoop.fs.F leSystem;
 mport org.apac .hadoop.fs.Path;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.quant y.Amount;
 mport com.tw ter.common.quant y.T  ;
 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common.database.DatabaseConf g;
 mport com.tw ter.search.common.ut l.zktrylock.TryLock;
 mport com.tw ter.search.common.ut l.zktrylock.ZooKeeperTryLockFactory;
 mport com.tw ter.search.earlyb rd.arch ve.Da lyStatusBatc s;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdProperty;
 mport com.tw ter.search.earlyb rd.ut l.ScrubGenUt l;
 mport com.tw ter.search.earlyb rd.part  on.HdfsUt l;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntSyncConf g;
 mport com.tw ter.ut l.Durat on;

/**
 * Coord nate bet en seg nt bu lders for scrubb ng p pel ne.
 * W n seg nt bu lder  s runn ng, all of t m w ll try to f nd a HDFS f le  nd cat ng  f data  s
 * ready.  f t  f le does not ex st, only one of t m w ll go through t  f les and see  f
 * scrubb ng p pel ne has generated all data for t  scrub gen.
 *
 *  f t   nstance that got t  lock found all data,   st ll ex sts, because ot rw se   w ll
 * have one s ngle seg ntbu lder  nstance try ng to bu ld all seg nts, wh ch  s not what   want.
 * But  f   ex sts, t n t  next t   all seg ntbu lder  nstances are sc duled, t y w ll all
 * f nd t  f le, and w ll start bu ld ng seg nts.
 */
class Seg ntBu lderCoord nator {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Seg ntBu lderCoord nator.class);

  pr vate stat c f nal Amount<Long, T  > ZK_LOCK_EXP RAT ON_M N = Amount.of(5L, T  .M NUTES);
  pr vate stat c f nal Str ng SEGMENT_BU LDER_SYNC_NODE = "scrub_gen_data_sync";
  pr vate stat c f nal Str ng SEGMENT_BU LDER_SYNC_ZK_PATH =
      Earlyb rdProperty.ZK_APP_ROOT.get() + "/seg nt_bu lder_sync";
  pr vate stat c f nal Str ng DATA_FULLY_BU LT_F LE = "_data_fully_bu lt";
  stat c f nal  nt F RST_ NSTANCE = 0;

  pr vate stat c f nal long NON_F RST_ NSTANCE_SLEEP_BEFORE_RETRY_DURAT ON_MS =
      Durat on.fromH s(1). nM ll s();

  pr vate f nal ZooKeeperTryLockFactory zkTryLockFactory;
  pr vate f nal Seg ntSyncConf g syncConf g;
  pr vate f nal Opt onal<Date> scrubGenDayOpt;
  pr vate f nal Opt onal<Str ng> scrubGenOpt;
  pr vate f nal Clock clock;

  Seg ntBu lderCoord nator(
      ZooKeeperTryLockFactory zkTryLockFactory, Seg ntSyncConf g syncConf g, Clock clock) {
    t .zkTryLockFactory = zkTryLockFactory;
    t .syncConf g = syncConf g;
    t .scrubGenOpt = syncConf g.getScrubGen();
    t .scrubGenDayOpt = scrubGenOpt.map(ScrubGenUt l::parseScrubGenToDate);
    t .clock = clock;
  }


  publ c boolean  sScrubGenDataFullyBu lt( nt  nstanceNumber) {
    // Only seg nt bu lder that takes scrub gen should use  sPart  on ngOutputReady to coord nate
    Precond  ons.c ckArgu nt(scrubGenDayOpt. sPresent());

    f nal F leSystem hdfs;
    try {
      hdfs = HdfsUt l.getHdfsF leSystem();
    } catch ( OExcept on e) {
      LOG.error("Could not create HDFS f le system.", e);
      return false;
    }

    return  sScrubGenDataFullyBu lt(
         nstanceNumber,
        scrubGenDayOpt.get(),
        NON_F RST_ NSTANCE_SLEEP_BEFORE_RETRY_DURAT ON_MS,
        hdfs
    );
  }

  @V s bleForTest ng
  boolean  sScrubGenDataFullyBu lt(
       nt  nstanceNumber,
      Date scrubGenDay,
      long nonF rst nstanceSleepBeforeRetryDurat on,
      F leSystem hdfs) {
    // C ck  f t  scrub gen has been fully bu lt f le ex sts.
     f (c ckHaveScrubGenDataFullyBu ltF leOnHdfs(hdfs)) {
      return true;
    }

    //  f   doesn't ex st, let f rst  nstance see  f scrub gen has been fully bu lt and create t 
    // f le.
     f ( nstanceNumber == F RST_ NSTANCE) {
      //    re m ss ng so  data on HDFS for t  scrub gen  n prev ous run,
      // but   m ght've gotten more data  n t   ant  , c ck aga n.
      // Only allow  nstance 0 to do t  ma nly for 2 reasons:
      // 1) S nce  nstances are sc duled  n batc s,  's poss ble that a  nstance from latter
      // batch f nd t  fully bu lt f le  n hdfs and start process ng.   end up do ng work w h
      // only part al  nstances.
      // 2)  f   sleep before   release lock,  's hard to est mate how long a  nstance w ll
      // be sc duled.
      // For determ n st c reason,   s mpl fy a b  and only allow  nstance 0 to c ck and wr e
      // data  s fully bu ld f le to hdfs.
      try {
        c ck fScrubGenData sFullyBu lt(hdfs, scrubGenDay);
      } catch ( OExcept on e) {
        LOG.error("Fa led to grab lock and c ck scrub gen data.", e);
      }
    } else {
      // for all ot r  nstances, sleep for a b  to g ve t   for f rst  nstance to c ck  f scrub
      // gen has been fully bu lt and create t  f le, t n c ck aga n.
      try {
        LOG. nfo(
            "Sleep ng for {} ms before re-c ck ng  f scrub gen has been fully bu lt f le ex sts",
            nonF rst nstanceSleepBeforeRetryDurat on);
        clock.wa For(nonF rst nstanceSleepBeforeRetryDurat on);
        return c ckHaveScrubGenDataFullyBu ltF leOnHdfs(hdfs);
      } catch ( nterruptedExcept on e) {
        LOG.warn(" nterrupted w n sleep ng before re-c ck ng  f scrub gen has been fully bu lt "
            + "f le ex sts", e);
      }
    }

    //  f hasSuccessF leToHdfs returns false, t n should always return false  n t  end.
    // next run w ll f nd success f le for t  scrub gen and move forward.
    return false;
  }

  pr vate vo d c ck fScrubGenData sFullyBu lt(
      F leSystem hdfs, Date scrubGenDay) throws  OExcept on {
    // Bu ld t  lock, try to acqu re  , and c ck t  data on HDFS
    TryLock lock = zkTryLockFactory.createTryLock(
        DatabaseConf g.getLocalHostna (),
        SEGMENT_BU LDER_SYNC_ZK_PATH,
        SEGMENT_BU LDER_SYNC_NODE,
        ZK_LOCK_EXP RAT ON_M N);
    Precond  ons.c ckState(scrubGenOpt. sPresent());
    Str ng scrubGen = scrubGenOpt.get();

    lock.tryW hLock(() -> {
      LOG. nfo(Str ng.format(
          "Obta ned ZK lock to c ck  f data for scrub gen %s  s ready.", scrubGen));
      f nal Da lyStatusBatc s d rectory =
          new Da lyStatusBatc s(zkTryLockFactory, scrubGenDay);
       f (d rectory. sScrubGenDataFullyBu lt(hdfs)
          && createScrubGenDataFullyBu ltF leOnHdfs(hdfs)) {
        LOG. nfo(Str ng.format("All data for scrub gen %s  s ready.", scrubGen));
      } else {
        LOG. nfo(Str ng.format("Data for scrub gen %s  s not ready yet.", scrubGen));
      }
    });
  }

  pr vate boolean createScrubGenDataFullyBu ltF leOnHdfs(F leSystem fs) {
    Path path = getScrubGenDataFullyBu ltF lePath();
    try {
      fs.mkd rs(new Path(statusReadyHDFSPath()));
       f (fs.createNewF le(path)) {
        LOG. nfo("Successfully created f le " + path + " on HDFS.");
        return true;
      } else {
        LOG.warn("Fa led to create f le " + path + " on HDFS.");
      }
    } catch ( OExcept on e) {
      LOG.error("Fa led to create f le on HDFS " + path.toStr ng(), e);
    }
    return false;
  }

  pr vate boolean c ckHaveScrubGenDataFullyBu ltF leOnHdfs(F leSystem fs) {
    Path path = getScrubGenDataFullyBu ltF lePath();
    try {
      boolean ret = fs.ex sts(path);
      LOG. nfo("C ck ng  f f le ex sts show ng scrubgen  s fully bu lt.");
      LOG. nfo("Path c cked: {}, Ex st c ck: {}", path, ret);
      return ret;
    } catch ( OExcept on e) {
      LOG.error("Fa led to c ck f le on HDFS " + path.toStr ng(), e);
      return false;
    }
  }

  @V s bleForTest ng
  Path getScrubGenDataFullyBu ltF lePath() {
    return new Path(statusReadyHDFSPath(), DATA_FULLY_BU LT_F LE);
  }

  @V s bleForTest ng
  Str ng statusReadyHDFSPath() {
    return syncConf g.getHdfsSeg ntSyncRootD r() + "/seg nt_bu lder_sync";
  }
}
