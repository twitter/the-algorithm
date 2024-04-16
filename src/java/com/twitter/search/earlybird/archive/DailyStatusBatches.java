package com.tw ter.search.earlyb rd.arch ve;

 mport java. o.F le;
 mport java. o.F leNotFoundExcept on;
 mport java. o.F leWr er;
 mport java. o. OExcept on;
 mport java.ut l.Calendar;
 mport java.ut l.Collect on;
 mport java.ut l.Date;
 mport java.ut l.Nav gableMap;
 mport java.ut l.concurrent.T  Un ;
 mport java.ut l.concurrent.atom c.Atom cBoolean;
 mport java.ut l.regex.Matc r;
 mport java.ut l.regex.Pattern;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.base.Stopwatch;
 mport com.google.common.collect.Maps;

 mport org.apac .commons. o. OUt ls;
 mport org.apac .commons.lang3.t  .FastDateFormat;
 mport org.apac .hadoop.fs.F leStatus;
 mport org.apac .hadoop.fs.F leSystem;
 mport org.apac .hadoop.fs.Path;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.quant y.Amount;
 mport com.tw ter.common.quant y.T  ;
 mport com.tw ter.search.common.database.DatabaseConf g;
 mport com.tw ter.search.common.ut l.date.DateUt l;
 mport com.tw ter.search.common.ut l. o.L neRecordF leReader;
 mport com.tw ter.search.common.ut l.zktrylock.TryLock;
 mport com.tw ter.search.common.ut l.zktrylock.ZooKeeperTryLockFactory;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdProperty;
 mport com.tw ter.search.earlyb rd.part  on.HdfsUt l;
 mport com.tw ter.search.earlyb rd.part  on.StatusBatchFlushVers on;

/**
 * Prov des access to preprocessed statuses (t ets) to be  ndexed by arch ve search earlyb rds.
 *
 * T se t ets can be com ng from a scrub gen or from t  output of t  da ly jobs.
 */
