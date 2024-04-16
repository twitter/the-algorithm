package com.tw ter.search.earlyb rd.arch ve;

 mport java. o.F leNotFoundExcept on;
 mport java. o. OExcept on;
 mport java.ut l.Comparator;
 mport java.ut l.Date;
 mport java.ut l.L st;
 mport java.ut l.concurrent.T  Un ;
 mport java.ut l.regex.Matc r;
 mport java.ut l.regex.Pattern;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Funct on;
 mport com.google.common.base.Pred cate;
 mport com.google.common.collect.Compar sonCha n;
 mport com.google.common.collect.L sts;

 mport org.apac .commons. o. OUt ls;
 mport org.apac .hadoop.fs.F leStatus;
 mport org.apac .hadoop.fs.F leSystem;
 mport org.apac .hadoop.fs.Path;
 mport org.apac .hadoop.fs.PathF lter;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.conf g.Conf g;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.part  on ng.snowflakeparser.Snowflake dParser;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdThr ftDocu ntUt l;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndex ngEvent;
 mport com.tw ter.search.common.ut l.date.DateUt l;
 mport com.tw ter.search.common.ut l. o.EmptyRecordReader;
 mport com.tw ter.search.common.ut l. o.LzoThr ftBlockF leReader;
 mport com.tw ter.search.common.ut l. o. rg ngSortedRecordReader;
 mport com.tw ter.search.common.ut l. o.Transform ngRecordReader;
 mport com.tw ter.search.common.ut l. o.recordreader.RecordReader;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.docu nt.Docu ntFactory;
 mport com.tw ter.search.earlyb rd.docu nt.T etDocu nt;
 mport com.tw ter.search.earlyb rd.part  on.HdfsUt l;

/**
 * A batch of pre-processed t ets for a s ngle hash part  on from a part cular day.
 */
