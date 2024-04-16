package com.tw ter.search.earlyb rd.part  on;

 mport java. o.F le;
 mport java. o. OExcept on;
 mport java.ut l.concurrent.T  Un ;

 mport org.apac .commons. o.F leUt ls;
 mport org.apac .hadoop.fs.F leSystem;
 mport org.apac .hadoop.fs.Path;
 mport org.apac .lucene.store.D rectory;
 mport org.apac .lucene.store.FSD rectory;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.base.Command;
 mport com.tw ter.common.quant y.Amount;
 mport com.tw ter.common.quant y.T  ;
 mport com.tw ter.search.common.database.DatabaseConf g;
 mport com.tw ter.search.common. tr cs.T  r;
 mport com.tw ter.search.common.ut l. o.flushable.Pers stentF le;
 mport com.tw ter.search.common.ut l.zktrylock.TryLock;
 mport com.tw ter.search.common.ut l.zktrylock.ZooKeeperTryLockFactory;

/**
 * Flush seg nts to d sk and upload t m to HDFS.
 */
publ c class Seg ntHdfsFlus r {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Seg ntHdfsFlus r.class);
  pr vate stat c f nal Amount<Long, T  > HDFS_UPLOADER_TRY_LOCK_NODE_EXP RAT ON_T ME_M LL S =
      Amount.of(1L, T  .HOURS);

  pr vate f nal Seg ntSyncConf g sync;
  pr vate f nal boolean holdLockWh leUpload ng;
  pr vate f nal ZooKeeperTryLockFactory zkTryLockFactory;

  publ c Seg ntHdfsFlus r(ZooKeeperTryLockFactory zooKeeperTryLockFactory,
                            Seg ntSyncConf g sync,
                            boolean holdLockWh leUpload ng) {
    t .zkTryLockFactory = zooKeeperTryLockFactory;
    t .sync = sync;
    t .holdLockWh leUpload ng = holdLockWh leUpload ng;
  }

  publ c Seg ntHdfsFlus r(
      ZooKeeperTryLockFactory zooKeeperTryLockFactory,
      Seg ntSyncConf g sync) {
    t (zooKeeperTryLockFactory, sync, true);
  }

  pr vate boolean shouldFlushSeg nt(Seg nt nfo seg nt nfo) {
    return seg nt nfo. sEnabled()
        && !seg nt nfo.getSync nfo(). sFlus d()
        && seg nt nfo. sComplete()
        && seg nt nfo. sOpt m zed()
        && !seg nt nfo. sFa ledOpt m ze()
        && !seg nt nfo.getSync nfo(). sLoaded();
  }

  /**
   * Flus s a seg nt to local d sk and to HDFS.
   */
  publ c boolean flushSeg ntToD skAndHDFS(Seg nt nfo seg nt nfo) {
     f (!shouldFlushSeg nt(seg nt nfo)) {
      return false;
    }
    try {
       f (seg nt nfo. s ndex ng()) {
        LOG.error("Tr ed to flush current seg nt!");
        return false;
      }

      // C ck-and-set t  be ngUploaded flag from false to true.  f t  CAS fa ls,    ans t 
      // seg nt  s be ng flus d already, or be ng deleted.  n t  case,   can just return false.
       f (!seg nt nfo.casBe ngUploaded(false, true)) {
        LOG.warn("Tr ed to flush a seg nt that's be ng flus d or deleted.");
        return false;
      }

      // At t  po nt, t  above CAS must have returned false. T   an t  be ngUploaded flag
      // was false, and set to true now.   can proceed w h flush ng t  seg nt.
      try {
        c ckAndFlushSeg ntToHdfs(seg nt nfo);
      } f nally {
        seg nt nfo.setBe ngUploaded(false);
      }
      return true;
    } catch (Except on e) {
      LOG.error("Except on wh le flush ng  ndexSeg nt to "
          + seg nt nfo.getSync nfo().getHdfsFlushD r(), e);
      return false;
    }
  }

  /**
   * F rst try to acqu re a lock  n Zookeeper for t  seg nt, so mult ple Earlyb rds  n t  sa 
   * part  on don't flush or upload t  seg nt at t  sa  t  . W n t  lock  s acqu red, c ck
   * for t  seg nt  n HDFS.  f t  data already ex sts, don't flush to d sk.
   */
  pr vate vo d c ckAndFlushSeg ntToHdfs(f nal Seg nt nfo seg nt) {
    LOG. nfo("C ck ng and flush ng seg nt {}", seg nt);

    try {
      // Always flush t  seg nt locally.
      D rectory d r = FSD rectory.open(createFlushD r(seg nt).toPath());
      seg nt.flush(d r);
      LOG. nfo("Completed local flush of seg nt {}. Flush to HDFS enabled: {}",
               seg nt, sync. sFlushToHdfsEnabled());
    } catch ( OExcept on e) {
      LOG.error("Fa led to flush seg nt " + seg nt + " locally", e);
      return;
    }

     f (!holdLockWh leUpload ng) {
      flushToHdfs fNecessary(seg nt);
    } else {
      TryLock lock = zkTryLockFactory.createTryLock(
          DatabaseConf g.getLocalHostna (),
          sync.getZooKeeperSyncFullPath(),
          sync.getVers onedNa (seg nt.getSeg nt()),
          HDFS_UPLOADER_TRY_LOCK_NODE_EXP RAT ON_T ME_M LL S
      );

      boolean gotLock = lock.tryW hLock((Command) () -> flushToHdfs fNecessary(seg nt));
       f (!gotLock) {
        LOG. nfo("Fa led to get zk upload lock for seg nt {}", seg nt);
      }
    }
  }

  /**
   * C ck w t r t  seg nt has already been flus d to HDFS.  f not, flush t  seg nt to d sk
   * and upload t  f les to HDFS.
   *
   *  f t  ZK lock  sn't used, t re  s a race bet en t  ex stence c ck and t  upload ( n
   * wh ch anot r Earlyb rd can sneak  n and upload t  seg nt), so   w ll potent ally upload
   * t  sa  seg nt from d fferent hosts. Thus, t  Earlyb rd hostna   s part of t  seg nt's
   * path on HDFS.
   */
  pr vate vo d flushToHdfs fNecessary(Seg nt nfo seg nt nfo) {
    T  r t  r = new T  r(T  Un .M LL SECONDS);
    Str ng status = "flus d";
    try (F leSystem fs = HdfsUt l.getHdfsF leSystem()) {
      //  f   can't load seg nts from HDFS, don't bot r c ck ng HDFS for t  seg nt
       f (sync. sSeg ntLoadFromHdfsEnabled()
          && (seg nt nfo.getSync nfo(). sFlus d()
              || HdfsUt l.seg ntEx stsOnHdfs(fs, seg nt nfo))) {
        status = "ex st ng";
      } else  f (sync. sFlushToHdfsEnabled()) {
        copyLocalF lesToHdfs(fs, seg nt nfo);
        status = "uploaded";
      }

      // w t r   uploaded, or so one else d d, t  seg nt should now be on HDFS.  f
      // upload ng to HDFS  s d sabled,   st ll cons der   complete.
      seg nt nfo.getSync nfo().setFlus d(true);
    } catch ( OExcept on e) {
      LOG.error("Fa led copy ng seg nt {} to HDFS after {} ms", seg nt nfo, t  r.stop(), e);
      status = "except on";
    } f nally {
       f (t  r.runn ng()) {
        t  r.stop();
      }
      LOG. nfo("Flush of seg nt {} to HDFS completed  n {} m ll seconds. Status: {}",
          seg nt nfo, t  r.getElapsed(), status);
    }
  }

  /**
   * Copy local seg nt f les to HDFS. F les are f rst cop ed  nto a temporary d rectory
   *  n t  form <hostna >_<seg ntna > and w n all t  f les are wr ten out to HDFS,
   * t  d r  s rena d to <seg ntna >_<hostna >, w re    s access ble to ot r Earlyb rds.
   */
  pr vate vo d copyLocalF lesToHdfs(F leSystem fs, Seg nt nfo seg nt) throws  OExcept on {
    Str ng hdfsTempBaseD r = seg nt.getSync nfo().getHdfsTempFlushD r();

    //  f t  temp d r already ex sts on HDFS, a pr or flush must have been  nterrupted.
    // Delete   and start fresh.
    removeHdfsTempD r(fs, hdfsTempBaseD r);

    for (Str ng f leNa  : sync.getAllSyncF leNa s(seg nt)) {
      Str ng hdfsF leNa  = hdfsTempBaseD r + "/" + f leNa ;
      Str ng localBaseD r = seg nt.getSync nfo().getLocalSyncD r();
      Str ng localF leNa  = localBaseD r + "/" + f leNa ;

      LOG.debug("About to start copy ng {} to HDFS, from {} to {}",
          f leNa , localF leNa , hdfsF leNa );
      T  r t  r = new T  r(T  Un .M LL SECONDS);
      fs.copyFromLocalF le(new Path(localF leNa ), new Path(hdfsF leNa ));
      LOG.debug("Completed copy ng {} to HDFS, from {} to {},  n {} ms",
          f leNa , localF leNa , hdfsF leNa , t  r.stop());
    }

    // now let's rena  t  d r  nto  s proper form.
    Str ng hdfsBaseD r = seg nt.getSync nfo().getHdfsFlushD r();
     f (fs.rena (new Path(hdfsTempBaseD r), new Path(hdfsBaseD r))) {
      LOG. nfo("Rena d seg nt d r on HDFS from {} to {}", hdfsTempBaseD r, hdfsBaseD r);
    } else {
      Str ng error ssage = Str ng.format("Fa led to rena  seg nt d r on HDFS from %s to %s",
          hdfsTempBaseD r, hdfsBaseD r);
      LOG.error(error ssage);

      removeHdfsTempD r(fs, hdfsTempBaseD r);

      // Throw an  OExcept on so t  call ng code knows that t  copy fa led
      throw new  OExcept on(error ssage);
    }
  }

  pr vate vo d removeHdfsTempD r(F leSystem fs, Str ng tempD r) throws  OExcept on {
    Path tempD rPath = new Path(tempD r);
     f (fs.ex sts(tempD rPath)) {
      LOG. nfo("Found ex st ng temporary flush d r {} on HDFS, remov ng", tempD r);
       f (!fs.delete(tempD rPath, true /* recurs ve */)) {
        LOG.error("Fa led to delete temp d r {}", tempD r);
      }
    }
  }

  // Create or replace t  local flush d rectory
  pr vate F le createFlushD r(Seg nt nfo seg nt nfo) throws  OExcept on {
    f nal Str ng flushD rStr = seg nt nfo.getSync nfo().getLocalSyncD r();

    F le flushD r = new F le(flushD rStr);
     f (flushD r.ex sts()) {
      // Delete just t  flus d pers stent f les  f t y are t re.
      //   may also have t  lucene on-d sk  ndexed  n t  sa  d r  re,
      // that   do not want to delete.
      for (Str ng pers stentF le : sync.getPers stentF leNa s(seg nt nfo)) {
        for (Str ng f leNa  : Pers stentF le.getAllF leNa s(pers stentF le)) {
          F le f le = new F le(flushD r, f leNa );
           f (f le.ex sts()) {
            LOG. nfo("Delet ng  ncomplete flush f le {}", f le.getAbsolutePath());
            F leUt ls.forceDelete(f le);
          }
        }
      }
      return flushD r;
    }

    // Try to create t  flush d rectory
     f (!flushD r.mkd rs()) {
      throw new  OExcept on("Not able to create seg nt flush d rectory \"" + flushD rStr + "\"");
    }

    return flushD r;
  }
}
