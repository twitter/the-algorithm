package com.tw ter.search.earlyb rd.part  on;

 mport java. o.F le;
 mport java. o. OExcept on;
 mport java.ut l.concurrent.T  Un ;

 mport org.apac .commons. o.F leUt ls;
 mport org.apac .commons. o. OUt ls;
 mport org.apac .hadoop.fs.F leStatus;
 mport org.apac .hadoop.fs.F leSystem;
 mport org.apac .hadoop.fs.Path;
 mport org.apac .lucene.store.D rectory;
 mport org.apac .lucene.store.FSD rectory;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common. tr cs.T  r;
 mport com.tw ter.search.common.part  on ng.snowflakeparser.Snowflake dParser;
 mport com.tw ter.search.common.ut l. o.flushable.Pers stentF le;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd.except on.FlushVers onM smatchExcept on;
 mport com.tw ter.search.earlyb rd.stats.Seg ntSyncStats;

publ c class Seg ntLoader {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Seg ntLoader.class);
  pr vate stat c f nal Seg ntSyncStats SEGMENT_LOAD_FROM_HDFS_STATS =
      new Seg ntSyncStats("load_from_hdfs");

  pr vate f nal Cr  calExcept onHandler cr  calExcept onHandler;
  pr vate f nal Seg ntSyncConf g seg ntSyncConf g;

  pr vate f nal Clock clock;

  publ c Seg ntLoader(Seg ntSyncConf g sync,
                       Cr  calExcept onHandler cr  calExcept onHandler) {
    t (sync, cr  calExcept onHandler, Clock.SYSTEM_CLOCK);
  }

  publ c Seg ntLoader(Seg ntSyncConf g sync,
                       Cr  calExcept onHandler cr  calExcept onHandler,
                       Clock clock) {
    t .cr  calExcept onHandler = cr  calExcept onHandler;
    t .seg ntSyncConf g = sync;
    t .clock = clock;
  }

  publ c boolean load(Seg nt nfo seg nt nfo) {
    return downloadSeg nt(seg nt nfo) && loadSeg ntFromD sk(seg nt nfo);
  }

  /**
   * Determ nes  f t  Earlyb rd should attempt to download t  g ven seg nt from HDFS. T 
   * returns true  f t  seg nt  s not already present on local d sk, and t  seg nt does ex st
   * on HDFS.
   */
  publ c boolean shouldDownloadSeg ntWh le nServerSet(Seg nt nfo seg nt nfo) {
     f ( sVal dSeg ntOnD sk(seg nt nfo)) {
      return false;
    }
    try (F leSystem fs = HdfsUt l.getHdfsF leSystem()) {
      return HdfsUt l.seg ntEx stsOnHdfs(fs, seg nt nfo);
    } catch ( OExcept on e) {
      LOG.error("Fa led to c ck HDFS for seg nt " + seg nt nfo, e);
      return false;
    }
  }

  /**
   * Ver f es  f t  data for t  g ven seg nt  s present on t  local d sk, and  f  's not,
   * downloads   from HDFS.
   */
  publ c boolean downloadSeg nt(Seg nt nfo seg nt nfo) {
     f (!seg nt nfo. sEnabled()) {
      LOG.debug("Seg nt  s d sabled: " + seg nt nfo);
      return false;
    }

     f (seg nt nfo. s ndex ng() || seg nt nfo.getSync nfo(). sLoaded()) {
      LOG.debug("Cannot load  ndex ng or loaded seg nt: " + seg nt nfo);
      return false;
    }

    // Return w t r t  appropr ate vers on  s on d sk, and  f not, download   from HDFS.
    return  sVal dSeg ntOnD sk(seg nt nfo) || c ckSeg ntOnHdfsAndCopyLocally(seg nt nfo);
  }

  /**
   * Loads t  data for t  g ven seg nt from t  local d sk.
   */
  publ c boolean loadSeg ntFromD sk(Seg nt nfo seg nt nfo) {
     f (seg nt nfo. s ndex ng()) {
      LOG.error("Tr ed to load current seg nt!");
      return false;
    }

    seg nt nfo.set ndex ng(true);
    try {
      F le flushD r = new F le(seg nt nfo.getSync nfo().getLocalSyncD r());
      D rectory loadD r = FSD rectory.open(flushD r.toPath());

      seg nt nfo.load(loadD r);

       f (!ver fySeg ntStatusCountLargeEnough(seg nt nfo)) {
        SearchRateCounter.export(
            "seg nt_loader_fa led_too_few_t ets_ n_seg nt_" + seg nt nfo.getSeg ntNa ())
            . ncre nt();
        return false;
      }

      seg nt nfo.set ndex ng(false);
      seg nt nfo.setComplete(true);
      seg nt nfo.getSync nfo().setLoaded(true);
      return true;
    } catch (FlushVers onM smatchExcept on e) {
      handleExcept on(seg nt nfo, e);
      //  f earlyb rd  s  n start ng state, handler w ll term nate  
      cr  calExcept onHandler.handle(t , e);
    } catch (Except on e) {
      handleExcept on(seg nt nfo, e);
    }

    SearchRateCounter.export("seg nt_loader_fa led_" + seg nt nfo.getSeg ntNa ()). ncre nt();
    return false;
  }

  // C ck to see  f t  seg nt ex sts on d sk, and  s c cksum passes.
  pr vate boolean  sVal dSeg ntOnD sk(Seg nt nfo seg nt) {
    Str ng loadD rStr = seg nt.getSync nfo().getLocalSyncD r();
    F le loadD r = new F le(loadD rStr);

     f (!loadD r.ex sts()) {
      return false;
    }

    for (Str ng pers stentF leNa  : seg ntSyncConf g.getPers stentF leNa s(seg nt)) {
       f (!ver fy nfoC cksum(loadD r, pers stentF leNa )) {
        return false;
      }
    }

    return true;
  }

  pr vate stat c boolean ver fy nfoC cksum(F le loadD r, Str ng databaseNa ) {
     f (c cksumF leEx sts(loadD r, databaseNa )) {
      try {
        D rectory d r = FSD rectory.open(loadD r.toPath());
        Pers stentF le.Reader reader = Pers stentF le.getReader(d r, databaseNa );
        try {
          reader.ver fy nfoC cksum();
          return true;
        } f nally {
           OUt ls.closeQu etly(reader);
           OUt ls.closeQu etly(d r);
        }
      } catch (Pers stentF le.CorruptF leExcept on e) {
        LOG.error("Fa led c cksum ver f cat on.", e);
      } catch ( OExcept on e) {
        LOG.error("Error wh le try ng to read c cksum f le", e);
      }
    }
    return false;
  }

  // C ck that t  loaded seg nt's status count  s h g r than t  conf gured threshold
  pr vate boolean ver fySeg ntStatusCountLargeEnough(Seg nt nfo seg nt nfo) {
    long seg ntStatusCount = seg nt nfo.get ndexStats().getStatusCount();
     f (seg ntStatusCount > seg ntSyncConf g.getM nSeg ntStatusCountThreshold()) {
      return true;
    } else  f (seg nt nfo.getEarlyb rd ndexConf g(). s ndexStoredOnD sk()
        && couldBeMostRecentArch veSeg nt(seg nt nfo)) {
      // T  most recent arch ve earlyb rd seg nt  s expected to be  ncomplete
      LOG. nfo("Seg nt status count (" + seg ntStatusCount + ")  s below t  threshold of "
          + seg ntSyncConf g.getM nSeg ntStatusCountThreshold()
          + ", but t   s expected because t  most recent seg nt  s expected to be  ncomplete: "
          + seg nt nfo);
      return true;
    } else {
      // T  seg nt status count  s small so t  seg nt  s l kely  ncomplete.
      LOG.error("Seg nt status count (" + seg ntStatusCount + ")  s below t  threshold of "
          + seg ntSyncConf g.getM nSeg ntStatusCountThreshold() + ": " + seg nt nfo);
      seg nt nfo.set ndex ng(false);
      seg nt nfo.getSync nfo().setLoaded(false);

      // Remove seg nt from local d sk
       f (!seg nt nfo.deleteLocal ndexedSeg ntD rectory m d ately()) {
        LOG.error("Fa led to cleanup unloadable seg nt d rectory.");
      }

      return false;
    }
  }

  // C ck  f t  seg nt could be t  most recent arch ve earlyb rd seg nt (would be on t 
  // latest t er). Arch ve seg nts tend to span around 12 days, so us ng a conservat ve threshold
  // of 20 days.
  pr vate boolean couldBeMostRecentArch veSeg nt(Seg nt nfo seg nt nfo) {
    long t  sl ceAgeMs =
        Snowflake dParser.getT etAge nMs(clock.nowM ll s(), seg nt nfo.getT  Sl ce D());
    return (t  sl ceAgeMs / 1000 / 60 / 60 / 24) <= 20;
  }

  /**
   * C ck to see  f t  seg nt ex sts on hdfs. W ll look for t  correct seg nt vers on
   * uploaded by any of t  hosts.
   *  f t  seg nt ex sts on hdfs, t  seg nt w ll be cop ed from hdfs to t  local f le
   * system, and   w ll ver fy t  c cksum aga nst t  cop ed vers on.
   * @return true  ff t  seg nt was cop ed to local d sk, and t  c cksum  s ver f ed.
   */
  pr vate boolean c ckSeg ntOnHdfsAndCopyLocally(Seg nt nfo seg nt) {
     f (!seg ntSyncConf g. sSeg ntLoadFromHdfsEnabled()) {
      return  sVal dSeg ntOnD sk(seg nt);
    }

    LOG. nfo("About to start download ng seg nt from hdfs: " + seg nt);
    T  r t  r = new T  r(T  Un .M LL SECONDS);
    Str ng status = null;
    Str ng localBaseD r = seg nt.getSync nfo().getLocalSyncD r();
    F leSystem fs = null;
    try {
      fs = HdfsUt l.getHdfsF leSystem();

      Str ng hdfsBaseD rPref x = seg nt.getSync nfo().getHdfsSyncD rPref x();
      F leStatus[] statuses = fs.globStatus(new Path(hdfsBaseD rPref x));
       f (statuses != null && statuses.length > 0) {
        Path hdfsSyncPath = statuses[0].getPath();
        copySeg ntF lesFromHdfs(seg nt, seg ntSyncConf g, fs, hdfsSyncPath);
        status = "loaded";
      } else {
        LOG. nfo("No seg nts found  n hdfs under: " + hdfsBaseD rPref x);
        status = "notloaded";
      }
      fs.close();
    } catch ( OExcept on ex) {
      LOG.error("Fa led copy ng seg nt from hdfs: " + seg nt + " after: "
                + t  r.stop() + " ms", ex);
      status = "except on";
      SEGMENT_LOAD_FROM_HDFS_STATS.recordError();
      try {
        F leUt ls.deleteD rectory(new F le(localBaseD r));
      } catch ( OExcept on e) {
        LOG.error("Error clean ng up local seg nt d rectory: " + seg nt, e);
      }
    } f nally {
      t  r.stop();
      SEGMENT_LOAD_FROM_HDFS_STATS.act onComplete(t  r);
      LOG. nfo("Download from hdfs completed  n "
          + t  r.getElapsed() + " m ll seconds: " + seg nt + " status: " + status);
       OUt ls.closeQu etly(fs);
    }

    // now c ck to see  f   have successfully cop ed t  seg nt
    return  sVal dSeg ntOnD sk(seg nt);
  }

  pr vate stat c vo d copySeg ntF lesFromHdfs(Seg nt nfo seg nt,
                                               Seg ntSyncConf g syncConf g,
                                               F leSystem fs,
                                               Path hdfsSyncPath) throws  OExcept on {
    Str ng localBaseD r = seg nt.getSync nfo().getLocalSyncD r();
    F le localBaseD rF le = new F le(localBaseD r);
    F leUt ls.deleteQu etly(localBaseD rF le);
     f (localBaseD rF le.ex sts()) {
      LOG.warn("Cannot delete t  ex st ng path: " + localBaseD r);
    }
    for (Str ng f leNa  : syncConf g.getAllSyncF leNa s(seg nt)) {
      Path hdfsF lePath = new Path(hdfsSyncPath, f leNa );
      Str ng localF leNa  = localBaseD r + "/" + f leNa ;
      LOG.debug("About to start load ng from hdfs: " + f leNa  + " from: "
                + hdfsF lePath + " to: " + localF leNa );

      T  r t  r = new T  r(T  Un .M LL SECONDS);
      fs.copyToLocalF le(hdfsF lePath, new Path(localF leNa ));
      LOG.debug("Loaded seg nt f le from hdfs: " + f leNa  + " from: "
                + hdfsF lePath + " to: " + localF leNa  + "  n: " + t  r.stop() + " ms.");
    }

    LOG. nfo("F n s d download ng seg nts from " + hdfsSyncPath);
  }

  pr vate stat c boolean c cksumF leEx sts(F le loadD r, Str ng databaseNa ) {
    Str ng c cksumF leNa  = Pers stentF le.genC cksumF leNa (databaseNa );
    F le c cksumF le = new F le(loadD r, c cksumF leNa );

    return c cksumF le.ex sts();
  }

  pr vate vo d handleExcept on(Seg nt nfo seg nt nfo, Except on e) {
    LOG.error("Except on wh le load ng  ndexSeg nt from "
        + seg nt nfo.getSync nfo().getLocalSyncD r(), e);

    seg nt nfo.set ndex ng(false);
    seg nt nfo.getSync nfo().setLoaded(false);
     f (!seg nt nfo.deleteLocal ndexedSeg ntD rectory m d ately()) {
      LOG.error("Fa led to cleanup unloadable seg nt d rectory.");
    }
  }
}