publ c class Da lyStatusBatc s {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Da lyStatusBatc s.class);

  // Max mum t   to spend on obta n ng da ly status batc s by comput ng or load ng from HDFS
  pr vate stat c f nal Amount<Long, T  > MAX_T ME_ALLOWED_DA LY_STATUS_BATCHES_M NUTES =
      Amount.of(Earlyb rdConf g.getLong("da ly_status_batc s_max_ n  al_load_t  _m nutes"),
          T  .M NUTES);
  // T   to wa  before try ng aga n w n obta n ng da ly status batc s fa ls
  pr vate stat c f nal Amount<Long, T  > DA LY_STATUS_BATCHES_WA T NG_T ME_M NUTES =
      Amount.of(Earlyb rdConf g.getLong("da ly_status_batc s_wa  ng_t  _m nutes"),
          T  .M NUTES);
  pr vate stat c f nal Str ng DA LY_STATUS_BATCHES_SYNC_PATH =
      Earlyb rdProperty.ZK_APP_ROOT.get() + "/da ly_batc s_sync";
  pr vate stat c f nal Str ng DA LY_BATCHES_ZK_LOCK = "da ly_batc s_zk_lock";
  pr vate stat c f nal Amount<Long, T  > DA LY_STATUS_BATCHES_ZK_LOCK_EXP RAT ON_M NUTES =
      Amount.of(Earlyb rdConf g.getLong("da ly_status_batc s_zk_lock_exp rat on_m nutes"),
          T  .M NUTES);

  stat c f nal FastDateFormat DATE_FORMAT = FastDateFormat.get nstance("yyyyMMdd");

  // before t  date, t re was no tw ter
  pr vate stat c f nal Date F RST_TW TTER_DAY = DateUt l.toDate(2006, 2, 1);

  pr vate stat c f nal Str ng STATUS_BATCHES_PREF X = "status_batc s";

  pr vate f nal Str ng rootD r =
      Earlyb rdConf g.getStr ng("hdfs_offl ne_seg nt_sync_d r", "top_arch ve_statuses");

  pr vate f nal Str ng bu ldGen =
      Earlyb rdConf g.getStr ng("offl ne_seg nt_bu ld_gen", "bg_1");

  publ c stat c f nal Str ng STATUS_SUBD R_NAME = "statuses";
  publ c stat c f nal Str ng LAYOUT_SUBD R_NAME = "la ts";
  publ c stat c f nal Str ng SCRUB_GEN_SUFF X_PATTERN = "scrubbed/%s";

  pr vate stat c f nal Str ng  NTERMED ATE_COUNTS_SUBD R_NAME = "counts";
  pr vate stat c f nal Str ng SUCCESS_F LE_NAME = "_SUCCESS";
  pr vate stat c f nal Pattern HASH_PART T ON_PATTERN = Pattern.comp le("p_(\\d+)_of_(\\d+)");
  pr vate stat c f nal Date F RST_TWEET_DAY = DateUt l.toDate(2006, 3, 21);

  pr vate f nal Path rootPath = new Path(rootD r);
  pr vate f nal Path bu ldGenPath = new Path(rootPath, bu ldGen);
  pr vate f nal Path statusPath = new Path(bu ldGenPath, STATUS_SUBD R_NAME);

  pr vate f nal Nav gableMap<Date, Da lyStatusBatch> statusBatc s = Maps.newTreeMap();

  pr vate Date f rstVal dDay = null;
  pr vate Date lastVal dDay = null;

  pr vate f nal ZooKeeperTryLockFactory zkTryLockFactory;
  pr vate f nal Date scrubGenDay;
  pr vate long numberOfDaysW hVal dScrubGenData;

  publ c Da lyStatusBatc s(
      ZooKeeperTryLockFactory zooKeeperTryLockFactory, Date scrubGenDay) throws  OExcept on {
    t .zkTryLockFactory = zooKeeperTryLockFactory;
    t .scrubGenDay = scrubGenDay;

    F leSystem hdfs = null;
    try {
      hdfs = HdfsUt l.getHdfsF leSystem();
      ver fyD rectory(hdfs);
    } f nally {
       OUt ls.closeQu etly(hdfs);
    }
  }

  @V s bleForTest ng
  publ c Date getScrubGenDay() {
    return scrubGenDay;
  }

  publ c Collect on<Da lyStatusBatch> getStatusBatc s() {
    return statusBatc s.values();
  }

  /**
   * Reset t  states of t  d rectory
   */
  pr vate vo d resetD rectory() {
    statusBatc s.clear();
    f rstVal dDay = null;
    lastVal dDay = null;
  }

  /**
   *   nd cate w t r t  d rectory has been  n  al zed
   */
  pr vate boolean  s n  al zed() {
    return lastVal dDay != null;
  }

  /**
   * Load t  da ly status batc s from HDFS; return true  f one or more batc s could be loaded.
   **/
  pr vate boolean refreshByLoad ngHDFSStatusBatc s(f nal F leSystem fs) throws  OExcept on {
    // f rst f nd t  latest val d end date of statuses
    f nal Date lastVal dStatusDay = getLastVal d nputDateFromNow(fs);
     f (lastVal dStatusDay != null) {
       f (hasStatusBatc sOnHdfs(fs, lastVal dStatusDay)) {
         f (loadStatusBatc sFromHdfs(fs, lastVal dStatusDay)) {
          return true;
        }
      }
    }

    resetD rectory();
    return false;
  }

  /**
   * C cks t  d rectory for new data and returns true,  f one or more new batc s could be loaded.
   */
  publ c vo d refresh() throws  OExcept on {
    f nal F leSystem hdfs = HdfsUt l.getHdfsF leSystem();

    f nal Stopwatch stopwatch = Stopwatch.createStarted();
    try {
       f (! s n  al zed()) {
         f ( n  al zeDa lyStatusBatc s(hdfs, stopwatch)) {
          LOG. nfo("Successfully obta ned da ly status batc s after {}", stopwatch);
        } else {
          Str ng errMsg = "Fa led to load or compute da ly status batc s after "
              + stopwatch.toStr ng();
          LOG.error(errMsg);
          throw new  OExcept on(errMsg);
        }
      } else {
        loadNewDa lyBatc s(hdfs);
      }
    } f nally {
       OUt ls.closeQu etly(hdfs);
    }
  }

  pr vate boolean  n  al zeDa lyStatusBatc s(f nal F leSystem hdfs,
                                               f nal Stopwatch stopwatch) throws  OExcept on {
    long t  SpentOnDa lyBatc s = 0L;
    long maxAllo dT  Ms = MAX_T ME_ALLOWED_DA LY_STATUS_BATCHES_M NUTES.as(T  .M LL SECONDS);
    long wa  ngT  Ms = DA LY_STATUS_BATCHES_WA T NG_T ME_M NUTES.as(T  .M LL SECONDS);
    boolean f rstLoop = true;
    LOG. nfo("Start ng to load or compute da ly status batc s for t  f rst t  .");
    wh le (t  SpentOnDa lyBatc s <= maxAllo dT  Ms && !Thread.currentThread(). s nterrupted()) {
       f (!f rstLoop) {
        try {
          LOG. nfo("Sleep ng " + wa  ngT  Ms
              + " m ll s before try ng to obta n da ly batc s aga n");
          Thread.sleep(wa  ngT  Ms);
        } catch ( nterruptedExcept on e) {
          LOG.warn(" nterrupted wh le wa  ng to load da ly batc s", e);
          Thread.currentThread(). nterrupt();
          break;
        }
      }

       f ( sStatusBatchLoad ngEnabled() && refreshByLoad ngHDFSStatusBatc s(hdfs)) {
        LOG. nfo("Successfully loaded da ly status batc s after {}", stopwatch);
        return true;
      }

      f nal Atom cBoolean successRef = new Atom cBoolean(false);
       f (computeDa lyBatc sW hZKLock(hdfs, successRef, stopwatch)) {
        return successRef.get();
      }

      t  SpentOnDa lyBatc s = stopwatch.elapsed(T  Un .M LL SECONDS);
      f rstLoop = false;
    }

    return false;
  }

  pr vate boolean computeDa lyBatc sW hZKLock(f nal F leSystem hdfs,
                                                f nal Atom cBoolean successRef,
                                                f nal Stopwatch stopwatch) throws  OExcept on {
    // Us ng a global lock to coord nate among earlyb rds and seg nt bu lders so that only
    // one  nstance would h  t  HDFS na  node to query t  da ly status d rector es
    TryLock lock = zkTryLockFactory.createTryLock(
        DatabaseConf g.getLocalHostna (),
        DA LY_STATUS_BATCHES_SYNC_PATH,
        DA LY_BATCHES_ZK_LOCK,
        DA LY_STATUS_BATCHES_ZK_LOCK_EXP RAT ON_M NUTES);

    return lock.tryW hLock(() -> {
      LOG. nfo("Obta ned ZK lock to compute da ly status batc s after {}", stopwatch);
      successRef.set( n  alLoadDa lyBatch nfos(hdfs));
       f (successRef.get()) {
        LOG. nfo("Successfully computed da ly status batc s after {}", stopwatch);
         f ( sStatusBatchFlush ngEnabled()) {
          LOG. nfo("Start ng to store da ly status batc s to HDFS");
           f (storeStatusBatc sToHdfs(hdfs, lastVal dDay)) {
            LOG. nfo("Successfully stored da ly status batc s to HDFS");
          } else {
            LOG.warn("Fa led stor ng da ly status batc s to HDFS");
          }
        }
      } else {
        LOG. nfo("Fa led load ng da ly status  nfo");
      }
    });
  }

  pr vate vo d ver fyD rectory(F leSystem hdfs) throws  OExcept on {
     f (!hdfs.ex sts(rootPath)) {
      throw new  OExcept on("Root d r '" + rootPath + "' does not ex st.");
    }

     f (!hdfs.ex sts(bu ldGenPath)) {
      throw new  OExcept on("Bu ld gen d r '" + bu ldGenPath + "' does not ex st.");
    }

     f (!hdfs.ex sts(statusPath)) {
      throw new  OExcept on("Status d r '" + statusPath + "' does not ex st.");
    }
  }

  pr vate vo d loadNewDa lyBatc s(F leSystem hdfs) throws  OExcept on {
    Precond  ons.c ckNotNull(lastVal dDay);

    Calendar day = Calendar.get nstance();
    day.setT  (lastVal dDay);
    day.add(Calendar.DATE, 1);

    wh le (loadDay(hdfs, day.getT  ()) != null) {
      lastVal dDay = day.getT  ();
      day.add(Calendar.DATE, 1);
    }
  }

  pr vate boolean  n  alLoadDa lyBatch nfos(F leSystem hdfs) throws  OExcept on {
    LOG. nfo("Start ng to bu ld t  sl ce map from scratch.");

    f nal Date lastVal dStatusDay = getLastVal d nputDateFromNow(hdfs);

     f (lastVal dStatusDay == null) {
      LOG.warn("No data found  n " + statusPath + " and scrubbed path");
      return false;
    }
     nt mostRecentYear = DateUt l.getCalendar(lastVal dStatusDay).get(Calendar.YEAR);
    for ( nt year = 2006; year <= mostRecentYear; ++year) {
      // construct path to avo d hdfs.l stStatus() calls
      Calendar day = Calendar.get nstance();
      day.set(year, Calendar.JANUARY, 1, 0, 0, 0);
      day.set(Calendar.M LL SECOND, 0);

      Calendar yearEnd = Calendar.get nstance();
      yearEnd.set(year, Calendar.DECEMBER, 31, 0, 0, 0);
      yearEnd.set(Calendar.M LL SECOND, 0);

       f (lastVal dDay != null) {
        //  're updat ng.
         f (lastVal dDay.after(yearEnd.getT  ())) {
          // T  year was already loaded.
          cont nue;
        }
         f (lastVal dDay.after(day.getT  ())) {
          // Start one day after last val d date.
          day.setT  (lastVal dDay);
          day.add(Calendar.DATE, 1);
        }
      }

      for (; !day.after(yearEnd); day.add(Calendar.DATE, 1)) {
        loadDay(hdfs, day.getT  ());
      }
    }

    boolean updated = false;
    numberOfDaysW hVal dScrubGenData = 0;

    //  erate batc s  n sorted order.
    for (Da lyStatusBatch batch : statusBatc s.values()) {
       f (!batch. sVal d()) {
        break;
      }
       f (batch.getDate().before(scrubGenDay)) {
        numberOfDaysW hVal dScrubGenData++;
      }
       f (f rstVal dDay == null) {
        f rstVal dDay = batch.getDate();
      }
       f (lastVal dDay == null || lastVal dDay.before(batch.getDate())) {
        lastVal dDay = batch.getDate();
        updated = true;
      }
    }

    LOG. nfo("Number of statusBatc s: {}", statusBatc s.s ze());
    return updated;
  }

  pr vate stat c Str ng f lesToStr ng(F leStatus[] f les) {
     f (f les == null) {
      return "null";
    }
    Str ngBu lder b = new Str ngBu lder();
    for (F leStatus s : f les) {
      b.append(s.getPath().toStr ng()).append(", ");
    }
    return b.toStr ng();
  }

  @V s bleForTest ng
  protected Da lyStatusBatch loadDay(F leSystem hdfs, Date day) throws  OExcept on {
    Path dayPath = new Path(getStatusPathToUseForDay(day), Arch veHDFSUt ls.dateToPath(day, "/"));
    LOG.debug("Look ng for batch  n " + dayPath.toStr ng());
    Da lyStatusBatch result = t .statusBatc s.get(day);
     f (result != null) {
      return result;
    }

    f nal F leStatus[] f les;
    try {
      f les = hdfs.l stStatus(dayPath);
      LOG.debug("F les found:  " + f lesToStr ng(f les));
    } catch (F leNotFoundExcept on e) {
      LOG.debug("loadDay() called, but d rectory does not ex st for day: " + day
          + "  n: " + dayPath);
      return null;
    }

     f (f les != null && f les.length > 0) {
      for (F leStatus f le : f les) {
        Matc r matc r = HASH_PART T ON_PATTERN.matc r(f le.getPath().getNa ());
         f (matc r.matc s()) {
           nt numHashPart  ons =  nteger.parse nt(matc r.group(2));
          result = new Da lyStatusBatch(
              day, numHashPart  ons, getStatusPathToUseForDay(day), hdfs);

          for ( nt part  on D = 0; part  on D < numHashPart  ons; part  on D++) {
            result.addPart  on(hdfs, dayPath, part  on D);
          }

           f (result. sVal d()) {
            statusBatc s.put(day, result);
            return result;
          } else {
            LOG. nfo(" nval d batch found for day: " + day + ", batch: " + result);
          }
        } else {
          // sk p logg ng t   nter d ate count subd rector es or _SUCCESS f les.
           f (! NTERMED ATE_COUNTS_SUBD R_NAME.equals(f le.getPath().getNa ())
              && !SUCCESS_F LE_NAME.equals(f le.getPath().getNa ())) {
            LOG.warn("Path does not match hash part  on pattern: " + f le.getPath());
          }
        }
      }
    } else {
      LOG.warn("No data found for day: " + day + "  n: " + dayPath
              + " f les null: " + (f les == null));
    }

    return null;
  }

  /**
   * Determ nes  f t  d rectory has a val d batch for t  g ven day.
   */
  publ c boolean hasVal dBatchForDay(Date day) throws  OExcept on {
    F leSystem hdfs = null;
    try {
      hdfs = HdfsUt l.getHdfsF leSystem();
      return hasVal dBatchForDay(hdfs, day);
    } f nally {
       OUt ls.closeQu etly(hdfs);
    }
  }

  pr vate boolean hasVal dBatchForDay(F leSystem fs, Date day) throws  OExcept on {
    Da lyStatusBatch batch = loadDay(fs, day);

    return batch != null && batch. sVal d();
  }

  @V s bleForTest ng
  Date getF rstVal dDay() {
    return f rstVal dDay;
  }

  @V s bleForTest ng
  Date getLastVal dDay() {
    return lastVal dDay;
  }

  pr vate Date getLastVal d nputDateFromNow(F leSystem hdfs) throws  OExcept on {
    Calendar cal = Calendar.get nstance();
    cal.setT  (new Date()); // current date
    return getLastVal d nputDate(hdfs, cal);
  }

  /**
   * Start ng from current date, probe backward t ll   f nd a val d  nput Date
   */
  @V s bleForTest ng
  Date getLastVal d nputDate(F leSystem hdfs, Calendar cal) throws  OExcept on {
    cal.set(Calendar.M LL SECOND, 0);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.M NUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.M LL SECOND, 0);
    Date lastVal d nputDate = cal.getT  ();
    LOG. nfo("Prob ng backwards for last val d data date from " + lastVal d nputDate);
    wh le (lastVal d nputDate.after(F RST_TW TTER_DAY)) {
       f (hasVal dBatchForDay(hdfs, lastVal d nputDate)) {
        LOG. nfo("Found latest val d data on date " + lastVal d nputDate);
        LOG. nfo("  Used path: {}", getStatusPathToUseForDay(lastVal d nputDate));
        return lastVal d nputDate;
      }
      cal.add(Calendar.DATE, -1);
      lastVal d nputDate = cal.getT  ();
    }

    return null;
  }

  /**
   * C ck  f t  da ly status batc s are already on HDFS
   */
  @V s bleForTest ng
  boolean hasStatusBatc sOnHdfs(F leSystem fs, Date lastDataDay) {
    Str ng hdfsF leNa  = getHdfsStatusBatchSyncF leNa (lastDataDay);
    try {
      return fs.ex sts(new Path(hdfsF leNa ));
    } catch ( OExcept on ex) {
      LOG.error("Fa led c ck ng status batch f le on HDFS: " + hdfsF leNa , ex);
      return false;
    }
  }

  /**
   * Load t  da ly status batc s from HDFS by f rst copy ng t  f le from HDFS to local d sk
   * and t n read ng from t  local d sk.
   *
   * @param day t  latest day of val d statuses.
   * @return true  f t  load ng  s successful.
   */
  @V s bleForTest ng
  boolean loadStatusBatc sFromHdfs(F leSystem fs, Date day) {
    // set t  d rectory state to  n  al state
    resetD rectory();

    Str ng f leHdfsPath = getHdfsStatusBatchSyncF leNa (day);
    Str ng f leLocalPath = getLocalStatusBatchSyncF leNa (day);

    LOG. nfo("Us ng " + f leHdfsPath + " as t  HDFS batch summary load path.");
    LOG. nfo("Us ng " + f leLocalPath + " as t  local batch summary sync path.");

    L neRecordF leReader l neReader = null;
    try {
      fs.copyToLocalF le(new Path(f leHdfsPath), new Path(f leLocalPath));

      l neReader = new L neRecordF leReader(f leLocalPath);
      Str ng batchL ne;
      wh le ((batchL ne = l neReader.readNext()) != null) {
        Da lyStatusBatch batch = Da lyStatusBatch.deser al zeFromJson(batchL ne);
         f (batch == null) {
          LOG.error(" nval d da ly status batch constructed from l ne: " + batchL ne);
          resetD rectory();
          return false;
        }
        Date date = batch.getDate();
         f (f rstVal dDay == null || f rstVal dDay.after(date)) {
          f rstVal dDay = date;
        }
         f (lastVal dDay == null || lastVal dDay.before(date)) {
          lastVal dDay = date;
        }
        statusBatc s.put(date, batch);
      }
      LOG. nfo("Loaded {} status batc s from HDFS: {}",
          statusBatc s.s ze(), f leHdfsPath);
      LOG. nfo("F rst entry: {}", statusBatc s.f rstEntry().getValue().toStr ng());
      LOG. nfo("Last entry: {}", statusBatc s.lastEntry().getValue().toStr ng());

      return true;
    } catch ( OExcept on ex) {
      LOG.error("Fa led load ng t   sl ces from HDFS: " + f leHdfsPath, ex);
      resetD rectory();
      return false;
    } f nally {
       f (l neReader != null) {
        l neReader.stop();
      }
    }
  }

  /**
   * Flush t  da ly status batc s to local d sk and t n upload to HDFS.
   */
  pr vate boolean storeStatusBatc sToHdfs(F leSystem fs, Date day) {
    Precond  ons.c ckNotNull(lastVal dDay);

     f (!StatusBatchFlushVers on.CURRENT_FLUSH_VERS ON. sOff c al()) {
      LOG. nfo("Status batch flush vers on  s not off c al, no batc s w ll be flus d to HDFS");
      return true;
    }

    Str ng f leLocalPath = getLocalStatusBatchSyncF leNa (day);

    // Flush to local d sk
    F le outputF le = null;
    F leWr er f leWr er = null;
    try {
      LOG. nfo("Flush ng da ly status batc s  nto: " + f leLocalPath);
      outputF le = new F le(f leLocalPath);
      outputF le.getParentF le().mkd rs();
       f (!outputF le.getParentF le().ex sts()) {
        LOG.error("Cannot create d rectory: " + outputF le.getParentF le().toStr ng());
        return false;
      }
      f leWr er = new F leWr er(outputF le, false);
      for (Date date : statusBatc s.keySet()) {
        f leWr er.wr e(statusBatc s.get(date).ser al zeToJson());
        f leWr er.wr e("\n");
      }
      f leWr er.flush();

      // Upload t  f le to HDFS
      return uploadStatusBatc sToHdfs(fs, day);
    } catch ( OExcept on e) {
      Str ng f leHdfsPath = getHdfsStatusBatchSyncF leNa (day);
      LOG.error("Fa led stor ng status batc s to HDFS: " + f leHdfsPath, e);
      return false;
    } f nally {
      try {
         f (f leWr er != null) {
          f leWr er.close();
        }
      } catch ( OExcept on e) {
        LOG.error("Error to close f leWr e.", e);
      }
       f (outputF le != null) {
        // Delete t  local f le
        outputF le.delete();
      }
    }
  }

  /**
   * Upload t  status batc s to HDFS.
   */
  @V s bleForTest ng
  boolean uploadStatusBatc sToHdfs(F leSystem fs, Date day) {
    Str ng localF leNa  = getLocalStatusBatchSyncF leNa (day);
    Str ng hdfsF leNa  = getHdfsStatusBatchSyncF leNa (day);

    LOG. nfo("Us ng " + hdfsF leNa  + " as t  HDFS batch summary upload path.");
    LOG. nfo("Us ng " + localF leNa  + " as t  local batch summary sync path.");

    try {
      Path hdfsF lePath = new Path(hdfsF leNa );
       f (fs.ex sts(hdfsF lePath)) {
        LOG.warn("Found status batch f le on HDFS: " + hdfsF leNa );
        return true;
      }

      Str ng hdfsTempNa  = getHdfsStatusBatchTempSyncF leNa (day);
      Path hdfsTempPath = new Path(hdfsTempNa );
       f (fs.ex sts(hdfsTempPath)) {
        LOG. nfo("Found ex st ng temporary status batch f le on HDFS, remov ng: " + hdfsTempNa );
         f (!fs.delete(hdfsTempPath, false)) {
          LOG.error("Fa led to delete temporary f le: " + hdfsTempNa );
          return false;
        }
      }
      fs.copyFromLocalF le(new Path(localF leNa ), hdfsTempPath);

       f (fs.rena (hdfsTempPath, hdfsF lePath)) {
        LOG.debug("Rena d " + hdfsTempNa  + " on HDFS to: " + hdfsF leNa );
        return true;
      } else {
        LOG.error("Fa led to rena  " + hdfsTempNa  + " on HDFS to: " + hdfsF leNa );
        return false;
      }
    } catch ( OExcept on ex) {
      LOG.error("Fa led upload ng status batch f le to HDFS: " + hdfsF leNa , ex);
      return false;
    }
  }

  pr vate stat c boolean  sStatusBatchFlush ngEnabled() {
    return Earlyb rdProperty.ARCH VE_DA LY_STATUS_BATCH_FLUSH NG_ENABLED.get(false);
  }

  pr vate stat c boolean  sStatusBatchLoad ngEnabled() {
    return Earlyb rdConf g.getBool("arch ve_da ly_status_batch_load ng_enabled", false);
  }

  pr vate stat c Str ng getVers onF leExtens on() {
    return StatusBatchFlushVers on.CURRENT_FLUSH_VERS ON.getVers onF leExtens on();
  }

  Str ng getStatusBatchSyncRootD r() {
    return Earlyb rdConf g.getStr ng("arch ve_da ly_status_batch_sync_d r",
        "da ly_status_batc s") + "/" + scrubGenSuff x();
  }

  @V s bleForTest ng
  Str ng getLocalStatusBatchSyncF leNa (Date day) {
    return  getStatusBatchSyncRootD r() + "/" + STATUS_BATCHES_PREF X + "_"
        + DATE_FORMAT.format(day) + getVers onF leExtens on();
  }

  Str ng getHdfsStatusBatchSyncRootD r() {
    return Earlyb rdConf g.getStr ng("hdfs_arch ve_da ly_status_batch_sync_d r",
        "da ly_status_batc s") + "/" + scrubGenSuff x();
  }

  @V s bleForTest ng
  Str ng getHdfsStatusBatchSyncF leNa (Date day) {
    return getHdfsStatusBatchSyncRootD r() + "/" + STATUS_BATCHES_PREF X + "_"
        + DATE_FORMAT.format(day) + getVers onF leExtens on();
  }

  pr vate Str ng getHdfsStatusBatchTempSyncF leNa (Date day) {
    return getHdfsStatusBatchSyncRootD r() + "/" + DatabaseConf g.getLocalHostna () + "_"
        + STATUS_BATCHES_PREF X + "_" + DATE_FORMAT.format(day) + getVers onF leExtens on();
  }

  pr vate Str ng scrubGenSuff x() {
    return Str ng.format(SCRUB_GEN_SUFF X_PATTERN, DATE_FORMAT.format(scrubGenDay));
  }

  /**
   * Returns t  path to t  d rectory that stores t  statuses for t  g ven day.
   */
  publ c Path getStatusPathToUseForDay(Date day) {
     f (!day.before(scrubGenDay)) {
      return statusPath;
    }

    Str ng suff x = scrubGenSuff x();
    Precond  ons.c ckArgu nt(!suff x. sEmpty());
    Path scrubPath = new Path(bu ldGenPath, suff x);
    return new Path(scrubPath, STATUS_SUBD R_NAME);
  }

  /**
   * Determ nes  f t  data for t  spec f ed scrub gen was fully bu lt, by c ck ng t  number of
   * days for wh ch data was bu lt aga nst t  expected number of days extracted from t  spec f ed
   * scrub gen date.
   */
  publ c boolean  sScrubGenDataFullyBu lt(F leSystem hdfs) throws  OExcept on {
     n  alLoadDa lyBatch nfos(hdfs);
     f (numberOfDaysW hVal dScrubGenData == 0) {
      LOG.warn("numberOfDaysW hVal dScrubGenData  s 0");
    }
    long expectedDays = getD ffBet enDays(scrubGenDay);
    return expectedDays == numberOfDaysW hVal dScrubGenData;
  }

  @V s bleForTest ng
  long getD ffBet enDays(Date day) {
    long d ff = day.getT  () - F RST_TWEET_DAY.getT  ();
    return T  Un .DAYS.convert(d ff, T  Un .M LL SECONDS);
  }
}
