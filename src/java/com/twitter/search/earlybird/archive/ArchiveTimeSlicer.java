package com.tw ter.search.earlyb rd.arch ve;

 mport java. o. OExcept on;
 mport java.ut l.ArrayL st;
 mport java.ut l.Calendar;
 mport java.ut l.Collect ons;
 mport java.ut l.Comparator;
 mport java.ut l.Date;
 mport java.ut l.L st;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.base.Pred cate;
 mport com.google.common.collect.L sts;


 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndex ngEvent;
 mport com.tw ter.search.common.ut l. o. rg ngSortedRecordReader;
 mport com.tw ter.search.common.ut l. o.recordreader.RecordReader;
 mport com.tw ter.search.earlyb rd.conf g.T erConf g;
 mport com.tw ter.search.earlyb rd.docu nt.Docu ntFactory;
 mport com.tw ter.search.earlyb rd.docu nt.Thr ft ndex ngEventDocu ntFactory;
 mport com.tw ter.search.earlyb rd.docu nt.T etDocu nt;


/**
 * Respons ble for tak ng a number of da ly status batc s and part  on ng t m  nto t   sl ces
 * wh ch w ll be used to bu ld seg nts.
 *
 *   try to put at most N number of t ets  nto a t   sl ce.
 */
publ c class Arch veT  Sl cer {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Arch veT  Sl cer.class);

  pr vate stat c f nal Comparator<T etDocu nt> ASCEND NG =
      (o1, o2) -> Long.compare(o1.getT et D(), o2.getT et D());

  pr vate stat c f nal Comparator<T etDocu nt> DESCEND NG =
      (o1, o2) -> Long.compare(o2.getT et D(), o1.getT et D());

  // Represents a number of da ly batc s wh ch w ll go  nto a seg nt.
  publ c stat c f nal class Arch veT  Sl ce {
    pr vate Date startDate;
    pr vate Date endDate;
    pr vate  nt statusCount;
    pr vate f nal Da lyStatusBatc s d rectory;
    pr vate f nal Arch veEarlyb rd ndexConf g earlyb rd ndexConf g;

    // T  l st  s always ordered from oldest day, to t  ne st day.
    // For t  on-d sk arch ve,   reverse t  days  n getT etReaders().
    pr vate f nal L st<Da lyStatusBatch> batc s = L sts.newArrayL st();

    pr vate Arch veT  Sl ce(Da lyStatusBatc s d rectory,
                             Arch veEarlyb rd ndexConf g earlyb rd ndexConf g) {
      t .d rectory = d rectory;
      t .earlyb rd ndexConf g = earlyb rd ndexConf g;
    }

    publ c Date getEndDate() {
      return endDate;
    }

    publ c  nt getStatusCount() {
      return statusCount;
    }

    publ c  nt getNumHashPart  ons() {
      return batc s. sEmpty() ? 0 : batc s.get(0).getNumHashPart  ons();
    }

    /**
     * Returns a reader for read ng t ets from t  t  sl ce.
     *
     * @param arch veSeg nt T  seg nt to wh ch t  t  sl ce belongs.
     * @param docu ntFactory T  Thr ft ndex ngEvent to T etDocu nt converter.
     * @param f lter A f lter that determ nes what dates should be read.
     */
    publ c RecordReader<T etDocu nt> getStatusReader(
        Arch veSeg nt arch veSeg nt,
        Docu ntFactory<Thr ft ndex ngEvent> docu ntFactory,
        Pred cate<Date> f lter) throws  OExcept on {
      //   no longer support Thr ftStatus based docu nt factor es.
      Precond  ons.c ckState(docu ntFactory  nstanceof Thr ft ndex ngEventDocu ntFactory);

      f nal  nt hashPart  on D = arch veSeg nt.getHashPart  on D();
      L st<RecordReader<T etDocu nt>> readers = new ArrayL st<>(batc s.s ze());
      L st<Da lyStatusBatch> orderedForRead ng = orderBatc sForRead ng(batc s);
      LOG. nfo("Creat ng new status reader for hashPart  on: "
          + hashPart  on D + " t  sl ce: " + getDescr pt on());

      for (Da lyStatusBatch batch : orderedForRead ng) {
         f (f lter.apply(batch.getDate())) {
          LOG. nfo("Add ng reader for " + batch.getDate() + " " + getDescr pt on());
          Part  onedBatch part  onedBatch = batch.getPart  on(hashPart  on D);
          // Don't even try to create a reader  f t  part  on  s empty.
          // T re does not seem to be any problem  n product on now, but HDFS F leSystem's javadoc
          // does  nd cate that l stStatus()  s allo d to throw a F leNotFoundExcept on  f t 
          // part  on does not ex st. T  c ck makes t  code more robust aga nst future
          // HDFS F leSystem  mple ntat on changes.
           f (part  onedBatch.getStatusCount() > 0) {
            RecordReader<T etDocu nt> t etReaders = part  onedBatch.getT etReaders(
                arch veSeg nt,
                d rectory.getStatusPathToUseForDay(batch.getDate()),
                docu ntFactory);
            readers.add(t etReaders);
          }
        } else {
          LOG. nfo("F ltered reader for " + batch.getDate() + " " + getDescr pt on());
        }
      }

      LOG. nfo("Creat ng reader for t  sl ce: " + getDescr pt on()
          + " w h " + readers.s ze() + " readers");

      return new  rg ngSortedRecordReader<T etDocu nt>(get rg ngComparator(), readers);
    }

    pr vate L st<Da lyStatusBatch> orderBatc sForRead ng(L st<Da lyStatusBatch> orderedBatc s) {
      // For t   ndex formats us ng stock lucene,   want t  most recent days to be  ndexed f rst.
      //  n t  tw ter  n- mory opt m zed  ndexes, older t ets w ll be added f rst, and
      // opt m zat on w ll reverse t  docu nts to make most recent t ets be f rst.
      return t .earlyb rd ndexConf g. sUs ngL FODocu ntOrder ng()
          ? orderedBatc s : L sts.reverse(orderedBatc s);
    }

    pr vate Comparator<T etDocu nt> get rg ngComparator() {
      //   always want to retr eve larger t et  ds f rst.
      // L FO  ans that t  smaller  ds get  nserted f rst --> ASCEND NG order.
      // F FO would  an that   want to f rst  nsert t  larger  ds --> DESCEND NG order.
      return t .earlyb rd ndexConf g. sUs ngL FODocu ntOrder ng()
          ? ASCEND NG : DESCEND NG;
    }

    /**
     * Returns t  smallest  ndexed t et  D  n t  t  sl ce for t  g ven part  on.
     *
     * @param hashPart  on D T  part  on.
     */
    publ c long getM nStatus D( nt hashPart  on D) {
       f (batc s. sEmpty()) {
        return 0;
      }

      for ( nt   = 0;   < batc s.s ze();  ++) {
        long m nStatus D = batc s.get( ).getPart  on(hashPart  on D).getM nStatus D();
         f (m nStatus D != Da lyStatusBatch.EMPTY_BATCH_STATUS_ D) {
          return m nStatus D;
        }
      }

      return 0;
    }

    /**
     * Returns t  h g st  ndexed t et  D  n t  t  sl ce for t  g ven part  on.
     *
     * @param hashPart  on D T  part  on.
     */
    publ c long getMaxStatus D( nt hashPart  on D) {
       f (batc s. sEmpty()) {
        return Long.MAX_VALUE;
      }

      for ( nt   = batc s.s ze() - 1;   >= 0;  --) {
        long maxStatus D = batc s.get( ).getPart  on(hashPart  on D).getMaxStatus D();
         f (maxStatus D != Da lyStatusBatch.EMPTY_BATCH_STATUS_ D) {
          return maxStatus D;
        }
      }

      return Long.MAX_VALUE;
    }

    /**
     * Returns a str ng w h so   nformat on for t  t  sl ce.
     */
    publ c Str ng getDescr pt on() {
      Str ngBu lder bu lder = new Str ngBu lder();
      bu lder.append("T  Sl ce[start date=");
      bu lder.append(Da lyStatusBatc s.DATE_FORMAT.format(startDate));
      bu lder.append(", end date=");
      bu lder.append(Da lyStatusBatc s.DATE_FORMAT.format(endDate));
      bu lder.append(", status count=");
      bu lder.append(statusCount);
      bu lder.append(", days count=");
      bu lder.append(batc s.s ze());
      bu lder.append("]");
      return bu lder.toStr ng();
    }
  }

  pr vate f nal  nt maxSeg ntS ze;
  pr vate f nal Da lyStatusBatc s da lyStatusBatc s;
  pr vate f nal Date t erStartDate;
  pr vate f nal Date t erEndDate;
  pr vate f nal Arch veEarlyb rd ndexConf g earlyb rd ndexConf g;

  pr vate L st<Arch veT  Sl ce> lastCac dT  sl ces = null;

  publ c Arch veT  Sl cer( nt maxSeg ntS ze,
                           Da lyStatusBatc s da lyStatusBatc s,
                           Arch veEarlyb rd ndexConf g earlyb rd ndexConf g) {
    t (maxSeg ntS ze, da lyStatusBatc s, T erConf g.DEFAULT_T ER_START_DATE,
        T erConf g.DEFAULT_T ER_END_DATE, earlyb rd ndexConf g);
  }

  publ c Arch veT  Sl cer( nt maxSeg ntS ze,
                           Da lyStatusBatc s da lyStatusBatc s,
                           Date t erStartDate,
                           Date t erEndDate,
                           Arch veEarlyb rd ndexConf g earlyb rd ndexConf g) {
    t .maxSeg ntS ze = maxSeg ntS ze;
    t .da lyStatusBatc s = da lyStatusBatc s;
    t .t erStartDate = t erStartDate;
    t .t erEndDate = t erEndDate;
    t .earlyb rd ndexConf g = earlyb rd ndexConf g;
  }

  pr vate boolean cac  sVal d() throws  OExcept on {
    return lastCac dT  sl ces != null
        && !lastCac dT  sl ces. sEmpty()
        && cac  sVal d(lastCac dT  sl ces.get(lastCac dT  sl ces.s ze() - 1).endDate);
  }

  pr vate boolean cac  sVal d(Date lastDate) throws  OExcept on {
     f (lastCac dT  sl ces == null || lastCac dT  sl ces. sEmpty()) {
      return false;
    }

    // C ck  f   have a da ly batch ne r than t  last batch used for t  ne st t  sl ce.
    Calendar cal = Calendar.get nstance();
    cal.setT  (lastDate);
    cal.add(Calendar.DATE, 1);
    Date nextDate = cal.getT  ();

    boolean foundBatch = da lyStatusBatc s.hasVal dBatchForDay(nextDate);

    LOG. nfo("C ck ng cac : Looked for val d batch for day {}. Found: {}",
        Da lyStatusBatc s.DATE_FORMAT.format(nextDate), foundBatch);

    return !foundBatch;
  }

  pr vate boolean t  sl ce sFull(Arch veT  Sl ce t  Sl ce, Da lyStatusBatch batch) {
    return t  Sl ce.statusCount + batch.getMaxPerPart  onStatusCount() > maxSeg ntS ze;
  }

  pr vate vo d doT  Sl c ng() throws  OExcept on {
    da lyStatusBatc s.refresh();

    lastCac dT  sl ces = L sts.newArrayL st();
    Arch veT  Sl ce currentT  Sl ce = null;

    //  erate over each day and add   to t  current t  sl ce, unt l   gets full.
    for (Da lyStatusBatch batch : da lyStatusBatc s.getStatusBatc s()) {
       f (!batch. sVal d()) {
        LOG.warn("Sk pp ng hole: " + batch.getDate());
        cont nue;
      }

       f (currentT  Sl ce == null || t  sl ce sFull(currentT  Sl ce, batch)) {
         f (currentT  Sl ce != null) {
          LOG. nfo("F lled t  sl ce: " + currentT  Sl ce.getDescr pt on());
        }
        currentT  Sl ce = new Arch veT  Sl ce(da lyStatusBatc s, earlyb rd ndexConf g);
        currentT  Sl ce.startDate = batch.getDate();
        lastCac dT  sl ces.add(currentT  Sl ce);
      }

      currentT  Sl ce.endDate = batch.getDate();
      currentT  Sl ce.statusCount += batch.getMaxPerPart  onStatusCount();
      currentT  Sl ce.batc s.add(batch);
    }
    LOG. nfo("Last t  sl ce: {}", currentT  Sl ce.getDescr pt on());

    LOG. nfo("Done w h t   sl c ng. Number of t  sl ces: {}",
        lastCac dT  sl ces.s ze());
  }

  /**
   * Returns all t  sl ces for t  earlyb rd.
   */
  publ c L st<Arch veT  Sl ce> getT  Sl ces() throws  OExcept on {
     f (cac  sVal d()) {
      return lastCac dT  sl ces;
    }

    LOG. nfo("Cac   s outdated. Load ng new da ly batc s now...");

    doT  Sl c ng();

    return lastCac dT  sl ces != null ? Collect ons.unmod f ableL st(lastCac dT  sl ces) : null;
  }

  /**
   * Return t  t  sl ces that overlap t  t er start/end date ranges  f t y are spec f ed
   */
  publ c L st<Arch veT  Sl ce> getT  Sl ces nT erRange() throws  OExcept on {
    L st<Arch veT  Sl ce> t  Sl ces = getT  Sl ces();
     f (t erStartDate == T erConf g.DEFAULT_T ER_START_DATE
        && t erEndDate == T erConf g.DEFAULT_T ER_END_DATE) {
      return t  Sl ces;
    }

    L st<Arch veT  Sl ce> f lteredT  Sl ce = L sts.newArrayL st();
    for (Arch veT  Sl ce t  Sl ce : t  Sl ces) {
       f (t  Sl ce.startDate.before(t erEndDate) && !t  Sl ce.endDate.before(t erStartDate)) {
        f lteredT  Sl ce.add(t  Sl ce);
      }
    }

    return f lteredT  Sl ce;
  }

  @V s bleForTest ng
  protected Da lyStatusBatc s getDa lyStatusBatc s() {
    return da lyStatusBatc s;
  }
}