publ c class Part  onedBatch {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Part  onedBatch.class);
  pr vate stat c f nal Date START_DATE_ NCLUS VE = DateUt l.toDate(2006, 03, 21);
  pr vate stat c f nal Str ng STATUS_COUNT_F LE_PREF X = "_status_count_";
  pr vate stat c f nal Pattern STATUS_COUNT_F LE_PATTERN =
      Pattern.comp le(STATUS_COUNT_F LE_PREF X + "(\\d+)_m n d_(\\d+)_max d_(\\d+)");
  pr vate stat c f nal  nt MAX MUM_OUT_OF_ORDER_TOLERANCE_HOURS =
      Earlyb rdConf g.get nt("arch ve_max_out_of_order_tolerance_h s", 12);
  pr vate stat c f nal  nt READER_ N T_ OEXCEPT ON_RETR ES = 20;
  pr vate stat c f nal PathF lter LZO_DATA_F LES_F LTER = f le -> f le.getNa ().endsW h(".lzo");
  pr vate stat c f nal PathF lter TXT_DATA_F LES_F LTER = f le -> f le.getNa ().endsW h(".txt");

  pr vate stat c f nal Comparator<Thr ft ndex ngEvent> DESC_THR FT_ NDEX NG_EVENT_COMPARATOR =
      (o1, o2) -> Compar sonCha n.start()
          .compare(o2.getSort d(), o1.getSort d())
          .compare(o2.getU d(), o1.getU d())
          .result();

  // Number arch ve t ets sk pped because t y are too out-of-order.
  pr vate stat c f nal SearchCounter OUT_OF_ORDER_STATUSES_SK PPED =
      SearchCounter.export("out_of_order_arch ve_statuses_sk pped");

  @V s bleForTest ng
  protected stat c f nal long MAX MUM_OUT_OF_ORDER_TOLERANCE_M LL S =
      T  Un .HOURS.toM ll s(MAX MUM_OUT_OF_ORDER_TOLERANCE_HOURS);

  pr vate f nal Date date;
  pr vate f nal Path path;
  pr vate  nt statusCount;
  pr vate long m nStatus D;
  pr vate long maxStatus D;
  pr vate f nal  nt hashPart  on D;
  pr vate boolean hasStatusCountF le;
  pr vate f nal  nt numHashPart  ons;

  @V s bleForTest ng
  publ c Part  onedBatch(
      Path path,
       nt hashPart  on D,
       nt numHashPart  ons,
      Date date) {
    t .path = path;
    t .hashPart  on D = hashPart  on D;
    t .numHashPart  ons = numHashPart  ons;
    t .date = date;
  }

  /**
   * Loads all t   nformat on (t et count, etc.) for t  part  on and day from HDFS.
   */
  publ c vo d load(F leSystem hdfs) throws  OExcept on {
    F leStatus[] da lyBatchF les = null;
    try {
      // l stStatus() javadoc says   throws F leNotFoundExcept on w n path does not ex st.
      // Ho ver, t  actual  mple ntat ons return null or an empty array  nstead.
      //   handle all 3 cases: null, empty array, or F leNotFoundExcept on.
      da lyBatchF les = hdfs.l stStatus(path);
    } catch (F leNotFoundExcept on e) {
      // don't do anyth ng  re and t  day w ll be handled as empty.
    }

     f (da lyBatchF les != null && da lyBatchF les.length > 0) {
      for (F leStatus f le : da lyBatchF les) {
        Str ng f leNa  = f le.getPath().getNa ();
         f (f leNa .equals(STATUS_COUNT_F LE_PREF X)) {
          // zero t ets  n t  part  on - t  can happen for early days  n 2006
          handleEmptyPart  on();
        } else {
          Matc r matc r = STATUS_COUNT_F LE_PATTERN.matc r(f leNa );
           f (matc r.matc s()) {
            try {
              statusCount =  nteger.parse nt(matc r.group(1));
              // Only adjustM nStatus d  n product on. For tests, t  makes t  tests harder to
              // understand.
              m nStatus D = Conf g.env ron nt sTest() ? Long.parseLong(matc r.group(2))
                  : adjustM nStatus d(Long.parseLong(matc r.group(2)), date);
              maxStatus D = Long.parseLong(matc r.group(3));
              hasStatusCountF le = true;
            } catch (NumberFormatExcept on e) {
              //  nval d f le -  gnore
              LOG.warn("Could not parse status count f le na .", e);
            }
          }
        }
      }
    } else {
      // Part  on folder does not ex st. T  case can happen for early days of tw ter
      // w re so  part  ons are empty. Set us to hav ng a status count f le, t  val d y of
      // t  parent Da lyStatusBatch w ll st ll be determ ned by w t r t re was a _SUCCESS f le
      //  n t  day root.
      handleEmptyPart  on();

       f (date.after(getEarl estDenseDay())) {
        LOG.error("Unexpected empty d rectory {} for {}", path, date);
      }
    }
  }

  pr vate vo d handleEmptyPart  on() {
    statusCount = 0;
    m nStatus D = Da lyStatusBatch.EMPTY_BATCH_STATUS_ D;
    maxStatus D = Da lyStatusBatch.EMPTY_BATCH_STATUS_ D;
    hasStatusCountF le = true;
  }

  /**
   * So t  s t ets are out-of-order (E.g. a t et from Sep 2012 got  nto a
   * batch  n July 2013). See SEARCH-1750 for more deta ls.
   * T  adjust t  m nStatus D  f    s badly out-of-order.
   */
  @V s bleForTest ng
  protected stat c long adjustM nStatus d(long m nStatus D, Date date) {
    long dateT   = date.getT  ();
    //  f t  da ly batch  s for a day before   started us ng snow flake  Ds. Never adjust.
     f (!Snowflake dParser. sUsableSnowflakeT  stamp(dateT  )) {
      return m nStatus D;
    }

    long earl estStartT   = dateT   - MAX MUM_OUT_OF_ORDER_TOLERANCE_M LL S;
    long m nStatusT   = Snowflake dParser.getT  stampFromT et d(m nStatus D);
     f (m nStatusT   < earl estStartT  ) {
      long newM n d =  Snowflake dParser.generateVal dStatus d(earl estStartT  , 0);
      LOG. nfo("Da ly batch for " + date + " has badly out of order t et: " + m nStatus D
          + ". T  m nStatus D for t  day t  batch  s adjusted to " + newM n d);
      return newM n d;
    } else {
      return m nStatus D;
    }
  }

  /**
   * Returns a reader that reads t ets from t  g ven d rectory.
   *
   * @param arch veSeg nt Determ nes t  t  sl ce  D of all read t ets.
   * @param t etsPath T  path to t  d rectory w re t  t ets for t  day are stored.
   * @param docu ntFactory T  Thr ft ndex ngEvent to T etDocu nt converter.
   */
  publ c RecordReader<T etDocu nt> getT etReaders(
      Arch veSeg nt arch veSeg nt,
      Path t etsPath,
      Docu ntFactory<Thr ft ndex ngEvent> docu ntFactory) throws  OExcept on {
    RecordReader<T etDocu nt> t etDocu ntReader =
        new Transform ngRecordReader<>(
            createT etReader(t etsPath), new Funct on<Thr ft ndex ngEvent, T etDocu nt>() {
          @Overr de
          publ c T etDocu nt apply(Thr ft ndex ngEvent event) {
            return new T etDocu nt(
                event.getSort d(),
                arch veSeg nt.getT  Sl ce D(),
                Earlyb rdThr ftDocu ntUt l.getCreatedAtMs(event.getDocu nt()),
                docu ntFactory.newDocu nt(event)
            );
          }
        });

    t etDocu ntReader.setExhaustStream(true);
    return t etDocu ntReader;
  }

  pr vate RecordReader<Thr ft ndex ngEvent> createT etReader(Path t etsPath) throws  OExcept on {
     f (date.before(START_DATE_ NCLUS VE)) {
      return new EmptyRecordReader<>();
    }

    L st<RecordReader<Thr ft ndex ngEvent>> readers = L sts.newArrayL st();
    F leSystem hdfs = HdfsUt l.getHdfsF leSystem();
    try {
      Path dayPath = new Path(t etsPath, Arch veHDFSUt ls.dateToPath(date, "/"));
      Path part  onPath =
          new Path(dayPath, Str ng.format("p_%d_of_%d", hashPart  on D, numHashPart  ons));
      PathF lter pathF lter =
          Conf g.env ron nt sTest() ? TXT_DATA_F LES_F LTER : LZO_DATA_F LES_F LTER;
      F leStatus[] f les = hdfs.l stStatus(part  onPath, pathF lter);
      for (F leStatus f leStatus : f les) {
        Str ng f leStatusPath = f leStatus.getPath().toStr ng().replaceAll("f le:/", "/");
        RecordReader<Thr ft ndex ngEvent> reader = createRecordReaderW hRetr es(f leStatusPath);
        readers.add(reader);
      }
    } f nally {
       OUt ls.closeQu etly(hdfs);
    }

     f (readers. sEmpty()) {
      return new EmptyRecordReader<>();
    }

    return new  rg ngSortedRecordReader<>(DESC_THR FT_ NDEX NG_EVENT_COMPARATOR, readers);
  }

  pr vate RecordReader<Thr ft ndex ngEvent> createRecordReaderW hRetr es(Str ng f lePath)
      throws  OExcept on {
    Pred cate<Thr ft ndex ngEvent> recordF lter = getRecordF lter();
     nt numTr es = 0;
    wh le (true) {
      try {
        ++numTr es;
        return new LzoThr ftBlockF leReader<>(f lePath, Thr ft ndex ngEvent.class, recordF lter);
      } catch ( OExcept on e) {
         f (numTr es < READER_ N T_ OEXCEPT ON_RETR ES) {
          LOG.warn("Fa led to open LzoThr ftBlockF leReader for " + f lePath + ". W ll retry.", e);
        } else {
          LOG.error("Fa led to open LzoThr ftBlockF leReader for " + f lePath
              + " after too many retr es.", e);
          throw e;
        }
      }
    }
  }

  pr vate Pred cate<Thr ft ndex ngEvent> getRecordF lter() {
    return Conf g.env ron nt sTest() ? null :  nput -> {
       f ( nput == null) {
        return false;
      }
      //   only guard aga nst status  Ds that are too small, because    s poss ble
      // for a very old t et to get  nto today's batch, but not poss ble for a very
      // large  D (a future t et  D that  s not yet publ s d) to get  n today's
      // batch, unless t et  D generat on  ssed up.
      long status d =  nput.getSort d();
      boolean keep = status d >= m nStatus D;
       f (!keep) {
        LOG.debug("Out of order docu nt d: {} m nStatus D: {} Date: {} Path: {}",
            status d, m nStatus D, date, path);
        OUT_OF_ORDER_STATUSES_SK PPED. ncre nt();
      }
      return keep;
    };
  }

  /**
   * Returns t  number of statuses  n t  batch
   */
  publ c  nt getStatusCount() {
    return statusCount;
  }

  /**
   * Was t  _status_count f le was found  n t  folder.
   */
  publ c boolean hasStatusCount() {
    return hasStatusCountF le;
  }

  publ c long getM nStatus D() {
    return m nStatus D;
  }

  publ c long getMaxStatus D() {
    return maxStatus D;
  }

  publ c Date getDate() {
    return date;
  }

  publ c Path getPath() {
    return path;
  }

  /**
   * C ck w t r t  part  on  s
   * . empty and
   * .    s d sallo d (empty part  on can only happen before 2010)
   * (Empty part  on  ans that t  d rectory  s m ss ng w n scan happens.)
   *
   * @return true  f t  part  on has no docu nts and    s not allo d.
   */
  publ c boolean  sD sallo dEmptyPart  on() {
    return hasStatusCountF le
        && statusCount == 0
        && m nStatus D == Da lyStatusBatch.EMPTY_BATCH_STATUS_ D
        && maxStatus D == Da lyStatusBatch.EMPTY_BATCH_STATUS_ D
        && date.after(getEarl estDenseDay());
  }

  @Overr de
  publ c Str ng toStr ng() {
    return "Part  onedBatch[hashPart  on d=" + hashPart  on D
        + ",numHashPart  ons=" + numHashPart  ons
        + ",date=" + date
        + ",path=" + path
        + ",hasStatusCountF le=" + hasStatusCountF le
        + ",statusCount=" + statusCount + "]";
  }

  pr vate Date getEarl estDenseDay() {
    return Earlyb rdConf g.getDate("arch ve_search_earl est_dense_day");
  }
}
